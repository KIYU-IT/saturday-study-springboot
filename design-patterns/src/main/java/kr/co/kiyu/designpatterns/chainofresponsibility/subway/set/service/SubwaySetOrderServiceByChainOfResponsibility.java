package kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.service;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.handler.SandwichOrderHandler;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.context.OrderContext;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.handler.DrinkHandler;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.command.SetOrderCommand;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.handler.DessertHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

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
    	long start = System.currentTimeMillis();

        // 체인 구성
        sandwichHandler.setNext(drinkHandler).setNext(dessertHandler);

        // Context 생성
        OrderContext context = OrderContext.builder()
                .menuName(command.getMenuName())
                .build();

        log.info("📦 세트 조립 시작 - 메뉴: {}", command.getMenuName());

        sandwichHandler.handle(context);

        log.info("✅ 세트 조립 완료");
        log.info("⏱️ [동기] 전체 소요 시간: {}ms", System.currentTimeMillis() - start);

        return toHtml(context);
    }

    public String buildSetAsync(SetOrderCommand command) {
    	long start = System.currentTimeMillis();

        // Context 생성
        OrderContext context = OrderContext.builder()
                .menuName(command.getMenuName())
                .build();

        log.info("📦 동시에 세트 조립 시작 - 메뉴: {}", command.getMenuName());

        // 1. 샌드위치는 동기 처리
        sandwichHandler.handle(context);

        // 2. 음료와 디저트는 병렬 비동기 처리
        CompletableFuture<Void> drinkFuture = drinkHandler.handleAsync(context);
        CompletableFuture<Void> dessertFuture = dessertHandler.handleAsync(context);

        // 3. 모든 작업이 완료될 때까지 대기
        CompletableFuture.allOf(drinkFuture, dessertFuture).join();

        log.info("✅ 동시에 세트 조립 완료");
        log.info("⏱️ [비동기] 전체 소요 시간: {}ms", System.currentTimeMillis() - start);

        return toHtml(context);
    }

    private String toHtml(OrderContext context) {
        StringBuilder html = new StringBuilder();

        html.append("<div style='font-family: Arial; line-height: 1.6;'>")
            .append("<h2>🍱 세트 구성</h2>")
            .append("<p>🥪 샌드위치: ").append(context.getSandwich().toTextSummary()).append("</p>")
            .append("<p>🥤 음료: ")
            .append(context.getDrink() != null ? context.getDrink().getDrink() : "선택되지 않음")
            .append("<p>🍪 디저트: ")
            .append(context.getDessert() != null ? context.getDessert().getDessert() : "선택되지 않음")
            .append("</div>");

        return html.toString();
    }

}
