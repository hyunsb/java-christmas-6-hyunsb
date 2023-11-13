package christmas.domain.event.discount;

import christmas.domain.order.Order;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class ChristmasDDayDiscount implements Discount {

    private static final LocalDate START_DATE = LocalDate.of(2023, 12, 1);
    private static final LocalDate END_DATE = LocalDate.of(2023, 12, 25);

    private static final int DEFAULT_DISCOUNT_VALUE = 1_000;
    private static final int DISCOUNT_PER_DAY = 100;

    private static ChristmasDDayDiscount instance;

    public static ChristmasDDayDiscount getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ChristmasDDayDiscount();
        }
        return instance;
    }

    private ChristmasDDayDiscount() {
    }

    @Override
    public Optional<Integer> getDiscountAmount(Order order) {
        if (!order.isVisitDateInRange(START_DATE, END_DATE)) { //TODO: 이부분을 검증 메서드로 변경하고 예외를 던져보자
            return Optional.empty();
        }
        return calculateDiscountAmount(order);
    }

    private Optional<Integer> calculateDiscountAmount(Order order) {
        int days = order.calculateDifferenceDaysToVisitDateFrom(START_DATE);
        return Optional.of(DEFAULT_DISCOUNT_VALUE + DISCOUNT_PER_DAY * days);
    }
}
