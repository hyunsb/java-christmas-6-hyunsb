package christmas.domain.order;

import christmas.dto.VisitDateDto;
import christmas.errors.ErrorMessage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class VisitDate {

    private static final Map<Integer, VisitDate> VISIT_DATE_CACHE = new HashMap<>();

    private static final int MIN_DATE = 1;
    private static final int MAX_DATE = 31;

    private final LocalDate visitDate;

    private VisitDate(Integer visitDate) {
        this.validate(visitDate);
        this.visitDate = LocalDate.of(2023, 12, visitDate);
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

    public int calculateDifferenceDaysFrom(LocalDate targetDate) {
        int days = (int) ChronoUnit.DAYS.between(visitDate, targetDate);
        return Math.abs(days);
    }

    public boolean isDateInRange(LocalDate start, LocalDate end) {
        return !visitDate.isBefore(start) && !visitDate.isAfter(end);
    }

    public boolean isOnDayOfWeek(DayOfWeek dayOfWeek) {
        return visitDate.getDayOfWeek().equals(dayOfWeek);
    }

    public boolean isOnDay(Integer day) {
        return visitDate.getDayOfMonth() == day;
    }

    public VisitDateDto toDto() {
        return new VisitDateDto(visitDate);
    }
}
