package christmas.controller;

import christmas.domain.order.Order;
import christmas.domain.order.OrderMenus;
import christmas.domain.order.VisitDate;
import christmas.dto.EventDto;
import christmas.dto.OrderDto;
import christmas.view.InputView;
import christmas.view.OutputView;

public class ChristmasController {

    public ChristmasController() {

    }

    public void run() {
        Order order = this.generateOrder();

        OrderDto orderDto = OrderDto.from(order);
        EventDto eventDto = EventDto.from(order);

        OutputView.printEventPreview(orderDto, eventDto);
    }

    private Order generateOrder() {
        VisitDate visitDate = InputView.inputVisitDate();
        OrderMenus orderMenus = InputView.inputOrders();

        return new Order(visitDate, orderMenus);
    }
}
