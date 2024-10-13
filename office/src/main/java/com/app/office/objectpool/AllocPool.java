package com.app.office.objectpool;

import java.util.Vector;

public class AllocPool {
    private Vector<IMemObj> free;
    private IMemObj prototype;

    public AllocPool(IMemObj iMemObj, int i, int i2) {
        this.prototype = iMemObj;
        this.free = new Vector<>(i, i2);
    }

    public synchronized IMemObj allocObject() {
        if (this.free.size() > 0) {
            return this.free.remove(0);
        }
        return this.prototype.getCopy();
    }

    public synchronized void free(IMemObj iMemObj) {
        this.free.add(iMemObj);
    }

    public synchronized void dispose() {
        Vector<IMemObj> vector = this.free;
        if (vector != null) {
            vector.clear();
        }
    }
}
