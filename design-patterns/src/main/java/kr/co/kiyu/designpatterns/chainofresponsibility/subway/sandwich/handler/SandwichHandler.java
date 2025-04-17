package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.handler;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.command.SandwichCommand;
import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.dto.SandwichDTO;

/**
 * 샌드위치 조립 책임 연쇄 추상 핸들러
 *
 * 샌드위치 조립 명령을 처리하는 각 핸들러(Bread, Cheese, Vegetable, Sauce 등)의 공통 부모 클래스
 * - `setNext()`를 통해 체인을 구성하고,
 * - `handle()` 메서드를 통해 현재 핸들러가 명령을 처리 가능한지 판단하여 실행하며,
 *   이후 다음 핸들러에게 책임을 위임
 *
 * 각 하위 핸들러 클래스는 `canHandle()`과 `apply()`를 구현하여 자신이 처리할 명령을 정의
 *
 * @author KIYU-IT
 * @date 2025. 4. 17.
 */
public abstract class SandwichHandler {

    protected SandwichHandler next;

    public SandwichHandler setNext(SandwichHandler next) {
        this.next = next;
        return next;
    }

    public void handle(SandwichCommand command, SandwichDTO sandwich) {
        if (canHandle(command)) {
            apply(command, sandwich);
        }
        if (next != null) {
            next.handle(command, sandwich);
        }
    }

    protected abstract boolean canHandle(SandwichCommand command);
    
    protected abstract void apply(SandwichCommand command, SandwichDTO sandwich);

}
