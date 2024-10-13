package com.app.office.thirdpart.emf.io;

import java.io.IOException;

public class IncompleteActionException extends IOException {
    private static final long serialVersionUID = -6817511986951461967L;
    private Action action;
    private byte[] rest;

    public IncompleteActionException(Action action2, byte[] bArr) {
        super("Action " + action2 + " contains " + bArr.length + " unread bytes");
        this.action = action2;
        this.rest = bArr;
    }

    public Action getAction() {
        return this.action;
    }

    public byte[] getBytes() {
        return this.rest;
    }
}
