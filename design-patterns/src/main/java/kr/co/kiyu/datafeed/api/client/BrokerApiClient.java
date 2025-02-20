package kr.co.kiyu.datafeed.api.client;

import kr.co.kiyu.common.broker.BrokerType;
import kr.co.kiyu.datafeed.kafka.KafkaMessageHandler;

/**
 * 데이터피드 - 브로커 클라이언트
 * @author KIYU-IT
 * @date 2025. 2. 11.
 */
public interface BrokerApiClient {

	/**
	 * 브로커 타입 조회
	 */
	public BrokerType getBrokerType();

	/**
     * 브로커 초기화
     */
    public void onInit();

    /**
     * 브로커 종료
     */
    public void onDestroy();

    /**
     * 카프카 메시지 전송
     */
    public default void sendToKafka(KafkaMessageHandler kafkaMessageHandler, String topic, Object data) {
        kafkaMessageHandler.sendMessage(topic, data);
    }

}