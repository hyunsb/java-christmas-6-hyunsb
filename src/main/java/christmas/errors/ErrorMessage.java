package christmas.errors;

public enum ErrorMessage {

    VISIT_IS_OUT_OF_BOUNDS("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    ORDERS_IS_NOT_INVALID("유효하지 않은 주문입니다. 다시 입력해 주세요.");

    private static final String PREFIX = "\n[ERROR]";
    private static final String DELIMITER = " ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return String.join(DELIMITER, PREFIX, message);
    }
}
