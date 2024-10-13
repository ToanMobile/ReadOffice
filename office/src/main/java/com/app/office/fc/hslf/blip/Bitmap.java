package com.app.office.fc.hslf.blip;

import com.app.office.fc.hslf.usermodel.PictureData;
import java.io.IOException;

public abstract class Bitmap extends PictureData {
    public void setData(byte[] bArr) throws IOException {
    }

    public byte[] getData() {
        byte[] rawData = getRawData();
        int length = rawData.length - 17;
        byte[] bArr = new byte[length];
        System.arraycopy(rawData, 17, bArr, 0, length);
        return bArr;
    }
}
