package kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.context.OrderContext;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.type.DessertType;

/**
 * 디저트 처리 핸들러
 *
 * 세트 주문 메뉴명에 따라 디저트를 자동 선택한다.
 * - SNACK 포함 시: 스낵
 * - 기본: 쿠키
 *
 * @author KIYU-IT
 * @date 2025. 4. 18.
 */
@Slf4j
@Component("dessertHandler")
public class DessertHandler extends OrderHandler {

	@Override
    protected void process(OrderContext context) {
        // 메뉴에 SNACK 키워드가 있으면 스낵, 아니면 쿠키로 결정
        DessertType dessert = context.getMenuName().contains("SNACK")
                ? DessertType.CHEESEBALL_SNACK
                : DessertType.DOUBLE_CHOCOLATE_COOKIE;

        context.setDessert(dessert);

        log.info("🍪 디저트 제공 완료: {}", dessert.getDessert());
    }

}
