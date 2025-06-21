package kr.co.kiyu.designpatterns.statepattern.subway.order.model.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 주문 상태 타입
 * 
 * 서브웨이 주문의 진행 상태를 나타내는 열거형
 * 주문 접수부터 완료까지의 전체 라이프사이클을 정의
 * 
 * @author KIYU-IT
 * @date 2025. 6. 20.
 */
@Schema(description = "주문 상태 타입")
public enum OrderStateType {
    
    ORDER_RECEIVED("주문 접수", "고객의 주문이 접수되었습니다", "🛒"),
    COOKING("조리 중", "샌드위치를 조리하고 있습니다", "🍳"),
    PACKAGING("포장 중", "주문을 포장하고 있습니다", "📦"),
    DELIVERY("배달 중", "주문이 배달 중입니다", "🚗"),
    COMPLETED("완료", "주문이 완료되었습니다", "✅"),
    CANCELLED("취소", "주문이 취소되었습니다", "❌");

    @Getter
    private final String stateName;
    
    @Getter
    private final String description;
    
    @Getter
    private final String emoji;

    OrderStateType(String stateName, String description, String emoji) {
        this.stateName = stateName;
        this.description = description;
        this.emoji = emoji;
    }
} 