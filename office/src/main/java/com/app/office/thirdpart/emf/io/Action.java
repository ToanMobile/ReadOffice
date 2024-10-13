package com.app.office.thirdpart.emf.io;

import java.io.IOException;

public abstract class Action {
    private int code;
    private String name;

    public abstract Action read(int i, TaggedInputStream taggedInputStream, int i2) throws IOException;

    protected Action(int i) {
        this.code = i;
        String name2 = getClass().getName();
        this.name = name2;
        int lastIndexOf = name2.lastIndexOf(".");
        this.name = lastIndexOf >= 0 ? this.name.substring(lastIndexOf + 1) : this.name;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "Action " + getName() + " (" + getCode() + ")";
    }

    public static class Unknown extends Action {
        private int[] data;

        public Unknown() {
            super(0);
        }

        public Unknown(int i) {
            super(i);
        }

        public Action read(int i, TaggedInputStream taggedInputStream, int i2) throws IOException {
            Unknown unknown = new Unknown(i);
            unknown.data = taggedInputStream.readUnsignedByte(i2);
            return unknown;
        }

        public String toString() {
            return Action.super.toString() + " UNKNOWN!, length " + this.data.length;
        }
    }
}
