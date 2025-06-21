package kr.co.kiyu.designpatterns.statepattern.subway.order.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import kr.co.kiyu.designpatterns.statepattern.subway.order.context.OrderContext;
import kr.co.kiyu.designpatterns.statepattern.subway.order.model.dto.OrderDTO;
import kr.co.kiyu.designpatterns.statepattern.subway.order.state.OrderReceivedState;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * State 패턴 - 서브웨이 주문 상태 관리 서비스
 * 
 * 주문의 생명주기를 State 패턴으로 관리하는 서비스
 * 각 상태별로 다른 행동을 수행할 수 있으며,
 * 상태 전환 로직을 캡슐화하여 관리한다
 * 
 * ⚠️ State 패턴을 사용하지 않았다면?
 * 
 * 만약 State 패턴을 사용하지 않았다면 다음과 같은 복잡한 조건문들이 필요했을 것입니다:
 * 
 * // ❌ State 패턴 없이 구현했다면 (안티 패턴 예시)
 * public String nextStep(String orderId) {
 *     OrderDTO order = getOrder(orderId);
 *     
 *     if (order.getCurrentState() == OrderStateType.RECEIVED) {
 *         // 주문 접수 → 조리 중
 *         order.setCurrentState(OrderStateType.COOKING);
 *         updateEstimatedTime(order, 15);
 *         sendNotification(order, "조리를 시작합니다");
 *         
 *     } else if (order.getCurrentState() == OrderStateType.COOKING) {
 *         // 조리 중 → 포장 중
 *         order.setCurrentState(OrderStateType.PACKAGING);
 *         updateEstimatedTime(order, 10);
 *         sendNotification(order, "포장을 시작합니다");
 *         
 *     } else if (order.getCurrentState() == OrderStateType.PACKAGING) {
 *         // 포장 중 → 배달 중
 *         order.setCurrentState(OrderStateType.DELIVERY);
 *         updateEstimatedTime(order, 0);
 *         sendNotification(order, "배달을 시작합니다");
 *         assignDeliveryDriver(order);
 *         
 *     } else if (order.getCurrentState() == OrderStateType.DELIVERY) {
 *         // 배달 중 → 완료
 *         order.setCurrentState(OrderStateType.COMPLETED);
 *         updateEstimatedTime(order, 0);
 *         sendNotification(order, "배달이 완료되었습니다");
 *         processPayment(order);
 *         
 *     } else if (order.getCurrentState() == OrderStateType.COMPLETED) {
 *         throw new IllegalStateException("이미 완료된 주문입니다");
 *         
 *     } else if (order.getCurrentState() == OrderStateType.CANCELLED) {
 *         throw new IllegalStateException("취소된 주문은 진행할 수 없습니다");
 *     }
 *     
 *     return order.toHtmlContent();
 * }
 * 
 * public String cancelOrder(String orderId) {
 *     OrderDTO order = getOrder(orderId);
 *     
 *     if (order.getCurrentState() == OrderStateType.RECEIVED) {
 *         // 주문 접수 상태에서만 취소 가능
 *         order.setCurrentState(OrderStateType.CANCELLED);
 *         processRefund(order);
 *         sendNotification(order, "주문이 취소되었습니다");
 *         return order.toHtmlContent();
 *         
 *     } else if (order.getCurrentState() == OrderStateType.COOKING) {
 *         // 조리 중에는 취소 불가
 *         throw new IllegalStateException("조리 중인 주문은 취소할 수 없습니다");
 *         
 *     } else if (order.getCurrentState() == OrderStateType.PACKAGING) {
 *         // 포장 중에는 취소 불가
 *         throw new IllegalStateException("포장 중인 주문은 취소할 수 없습니다");
 *         
 *     } else if (order.getCurrentState() == OrderStateType.DELIVERY) {
 *         // 배달 중에는 취소 불가
 *         throw new IllegalStateException("배달 중인 주문은 취소할 수 없습니다");
 *         
 *     } else if (order.getCurrentState() == OrderStateType.COMPLETED) {
 *         throw new IllegalStateException("완료된 주문은 취소할 수 없습니다");
 *         
 *     } else if (order.getCurrentState() == OrderStateType.CANCELLED) {
 *         throw new IllegalStateException("이미 취소된 주문입니다");
 *     }
 * }
 * 
 * public List<String> getAvailableActions(String orderId) {
 *     OrderDTO order = getOrder(orderId);
 *     List<String> actions = new ArrayList<>();
 *     
 *     if (order.getCurrentState() == OrderStateType.RECEIVED) {
 *         actions.add("조리 시작");
 *         actions.add("주문 취소");
 *         actions.add("주문 수정");
 *         
 *     } else if (order.getCurrentState() == OrderStateType.COOKING) {
 *         actions.add("포장 시작");
 *         actions.add("조리 상태 확인");
 *         
 *     } else if (order.getCurrentState() == OrderStateType.PACKAGING) {
 *         actions.add("배달 시작");
 *         actions.add("포장 확인");
 *         
 *     } else if (order.getCurrentState() == OrderStateType.DELIVERY) {
 *         actions.add("배달 완료");
 *         actions.add("위치 추적");
 *         actions.add("고객 연락");
 *         
 *     } else if (order.getCurrentState() == OrderStateType.COMPLETED) {
 *         actions.add("리뷰 작성");
 *         actions.add("재주문");
 *         
 *     } else if (order.getCurrentState() == OrderStateType.CANCELLED) {
 *         actions.add("환불 확인");
 *     }
 *     
 *     return actions;
 * }
 * 
 * ❌ 이런 방식의 문제점들:
 * 1. 모든 메서드에 동일한 if-else 구조 반복
 * 2. 새로운 상태 추가 시 모든 메서드 수정 필요
 * 3. 상태별 로직이 여러 곳에 분산되어 유지보수 어려움
 * 4. 코드 중복과 복잡성 증가
 * 5. 테스트 케이스 작성 복잡
 * 6. 상태 전환 규칙이 명확하지 않음
 * 
 * ✅ State 패턴 사용 시의 장점:
 * 1. 각 상태의 로직이 해당 State 클래스에 캡슐화
 * 2. 새로운 상태 추가 시 새로운 State 클래스만 생성
 * 3. 상태별 독립적인 테스트 가능
 * 4. 코드 가독성과 유지보수성 향상
 * 5. 상태 전환 규칙이 명확하게 정의됨
 * 
 * @author KIYU-IT
 * @date 2025. 6. 20.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SubwayOrderStateService {

    private final OrderReceivedState orderReceivedState;
    
    // 메모리 내 주문 저장소 (실제 환경에서는 DB 사용)
    private final Map<String, OrderContext> orderStorage = new ConcurrentHashMap<>();

    /**
     * 새로운 주문을 생성한다.
     * 
     * @param customerName 고객명
     * @param menuName 메뉴명
     * @param quantity 수량
     * @param totalPrice 총 금액
     * @param deliveryAddress 배달 주소
     * @param phoneNumber 연락처
     * @param specialRequest 특별 요청사항
     * @return 생성된 주문 정보 HTML
     */
    public String createOrder(String customerName, String menuName, int quantity, 
                            int totalPrice, String deliveryAddress, String phoneNumber, 
                            String specialRequest) {
        
        // 주문 ID 생성
        String orderId = generateOrderId();
        
        log.info("🛒 새로운 주문 생성 시작 - 주문ID: {}, 고객: {}", orderId, customerName);
        
        // 주문 DTO 생성
        OrderDTO order = OrderDTO.builder()
                .orderId(orderId)
                .customerName(customerName)
                .menuName(menuName)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .deliveryAddress(deliveryAddress)
                .phoneNumber(phoneNumber)
                .specialRequest(specialRequest)
                .createdAt(LocalDateTime.now())
                .build();
        
        // 주문 컨텍스트 생성 (초기 상태: ORDER_RECEIVED)
        OrderContext orderContext = new OrderContext(order, orderReceivedState);
        
        // 메모리에 저장
        orderStorage.put(orderId, orderContext);
        
        log.info("✅ 주문 생성 완료 - 주문ID: {}", orderId);
        
        return orderContext.getOrder().toHtmlContent();
    }

    /**
     * 주문을 다음 단계로 진행한다.
     * 
     * 🎯 State 패턴의 핵심! 
     * 현재 상태 객체가 스스로 다음 상태로 전환을 처리한다.
     * 복잡한 if-else 조건문 없이 context.nextStep() 한 줄로 해결!
     * 
     * ❌ State 패턴 없이 구현했다면:
     * if (currentState == RECEIVED) { 
     *     setState(COOKING); 
     * } else if (currentState == COOKING) { 
     *     setState(PACKAGING); 
     * } else if...
     * 
     * ✅ State 패턴 사용:
     * context.nextStep(); // 현재 상태가 알아서 처리!
     * 
     * @param orderId 주문 ID
     * @return 진행 결과 HTML
     */
    public String nextStep(String orderId) {
        OrderContext context = getOrderContext(orderId);
        if (context == null) {
            return createErrorHtml("주문을 찾을 수 없습니다: " + orderId);
        }

        log.info("➡️ 주문 다음 단계 진행 - 주문ID: {}", orderId);
        
        // 🎯 State 패턴의 핵심: 현재 상태 객체가 스스로 다음 상태를 결정!
        boolean success = context.nextStep();
        
        if (success) {
            log.info("✅ 다음 단계 진행 성공 - 주문ID: {}, 현재상태: {}", 
                    orderId, context.getCurrentStateType().getStateName());
            return context.getOrder().toHtmlContent();
        } else {
            log.warn("❌ 다음 단계 진행 실패 - 주문ID: {}", orderId);
            return createErrorHtml("다음 단계로 진행할 수 없습니다.");
        }
    }

    /**
     * 주문을 취소한다.
     * 
     * 🎯 State 패턴의 핵심 2!
     * 각 상태별로 다른 취소 정책을 적용한다.
     * - 주문 접수: 취소 가능
     * - 조리 중: 취소 불가
     * - 포장/배달: 취소 불가
     * 
     * ❌ State 패턴 없이 구현했다면:
     * if (currentState == RECEIVED) {
     *     return true; // 취소 가능
     * } else if (currentState == COOKING || currentState == PACKAGING) {
     *     return false; // 취소 불가
     * } else if...
     * 
     * ✅ State 패턴 사용:
     * context.cancel(); // 각 상태가 자신의 취소 정책 적용!
     * 
     * @param orderId 주문 ID
     * @return 취소 결과 HTML
     */
    public String cancelOrder(String orderId) {
        OrderContext context = getOrderContext(orderId);
        if (context == null) {
            return createErrorHtml("주문을 찾을 수 없습니다: " + orderId);
        }

        log.info("🚫 주문 취소 시도 - 주문ID: {}", orderId);
        
        // 🎯 State 패턴의 핵심: 각 상태가 자신만의 취소 정책을 가짐!
        boolean success = context.cancel();
        
        if (success) {
            log.info("✅ 주문 취소 성공 - 주문ID: {}", orderId);
            return context.getOrder().toHtmlContent();
        } else {
            log.warn("❌ 주문 취소 실패 - 주문ID: {}", orderId);
            return createErrorHtml("현재 상태에서는 주문을 취소할 수 없습니다.");
        }
    }

    /**
     * 주문 상태를 조회한다.
     * 
     * @param orderId 주문 ID
     * @return 주문 상태 HTML
     */
    public String getOrderStatus(String orderId) {
        OrderContext context = getOrderContext(orderId);
        if (context == null) {
            return createErrorHtml("주문을 찾을 수 없습니다: " + orderId);
        }

        log.info("📋 주문 상태 조회 - 주문ID: {}", orderId);
        
        return context.getOrder().toHtmlContent();
    }

    /**
     * 주문의 가능한 액션 목록을 조회한다.
     * 
     * 🎯 State 패턴의 핵심 3!
     * 각 상태별로 다른 액션 목록을 제공한다.
     * - 주문 접수: [조리 시작, 주문 취소, 주문 수정]
     * - 조리 중: [포장 시작, 조리 상태 확인]
     * - 완료: [리뷰 작성, 재주문]
     * 
     * ❌ State 패턴 없이 구현했다면:
     * List<String> actions = new ArrayList<>();
     * if (currentState == RECEIVED) {
     *     actions.add("조리 시작");
     *     actions.add("주문 취소");
     * } else if (currentState == COOKING) {
     *     actions.add("포장 시작");
     * } else if...
     * 
     * ✅ State 패턴 사용:
     * context.getAvailableActions(); // 각 상태가 자신만의 액션 목록 반환!
     * 
     * @param orderId 주문 ID
     * @return 액션 목록 HTML
     */
    public String getAvailableActions(String orderId) {
        OrderContext context = getOrderContext(orderId);
        if (context == null) {
            return createErrorHtml("주문을 찾을 수 없습니다: " + orderId);
        }

        log.info("🎯 주문 가능 액션 조회 - 주문ID: {}", orderId);
        
        StringBuilder html = new StringBuilder();
        html.append("<div style='font-family: Arial; padding: 20px;'>")
            .append("<h2>🎯 사용 가능한 액션</h2>")
            .append("<p><strong>주문 ID:</strong> ").append(orderId).append("</p>")
            .append("<p><strong>현재 상태:</strong> ").append(context.getCurrentStateType().getStateName()).append("</p>")
            .append("<ul>");
        
        // 🎯 State 패턴의 핵심: 각 상태가 자신만의 액션 목록을 정의!
        for (String action : context.getAvailableActions()) {
            html.append("<li>").append(action).append("</li>");
        }
        
        html.append("</ul>")
            .append("<p><strong>예상 완료 시간:</strong> ").append(context.getEstimatedMinutes()).append("분</p>")
            .append("</div>");
        
        return html.toString();
    }

    /**
     * 샘플 주문을 생성한다.
     * 
     * @return 샘플 주문 HTML
     */
    public String createSampleOrder() {
        return createOrder(
            "홍길동",
            "치킨 데리야끼",
            1,
            8500,
            "서울시 강남구 테헤란로 123",
            "010-1234-5678",
            "피클 빼주세요"
        );
    }

    /**
     * 주문 컨텍스트를 조회한다.
     * 
     * @param orderId 주문 ID
     * @return 주문 컨텍스트 또는 null
     */
    private OrderContext getOrderContext(String orderId) {
        return orderStorage.get(orderId);
    }

    /**
     * 주문 ID를 생성한다.
     * 
     * @return 생성된 주문 ID
     */
    private String generateOrderId() {
        return "ORD-" + System.currentTimeMillis();
    }

    /**
     * 오류 메시지 HTML을 생성한다.
     * 
     * @param message 오류 메시지
     * @return 오류 HTML
     */
    private String createErrorHtml(String message) {
        return "<div style='font-family: Arial; padding: 20px; color: red;'>" +
               "<h2>❌ 오류</h2>" +
               "<p>" + message + "</p>" +
               "</div>";
    }
} 