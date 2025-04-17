package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.handler;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.command.SandwichCommand;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.dto.SandwichDTO;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.type.BreadType;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 빵 추가 핸들러
 *
 * 샌드위치 주문 과정에서 빵 타입의 명령을 처리하는 책임을 가진 핸들러
 * 빵 타입 명령이 들어오면 해당 빵를 샌드위치에 추가하고, 이후 체인의 다음 핸들러로 책임을 전달
 *
 * @author KIYU-IT
 * @date 2025. 4. 17.
 */
@Slf4j
@Component
public class BreadHandler extends SandwichHandler {

    @Override
    protected boolean canHandle(SandwichCommand command) {
        return command.getType() == SandwichCommand.Type.BREAD;
    }

    @Override
    protected void apply(SandwichCommand command, SandwichDTO sandwich) {
    	try {
            String breadName = command.getValue().toUpperCase();
            BreadType bread = BreadType.valueOf(breadName);

            log.info("🥖 [{}] 빵 선택 중...", bread.getBread());
            Thread.sleep(800);

            log.info("🔥 [{}] 빵 굽는 중...", bread.getBread());
            Thread.sleep(1200);

            sandwich.setBread(bread);

            log.info("✅ [{}] 빵 선택 및 조리 완료!", bread.getBread());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("⚠️ 빵 처리 중 인터럽트 발생");
        }
    }

}
