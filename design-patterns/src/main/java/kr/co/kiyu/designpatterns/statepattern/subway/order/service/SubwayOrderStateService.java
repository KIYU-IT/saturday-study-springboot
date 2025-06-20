package kr.co.kiyu.designpatterns.statepattern.subway.order.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import kr.co.kiyu.designpatterns.statepattern.subway.order.context.OrderContext;
import kr.co.kiyu.designpatterns.statepattern.subway.order.model.dto.OrderDTO;
import kr.co.kiyu.designpatterns.statepattern.subway.order.model.type.OrderStateType;
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
 * @author KIYU-IT
 * @date 2025. 1. 23.
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
     * @param orderId 주문 ID
     * @return 진행 결과 HTML
     */
    public String nextStep(String orderId) {
        OrderContext context = getOrderContext(orderId);
        if (context == null) {
            return createErrorHtml("주문을 찾을 수 없습니다: " + orderId);
        }

        log.info("➡️ 주문 다음 단계 진행 - 주문ID: {}", orderId);
        
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
     * @param orderId 주문 ID
     * @return 취소 결과 HTML
     */
    public String cancelOrder(String orderId) {
        OrderContext context = getOrderContext(orderId);
        if (context == null) {
            return createErrorHtml("주문을 찾을 수 없습니다: " + orderId);
        }

        log.info("🚫 주문 취소 시도 - 주문ID: {}", orderId);
        
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
            "김지유",
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