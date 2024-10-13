package com.app.office.fc;

public abstract class OldFileFormatException extends IllegalArgumentException {
    public OldFileFormatException(String str) {
        super(str);
    }
}
