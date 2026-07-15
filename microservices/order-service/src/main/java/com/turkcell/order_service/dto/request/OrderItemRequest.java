package com.turkcell.order_service.dto.request;

import java.util.UUID;

public record OrderItemRequest(UUID productId, int quantity) {

}
