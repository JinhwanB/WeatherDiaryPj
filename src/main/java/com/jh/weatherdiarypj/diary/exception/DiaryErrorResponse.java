package com.jh.weatherdiarypj.diary.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DiaryErrorResponse {
    private int status;
    private String message;
}
