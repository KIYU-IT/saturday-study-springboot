package kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.command;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 세트 주문 명령 객체
 *
 * 사용자가 요청한 메뉴 이름을 기반으로 세트 조립 명령을 전달
 * 해당 명령은 핸들러 체인을 통해 샌드위치 → 음료 → 디저트 순으로 처리
 *
 * @author KIYU-IT
 * @date 2025. 4. 18.
 */
@Data
@AllArgsConstructor
public class SetOrderCommand {

    private String menuName;

}
