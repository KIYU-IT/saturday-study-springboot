package kr.co.kiyu.datafeed.api.client.bybit.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import kr.co.kiyu.common.rest.ApiUtils;
import kr.co.kiyu.datafeed.api.client.bybit.dto.BybitKlineResponseDTO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

/**
 * Bybit API 전용 유틸리티 클래스
 * - Bybit 과거 가격 데이터 요청 기능 추가
 */
@Slf4j
public class BybitApiUtils extends ApiUtils {

    private static final String API_BASE_URL = "https://api.bybit.com";
    private static final RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory()); // ✅ RestTemplate 인스턴스 생성

    /**
     * Bybit 전용 GET 요청 메서드 (헤더 없이 요청 가능)
     *
     * @param url API 엔드포인트
     * @param returnType 응답 데이터 타입
     * @return 응답 결과
     */
    public static <T> T get(String url, Class<T> returnType) {
        try {
            URI uri = new URI(url);
            HttpHeaders headers = new HttpHeaders(); // ✅ 빈 헤더 사용
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);

            log.info("Bybit API 요청: {}", uri);

            return restTemplate.exchange(uri, HttpMethod.GET, requestEntity, returnType).getBody();
        } catch (URISyntaxException e) {
            log.error("Bybit API URI 변환 오류: {}", url, e);
            return null;
        } catch (Exception e) {
            log.error("Bybit API 요청 실패: {}", url, e);
            return null;
        }
    }

    /**
     * 특정 기간의 Bybit Kline 데이터 조회 (헤더 없이 요청 가능)
     *
     * @param symbol 심볼 (예: BTCUSDT)
     * @param interval 주기 (예: "60" -> 1시간봉)
     * @param startTimestamp 시작 시간 (밀리초 단위)
     * @param endTimestamp 종료 시간 (밀리초 단위)
     * @return 캔들 데이터 리스트
     */
    public static Object getKlineDataWithoutHeaders(String symbol, String interval, long startTimestamp, long endTimestamp) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(API_BASE_URL + "/v5/market/kline")
                    .queryParam("category", "linear")
                    .queryParam("symbol", symbol)
                    .queryParam("interval", interval)
                    .queryParam("start", startTimestamp)
                    .queryParam("end", endTimestamp)
                    .build()
                    .toUri();

            log.info("Bybit Kline API 요청: {}", uri);

            // ✅ 헤더 없이 요청 가능하도록 ApiUtils.get()을 오버로드하여 사용
            BybitKlineResponseDTO response = getWithoutHeaders(uri.toString(), BybitKlineResponseDTO.class);

            if (response != null && response.getResult() != null) {
                log.info("Bybit Kline 데이터 수신 완료 -> {}", response.getResult());
                return response.getResult();
            } else {
                log.warn("Bybit Kline 데이터 조회 실패");
                return List.of();
            }
        } catch (JsonProcessingException | URISyntaxException e) {
            log.error("Bybit Kline API 요청 중 오류 발생", e);
            return List.of();
        }
    }

    /**
     * 최근 N년간 Bybit Kline 데이터 조회 (헤더 없이 요청 가능)
     *
     * @param symbol 심볼 (예: BTCUSDT)
     * @param interval 주기 (예: "60" -> 1시간봉)
     * @param years 조회 기간 (예: 1년, 2년)
     * @return 캔들 데이터 리스트
     */
    public static Object getLastNYearsKlineDataWithoutHeaders(String symbol, String interval, int years) {
        long startTimestamp = Instant.now().minusSeconds(years * 365L * 24 * 60 * 60).toEpochMilli();
        long endTimestamp = Instant.now().toEpochMilli();

        return getKlineDataWithoutHeaders(symbol, interval, startTimestamp, endTimestamp);
    }

    /**
     * API 요청 (헤더 없이)
     *
     * @param url 요청할 URL
     * @param returnType 응답 객체 타입
     * @return API 응답 결과
     */
    public static <T> T getWithoutHeaders(String url, Class<T> returnType) throws JsonMappingException, JsonProcessingException, URISyntaxException {
        return get(url, returnType); // ✅ 헤더 없이 빈 HttpHeaders 사용
    }
}
