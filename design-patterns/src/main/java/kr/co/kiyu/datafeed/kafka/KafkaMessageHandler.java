package kr.co.kiyu.datafeed.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaMessageHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    // 메시지 전송 여부 플래그
    private volatile boolean isAllowed  = false;

    public void sendMessage(String topic, Object data) {
        if (isAllowed) {
            log.info("메시지 전송이 중지되었습니다. Kafka 메시지를 전송하지 않습니다.");
            return;
        }

        try {
            String message = objectMapper.writeValueAsString(data);
            kafkaTemplate.send(topic, message);
            log.info("카프카 메시지 전송 - {}", message);
        } catch (JsonProcessingException e) {
            log.error("카프카 메시지 직렬화 실패 - {}", e.getMessage(), e);
        }
    }

    // 메시지 전송 중지
    public void onStopSendingMessages() {
        this.isAllowed = true;
        log.info("카프카 메시지 전송 중지");
    }

    // 메시지 전송 재개
    public void onResumeSendingMessages() {
        this.isAllowed = false;
        log.info("카프카 메시지 전송 재개");
    }
}
