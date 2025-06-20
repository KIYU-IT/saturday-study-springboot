package kr.co.kiyu.designpatterns.statepattern.subway.order.state;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.statepattern.subway.order.context.OrderContext;
import kr.co.kiyu.designpatterns.statepattern.subway.order.model.type.OrderStateType;

import lombok.extern.slf4j.Slf4j;

/**
 * 배달 중 상태
 * 
 * 포장이 완료된 후 고객에게 배달 중인 상태
 * 이 상태에서는 취소가 불가능하며, 완료 단계로 넘어갈 수 있다
 * 
 * @author KIYU-IT
 * @date 2025. 1. 23.
 */
@Slf4j
@Component
public class DeliveryState implements OrderState {

    @Autowired
    private CompletedState completedState;

    @Override
    public boolean nextStep(OrderContext context) {
        log.info("✅ 배달 완료 - 주문ID: {}", context.getOrder().getOrderId());
        
        // 배달 완료 후 완료 상태로 변경
        context.changeState(completedState, "배달 완료");
        
        return true;
    }

    @Override
    public boolean cancel(OrderContext context) {
        log.warn("❌ 배달 중 상태에서는 취소가 불가능합니다 - 주문ID: {}", context.getOrder().getOrderId());
        
        // 배달 중에는 취소 불가능
        return false;
    }

    @Override
    public List<String> getAvailableActions() {
        return Arrays.asList(
            "다음 단계 진행 (배달 완료)",
            "배달 현황 추적",
            "배달기사 연락하기",
            "고객 연락하기"
        );
    }

    @Override
    public String getStatusMessage() {
        return "주문이 배달 중입니다. 곧 고객님께 도착할 예정입니다.";
    }

    @Override
    public OrderStateType getStateType() {
        return OrderStateType.DELIVERY;
    }

    @Override
    public int getEstimatedMinutes() {
        return 0; // 이미 마지막 단계이므로 0분
    }
} 