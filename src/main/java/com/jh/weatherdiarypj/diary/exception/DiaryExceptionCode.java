package com.jh.weatherdiarypj.diary.exception;

import lombok.Getter;

@Getter
public enum DiaryExceptionCode {
    NOT_FOUNT_DIARY(404, "해당 날짜의 일기가 없습니다.");
    
    private final int status;
    private final String message;

    DiaryExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
