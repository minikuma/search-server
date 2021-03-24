### Search API 구현   

---
#### | Tech. Spec
* Spring Boot 2.4.3
* H2 Database (In-Memory)
* MAC OS Big Sur 11.2.2
* JDK 1.8.0_241

#### | 구성도
<img width="500" alt="스크린샷 2021-03-24 오후 6 04 35" src="https://user-images.githubusercontent.com/20740884/112283403-7559c800-8ccb-11eb-94b7-3a0f811138bc.png">

#### | Feature
* 회원가입, 로그인
  * JWT 발급을 통한 로그인 기능
* 장소 검색 (Open API)
  * 외부 Open API 연동 및 검색 데이터 Handling (우선순위)
* 내 검색 히스토리
  * 현재 사용자 기준 검색 히스토리 조회 (최근날짜 순)
* 인기 키워드 목록
  * 현재 검색어 중 상위 10개 검색 키워드 조회 
    
#### | Library
* Security
    * Spring Boot Convention Library 인 spring-boot-starter-security 사용하여 인증(Authenticatoin) 구현
    * jjwt 를 통해 발급 토큰 생성 및 검증
* ORM
    * spring-boot-starter-data-jpa 명세 사용, 구현체는 Hibernate 사용
* Lombok
    * Annotaion Proccessor 를 통해 반복되는 소스 경량화     
      (```@Getter```, ```@Setter``` 등)
* Web
    * spring-boot-starter-web Convention 을 통해 Web 환경 구성을 위한 라이브러리 적용

#### API 명세
(1) 회원 가입   
* URI: POST http://localhost:8080/api/v1/signup
* Request
````json
{
  "userName": "spring",
  "password": "spring"
}
````
* Response
````json

````