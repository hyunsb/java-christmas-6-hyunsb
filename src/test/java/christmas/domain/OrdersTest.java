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
        Set<Order> orderDuplicateCheck = new HashSet<>();
        while (orderDuplicateCheck.size() < count) {
            Order order = Order.from(pickRandomMenuName(), pickRandomInteger());
            orderDuplicateCheck.add(order);
        }

        List<Order> orders = orderDuplicateCheck.stream().toList();
        if (orders.size() == this.getBeverageCount(orders)) {
            return pickOrders(count);
        }
        return orders;
    }

    private String pickRandomMenuName() {
        Menu[] menus = Menu.values();
        Random random = new Random();
        return menus[random.nextInt(0, menus.length)].getMenuName();
    }

    private int pickRandomInteger() {
        Random random = new Random();
        return random.nextInt(1, 5);
    }

    private int getBeverageCount(List<Order> orders) {
        return orders.stream()
                .filter(Order::isBeverageOrder)
                .toList()
                .size();
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

    @DisplayName("빈 리스트가 주어진 경우 예외를 발생한다.")
    @Test
    void 빈_리스트_검증_테스트() {
        Assertions.assertThatThrownBy(() -> new Orders(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
    }

    @DisplayName("음료 메뉴만 주어진 경우 예외를 발생한다.")
    @Test
    void 음료_리스트_검증_테스트() {
        String menuName = this.pickRandomBeverageMenuName();
        List<Order> orders = List.of(Order.from(menuName, pickRandomInteger()));

        Assertions.assertThatThrownBy(() -> new Orders(orders))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
    }

    @DisplayName("총 주문 개수가 20개를 초과하는 경우 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {21, 30})
    void 주문개수_검증_테스트(int count) {
        String menuName = Arrays.stream(Menu.values())
                .filter(menu -> !menu.isBeverage())
                .findAny()
                .orElseThrow()
                .getMenuName();

        Assertions.assertThatThrownBy(() -> new Orders(List.of(Order.from(menuName, count))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
    }

    private String pickRandomBeverageMenuName() {
        return Arrays.stream(Menu.values())
                .filter(Menu::isBeverage)
                .findAny()
                .orElseThrow(IllegalArgumentException::new)
                .getMenuName();
    }
}