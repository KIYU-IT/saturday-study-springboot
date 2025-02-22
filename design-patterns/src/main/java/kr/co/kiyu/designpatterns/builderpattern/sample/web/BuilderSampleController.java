package kr.co.kiyu.designpatterns.builderpattern.sample.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kiyu.designpatterns.builderpattern.sample.service.BuilderSampleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 빌더 패턴 - 샘플 API
 *
 * @author KIYU-IT
 * @date 2025. 2. 22.
 */
@Tag(name = "빌더 패턴 샘플 API", description = "빌더 패턴 샘플 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/designPatterns/builder/sample")
public class BuilderSampleController {

	private final BuilderSampleService builderSampleService;

	@Operation(summary = "빌더 패턴 샘플 TextBuilder 실행", description = "빌더 패턴 샘플 TextBuilder 실행")
	@GetMapping("/textBuild")
	public String textBuild(HttpServletRequest request) {
        return this.builderSampleService.initTextBuilder();
	}

	@Operation(summary = "빌더 패턴 샘플 HtmlBuilder 실행", description = "빌더 패턴 샘플 HtmlBuilder 실행")
	@GetMapping("/htmlBuild")
	public String htmlBuild(HttpServletRequest request) {
        return this.builderSampleService.initHtmlBuilder();
	}

}
