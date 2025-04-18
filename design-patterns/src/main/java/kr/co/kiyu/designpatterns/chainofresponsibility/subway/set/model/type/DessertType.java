package kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 디저트 종류 (쿠키, 스낵 등 선택)
 * @author KIYU-IT
 * @date 2025. 4. 18.
 */
@Schema(description = "디저트 종류")
public enum DessertType {

    DOUBLE_CHOCOLATE_COOKIE("더블초코칩 쿠키"),
    WHITE_MACADAMIA_COOKIE("화이트 마카다미아 쿠키"),
    CHEESEBALL_SNACK("치즈볼 스낵"),
    CHURROS("츄러스");

    @Getter
    private final String dessert;

    DessertType(String dessert) {
        this.dessert = dessert;
    }

}
