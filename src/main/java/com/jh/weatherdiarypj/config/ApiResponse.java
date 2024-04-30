package com.jh.weatherdiarypj.config;

import com.jh.weatherdiarypj.diary.dto.DiaryResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter // 테스트용
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@ToString
@Schema(description = "전체 api 응답 response")
public class ApiResponse {
    @Schema(description = "http 상태 코드", examples = {"200", "400", "404", "500"})
    private int status;

    @Schema(description = "예외에 대한 설명", examples = {"성공", "유효성 검사 실패", "일기를 찾을 수 없습니다.", "서버에 문의하세요."})
    private String message;

    @Schema(description = "성공 시 응답 결과", implementation = DiaryResponseDto.class)
    private DiaryResponseDto result;

    // api 응답 성공 시 apiResponse로 변환 메소드
    public ApiResponse toApiResponse(DiaryResponseDto diaryResponseDto) {
        return ApiResponse.builder()
                .message("성공")
                .result(diaryResponseDto)
                .status(200)
                .build();
    }
}
