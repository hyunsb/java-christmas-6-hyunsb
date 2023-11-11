package christmas.errors;

public enum ErrorMessage {

    // TODO: 다시 입력해 주세요 메시지 분리하기
    INPUT_INTEGER_VALUE("숫자만 입력할 수 있습니다. 다시 입력해 주세요."),
    VISIT_IS_OUT_OF_BOUNDS("유효하지 않은 날짜입니다. 다시 입력해 주세요.");

    private static final String PREFIX = "[ERROR]";
    private static final String DELIMITER = " ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return String.join(DELIMITER, PREFIX, message);
    }
}
