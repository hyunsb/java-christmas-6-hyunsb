package christmas.domain.Discount;

import christmas.domain.Order;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WeekendsDiscount implements Discount {

    private static final List<DayOfWeek> DISCOUNTED_DAY_OF_WEEKS;
    private static final int DISCOUNT_PER_COUNT = 2023;

    static {
        DISCOUNTED_DAY_OF_WEEKS = List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
    }

    private static WeekendsDiscount instance;

    public static WeekendsDiscount getInstance() {
        if (Objects.isNull(instance)) {
            instance = new WeekendsDiscount();
        }
        return instance;
    }

    private WeekendsDiscount() {

    }

    @Override
    public Optional<Integer> getDiscountAmount(Order order) {
        if (!order.isVisitDateOnDayOfWeeks(DISCOUNTED_DAY_OF_WEEKS)) {
            return Optional.empty();
        }
        return this.calculateDiscountAmount(order);
    }

    private Optional<Integer> calculateDiscountAmount(Order order) {
        int totalCountOfMainMenu = order.getTotalCountOfMainMenu();
        return Optional.of(totalCountOfMainMenu * DISCOUNT_PER_COUNT);
    }
}
