package christmas.domain;

import christmas.errors.ErrorMessage;

import java.util.HashMap;
import java.util.Map;

public class VisitDate {

    private static final Map<Integer, VisitDate> VISIT_DATE_CACHE = new HashMap<>();

    private static final int MIN_DATE = 1;
    private static final int MAX_DATE = 31;

    private final Integer visitDate;

    private VisitDate(Integer visitDate) {
        this.validate(visitDate);
        this.visitDate = visitDate;
        VISIT_DATE_CACHE.put(visitDate, this);
    }

    private void validate(Integer visitDate) throws IllegalArgumentException {
        if (visitDate.compareTo(MAX_DATE) > 0 || visitDate.compareTo(MIN_DATE) < 0) {
            throw new IllegalArgumentException(ErrorMessage.VISIT_IS_OUT_OF_BOUNDS.message());
        }
    }

    public static VisitDate from(Integer visitDate) throws IllegalArgumentException {
        if (!VISIT_DATE_CACHE.containsKey(visitDate)) {
            new VisitDate(visitDate);
        }
        return VISIT_DATE_CACHE.get(visitDate);
    }

    @Override
    public String toString() {
        return "VisitDate{" +
                "visitDate=" + visitDate +
                '}';
    }
}
