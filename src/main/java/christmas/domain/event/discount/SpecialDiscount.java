package christmas.domain.event.discount;

import christmas.domain.order.Order;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SpecialDiscount implements Discount {

    private static final List<Integer> DISCOUNTED_DAYS;
    private static final int DISCOUNT = 1000;

    static {
        DISCOUNTED_DAYS = List.of(3, 10, 17, 24, 25, 31);
    }

    private static SpecialDiscount instance;

    public static SpecialDiscount getInstance() {
        if (Objects.isNull(instance)) {
            instance = new SpecialDiscount();
        }
        return instance;
    }

    private SpecialDiscount() {

    }

    @Override
    public Optional<Integer> getDiscountAmount(Order order) {
        if (!order.isVisitDateOnDays(DISCOUNTED_DAYS)) {
            return Optional.empty();
        }
        return Optional.of(DISCOUNT);
    }
}
