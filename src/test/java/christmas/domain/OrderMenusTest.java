package christmas.domain;

import christmas.domain.order.Menu;
import christmas.domain.order.OrderMenu;
import christmas.domain.order.OrderMenus;
import christmas.errors.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderMenusTest {

    private List<OrderMenu> pickOrders(int count) {
        Set<OrderMenu> orderMenuDuplicateCheck = new HashSet<>();
        while (orderMenuDuplicateCheck.size() < count) {
            OrderMenu orderMenu = OrderMenu.from(pickRandomMenuName(), pickRandomInteger());
            orderMenuDuplicateCheck.add(orderMenu);
        }

        List<OrderMenu> orderMenus = orderMenuDuplicateCheck.stream().toList();
        if (orderMenus.size() == this.getBeverageCount(orderMenus)) {
            return pickOrders(count);
        }
        return orderMenus;
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

    private int getBeverageCount(List<OrderMenu> orderMenus) {
        return orderMenus.stream()
                .filter(OrderMenu::isBeverageOrder)
                .toList()
                .size();
    }

    @DisplayName("중복되지 않는 메뉴리스트가 주어진 경우 인스턴스를 생성한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4})
    void 인스턴스_생성_테스트(int count) {
        List<OrderMenu> orderMenus = pickOrders(count);

        assertDoesNotThrow(() -> new OrderMenus(orderMenus));
    }

    @DisplayName("중복된 메뉴가 포함된 경우 예외를 발생한다.")
    @Test
    void 중복_검증_테스트() {
        String menuNam = this.pickRandomMenuName();

        List<OrderMenu> orderMenus = List.of(
                OrderMenu.from(menuNam, pickRandomInteger()),
                OrderMenu.from(menuNam, pickRandomInteger()),
                OrderMenu.from(menuNam, pickRandomInteger())
        );

        Assertions.assertThatThrownBy(() -> new OrderMenus(orderMenus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
    }

    @DisplayName("빈 리스트가 주어진 경우 예외를 발생한다.")
    @Test
    void 빈_리스트_검증_테스트() {
        Assertions.assertThatThrownBy(() -> new OrderMenus(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
    }

    @DisplayName("음료 메뉴만 주어진 경우 예외를 발생한다.")
    @Test
    void 음료_리스트_검증_테스트() {
        String menuName = this.pickRandomBeverageMenuName();
        List<OrderMenu> orderMenus = List.of(OrderMenu.from(menuName, pickRandomInteger()));

        Assertions.assertThatThrownBy(() -> new OrderMenus(orderMenus))
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

        Assertions.assertThatThrownBy(() -> new OrderMenus(List.of(OrderMenu.from(menuName, count))))
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