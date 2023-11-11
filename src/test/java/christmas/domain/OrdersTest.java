package christmas.domain;

import christmas.errors.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrdersTest {

    private List<Order> pickOrders(int count) {
        Set<Order> orders = new HashSet<>();
        while (orders.size() < count) {
            Order order = Order.from(pickRandomMenuName(), pickRandomInteger());
            orders.add(order);
        }
        return orders.stream().toList();
    }

    private String pickRandomMenuName() {
        Menu[] menus = Menu.values();
        Random random = new Random();
        return menus[random.nextInt(0, menus.length)].getMenuName();
    }

    private int pickRandomInteger() {
        Random random = new Random();
        return random.nextInt(1, 200);
    }

    @DisplayName("중복되지 않는 메뉴리스트가 주어진 경우 인스턴스를 생성한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4})
    void 인스턴스_생성_테스트(int count) {
        List<Order> orders = pickOrders(count);

        assertDoesNotThrow(() -> new Orders(orders));
    }

    @DisplayName("중복된 메뉴가 포함된 경우 예외를 발생한다.")
    @Test
    void 중복_검증_테스트() {
        String menuNam = this.pickRandomMenuName();

        List<Order> orders = List.of(
                Order.from(menuNam, pickRandomInteger()),
                Order.from(menuNam, pickRandomInteger()),
                Order.from(menuNam, pickRandomInteger())
        );

        Assertions.assertThatThrownBy(() -> new Orders(orders))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
    }
}