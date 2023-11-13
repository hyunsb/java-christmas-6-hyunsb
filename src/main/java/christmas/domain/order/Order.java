package christmas.domain.order;

import christmas.dto.OrderMenuDto;
import christmas.dto.VisitDateDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class Order {

    private final VisitDate visitDate;
    private final OrderMenus orderMenus;

    public Order(VisitDate visitDate, OrderMenus orderMenus) {
        this.visitDate = visitDate;
        this.orderMenus = orderMenus;
    }

    public List<OrderMenuDto> getOrderMenuDtos() {
        return orderMenus.getOrderMenuDtos();
    }

    public VisitDateDto getVisitDateDto() {
        return visitDate.toDto();
    }

    public int getTotalOrderAmount() {
        return orderMenus.calculateTotalOrderAmount();
    }

    public boolean isVisitDateInRange(LocalDate startDate, LocalDate endDate) {
        return visitDate.isDateInRange(startDate, endDate);
    }

    public int calculateDifferenceDaysToVisitDateFrom(LocalDate startDate) {
        return visitDate.calculateDifferenceDaysFrom(startDate);
    }

    public boolean isVisitDateOnDayOfWeeks(List<DayOfWeek> dayOfWeeks) {
        for (DayOfWeek dayOfWeek : dayOfWeeks) {
            if (visitDate.isOnDayOfWeek(dayOfWeek)) {
                return true;
            }
        }
        return false;
    }

    public int getTotalCountOfMainMenu() {
        return orderMenus.calculateTotalCountOfMainMenu();
    }

    public int getTotalCountOfDessertMenu() {
        return orderMenus.calcualteTotalCountOfDessertMenu();
    }

    public boolean isVisitDateOnDays(List<Integer> days) {
        for (Integer day : days) {
            if (visitDate.isOnDay(day)) {
                return true;
            }
        }
        return false;
    }
}
