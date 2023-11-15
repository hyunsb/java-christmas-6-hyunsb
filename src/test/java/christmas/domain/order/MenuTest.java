package christmas.domain.order;

import christmas.domain.order.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class MenuTest {

    @DisplayName("정확한 메뉴 이름이 주어진 경우 메뉴 객체를 캐싱하여 반환한다.")
    @Test
    void 인스턴스_반환_테스트() {
        String menuName = Arrays.stream(Menu.values())
                .findAny()
                .orElseThrow()
                .getMenuName();

        Assertions.assertEquals(menuName, Menu.from(menuName).getMenuName());
    }
}