package christmas.domain.order;

import org.junit.jupiter.api.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        VisitDate visitDate = VisitDate.from(3);
        OrderMenus orderMenus = new OrderMenus(List.of(
                OrderMenu.from(Menu.BARBECUED_RIB.getMenuName(), 5),
                OrderMenu.from(Menu.CHRISTMAS_PASTA.getMenuName(), 2)
        ));

        order = new Order(visitDate, orderMenus);
    }

    @DisplayName("총 주문 금액의 합계를 반환한다.")
    @Test
    void 주문_금액_반환_테스트() {
        int expected = Menu.BARBECUED_RIB.getPrice() * 5 + Menu.CHRISTMAS_PASTA.getPrice() * 2;
        Assertions.assertEquals(expected, order.getTotalOrderAmount());
    }

    @DisplayName("방문 날짜가 파라매터로 전달된 두 날짜 사이에 존재하는지 여부를 반환한다.")
    @Test
    void 방문_날짜_사이값_테스트() {
        LocalDate start = LocalDate.of(2023, 12, 1);
        LocalDate end = LocalDate.of(2023, 12, 4);

        Assertions.assertTrue(order.isVisitDateInRange(start, end));
    }

    @DisplayName("방문 요일이 파라매터로 전달된 요일 리스트에 존재하는지 여부를 반환한다.")
    @Test
    void 방문_요일_포함_테스트() {
        List<DayOfWeek> days = List.of(DayOfWeek.MONDAY, DayOfWeek.SUNDAY);

        Assertions.assertTrue(order.isVisitDateOnDayOfWeeks(days));
    }

    @DisplayName("방문 날짜가 파라매터로 전달된 날짜 리스트에 존재하는지 여부를 반환한다.")
    @Test
    void 방문_날짜_포함_테스트() {
        List<Integer> days = List.of(1, 3, 4);

        Assertions.assertTrue(order.isVisitDateOnDays(days));
    }

    @DisplayName("방문 날짜와 파라매터로 전달된 날짜의 차이를 반환한다.")
    @Test
    void 방문_날짜_차이값_테스트() {
        LocalDate date = LocalDate.of(2023, 12, 1);
        Assertions.assertEquals(2, order.calculateDifferenceDaysToVisitDateFrom(date));
    }
}