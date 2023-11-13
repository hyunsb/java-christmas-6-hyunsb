package christmas.domain;

import christmas.domain.event.benefit.Gift;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

class GiftTest {

    @DisplayName("총 주문 금액이 12만원 이상인 경우 증정 메뉴를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {120000, 1300000})
    void 증정_메뉴_반환_테스트(int totalAmount) {
        Optional<Gift> gift = Gift.select(totalAmount);

        Assertions.assertAll(
                () -> Assertions.assertTrue(gift.isPresent()),
                () -> Assertions.assertEquals(Gift.MENU, gift.get())
        );
    }

    @DisplayName("총 주문 금액이 12만원 미만인 경우 비어있는 Optional을 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {12000, 110000})
    void 증정_메뉴_없음_테스트(int totalAmount) {
        Optional<Gift> gift = Gift.select(totalAmount);

        Assertions.assertTrue(gift.isEmpty());
    }
}