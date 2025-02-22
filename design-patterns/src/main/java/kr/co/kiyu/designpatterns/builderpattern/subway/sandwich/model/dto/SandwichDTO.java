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

	/**
     * 샌드위치 구성 요소를 HTML 형식의 문자열로 반환한다.
     */
    public String toHtmlContent() {
        StringBuilder html = new StringBuilder();

        html.append("<div style='font-family: Arial, sans-serif; line-height: 1.6;'>");

        // 빵 종류
        html.append("<h2>주문하신 샌드위치 구성 요소를 안내해드립니다.</h2>");
        html.append("<p><strong>빵:</strong> ").append(bread != null ? bread.getBread() : "선택되지 않음").append("</p>");

        // 치즈 종류 및 수량
        html.append("<p><strong>치즈:</strong></p>");
        if (cheeses != null && !cheeses.isEmpty()) {
            html.append("<ul>");
            for (Map.Entry<CheeseType, Integer> entry : cheeses.entrySet()) {
                html.append("<li>")
                    .append(entry.getKey().getCheese())
                    .append(" - ")
                    .append(entry.getValue())
                    .append("장")
                    .append("</li>");
            }
            html.append("</ul>");
        } else {
            html.append("<p>선택되지 않음</p>");
        }

        // 야채 목록
        html.append("<p><strong>야채:</strong></p>");
        if (vegetables != null && !vegetables.isEmpty()) {
            html.append("<ul>");
            for (VegetableType vegetable : vegetables) {
                html.append("<li>").append(vegetable.getVegetable()).append("</li>");
            }
            html.append("</ul>");
        } else {
            html.append("<p>선택되지 않음</p>");
        }

        // 소스 목록
        html.append("<p><strong>소스:</strong></p>");
        if (sauces != null && !sauces.isEmpty()) {
            html.append("<ul>");
            for (SauceType sauce : sauces) {
                html.append("<li>").append(sauce.getSauce()).append("</li>");
            }
            html.append("</ul>");
        } else {
            html.append("<p>선택되지 않음</p>");
        }

        html.append("</div>");

        return html.toString();
    }

}
