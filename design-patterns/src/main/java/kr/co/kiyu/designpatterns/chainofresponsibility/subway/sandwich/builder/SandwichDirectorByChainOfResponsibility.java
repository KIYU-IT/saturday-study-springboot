package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.builder;

import org.springframework.stereotype.Component;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.dto.SandwichDTO;

@Component
public class SandwichDirectorByChainOfResponsibility {

    // 치킨 데리야끼 샌드위치 (WHEAT + 아메리칸 치즈 + 양상추, 토마토 + 스위트 어니언, 마요네즈)
    public SandwichDTO buildChickenTeriyaki(SandwichBuilderByChainOfResponsibility builder) {
        return builder
                .selectBread("WHEAT")
                .addCheese("AMERICAN_CHEESE", 2)
                .addVegetable("LETTUCE")
                .addVegetable("TOMATO")
                .addSauce("SWEET_ONION")
                .addSauce("MAYO")
                .build();
    }

    // 베지 딜라이트 (화이트 + 각종 야채 + 올리브 오일, 레드와인 식초)
    public SandwichDTO buildVeggieDelight(SandwichBuilderByChainOfResponsibility builder) {
        return builder
                .selectBread("WHITE")
                .addVegetable("CUCUMBER")
                .addVegetable("OLIVE")
                .addVegetable("GREEN_PEPPER")
                .addVegetable("TOMATO")
                .addVegetable("ONION")
                .addSauce("OLIVE_OIL")
                .addSauce("RED_WINE_VINEGAR")
                .build();
    }

    // 🥓 **스파이시 이탈리안** (매콤한 살라미와 페퍼로니 조합)
    public SandwichDTO buildSpicyItalian(SandwichBuilderByChainOfResponsibility builder) {
        return builder
                .selectBread("HEARTY_ITALIAN")
                .addCheese("SHREDDED_CHEESE", 2)
                .addVegetable("LETTUCE")
                .addVegetable("ONION")
                .addVegetable("JALAPENO")
                .addSauce("HOT_CHILI")
                .addSauce("RANCH")
                .build();
    }

    // 🧀 **치킨 베이컨 아보카도** (치킨 + 베이컨 + 아보카도 + 랜치 소스)
    public SandwichDTO buildChickenBaconAvocado(SandwichBuilderByChainOfResponsibility builder) {
        return builder
                .selectBread("HONEY_OAT")
                .addCheese("MOZZARELLA_CHEESE", 2)
                .addVegetable("LETTUCE")
                .addVegetable("TOMATO")
                .addVegetable("AVOCADO")
                .addSauce("RANCH")
                .addSauce("MAYO")
                .build();
    }

    // 🥩 **풀드 포크 바비큐** (부드러운 바비큐 스타일 돼지고기)
    public SandwichDTO buildPulledPorkBBQ(SandwichBuilderByChainOfResponsibility builder) {
        return builder
                .selectBread("PARMESAN_OREGANO")
                .addCheese("CHEDDAR", 2)
                .addVegetable("ONION")
                .addVegetable("PICKLE")
                .addVegetable("TOMATO")
                .addSauce("SOUTHWEST_CHIPOTLE")
                .addSauce("BBQ")
                .build();
    }

    // 🍤 **쉬림프 샌드위치** (새우 + 다양한 야채)
    public SandwichDTO buildShrimp(SandwichBuilderByChainOfResponsibility builder) {
        return builder
                .selectBread("WHITE")
                .addCheese("MOZZARELLA_CHEESE", 1)
                .addVegetable("LETTUCE")
                .addVegetable("TOMATO")
                .addVegetable("CUCUMBER")
                .addVegetable("GREEN_PEPPER")
                .addSauce("OLIVE_OIL")
                .addSauce("SALT")
                .addSauce("PEPPER")
                .build();
    }

}
