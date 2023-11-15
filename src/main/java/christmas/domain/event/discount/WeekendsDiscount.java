package christmas.domain.event.discount;

import christmas.domain.order.Order;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WeekendsDiscount implements Discount {

    private static final List<DayOfWeek> DISCOUNTED_DAY_OF_WEEKS;
    private static final int DISCOUNT_PER_COUNT = 2023;

    static {
        DISCOUNTED_DAY_OF_WEEKS = List.of(
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY
        );
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
        if (this.isInvalidMenuCount(order) || this.isNotDiscountPeriod(order)) {
            return Optional.empty();
        }
        int totalCountOfMainMenu = order.getTotalCountOfMainMenu();
        return Optional.of(totalCountOfMainMenu * DISCOUNT_PER_COUNT);
    }

    private boolean isNotDiscountPeriod(Order order) {
        return !order.isVisitDateOnDayOfWeeks(DISCOUNTED_DAY_OF_WEEKS);
    }

    private boolean isInvalidMenuCount(Order order) {
        return order.getTotalCountOfMainMenu() == 0;
    }
}
