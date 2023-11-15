package christmas.domain.event.benefit;

import christmas.domain.event.benefit.Badge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

class BadgeTest {

    @DisplayName("달성 금액에 따른 배지 인스턴스를 반환한다.")
    @Test
    void 인스턴스_반환_테스트() {
        Map<Integer, Badge> testMap = Map.ofEntries(
                Map.entry(5000, Badge.STAR),
                Map.entry(10000, Badge.TREE),
                Map.entry(20000, Badge.SANTA)
        );

        for (Integer amount : testMap.keySet()) {
            Optional<Badge> badge = Badge.of(amount);
            Assertions.assertEquals(testMap.get(amount), badge.get());
        }
    }
}