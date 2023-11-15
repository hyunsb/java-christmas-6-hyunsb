package christmas.domain.order;

import christmas.domain.order.VisitDate;
import christmas.errors.ErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VisitDateTest {

    @DisplayName("1에서 31사이의 정수가 주어진 경우 인스턴스를 생성한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 31})
    void 인스턴스_생성_테스트(Integer input) {
        VisitDate visitDate = VisitDate.from(input);
        Assertions.assertNotNull(visitDate);
    }

    @DisplayName("1에서 31사이를 벗어나는 정수가 주어진 경우 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 32})
    void 범위_검증_테스트(Integer input) {
        assertThatThrownBy(() -> VisitDate.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.VISIT_IS_OUT_OF_BOUNDS.message());
    }
}