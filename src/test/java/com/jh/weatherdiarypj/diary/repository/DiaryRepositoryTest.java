package com.jh.weatherdiarypj.diary.repository;

import com.jh.weatherdiarypj.diary.domain.Diary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DiaryRepositoryTest {
    @Autowired
    private DiaryRepository diaryRepository;

    // startDate ~ endDate까지 조회 테스트 시 주석 필요
    @BeforeEach
    void before() {
        Diary diary = Diary.builder()
                .date("1234-12-12")
                .ico("ico")
                .weather("weather")
                .temp(123.12)
                .content("test")
                .build();
        diaryRepository.save(diary);
    }

    @Test
    @DisplayName("일기 저장 및 조회")
    void diarySaveAndRead() {
        List<Diary> diarys = diaryRepository.findAllByDateAndDelDate("1234-12-12", null);

        assertThat(diarys).hasSize(1);
        assertThat(diarys.get(0).getContent()).isEqualTo("test");
    }

    @Test
    @DisplayName("일기 조회 - startDate ~ endDate까지 조회")
        // diary 엔티티의 @createdDate 어노테이션 제거 후 진행
    void diaryReadStartEnd() {
        Diary diary1 = Diary.builder()
                .date("1234-12-12")
                .ico("ico")
                .weather("weather")
                .temp(123.12)
                .content("test")
                .regDate(LocalDateTime.now())
                .build();
        Diary diary2 = diary1.toBuilder()
                .regDate(diary1.getRegDate().plusDays(1))
                .build();
        Diary diary3 = diary2.toBuilder()
                .regDate(diary2.getRegDate().plusDays(1))
                .build();
        diaryRepository.save(diary1);
        diaryRepository.save(diary2);
        diaryRepository.save(diary3);

        List<Diary> diaryList = diaryRepository.findAllByRegDateBetweenAndDelDateIsNull(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        assertThat(diaryList).hasSize(2);
    }

    @Test
    @DisplayName("일기 수정")
    void diaryUpdate() {
        List<Diary> diarys = diaryRepository.findAllByDateAndDelDate("1234-12-12", null);
        Diary modified = diarys.get(0).toBuilder()
                .content("good")
                .build();
        diaryRepository.save(modified);

        List<Diary> diaryList = diaryRepository.findAllByDateAndDelDate("1234-12-12", null);

        assertThat(diaryList).hasSize(1);
        assertThat(diaryList.get(0).getContent()).isEqualTo("good");
    }

    @Test
    @DisplayName("일기 삭제")
    void diaryDelete() {
        List<Diary> diaryList = diaryRepository.findAllByDateAndDelDate("1234-12-12", null);
        for (Diary diary : diaryList) {
            Diary deleted = diary.toBuilder()
                    .delDate(LocalDateTime.now())
                    .build();
            diaryRepository.save(deleted);
        }

        List<Diary> diaries = diaryRepository.findAllByDateAndDelDate("1234-12-12", null);

        assertThat(diaries).hasSize(0);
    }
}