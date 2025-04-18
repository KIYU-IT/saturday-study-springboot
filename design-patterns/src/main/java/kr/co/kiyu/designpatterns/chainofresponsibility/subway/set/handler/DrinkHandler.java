package kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.context.OrderContext;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.type.DrinkType;

/**
 * 음료 처리 핸들러
 *
 * 세트 주문에 기본 음료를 포함시키는 역할
 * (기본값: 코카콜라)
 *
 * @author KIYU-IT
 * @date 2025. 4. 18.
 */
@Slf4j
@Component("drinkHandler")
public class DrinkHandler extends OrderHandler {

    @Override
    protected void process(OrderContext context) {
        DrinkType drink = DrinkType.COKE;

        context.setDrink(drink);

        log.info("🥤 음료 제공 완료: {}", drink.getDrink());
    }

}
