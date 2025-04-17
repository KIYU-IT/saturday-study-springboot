package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.type;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;

/**
 * 치즈 종류
 * @author KIYU-IT
 * @date 2025. 4. 17.
 */
@Schema(description = "치즈 종류")
public enum CheeseType {

	AMERICAN_CHEESE("아메리칸 치즈"),
    SHREDDED_CHEESE("슈레드 치즈"),
    MOZZARELLA_CHEESE("모짜렐라 치즈");

    @Getter
	private String cheese;

	CheeseType(String cheese) {
		this.cheese = cheese;
	}

}
