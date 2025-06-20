package kr.co.kiyu.designpatterns.statepattern.subway.order.state;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.statepattern.subway.order.context.OrderContext;
import kr.co.kiyu.designpatterns.statepattern.subway.order.model.type.OrderStateType;

import lombok.extern.slf4j.Slf4j;

/**
 * 완료 상태
 * 
 * 주문이 성공적으로 완료된 최종 상태
 * 이 상태에서는 더 이상의 상태 전환이나 취소가 불가능하며,
 * 리뷰 작성, 재주문 등의 부가 기능만 가능하다
 * 
 * @author KIYU-IT
 * @date 2025. 1. 23.
 */
@Slf4j
@Component
public class CompletedState implements OrderState {

    @Override
    public boolean nextStep(OrderContext context) {
        log.info("ℹ️ 이미 완료된 주문입니다 - 주문ID: {}", context.getOrder().getOrderId());
        
        // 완료 상태에서는 더 이상 진행할 단계가 없음
        return false;
    }

    @Override
    public boolean cancel(OrderContext context) {
        log.warn("❌ 완료된 주문은 취소할 수 없습니다 - 주문ID: {}", context.getOrder().getOrderId());
        
        // 완료된 주문은 취소 불가능
        return false;
    }

    @Override
    public List<String> getAvailableActions() {
        return Arrays.asList(
            "리뷰 작성하기",
            "재주문하기",
            "주문 내역 확인",
            "영수증 다운로드"
        );
    }

    @Override
    public String getStatusMessage() {
        return "주문이 성공적으로 완료되었습니다. 맛있게 드세요!";
    }

    @Override
    public OrderStateType getStateType() {
        return OrderStateType.COMPLETED;
    }

    @Override
    public int getEstimatedMinutes() {
        return 0; // 완료 상태이므로 0분
    }
} 