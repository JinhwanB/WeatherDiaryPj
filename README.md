# 스프링부트와 java를 활용한 날씨 정보를 가지는 일기 작성 api 설계 프로젝트

# 사용한 기술

- spring boot 3.2.5
- java 17
- jpa
- mysql
- swagger
- validation
- lombok

# ERD

|                                                        테이블                                                         |
|:------------------------------------------------------------------------------------------------------------------:|
| ![weather_diary](https://github.com/JinhwanB/WeatherDiaryPj/assets/123534245/b6cfcbbc-b2da-432e-aadb-748729f94d48) |

# 목표

- [테스트 코드 작성해보기](https://github.com/JinhwanB/WeatherDiaryPj/tree/main/src/test/java/com/jh/weatherdiarypj)
- [외부 API의 데이터를 활용해보기](https://github.com/JinhwanB/WeatherDiaryPj/blob/fbe6e50b968143e50ebedaa967f420eabf0c5008/src/main/java/com/jh/weatherdiarypj/weather/service/WeatherService.java#L67) - [Open Weather Map api](https://openweathermap.org/)
- JPA 방식으로 MySQL 사용하기

# Problem

- [자식 트랜잭션 메소드의 RuntimeException으로 인한 부모 트랜잭션 롤백](https://github.com/JinhwanB/WeatherDiaryPj/issues/1#issue-2270308074)

# 최종 구현 API 리스트

✅ [POST / create / diary](https://github.com/JinhwanB/WeatherDiaryPj/blob/fbe6e50b968143e50ebedaa967f420eabf0c5008/src/main/java/com/jh/weatherdiarypj/diary/controller/DiaryController.java#L49)

- date parameter 로 받아주세요. (date 형식 : yyyy-MM-dd)
- text parameter 로 일기 글을 받아주세요.
- 외부 API 에서 받아온 날씨 데이터와 함께 DB에 저장해주세요.

✅ [GET / read / diary](https://github.com/JinhwanB/WeatherDiaryPj/blob/fbe6e50b968143e50ebedaa967f420eabf0c5008/src/main/java/com/jh/weatherdiarypj/diary/controller/DiaryController.java#L115)

- date parameter 로 조회할 날짜를 받아주세요.
- 해당 날짜의 일기를 List 형태로 반환해주세요.

✅ [GET / read / diaries](https://github.com/JinhwanB/WeatherDiaryPj/blob/fbe6e50b968143e50ebedaa967f420eabf0c5008/src/main/java/com/jh/weatherdiarypj/diary/controller/DiaryController.java#L136)

- startDate, endDate parameter 로 조회할 날짜 기간의 시작일/종료일을 받아주세요.
- 해당 기간의 일기를 List 형태로 반환해주세요.

✅ [PUT / update / diary](https://github.com/JinhwanB/WeatherDiaryPj/blob/fbe6e50b968143e50ebedaa967f420eabf0c5008/src/main/java/com/jh/weatherdiarypj/diary/controller/DiaryController.java#L73)

- date parameter 로 수정할 날짜를 받아주세요.
- text parameter 로 수정할 새 일기 글을 받아주세요.
- 해당 날짜의 첫번째 일기 글을 새로 받아온 일기글로 수정해주세요.

✅ [DELETE / delete / diary](https://github.com/JinhwanB/WeatherDiaryPj/blob/fbe6e50b968143e50ebedaa967f420eabf0c5008/src/main/java/com/jh/weatherdiarypj/diary/controller/DiaryController.java#L94) -
**soft delete**

- date parameter 로 삭제할 날짜를 받아주세요.
- 해당 날짜의 모든 일기를 지워주세요.

# 프로젝트 완성도 높이기

✅ DB와 관련된 함수들을 트랜잭션 처리
해주세요. - [diary](https://github.com/JinhwanB/WeatherDiaryPj/blob/main/src/main/java/com/jh/weatherdiarypj/diary/service/DiaryService.java), [weather](https://github.com/JinhwanB/WeatherDiaryPj/blob/main/src/main/java/com/jh/weatherdiarypj/weather/service/WeatherService.java)

✅ [매일 새벽 1시에 날씨 데이터를 외부 API 에서 받아다 DB에 저장해두는 로직을 구현해주세요.](https://github.com/JinhwanB/WeatherDiaryPj/blob/fbe6e50b968143e50ebedaa967f420eabf0c5008/src/main/java/com/jh/weatherdiarypj/weather/service/WeatherService.java#L37)

✅ [logback 을 이용하여 프로젝트에 로그를 남겨주세요.](https://github.com/JinhwanB/WeatherDiaryPj/blob/main/src/main/resources/logback-spring.xml)

✅ [ExceptionHandler 을 이용한 예외처리를 해주세요.](https://github.com/JinhwanB/WeatherDiaryPj/blob/main/src/main/java/com/jh/weatherdiarypj/config/GlobalExceptionHandler.java)

✅ swagger 을 이용하여 API documentation 을
해주세요. - [controller](https://github.com/JinhwanB/WeatherDiaryPj/blob/main/src/main/java/com/jh/weatherdiarypj/diary/controller/DiaryController.java), [일기 dto](https://github.com/JinhwanB/WeatherDiaryPj/blob/main/src/main/java/com/jh/weatherdiarypj/diary/dto/DiaryResponseDto.java), [api 응답 dto](https://github.com/JinhwanB/WeatherDiaryPj/blob/main/src/main/java/com/jh/weatherdiarypj/config/GlobalApiResponse.java)

