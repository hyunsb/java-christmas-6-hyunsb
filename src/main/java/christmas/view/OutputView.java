package christmas.view;

import christmas.domain.event.benefit.Badge;
import christmas.dto.EventDto;
import christmas.dto.OrderDto;
import christmas.dto.OrderMenuDto;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class OutputView {

    private static final String PREVIEW_MESSAGE_FORMAT = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n";

    private static final DecimalFormat DISCOUNT_AMOUNT_FORMAT = new DecimalFormat("-###,###원");
    private static final DecimalFormat AMOUNT_FORMAT = new DecimalFormat("###,###원");

    private static final String NONE = "없음";
    private static final String NEW_LINE = "\n";

    public static void printEventPreview(OrderDto orderDto, EventDto eventDto) {
        System.out.println(
                String.format(PREVIEW_MESSAGE_FORMAT, orderDto.getVisitMonth(), orderDto.getVisitDayOfMonth())
        );

        printOrderList(orderDto);
        printTotalOrderAmount(orderDto);
        printGift(eventDto);
        printDiscountList(eventDto);
        printTotalDiscountAmount(eventDto);
        printPaymentAmount(orderDto.totalOrderAmount() - eventDto.totalDiscountAmount());
        printBadge(eventDto.badge());
    }

    private static void printOrderList(OrderDto orderDto) {
        printMessage("<주문 메뉴>", toString(orderDto.orderMenuDtos()));
    }

    private static void printTotalOrderAmount(OrderDto orderDto) {
        printMessage("<할인 전 총주문 금액>", AMOUNT_FORMAT.format(orderDto.totalOrderAmount()));
    }

    private static void printGift(EventDto eventDto) {
        printOptional("<증정 메뉴>", eventDto.gift(), gift -> String.format("%s 1개", gift.getGiftMenuName()));
    }

    private static void printDiscountList(EventDto eventDto) {
        printMessage("<혜택 내역>", toString(eventDto.discountStatistics()));
    }

    private static void printTotalDiscountAmount(EventDto eventDto) {
        printMessage("<총혜택 금액>",
                DISCOUNT_AMOUNT_FORMAT.format(eventDto.totalDiscountAmount()));
    }

    private static void printPaymentAmount(int totalPaymentAmount) {
        printPaidAmount("<할인 후 예상 결제 금액>",
                AMOUNT_FORMAT.format(totalPaymentAmount));
    }

    private static void printPaidAmount(String header, String AMOUNT_FORMAT) {
        printMessage(header, AMOUNT_FORMAT);
    }

    private static void printBadge(Optional<Badge> badge) {
        printOptional("<12월 이벤트 배지>", badge, Badge::getName);
    }

    private static String toString(List<OrderMenuDto> orderMenuDtos) {
        StringBuilder message = new StringBuilder();
        orderMenuDtos.forEach(orderMenuDto -> {
            message.append(String.format("%s %d개", orderMenuDto.menuName(), orderMenuDto.count())).append("\n");
        });
        return message.toString();
    }

    private static String toString(Map<String, Integer> discountStatistics) {
        if (discountStatistics.isEmpty()) {
            return NONE;
        }

        StringBuilder message = new StringBuilder();
        for (String discountName : discountStatistics.keySet()) {
            Integer discountValue = discountStatistics.get(discountName);
            message.append(discountName + ": " + DISCOUNT_AMOUNT_FORMAT.format(discountValue)).append("\n");
        }

        return message.toString();
    }

    private static void printMessage(String header, String message) {
        System.out.println(header);
        System.out.println(message);
        System.out.print(NEW_LINE);
    }

    private static <T> void printOptional(String header, Optional<T> optional, Function<T, String> mapper) {
        String message = optional.map(mapper).orElse(NONE);
        printMessage(header, message);
    }
}
