package christmas.domain;

import christmas.errors.ErrorMessage;

import java.util.Objects;

public class Order {

    private final Menu menu;
    private final int count;

    private Order(Menu menu, int count) throws IllegalArgumentException {
        this.validateCount(count);
        this.menu = menu;
        this.count = count;
    }

    private void validateCount(int count) throws IllegalArgumentException {
        if (count < 1) {
            throw new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
        }
    }

    public static Order from(String menuName, int count) throws IllegalArgumentException {
        Menu menu = Menu.from(menuName);
        return new Order(menu, count);
    }

    protected int getCount() {
        return count;
    }

    public boolean isBeverageOrder() {
        return menu.isBeverage();
    }

    @Override
    public boolean equals(Object target) {
        if (this == target) return true;
        if (target == null || getClass() != target.getClass()) return false;
        return this.menu.equals(((Order) target).menu);
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
