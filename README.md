### Search API 구현   

---
#### | Tech. Spec
* Spring Boot 2.4.3
* H2 Database (In-Memory)
* MAC OS Big Sur 11.2.2
* Gradle 6.8.3
* JDK 1.8.0_241

#### | 구성도
<img width="600" alt="스크린샷 2021-03-24 오후 6 04 35" src="https://user-images.githubusercontent.com/20740884/112283403-7559c800-8ccb-11eb-94b7-3a0f811138bc.png">

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
    * Spring Boot Convention Library 인 ```spring-boot-starter-security``` 사용하여 인증(Authenticatoin) 구현
    * jjwt 를 통해 발급 토큰 생성 및 검증
* ORM
    * ```spring-boot-starter-data-jpa``` 명세 사용, 구현체는 ```Hibernate``` 사용
* Lombok
    * Annotaion Proccessor 를 통해 반복되는 소스 경량화     
      (```@Getter```, ```@Setter``` 등)
* Web
    * ```spring-boot-starter-web``` Convention 을 통해 Web 환경 구성을 위한 라이브러리 적용
* 외부 연동 안정성
    * ```spring-retry``` 를 통한 API 재 처리, 복구 구조 적용

#### | API 명세
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
{
    "info": {
        "userName": "spring"
    },
    "status": 200,
    "message": "success"
}
````
* Biz. Exception      

Case 1) 가입된 회원이 존재하는 경우
```json
{
    "info": {
        "errorStatus": 1000,
        "errorCode": "D001",
        "errorMessage": "이미 가입되었습니다."
    },
    "status": 200,
    "message": "success"
}
```    

Case 2) 회원 가입 시 사용자 이름을 누락한 경우
```json
{
  "info": {
    "errorStatus": 4000,
    "errorCode": "D004",
    "errorMessage": "사용자 이름은 필수값 입니다."
  },
  "status": 400,
  "message": "Bad Request"
}
```    

Case 3) 회원 가입 시 비밀번호를 누락한 경우
```json
{
    "info": {
        "errorStatus": 4000,
        "errorCode": "D004",
        "errorMessage": "비밀번호는 필수값 입니다."
    },
    "status": 400,
    "message": "Bad Request"
}
```

(2) 로그인
* URI: POST http://localhost:8080/api/v1/login
* Request
```json
{
    "userName": "spring",
    "password": "spring"
}
```     

* Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzcHJpbmciLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjE2NTkwMzgzLCJleHAiOjE2MTY1OTIxODN9.s9-011JlIRR36lbC6Wnp0pfeb7uPlZTr4g6OJVA-UQ4"
}
```    

* Biz. Exception   

Case 1) 사용자를 잘못 입력한 경우
```json
{
    "info": {
        "errorStatus": 2000,
        "errorCode": "D002",
        "errorMessage": "사용자를 찾을 수 없습니다."
    },
    "status": 200,
    "message": "success"
}
```   

Case 2) 비밀번호를 잘못 입력한 경우
```json
{
    "info": {
        "errorStatus": 3000,
        "errorCode": "D003",
        "errorMessage": "잘못된 패스워드 입니다."
    },
    "status": 200,
    "message": "success"
}
```    

(3) 장소 검색    
* URI: POST http://localhost:8080/api/v1/user/search   
* Authentication: token_value
* Header: X-USER-NAME
* Request
```json
{
    "keyWord": "자전거",
    "page": 1,
    "size": 10
}
```    

* Response (최대 10개 결과 리스트 제공)
```json
{
  "places": [
    {
      "placeName": "광명스피돔 경륜본장"
    },
    {
      "placeName": "런바이크"
    },
    {
      "placeName": "매너바이크"
    },
    {
      "placeName": "바이크마트 대전점"
    },
    {
      "placeName": "스페셜라이즈드 조세핀"
    },
    {
      "placeName": "스페셜라이즈드 코리아"
    },
    {
      "placeName": "아라뱃길자전거대여소"
    },
    {
      "placeName": "위너스 스포츠클럽 골프연습장옆 대여소 금천구점"
    },
    {
      "placeName": "트라이엄프 코리아 플래그십스토어"
    },
    {
      "placeName": "하이텐"
    }
  ],
  "page": 1,
  "size": 10,
  "totalPage": 1,
  "totalCount": 10
}
```    

* Biz. Exception

Case 1) 검색 키워드 체크 (공백, 스페이스 허용 불가)
```json
{
    "info": {
        "errorStatus": 4000,
        "errorCode": "D004",
        "errorMessage": "검색 키워드는 필수값입니다."
    },
    "status": 400,
    "message": "Bad Request"
}
```    

* Authentication Exception
```json
{
    "timestamp": "2021-03-24T13:28:24.858+00:00",
    "status": 403,
    "error": "Forbidden",
    "message": "",
    "path": "/api/v1/user/search"
}
```   

(4) 내 검색 히스토리
* URI: GET http://localhost:8080/api/v1/user/search/history
* Authentication: token_value
* Request
```json
{
    "userName": "spring"
}
```    

* Response (최신 검색순으로 정렬 후 제공)
```json
{
    "history": [
        {
            "keyWord": "G",
            "searchDate": "2021-03-24T22:32:50.479"
        },
        {
            "keyWord": "뷝",
            "searchDate": "2021-03-24T22:32:43.578"
        },
        {
            "keyWord": "벨",
            "searchDate": "2021-03-24T22:32:40.444"
        },
        {
            "keyWord": "자전거",
            "searchDate": "2021-03-24T22:32:37.976"
        }
    ],
    "totalCount": 4,
    "status": 200,
    "message": "success"
}
```   

* Biz. Exception

Case 1) 사용자 이름 체크 (공백, 스페이스 허용 불가)
```json
{
    "info": {
        "errorStatus": 4000,
        "errorCode": "D004",
        "errorMessage": "사용자는 필수값입니다."
    },
    "status": 400,
    "message": "Bad Request"
}
```     

(5) 인기 키워드 목록 (검색 횟수가 많은 순 정렬, 최대 10개 제공)
* URI: GET http://localhost:8080/api/v1/user/search/ranking
* Authentication: token_value
* Request: 없음
* Response
```json
{
    "ranking": [
        {
            "keyWord": "자전거",
            "rankingCount": 3
        },
        {
            "keyWord": "G",
            "rankingCount": 2
        },
        {
            "keyWord": "벨",
            "rankingCount": 1
        },
        {
            "keyWord": "뷝",
            "rankingCount": 1
        }
    ],
    "totalCount": 4,
    "status": 200,
    "message": "success"
}
```        

#### | 설계 방향성 
* 외부 API 안정성 고려 방안
  * RestTemplate 과 Retry, Recover 를 통한 재 처리 구조
* 키워드 별 검색 횟수의 정확성 (동시성 이슈 처리)
  * 기본적으로 Transaction(Read)를 하고, DML Query 인 경우 Transactional 경계를 지정하여 처리
* JPA 성능 향상   
  * Open-Session-In-View: false
    * Spring Boot 내에서 OSIV 의 Default 설정인 true 를 false 변경
    * 커넥션 자원을 트랜젝션 범위에서만 사용하여 자원 최적화
    * 지연로딩 처리를 트랜젝션 범위 내에서만 처리하도록 구조화    
  
     
#### | 외부 API를 사용하는 검색 End-Point 부하 발생 시 극복 방안   
* Hit & Cache 를 통한 성능과 안정성 확보
  * 유입되는 검색어 기준으로 조회된 정보를 Cache 에 저장
  * 동일 검색이 유입된 경우 외부 API 연동 하지 않고 Cache 저장 정보 제공
  * Expire 정책으로 Cache 저장소 용량 확보
  * Cache의 키 값은 여러 Key 생성 알고리즘(md5, sha256 등)을 통해 key 충돌 방지


![캡처](https://user-images.githubusercontent.com/20740884/112323614-4d7f5a00-8cf5-11eb-8b50-3d8a5c6420e7.JPG)

---   

