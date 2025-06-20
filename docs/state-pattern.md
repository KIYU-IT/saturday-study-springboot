# 🔄 State Pattern (상태 패턴)

## 📖 패턴 개요

State Pattern은 **객체의 상태에 따라 행동이 달라지는 것**을 캡슐화하여, 상태 변화에 따른 복잡한 조건문을 제거하고 각 상태를 독립적인 클래스로 관리하는 행동 패턴입니다.

### 🎯 사용 목적
- **상태에 따라 객체의 행동이 달라져야 할 때**
- **복잡한 조건문(if-else, switch)을 제거**하고 싶을 때
- **상태 전환 로직을 체계적으로 관리**하고 싶을 때
- **새로운 상태를 쉽게 추가**하고 싶을 때

## 🛒 실생활 예제: 서브웨이 주문 상태 관리

배달 주문을 할 때의 상태 변화를 생각해보세요:

### 📱 주문 진행 흐름
1. **🛒 주문 접수** - 취소 가능, 수정 가능
2. **🍳 조리 중** - 취소 제한, 예상 시간 제공
3. **📦 포장 중** - 취소 불가, 배달 준비
4. **🚗 배달 중** - 위치 추적, 연락 가능
5. **✅ 완료** - 리뷰 작성, 재주문 가능
6. **❌ 취소** - 환불 처리 (특정 상태에서만)

**각 상태마다 가능한 행동과 제약사항이 완전히 다릅니다!**

## 🏗️ 구조 및 구현

### 1. State 인터페이스
```java
public interface OrderState {
    boolean nextStep(OrderContext context);     // 다음 단계 진행
    boolean cancel(OrderContext context);       // 주문 취소
    List<String> getAvailableActions();         // 가능한 액션 목록
    String getStatusMessage();                  // 상태 메시지
    OrderStateType getStateType();              // 상태 타입
    int getEstimatedMinutes();                  // 예상 완료 시간
}
```

### 2. Context (컨텍스트)
```java
@Slf4j
@Getter
public class OrderContext {
    private OrderDTO order;                     // 주문 정보
    private OrderState currentState;            // 현재 상태
    private List<String> stateHistory;          // 상태 변경 이력

    public boolean nextStep() {
        return currentState.nextStep(this);
    }

    public boolean cancel() {
        return currentState.cancel(this);
    }

    public void changeState(OrderState newState, String reason) {
        // 상태 변경 로직
        this.currentState = newState;
        this.order.setCurrentState(newState.getStateType());
        // 로깅 및 이력 관리
    }
}
```

### 3. Concrete States (구체적 상태들)

#### 주문 접수 상태 🛒
```java
@Component
public class OrderReceivedState implements OrderState {
    
    @Autowired private CookingState cookingState;
    @Autowired private CancelledState cancelledState;

    @Override
    public boolean nextStep(OrderContext context) {
        context.changeState(cookingState, "조리 시작");
        return true;
    }

    @Override
    public boolean cancel(OrderContext context) {
        context.changeState(cancelledState, "고객 요청으로 취소");
        return true; // 접수 단계에서는 취소 가능
    }

    @Override
    public List<String> getAvailableActions() {
        return Arrays.asList("조리 시작", "주문 취소", "주문 수정");
    }
}
```

#### 조리 중 상태 🍳
```java
@Component
public class CookingState implements OrderState {
    
    @Autowired private PackagingState packagingState;

    @Override
    public boolean nextStep(OrderContext context) {
        context.changeState(packagingState, "조리 완료, 포장 시작");
        return true;
    }

    @Override
    public boolean cancel(OrderContext context) {
        return false; // 조리 중에는 취소 불가
    }

    @Override
    public List<String> getAvailableActions() {
        return Arrays.asList("포장 시작", "조리 상태 확인");
    }
}
```

## 🌐 REST API 사용법

### 핵심 엔드포인트
```bash
# 1. 샘플 주문 생성
POST /api/state/order/sample

# 2. 다음 단계 진행 (핵심 기능!)
PUT /api/state/order/{orderId}/next-step

# 3. 주문 취소 시도
PUT /api/state/order/{orderId}/cancel

# 4. 현재 상태 조회
GET /api/state/order/{orderId}/status

# 5. 가능한 액션 조회
GET /api/state/order/{orderId}/actions
```

### 사용 시나리오
```bash
# 1. 주문 생성
curl -X POST "http://localhost:8080/api/state/order/sample"
# → 응답에서 주문 ID 확인 (예: ORD-1737623456789)

# 2. 상태 진행 (여러 번 호출하여 상태 변화 확인)
curl -X PUT "http://localhost:8080/api/state/order/ORD-1737623456789/next-step"
# → 주문 접수 → 조리 중

curl -X PUT "http://localhost:8080/api/state/order/ORD-1737623456789/next-step"
# → 조리 중 → 포장 중

# 3. 취소 시도 (상태에 따라 결과 다름)
curl -X PUT "http://localhost:8080/api/state/order/ORD-1737623456789/cancel"
# → 포장 중 상태에서는 취소 불가!

# 4. 가능한 액션 확인
curl -X GET "http://localhost:8080/api/state/order/ORD-1737623456789/actions"
```

## 📊 상태별 행동 비교

| 상태 | 다음 단계 | 취소 가능 | 주요 액션 | 예상 시간 |
|------|-----------|-----------|-----------|-----------|
| **🛒 주문 접수** | 조리 시작 | ✅ | 수정, 결제 확인 | 25분 |
| **🍳 조리 중** | 포장 시작 | ❌ | 상태 확인 | 15분 |
| **📦 포장 중** | 배달 시작 | ❌ | 배달 준비 확인 | 10분 |
| **🚗 배달 중** | 완료 | ❌ | 위치 추적, 연락 | 0분 |
| **✅ 완료** | - | ❌ | 리뷰, 재주문 | 0분 |
| **❌ 취소** | - | - | 환불 확인 | 0분 |

## ✅ 패턴의 장점

### 1. **조건문 제거**
```java
// Before: 복잡한 조건문
if (order.getStatus() == OrderStatus.RECEIVED) {
    if (action.equals("next")) {
        order.setStatus(OrderStatus.COOKING);
    } else if (action.equals("cancel")) {
        order.setStatus(OrderStatus.CANCELLED);
    }
} else if (order.getStatus() == OrderStatus.COOKING) {
    // 더 많은 조건문...
}

// After: State 패턴
context.nextStep(); // 현재 상태가 알아서 처리
context.cancel();   // 상태별로 다른 동작
```

### 2. **확장성**
- 새로운 상태 추가가 쉬움 (예: "배달 지연" 상태)
- 기존 코드 수정 없이 새로운 행동 추가 가능

### 3. **캡슐화**
- 각 상태의 로직이 해당 클래스에만 집중
- 상태 전환 규칙이 명확하게 정의됨

### 4. **테스트 용이성**
- 각 상태를 독립적으로 테스트 가능
- Mock 객체 활용이 쉬움

## ❌ 패턴의 단점

### 1. **클래스 수 증가**
- 상태마다 클래스가 필요
- 간단한 상태 관리에는 오버스펙

### 2. **복잡성**
- 상태 간 의존성 관리 필요
- 순환 참조 위험

### 3. **메모리 사용량**
- 각 상태 객체가 메모리 점유

## 🔄 다른 패턴과의 관계

### **vs Strategy Pattern**
- **Strategy**: 알고리즘 교체에 중점
- **State**: 상태 변화에 따른 행동 변화에 중점

### **vs Command Pattern**
- **Command**: 요청을 객체로 캡슐화
- **State**: 상태를 객체로 캡슐화

### **+ Observer Pattern**
```java
// 상태 변경 시 옵저버에게 알림
public void changeState(OrderState newState, String reason) {
    this.currentState = newState;
    notifyObservers(newState); // 상태 변화 알림
}
```

## 💡 실무 활용 팁

### 1. **Spring 의존성 주입 활용**
```java
@Component
public class OrderReceivedState implements OrderState {
    @Autowired private CookingState cookingState;
    @Autowired private CancelledState cancelledState;
    // 상태 객체들을 Spring이 관리
}
```

### 2. **상태 이력 관리**
```java
private List<String> stateHistory = new ArrayList<>();

private void addStateHistory(String reason, OrderStateType newState) {
    String entry = String.format("[%s] %s → %s", 
            LocalDateTime.now(), reason, newState.getStateName());
    stateHistory.add(entry);
}
```

### 3. **예외 처리**
```java
@Override
public boolean nextStep(OrderContext context) {
    try {
        // 상태 전환 로직
        context.changeState(nextState, "정상 진행");
        return true;
    } catch (Exception e) {
        log.error("상태 전환 실패: {}", e.getMessage());
        return false;
    }
}
```

### 4. **성능 최적화**
```java
// 상태 객체 캐싱
@Component
@Scope("singleton") // 싱글톤으로 관리
public class CookingState implements OrderState {
    // 상태 객체는 상태를 가지지 않으므로 공유 가능
}
```

## 🧪 테스트 방법

### 1. **단위 테스트**
```java
@Test
void testOrderReceivedStateNextStep() {
    // Given
    OrderContext context = new OrderContext(order, orderReceivedState);
    
    // When
    boolean result = context.nextStep();
    
    // Then
    assertTrue(result);
    assertEquals(OrderStateType.COOKING, context.getCurrentStateType());
}
```

### 2. **통합 테스트**
```java
@Test
void testFullOrderLifecycle() {
    // 주문 생성 → 조리 → 포장 → 배달 → 완료
    // 전체 라이프사이클 테스트
}
```

## 📝 학습 체크포인트

- [ ] State 패턴의 핵심 개념 이해 (상태별 행동 캡슐화)
- [ ] Context의 역할과 상태 전환 메커니즘 파악
- [ ] 각 상태 클래스의 독립성과 책임 분리 이해
- [ ] 조건문 제거의 효과 체험
- [ ] 실제 API를 통한 상태 전환 시나리오 테스트
- [ ] 새로운 상태 추가 실습 (예: "배달 지연" 상태)
- [ ] Strategy 패턴과의 차이점 명확히 구분

## 🚀 확장 아이디어

1. **알림 시스템**: 상태 변경 시 고객에게 SMS/푸시 알림
2. **배치 처리**: 특정 시간 후 자동 상태 전환
3. **롤백**: 이전 상태로 되돌리기 기능
4. **상태 제한**: 특정 조건에서만 상태 전환 허용
5. **메트릭스**: 각 상태별 평균 소요 시간 측정

---

> 💡 **핵심 포인트**: State 패턴은 복잡한 상태 관리를 **"각 상태가 스스로 행동하게"** 만드는 패턴입니다. 
> 상태별 if-else 지옥에서 벗어나 깔끔하고 확장 가능한 코드를 만들어보세요! 