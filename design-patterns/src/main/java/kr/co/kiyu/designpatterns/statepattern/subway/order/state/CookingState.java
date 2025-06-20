package kr.co.kiyu.designpatterns.statepattern.subway.order.state;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.statepattern.subway.order.context.OrderContext;
import kr.co.kiyu.designpatterns.statepattern.subway.order.model.type.OrderStateType;

import lombok.extern.slf4j.Slf4j;

/**
 * 조리 중 상태
 * 
 * 주문이 접수된 후 실제로 샌드위치를 조리하고 있는 상태
 * 이 상태에서는 취소가 제한적이며, 포장 단계로 넘어갈 수 있다
 * 
 * @author KIYU-IT
 * @date 2025. 1. 23.
 */
@Slf4j
@Component
public class CookingState implements OrderState {

    @Autowired
    private PackagingState packagingState;

    @Override
    public boolean nextStep(OrderContext context) {
        log.info("📦 포장 단계로 진행 - 주문ID: {}", context.getOrder().getOrderId());
        
        // 조리 완료 후 포장 단계로 상태 변경
        context.changeState(packagingState, "조리 완료, 포장 시작");
        
        return true;
    }

    @Override
    public boolean cancel(OrderContext context) {
        log.warn("⚠️ 조리 중 상태에서는 취소가 제한됩니다 - 주문ID: {}", context.getOrder().getOrderId());
        
        // 조리 중에는 일반적으로 취소가 어려움
        // 특별한 사유가 있는 경우에만 취소 가능하도록 제한
        return false;
    }

    @Override
    public List<String> getAvailableActions() {
        return Arrays.asList(
            "다음 단계 진행 (포장 시작)",
            "조리 상태 확인",
            "예상 시간 조회"
        );
    }

    @Override
    public String getStatusMessage() {
        return "맛있는 샌드위치를 조리하고 있습니다. 조금만 기다려 주세요!";
    }

    @Override
    public OrderStateType getStateType() {
        return OrderStateType.COOKING;
    }

    @Override
    public int getEstimatedMinutes() {
        return 15; // 조리 완료까지 약 15분 예상
    }
} 