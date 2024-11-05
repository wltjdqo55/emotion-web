<div align="center">
<h2>날씨에 따른 맛집 추천 프로젝트 🎮</h2>
날씨 정보 ( 기온, 기상 ) 의 변화에 따라 먹고 싶은 음식이 달라 질 수 있습니다.<br><br>
"비 오는데 파전에 막걸리 먹으러 가자!"<br>
"오늘 너무 더운데 시원한게 먹고 싶다"<br><br>
와 같이 날씨에 따라 먹고 싶은 음식이 생각납니다. 그래서 저는 날씨 API와 네이버 검색 API, 카카오 API 를 활용해서 맛집을 추천해 주는 프로젝트를 만들어 보았습니다.
</div>

&nbsp;

## **📗 목차**

- ### 포트폴리오 개요
- ### 사용 API
- ### 개발 환경
- ### 구현 순서
- ### 주요 기능
- ### 음식 추천 Flow
- ### 주요 화면

&nbsp;

## **🔲 포트폴리오 개요**

> **기획 및 제작:** 지성배
>
> **분류:** 개인 프로젝트
>
> **개발기간:** 2024.10.21 ~ 2024.11.06
>
> **문의:** wltjdqo55@gmail.com
> 
> **블로그:** https://blog.naver.com/seongbae365/223646428763 ( 프로젝트에 대한 자세한 소개글)

&nbsp;

## **🔲 사용 API**

> **1. 기상청 단기예보 조회서비스 ( 공공 데이터 포털 )**
> 
> **2. 카카오 로그인, 카카오 메시지 템플릿, 카카오 맵 API**
> 
> **3. Geolocation API ( 현재 위치 좌표를 구하는 API )**
> 
> **4. Geocoder API ( 좌표 => 주소로 변환해주는 API)**
> 
> **5. 네이버 검색 API**

&nbsp;

## **🔲 사용 기술**

> **형상관리:** <img src="https://img.shields.io/badge/GITHUB-181717?style=flat-square&logo=github&logoColor=white"/>
>
> **프론트엔드:** <img src="https://img.shields.io/badge/HTML-E34F26?style=flat-square&logo=HTML5&logoColor=white"/> <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=CSS3&logoColor=white"/> <img src="https://img.shields.io/badge/Vue.js-4FC08D?style=flat-square&logo=Vue.js&logoColor=white"/> <img src="https://img.shields.io/badge/Axios-5A29E4?style=flat-square&logo=Axios&logoColor=white"/>
>
> **백엔드:** <img src="https://img.shields.io/badge/JAVA-1572B6?style=flat-square&logo=JAVA&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/INTELLIJ IDEA-000000?style=flat-square&logo=intellijidea&logoColor=white"/> <img src="https://img.shields.io/badge/POSTMAN-FF6C37?style=flat-square&logo=postman&logoColor=white"/> <img src="https://img.shields.io/badge/DBeaver-382923?style=flat-square&logo=dbeaver&logoColor=white"/> <img src="https://img.shields.io/badge/GRADLE-02303A?style=flat-square&logo=gradle&logoColor=white"/>
>
> **데이터베이스:** <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=PostgreSQL&logoColor=white"/>

&nbsp;

## **🔲 구현 순서**

> **1. 공공데이터포털 API 신청 및 인증키 얻기**
>
> **2. 현재 좌표 구하기 ( GeoLocation Open API 활용 )**
>
> **3. 현재 위치의 날씨 정보 가져오기**
>
> **4. 카카오 로그인 API 신청 및 인증키 얻기**
>
> **5. 카카오 맵 구현**
> 
> **6. 날씨에 따른 음식 데이터 구하기**
>
> **7. 음식점 검색하기 ( 네이버 지역검색 Open API )**
>
> **8. 카카오 맵에 음식점 위치 표시하기**
>
> **9. 카카오톡 보내기**

&nbsp;

## **🔲 주요 기능**

> **1. HTTPS 서버 설정**
>
> **2. REST API 설계 및 구현**
>
> **3. 카카오 로그인 기능**
>
> **4. 카카오 맵 구현 ( 좌표를 통해 사용자 위치, 음식점 위치 표시 )**
>
> **5. 날씨 정보 조회**
>
> **6. 네이버 검색 기능**
>
> **7. 카카오톡 메세지 구현 ( 나에게 보내기 )**
>
> **8. 날씨에 따른 음식 추천 받기**

&nbsp;

## **🔲 음식 추천 Flow**

&nbsp;

<img src="https://github.com/user-attachments/assets/6bdd617e-c9be-403a-8b4b-c3e4b0ba8ada" alt="메인화면" style="display:inline-block; "/>

#### 1. 메인화면 진입시 사용자 위치정보 요청 ( 위치정보 동의시 카카오맵 OPEN )
#### 2. 맛집 추천 받기 클릭시 로그인 체크
-  카카오 로그인이 되어 있으면 진행, 되어 있지 않으면 카카오 로그인 화면 이동
#### 3. 날씨 정보 기반 음식 추천 절차
- **3-1. 현재 기온과 기상 상태 조회**  
  날씨 API를 사용하여 현재 기온 및 기상 상태 정보를 가져옵니다.
- **3-2. 날씨에 맞는 음식 선정**  
  기온과 날씨에 적합한 음식을 추천 리스트에서 선정합니다.
- **3-3. 음식점 정보 가져오기**  
  네이버 검색 API로 추천 음식과 관련된 음식점 정보를 가져옵니다.
- **3-4. 음식점 위치 표시**  
  음식점의 좌표를 통해 카카오맵에 해당 위치를 표시합니다.
- **3-5. 카카오톡 보내기**  
  카카오톡으로 날씨정보 및 추천된 맛집 정보를 전송합니다.

&nbsp;

## **🔲 주요 화면**

&nbsp;

<img src="https://github.com/user-attachments/assets/5552af47-a4ff-4bfa-8525-19118d3040b6" alt="오늘의 날씨 정보" style="display:inline-block; "/>
<img src="https://github.com/user-attachments/assets/a5a0a171-fb45-4eb1-8bdb-bb1d1fceceb9" alt="현재 날씨에 따른 음식 추천" style="display:inline-block; "/>

- 카카오톡으로 전송된 화면 ( 카카오톡 메세지 API : 나에게 보내기)
- 왼쪽 - 오늘의 날씨 정보 ( 피드 템플릿 )
- 오른쪽 - 현재 날씨에 따른 음식 추천 ( 리스트 템플릿 )
- 날씨의 따른 음식 추천 조건은 다음과 같습니다. ( 기상청에서 발표한 더운날 추운날 온도지수 참고했음 )
- 
  | 기상 조건                 | 온도 33 ℃ 이상          | 온도 5 ℃ 미만        | 추천 결과                |
  |--------------------------|-------------------------|-----------------------|--------------------------|
  | 비/눈/소나기 여부 (O)    | X                       | X                     | 비 오는 날 음식 3개 추천 |
  |                          | O                       | X                     | 비 오는 날 음식 3개 추천 |
  |                          | X                       | O                     | 비 오는 날 음식 3개 추천 |
  | 없음 (X)                 | O                       | X                     | 더운 날 음식 3개 추천    |
  |                          | X                       | O                     | 추운 날 음식 3개 추천    |
  |                          | X                       | X                     | 리뷰 순 맛집 3개 추천    |

&nbsp;

&nbsp;

<img src="https://github.com/user-attachments/assets/c3e8f105-e4e2-45de-9bba-9858968bce4b" alt="카카오톡 동의하고 계속하기" style="display:inline-block; "/>

- 카카오 로그인 진행시 사용자의 정보를 수집하는 과정
- 동의하고 계속하기 클릭시 인가코드를 전달받고, 인가코드를 통해 토큰 발급
- 응답코드로 받은 ID값이 DB에 존재하면 아이디 정보 불러오고, 없으면 DB에 등록 후 정보 가져옴
- 카카오 메세지 API를 사용하기 위한 필수 조건

&nbsp;

&nbsp;

<img src="https://github.com/user-attachments/assets/e3142280-0052-4831-b2a4-c68413daf765" alt="카카오 맵" style="display:inline-block; "/><br>
<img src="https://github.com/user-attachments/assets/d40a0882-9be6-4183-a461-8d2239e88222" alt="검색음식점 정보" style="display:inline-block; "/>

- 카카오 맵 구현 ( 현재 위치를 기준으로 지도 중심 위치 잡음 )
- 카카오 맵에 현재 위치, 추천받은 음식점의 위치 표시 - 아이콘 마우스를 올리면 음식점 제목 표시
- 아래 사진은 네이버 검색으로 가져온 음식점 정보 ( 네이버 지역 검색 API )
- | 기상 조건                | 검색어 형식                      |
  |----------------------|----------------------------------|
  | 비/눈/소나기/더운날/추운날 | 지역명 + 음식명 + 맛집          |
  | 평범한 날                | 지역명 + 맛집                   |
