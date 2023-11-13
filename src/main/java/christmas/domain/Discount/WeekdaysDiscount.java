package christmas.domain.Discount;

import christmas.domain.Order;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WeekdaysDiscount implements Discount {

    private static final List<DayOfWeek> DISCOUNTED_DAY_OF_WEEKS;
    private static final int DISCOUNT_PER_COUNT = 2023;

    static {
        DISCOUNTED_DAY_OF_WEEKS = List.of(
                DayOfWeek.SUNDAY,
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY
        );
    }

    private static WeekdaysDiscount instance;

    public static WeekdaysDiscount getInstance() {
        if (Objects.isNull(instance)) {
            instance = new WeekdaysDiscount();
        }
        return instance;
    }

    private WeekdaysDiscount() {

    }

    @Override
    public Optional<Integer> getDiscountAmount(Order order) {
        if (!order.isVisitDateOnDayOfWeeks(DISCOUNTED_DAY_OF_WEEKS)) {
            return Optional.empty();
        }
        return this.calculateDiscountAmount(order);
    }

    private Optional<Integer> calculateDiscountAmount(Order order) {
        int totalCountOfDessertMenu = order.getTotalCountOfDessertMenu();
        return Optional.of(totalCountOfDessertMenu * DISCOUNT_PER_COUNT);
    }
}
