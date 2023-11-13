package christmas.domain.order;

import christmas.domain.benefit.Gift;
import christmas.dto.OrderDto;
import christmas.dto.OrdersDto;
import christmas.errors.ErrorMessage;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class OrderMenus {

    private static final int MAX_ORDER_COUNT = 20;

    private final List<OrderMenu> orderMenus; // TODO: Map을 사용하는 방식으로 리팩터링할 수 있을 것 같음.

    public OrderMenus(List<OrderMenu> orderMenus) {
        this.validate(orderMenus);
        this.orderMenus = orderMenus;
    }

    private void validate(List<OrderMenu> orderMenus) throws IllegalArgumentException {
        this.validateIsNotEmpty(orderMenus);
        this.validateIsNotDuplicated(orderMenus);
        this.validateOnlyBeverageOrder(orderMenus);
        this.validateTotalOderCount(orderMenus);
    }

    private void validateIsNotEmpty(List<OrderMenu> orderMenus) throws IllegalArgumentException {
        if (orderMenus.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
        }
    }

    private void validateIsNotDuplicated(List<OrderMenu> orderMenus) {
        Set<OrderMenu> duplicateCheck = new HashSet<>();
        orderMenus.forEach(order -> {
            if (!duplicateCheck.add(order)) {
                throw new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
            }
        });
    }

    private void validateOnlyBeverageOrder(List<OrderMenu> orderMenus) {
        int beverageOrderCount = orderMenus.stream()
                .filter(OrderMenu::isBeverageOrder)
                .toList()
                .size();

        if (orderMenus.size() == beverageOrderCount) {
            throw new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
        }
    }

    private void validateTotalOderCount(List<OrderMenu> orderMenus) {
        int totalOrderCount = orderMenus.stream()
                .mapToInt(OrderMenu::getCount)
                .sum();

        if (totalOrderCount > MAX_ORDER_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
        }
    }

    public OrdersDto toDto() {
        List<OrderDto> orderDtos = this.getOrderDtos();
        int totalOrderAmount = this.calculateTotalOrderAmount();
        Optional<Gift> gift = Gift.select(totalOrderAmount);

        return new OrdersDto(orderDtos, totalOrderAmount, gift);
    }

    private List<OrderDto> getOrderDtos() {
        return orderMenus.stream()
                .map(OrderMenu::toDto)
                .toList();
    }

    private int calculateTotalOrderAmount() {
        return orderMenus.stream()
                .mapToInt(OrderMenu::getAmount)
                .sum();
    }

    public int getTotalCountOfMainMenu() {
        return orderMenus.stream()
                .filter(OrderMenu::isMainOrder)
                .mapToInt(OrderMenu::getCount)
                .sum();
    }

    public int getTotalCountOfDessertMenu() {
        return orderMenus.stream()
                .filter(OrderMenu::isDessertOrder)
                .mapToInt(OrderMenu::getCount)
                .sum();
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orders=" + orderMenus +
                '}';
    }
}