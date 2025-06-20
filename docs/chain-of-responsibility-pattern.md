# ⛓️ Chain of Responsibility Pattern (책임 연쇄 패턴)

## 📖 패턴 개요

Chain of Responsibility Pattern은 **요청을 처리할 수 있는 객체들을 체인으로 연결**하여, 요청이 적절한 처리기를 만날 때까지 체인을 따라 전달하는 행동 패턴입니다.

### 🎯 사용 목적
- **여러 객체 중 어떤 것이 요청을 처리할지 미리 알 수 없을 때**
- **요청 처리 순서를 동적으로 변경**하고 싶을 때
- **처리기의 집합과 순서를 런타임에 지정**하고 싶을 때
- **요청자와 처리자를 분리**하고 싶을 때

## 🍔 실생활 예제: 서브웨이 샌드위치 조립 과정

서브웨이에서 샌드위치를 만들 때 직원들의 역할 분담을 생각해보세요:

### 1. 샌드위치 조립 체인 🥪
1. **빵 담당자** → 빵을 선택하고 자른다
2. **치즈 담당자** → 치즈를 올린다
3. **야채 담당자** → 야채들을 추가한다
4. **소스 담당자** → 소스를 뿌린다

### 2. 세트 주문 체인 🍱
1. **샌드위치 주문 처리** → 샌드위치를 먼저 준비
2. **음료 담당자** → 음료를 준비 (병렬 처리 가능)
3. **디저트 담당자** → 디저트를 준비 (병렬 처리 가능)

각 담당자는 **자신의 역할만 수행**하고, **다음 담당자에게 전달**합니다.

## 🏗️ 구조 및 구현

### 1. Abstract Handler (추상 핸들러)
```java
public abstract class SandwichHandler {
    protected SandwichHandler next;

    public SandwichHandler setNext(SandwichHandler next) {
        this.next = next;
        return next;
    }

    public void handle(SandwichCommand command, SandwichDTO sandwich) {
        if (canHandle(command)) {
            apply(command, sandwich);
        }
        if (next != null) {
            next.handle(command, sandwich);
        }
    }

    protected abstract boolean canHandle(SandwichCommand command);
    protected abstract void apply(SandwichCommand command, SandwichDTO sandwich);
}
```

### 2. Concrete Handlers (구체적 핸들러들)

#### 빵 담당자 🥖
```java
@Component
public class BreadHandler extends SandwichHandler {
    
    @Override
    protected boolean canHandle(SandwichCommand command) {
        return command.getBread() != null;
    }
    
    @Override
    protected void apply(SandwichCommand command, SandwichDTO sandwich) {
        log.info("🥖 빵 선택: {}", command.getBread());
        sandwich.setBread(BreadType.valueOf(command.getBread().toUpperCase()));
    }
}
```

#### 치즈 담당자 🧀
```java
@Component
public class CheeseHandler extends SandwichHandler {
    
    @Override
    protected boolean canHandle(SandwichCommand command) {
        return command.getCheeses() != null && !command.getCheeses().isEmpty();
    }
    
    @Override
    protected void apply(SandwichCommand command, SandwichDTO sandwich) {
        log.info("🧀 치즈 추가");
        Map<CheeseType, Integer> cheeses = new HashMap<>();
        command.getCheeses().forEach((type, quantity) -> {
            CheeseType cheeseType = CheeseType.valueOf(type.toUpperCase());
            cheeses.put(cheeseType, quantity);
            log.info("   - {}: {}장", cheeseType.getCheese(), quantity);
        });
        sandwich.setCheeses(cheeses);
    }
}
```

### 3. Command (명령 객체)
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SandwichCommand {
    private String bread;
    private Map<String, Integer> cheeses;
    private List<String> vegetables;
    private List<String> sauces;
}
```

### 4. 세트 주문을 위한 Context
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderContext {
    private String menuName;
    private SandwichDTO sandwich;
    private DrinkType drink;
    private DessertType dessert;
    private int totalPrice;
}
```

## 🌐 REST API 사용법

### 샌드위치 조립 엔드포인트
- `POST /api/chain/sandwich/build` - 커스텀 샌드위치 조립
- `GET /api/chain/sandwich/chicken-teriyaki` - 치킨 데리야끼 샌드위치 조립

### 세트 주문 엔드포인트
- `GET /api/chain/set/classic` - 클래식 세트 주문 (동기 처리)
- `GET /api/chain/set/premium-async` - 프리미엄 세트 주문 (비동기 처리)

### 사용 예시
```bash
# 동기 처리
curl -X GET "http://localhost:8080/api/chain/set/classic"

# 비동기 처리 (더 빠름)
curl -X GET "http://localhost:8080/api/chain/set/premium-async"
```

### 커스텀 샌드위치 주문
```bash
curl -X POST "http://localhost:8080/api/chain/sandwich/build" \
-H "Content-Type: application/json" \
-d '{
  "bread": "HONEY_OAT",
  "cheeses": {"CHEDDAR": 2, "AMERICAN": 1},
  "vegetables": ["LETTUCE", "TOMATO", "CUCUMBER"],
  "sauces": ["RANCH", "MAYONNAISE"]
}'
```

## ⚡ 동기 vs 비동기 처리

### 동기 처리 (Sequential)
```java
public String buildSet(SetOrderCommand command) {
    // 체인 구성
    sandwichHandler.setNext(drinkHandler).setNext(dessertHandler);
    
    // 순차적 처리
    sandwichHandler.handle(context);
    
    return toHtml(context);
}
```

### 비동기 처리 (Parallel)
```java
public String buildSetAsync(SetOrderCommand command) {
    // 1. 샌드위치는 동기 처리 (기본)
    sandwichHandler.handle(context);
    
    // 2. 음료와 디저트는 병렬 비동기 처리
    CompletableFuture<Void> drinkFuture = drinkHandler.handleAsync(context);
    CompletableFuture<Void> dessertFuture = dessertHandler.handleAsync(context);
    
    // 3. 모든 작업 완료 대기
    CompletableFuture.allOf(drinkFuture, dessertFuture).join();
    
    return toHtml(context);
}
```

## ✅ 패턴의 장점

1. **결합도 감소**: 요청자와 처리자가 분리됨
2. **유연성**: 체인을 동적으로 구성할 수 있음
3. **확장성**: 새로운 핸들러를 쉽게 추가할 수 있음
4. **단일 책임 원칙**: 각 핸들러는 하나의 책임만 가짐
5. **개방-폐쇄 원칙**: 기존 코드 수정 없이 새로운 핸들러 추가 가능

## ❌ 패턴의 단점

1. **성능**: 체인이 길어질수록 처리 시간 증가
2. **디버깅 어려움**: 어떤 핸들러가 처리했는지 추적 어려움
3. **무한 루프 위험**: 잘못된 체인 구성 시 무한 루프 발생 가능
4. **메모리 사용량**: 여러 핸들러 객체로 인한 메모리 사용

## 🔄 다른 패턴과의 관계

- **Command Pattern**: 요청을 객체로 캡슐화하여 체인으로 전달
- **Composite Pattern**: 트리 구조의 복합 객체에서 책임 연쇄 활용
- **Decorator Pattern**: 객체에 동적으로 기능을 추가하는 체인

## 💡 실무 활용 팁

### 1. 병렬 처리 최적화
```java
// 독립적인 작업들은 병렬로 처리
CompletableFuture.allOf(
    drinkHandler.handleAsync(context),
    dessertHandler.handleAsync(context)
).join();
```

### 2. 로깅 및 모니터링
```java
@Override
public void handle(SandwichCommand command, SandwichDTO sandwich) {
    long start = System.currentTimeMillis();
    if (canHandle(command)) {
        apply(command, sandwich);
        log.info("{}ms - {}", System.currentTimeMillis() - start, this.getClass().getSimpleName());
    }
    // ...
}
```

### 3. 예외 처리
```java
@Override
public void handle(SandwichCommand command, SandwichDTO sandwich) {
    try {
        if (canHandle(command)) {
            apply(command, sandwich);
        }
    } catch (Exception e) {
        log.error("Handler 처리 중 오류: {}", e.getMessage());
        // 오류 처리 로직
    }
    // 다음 핸들러로 계속 진행
    if (next != null) {
        next.handle(command, sandwich);
    }
}
```

## 📊 성능 비교

| 처리 방식 | 샌드위치 | 음료 | 디저트 | 총 시간 |
|---------|----------|------|--------|--------|
| **동기** | 1000ms | 800ms | 600ms | ~2400ms |
| **비동기** | 1000ms | 800ms + 600ms = 800ms (병렬) | ~1800ms |

> 💡 **비동기 처리**로 약 **25% 성능 향상** 달성!

## 📝 학습 체크포인트

- [ ] Chain of Responsibility 패턴의 핵심 개념 이해
- [ ] Handler 체인을 구성하는 방법 파악
- [ ] 동기 vs 비동기 처리의 차이점 이해
- [ ] 언제 이 패턴을 사용해야 하는지 판단 가능
- [ ] 실제 코드에서 패턴 적용 및 성능 테스트 완료
- [ ] 병렬 처리를 통한 성능 최적화 방법 숙지
</rewritten_file> 