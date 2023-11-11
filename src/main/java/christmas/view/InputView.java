package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.VisitDate;
import christmas.errors.ErrorMessage;

public class InputView {

    private static final String WELCOME = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String INPUT_VISIT_DATE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";

    public static VisitDate inputVisitDate() {
        try {
            System.out.println(WELCOME + "\n" + INPUT_VISIT_DATE);
            int visitDate = readToInt(Console.readLine());
            return VisitDate.from(visitDate);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return inputVisitDate();
        }
    }

    private static int readToInt(String read) throws IllegalArgumentException {
        try {
            validateOutOfBoundsInteger(read);
            return Integer.parseInt(read);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_INTEGER_VALUE.message());
        }
    }

    private static void validateOutOfBoundsInteger(String read) throws NumberFormatException {
        if (read.compareTo(String.valueOf(Integer.MAX_VALUE)) >= 0) {
            throw new NumberFormatException();
        }
    }
}
