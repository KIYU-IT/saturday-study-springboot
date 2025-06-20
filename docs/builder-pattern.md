# 🏗️ Builder Pattern (빌더 패턴)

## 📖 패턴 개요

Builder Pattern은 **복잡한 객체의 생성 과정을 단계별로 구성**할 수 있게 해주는 생성 패턴입니다. 동일한 생성 과정을 통해 서로 다른 표현 결과를 만들어낼 수 있습니다.

### 🎯 사용 목적
- **복잡한 객체**를 단계별로 구성해야 할 때
- **동일한 생성 과정**으로 다양한 형태의 객체를 만들어야 할 때
- **생성자의 매개변수가 너무 많을 때**
- **객체 생성 과정을 캡슐화**하고 싶을 때

## 🍔 실생활 예제: 서브웨이 샌드위치 주문

서브웨이에서 샌드위치를 주문할 때를 생각해보세요:
1. **빵 선택** (화이트, 위트, 허니오트 등)
2. **치즈 추가** (종류와 수량)
3. **야채 추가** (양상추, 토마토, 오이 등)
4. **소스 선택** (최대 3가지)

각 단계는 **순서가 있고**, **선택사항이 많으며**, **최종 결과물(샌드위치)은 복잡**합니다.

## 🏗️ 구조 및 구현

### 1. Builder 인터페이스
```java
public interface SandwichBuilder {
    SandwichBuilder selectBread(String bread);
    SandwichBuilder addCheese(String cheese, int quantity);
    SandwichBuilder addVegetable(String vegetable);
    SandwichBuilder addSauce(String sauce);
    SandwichDTO build();
}
```

### 2. Concrete Builder (구체적 빌더)
```java
@Component
public class SubwaySandwichBuilder implements SandwichBuilder {
    private BreadType bread;
    private Map<CheeseType, Integer> cheeses = new HashMap<>();
    private List<VegetableType> vegetables = new ArrayList<>();
    private Set<SauceType> sauces = new HashSet<>();

    @Override
    public SandwichBuilder selectBread(String bread) {
        this.bread = BreadType.valueOf(bread.toUpperCase());
        return this;
    }
    
    // ... 다른 메서드들
    
    @Override
    public SandwichDTO build() {
        return SandwichDTO.builder()
                .bread(bread)
                .cheeses(cheeses)
                .vegetables(vegetables)
                .sauces(sauces)
                .build();
    }
}
```

### 3. Director (디렉터)
```java
@Component
public class SandwichDirector {
    
    public SandwichDTO buildChickenTeriyaki(SandwichBuilder builder) {
        return builder
                .selectBread("HONEY_OAT")
                .addCheese("AMERICAN", 2)
                .addVegetable("LETTUCE")
                .addVegetable("TOMATO")
                .addVegetable("CUCUMBER")
                .addSauce("TERIYAKI")
                .addSauce("MAYONNAISE")
                .build();
    }
    
    public SandwichDTO buildVeggieDelight(SandwichBuilder builder) {
        return builder
                .selectBread("WHEAT")
                .addVegetable("LETTUCE")
                .addVegetable("TOMATO")
                .addVegetable("CUCUMBER")
                .addVegetable("BELL_PEPPER")
                .addVegetable("ONION")
                .addSauce("ITALIAN_DRESSING")
                .build();
    }
}
```

### 4. Product (결과물)
```java
@Schema(description = "서브웨이 샌드위치 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SandwichDTO {
    private BreadType bread;
    private Map<CheeseType, Integer> cheeses;
    private List<VegetableType> vegetables;
    private Set<SauceType> sauces;
    
    public String toHtmlContent() {
        // HTML 형태로 주문 결과를 예쁘게 표시
        // ... HTML 생성 로직
    }
}
```

## 🌐 REST API 사용법

### 엔드포인트
- `GET /api/builder/sandwich/chicken-teriyaki` - 치킨 데리야끼 샌드위치 주문
- `GET /api/builder/sandwich/veggie-delight` - 베지 딜라이트 샌드위치 주문

### 사용 예시
```bash
curl -X GET "http://localhost:8080/api/builder/sandwich/chicken-teriyaki"
```

### 응답 예시 (HTML)
```html
<div class='container'>
    <div class='title'>🍽️ 주문하신 샌드위치 구성</div>
    <div class='section'>
        <h3>🥖 빵</h3>
        <p>허니 오트</p>
    </div>
    <div class='section'>
        <h3>🧀 치즈</h3>
        <ul>
            <li><span class='icon'>🟡</span>아메리칸 치즈 - 2장</li>
        </ul>
    </div>
    <!-- ... 더 많은 구성 요소들 -->
</div>
```

## ✅ 패턴의 장점

1. **복잡한 객체 생성 단순화**: 단계별로 객체를 구성
2. **재사용성**: 동일한 빌더로 다양한 객체 생성 가능
3. **가독성**: 메서드 체이닝으로 직관적인 코드
4. **유연성**: 필요한 구성 요소만 선택적으로 설정
5. **불변 객체**: 한 번 생성된 후 변경 불가능한 객체 생성 가능

## ❌ 패턴의 단점

1. **코드 복잡성**: 구현해야 할 클래스가 많음
2. **메모리 사용량**: 빌더 객체로 인한 추가 메모리 사용
3. **러닝 커브**: 간단한 객체에는 오버스펙

## 🔄 다른 패턴과의 관계

- **Abstract Factory**: 복잡한 객체 생성이지만, Builder는 단계별 구성
- **Composite**: Builder로 Composite 객체를 구성할 수 있음
- **Decorator**: Builder로 기본 객체에 장식을 추가하는 과정 구현 가능

## 💡 실무 활용 팁

1. **lombok의 @Builder**와 결합하여 더 간단하게 구현
2. **Validation 로직**을 Builder에 포함시켜 잘못된 객체 생성 방지
3. **Default 값 설정**으로 사용자 편의성 증대
4. **Method Chaining**을 활용한 Fluent API 제공

## 📝 학습 체크포인트

- [ ] Builder Pattern의 구성 요소 4가지 이해
- [ ] Director의 역할과 필요성 파악
- [ ] Method Chaining이 가능한 이유 이해
- [ ] 언제 Builder Pattern을 사용해야 하는지 판단 가능
- [ ] 실제 코드에서 패턴 적용 및 테스트 완료 