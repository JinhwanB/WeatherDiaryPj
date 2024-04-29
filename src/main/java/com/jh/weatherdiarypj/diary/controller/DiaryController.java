package com.jh.weatherdiarypj.diary.controller;

import com.jh.weatherdiarypj.diary.domain.Diary;
import com.jh.weatherdiarypj.diary.dto.DiaryResponseDto;
import com.jh.weatherdiarypj.diary.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/diaries")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "일기 api")
public class DiaryController {
    private final DiaryService diaryService;

    // 일기 작성
    @Operation(summary = "일기 작성")
    @Parameters(value = {
            @Parameter(name = "date", description = "작성할 일기의 날짜"),
            @Parameter(name = "text", description = "작성할 일기의 내용")
    })
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = DiaryResponseDto.class)))
    @PostMapping("/diary")
    public ResponseEntity<DiaryResponseDto> create(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String date,
            @RequestParam String text) throws IOException {
        Diary diary = diaryService.createDiary(date, text);
        return ResponseEntity.ok(diary.toDto());
    }
}
