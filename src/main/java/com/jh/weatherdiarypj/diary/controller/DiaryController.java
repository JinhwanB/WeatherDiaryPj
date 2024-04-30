package com.jh.weatherdiarypj.diary.controller;

import com.jh.weatherdiarypj.config.GlobalApiResponse;
import com.jh.weatherdiarypj.diary.domain.Diary;
import com.jh.weatherdiarypj.diary.dto.DiaryResponseDto;
import com.jh.weatherdiarypj.diary.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diaries")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "일기 api")
public class DiaryController {
    private final DiaryService diaryService;

    // 일기 작성
    @Operation(summary = "일기 작성")
    @Parameters(value = {
            @Parameter(name = "date", description = "작성할 일기의 날짜"),
            @Parameter(name = "text", description = "작성할 일기의 내용")
    })
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = DiaryResponseDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 유효성 검증 실패", content = @Content(array = @ArraySchema(schema = @Schema(implementation = GlobalApiResponse.class))))
    })
    @PostMapping("/diary")
    public ResponseEntity<DiaryResponseDto> create(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String date,
            @RequestParam String text) {
        log.info("작성 날짜={}", date);
        log.info("작성할 내용={}", text);

        Diary diary = diaryService.createDiary(date, text);
        return ResponseEntity.ok(diary.toDto());
    }

    // 일기 수정
    @Operation(summary = "일기 수정")
    @Parameters(value = {
            @Parameter(name = "date", description = "수정할 일기의 날짜"),
            @Parameter(name = "text", description = "수정할 일기의 내용")
    })
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = DiaryResponseDto.class)))
    @PutMapping("/diary")
    public ResponseEntity<DiaryResponseDto> update(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String date,
            @RequestParam String text) {
        log.info("수정할 일기의 날짜={}", date);
        log.info("수정할 내용={}", text);

        Diary diary = diaryService.updateDiary(date, text);
        return ResponseEntity.ok(diary.toDto());
    }

    // 일기 삭제
    @Operation(summary = "일기 삭제")
    @Parameter(name = "date", description = "삭제할 일기의 날짜")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = String.class)))
    @DeleteMapping("/diary")
    public ResponseEntity<String> delete(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String date) {
        log.info("삭제할 일기의 작성 날짜={}", date);

        diaryService.deleteDiary(date);
        return ResponseEntity.ok("삭제 성공");
    }

    // 일기 조회
    @Operation(summary = "일기 조회")
    @Parameter(name = "date", description = "삭제할 일기의 날짜")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DiaryResponseDto.class))))
    @GetMapping("/diary")
    public ResponseEntity<List<DiaryResponseDto>> get(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String date) {
        log.info("조회할 일기의 작성 날짜={}", date);

        List<Diary> diaryList = diaryService.getDiary(date);
        return ResponseEntity.ok(diaryList.stream().map(Diary::toDto).toList());
    }

    // startDate부터 endDate까지의 일기 조회
    @Operation(summary = "일기 조회 - startDate부터 endDate까지의 일기 조회")
    @Parameters(value = {
            @Parameter(name = "startDate", description = "조회할 일기 범위의 시작날짜"),
            @Parameter(name = "endDate", description = "조회할 일기 범위의 끝날짜")
    })
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DiaryResponseDto.class))))
    @GetMapping
    public ResponseEntity<List<DiaryResponseDto>> getFromStartEnd(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String startDate,
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String endDate) {
        log.info("조회할 날짜의 시작날짜 ={}", startDate);
        log.info("조회할 날짜의 끝날짜={}", endDate);

        List<Diary> diaries = diaryService.getDiaries(startDate, endDate);
        return ResponseEntity.ok(diaries.stream().map(Diary::toDto).toList());
    }
}
