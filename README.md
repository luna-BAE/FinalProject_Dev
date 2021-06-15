__Spring boot 기반 코워킹스페이스 사이트__
============================

![logo_black](https://user-images.githubusercontent.com/78431040/120638001-8cc2e780-c4aa-11eb-9655-e8f3f346e8fc.png)

###### *모든 내용은 본인 파트에 해당하는 것만 작성하였습니다.*   
   
프로젝트 소개
------------
> 코워킹 스페이스 운영/관리와 멤버 커뮤니티를 위한 웹 사이트 구축
> - 건물과 멤버 관리를 하나의 사이트에서 관리
> - 편리한 예약 시스템과 커뮤니티 제공

담당 역할
-----------
> - 멤버 커뮤니티 & UI   
> - 게시판, 페이징, 댓글   
> - 회의실 예약 시스템   
> - 예약결제 / 결제취소   
> - 스케쥴러(메일 발송)   

구현 화면
-----------
> 메인 화면   
![main](https://user-images.githubusercontent.com/78431040/120637114-71a3a800-c4a9-11eb-8c7b-e324bb1607d0.png)
> 멤버 커뮤니티 인트로   
![intro](https://user-images.githubusercontent.com/78431040/120637122-736d6b80-c4a9-11eb-9806-3e593188a452.png)
> 멤버 커뮤니티 게시판   
![board](https://user-images.githubusercontent.com/78431040/120637124-736d6b80-c4a9-11eb-973b-eb3eca195561.png)
> 멤버 커뮤니티 댓글
![comment](https://user-images.githubusercontent.com/78431040/120637130-749e9880-c4a9-11eb-8ea9-763050a0a0a2.png)
> 회의실 예약
![reservation](https://user-images.githubusercontent.com/78431040/120637125-74060200-c4a9-11eb-939b-d28583065e9e.png)
> 예약 확인 메일   
![remindMail](https://user-images.githubusercontent.com/78431040/120638392-03f87b80-c4ab-11eb-8513-ab0fd9108ed6.png)
> 결제 메시지 기능   
> <img width="30%" src="https://user-images.githubusercontent.com/78431040/120637128-749e9880-c4a9-11eb-8a8e-c4a9f7a9735f.jpg"/>


   
개발환경/기술
--------------------
> **Back-end**
> - JAVA
> - Spring boot
> - MyBatis
> - lombok
> - Maven
  
> **Front-end**
> - JSP
> - HTML
> - JavaScript
> - CSS
> - BootStrap
> - JQuery
> - AJAX

> **DataBase**
> - MariaDB

> **WAS/WEB**
> - Apache Tomcat
> - Apache HTTP Server

> **OPEN API**
> - Java mail api
> - I'mport;

ERD
------------
> ![ERD](https://user-images.githubusercontent.com/78431040/120638828-9ac53800-c4ab-11eb-826e-9736399acd07.png)
   
추후 버전업 계획
--------------------
> - 댓글 기능 보완과 모듈화
> - 권한과 시큐리티 기능 업데이트
> - 예약/결제/취소 코드 리팩토링
