package com.app.office.system;

import java.util.Vector;

public interface IDialogAction {
    void dispose();

    void doAction(int i, Vector<Object> vector);

    IControl getControl();
}
