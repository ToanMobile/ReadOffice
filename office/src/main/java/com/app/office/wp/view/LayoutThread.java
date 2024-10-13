package com.app.office.wp.view;

import com.app.office.simpletext.control.IWord;
import com.app.office.simpletext.view.IRoot;
import com.app.office.simpletext.view.IView;

public class LayoutThread extends Thread {
    private boolean isDied;
    private IRoot root;

    public LayoutThread(IRoot iRoot) {
        this.root = iRoot;
    }

    public void run() {
        IWord container;
        while (!this.isDied) {
            try {
                if (this.root.canBackLayout()) {
                    this.root.backLayout();
                    sleep(50);
                } else {
                    sleep(1000);
                }
            } catch (Exception e) {
                IRoot iRoot = this.root;
                if (iRoot != null && (container = ((IView) iRoot).getContainer()) != null && container.getControl() != null) {
                    container.getControl().getSysKit().getErrorKit().writerLog(e);
                    return;
                }
                return;
            }
        }
    }

    public void setDied() {
        this.isDied = true;
    }

    public void dispose() {
        this.root = null;
        this.isDied = true;
    }
}
