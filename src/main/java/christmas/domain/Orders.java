package christmas.domain;

import christmas.errors.ErrorMessage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Orders {

    private final List<Order> orders;

    public Orders(List<Order> orders) {
        this.validate(orders);
        this.orders = orders;
    }

    private void validate(List<Order> orders) throws IllegalArgumentException {
        this.validateIsNotEmpty(orders);
        this.validateIsNotDuplicated(orders);
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

    @Override
    public String toString() {
        return "Orders{" +
                "orders=" + orders +
                '}';
    }
}
