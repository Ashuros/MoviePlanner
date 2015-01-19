package com.mp.movieplanner.common;

import android.app.Fragment;

import com.mp.movieplanner.SearchMovieListFragment;
import com.mp.movieplanner.SearchTvFragment;

import java.util.HashMap;
import java.util.Map;

public enum CollectionType {
    MOVIE(0, SearchMovieListFragment.class),
    TV(1, SearchTvFragment.class);

    private int collectionType;
    private Class clazz;

    private static Map<Integer, CollectionType> map = new HashMap<>();

    static {
        for (CollectionType type : CollectionType.values()) {
            map.put(type.collectionType, type);
        }
    }

    private <T extends Fragment> CollectionType(final int type, Class<T> clazz) {
        collectionType = type;
        this.clazz = clazz;
    }

    public static CollectionType valueOf(int type) {
        return map.get(type);
    }

    public int getValue() {
        return collectionType;
    }

    public Fragment newSearchFragmentInstance() {
        try {
            return (Fragment) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
