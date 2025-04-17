package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.type.BreadType;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.type.CheeseType;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.type.SauceType;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.type.VegetableType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 서브웨이 샌드위치
 * @author KIYU-IT
 * @date 2025. 4. 17.
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

	    // CSS 스타일 선언
	    html.append("<style>")
	        .append("body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }")
	        .append(".container { max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }")
	        .append(".title { text-align: center; font-size: 22px; font-weight: bold; margin-bottom: 20px; color: #333; }")
	        .append(".section { margin-bottom: 15px; padding: 10px; border-radius: 5px; background: #f9f9f9; }")
	        .append(".section h3 { margin: 0; font-size: 18px; color: #444; border-bottom: 2px solid #ddd; padding-bottom: 5px; }")
	        .append(".section ul { list-style: none; padding: 0; }")
	        .append(".section li { padding: 5px 0; font-size: 16px; color: #555; }")
	        .append(".icon { margin-right: 8px; font-weight: bold; }")
	        .append("</style>");

	    // HTML 컨테이너 시작
	    html.append("<div class='container'>")
	        .append("<div class='title'>🍽️ 주문하신 샌드위치 구성</div>");

	    // 빵 종류
	    html.append("<div class='section'>")
	        .append("<h3>🥖 빵</h3>")
	        .append("<p>").append(bread != null ? bread.getBread() : "선택되지 않음").append("</p>")
	        .append("</div>");

	    // 치즈 종류 및 수량
	    html.append("<div class='section'>")
	        .append("<h3>🧀 치즈</h3>");
	    if (cheeses != null && !cheeses.isEmpty()) {
	        html.append("<ul>");
	        for (Map.Entry<CheeseType, Integer> entry : cheeses.entrySet()) {
	            html.append("<li><span class='icon'>🟡</span>")
	                .append(entry.getKey().getCheese())
	                .append(" - ")
	                .append(entry.getValue())
	                .append("장</li>");
	        }
	        html.append("</ul>");
	    } else {
	        html.append("<p>선택되지 않음</p>");
	    }
	    html.append("</div>");

	    // 야채 목록
	    html.append("<div class='section'>")
	        .append("<h3>🥗 야채</h3>");
	    if (vegetables != null && !vegetables.isEmpty()) {
	        html.append("<ul>");
	        for (VegetableType vegetable : vegetables) {
	            html.append("<li><span class='icon'>🥬</span>").append(vegetable.getVegetable()).append("</li>");
	        }
	        html.append("</ul>");
	    } else {
	        html.append("<p>선택되지 않음</p>");
	    }
	    html.append("</div>");

	    // 소스 목록
	    html.append("<div class='section'>")
	        .append("<h3>🥣 소스</h3>");
	    if (sauces != null && !sauces.isEmpty()) {
	        html.append("<ul>");
	        for (SauceType sauce : sauces) {
	            html.append("<li><span class='icon'>🧂</span>").append(sauce.getSauce()).append("</li>");
	        }
	        html.append("</ul>");
	    } else {
	        html.append("<p>선택되지 않음</p>");
	    }
	    html.append("</div>");

	    // 컨테이너 닫기
	    html.append("</div>");

	    return html.toString();
	}

	/**
     * 샌드위치 구성 요소를 문자열로 반환한다.
     */
	public String toTextSummary() {
	    StringBuilder sb = new StringBuilder();

	    sb.append("🥖 ").append(bread != null ? bread.getBread() : "선택되지 않음").append(" | ");

	    if (cheeses != null && !cheeses.isEmpty()) {
	        sb.append("🧀 ");
	        cheeses.forEach((cheese, qty) -> sb.append(cheese.getCheese()).append(" x").append(qty).append(" "));
	    }

	    if (vegetables != null && !vegetables.isEmpty()) {
	        sb.append("| 🥗 ");
	        vegetables.forEach(veg -> sb.append(veg.getVegetable()).append(" "));
	    }

	    if (sauces != null && !sauces.isEmpty()) {
	        sb.append("| 🥣 ");
	        sauces.forEach(sauce -> sb.append(sauce.getSauce()).append(" "));
	    }

	    return sb.toString().trim();
	}

}
