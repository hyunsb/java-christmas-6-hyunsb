package christmas.dto;

import christmas.domain.benefit.Gift;

import java.util.List;
import java.util.Optional;

public record OrdersDto(List<OrderDto> orders, int totalOrderAmount, Optional<Gift> gift) {

}
