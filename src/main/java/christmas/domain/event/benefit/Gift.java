package christmas.domain.event.benefit;

import christmas.domain.order.Menu;

import java.util.Optional;

public enum Gift {

    MENU("증정 이벤트", Menu.CHAMPAGNE);

    private static final int MIN_ORDER_AMOUNT_FOR_GIFT = 120_000;

    private final String name;
    private final Menu menu;

    Gift(String name, Menu menu) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public int getPrice() {
        return menu.getPrice();
    }
}
