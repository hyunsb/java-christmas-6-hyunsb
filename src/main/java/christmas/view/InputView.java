package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.OrderMenu;
import christmas.domain.OrderMenus;
import christmas.domain.VisitDate;
import christmas.errors.ErrorMessage;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputView {

    private static final String WELCOME = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String INPUT_VISIT_DATE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";

    private InputView() {
        throw new IllegalArgumentException();
    }

    public static <T> T repeatUntilNoException(Supplier<T> supplier) {
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
        return repeatUntilNoException(() -> VisitDate.from(readToInt(Console.readLine())));
    }

    public static OrderMenus inputOrders() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        return repeatUntilNoException(() -> parseOrders(Console.readLine()));
    }

    private static int readToInt(String read) throws IllegalArgumentException {
        try {
            return Integer.parseInt(read);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_INTEGER_VALUE.message());
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
        int count = readToInt(menuAndCount[1]);

        return OrderMenu.from(menu, count);
    }

    private static void validateIsCorrectMenuFormat(String read) {
        String menuFormat = "^(.+)-(\\d+)$";
        Pattern pattern = Pattern.compile(menuFormat);
        Matcher matcher = pattern.matcher(read);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message());
        }
    }
}
