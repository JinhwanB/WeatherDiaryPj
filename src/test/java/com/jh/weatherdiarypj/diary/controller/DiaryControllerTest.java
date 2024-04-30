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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("성공"))
                .andExpect(jsonPath("$.result[0].content").value("test"))
                .andExpect(jsonPath("$.result[0].weather").value("weather"))
                .andExpect(jsonPath("$.result[0].ico").value("ico"))
                .andExpect(jsonPath("$.result[0].temp").value(123.12))
                .andExpect(jsonPath("$.result[0].writeDate").value("1234-12-12"));
    }

    @Test
    @DisplayName("일기 수정 컨트롤러 테스트")
    void updateDiaryController() throws Exception {
        given(diaryService.updateDiary(any(), any())).willReturn(diary);

        mockMvc.perform(put("/diaries/diary?date=1234-12-12&text=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("성공"))
                .andExpect(jsonPath("$.result[0].content").value("test"))
                .andExpect(jsonPath("$.result[0].weather").value("weather"))
                .andExpect(jsonPath("$.result[0].ico").value("ico"))
                .andExpect(jsonPath("$.result[0].temp").value(123.12))
                .andExpect(jsonPath("$.result[0].writeDate").value("1234-12-12"));
    }

    @Test
    @DisplayName("일기 삭제 컨트롤러 테스트")
    void deleteDiaryController() throws Exception {
        mockMvc.perform(delete("/diaries/diary?date=1234-12-12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("성공"))
                .andExpect(jsonPath("$.result[0]").doesNotExist());
    }

    @Test
    @DisplayName("일기 조회 컨트롤러 테스트")
    void readDiaryController() throws Exception {
        List<Diary> list = new ArrayList<>(List.of(diary));
        given(diaryService.getDiary(any())).willReturn(list);

        mockMvc.perform(get("/diaries/diary?date=1234-12-12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("성공"))
                .andExpect(jsonPath("$.result[0].content").value("test"))
                .andExpect(jsonPath("$.result[0].weather").value("weather"))
                .andExpect(jsonPath("$.result[0].ico").value("ico"))
                .andExpect(jsonPath("$.result[0].temp").value(123.12))
                .andExpect(jsonPath("$.result[0].writeDate").value("1234-12-12"));
    }
}