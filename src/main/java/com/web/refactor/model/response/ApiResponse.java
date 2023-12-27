package com.web.refactor.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ApiResponse {

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "HRD-NET API 조회 객체")
	public static class Info {
		@JsonProperty("srchList")
		List<SearchElement> searchList;
		@JsonProperty("scn_cnt")
		int scn_cnt;
		@JsonProperty("pageSize")
		int pageSize;
		@JsonProperty("pageNum")
		int pageNum;

	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "HRD-NET API 조회 객체 세부정보")
	public static class SearchElement {

		String eiEmplCnt3Gt10;
		String eiEmplRate6;
		String eiEmplCnt3;
		String eiEmplRate3;
		String title;
		Integer realMan;
		String telNo;
		String traStartDate;
		String grade;
		String ncsCd;
		String regCourseMan;
		String trprDegr;
		String address;
		String traEndDate;
		String subTitle;
		String instCd;
		String trngAreaCd;
		String trprId;
		String yardMan;
		Integer courseMan;
		String trainTarget;
		String trainTargetCd;
		String trainstCstId;
		String contents;
		String subTitleLink;
		String titleLink;
		String titleIcon;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "HRD-NET API 강의 세부조회 객체")
	public static class TrainingInfo {
		InstBaseInfo instBaseInfo;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "HRD-NET API 강의 조회 객체 -1")
	public static class InstBaseInfo {
		@JsonProperty("trprId")
		String trprId;

		@JsonProperty("trprDegr")
		int trprDegr;

		@JsonProperty("trprGbn")
		String trprGbn;

		@JsonProperty("trprTarget")
		String trprTarget;

		@JsonProperty("trprTargetNm")
		String trprTargetNm;

		@JsonProperty("trprNm")
		String trprNm;

		@JsonProperty("inoNm")
		String inoNm;

		@JsonProperty("instIno")
		String instIno;

		@JsonProperty("traingMthCd")
		String traingMthCd;

		@JsonProperty("trprChap")
		String trprChap;

		@JsonProperty("trprChapTel")
		String trprChapTel;

		@JsonProperty("trprChapEmail")
		String trprChapEmail;

		@JsonProperty("ncsYn")
		String ncsYn;

		@JsonProperty("ncsCd")
		String ncsCd;

		@JsonProperty("ncsNm")
		String ncsNm;

		@JsonProperty("trDcnt")
		int trDcnt;

		@JsonProperty("trtm")
		int trtm;

		@JsonProperty("zipCd")
		String zipCd;

		@JsonProperty("addr1")
		String addr1;

		@JsonProperty("addr2")
		String addr2;

		@JsonProperty("hpAddr")
		String hpAddr;

		@JsonProperty("filePath")
		String filePath;

		@JsonProperty("pFileName")
		String pFileName;

		@JsonProperty("torgParGrad")
		String torgParGrad;

		@JsonProperty("perTrco")
		int perTrco;

		@JsonProperty("instPerTrco")
		int instPerTrco;

	}
}
