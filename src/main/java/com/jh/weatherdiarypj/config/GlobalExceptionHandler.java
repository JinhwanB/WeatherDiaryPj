package com.jh.weatherdiarypj.config;

import com.jh.weatherdiarypj.diary.exception.DiaryException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<List<GlobalApiResponse>> handleValidException(ConstraintViolationException e) {
        log.error("유효성 검사 실패");

        List<GlobalApiResponse> list = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            GlobalApiResponse response = GlobalApiResponse.builder()
                    .status(400)
                    .message(constraintViolation.getMessage())
                    .build();
            list.add(response);
        }


        return ResponseEntity.badRequest().body(list);
    }

    @ExceptionHandler(DiaryException.class)
    private ResponseEntity<GlobalApiResponse> handleDiaryException(DiaryException e) {
        log.error("DiaryException={}", e.getErrorCode().getMessage());
        GlobalApiResponse response = GlobalApiResponse.builder()
                .status(e.getErrorCode().getStatus())
                .message(e.getErrorCode().getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.resolve(e.getErrorCode().getStatus()));
    }

    // 예상하지 못한 오류 응답
    @ExceptionHandler(Exception.class)
    private ResponseEntity<GlobalApiResponse> handleAllException(Exception e) {
        log.error("예기치 못한 exception이 발생했습니다={}", e.getClass());
        log.error("에러 메시지={}", e.getMessage());
        GlobalApiResponse response = GlobalApiResponse.builder()
                .message("예기치 못한 오류가 발생했습니다. 서버에 문의하세요.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseEntity.internalServerError().body(response);
    }
}
