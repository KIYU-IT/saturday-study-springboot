package kr.co.kiyu.designpatterns.builderpattern.sample.service;

import org.springframework.stereotype.Service;

import kr.co.kiyu.designpatterns.builderpattern.sample.util.Director;
import kr.co.kiyu.designpatterns.builderpattern.sample.util.HTMLBuilder;
import kr.co.kiyu.designpatterns.builderpattern.sample.util.TextBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 빌더 패턴 샘플 서비스
 *
 * @author KIYU-IT
 * @date 2025. 2. 21.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BuilderSampleService {
	private final TextBuilder textBuilder;

	private final HTMLBuilder htmlBuilder;

	public String initTextBuilder() {
		Director director = new Director(this.textBuilder);

		director.construct();

        String result = this.textBuilder.getResult();

        System.out.println(result);

        return result;
	}

	public String initHtmlBuilder() {
		Director director = new Director(this.htmlBuilder);

		director.construct();

        String filename = this.htmlBuilder.getResult();

        String htmlContent = this.htmlBuilder.getHtmlContent();

        System.out.println(filename + "파일이 작성되었습니다.");

        return htmlContent;
	}

	public static void usage() {
        log.info("java Main plain 일반 텍스트로 문서작성");
        log.info("java Main html  HTML 파일로 문서작성");
    }

}
