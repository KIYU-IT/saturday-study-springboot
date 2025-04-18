package kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.handler;

import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.builder.SandwichDirectorByChainOfResponsibility;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.builder.SubwaySandwichBuilderByChainOfResponsibility;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.context.OrderContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 샌드위치 주문 핸들러
 *
 * 세트 주문에서 샌드위치 구성 명령을 처리하며,
 * 내부적으로 기존 빌더 체인을 호출
 *
 * [ex] CHICKEN_TERIYAKI_SET, SPICY_ITALIAN_SET 등
 *
 * @author KIYU-IT
 * @date 2025. 4. 18.
 */
@Slf4j
@RequiredArgsConstructor
@Component("sandwichOrderHandler")
public class SandwichOrderHandler extends OrderHandler {

    private final SandwichDirectorByChainOfResponsibility director;
    private final SubwaySandwichBuilderByChainOfResponsibility builder;

    @Override
    protected void process(OrderContext context) {
        log.info("🥪 샌드위치 준비 시작 - 메뉴명: {}", context.getMenuName());

        switch (context.getMenuName()) {
            case "CHICKEN_TERIYAKI_SET" -> context.setSandwich(director.buildChickenTeriyaki(builder));
            case "SPICY_ITALIAN_SET" -> context.setSandwich(director.buildSpicyItalian(builder));
            case "VEGGIE_DELIGHT_SET" -> context.setSandwich(director.buildVeggieDelight(builder));
            case "SHRIMP_SET" -> context.setSandwich(director.buildShrimp(builder));
            case "BBQ_PULLED_PORK_SET" -> context.setSandwich(director.buildPulledPorkBBQ(builder));
            default -> throw new IllegalArgumentException("지원하지 않는 메뉴: " + context.getMenuName());
        }

        log.info("✅ 샌드위치 준비 완료: {}", context.getSandwich().toTextSummary());
    }

}
