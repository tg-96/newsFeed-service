# newsFeed-service
## 프로젝트 소개
-  MSA 구조로 설계한 뉴스피드 서비스 입니다.
## 프로젝트 시작하기
- git clone
```
git clone https://github.com/tg-96/pre-order-service.git
```
- docker compose
```
docker-compose up -d  
```
## 요구 사항
- springboot: 3.2.2
- java: 17
  
## 기술 스택
- Java
- SpringBoot
- JPA
- MYSQL
- Eureka
- APIGateway
- JWT
- Docker
- AWS S3

## ERD
### UserService
![image](https://github.com/tg-96/newsFeed-service/assets/98454438/e8119127-6c01-425c-88fc-d20252140369)
### FeedService
![image](https://github.com/tg-96/newsFeed-service/assets/98454438/f6870949-2665-4e0d-a47c-43976903a708)
### ActivityService
![image](https://github.com/tg-96/newsFeed-service/assets/98454438/25da1618-5ed7-4dd2-a050-efae14327372)

## 모듈별 API
### UserService
- JWT 토큰 인증
- 로그인
- 회원가입
- 메일 인증
- 프로필 업데이트
- 프로필 조회
- 비밀번호 변경
### ActivityService
- 팔로우
- 포스트 작성
- 댓글 작성
- 내가 팔로우 한 유저들의 활동 조회
- 게시글 좋아요
- 게시글별 좋아요 조회
- 댓글 좋아요
- 댓글별 좋아요 조회
- 게시글별 댓글 조회
### FeedService
- 피드 조회 

  
