package com.jh.weatherdiarypj.diary.service;

import com.jh.weatherdiarypj.diary.domain.Diary;
import com.jh.weatherdiarypj.diary.exception.DiaryException;
import com.jh.weatherdiarypj.diary.exception.DiaryExceptionCode;
import com.jh.weatherdiarypj.weather.domain.Weather;
import com.jh.weatherdiarypj.weather.repository.WeatherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DiaryServiceTest {
    @Autowired
    private DiaryService diaryService;

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    @DisplayName("일기 작성 성공 테스트 - 날씨 데이터가 없을 경우")
    void createDiaryWithoutWeather() throws IOException {
        Diary diary = diaryService.createDiary("2024-04-30", "test");

        assertThat(diary.getContent()).isEqualTo("test");
    }

    @Test
    @DisplayName("일기 작성 성공 테스트 - 날씨 데이터가 있는 경우")
    void createDiaryWithWeather() throws IOException {
        Weather weather = Weather.builder()
                .ico("ico")
                .regDate("2024-04-30")
                .temp(123.12)
                .weather("weather")
                .build();
        weatherRepository.save(weather);

        Diary diary = diaryService.createDiary("2024-04-30", "test");

        assertThat(diary.getContent()).isEqualTo("test");
    }

    @Test
    @DisplayName("일기 수정 성공 테스트")
    void updateDiary() throws IOException {
        Diary diary = diaryService.createDiary("2024-04-30", "test");
        Diary modified = diaryService.updateDiary("2024-04-30", "good");

        assertThat(modified.getContent()).isEqualTo("good");
    }

    @Test
    @DisplayName("일기 수정 실패 테스트")
    void updateDiaryFail() {
        try {
            Diary modified = diaryService.updateDiary("2024-04-30", "good");
        } catch (DiaryException e) {
            assertThat(e.getMessage()).isEqualTo(DiaryExceptionCode.NOT_FOUNT_DIARY.getMessage());
        }
    }

    @Test
    @DisplayName("일기 삭제 성공 테스트")
    void deleteDiary() throws IOException {
        Diary diary = diaryService.createDiary("2024-04-30", "test");
        diaryService.deleteDiary("2024-04-30");

        List<Diary> diaryList = diaryService.getDiary("2024-04-30");

        assertThat(diaryList).hasSize(0);
    }

    @Test
    @DisplayName("일기 삭제 실패 테스트")
    void deleteDiaryFail() {
        try {
            diaryService.deleteDiary("2024-04-30");
        } catch (DiaryException e) {
            assertThat(e.getMessage()).isEqualTo(DiaryExceptionCode.NOT_FOUNT_DIARY.getMessage());
        }
    }
}