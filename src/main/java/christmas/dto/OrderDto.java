package christmas.dto;

import christmas.domain.order.Order;

import java.util.List;

public record OrderDto(List<OrderMenuDto> orderMenuDtos, int totalOrderAmount) {

    public static OrderDto from(Order order) {
        List<OrderMenuDto> orders = order.getOrderMenuDtos();
        int totalOrderAmount = order.getTotalOrderAmount();

        return new OrderDto(orders, totalOrderAmount);
    }
}
