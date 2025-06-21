package kr.co.kiyu.designpatterns.statepattern.subway.order.context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kr.co.kiyu.designpatterns.statepattern.subway.order.model.dto.OrderDTO;
import kr.co.kiyu.designpatterns.statepattern.subway.order.model.type.OrderStateType;
import kr.co.kiyu.designpatterns.statepattern.subway.order.state.OrderState;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 주문 컨텍스트
 * 
 * State 패턴의 Context 역할을 담당
 * 현재 주문 상태를 관리하고, 상태 전환을 위한 메서드들을 제공
 * 클라이언트는 이 컨텍스트를 통해 주문 상태를 변경하고 조회할 수 있다
 * 
 * @author KIYU-IT
 * @date 2025. 6. 20.
 */
@Slf4j
@Getter
public class OrderContext {

    /**
     * 주문 정보
     */
    private OrderDTO order;

    /**
     * 현재 상태 객체
     */
    private OrderState currentState;

    /**
     * 상태 변경 이력
     */
    private List<String> stateHistory;

    /**
     * 생성자
     * 
     * @param order 주문 정보
     * @param initialState 초기 상태
     */
    public OrderContext(OrderDTO order, OrderState initialState) {
        this.order = order;
        this.currentState = initialState;
        this.stateHistory = new ArrayList<>();
        
        // 초기 상태 설정
        this.order.setCurrentState(initialState.getStateType());
        this.order.setStateChangedAt(LocalDateTime.now());
        
        // 상태 변경 이력 추가
        addStateHistory("주문 생성", initialState.getStateType());
        
        log.info("🛒 주문 컨텍스트 생성 - 주문ID: {}, 초기상태: {}", 
                order.getOrderId(), initialState.getStateType().getStateName());
    }

    /**
     * 다음 단계로 진행한다.
     * 현재 상태의 nextStep 메서드를 호출하여 상태 전환을 시도한다.
     * 
     * @return 진행 성공 여부
     */
    public boolean nextStep() {
        log.info("➡️ 다음 단계 진행 시도 - 현재 상태: {}", currentState.getStateType().getStateName());
        
        boolean success = currentState.nextStep(this);
        
        if (success) {
            log.info("✅ 단계 진행 성공 - 변경된 상태: {}", currentState.getStateType().getStateName());
        } else {
            log.warn("❌ 단계 진행 실패 - 현재 상태: {}", currentState.getStateType().getStateName());
        }
        
        return success;
    }

    /**
     * 주문을 취소한다.
     * 현재 상태의 cancel 메서드를 호출하여 취소를 시도한다.
     * 
     * @return 취소 성공 여부
     */
    public boolean cancel() {
        log.info("🚫 주문 취소 시도 - 현재 상태: {}", currentState.getStateType().getStateName());
        
        boolean success = currentState.cancel(this);
        
        if (success) {
            log.info("✅ 주문 취소 성공");
        } else {
            log.warn("❌ 주문 취소 실패 - 현재 상태에서 취소할 수 없습니다");
        }
        
        return success;
    }

    /**
     * 상태를 변경한다.
     * 새로운 상태로 컨텍스트를 업데이트하고 주문 정보도 함께 변경한다.
     * 
     * @param newState 새로운 상태 객체
     * @param reason 상태 변경 사유
     */
    public void changeState(OrderState newState, String reason) {
        OrderStateType previousState = currentState.getStateType();
        
        this.currentState = newState;
        this.order.setCurrentState(newState.getStateType());
        this.order.setStateChangedAt(LocalDateTime.now());
        
        // 상태 변경 이력 추가
        addStateHistory(reason, newState.getStateType());
        
        log.info("🔄 상태 변경 완료 - {} → {} (사유: {})", 
                previousState.getStateName(), 
                newState.getStateType().getStateName(), 
                reason);
    }

    /**
     * 현재 상태에서 가능한 액션 목록을 반환한다.
     * 
     * @return 가능한 액션들의 목록
     */
    public List<String> getAvailableActions() {
        return currentState.getAvailableActions();
    }

    /**
     * 현재 상태 메시지를 반환한다.
     * 
     * @return 현재 상태의 설명 메시지
     */
    public String getStatusMessage() {
        return currentState.getStatusMessage();
    }

    /**
     * 예상 완료 시간을 반환한다.
     * 
     * @return 예상 완료 시간(분)
     */
    public int getEstimatedMinutes() {
        return currentState.getEstimatedMinutes();
    }

    /**
     * 현재 상태 타입을 반환한다.
     * 
     * @return 현재 주문 상태 타입
     */
    public OrderStateType getCurrentStateType() {
        return currentState.getStateType();
    }

    /**
     * 상태 변경 이력을 추가한다.
     * 
     * @param reason 변경 사유
     * @param newState 새로운 상태
     */
    private void addStateHistory(String reason, OrderStateType newState) {
        String historyEntry = String.format("[%s] %s → %s", 
                LocalDateTime.now().toString(), 
                reason, 
                newState.getStateName());
        stateHistory.add(historyEntry);
    }
} 