package kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 음료 종류
 * @author KIYU-IT
 * @date 2025. 4. 18.
 */
@Schema(description = "음료 종류")
public enum DrinkType {

    COKE("코카콜라"),
    SPRITE("스프라이트"),
    ICED_TEA("아이스티"),
    ZERO_COKE("코카콜라 제로");

    @Getter
    private final String drink;

    DrinkType(String drink) {
        this.drink = drink;
    }

}
