package kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.service.SubwaySandwichService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 빌더 패턴 - 서브웨이 샌드위치 주문 API
 *
 * @author KIYU-IT
 * @date 2025. 2. 22.
 */
@Tag(name = "서브웨이 샌드위치 주문 API", description = "서브웨이 샌드위치 주문 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/designPatterns/builder/subway/sandwich")
public class SubwaySandwichOrder {

	private final SubwaySandwichService subwaySandwichService;

	@Operation(summary = "서브웨이 샌드위치 - 치킨데리야끼 주문", description = "서브웨이 샌드위치 - 치킨데리야끼 주문")
	@GetMapping("/order/chickenTeriyaki")
	public String chickenTeriyaki(HttpServletRequest request) {
        return this.subwaySandwichService.buildChickenTeriyaki();
	}

	@Operation(summary = "서브웨이 샌드위치 - 베지딜라이트 주문", description = "서브웨이 샌드위치 - 베지딜라이트 주문")
	@GetMapping("/order/veggieDelight")
	public String veggieDelight(HttpServletRequest request) {
        return this.subwaySandwichService.buildVeggieDelight();
	}

}
