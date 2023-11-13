package christmas.domain.discount;

import christmas.domain.order.Order;

import java.util.Optional;

public interface Discount {

    Optional<Integer> getDiscountAmount(Order order);
}
