package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.ddf.DefaultEscherRecordFactory;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.hwpf.model.PictureDescriptor;
import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;

public class InlineWordArt extends PictureDescriptor {
    private ArrayList<EscherRecord> escherRecords;

    public InlineWordArt(byte[] bArr, int i) {
        super(bArr, i);
        int i2 = LittleEndian.getInt(bArr, i) + i;
        int i3 = i + 4;
        int i4 = i + LittleEndian.getShort(bArr, i3) + 4;
        i4 = LittleEndian.getShort(bArr, i3 + 2) == 102 ? i4 + LittleEndian.getUnsignedByte(bArr, i4) + 1 : i4;
        int i5 = LittleEndian.getShort(bArr, i4) + i4;
        this.escherRecords = fillEscherRecords(bArr, i4 - 4, ((i5 >= i2 ? i4 : i5) - i4) + 4);
    }

    public HWPFShape getInlineWordArt() {
        ArrayList<EscherRecord> arrayList = this.escherRecords;
        if (arrayList == null || arrayList.size() <= 0 || !(this.escherRecords.get(0) instanceof EscherContainerRecord)) {
            return null;
        }
        return HWPFShapeFactory.createShape((EscherContainerRecord) this.escherRecords.get(0), (HWPFShape) null);
    }

    public int getHorizontalScalingFactor() {
        return this.mx;
    }

    public int getVerticalScalingFactor() {
        return this.my;
    }

    public int getDxaGoal() {
        return this.dxaGoal;
    }

    public int getDyaGoal() {
        return this.dyaGoal;
    }

    private ArrayList<EscherRecord> fillEscherRecords(byte[] bArr, int i, int i2) {
        ArrayList<EscherRecord> arrayList = new ArrayList<>();
        DefaultEscherRecordFactory defaultEscherRecordFactory = new DefaultEscherRecordFactory();
        int i3 = i;
        while (i3 < i + i2) {
            try {
                EscherRecord createRecord = defaultEscherRecordFactory.createRecord(bArr, i3);
                arrayList.add(createRecord);
                i3 += createRecord.fillFields(bArr, i3, defaultEscherRecordFactory);
            } catch (Exception unused) {
                return null;
            }
        }
        return arrayList;
    }
}
