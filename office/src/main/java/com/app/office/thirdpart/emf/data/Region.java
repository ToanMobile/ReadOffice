package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import java.io.IOException;

public class Region {
    private Rectangle bounds;
    private Rectangle region;

    public int length() {
        return 48;
    }

    public Region(Rectangle rectangle, Rectangle rectangle2) {
        this.bounds = rectangle;
        this.region = rectangle2;
    }

    public Region(EMFInputStream eMFInputStream) throws IOException {
        eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        int readDWORD = eMFInputStream.readDWORD();
        this.bounds = eMFInputStream.readRECTL();
        this.region = eMFInputStream.readRECTL();
        for (int i = 16; i < readDWORD; i += 16) {
            eMFInputStream.readRECTL();
        }
    }

    public String toString() {
        return "  Region\n    bounds: " + this.bounds + "\n    region: " + this.region;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }
}
