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
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = GlobalApiResponse.class), examples = @ExampleObject(value = "{\n  \"status\": 200,\n  \"message\": \"성공\",\n \"result\": [\n {\n \"content\": \"good\",\n \"weather\": \"Clouds\",\n \"ico\": \"04d\",\n \"temp\": 294.56,\n \"writeDate\": \"1234-12-12\"\n}\n]}"))),
            @ApiResponse(responseCode = "400", description = "파라미터 유효성 검증 실패", content = @Content(array = @ArraySchema(schema = @Schema(implementation = GlobalApiResponse.class)), examples = @ExampleObject(value = "{\n  \"status\": 400,\n  \"message\": \"파라미터 유효성 검증 실패\",\n \"result\": []}"))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 오류", content = @Content(schema = @Schema(implementation = GlobalApiResponse.class), examples = @ExampleObject(value = "{\n  \"status\": 500,\n  \"message\": \"예상치 못한 오류\",\n \"result\": []}")))
    })

    @PostMapping("/diary")
    public ResponseEntity<GlobalApiResponse> create(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String date,
            @RequestParam String text) {
        log.info("작성 날짜={}", date);
        log.info("작성할 내용={}", text);

        Diary diary = diaryService.createDiary(date, text);
        List<DiaryResponseDto> list = new ArrayList<>(List.of(diary.toDto()));
        return ResponseEntity.ok(GlobalApiResponse.toApiResponse(list));
    }

    // 일기 수정
    @Operation(summary = "일기 수정")
    @Parameters(value = {
            @Parameter(name = "date", description = "수정할 일기의 날짜"),
            @Parameter(name = "text", description = "수정할 일기의 내용")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = GlobalApiResponse.class), examples = @ExampleObject(value = "{\n  \"status\": 200,\n  \"message\": \"성공\",\n \"result\": [\n {\n \"content\": \"good\",\n \"weather\": \"Clouds\",\n \"ico\": \"04d\",\n \"temp\": 294.56,\n \"writeDate\": \"1234-12-12\"\n}\n]}"))),
            @ApiResponse(responseCode = "404", description = "수정하려는 일기가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = GlobalApiResponse.class), examples = @ExampleObject(value = "{\n  \"status\": 404,\n  \"message\": \"일기를 찾을 수 없습니다.\",\n \"result\": []}"))),
            @ApiResponse(responseCode = "400", description = "파라미터 유효성 검증 실패", content = @Content(array = @ArraySchema(schema = @Schema(implementation = GlobalApiResponse.class)), examples = @ExampleObject(value = "{\n  \"status\": 400,\n  \"message\": \"파라미터 유효성 검증 실패\",\n \"result\": []}"))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 오류", content = @Content(schema = @Schema(implementation = GlobalApiResponse.class), examples = @ExampleObject(value = "{\n  \"status\": 500,\n  \"message\": \"예상치 못한 오류\",\n \"result\": []}")))
    })
    @PutMapping("/diary")
    public ResponseEntity<GlobalApiResponse> update(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String date,
            @RequestParam String text) {
        log.info("수정할 일기의 날짜={}", date);
        log.info("수정할 내용={}", text);

        Diary diary = diaryService.updateDiary(date, text);
        List<DiaryResponseDto> list = new ArrayList<>(List.of(diary.toDto()));
        return ResponseEntity.ok(GlobalApiResponse.toApiResponse(list));
    }

    // 일기 삭제
    @Operation(summary = "일기 삭제")
    @Parameter(name = "date", description = "삭제할 일기의 날짜")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = GlobalApiResponse.class), examples = @ExampleObject(value = "{\n  \"status\": 200,\n  \"message\": \"성공\",\n \"result\": []}"))),
            @ApiResponse(responseCode = "404", description = "삭제하려는 일기가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = GlobalApiResponse.class), examples = @ExampleObject(value = "{\n  \"status\": 404,\n  \"message\": \"일기를 찾을 수 없습니다.\",\n \"result\": []}"))),
            @ApiResponse(responseCode = "400", description = "파라미터 유효성 검증 실패", content = @Content(array = @ArraySchema(schema = @Schema(implementation = GlobalApiResponse.class)), examples = @ExampleObject(value = "{\n  \"status\": 400,\n  \"message\": \"파라미터 유효성 검증 실패\",\n \"result\": []}"))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 오류", content = @Content(schema = @Schema(implementation = GlobalApiResponse.class), examples = @ExampleObject(value = "{\n  \"status\": 500,\n  \"message\": \"예상치 못한 오류\",\n \"result\": []}")))
    })
    @DeleteMapping("/diary")
    public ResponseEntity<GlobalApiResponse> delete(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String date) {
        log.info("삭제할 일기의 작성 날짜={}", date);

        diaryService.deleteDiary(date);
        GlobalApiResponse response = GlobalApiResponse.builder()
                .status(200)
                .message("성공")
                .build();
        return ResponseEntity.ok(response);
    }

    // 일기 조회
    @Operation(summary = "일기 조회")
    @Parameter(name = "date", description = "삭제할 일기의 날짜")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = GlobalApiResponse.class), examples = @ExampleObject(value = "{\n  \"status\": 200,\n  \"message\": \"성공\",\n \"result\": [\n {\n \"content\": \"good\",\n \"weather\": \"Clouds\",\n \"ico\": \"04d\",\n \"temp\": 294.56,\n \"writeDate\": \"1234-12-12\"\n}\n]}"))),
            @ApiResponse(responseCode = "400", description = "파라미터 유효성 검증 실패", content = @Content(array = @ArraySchema(schema = @Schema(implementation = GlobalApiResponse.class)), examples = @ExampleObject(value = "{\n  \"status\": 400,\n  \"message\": \"파라미터 유효성 검증 실패\",\n \"result\": []}"))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 오류", content = @Content(schema = @Schema(implementation = GlobalApiResponse.class), examples = @ExampleObject(value = "{\n  \"status\": 500,\n  \"message\": \"예상치 못한 오류\",\n \"result\": []}")))
    })
    @GetMapping("/diary")
    public ResponseEntity<GlobalApiResponse> get(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String date) {
        log.info("조회할 일기의 작성 날짜={}", date);

        List<Diary> diaryList = diaryService.getDiary(date);
        List<DiaryResponseDto> dtoList = diaryList.stream().map(Diary::toDto).toList();
        return ResponseEntity.ok(GlobalApiResponse.toApiResponse(dtoList));
    }

    // startDate부터 endDate까지의 일기 조회
    @Operation(summary = "일기 조회 - startDate부터 endDate까지의 일기 조회")
    @Parameters(value = {
            @Parameter(name = "startDate", description = "조회할 일기 범위의 시작날짜"),
            @Parameter(name = "endDate", description = "조회할 일기 범위의 끝날짜")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = GlobalApiResponse.class), examples = @ExampleObject(value = "{\n  \"status\": 200,\n  \"message\": \"성공\",\n \"result\": [\n {\n \"content\": \"good\",\n \"weather\": \"Clouds\",\n \"ico\": \"04d\",\n \"temp\": 294.56,\n \"writeDate\": \"1234-12-12\"\n}\n]}"))),
            @ApiResponse(responseCode = "400", description = "파라미터 유효성 검증 실패", content = @Content(array = @ArraySchema(schema = @Schema(implementation = GlobalApiResponse.class)), examples = @ExampleObject(value = "{\n  \"status\": 400,\n  \"message\": \"파라미터 유효성 검증 실패\",\n \"result\": []}"))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 오류", content = @Content(schema = @Schema(implementation = GlobalApiResponse.class), examples = @ExampleObject(value = "{\n  \"status\": 500,\n  \"message\": \"예상치 못한 오류\",\n \"result\": []}")))
    })
    @GetMapping
    public ResponseEntity<GlobalApiResponse> getFromStartEnd(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String startDate,
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-mm-dd로 작성해야 합니다.") String endDate) {
        log.info("조회할 날짜의 시작날짜 ={}", startDate);
        log.info("조회할 날짜의 끝날짜={}", endDate);

        List<Diary> diaries = diaryService.getDiaries(startDate, endDate);
        List<DiaryResponseDto> dtoList = diaries.stream().map(Diary::toDto).toList();
        return ResponseEntity.ok(GlobalApiResponse.toApiResponse(dtoList));
    }
}
