package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.handler;

import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.command.SandwichCommand;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.dto.SandwichDTO;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.type.SauceType;

import lombok.extern.slf4j.Slf4j;

/**
 * 소스 추가 핸들러
 *
 * 샌드위치 주문 과정에서 소스 타입의 명령을 처리하는 책임을 가진 핸들러
 * 소스 타입 명령이 들어오면 해당 소스를 샌드위치에 추가하고, 이후 체인의 다음 핸들러로 책임을 전달
 *
 * @author KIYU-IT
 * @date 2025. 4. 17.
 */
@Slf4j
@Component
public class SauceHandler extends SandwichHandler {

    @Override
    protected boolean canHandle(SandwichCommand command) {
        return command.getType() == SandwichCommand.Type.SAUCE;
    }

    @Override
    protected void apply(SandwichCommand command, SandwichDTO sandwich) {
    	try {
            SauceType sauce = SauceType.valueOf(command.getValue().toUpperCase());

            log.info("🧂 [{}] 소스 선택 중...", sauce.getSauce());
            Thread.sleep(300);

            log.info("🥄 [{}] 소스 뿌리는 중...", sauce.getSauce());
            Thread.sleep(300);

            sandwich.getSauces().add(sauce);

            log.info("✅ [{}] 소스 추가 완료!", sauce.getSauce());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("⚠️ 소스 처리 중 인터럽트 발생");
        }

    }

}
