package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetWindowOrgEx extends EMFTag {
    private Point point;

    public SetWindowOrgEx() {
        super(10, 1);
    }

    public SetWindowOrgEx(Point point2) {
        this();
        this.point = point2;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetWindowOrgEx(eMFInputStream.readPOINTL());
    }

    public String toString() {
        return super.toString() + "\n  point: " + this.point;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setWindowOrigin(this.point);
    }
}
