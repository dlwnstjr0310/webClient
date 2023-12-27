package com.web.refactor.controller;

import com.web.refactor.model.response.Response;
import com.web.refactor.service.DBMigrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/migration")
@Tag(name = "DB Migration", description = "hrd-net 를 이용해 데이터를 복사하는 API")
public class DBMigrationController {

	private final DBMigrationService dbMigrationService;

	@PostMapping
	@Operation(summary = "데이터 복사", description = "HRD-NET 의 데이터를 가져와 DB 에 등록하는 API 입니다.")
	@ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = Response.class)))
	public Response<Void> dbIntegration(@RequestParam String startDate, @RequestParam String endDate) {
		dbMigrationService.migration(startDate, endDate);

		return Response.<Void>builder()
				.build();
	}

}
