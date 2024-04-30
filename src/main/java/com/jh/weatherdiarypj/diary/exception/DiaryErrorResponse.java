package com.jh.weatherdiarypj.diary.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(description = "예외 응답 response")
public class DiaryErrorResponse {
    @Schema(description = "http 상태 코드", example = "404")
    private int status;

    @Schema(description = "예외에 대한 설명", example = "예외가 발생했습니다.")
    private String message;
}
