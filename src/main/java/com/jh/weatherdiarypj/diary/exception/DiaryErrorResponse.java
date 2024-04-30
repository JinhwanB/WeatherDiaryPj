package com.jh.weatherdiarypj.diary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DiaryErrorResponse {
    private int status;
    private String message;
}
