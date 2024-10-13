package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class ScaleWindowExtEx extends EMFTag {
    private int xDenom;
    private int xNum;
    private int yDenom;
    private int yNum;

    public ScaleWindowExtEx() {
        super(32, 1);
    }

    public ScaleWindowExtEx(int i, int i2, int i3, int i4) {
        this();
        this.xNum = i;
        this.xDenom = i2;
        this.yNum = i3;
        this.yDenom = i4;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new ScaleWindowExtEx(eMFInputStream.readLONG(), eMFInputStream.readLONG(), eMFInputStream.readLONG(), eMFInputStream.readLONG());
    }

    public String toString() {
        return super.toString() + "\n  xNum: " + this.xNum + "\n  xDenom: " + this.xDenom + "\n  yNum: " + this.yNum + "\n  yDenom: " + this.yDenom;
    }
}
