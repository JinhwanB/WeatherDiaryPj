package com.jh.weatherdiarypj.weather.exception;

import lombok.Getter;

@Getter
public enum WeatherErrorCode {
    NOT_FOUNT_WEATHER_INFO(404, "날씨 정보를 찾을 수 없습니다.");

    private final int status;
    private final String message;

    WeatherErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
