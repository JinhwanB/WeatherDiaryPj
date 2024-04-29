package com.jh.weatherdiarypj.weather.service;

import com.jh.weatherdiarypj.openapi.WeatherApi;
import com.jh.weatherdiarypj.weather.domain.Weather;
import com.jh.weatherdiarypj.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;

    @Scheduled(cron = "0 0 1 * * *")
    public void saveWeather() throws IOException {
        weatherRepository.save(getWeatherFromApi());
    }

    private Weather getWeatherFromApi() throws IOException {
        WeatherApi weatherApi = new WeatherApi();
        Map<String, Object> weatherInfo = weatherApi.getWeatherInfo();
        double temp = (double) weatherInfo.get("temp");
        String weather = (String) weatherInfo.get("main");
        String icon = (String) weatherInfo.get("icon");

        return Weather.builder()
                .ico(icon)
                .temp(temp)
                .weather(weather)
                .build();
    }
}
