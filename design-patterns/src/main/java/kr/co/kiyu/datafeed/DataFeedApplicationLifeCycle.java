package kr.co.kiyu.datafeed;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import kr.co.kiyu.datafeed.api.client.bybit.BybitApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 데이터피드 애플리케이션 생명주기
 * @author KIYU-IT
 * @date 2025. 2. 11.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataFeedApplicationLifeCycle {

	private final BybitApiClient api = new BybitApiClient();

    /**
	 * Spring Bean이 생성된 후 실행
	 */
    @PostConstruct
    public void onInit() {
    	this.api.onInit();
    }

    /**
	 * Spring 애플리케이션이 종료되기 전에 호출
	 */
    @PreDestroy
    public void onDestroy() {

    }
}
