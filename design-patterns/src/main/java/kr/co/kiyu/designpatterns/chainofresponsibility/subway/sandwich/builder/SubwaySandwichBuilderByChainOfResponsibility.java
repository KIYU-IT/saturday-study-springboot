package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.builder;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.command.SandwichCommand;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.command.SandwichCommand.Type;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.handler.*;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.dto.SandwichDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 책임 연쇄 패턴 기반 서브웨이 샌드위치 빌더
 *
 * 이 빌더는 SandwichDirector로부터 전달받은 조립 명령들을 내부 Queue(SandwichCommand)로 수집한 후,
 * Chain of Responsibility 패턴 기반으로 구성된 Handler 체인을 통해 실행
 *
 * 빵, 치즈, 야채, 소스 각각의 핸들러가 정의된 책임에 따라 명령을 처리하며,
 * 각 핸들러는 자신이 처리할 수 없는 명령은 다음 핸들러로 위임
 *
 * [Builder + Chain of Responsibility 패턴의 결합 구조]
 * - Builder: 명령 선언을 위한 Fluent Interface 제공
 * - Chain of Responsibility: 실행 시점의 처리 책임 분리 및 확장 가능성 확보
 *
 * 단일 책임 원칙을 지키며, 명령 추가, 처리 로직 수정이 매우 유연한 구조
 *
 * @author KIYU-IT
 * @date 2025. 4. 17.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SubwaySandwichBuilderByChainOfResponsibility implements SandwichBuilderByChainOfResponsibility {

    // Spring의 의존성 주입(DI)은 타입 기반 + 이름 기반으로 작동하므로 ex: @Component("breadHandler")로 등록된 Bean을 찾아서 주입,
    // @Qualifier("breadHandler")로 이름을 명시적으로 지정하여 주입 또한 가능
    private final SandwichHandler breadHandler;
    private final SandwichHandler cheeseHandler;
    private final SandwichHandler vegetableHandler;
    private final SandwichHandler sauceHandler;

    private final List<SandwichCommand> commandQueue = new ArrayList<>();

    @Override
    public SandwichBuilderByChainOfResponsibility selectBread(String bread) {
        commandQueue.add(new SandwichCommand(Type.BREAD, bread, 1));
        return this;
    }

    @Override
    public SandwichBuilderByChainOfResponsibility addCheese(String cheese, int quantity) {
        commandQueue.add(new SandwichCommand(Type.CHEESE, cheese, quantity));
        return this;
    }

    @Override
    public SandwichBuilderByChainOfResponsibility addVegetable(String vegetable) {
        commandQueue.add(new SandwichCommand(Type.VEGETABLE, vegetable, 1));
        return this;
    }

    @Override
    public SandwichBuilderByChainOfResponsibility addSauce(String sauce) {
        commandQueue.add(new SandwichCommand(Type.SAUCE, sauce, 1));
        return this;
    }

    @Override
    public SandwichDTO build() {
    	log.info("🛠️ 샌드위치 조립 시작 - 총 {}개의 명령 처리 예정", commandQueue.size());

        SandwichDTO sandwich = SandwichDTO.builder()
            .cheeses(new HashMap<>())
            .vegetables(new ArrayList<>())
            .sauces(new HashSet<>())
            .build();

        log.info("🔗 핸들러 체인 구성: Bread → Cheese → Vegetable → Sauce");

        breadHandler.setNext(cheeseHandler)
                    .setNext(vegetableHandler)
                    .setNext(sauceHandler);

        int step = 1;

        for (SandwichCommand command : commandQueue) {
        	log.info("➡️ Step {}: 명령 실행 - [{}] {} x{}", step++, command.getType(), command.getValue(), command.getQuantity());
            breadHandler.handle(command, sandwich);
        }

        log.info("✅ 샌드위치 조립 완료: {}", sandwich.toTextSummary());

        commandQueue.clear();

        return sandwich;
    }

    public SandwichHandler getBreadHandler() {
        return breadHandler;
    }

    public SandwichHandler getCheeseHandler() {
        return cheeseHandler;
    }

    public SandwichHandler getVegetableHandler() {
        return vegetableHandler;
    }

    public SandwichHandler getSauceHandler() {
        return sauceHandler;
    }

}
