package com.app.office.wp.control;

public class StatusManage {
    private boolean isTouchDown;
    private long pressOffset;
    private boolean selectText;

    public void dispose() {
    }

    public boolean isSelectTextStatus() {
        return this.selectText;
    }

    public void setSelectTextStatus(boolean z) {
        this.selectText = z;
    }

    public long getPressOffset() {
        return this.pressOffset;
    }

    public void setPressOffset(long j) {
        this.pressOffset = j;
    }

    public boolean isTouchDown() {
        return this.isTouchDown;
    }

    public void setTouchDown(boolean z) {
        this.isTouchDown = z;
    }
}
