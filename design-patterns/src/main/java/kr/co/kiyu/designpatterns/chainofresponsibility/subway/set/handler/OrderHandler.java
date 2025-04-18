package kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.handler;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.context.OrderContext;

/**
 * 세트 주문의 상위 책임 연쇄 핸들러
 *
 * 각 핸들러는 자신이 처리할 수 있는 로직만 처리하고,
 * 나머지는 다음 체인으로 위임
 *
 * [Handler 구성]
 * - SandwichOrderHandler
 * - DrinkHandler
 * - DessertHandler
 * - PaymentHandler
 *
 * @author KIYU-IT
 * @date 2025. 4. 18.
 */
public abstract class OrderHandler {

    protected OrderHandler next;

    public OrderHandler setNext(OrderHandler next) {
        this.next = next;
        return next;
    }

    public void handle(OrderContext context) {
        process(context);

        if (next != null) {
            next.handle(context);
        }
    }

    protected abstract void process(OrderContext context);

}
