package com.jh.weatherdiarypj.weather.service;

import com.jh.weatherdiarypj.weather.domain.Weather;
import com.jh.weatherdiarypj.weather.exception.WeatherErrorCode;
import com.jh.weatherdiarypj.weather.exception.WeatherException;
import com.jh.weatherdiarypj.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final WeatherRepository weatherRepository;

    @Value("${openapi.key}")
    private String apiKey;

    @Scheduled(cron = "0 0 1 * * *")
    public void saveWeather() throws IOException {
        weatherRepository.save(getWeatherFromApi());
    }

    public Map<String, Object> getWeatherInfo() throws IOException {
        String weatherData = getWeatherString();
        return parseWeather(weatherData);
    }

    public Weather getWeatherFromApi() throws IOException {
        Map<String, Object> weatherInfo = getWeatherInfo();
        double temp = (double) weatherInfo.get("temp");
        String weather = (String) weatherInfo.get("main");
        String icon = (String) weatherInfo.get("icon");
        String date = getDateToString(LocalDateTime.now());

        return Weather.builder()
                .ico(icon)
                .temp(temp)
                .weather(weather)
                .regDate(date)
                .build();
    }

    @Transactional(readOnly = true)
    public Weather getWeather(String date) {
        return weatherRepository.findByRegDate(date).orElseThrow(() -> new WeatherException(WeatherErrorCode.NOT_FOUNT_WEATHER_INFO.getMessage()));
    }

    // api에서 데이터를 받아옴
    private String getWeatherString() throws IOException {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=" + apiKey;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (Exception e) {
            log.error("failed to get response");
            throw e;
        }
    }

    // 받아온 json데이터를 파싱하여 필요한 데이터만 가져옴
    private Map<String, Object> parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);

        } catch (ParseException e) {
            log.error("failed parse apiData = {}", e.getMessage());
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();
        JSONObject mainData = (JSONObject) jsonObject.get("main");
        resultMap.put("temp", mainData.get("temp"));
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));
        return resultMap;
    }

    // localDateTime -> String(yyyy-mm-dd)
    public String getDateToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
