package com.jh.weatherdiarypj.diary.service;

import com.jh.weatherdiarypj.diary.domain.Diary;
import com.jh.weatherdiarypj.diary.exception.DiaryException;
import com.jh.weatherdiarypj.diary.exception.DiaryExceptionCode;
import com.jh.weatherdiarypj.diary.repository.DiaryRepository;
import com.jh.weatherdiarypj.weather.domain.Weather;
import com.jh.weatherdiarypj.weather.exception.WeatherException;
import com.jh.weatherdiarypj.weather.repository.WeatherRepository;
import com.jh.weatherdiarypj.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final WeatherRepository weatherRepository;
    private final WeatherService weatherService;

    /**
     * 일기 작성
     *
     * @param date 작성 날짜(yyyy-mm-dd)
     * @param text 작성할 글
     * @return 작성된 일기
     * @throws IOException
     */
    public Diary createDiary(String date, String text) throws IOException {
        Weather weather;
        try {
            weather = weatherService.getWeather(date);
            log.info("저장된 날씨 불러오기 성공");
        } catch (WeatherException e) {
            log.error("날짜에 해당하는 날씨 정보가 없으므로 새롭게 저장합니다.");

            weather = weatherService.getWeatherFromApi();
            weatherRepository.save(weather);
        }

        Diary diary = Diary.builder()
                .ico(weather.getIco())
                .content(text)
                .temp(weather.getTemp())
                .weather(weather.getWeather())
                .date(date)
                .build();
        return diaryRepository.save(diary);
    }

    /**
     * 일기 수정
     *
     * @param date 수정할 일기의 작성 날짜
     * @param text 수정할 내용
     * @return 수정된 일기
     */
    public Diary updateDiary(String date, String text) {
        List<Diary> diaryList = getDiary(date);
        if (diaryList.isEmpty()) {
            throw new DiaryException(DiaryExceptionCode.NOT_FOUNT_DIARY.getMessage());
        }

        Diary diary = diaryList.get(0);
        Diary modified = diary.toBuilder()
                .content(text)
                .build();
        return diaryRepository.save(modified);
    }

    /**
     * 일기 삭제
     *
     * @param date 삭제할 일기의 날짜
     */
    public void deleteDiary(String date) {
        List<Diary> diaryList = getDiary(date);
        if (diaryList.isEmpty()) {
            throw new DiaryException(DiaryExceptionCode.NOT_FOUNT_DIARY.getMessage());
        }

        for (Diary diary : diaryList) {
            Diary deleted = diary.toBuilder()
                    .delDate(LocalDateTime.now())
                    .build();
            diaryRepository.save(deleted);
        }
    }

    // diary 조회
    @Transactional(readOnly = true)
    public List<Diary> getDiary(String date) {
        return diaryRepository.findAllByDateAndDelDate(date, null);
    }

    public List<Diary> getDiaries(String startDate, String endDate) {

    }

    // string -> localDateTime
    private LocalDateTime stringToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.parse(date, formatter);
    }
}
