package com.mp.movieplanner.data.service;

public interface Service<T> {

    public boolean isOpen();

    public void close();
}
