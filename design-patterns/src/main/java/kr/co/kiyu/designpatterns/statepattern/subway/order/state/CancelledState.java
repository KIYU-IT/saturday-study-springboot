package kr.co.kiyu.designpatterns.statepattern.subway.order.state;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.statepattern.subway.order.context.OrderContext;
import kr.co.kiyu.designpatterns.statepattern.subway.order.model.type.OrderStateType;

import lombok.extern.slf4j.Slf4j;

/**
 * 취소 상태
 * 
 * 주문이 취소된 최종 상태
 * 이 상태에서는 더 이상의 상태 전환이 불가능하며,
 * 환불 처리, 재주문 등의 부가 기능만 가능하다
 * 
 * @author KIYU-IT
 * @date 2025. 6. 20.
 */
@Slf4j
@Component
public class CancelledState implements OrderState {

    @Override
    public boolean nextStep(OrderContext context) {
        log.info("ℹ️ 취소된 주문은 진행할 수 없습니다 - 주문ID: {}", context.getOrder().getOrderId());
        
        // 취소 상태에서는 더 이상 진행할 단계가 없음
        return false;
    }

    @Override
    public boolean cancel(OrderContext context) {
        log.info("ℹ️ 이미 취소된 주문입니다 - 주문ID: {}", context.getOrder().getOrderId());
        
        // 이미 취소된 상태
        return true;
    }

    @Override
    public List<String> getAvailableActions() {
        return Arrays.asList(
            "환불 처리 확인",
            "취소 사유 확인",
            "재주문하기",
            "고객센터 문의"
        );
    }

    @Override
    public String getStatusMessage() {
        return "주문이 취소되었습니다. 환불 처리가 진행됩니다.";
    }

    @Override
    public OrderStateType getStateType() {
        return OrderStateType.CANCELLED;
    }

    @Override
    public int getEstimatedMinutes() {
        return 0; // 취소 상태이므로 0분
    }
} 