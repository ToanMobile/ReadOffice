package com.app.office.fc.hslf.blip;

public final class PNG extends Bitmap {
    public int getSignature() {
        return 28160;
    }

    public int getType() {
        return 6;
    }

    public byte[] getData() {
        return super.getData();
    }
}
