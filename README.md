# Java 렌트카 예약 관리 시스템

**Java Swing 기반 렌트카 예약 및 관리 시스템**

Java와 Oracle 데이터베이스를 활용하여 구현한 렌트카 예약 관리 프로그램입니다. 회원 관리, 차량 관리, 예약 관리 기능을 제공하는 GUI 기반 데스크톱 애플리케이션으로, MVC 패턴과 DAO 패턴을 적용한 체계적인 시스템입니다.

## 기술 스택

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-007396?style=for-the-badge&logo=java&logoColor=white)
![Eclipse](https://img.shields.io/badge/Eclipse-2C2255?style=for-the-badge&logo=eclipse&logoColor=white)

### 개발 환경
- **Language**: Java 8+
- **GUI Framework**: Java Swing
- **Database**: Oracle Database (XE)
- **IDE**: Eclipse

### 외부 라이브러리
- **ojdbc8.jar**: Oracle Database 연결을 위한 JDBC 드라이버
- **jcalendar-1.4.jar**: 달력 UI 컴포넌트 제공 라이브러리

### 아키텍처 패턴
- **MVC Pattern**: Model-View-Controller 구조
- **DAO Pattern**: Data Access Object를 통한 데이터 계층 분리
- **Singleton Pattern**: 데이터베이스 연결 관리

## 시스템 아키텍처

```
    사용자 인터페이스 (Swing GUI)
           ↓
    컨트롤러 계층 (Controller)
           ↓
    비즈니스 로직 계층 (Service)
           ↓
    데이터 액세스 계층 (DAO)
           ↓
    데이터베이스 (Oracle DB)
```

3계층 아키텍처로 설계되어 각 계층의 책임이 명확히 분리되었습니다.
Swing을 통한 직관적인 GUI와 Oracle 데이터베이스를 통한 안정적인 데이터 관리를 제공합니다.

## 핵심 기능

### 회원 관리 시스템
- **회원 등록**: 신규 회원 정보 등록
  - 아이디, 비밀번호, 이름, 주소, 전화번호 관리
  - 중복 아이디 검증
  - 필수 입력 항목 검증
- **회원 조회**: 등록된 회원 정보 검색 및 조회
  - 이름 기반 검색 기능
  - 테이블 형태의 회원 목록 표시
- **회원 정보 수정/삭제**: 기존 회원 정보 관리
  - 실시간 데이터 수정
  - 안전한 삭제 기능

### 차량 관리 시스템
- **차량 등록**: 렌트카 정보 등록 및 관리
  - 차량번호, 차량명, 색상, 배기량, 제조사 정보
  - 차량 상태 관리
- **차량 조회**: 등록된 차량 검색 및 조회
  - 차량번호 기반 검색
  - 전체 차량 목록 조회
- **차량 수정/삭제**: 차량 정보 업데이트 및 제거

### 예약 관리 시스템
- **예약 등록**: 렌트카 예약 생성
  - 예약번호 자동 생성
  - 차량 선택 및 일정 설정
  - 예약자 정보 연동
- **예약 조회**: 예약 현황 확인
  - 예약번호 기반 검색
  - 예약 상세 정보 표시
- **예약 수정/취소**: 예약 정보 변경 및 취소 처리

### 추가 기능
- **로그인 시스템**: 사용자 인증 및 권한 관리
- **달력 기능**: JCalendar 라이브러리를 활용한 날짜 선택 및 메모 기능
- **환경설정**: 사용자 설정 저장 (이미지 설정 등)
- **실시간 시계**: 현재 시간 표시

## 데이터 설계

시스템은 Oracle 데이터베이스를 기반으로 설계되었으며, 다음과 같은 주요 엔티티들로 구성됩니다:

- **회원 정보**: 사용자 계정 및 개인정보 관리
- **차량 정보**: 렌트카 기본 정보 및 상태 관리  
- **예약 정보**: 렌트카 예약 내역 및 일정 관리
- **메모 정보**: 달력 기능을 위한 사용자 메모 저장

## 프로젝트 구조

```
Java_rental-car_project/
├── src/com/oracle/rent/
│   ├── mainmain/                       # 메인 패키지
│   │   ├── main/
│   │   │   └── RentMainWindow.java     # 메인 윈도우
│   │   ├── member/                     # 회원 관리
│   │   │   ├── controller/
│   │   │   │   ├── MemberController.java
│   │   │   │   └── MemberControllerImpl.java
│   │   │   ├── dao/
│   │   │   │   ├── MemberDAO.java
│   │   │   │   └── MemberDAOImpl.java
│   │   │   ├── vo/
│   │   │   │   └── MemberVO.java
│   │   │   ├── window/
│   │   │   │   ├── SearchMemDialog.java
│   │   │   │   └── RegMemDialog.java
│   │   │   ├── login/
│   │   │   │   └── LoginMember.java
│   │   │   └── registration/
│   │   │       └── RegisterMember.java
│   │   ├── car/                        # 차량 관리
│   │   │   ├── controller/
│   │   │   ├── dao/
│   │   │   ├── vo/
│   │   │   └── window/
│   │   ├── res/                        # 예약 관리
│   │   │   ├── controller/
│   │   │   ├── dao/
│   │   │   ├── vo/
│   │   │   └── window/
│   │   ├── calendar/
│   │   │   └── CalendarDialog.java     # 달력 기능
│   │   └── common/                     # 공통 클래스
│   │       └── base/
│   │           ├── AbstractBaseDAO.java
│   │           ├── AbstractBaseController.java
│   │           └── AbstractBaseWindow.java
│   ├── sub1/                           # 콘솔 버전
│   └── sub2/                           # Swing 버전
├── lib/                                # 외부 라이브러리
│   ├── ojdbc8.jar                      # Oracle JDBC Driver
│   └── jcalendar-1.4.jar               # 달력 UI 컴포넌트
└── README.md
```

### 아키텍처 설계 원칙

**MVC 패턴 적용**
각 기능별로 Model-View-Controller를 분리하여 유지보수성과 확장성을 확보했습니다.

- **Model**: VO(Value Object) 클래스로 데이터 표현
- **View**: Swing GUI 컴포넌트로 사용자 인터페이스 구성
- **Controller**: 비즈니스 로직과 데이터 처리 담당

**DAO 패턴 적용**
- 데이터 액세스 로직을 별도 계층으로 분리
- 데이터베이스 의존성 최소화
- 인터페이스를 통한 구현체 분리

## 설치 및 실행

### 사전 요구사항
- **JDK**: Java 8 이상
- **Oracle Database**: Oracle Database XE 또는 Standard Edition
- **외부 라이브러리**: 
  - ojdbc8.jar (Oracle JDBC Driver)
  - jcalendar-1.4.jar (Calendar UI Component)
- **IDE**: Eclipse 또는 IntelliJ IDEA

### 데이터베이스 설정

1. **Oracle Database 설치 및 설정**
   ```sql
   -- 사용자 생성
   CREATE USER C##user1 IDENTIFIED BY 1234;
   GRANT CONNECT, RESOURCE TO C##user1;
   ```

2. **데이터베이스 연결 정보 수정**
   ```java
   // AbstractBaseDAO.java에서 Oracle 연결 정보 설정
   protected static final String driver = "oracle.jdbc.driver.OracleDriver";
   protected static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
   protected static final String user = "C##user1";
   protected static final String pwd = "1234";
   ```

### 프로젝트 설정

1. **저장소 복제**
   ```bash
   git clone [repository-url]
   cd Java_rental-car_project
   ```

2. **Eclipse에서 프로젝트 import**
   ```
   File → Import → Existing Projects into Workspace
   프로젝트 폴더 선택
   ```

3. **라이브러리 설정**
   ```
   프로젝트 우클릭 → Properties → Java Build Path → Libraries
   Add External JARs를 클릭하여 lib 폴더의 다음 파일들 추가:
   - lib/ojdbc8.jar (Oracle Database JDBC Driver)
   - lib/jcalendar-1.4.jar (달력 UI 컴포넌트)
   
   ※ 라이브러리 파일들은 프로젝트의 lib 폴더에 포함되어 있습니다.
   ```

4. **실행**
   ```
   RentMainWindow.java → Run As → Java Application
   ```

## 사용법

### 기본 사용 흐름

1. **로그인**: 등록된 계정으로 로그인 또는 회원가입
2. **메인 메뉴**: 상단 메뉴바에서 원하는 기능 선택
3. **데이터 관리**: 각 기능별 CRUD 작업 수행
4. **결과 확인**: 테이블 형태로 데이터 조회 및 확인

### 주요 기능별 사용법

**회원 관리**
- 회원관리 → 회원등록: 새로운 회원 정보 입력
- 회원관리 → 회원조회: 등록된 회원 검색 및 조회
- 테이블에서 행 선택 후 수정/삭제 버튼 클릭

**차량 관리**
- 차량관리 → 차량등록: 새로운 차량 정보 입력
- 차량관리 → 차량조회: 등록된 차량 검색 및 조회

**예약 관리**
- 예약관리 → 예약등록: 새로운 예약 생성
- 차량조회 화면에서 "렌터카 예약하기" 버튼으로 직접 예약 가능

## 주요 특징

### 기술적 특징
- **MVC 아키텍처**: 체계적인 코드 구조와 유지보수성
- **DAO 패턴**: 데이터 액세스 로직 분리
- **Oracle JDBC 연동**: ojdbc8 드라이버를 통한 안정적인 데이터베이스 연결
- **Swing GUI**: 직관적이고 사용하기 쉬운 인터페이스
- **외부 라이브러리 활용**: JCalendar를 통한 달력 기능 구현

### 사용자 편의성
- **테이블 기반 조회**: 데이터를 한눈에 확인 가능
- **실시간 업데이트**: 데이터 변경 즉시 반영
- **검색 기능**: 효율적인 데이터 검색
- **JCalendar 연동**: 직관적인 예약 날짜 선택 인터페이스

### 확장성
- **모듈화된 구조**: 새로운 기능 추가 용이
- **인터페이스 기반 설계**: 구현체 변경 가능
- **설정 파일**: 환경 설정 외부화

## 개발 과정에서 해결한 문제

### 데이터베이스 연결 관리
- **문제**: 다중 DAO에서의 연결 관리 복잡성
- **해결**: AbstractBaseDAO를 통한 공통 연결 관리
- **결과**: 코드 중복 제거 및 연결 안정성 향상

### GUI 이벤트 처리
- **문제**: 복잡한 이벤트 처리 로직
- **해결**: 내부 클래스를 활용한 이벤트 핸들러 분리
- **결과**: 코드 가독성 및 유지보수성 향상

### 데이터 유효성 검증
- **문제**: 사용자 입력 데이터 검증 부족
- **해결**: Controller 계층에서 체계적인 검증 로직 구현
- **결과**: 데이터 무결성 보장

## 학습 성과

### 기술 역량 향상
- **Java 심화**: 객체지향 프로그래밍과 디자인 패턴 적용
- **데이터베이스 연동**: Oracle JDBC를 통한 데이터베이스 연결 기술
- **GUI 프로그래밍**: Swing을 활용한 데스크톱 애플리케이션 개발
- **외부 라이브러리 활용**: JCalendar 등 서드파티 라이브러리 연동 경험
- **아키텍처 설계**: MVC 패턴과 계층형 아키텍처 이해

### 프로젝트 관리
- **모듈화**: 기능별 패키지 분리를 통한 체계적 관리
- **버전 관리**: 다양한 버전의 코드 관리 경험
- **문서화**: 체계적인 코드 문서화

## 향후 개선 방향성

### 기능 개선
- **웹 애플리케이션 전환**: Spring Boot를 활용한 웹 버전 개발
- **모바일 앱**: Android/iOS 네이티브 앱 개발
- **결제 시스템**: 온라인 결제 기능 추가
- **실시간 알림**: 예약 상태 변경 알림 기능

### 기술적 개선
- **Spring Framework**: Spring Boot, Spring Data JPA 도입
- **REST API**: RESTful API 설계 및 구현
- **보안 강화**: Spring Security를 통한 인증/인가
- **테스트 자동화**: JUnit을 활용한 단위 테스트

### 사용자 경험 개선
- **반응형 UI**: 다양한 화면 크기 지원
- **사용자 피드백**: 작업 완료 메시지 및 진행 상태 표시
- **데이터 시각화**: 차트를 통한 통계 정보 제공
- **다국어 지원**: 국제화(i18n) 기능 추가

---

**개발 정보**
- **개발자**: 송재원
- **개발 기간**: 2024_3월~5월
- **프로젝트 성격**: Java 학습용 포트폴리오 프로젝트
- **목표**: Java GUI 프로그래밍과 데이터베이스 연동 기술 습득 등
