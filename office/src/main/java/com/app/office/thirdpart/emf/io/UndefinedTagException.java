package com.app.office.thirdpart.emf.io;

import java.io.IOException;

public class UndefinedTagException extends IOException {
    private static final long serialVersionUID = 7504997713135869344L;

    public UndefinedTagException() {
    }

    public UndefinedTagException(String str) {
        super(str);
    }

    public UndefinedTagException(int i) {
        super("Code: (" + i + ")");
    }
}
