package com.app.office.objectpool;

public class ParaToken {
    private boolean isFree;
    private IMemObj obj;

    public ParaToken(IMemObj iMemObj) {
        this.obj = iMemObj;
    }

    public void free() {
        this.obj.free();
    }

    public boolean isFree() {
        return this.isFree;
    }

    public void setFree(boolean z) {
        this.isFree = z;
    }

    public void dispose() {
        this.obj = null;
    }
}
