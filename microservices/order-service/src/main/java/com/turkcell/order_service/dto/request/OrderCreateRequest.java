package com.turkcell.order_service.dto.request;

import java.util.List;

public record OrderCreateRequest(List<OrderItemRequest> orderItems) {

}
