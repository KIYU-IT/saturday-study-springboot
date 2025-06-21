package kr.co.kiyu.designpatterns.statepattern.subway.order.state;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.statepattern.subway.order.context.OrderContext;
import kr.co.kiyu.designpatterns.statepattern.subway.order.model.type.OrderStateType;

import lombok.extern.slf4j.Slf4j;

/**
 * 포장 중 상태
 * 
 * 조리가 완료된 후 주문을 포장하고 있는 상태
 * 이 상태에서는 취소가 불가능하며, 배달 단계로 넘어갈 수 있다
 * 
 * @author KIYU-IT
 * @date 2025. 6. 20.
 */
@Slf4j
@Component
public class PackagingState implements OrderState {

    @Autowired
    private DeliveryState deliveryState;

    @Override
    public boolean nextStep(OrderContext context) {
        log.info("🚗 배달 단계로 진행 - 주문ID: {}", context.getOrder().getOrderId());
        
        // 포장 완료 후 배달 단계로 상태 변경
        context.changeState(deliveryState, "포장 완료, 배달 시작");
        
        return true;
    }

    @Override
    public boolean cancel(OrderContext context) {
        log.warn("❌ 포장 중 상태에서는 취소가 불가능합니다 - 주문ID: {}", context.getOrder().getOrderId());
        
        // 포장 중에는 취소 불가능
        return false;
    }

    @Override
    public List<String> getAvailableActions() {
        return Arrays.asList(
            "다음 단계 진행 (배달 시작)",
            "포장 상태 확인",
            "배달 준비 상태 확인"
        );
    }

    @Override
    public String getStatusMessage() {
        return "주문을 정성스럽게 포장하고 있습니다. 곧 배달을 시작하겠습니다.";
    }

    @Override
    public OrderStateType getStateType() {
        return OrderStateType.PACKAGING;
    }

    @Override
    public int getEstimatedMinutes() {
        return 10; // 포장 완료 후 배달까지 약 10분 예상
    }
} 