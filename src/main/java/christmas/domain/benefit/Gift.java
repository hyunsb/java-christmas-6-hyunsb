package christmas.domain.benefit;

import christmas.domain.order.Menu;

import java.util.Optional;

public enum Gift {

    MENU(Menu.CHAMPAGNE);

    private static final int MIN_ORDER_AMOUNT_FOR_GIFT = 120_000;

    private final Menu menu;

    Gift(Menu menu) {
        this.menu = menu;
    }

    public static Optional<Gift> select(int totalOrderAmount) {
        if (totalOrderAmount >= MIN_ORDER_AMOUNT_FOR_GIFT) {
            return Optional.of(Gift.MENU);
        }
        return Optional.empty();
    }

    public String getGiftMenuName() {
        return menu.getMenuName();
    }
}
