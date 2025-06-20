# 🎯 토요스터디 - 디자인 패턴 예제 프로젝트

## 📖 프로젝트 소개

이 프로젝트는 **디자인 패턴 학습을 위한 Spring Boot 기반 예제 프로젝트**입니다. 
**서브웨이 샌드위치 주문 시스템**이라는 친숙한 실생활 예제를 통해 각 디자인 패턴의 핵심 개념과 구현 방법을 직관적으로 이해할 수 있도록 설계되었습니다.

### 🎨 설계 철학
- **실생활 예제**: 누구나 알고 있는 서브웨이 주문 과정
- **직관적 이해**: 복잡한 이론보다는 쉬운 예제로 설명
- **실무 적용**: Spring Boot 기반으로 실제 웹 애플리케이션 구현
- **시각적 결과**: HTML 출력으로 주문 결과를 예쁘게 확인

## 🏗️ 기술 스택

- **Java 17** + **Spring Boot 2.6.1**
- **Maven** (멀티 모듈 프로젝트)
- **Lombok** (보일러플레이트 코드 제거)
- **Swagger/OpenAPI 3** (API 문서화)
- **ModelMapper** (객체 매핑)

## 📚 구현된 디자인 패턴

### 1. 🏗️ [Builder Pattern](./builder-pattern.md)
> **복잡한 객체를 단계별로 구성하는 생성 패턴**

**실생활 예제**: 서브웨이 샌드위치 주문 과정
- 빵 선택 → 치즈 추가 → 야채 추가 → 소스 선택

**핵심 학습 포인트**:
- Method Chaining을 통한 Fluent API
- Director 패턴과의 결합
- Lombok @Builder와의 비교

**API 엔드포인트**:
```bash
GET /api/builder/sandwich/chicken-teriyaki
GET /api/builder/sandbox/veggie-delight
```

### 2. ⛓️ [Chain of Responsibility Pattern](./chain-of-responsibility-pattern.md)
> **요청을 처리할 수 있는 객체들을 체인으로 연결하는 행동 패턴**

**실생활 예제**: 서브웨이 직원들의 역할 분담
- 빵 담당자 → 치즈 담당자 → 야채 담당자 → 소스 담당자

**핵심 학습 포인트**:
- Handler 체인 구성
- 동기 vs 비동기 처리 비교
- 병렬 처리를 통한 성능 최적화

**API 엔드포인트**:
```bash
POST /api/chain/sandwich/build           # 커스텀 샌드위치
GET /api/chain/set/classic               # 동기 처리
GET /api/chain/set/premium-async         # 비동기 처리
```

## 🚀 실행 방법

### 1. 프로젝트 클론 및 실행
```bash
# 프로젝트 클론
git clone <repository-url>
cd saturday-study-springboot

# Maven 빌드 및 실행
mvn clean install
cd design-patterns
mvn spring-boot:run
```

### 2. API 테스트
```bash
# Builder Pattern 테스트
curl -X GET "http://localhost:8080/api/builder/sandwich/chicken-teriyaki"

# Chain of Responsibility Pattern 테스트  
curl -X GET "http://localhost:8080/api/chain/set/classic"
curl -X GET "http://localhost:8080/api/chain/set/premium-async"
```

### 3. Swagger UI 접속
```
http://localhost:8080/swagger-ui/index.html
```

## 📁 프로젝트 구조

```
saturday-study-springboot/
├── pom.xml                                 # 루트 Maven 설정
├── .cursorrules                           # 개발 가이드라인
├── docs/                                  # 문서 디렉터리
│   ├── README.md                          # 프로젝트 개요 (현재 파일)
│   ├── builder-pattern.md                 # Builder Pattern 문서
│   └── chain-of-responsibility-pattern.md # Chain of Responsibility Pattern 문서
└── design-patterns/                       # 메인 모듈
    ├── pom.xml                           # 모듈 Maven 설정
    └── src/main/java/kr/co/kiyu/designpatterns/
        ├── builderpattern/               # Builder Pattern 구현
        │   └── subway/sandwich/
        │       ├── model/dto/            # DTO 클래스들
        │       ├── model/type/           # Enum 타입들
        │       ├── builder/              # Builder 구현체들
        │       ├── service/              # 비즈니스 로직
        │       └── order/                # REST 컨트롤러
        └── chainofresponsibility/        # Chain of Responsibility Pattern 구현
            └── subway/
                ├── sandwich/             # 샌드위치 조립 체인
                └── set/                  # 세트 주문 체인
```

## 🎯 학습 로드맵

### 1단계: 기본 이해
- [ ] 각 패턴의 문서 읽기
- [ ] 실생활 예제와 패턴 개념 연결하기
- [ ] API를 통해 실제 동작 확인하기

### 2단계: 코드 분석
- [ ] 각 패턴의 구현체 코드 분석
- [ ] 클래스 다이어그램 그려보기
- [ ] 패턴 적용 전후 비교해보기

### 3단계: 실무 적용
- [ ] 새로운 패턴 추가해보기
- [ ] 기존 패턴을 다른 도메인에 적용해보기
- [ ] 성능 최적화 방법 실험해보기

## 💡 주요 특징

### 🍔 친숙한 도메인
서브웨이 주문 시스템으로 누구나 이해하기 쉬운 예제

### 🎨 시각적 출력
HTML 형태로 주문 결과를 예쁘게 표시

### ⚡ 성능 비교
동기 vs 비동기 처리 성능 비교 가능

### 📊 실시간 모니터링
로그를 통한 각 단계별 처리 시간 확인

### 🧪 확장 가능한 구조
새로운 패턴과 기능을 쉽게 추가할 수 있는 구조

## 🤝 기여 방법

1. **새로운 패턴 추가**
   - `.cursorrules` 파일의 가이드라인 준수
   - 서브웨이 도메인 활용
   - `/docs/{패턴명}.md` 문서 작성

2. **기존 패턴 개선**
   - 성능 최적화
   - 코드 품질 개선
   - 문서 업데이트

3. **버그 리포트 및 기능 제안**
   - 이슈 등록을 통한 버그 리포트
   - 새로운 기능 아이디어 공유

## 📞 문의 및 지원

- **작성자**: KIYU-IT
- **프로젝트**: 토요스터디 - 디자인 패턴 학습
- **목적**: 교육용 예제 프로젝트

---

> 💡 **팁**: 각 패턴의 상세한 설명은 해당 패턴의 문서를 참고해주세요!
> - [Builder Pattern 상세 보기](./builder-pattern.md)
> - [Chain of Responsibility Pattern 상세 보기](./chain-of-responsibility-pattern.md) 