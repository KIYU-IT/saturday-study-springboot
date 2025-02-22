package kr.co.kiyu.designpatterns.builderpattern.subway.v2.model.type;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;

/**
 * 샌드위치 종류
 * @author KIYU-IT
 * @date 2025. 2. 22.
 */
@Schema(description = "샌드위치 종류")
public enum SandwichMenuType {

    // 프리미엄
    CHICKEN_TERIYAKI("치킨 데리야끼"),
    SPICY_ITALIAN("스파이시 이탈리안"),
    CHICKEN_BACON_AVOCADO("치킨 베이컨 아보카도"),
    PULLED_PORK_BBQ("풀드 포크 바비큐"),
    K_BBQ("K-바비큐"),
    SHRIMP("쉬림프"),
    SPICY_SHRIMP("스파이시 쉬림프"),

    // 클래식
    TUNA("참치"),
    BLT("비엘티"),
    HAM("햄"),

    // 프레시 & 라이트
    VEGGIE("베지"),
    CHICKEN_SLICE("치킨 슬라이스"),
    ROTISSERIE_BARBEQUE_CHICKEN("로티세리 바비큐 치킨"),
    SUBWAY_CLUB("서브웨이 클럽");

    @Getter
    private final String menuName;

    SandwichMenuType(String menuName) {
        this.menuName = menuName;
    }
}
