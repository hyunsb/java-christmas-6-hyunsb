package christmas.domain;

import christmas.errors.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderMenuTest {

    private Stream<Arguments> provideMenuNameAndCountValues() {
        return Stream.of(
                Arguments.of(this.pickRandomMenuName(), this.pickRandomInteger()),
                Arguments.of(this.pickRandomMenuName(), this.pickRandomInteger()),
                Arguments.of(this.pickRandomMenuName(), this.pickRandomInteger())
        );
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

    @DisplayName("메뉴와 1이상의 카운트가 주어진 경우 인스턴스를 생성한다.")
    @ParameterizedTest
    @MethodSource("provideMenuNameAndCountValues")
    void 인스턴스_생성_테스트(String menuName, int count) {
        assertDoesNotThrow(() -> OrderMenu.from(menuName, count));
    }

    @DisplayName("존재하지 않는 메뉴가 주어지는 경우 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"ㅇㄴㅁㄴㅇ", "ㅇㅇㄷㄷ", "ㄹ개ㅏㄱㄷ"})
    void 메뉴_검증_테스트(String menuName) {
        assertThatThrownBy(() -> OrderMenu.from(menuName, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
    }

    private Stream<Arguments> provideMenuNameAndNegativeCountValues() {
        return Stream.of(
                Arguments.of(this.pickRandomMenuName(), -1),
                Arguments.of(this.pickRandomMenuName(), -100),
                Arguments.of(this.pickRandomMenuName(), -49)
        );
    }

    @DisplayName("1미만의 메뉴 주문 개수가 주어지는 경우 예외를 발생한다.")
    @ParameterizedTest
    @MethodSource("provideMenuNameAndNegativeCountValues")
    void 카운트_검증_테스트(String menuName, int count) {
        assertThatThrownBy(() -> OrderMenu.from(menuName, count))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
    }
}