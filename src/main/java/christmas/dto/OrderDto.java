package christmas.dto;

import christmas.domain.order.Order;

import java.time.Month;
import java.util.List;

public record OrderDto(List<OrderMenuDto> orderMenuDtos, VisitDateDto visitDate, int totalOrderAmount) {

    public static OrderDto from(Order order) {
        List<OrderMenuDto> orders = order.getOrderMenuDtos();
        int totalOrderAmount = order.getTotalOrderAmount();
        VisitDateDto visitDateDto = order.getVisitDateDto();

        return new OrderDto(orders, visitDateDto, totalOrderAmount);
    }

    public int getVisitMonth() {
        Month month = visitDate.visitDate().getMonth();
        return month.getValue();
    }

    public int getVisitDayOfMonth() {
        return visitDate.visitDate().getDayOfMonth();
    }
}
