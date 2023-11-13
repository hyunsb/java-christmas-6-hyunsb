package christmas.dto;

import christmas.domain.Gift;

import java.util.List;
import java.util.Optional;

public record OrdersDto(List<OrderDto> orders, int totalOrderAmount, Optional<Gift> gift) {

}
