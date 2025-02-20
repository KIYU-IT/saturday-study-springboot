package kr.co.kiyu.designpatterns.builderpattern.subway.v2.model.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 야채 종류
 * @author KIYU-IT
 * @date 2025. 2. 20.
 */
@Schema(description = "야채 종류")
public enum VegetableType {

    LETTUCE("양상추"),
    TOMATO("토마토"),
    CUCUMBER("오이"),
    GREEN_PEPPER("피망"),
    ONION("양파"),
    PICKLE("피클"),
    OLIVE("올리브"),
    JALAPENO("할라피뇨"),
    AVOCADO("아보카도");

    @Getter
    private final String vegetable;

    VegetableType(String vegetable) {
        this.vegetable = vegetable;
    }

}
