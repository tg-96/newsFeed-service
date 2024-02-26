# newsFeed-service
## 프로젝트 소개
-  MSA 구조로 설계한 뉴스피드 서비스 입니다.
## 목차
- [프로젝트 시작하기](#프로젝트-시작하기)
- [요구 사항](#요구-사항)
- [기술 스택](#기술-스택)
- [ERD](#erd)
- [모듈별 API](#모듈별-api)
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
![image](https://github.com/tg-96/newsFeed-service/assets/98454438/cdb0d649-abab-4304-a99c-a0001db7d554)


## 모듈별 API
### UserService
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

  
