package kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.context;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.dto.SandwichDTO;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.type.DessertType;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.type.DrinkType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 서브웨이 세트 주문 컨텍스트
 *
 * 샌드위치, 음료, 디저트, 결제 정보 등
 * 전체 주문 흐름을 공유하는 상태 객체
 *
 * @author KIYU-IT
 * @date 2025. 4. 18.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderContext {

    private String menuName;

    private SandwichDTO sandwich;

    private DrinkType drink;

    private DessertType dessert;

    private int totalPrice;

}
