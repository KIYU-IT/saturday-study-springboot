package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.service.SubwaySandwichServiceByChainOfResponsibility;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 책임 연쇄 패턴 - 서브웨이 샌드위치 주문 API
 *
 * @author KIYU-IT
 * @date 2025. 4. 17.
 */
@Tag(name = "서브웨이 샌드위치 주문 API", description = "책임 연쇄 패턴을 사용한 서브웨이 샌드위치 주문 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/designPatterns/chainofresponsibility/subway/sandwich")
public class SubwaySandwichOrderByChainOfResponsibility {

	private final SubwaySandwichServiceByChainOfResponsibility subwaySandwichService;

	@Operation(summary = "서브웨이 샌드위치 - 치킨데리야끼 주문", description = "서브웨이 샌드위치 - 치킨데리야끼, 책임 연쇄 패턴을 사용하여 주문")
	@GetMapping("/order/chickenTeriyaki")
	public String chickenTeriyaki(HttpServletRequest request) {
        return this.subwaySandwichService.buildChickenTeriyaki();
	}

	@Operation(summary = "서브웨이 샌드위치 - 베지딜라이트 주문", description = "서브웨이 샌드위치 - 베지딜라이트, 책임 연쇄 패턴을 사용하여 주문")
	@GetMapping("/order/veggieDelight")
	public String veggieDelight(HttpServletRequest request) {
        return this.subwaySandwichService.buildVeggieDelight();
	}

	@Operation(summary = "서브웨이 샌드위치 - 스파이시 이탈리안 주문", description = "서브웨이 샌드위치 - 스파이시 이탈리안, 책임 연쇄 패턴을 사용하여 주문")
	@GetMapping("/order/spicyItalian")
	public String spicyItalian(HttpServletRequest request) {
	    return this.subwaySandwichService.buildSpicyItalian();
	}

	@Operation(summary = "서브웨이 샌드위치 - 치킨 베이컨 아보카도 주문", description = "서브웨이 샌드위치 - 치킨 베이컨 아보카도, 책임 연쇄 패턴을 사용하여 주문")
	@GetMapping("/order/chickenBaconAvocado")
	public String chickenBaconAvocado(HttpServletRequest request) {
	    return this.subwaySandwichService.buildChickenBaconAvocado();
	}

	@Operation(summary = "서브웨이 샌드위치 - 풀드 포크 바비큐 주문", description = "서브웨이 샌드위치 - 풀드 포크 바비큐, 책임 연쇄 패턴을 사용하여 주문")
	@GetMapping("/order/pulledPorkBBQ")
	public String pulledPorkBBQ(HttpServletRequest request) {
	    return this.subwaySandwichService.buildPulledPorkBBQ();
	}

	@Operation(summary = "서브웨이 샌드위치 - 쉬림프 주문", description = "서브웨이 샌드위치 - 쉬림프, 책임 연쇄 패턴을 사용하여 주문")
	@GetMapping("/order/shrimp")
	public String shrimp(HttpServletRequest request) {
	    return this.subwaySandwichService.buildShrimp();
	}

}
