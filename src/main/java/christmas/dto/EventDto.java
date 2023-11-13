package christmas.dto;

import christmas.domain.event.benefit.Badge;
import christmas.domain.event.benefit.Gift;
import christmas.domain.event.discount.Discounts;
import christmas.domain.order.Order;

import java.util.Map;
import java.util.Optional;

public record EventDto(
        Map<String, Integer> discountStatistics,
        Optional<Gift> giftOptional,
        Optional<Badge> badgeOptional,
        int totalDiscountAmount) {

    public static EventDto from(Order order) {
        int totalOrderAmount = order.getTotalOrderAmount();

        Optional<Gift> giftOptional = Gift.select(totalOrderAmount);
        Map<String, Integer> discountStatistics = getDiscountStatistics(order, giftOptional);
        int totalDiscountAmount = getTotalDiscountAmount(discountStatistics);
        Optional<Badge> badgeOptional = Badge.of(totalDiscountAmount);

        return new EventDto(discountStatistics, giftOptional, badgeOptional, totalDiscountAmount);
    }

    private static Map<String, Integer> getDiscountStatistics(Order order, Optional<Gift> giftOptional) {
        Map<String, Integer> discountStatistics = Discounts.getDiscountStatistics(order);
        giftOptional.ifPresent(gift -> {
            discountStatistics.put(gift.getName(), gift.getPrice());
        });
        return discountStatistics;
    }

    private static int getTotalDiscountAmount(Map<String, Integer> discountStatistics) {
        return discountStatistics.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
