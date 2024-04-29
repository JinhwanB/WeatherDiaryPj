package com.jh.weatherdiarypj.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter // 테스트용
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@ToString
@Schema(description = "조회된 일기 dto")
public class DiaryResponseDto {
    @Schema(description = "일기에 작성된 글", example = "test")
    private String content;

    @Schema(description = "날씨", example = "Cloud")
    private String weather;

    @Schema(description = "날씨 아이콘", example = "04n")
    private String ico;

    @Schema(description = "온도", example = "123.12")
    private double temp;

    @Schema(description = "일기 작성 날짜", example = "2024-04-30")
    private String writeDate;
}
