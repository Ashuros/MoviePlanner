package com.mp.movieplanner.common;

import java.util.HashMap;
import java.util.Map;

public enum CollectionType {
    MOVIE(0), TV(1);

    private int collectionType;

    private static Map<Integer, CollectionType> map = new HashMap<>();

    static {
        for (CollectionType type : CollectionType.values()) {
            map.put(type.collectionType, type);
        }
    }

    private CollectionType(final int type) {
        collectionType = type;
    }

    public static CollectionType valueOf(int type) {
        return map.get(type);
    }
}
