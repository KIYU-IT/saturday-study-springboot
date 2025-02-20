package kr.co.kiyu.datafeed.api.client.bybit;

import kr.co.kiyu.common.broker.BrokerType;
import kr.co.kiyu.datafeed.api.client.BrokerApiClient;
import kr.co.kiyu.datafeed.api.client.bybit.utils.BybitApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 데이터피드 - Bybit 클라이언트 구현체
 * 과거 BTCUSDT 가격 데이터를 가져오는 역할
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BybitApiClient implements BrokerApiClient {

    @Override
    public BrokerType getBrokerType() {
        return BrokerType.BYBIT;
    }

    @Override
    public void onInit() {
        log.info("Bybit 과거 시장 데이터 요청 시작");
        this.getHistoricalKlineData();
    }

    @Override
    public void onDestroy() {
        log.info("Bybit 과거 시장 데이터 요청 종료");
    }

    /**
     * 특정 기간의 BTCUSDT 1시간봉 데이터 조회
     * @return 캔들 데이터 리스트
     */
    public Object getHistoricalKlineData() {
        return BybitApiUtils.getKlineDataWithoutHeaders(
                "BTCUSDT",
                "60",
                1735689600000L, // 2025-02-01T00:00:00Z (밀리초)
                System.currentTimeMillis()
        );
    }

    /**
     * 최근 N년간 BTCUSDT 가격 데이터 조회
     * @param years 조회할 연도
     * @return 캔들 데이터 리스트
     */
    public Object getLastNYearsKlineData(int years) {
        return BybitApiUtils.getLastNYearsKlineDataWithoutHeaders("BTCUSDT", "60", years);
    }
}
