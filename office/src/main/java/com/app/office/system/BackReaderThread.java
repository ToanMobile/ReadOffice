package com.app.office.system;

public class BackReaderThread extends Thread {
    private IControl control;
    private boolean die;
    private IReader reader;

    public BackReaderThread(IReader iReader, IControl iControl) {
        this.reader = iReader;
        this.control = iControl;
    }

    public void run() {
        this.control.actionEvent(24, true);
        while (!this.die) {
            try {
                if (!this.reader.isReaderFinish()) {
                    this.reader.backReader();
                    sleep(50);
                } else {
                    this.control.actionEvent(23, true);
                    this.control = null;
                    this.reader = null;
                    return;
                }
            } catch (OutOfMemoryError e) {
                this.control.getSysKit().getErrorKit().writerLog(e, true);
                this.control.actionEvent(23, true);
                this.control = null;
                this.reader = null;
                return;
            } catch (Exception e2) {
                if (!this.reader.isAborted()) {
                    this.control.getSysKit().getErrorKit().writerLog(e2, true);
                    this.control.actionEvent(23, true);
                    this.control = null;
                    this.reader = null;
                    return;
                }
                return;
            }
        }
    }

    public void setDie(boolean z) {
        this.die = z;
    }
}
