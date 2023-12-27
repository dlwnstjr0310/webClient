package com.web.refactor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.refactor.auth.CustomPasswordEncoder;
import com.web.refactor.domain.common.Status;
import com.web.refactor.domain.entity.Study;
import com.web.refactor.domain.entity.Training;
import com.web.refactor.domain.entity.employee.Employee;
import com.web.refactor.model.request.ApiRequest;
import com.web.refactor.model.response.ApiResponse;
import com.web.refactor.repository.EmployeeRepository;
import com.web.refactor.repository.TrainingRepository;
import com.web.refactor.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

import static com.web.refactor.domain.entity.employee.Role.ROLE_MEMBER;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class DBMigrationService {

	private final EmployeeRepository employeeRepository;
	private final TrainingRepository trainingRepository;
	private final ObjectMapper mapper;
	private final CustomPasswordEncoder passwordEncoder;

	@Value("${API_KEY}")
	private String API_KEY;

	@Transactional
	public void migration(String startDate, String endDate) {

		WebClient webClient = WebClientUtil.getBaseUrl("https://www.hrd.go.kr/jsp/HRDP/HRDPO00/HRDPOA60/HRDPOA60_1.jsp?");

		Map<String, Object> block = webClient.get()
				.uri(uriBuilder -> uriBuilder
						.queryParam("returnType", "JSON")
						.queryParam("authKey", API_KEY)
						.queryParam("pageNum", "1")
						.queryParam("pageSize", "100")
						.queryParam("srchTraStDt", startDate)
						.queryParam("srchTraEndDt", endDate)
						.queryParam("outType", "1")
						.queryParam("sort", "ASC")
						.queryParam("sortCol", "TRNG_BGDE")
						.queryParam("srchTraArea1", "11")
						.queryParam("srchNcs1", "20")
						.queryParam("crseTracseSe", "C0104")
						.queryParam("srchTraGbn", "M1001")
						.build())
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
				})
				.block();

		String returnJSON = Objects.requireNonNull(block).get("returnJSON").toString();

		try {
			ApiResponse.Info info = mapper.readValue(returnJSON, ApiResponse.Info.class);

			List<Training> trainingList = new ArrayList<>();
			Employee writer = Employee.builder().id(1L).build();

			Map<String, ApiRequest.TrainingResult> resultMap = new HashMap<>();

			info.getSearchList().forEach(training -> {

				Map<Integer, ApiRequest.StudyResult> studyResultMap = new HashMap<>();

				Integer count = Integer.valueOf(training.getTrprDegr());

				studyResultMap.put(count,
						ApiRequest.StudyResult.builder()
								.startDate(training.getTraStartDate())
								.endDate(training.getTraEndDate())
								.count(count)
								.capacity(Long.valueOf(training.getYardMan()))
								.build()
				);

				ApiRequest.TrainingResult trainingResult = ApiRequest.TrainingResult.builder()
						.id(training.getTrprId())
						.title(training.getTitle())
						.count(count)
						.centerId(training.getTrainstCstId())
						.studyResultMap(studyResultMap)
						.build();

				resultMap.put(training.getTrprId(), trainingResult);
			});

			setUpTrainingDetail(resultMap);

			resultMap.forEach((key, value) -> {

				value.getStudyResultMap().entrySet().stream().sorted(Map.Entry.comparingByKey());

				Training training = value.toEntity(writer);

				value.getStudyResultMap().forEach((studyKey, studyValue) -> {

					String name = ofNullable(studyValue.getName())
							.orElse("테스트계정");

					String email = ofNullable(studyValue.getEmail())
							.orElse("test@dev.com");

					String telephone = ofNullable(studyValue.getTelephone())
							.orElse("010-1234-1234");

					Study study = studyValue.toEntity(training);

					employeeRepository.findByEmail(email).ifPresentOrElse(study::setTeacher
							,
							() -> {
								Employee teacher = employeeRepository.save(Employee.builder()
										.employeeName(name)
										.account(email)
										.password(passwordEncoder.encode(email))
										.email(email)
										.phone(telephone)
										.isLocked(false)
										.role(ROLE_MEMBER)
										.department(Status.Department.MANAGEMENT)
										.build());

								study.setTeacher(teacher);
							});
					training.getStudyList().add(study);
				});
				trainingList.add(training);
			});

			trainingRepository.saveAll(trainingList);

		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("변환 실패");
		}
	}

	private void setUpTrainingDetail(Map<String, ApiRequest.TrainingResult> keywordMap) {
		WebClient webClient = WebClientUtil.getBaseUrl("https://www.hrd.go.kr/jsp/HRDP/HRDPO00/HRDPOA60/HRDPOA60_2.jsp?");

		keywordMap.forEach((key, value) -> {
			for (int i = value.getCount() - 1; 1 <= i; i--) {

				int count = value.getCount() - i;

				ApiRequest.Keyword keyword = ApiRequest.Keyword.builder()
						.id(key)
						.count(count)
						.centerId(value.getCenterId())
						.build();

				String returnJSON = getAPIResult(webClient, keyword);

				try {
					Map<String, Map<String, String>> map = mapper.readValue(returnJSON, new TypeReference<>() {
					});

					value.getStudyResultMap().computeIfAbsent(
							count,
							(studyMapKey) -> getTrainingAdditionalData(keyword, map)
					);

					String name = map.get("inst_base_info").get("trprChap");
					String email = map.get("inst_base_info").get("trprChapEmail");
					String telephone = map.get("inst_base_info").get("trprChapTel");

					ApiRequest.StudyResult studyResult = value.getStudyResultMap().get(count);

					studyResult.setName(name);
					studyResult.setEmail(email);
					studyResult.setTelephone(telephone);

				} catch (JsonProcessingException e) {
					throw new IllegalArgumentException("변환 실패 -2");
				}
			}
		});
	}

	private ApiRequest.StudyResult getTrainingAdditionalData(ApiRequest.Keyword keyword, Map<String, Map<String, String>> map) {

		WebClient webClient = WebClientUtil.getBaseUrl("https://www.hrd.go.kr/jsp/HRDP/HRDPO00/HRDPOA60/HRDPOA60_3.jsp?");

		ApiRequest.StudyResult.StudyResultBuilder builder = ApiRequest.StudyResult.builder();

		String returnJSON = getAPIResult(webClient, keyword);

		try {
			List<Map<String, String>> list = mapper.readValue(returnJSON, new TypeReference<>() {
			});

			String capacity = list.get(0).get("totFxnum");
			String count = list.get(0).get("trprDegr");
			String startDate = list.get(0).get("trStaDt");
			String endDate = list.get(0).get("trEndDt");
			String totalStudyDay = map.get("inst_base_info").get("trDcnt");
			String totalStudyHour = map.get("inst_base_info").get("trtm");

			builder.capacity(Long.valueOf(capacity))
					.count(Integer.valueOf(count))
					.startDate(startDate)
					.endDate(endDate)
					.totalStudyDay(Integer.valueOf(totalStudyDay))
					.totalStudyHour(Integer.valueOf(totalStudyHour));

		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("변환 실패 -3" + e);
		}

		return builder.build();
	}

	private String getAPIResult(WebClient webClient, ApiRequest.Keyword keyword) {

		Map<String, Object> block = webClient.get()
				.uri(uriBuilder -> uriBuilder
						.queryParam("returnType", "JSON")
						.queryParam("authKey", API_KEY)
						.queryParam("srchTrprId", keyword.getId())
						.queryParam("srchTrprDegr", keyword.getCount())
						.queryParam("srchTorgId", keyword.getCenterId())
						.queryParam("outType", "2")
						.build())
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
				})
				.block();

		return Objects.requireNonNull(block).get("returnJSON").toString();
	}
}

