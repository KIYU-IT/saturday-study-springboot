package kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.service;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.handler.SandwichOrderHandler;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.context.OrderContext;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.handler.DrinkHandler;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.command.SetOrderCommand;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.handler.DessertHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 * 책임 연쇄 패턴 - 서브웨이 세트 주문 서비스
 *
 * SetOrderCommand를 기반으로 핸들러 체인을 통해 세트 구성 요소들을 조립한다.
 *
 * @author KIYU-IT
 * @date 2025. 4. 18.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SubwaySetOrderServiceByChainOfResponsibility {

    private final SandwichOrderHandler sandwichHandler;
    private final DrinkHandler drinkHandler;
    private final DessertHandler dessertHandler;

    public String buildSet(SetOrderCommand command) {
        // 체인 구성
        sandwichHandler.setNext(drinkHandler).setNext(dessertHandler);

        // Context 생성
        OrderContext context = OrderContext.builder()
                .menuName(command.getMenuName())
                .build();

        log.info("📦 세트 조립 시작 - 메뉴: {}", command.getMenuName());

        sandwichHandler.handle(context);
        
        log.info("✅ 세트 조립 완료");

        return toHtml(context);
    }

    private String toHtml(OrderContext context) {
        StringBuilder html = new StringBuilder();

        html.append("<div style='font-family: Arial; line-height: 1.6;'>")
            .append("<h2>🍱 세트 구성</h2>")
            .append("<p>🥪 샌드위치: ").append(context.getSandwich().toTextSummary()).append("</p>")
            .append("<p>🥤 음료: ").append(context.getDrink().getDrink()).append("</p>")
            .append("<p>🍪 디저트: ").append(context.getDessert().getDessert()).append("</p>")
            .append("</div>");

        return html.toString();
    }

}
