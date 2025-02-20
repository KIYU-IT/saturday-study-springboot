package kr.co.kiyu.designpatterns;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 디자인패턴 애플리케이션 생명주기
 * @author KIYU-IT
 * @date 2025. 2. 20.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DesignPatternsApplicationLifeCycle implements ApplicationRunner {

    /**
	 * Spring Bean이 생성된 후 실행
	 */
    @PostConstruct
    public void onInit() {

    }

    /**
	 * 애플리케이션이 완전히 시작된 후 실행
	 */
    @Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("디자인패턴의 세계로!!!");
	}

    /**
	 * Spring 애플리케이션이 종료되기 전에 호출
	 */
    @PreDestroy
    public void onDestroy() {

    }
}
