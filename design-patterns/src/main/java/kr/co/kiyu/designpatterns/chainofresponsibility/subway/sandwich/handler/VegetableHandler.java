
package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.handler;

import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.command.SandwichCommand;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.dto.SandwichDTO;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.type.VegetableType;

import lombok.extern.slf4j.Slf4j;

/**
 * 야채 추가 핸들러
 *
 * 샌드위치 주문 과정에서 야채 타입의 명령을 처리하는 책임을 가진 핸들러
 * 야채 타입 명령이 들어오면 해당 야채를 샌드위치에 추가하고, 이후 체인의 다음 핸들러로 책임을 전달
 *
 * @author KIYU-IT
 * @date 2025. 4. 17.
 */
@Slf4j
@Component
public class VegetableHandler extends SandwichHandler {

    @Override
    protected boolean canHandle(SandwichCommand command) {
        return command.getType() == SandwichCommand.Type.VEGETABLE;
    }

    @Override
    protected void apply(SandwichCommand command, SandwichDTO sandwich) {
    	try {
            VegetableType vegetable = VegetableType.valueOf(command.getValue().toUpperCase());

            log.info("🥬 [{}] 야채 준비 중...", vegetable.getVegetable());
            Thread.sleep(400);

            log.info("💦 [{}] 야채 세척 중...", vegetable.getVegetable());
            Thread.sleep(500);

            log.info("🌿 [{}] 샌드위치에 토핑 중...", vegetable.getVegetable());
            Thread.sleep(500);

            sandwich.getVegetables().add(vegetable);

            log.info("✅ [{}] 야채 추가 완료!", vegetable.getVegetable());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("⚠️ 야채 처리 중 인터럽트 발생");
        }

    }

}
