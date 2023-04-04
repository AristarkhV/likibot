package com.ts.compendium.telegram.bot.order;

import java.util.Collections;
import java.util.List;

public enum OrderStatus {
    NEW,
    RECEIVED("NEW"),
    APPROVED("NEW", "RECEIVED"),
    PAID("NEW", "APPROVED", "RECEIVED", "CANCELED", "CANCELED_BY_LIKIE"),
    CANCELED("NEW", "APPROVED", "RECEIVED"),
    CANCELED_BY_LIKIE("NEW", "APPROVED", "RECEIVED"),
    CANCEL_APPROVED("CANCELED", "CANCELED_BY_LIKIE"),
    DELIVERED("PAID", "APPROVED", "RECEIVED", "CANCELED", "CANCELED_BY_LIKIE");

    private final List<String> previousStatuses;

    OrderStatus(String... previousStatuses) {
        this.previousStatuses = List.of(previousStatuses);
    }

    OrderStatus() {
        this.previousStatuses = Collections.emptyList();
    }

    public boolean isNextStatusAcceptable(OrderStatus newOrderStatus) {
        if (this.equals(newOrderStatus)) {
            return true;
        }
        return newOrderStatus.previousStatuses.contains(this.name());
    }
}