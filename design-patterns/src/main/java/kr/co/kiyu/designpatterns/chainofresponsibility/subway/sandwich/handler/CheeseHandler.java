package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.handler;

import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.command.SandwichCommand;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.dto.SandwichDTO;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.type.CheeseType;

import lombok.extern.slf4j.Slf4j;

/**
 * 치즈 추가 핸들러
 *
 * 샌드위치 주문 과정에서 치즈 타입의 명령을 처리하는 책임을 가진 핸들러
 * 치즈 타입 명령이 들어오면 해당 치즈를 샌드위치에 추가하고, 이후 체인의 다음 핸들러로 책임을 전달
 *
 * @author KIYU-IT
 * @date 2025. 4. 17.
 */
@Slf4j
@Component
public class CheeseHandler extends SandwichHandler {

    @Override
    protected boolean canHandle(SandwichCommand command) {
        return command.getType() == SandwichCommand.Type.CHEESE;
    }

    @Override
    protected void apply(SandwichCommand command, SandwichDTO sandwich) {
    	try {
            CheeseType cheese = CheeseType.valueOf(command.getValue().toUpperCase());
            int quantity = command.getQuantity();

            log.info("🧀 [{}] 치즈 선택 중...", cheese.getCheese());
            Thread.sleep(600);

            log.info("🔪 [{}] 치즈 슬라이스 중... ({}장)", cheese.getCheese(), quantity);
            Thread.sleep(800);

            sandwich.getCheeses().put(cheese, sandwich.getCheeses().getOrDefault(cheese, 0) + quantity);

            log.info("✅ [{}] 치즈 추가 완료!", cheese.getCheese());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("⚠️ 치즈 처리 중 인터럽트 발생");
        }
    }

}
