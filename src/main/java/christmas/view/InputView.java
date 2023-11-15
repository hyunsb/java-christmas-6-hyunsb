package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.order.OrderMenu;
import christmas.domain.order.OrderMenus;
import christmas.domain.order.VisitDate;
import christmas.errors.ErrorMessage;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputView {

    private static final String WELCOME = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String INPUT_VISIT_DATE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String INPUT_ORDERS = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";

    private static final String INPUT_ORDER_FORMAT = "^(.+)-(\\d+)$";
    private static final Pattern INPUT_ORDER_PATTERN = Pattern.compile(INPUT_ORDER_FORMAT);

    private InputView() {
        throw new IllegalArgumentException();
    }

    private static <T> T repeatUntilNoException(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public static VisitDate inputVisitDate() {
        System.out.println(WELCOME + "\n" + INPUT_VISIT_DATE);
        return repeatUntilNoException(() ->
                VisitDate.from(readToIntWithThrow(Console.readLine(), ErrorMessage.VISIT_IS_OUT_OF_BOUNDS.message())));
    }

    public static OrderMenus inputOrders() {
        System.out.println(INPUT_ORDERS);
        return repeatUntilNoException(() -> parseOrders(Console.readLine()));
    }

    private static int readToIntWithThrow(String read, String message) throws IllegalArgumentException {
        try {
            return Integer.parseInt(read);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(message);
        }
    }

    private static OrderMenus parseOrders(String orders) {
        return new OrderMenus(Arrays.stream(orders.split(","))
                .map(String::trim)
                .map(InputView::parseOrder)
                .toList());
    }

    private static OrderMenu parseOrder(String order) {
        validateIsCorrectMenuFormat(order);
        String[] menuAndCount = order.split("-");

        String menu = menuAndCount[0];
        int count = readToIntWithThrow(menuAndCount[1], ErrorMessage.ORDERS_IS_NOT_INVALID.message());

        return OrderMenu.from(menu, count);
    }

    private static void validateIsCorrectMenuFormat(String read) {
        Matcher matcher = INPUT_ORDER_PATTERN.matcher(read);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
        }
    }
}
