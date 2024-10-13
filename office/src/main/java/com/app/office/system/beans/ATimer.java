package com.app.office.system.beans;

import com.app.office.system.ITimerListener;
import java.util.Timer;
import java.util.TimerTask;

public class ATimer {
    /* access modifiers changed from: private */
    public int delay;
    private boolean isRunning;
    /* access modifiers changed from: private */
    public ITimerListener listener;
    /* access modifiers changed from: private */
    public Timer timer;

    public ATimer(int i, ITimerListener iTimerListener) {
        this.delay = i;
        this.listener = iTimerListener;
    }

    public void start() {
        if (!this.isRunning) {
            Timer timer2 = new Timer();
            this.timer = timer2;
            timer2.schedule(new ATimerTask(), (long) this.delay);
            this.isRunning = true;
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void stop() {
        if (this.isRunning) {
            this.timer.cancel();
            this.timer.purge();
            this.isRunning = false;
        }
    }

    public void restart() {
        stop();
        start();
    }

    class ATimerTask extends TimerTask {
        public ATimerTask() {
        }

        public void run() {
            try {
                ATimer.this.timer.schedule(new ATimerTask(), (long) ATimer.this.delay);
                ATimer.this.listener.actionPerformed();
            } catch (Exception unused) {
            }
        }
    }

    public void dispose() {
        if (this.isRunning) {
            this.timer.cancel();
            this.timer.purge();
            this.isRunning = false;
        }
        this.timer = null;
        this.listener = null;
    }
}
