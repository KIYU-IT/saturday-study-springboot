package kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.service;

import org.springframework.stereotype.Service;

import kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.builder.SandwichDirector;
import kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.builder.SubwaySandwichBuilder;
import kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.model.dto.SandwichDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 빌더 패턴 - 서브웨이 샌드위치 서비스
 *
 * @author KIYU-IT
 * @date 2025. 2. 21.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SubwaySandwichService {

	private final SandwichDirector sandwichDirector;
	private final SubwaySandwichBuilder subwaySandwichBuilder;

	public String buildSandwich() {

        // 샌드위치 조합 생성 (예: 치킨 데리야끼 샌드위치)
        SandwichDTO sandwich = sandwichDirector.buildChickenTeriyaki(subwaySandwichBuilder);

        return sandwich.toHtmlContent();
    }

}
