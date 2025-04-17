package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 소스 종류
 * @author KIYU-IT
 * @date 2025. 4. 17.
 */
@Schema(description = "소스 종류")
public enum SauceType {

    MAYO("마요네즈"),
    SWEET_ONION("스위트 어니언"),
    HONEY_MUSTARD("허니 머스타드"),
    RANCH("렌치 드레싱"),
    HOT_CHILI("핫칠리"),
    SOUTHWEST_CHIPOTLE("사우스웨스트 치폴레"),
    OLIVE_OIL("올리브 오일"),
    RED_WINE_VINEGAR("레드와인 식초"),
    SALT("소금"),
    PEPPER("후추"),
    BBQ("바비큐 소스");

    @Getter
    private final String sauce;

    SauceType(String sauce) {
        this.sauce = sauce;
    }

}
