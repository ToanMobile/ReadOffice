package com.app.office.fc.ddf;

import com.app.office.fc.ddf.EscherRecord;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class DefaultEscherRecordFactory implements EscherRecordFactory {
    private static Class<?>[] escherRecordClasses;
    private static Map<Short, Constructor<? extends EscherRecord>> recordsMap;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.lang.Class<?>[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            r0 = 13
            java.lang.Class[] r0 = new java.lang.Class[r0]
            r1 = 0
            java.lang.Class<com.app.office.fc.ddf.EscherBSERecord> r2 = com.app.office.fc.ddf.EscherBSERecord.class
            r0[r1] = r2
            r1 = 1
            java.lang.Class<com.app.office.fc.ddf.EscherOptRecord> r2 = com.app.office.fc.ddf.EscherOptRecord.class
            r0[r1] = r2
            r1 = 2
            java.lang.Class<com.app.office.fc.ddf.EscherTertiaryOptRecord> r2 = com.app.office.fc.ddf.EscherTertiaryOptRecord.class
            r0[r1] = r2
            r1 = 3
            java.lang.Class<com.app.office.fc.ddf.EscherClientAnchorRecord> r2 = com.app.office.fc.ddf.EscherClientAnchorRecord.class
            r0[r1] = r2
            r1 = 4
            java.lang.Class<com.app.office.fc.ddf.EscherDgRecord> r2 = com.app.office.fc.ddf.EscherDgRecord.class
            r0[r1] = r2
            r1 = 5
            java.lang.Class<com.app.office.fc.ddf.EscherSpgrRecord> r2 = com.app.office.fc.ddf.EscherSpgrRecord.class
            r0[r1] = r2
            r1 = 6
            java.lang.Class<com.app.office.fc.ddf.EscherSpRecord> r2 = com.app.office.fc.ddf.EscherSpRecord.class
            r0[r1] = r2
            r1 = 7
            java.lang.Class<com.app.office.fc.ddf.EscherClientDataRecord> r2 = com.app.office.fc.ddf.EscherClientDataRecord.class
            r0[r1] = r2
            r1 = 8
            java.lang.Class<com.app.office.fc.ddf.EscherDggRecord> r2 = com.app.office.fc.ddf.EscherDggRecord.class
            r0[r1] = r2
            r1 = 9
            java.lang.Class<com.app.office.fc.ddf.EscherSplitMenuColorsRecord> r2 = com.app.office.fc.ddf.EscherSplitMenuColorsRecord.class
            r0[r1] = r2
            r1 = 10
            java.lang.Class<com.app.office.fc.ddf.EscherChildAnchorRecord> r2 = com.app.office.fc.ddf.EscherChildAnchorRecord.class
            r0[r1] = r2
            r1 = 11
            java.lang.Class<com.app.office.fc.ddf.EscherTextboxRecord> r2 = com.app.office.fc.ddf.EscherTextboxRecord.class
            r0[r1] = r2
            r1 = 12
            java.lang.Class<com.app.office.fc.ddf.EscherBinaryTagRecord> r2 = com.app.office.fc.ddf.EscherBinaryTagRecord.class
            r0[r1] = r2
            escherRecordClasses = r0
            java.util.Map r0 = recordsToMap(r0)
            recordsMap = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ddf.DefaultEscherRecordFactory.<clinit>():void");
    }

    public EscherRecord createRecord(byte[] bArr, int i) {
        EscherBlipRecord escherBlipRecord;
        EscherRecord.EscherRecordHeader readHeader = EscherRecord.EscherRecordHeader.readHeader(bArr, i);
        if ((readHeader.getOptions() & 15) == 15 && readHeader.getRecordId() != -4083) {
            EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
            escherContainerRecord.setRecordId(readHeader.getRecordId());
            escherContainerRecord.setOptions(readHeader.getOptions());
            return escherContainerRecord;
        } else if (readHeader.getRecordId() < -4072 || readHeader.getRecordId() > -3817) {
            Constructor constructor = recordsMap.get(Short.valueOf(readHeader.getRecordId()));
            if (constructor == null) {
                return new UnknownEscherRecord();
            }
            try {
                EscherRecord escherRecord = (EscherRecord) constructor.newInstance(new Object[0]);
                escherRecord.setRecordId(readHeader.getRecordId());
                escherRecord.setOptions(readHeader.getOptions());
                return escherRecord;
            } catch (Exception unused) {
                return new UnknownEscherRecord();
            }
        } else {
            if (readHeader.getRecordId() == -4065 || readHeader.getRecordId() == -4067 || readHeader.getRecordId() == -4066) {
                escherBlipRecord = new EscherBitmapBlip();
            } else if (readHeader.getRecordId() == -4070 || readHeader.getRecordId() == -4069 || readHeader.getRecordId() == -4068) {
                escherBlipRecord = new EscherMetafileBlip();
            } else {
                escherBlipRecord = new EscherBlipRecord();
            }
            escherBlipRecord.setRecordId(readHeader.getRecordId());
            escherBlipRecord.setOptions(readHeader.getOptions());
            return escherBlipRecord;
        }
    }

    private static Map<Short, Constructor<? extends EscherRecord>> recordsToMap(Class<?>[] clsArr) {
        HashMap hashMap = new HashMap();
        int i = 0;
        Class[] clsArr2 = new Class[0];
        while (i < clsArr.length) {
            Class<?> cls = clsArr[i];
            try {
                short s = cls.getField("RECORD_ID").getShort((Object) null);
                try {
                    hashMap.put(Short.valueOf(s), cls.getConstructor(clsArr2));
                    i++;
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            } catch (IllegalArgumentException e2) {
                throw new RuntimeException(e2);
            } catch (IllegalAccessException e3) {
                throw new RuntimeException(e3);
            } catch (NoSuchFieldException e4) {
                throw new RuntimeException(e4);
            }
        }
        return hashMap;
    }
}
