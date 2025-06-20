package kr.co.kiyu.designpatterns.statepattern.subway.order.state;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.statepattern.subway.order.context.OrderContext;
import kr.co.kiyu.designpatterns.statepattern.subway.order.model.type.OrderStateType;

import lombok.extern.slf4j.Slf4j;

/**
 * 주문 접수 상태
 * 
 * 고객이 주문을 완료한 직후의 상태
 * 이 상태에서는 주문 취소, 수정, 결제가 가능하며
 * 조리 단계로 넘어갈 수 있다
 * 
 * @author KIYU-IT
 * @date 2025. 1. 23.
 */
@Slf4j
@Component
public class OrderReceivedState implements OrderState {

    @Autowired
    private CookingState cookingState;

    @Autowired  
    private CancelledState cancelledState;

    @Override
    public boolean nextStep(OrderContext context) {
        log.info("🍳 조리 단계로 진행 - 주문ID: {}", context.getOrder().getOrderId());
        
        // 조리 단계로 상태 변경
        context.changeState(cookingState, "조리 시작");
        
        return true;
    }

    @Override
    public boolean cancel(OrderContext context) {
        log.info("🚫 주문 취소 처리 - 주문ID: {}", context.getOrder().getOrderId());
        
        // 취소 상태로 변경
        context.changeState(cancelledState, "고객 요청으로 취소");
        
        return true;
    }

    @Override
    public List<String> getAvailableActions() {
        return Arrays.asList(
            "다음 단계 진행 (조리 시작)",
            "주문 취소",
            "주문 수정",
            "결제 정보 확인"
        );
    }

    @Override
    public String getStatusMessage() {
        return "고객님의 주문이 접수되었습니다. 곧 조리를 시작하겠습니다.";
    }

    @Override
    public OrderStateType getStateType() {
        return OrderStateType.ORDER_RECEIVED;
    }

    @Override
    public int getEstimatedMinutes() {
        return 25; // 접수 후 완료까지 약 25분 예상
    }
} 