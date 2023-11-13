package christmas.domain;

import christmas.dto.OrderDto;
import christmas.dto.OrdersDto;
import christmas.errors.ErrorMessage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Orders {

    private static final int MAX_ORDER_COUNT = 20;

    private final List<Order> orders;

    public Orders(List<Order> orders) {
        this.validate(orders);
        this.orders = orders;
    }

    private void validate(List<Order> orders) throws IllegalArgumentException {
        this.validateIsNotEmpty(orders);
        this.validateIsNotDuplicated(orders);
        this.validateOnlyBeverageOrder(orders);
        this.validateTotalOderCount(orders);
    }

    private void validateIsNotEmpty(List<Order> orders) throws IllegalArgumentException {
        if (orders.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
        }
    }

    private void validateIsNotDuplicated(List<Order> orders) {
        Set<Order> duplicateCheck = new HashSet<>();
        orders.forEach(order -> {
            if (!duplicateCheck.add(order)) {
                throw new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
            }
        });
    }

    private void validateOnlyBeverageOrder(List<Order> orders) {
        int beverageOrderCount = orders.stream()
                .filter(Order::isBeverageOrder)
                .toList()
                .size();

        if (orders.size() == beverageOrderCount) {
            throw new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
        }
    }

    private void validateTotalOderCount(List<Order> orders) {
        int totalOrderCount = orders.stream()
                .mapToInt(Order::getCount)
                .sum();

        if (totalOrderCount > MAX_ORDER_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
        }
    }

    public OrdersDto toDto() {
        List<OrderDto> orderDtos = orders.stream().map(Order::toDto).toList();
        int totalOrderAmount = orders.stream().mapToInt(Order::getAmount).sum();

        return new OrdersDto(orderDtos, totalOrderAmount);
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orders=" + orders +
                '}';
    }
}
