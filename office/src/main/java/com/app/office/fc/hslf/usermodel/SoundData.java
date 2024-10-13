package com.app.office.fc.hslf.usermodel;

import com.app.office.fc.hslf.record.Document;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.RecordContainer;
import com.app.office.fc.hslf.record.RecordTypes;
import com.app.office.fc.hslf.record.Sound;
import java.util.ArrayList;

public final class SoundData {
    private Sound _container;

    public SoundData(Sound sound) {
        this._container = sound;
    }

    public String getSoundName() {
        return this._container.getSoundName();
    }

    public String getSoundType() {
        return this._container.getSoundType();
    }

    public byte[] getData() {
        return this._container.getSoundData();
    }

    public static SoundData[] find(Document document) {
        ArrayList arrayList = new ArrayList();
        Record[] childRecords = document.getChildRecords();
        for (int i = 0; i < childRecords.length; i++) {
            if (childRecords[i].getRecordType() == ((long) RecordTypes.SoundCollection.typeID)) {
                Record[] childRecords2 = ((RecordContainer) childRecords[i]).getChildRecords();
                for (int i2 = 0; i2 < childRecords2.length; i2++) {
                    if (childRecords2[i2] instanceof Sound) {
                        arrayList.add(new SoundData((Sound) childRecords2[i2]));
                    }
                }
            }
        }
        return (SoundData[]) arrayList.toArray(new SoundData[arrayList.size()]);
    }
}
