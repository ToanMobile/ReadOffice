package com.app.office.officereader.settings;

import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import java.util.Vector;

public class SettingDialogAction implements IDialogAction {
    public IControl control;

    public SettingDialogAction(IControl iControl) {
        this.control = iControl;
    }

    public void doAction(int i, Vector<Object> vector) {
        if (i == 7 && vector != null) {
            this.control.actionEvent(21, vector.get(0));
        }
    }

    public IControl getControl() {
        return this.control;
    }

    public void dispose() {
        this.control = null;
    }
}
