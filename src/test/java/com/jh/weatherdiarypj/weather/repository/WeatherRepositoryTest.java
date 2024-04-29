package com.jh.weatherdiarypj.weather.repository;

import com.jh.weatherdiarypj.weather.domain.Weather;
import com.jh.weatherdiarypj.weather.exception.WeatherErrorCode;
import com.jh.weatherdiarypj.weather.exception.WeatherException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class WeatherRepositoryTest {
    @Autowired
    private WeatherRepository weatherRepository;

    @BeforeEach
    void before() {
        Weather weather = Weather.builder()
                .weather("weather")
                .temp(345.23)
                .ico("ico")
                .regDate(LocalDateTime.now())
                .build();
        weatherRepository.save(weather);
    }

    @Test
    @DisplayName("날씨 데이터 저장 및 조회")
    void save() {
        Weather weather = weatherRepository.findById(1L).orElseThrow(() -> new WeatherException(WeatherErrorCode.NOT_FOUNT_WEATHER_INFO.getMessage()));

        assertThat(weather.getWeather()).isEqualTo("weather");
    }
}