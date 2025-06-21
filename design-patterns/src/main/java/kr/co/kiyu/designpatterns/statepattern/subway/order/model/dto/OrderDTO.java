package kr.co.kiyu.designpatterns.statepattern.subway.order.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kiyu.designpatterns.statepattern.subway.order.model.type.OrderStateType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 서브웨이 주문 정보 DTO
 * 
 * 주문의 기본 정보와 현재 상태를 포함하는 데이터 전송 객체
 * State 패턴의 Context에서 상태 정보를 관리하기 위해 사용
 * 
 * @author KIYU-IT
 * @date 2025. 6. 20.
 */
@Schema(description = "서브웨이 주문 정보 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDTO {

    @Schema(description = "주문 ID")
    private String orderId;

    @Schema(description = "고객명")
    private String customerName;

    @Schema(description = "주문 메뉴")
    private String menuName;

    @Schema(description = "주문 수량")
    private int quantity;

    @Schema(description = "주문 금액")
    private int totalPrice;

    @Schema(description = "배달 주소")
    private String deliveryAddress;

    @Schema(description = "고객 연락처")
    private String phoneNumber;

    @Schema(description = "현재 주문 상태")
    private OrderStateType currentState;

    @Schema(description = "주문 생성 시간")
    private LocalDateTime createdAt;

    @Schema(description = "상태 변경 시간")
    private LocalDateTime stateChangedAt;

    @Schema(description = "특별 요청사항")
    private String specialRequest;

    /**
     * 주문 정보를 HTML 형식으로 출력한다.
     * 
     * @return HTML 형식의 주문 정보 문자열
     */
    public String toHtmlContent() {
        StringBuilder html = new StringBuilder();
        
        // CSS 스타일 선언
        html.append("<style>")
            .append("body { font-family: Arial, sans-serif; background-color: #f5f5f5; padding: 20px; }")
            .append(".order-container { max-width: 600px; margin: auto; background: white; padding: 25px; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.15); }")
            .append(".order-header { text-align: center; margin-bottom: 25px; padding-bottom: 15px; border-bottom: 2px solid #00a651; }")
            .append(".order-title { font-size: 24px; font-weight: bold; color: #00a651; margin-bottom: 5px; }")
            .append(".order-id { font-size: 14px; color: #666; }")
            .append(".status-badge { display: inline-block; padding: 8px 16px; border-radius: 20px; font-weight: bold; font-size: 16px; margin: 10px 0; }")
            .append(".status-" + currentState.name().toLowerCase() + " { background-color: " + getStatusColor() + "; color: white; }")
            .append(".info-section { margin: 15px 0; padding: 15px; background: #f9f9f9; border-radius: 8px; }")
            .append(".info-row { display: flex; justify-content: space-between; margin: 8px 0; }")
            .append(".info-label { font-weight: bold; color: #333; }")
            .append(".info-value { color: #555; }")
            .append(".timeline { margin-top: 20px; }")
            .append(".timeline-item { padding: 10px 0; border-left: 3px solid #00a651; padding-left: 15px; margin-left: 10px; }")
            .append("</style>");

        // HTML 컨테이너 시작
        html.append("<div class='order-container'>")
            .append("<div class='order-header'>")
            .append("<div class='order-title'>🍔 서브웨이 주문 정보</div>")
            .append("<div class='order-id'>주문번호: ").append(orderId).append("</div>")
            .append("</div>");

        // 현재 상태 표시
        html.append("<div style='text-align: center;'>")
            .append("<span class='status-badge status-").append(currentState.name().toLowerCase()).append("'>")
            .append(currentState.getEmoji()).append(" ").append(currentState.getStateName())
            .append("</span>")
            .append("</div>");

        // 주문 정보
        html.append("<div class='info-section'>")
            .append("<h3>📋 주문 상세</h3>")
            .append("<div class='info-row'>")
            .append("<span class='info-label'>고객명:</span>")
            .append("<span class='info-value'>").append(customerName).append("</span>")
            .append("</div>")
            .append("<div class='info-row'>")
            .append("<span class='info-label'>메뉴:</span>")
            .append("<span class='info-value'>").append(menuName).append("</span>")
            .append("</div>")
            .append("<div class='info-row'>")
            .append("<span class='info-label'>수량:</span>")
            .append("<span class='info-value'>").append(quantity).append("개</span>")
            .append("</div>")
            .append("<div class='info-row'>")
            .append("<span class='info-label'>총 금액:</span>")
            .append("<span class='info-value'>").append(String.format("%,d", totalPrice)).append("원</span>")
            .append("</div>")
            .append("</div>");

        // 배달 정보
        html.append("<div class='info-section'>")
            .append("<h3>🚗 배달 정보</h3>")
            .append("<div class='info-row'>")
            .append("<span class='info-label'>배달 주소:</span>")
            .append("<span class='info-value'>").append(deliveryAddress).append("</span>")
            .append("</div>")
            .append("<div class='info-row'>")
            .append("<span class='info-label'>연락처:</span>")
            .append("<span class='info-value'>").append(phoneNumber).append("</span>")
            .append("</div>");
        
        if (specialRequest != null && !specialRequest.trim().isEmpty()) {
            html.append("<div class='info-row'>")
                .append("<span class='info-label'>특별 요청:</span>")
                .append("<span class='info-value'>").append(specialRequest).append("</span>")
                .append("</div>");
        }
        html.append("</div>");

        // 시간 정보
        html.append("<div class='info-section'>")
            .append("<h3>⏰ 시간 정보</h3>")
            .append("<div class='info-row'>")
            .append("<span class='info-label'>주문 시간:</span>")
            .append("<span class='info-value'>").append(createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("</span>")
            .append("</div>")
            .append("<div class='info-row'>")
            .append("<span class='info-label'>상태 변경:</span>")
            .append("<span class='info-value'>").append(stateChangedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("</span>")
            .append("</div>")
            .append("</div>");

        html.append("</div>");

        return html.toString();
    }

    /**
     * 현재 상태에 따른 컬러를 반환한다.
     * 
     * @return 상태에 맞는 CSS 컬러 코드
     */
    private String getStatusColor() {
        switch (currentState) {
            case ORDER_RECEIVED: return "#3498db";
            case COOKING: return "#e67e22";
            case PACKAGING: return "#f39c12";
            case DELIVERY: return "#9b59b6";
            case COMPLETED: return "#27ae60";
            case CANCELLED: return "#e74c3c";
            default: return "#95a5a6";
        }
    }
} 