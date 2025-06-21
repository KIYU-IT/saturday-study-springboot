package kr.co.kiyu.designpatterns.statepattern.subway.order.state;

import java.util.List;

import kr.co.kiyu.designpatterns.statepattern.subway.order.context.OrderContext;
import kr.co.kiyu.designpatterns.statepattern.subway.order.model.type.OrderStateType;

/**
 * 주문 상태 인터페이스
 * 
 * State 패턴의 State 인터페이스 역할
 * 각 상태에서 수행할 수 있는 행동들을 정의하며,
 * 구체적인 상태 클래스들이 이 인터페이스를 구현하여
 * 상태별로 다른 행동을 수행한다
 * 
 * @author KIYU-IT
 * @date 2025. 6. 20.
 */
public interface OrderState {

    /**
     * 다음 단계로 진행한다.
     * 각 상태마다 다음 단계가 다르므로 구체적인 구현은 각 상태 클래스에서 수행
     * 
     * @param context 주문 컨텍스트 정보
     * @return 다음 단계 진행 성공 여부
     */
    boolean nextStep(OrderContext context);

    /**
     * 주문을 취소한다.
     * 모든 상태에서 취소가 가능한 것은 아니므로 각 상태별로 구현 필요
     * 
     * @param context 주문 컨텍스트 정보
     * @return 취소 성공 여부
     */
    boolean cancel(OrderContext context);

    /**
     * 현재 상태에서 가능한 액션 목록을 반환한다.
     * 
     * @return 가능한 액션들의 목록
     */
    List<String> getAvailableActions();

    /**
     * 현재 상태의 상태 메시지를 반환한다.
     * 
     * @return 상태 설명 메시지
     */
    String getStatusMessage();

    /**
     * 현재 상태 타입을 반환한다.
     * 
     * @return 주문 상태 타입
     */
    OrderStateType getStateType();

    /**
     * 예상 완료 시간(분)을 반환한다.
     * 각 상태별로 완료까지 걸리는 예상 시간이 다름
     * 
     * @return 예상 완료 시간(분)
     */
    int getEstimatedMinutes();
} 