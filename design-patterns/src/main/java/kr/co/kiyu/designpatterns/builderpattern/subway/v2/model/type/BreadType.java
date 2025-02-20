package kr.co.kiyu.designpatterns.builderpattern.subway.v2.model.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 빵 종류
 * @author KIYU-IT
 * @date 2025. 2. 20.
 */
@Schema(description = "빵 종류")
public enum BreadType {

    WHITE("화이트"),
    WHEAT("위트"),
    HONEY_OAT("허니 오트"),
    HEARTY_ITALIAN("하티 이탈리안"),
    PARMESAN_OREGANO("파마산 오레가노"),
    FLATBREAD("플랫브레드");

    @Getter
    private final String bread;

    BreadType(String bread) {
        this.bread = bread;
    }

}
