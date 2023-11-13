package christmas.domain;

import christmas.dto.OrderDto;
import christmas.errors.ErrorMessage;

import java.util.Objects;

public class OrderMenu {

    private final Menu menu;
    private final int count;

    private OrderMenu(Menu menu, int count) throws IllegalArgumentException {
        this.validateCount(count);
        this.menu = menu;
        this.count = count;
    }

    private void validateCount(int count) throws IllegalArgumentException {
        if (count < 1) {
            throw new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
        }
    }

    public static OrderMenu from(String menuName, int count) throws IllegalArgumentException {
        Menu menu = Menu.from(menuName);
        return new OrderMenu(menu, count);
    }

    protected int getCount() {
        return count;
    }

    protected int getAmount() {
        return menu.getPrice() * count;
    }

    public boolean isBeverageOrder() {
        return menu.isBeverage();
    }

    protected OrderDto toDto() {
        return new OrderDto(menu.getMenuName(), count);
    }

    // Set을 통하여 메뉴 중복 검증 로직을 수행하기 위한 재정의
    @Override
    public boolean equals(Object target) {
        if (this == target) return true;
        if (target == null || getClass() != target.getClass()) return false;
        return this.menu.equals(((OrderMenu) target).menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menu);
    }

    @Override
    public String toString() {
        return "Order{" +
                "menu=" + menu +
                ", count=" + count +
                '}';
    }
}
