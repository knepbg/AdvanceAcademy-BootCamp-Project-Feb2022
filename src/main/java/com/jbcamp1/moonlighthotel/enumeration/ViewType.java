package com.jbcamp1.moonlighthotel.enumeration;

import java.util.Arrays;

public enum ViewType {
    SEA, GARDEN, POOL;

    public static ViewType findByName(String name) {
        return Arrays.stream(ViewType.values())
                .filter(viewType -> viewType.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
