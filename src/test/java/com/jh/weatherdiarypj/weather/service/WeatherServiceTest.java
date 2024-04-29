package com.jh.weatherdiarypj.weather.service;

import com.jh.weatherdiarypj.weather.domain.Weather;
import com.jh.weatherdiarypj.weather.exception.WeatherErrorCode;
import com.jh.weatherdiarypj.weather.exception.WeatherException;
import com.jh.weatherdiarypj.weather.repository.WeatherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class WeatherServiceTest {
    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    @DisplayName("날씨 데이터 저장 스케줄링 확인 테스트")
        // 테스트를 위해 5초마다 저장하도록 스케줄 변경 후 실행함
    void scheduleTest() throws InterruptedException {
        Thread.sleep(6000);

        Weather weather = weatherRepository.findById(1L).orElseThrow(() -> new WeatherException(WeatherErrorCode.NOT_FOUNT_WEATHER_INFO.getMessage()));

        assertThat(weather).isNotNull();
    }
}