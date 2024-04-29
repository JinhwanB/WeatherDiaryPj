package com.jh.weatherdiarypj.diary.controller;

import com.jh.weatherdiarypj.diary.domain.Diary;
import com.jh.weatherdiarypj.diary.service.DiaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class DiaryControllerTest {
    @MockBean
    private DiaryService diaryService;

    @Autowired
    private MockMvc mockMvc;

    Diary diary;

    @BeforeEach
    void before() {
        diary = Diary.builder()
                .date("1234-12-12")
                .ico("ico")
                .temp(123.12)
                .weather("weather")
                .content("test")
                .build();
    }

    @Test
    @DisplayName("일기 작성 컨트롤러 테스트")
    void createDiaryController() throws Exception {
        given(diaryService.createDiary(any(), any())).willReturn(diary);

        mockMvc.perform(post("/diaries/diary?date=1234-12-12&text=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("test"))
                .andExpect(jsonPath("$.weather").value("weather"))
                .andExpect(jsonPath("$.ico").value("ico"))
                .andExpect(jsonPath("$.temp").value(123.12))
                .andExpect(jsonPath("$.writeDate").value("1234-12-12"));
    }

    @Test
    @DisplayName("일기 수정 컨트롤러 테스트")
    void updateDiaryController() throws Exception {
        given(diaryService.updateDiary(any(), any())).willReturn(diary);

        mockMvc.perform(put("/diaries/diary?date=1234-12-12&text=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("test"))
                .andExpect(jsonPath("$.weather").value("weather"))
                .andExpect(jsonPath("$.ico").value("ico"))
                .andExpect(jsonPath("$.temp").value(123.12))
                .andExpect(jsonPath("$.writeDate").value("1234-12-12"));
    }
}