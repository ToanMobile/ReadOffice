package com.app.office.fc.openxml4j.util;

public final class Nullable<E> {
    private E value;

    public Nullable() {
    }

    public Nullable(E e) {
        this.value = e;
    }

    public E getValue() {
        return this.value;
    }

    public boolean hasValue() {
        return this.value != null;
    }

    public void nullify() {
        this.value = null;
    }
}
