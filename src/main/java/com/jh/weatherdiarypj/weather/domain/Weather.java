package com.jh.weatherdiarypj.weather.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter // 테스트용
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@ToString
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String weather;

    @Column(nullable = false)
    private String ico;

    @Column(nullable = false)
    private double temp;

    @Column(nullable = false)
    private String regDate;
}
