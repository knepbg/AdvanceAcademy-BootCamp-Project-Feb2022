package com.jbcamp1.moonlighthotel.enumeration;

import java.util.Arrays;

public enum StatusType {
    NEW, ACCEPTED, CANCELED;

    public static StatusType findByName(String name) {
        return Arrays.stream(StatusType.values())
                .filter(statusType -> statusType.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
