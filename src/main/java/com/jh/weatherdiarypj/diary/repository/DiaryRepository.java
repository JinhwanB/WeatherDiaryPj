package com.jh.weatherdiarypj.diary.repository;

import com.jh.weatherdiarypj.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByDateAndDelDate(String date, LocalDateTime delDate);

    List<Diary> findAllByRegDateBetweenAndDelDateIsNull(LocalDateTime startDate, LocalDateTime endDate);
}
