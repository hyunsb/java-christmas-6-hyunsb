package christmas.domain.Discount;

import christmas.domain.Order;

import java.util.Optional;

public interface Discount {

    Optional<Integer> getDiscountAmount(Order order);
}
