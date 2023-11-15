package christmas.domain.event.discount;

import christmas.domain.order.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SpecialDiscountTest {

    @DisplayName("인스턴스를 싱글톤으로 관리하며 캐싱하여 반환한다.")
    @Test
    void 싱글톤_테스트() {
        SpecialDiscount specialDiscount = SpecialDiscount.getInstance();
        assertSame(SpecialDiscount.getInstance(), specialDiscount);
    }

    @DisplayName("이번트가 적용 대상인 경우 할인 금액을 반환한다.")
    @Test
    void 할인_적용_테스트() {
        // Given
        VisitDate visitDate = VisitDate.from(3);
        OrderMenus orderMenus = new OrderMenus(List.of(
                OrderMenu.from(Menu.BARBECUED_RIB.getMenuName(), 5),
                OrderMenu.from(Menu.CHRISTMAS_PASTA.getMenuName(), 2)
        ));
        Order order = new Order(visitDate, orderMenus);

        // When
        SpecialDiscount specialDiscount = SpecialDiscount.getInstance();
        Optional<Integer> discountAmount = specialDiscount.getDiscountAmount(order);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertTrue(discountAmount.isPresent()),
                () -> Assertions.assertEquals(1000, discountAmount.get())
        );
    }

    @DisplayName("이벤트 적용 대상이 아닌 경우 빈 Optioanl 객체를 반환한다.")
    @Test
    void 할인_미적용_테스트() {
        // Given
        VisitDate visitDate = VisitDate.from(12);
        OrderMenus orderMenus = new OrderMenus(List.of(
                OrderMenu.from(Menu.BARBECUED_RIB.getMenuName(), 5),
                OrderMenu.from(Menu.CHRISTMAS_PASTA.getMenuName(), 2)
        ));
        Order order = new Order(visitDate, orderMenus);

        // When
        SpecialDiscount specialDiscount = SpecialDiscount.getInstance();
        Optional<Integer> discountAmount = specialDiscount.getDiscountAmount(order);

        // Then
        Assertions.assertFalse(discountAmount.isPresent());
    }
}
