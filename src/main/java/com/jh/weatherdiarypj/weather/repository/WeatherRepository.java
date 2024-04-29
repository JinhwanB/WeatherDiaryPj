package com.jh.weatherdiarypj.weather.repository;

import com.jh.weatherdiarypj.weather.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findByRegDate(String date);
}
