package kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.model.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.model.type.BreadType;
import kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.model.type.CheeseType;
import kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.model.type.SauceType;
import kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.model.type.VegetableType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 서브웨이 샌드위치
 * @author KIYU-IT
 * @date 2025. 2. 20.
 */
@Schema(description = "서브웨이 샌드위치 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SandwichDTO {

	@Schema(description = "빵은 반드시 한 가지만 선택할 수 있습니다.")
	private BreadType bread;

	@Schema(description = "치즈는 돈내면 추가가 가능한데, 장 수에 제한이 있습니다.")
	private Map<CheeseType, Integer> cheeses;

	@Schema(description = "야채는 돈내면 무제한 추가 가능합니다. ^^")
	private List<VegetableType> vegetables;

	@Schema(description = "소스는 최대 3가지를 선택할 수 있으며, 중복 선택은 불가합니다.")
	private Set<SauceType> sauces;

}
