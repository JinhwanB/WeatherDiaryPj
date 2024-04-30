package com.jh.weatherdiarypj.diary.exception;

import lombok.Getter;

@Getter
public class DiaryException extends RuntimeException {
    private DiaryExceptionCode errorCode;
    private String message;

    public DiaryException(DiaryExceptionCode diaryExceptionCode, String message) {
        super(message);
        this.errorCode = diaryExceptionCode;
    }
}
