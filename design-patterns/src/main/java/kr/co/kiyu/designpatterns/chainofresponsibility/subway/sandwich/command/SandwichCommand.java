package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.command;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 샌드위치 조립 명령 객체
 *
 * Builder에서 수집한 사용자의 샌드위치 구성 요청을 표현하는 명령 객체
 * 이 객체는 체인으로 구성된 핸들러에게 전달되어,
 * 각 핸들러가 자신이 책임지는 유형(Type)에 해당하는 명령만을 처리
 *
 * 구성 요소:
 * - type     : 명령의 유형 (빵, 치즈, 야채, 소스)
 * - value    : 추가할 재료의 이름
 * - quantity : 수량 (치즈 등 일부 재료는 수량 정보 포함)
 *
 * 이 클래스는 데이터 중심의 Command 객체이며, Chain of Responsibility 패턴 내에서
 * 핸들러 간 명확한 메시지 단위 역할을 수행
 *
 * @see kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.handler.SandwichHandler
 * @author KIYU-IT
 * @date 2025. 4. 17.
 */
@Data
@AllArgsConstructor
public class SandwichCommand {

    public enum Type { BREAD, CHEESE, VEGETABLE, SAUCE }

    Type type;

    String value;

    int quantity;

}
