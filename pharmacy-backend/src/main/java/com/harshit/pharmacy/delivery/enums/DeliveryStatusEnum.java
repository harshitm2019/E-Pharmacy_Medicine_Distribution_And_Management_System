package com.harshit.pharmacy.delivery.enums;

import java.util.Set;

public enum DeliveryStatusEnum {

    ASSIGNED,
    OUT_FOR_DELIVERY,
    DELIVERED;

    public static final Set<DeliveryStatusEnum> ACTIVE_STATUSES = Set.of(
            ASSIGNED,
            OUT_FOR_DELIVERY
    );

}
