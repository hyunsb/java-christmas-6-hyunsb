package christmas.dto;

import christmas.domain.benefit.Gift;

import java.util.List;
import java.util.Optional;

public record OrderMenusDto(List<OrderMenuDto> orders, int totalOrderAmount, Optional<Gift> gift) {

}
