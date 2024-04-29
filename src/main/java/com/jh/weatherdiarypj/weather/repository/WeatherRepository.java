package com.jh.weatherdiarypj.weather.repository;

import com.jh.weatherdiarypj.weather.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
