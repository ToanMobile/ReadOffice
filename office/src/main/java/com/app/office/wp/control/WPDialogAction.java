package com.app.office.wp.control;

import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import java.util.Vector;

public class WPDialogAction implements IDialogAction {
    public IControl control;

    public void doAction(int i, Vector<Object> vector) {
    }

    public WPDialogAction(IControl iControl) {
        this.control = iControl;
    }

    public IControl getControl() {
        return this.control;
    }

    public void dispose() {
        this.control = null;
    }
}
