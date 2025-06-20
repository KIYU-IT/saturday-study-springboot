package kr.co.kiyu.designpatterns.statepattern.subway.order.order;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kiyu.designpatterns.statepattern.subway.order.service.SubwayOrderStateService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * State 패턴 - 서브웨이 주문 상태 관리 컨트롤러
 * 
 * 주문의 상태 변화를 State 패턴으로 관리하는 REST API 엔드포인트
 * 주문 생성, 상태 전환, 취소, 조회 등의 기능을 제공한다
 * 
 * 실생활 예제: 서브웨이 주문 시스템
 * - 주문 접수 → 조리 중 → 포장 중 → 배달 중 → 완료
 * - 각 상태별로 다른 행동과 제약사항을 가짐
 * 
 * @author KIYU-IT
 * @date 2025. 1. 23.
 */
@Tag(name = "State Pattern API", description = "상태 패턴을 활용한 서브웨이 주문 상태 관리 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/state/order")
public class SubwayOrderStateController {

    private final SubwayOrderStateService orderStateService;

    @Operation(
        summary = "샘플 주문 생성",
        description = "미리 정의된 샘플 주문을 생성하여 State 패턴의 동작을 확인할 수 있습니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "샘플 주문 생성 성공")
        }
    )
    @PostMapping(value = "/sample", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> createSampleOrder() {
        log.info("🎯 State 패턴 - 샘플 주문 생성 요청");
        
        String result = orderStateService.createSampleOrder();
        
        log.info("✅ 샘플 주문 생성 완료");
        return ResponseEntity.ok(result);
    }

    @Operation(
        summary = "커스텀 주문 생성",
        description = "사용자가 직접 입력한 정보로 새로운 주문을 생성합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "주문 생성 성공")
        }
    )
    @PostMapping(value = "/create", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> createOrder(
            @Parameter(description = "고객명", example = "김지유") @RequestParam String customerName,
            @Parameter(description = "메뉴명", example = "치킨 데리야끼") @RequestParam String menuName,
            @Parameter(description = "수량", example = "1") @RequestParam(defaultValue = "1") int quantity,
            @Parameter(description = "총 금액", example = "8500") @RequestParam int totalPrice,
            @Parameter(description = "배달 주소", example = "서울시 강남구 테헤란로 123") @RequestParam String deliveryAddress,
            @Parameter(description = "연락처", example = "010-1234-5678") @RequestParam String phoneNumber,
            @Parameter(description = "특별 요청사항", example = "피클 빼주세요") @RequestParam(required = false) String specialRequest) {
        
        log.info("🛒 State 패턴 - 커스텀 주문 생성 요청 - 고객: {}, 메뉴: {}", customerName, menuName);
        
        String result = orderStateService.createOrder(
                customerName, menuName, quantity, totalPrice, 
                deliveryAddress, phoneNumber, specialRequest);
        
        log.info("✅ 커스텀 주문 생성 완료");
        return ResponseEntity.ok(result);
    }

    @Operation(
        summary = "주문 다음 단계 진행",
        description = "현재 상태에서 다음 단계로 주문을 진행합니다. State 패턴의 핵심 기능입니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "다음 단계 진행 성공"),
            @ApiResponse(responseCode = "400", description = "진행할 수 없는 상태")
        }
    )
    @PutMapping(value = "/{orderId}/next-step", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> nextStep(
            @Parameter(description = "주문 ID", example = "ORD-1737623456789") @PathVariable String orderId) {
        
        log.info("➡️ State 패턴 - 다음 단계 진행 요청 - 주문ID: {}", orderId);
        
        String result = orderStateService.nextStep(orderId);
        
        log.info("✅ 다음 단계 진행 처리 완료");
        return ResponseEntity.ok(result);
    }

    @Operation(
        summary = "주문 취소",
        description = "주문을 취소합니다. 상태에 따라 취소 가능 여부가 결정됩니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "취소 성공"),
            @ApiResponse(responseCode = "400", description = "취소할 수 없는 상태")
        }
    )
    @PutMapping(value = "/{orderId}/cancel", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> cancelOrder(
            @Parameter(description = "주문 ID", example = "ORD-1737623456789") @PathVariable String orderId) {
        
        log.info("🚫 State 패턴 - 주문 취소 요청 - 주문ID: {}", orderId);
        
        String result = orderStateService.cancelOrder(orderId);
        
        log.info("✅ 주문 취소 처리 완료");
        return ResponseEntity.ok(result);
    }

    @Operation(
        summary = "주문 상태 조회",
        description = "현재 주문의 상태와 상세 정보를 조회합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음")
        }
    )
    @GetMapping(value = "/{orderId}/status", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getOrderStatus(
            @Parameter(description = "주문 ID", example = "ORD-1737623456789") @PathVariable String orderId) {
        
        log.info("📋 State 패턴 - 주문 상태 조회 요청 - 주문ID: {}", orderId);
        
        String result = orderStateService.getOrderStatus(orderId);
        
        log.info("✅ 주문 상태 조회 완료");
        return ResponseEntity.ok(result);
    }

    @Operation(
        summary = "사용 가능한 액션 조회",
        description = "현재 상태에서 수행할 수 있는 액션 목록을 조회합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "액션 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음")
        }
    )
    @GetMapping(value = "/{orderId}/actions", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getAvailableActions(
            @Parameter(description = "주문 ID", example = "ORD-1737623456789") @PathVariable String orderId) {
        
        log.info("🎯 State 패턴 - 가능 액션 조회 요청 - 주문ID: {}", orderId);
        
        String result = orderStateService.getAvailableActions(orderId);
        
        log.info("✅ 가능 액션 조회 완료");
        return ResponseEntity.ok(result);
    }

    @Operation(
        summary = "패턴 사용법 안내",
        description = "State 패턴의 사용법과 API 호출 순서를 안내합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "사용법 안내 성공")
        }
    )
    @GetMapping(value = "/guide", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getUsageGuide() {
        log.info("📚 State 패턴 - 사용법 안내 요청");
        
        String guide = createUsageGuideHtml();
        
        log.info("✅ 사용법 안내 완료");
        return ResponseEntity.ok(guide);
    }

    /**
     * State 패턴 사용법 안내 HTML을 생성한다.
     * 
     * @return 사용법 안내 HTML
     */
    private String createUsageGuideHtml() {
        return "<div style='font-family: Arial; padding: 20px; line-height: 1.6;'>" +
               "<h1>🎯 State Pattern 사용법 안내</h1>" +
               "<h2>📋 API 호출 순서</h2>" +
               "<ol>" +
               "<li><strong>주문 생성:</strong> POST /api/state/order/sample 또는 /create</li>" +
               "<li><strong>주문 진행:</strong> PUT /api/state/order/{orderId}/next-step (여러 번 호출)</li>" +
               "<li><strong>상태 확인:</strong> GET /api/state/order/{orderId}/status</li>" +
               "<li><strong>가능 액션:</strong> GET /api/state/order/{orderId}/actions</li>" +
               "</ol>" +
               "<h2>🔄 상태 전환 흐름</h2>" +
               "<p>🛒 주문 접수 → 🍳 조리 중 → 📦 포장 중 → 🚗 배달 중 → ✅ 완료</p>" +
               "<h2>⚠️ 제약사항</h2>" +
               "<ul>" +
               "<li>주문 접수 상태에서만 취소 가능</li>" +
               "<li>조리 중부터는 취소 제한</li>" +
               "<li>각 상태별로 다른 액션 제공</li>" +
               "</ul>" +
               "<h2>🧪 테스트 방법</h2>" +
               "<p>1. 먼저 샘플 주문을 생성하세요</p>" +
               "<p>2. 응답에서 주문 ID를 확인하세요</p>" +
               "<p>3. next-step을 여러 번 호출하여 상태 변화를 확인하세요</p>" +
               "</div>";
    }
} 