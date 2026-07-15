package com.turkcell.product_service.event;

import java.util.UUID;

public record OrderItemEvent(UUID productId, int quantity) {

}
