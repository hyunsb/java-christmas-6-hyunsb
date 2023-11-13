package christmas.domain.discount;

import christmas.domain.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Discounts {

    CHRISTMAS_D_DAY_DISCOUNT("크리스마스 디데이 할인", ChristmasDDayDiscount.getInstance()),
    WEEKDAYS_DISCOUNT("평일 할인", WeekdaysDiscount.getInstance()),
    WEEKENDS_DISCOUNT("주말 할인", WeekendsDiscount.getInstance()),
    SPECIAL_DISCOUNT("특별 할인", SpecialDiscount.getInstance());

    private final String name;
    private final Discount discount;

    Discounts(String name, Discount discount) {
        this.name = name;
        this.discount = discount;
    }

    public static Map<String, Integer> getDiscountStatistics(Order order) {
        Map<String, Integer> discountStatistics = new HashMap<>();
        for (Discounts discounts : Discounts.values()) {
            Optional<Integer> discountAmount = discounts.discount.getDiscountAmount(order);
            discountAmount.ifPresent(amount -> {
                discountStatistics.put(discounts.name, amount);
            });
        }
        return discountStatistics;
    }
}
