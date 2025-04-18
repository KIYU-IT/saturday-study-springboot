package kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.handler;

import java.util.concurrent.CompletableFuture;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.set.model.context.OrderContext;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public abstract class OrderHandler {

    protected OrderHandler next;

    public OrderHandler setNext(OrderHandler next) {
        this.next = next;
        return next;
    }

    public void handle(OrderContext context) {
        long start = System.currentTimeMillis();

        this.process(context);

        long end = System.currentTimeMillis();
        log.info("⏱️ [{}] 순차 처리 소요 시간: {}ms", this.getClass().getSimpleName(), (end - start));
    }

    public CompletableFuture<Void> handleAsync(OrderContext context) {
        long start = System.currentTimeMillis();

        return CompletableFuture.runAsync(() -> process(context))
            .thenCompose(v -> {
                if (next != null) {
                    return next.handleAsync(context);
                } else {
                    return CompletableFuture.completedFuture(null);
                }
            })
            .whenComplete((result, ex) -> {
                long end = System.currentTimeMillis();
                log.info("⏱️ [{}] 비동기 처리 소요 시간: {}ms", this.getClass().getSimpleName(), end - start);
            });
    }

    protected abstract void process(OrderContext context);

}
