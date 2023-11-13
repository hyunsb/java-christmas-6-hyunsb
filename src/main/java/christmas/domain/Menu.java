package christmas.domain;

import christmas.errors.ErrorMessage;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Menu {

    PINE_MUSHROOM_SOUP("양송이수프", 6_000, Category.APPETIZER),
    TAPAS("타파스", 5_500, Category.APPETIZER),
    CAESAR_SALAD("시저샐러드", 8_000, Category.APPETIZER),

    T_BONE_STEAK("티본스테이크", 55_000, Category.MAIN),
    BARBECUED_RIB("바베큐립", 54_000, Category.MAIN),
    SEAFOOD_PASTA("해산물파스타", 35_000, Category.MAIN),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000, Category.MAIN),

    CHOCOLATE_CAKE("초코케이크", 15_000, Category.DESSERT),
    ICE_CREAM("아이스크림", 5_000, Category.DESSERT),

    ZERO_COKE("제로콜라", 3_000, Category.BEVERAGE),
    RED_WINE("레드와인", 60_000, Category.BEVERAGE),
    CHAMPAGNE("샴페인", 25_000, Category.BEVERAGE);

    private enum Category {APPETIZER, MAIN, DESSERT, BEVERAGE;}

    private static final Map<String, Menu> MENU_NAME_CLASSIFIER;

    static {
        MENU_NAME_CLASSIFIER = Arrays.stream(Menu.values())
                .collect(Collectors.toMap(menu -> menu.menuName, menu -> menu));
    }

    private final String menuName;
    private final int price;
    private final Category category;

    Menu(String menuName, int price, Category category) {
        this.menuName = menuName;
        this.price = price;
        this.category = category;
    }

    public static Menu from(String menu) throws IllegalArgumentException {
        return Optional.ofNullable(MENU_NAME_CLASSIFIER.get(menu))
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.ORDERS_IS_NOT_INVALID.message()));
    }

    public String getMenuName() {
        return menuName;
    }

    public int getPrice() {
        return price;
    }

    public boolean isBeverage() {
        return category.equals(Category.BEVERAGE);
    }

    public boolean isMain() {
        return category.equals(Category.MAIN);
    }

    public boolean isDessert() {
        return category.equals(Category.DESSERT);
    }
}
