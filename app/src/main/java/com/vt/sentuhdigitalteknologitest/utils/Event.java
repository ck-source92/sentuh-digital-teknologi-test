package com.vt.sentuhdigitalteknologitest.utils;

public class Event<T> {

    private final T content;
    private boolean eventHandled = false;

    public Event(T content) {
        this.content = content;
    }

    public T getContentIfNotHandled() {
        if (eventHandled) {
            return null;
        } else {
            eventHandled = true;
            return content;
        }
    }
}