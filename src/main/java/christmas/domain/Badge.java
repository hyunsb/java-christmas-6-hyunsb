package christmas.domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public enum Badge {

    STAR("별", 5_000),
    TREE("트리", 10_000),
    SANTA("산타", 20_000);

    private final String name;
    private final int achievementAmount;

    Badge(String name, int achievementAmount) {
        this.name = name;
        this.achievementAmount = achievementAmount;
    }

    public static Optional<Badge> of(int amount) {
        return Arrays.stream(Badge.values())
                .sorted(Comparator.comparing(Badge::getAchievementAmount).reversed())
                .filter(badge -> amount >= badge.achievementAmount)
                .findFirst();
    }

    private int getAchievementAmount() {
        return achievementAmount;
    }

    public String getName() {
        return name;
    }
}
