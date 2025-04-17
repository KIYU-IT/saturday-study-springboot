package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.service;

import org.springframework.stereotype.Service;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.builder.SandwichDirectorByChainOfResponsibility;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.builder.SubwaySandwichBuilderByChainOfResponsibility;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.dto.SandwichDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 책임 연쇄 패턴 - 서브웨이 샌드위치 서비스
 *
 * @author KIYU-IT
 * @date 2025. 2. 21.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SubwaySandwichServiceByChainOfResponsibility {

	private final SandwichDirectorByChainOfResponsibility sandwichDirector;
	private final SubwaySandwichBuilderByChainOfResponsibility subwaySandwichBuilder;

	public String buildChickenTeriyaki() {
        SandwichDTO sandwich = sandwichDirector.buildChickenTeriyaki(subwaySandwichBuilder);
        return sandwich.toHtmlContent();
    }

	public String buildVeggieDelight() {
        SandwichDTO sandwich = sandwichDirector.buildVeggieDelight(subwaySandwichBuilder);
        return sandwich.toHtmlContent();
    }

	public String buildSpicyItalian() {
	    SandwichDTO sandwich = sandwichDirector.buildSpicyItalian(subwaySandwichBuilder);
	    return sandwich.toHtmlContent();
	}

	public String buildChickenBaconAvocado() {
	    SandwichDTO sandwich = sandwichDirector.buildChickenBaconAvocado(subwaySandwichBuilder);
	    return sandwich.toHtmlContent();
	}

	public String buildPulledPorkBBQ() {
	    SandwichDTO sandwich = sandwichDirector.buildPulledPorkBBQ(subwaySandwichBuilder);
	    return sandwich.toHtmlContent();
	}

	public String buildShrimp() {
	    SandwichDTO sandwich = sandwichDirector.buildShrimp(subwaySandwichBuilder);
	    return sandwich.toHtmlContent();
	}

}
