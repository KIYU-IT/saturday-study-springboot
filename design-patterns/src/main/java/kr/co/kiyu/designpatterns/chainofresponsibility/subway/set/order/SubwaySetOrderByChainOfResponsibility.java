package kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.command.SetOrderCommand;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.service.SubwaySetOrderServiceByChainOfResponsibility;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

/**
 * 책임 연쇄 패턴 - 서브웨이 세트 주문 API
 *
 * @author KIYU-IT
 * @date 2025. 4. 18.
 */
@Tag(name = "서브웨이 세트 주문 API", description = "책임 연쇄 패턴을 사용한 세트 주문 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/designPatterns/chainofresponsibility/subway/set")
public class SubwaySetOrderByChainOfResponsibility {

    private final SubwaySetOrderServiceByChainOfResponsibility service;

    @Operation(summary = "세트 주문 - 치킨데리야끼", description = "치킨데리야끼 세트 구성")
    @GetMapping("/order/chickenTeriyaki")
    public String chickenTeriyaki(HttpServletRequest request) {
        return service.buildSet(new SetOrderCommand("CHICKEN_TERIYAKI_SET"));
    }

    @Operation(summary = "세트 주문 - 스파이시 이탈리안", description = "스파이시 이탈리안 세트 구성")
    @GetMapping("/order/spicyItalian")
    public String spicyItalian(HttpServletRequest request) {
        return service.buildSet(new SetOrderCommand("SPICY_ITALIAN_SET"));
    }

}
