package com.app.office.fc.ddf;

import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.ShortCompanionObject;

public final class EscherPropertyFactory {
    public List<EscherProperty> createProperties(byte[] bArr, int i, short s) {
        int i2;
        ArrayList<EscherProperty> arrayList = new ArrayList<>();
        for (int i3 = 0; i3 < s; i3++) {
            short s2 = LittleEndian.getShort(bArr, i);
            int i4 = LittleEndian.getInt(bArr, i + 2);
            short s3 = (short) (s2 & 16383);
            boolean z = (s2 & ShortCompanionObject.MIN_VALUE) != 0;
            short s4 = s2 & 16384;
            byte propertyType = EscherProperties.getPropertyType(s3);
            if (propertyType == 1) {
                arrayList.add(new EscherBoolProperty(s2, i4));
            } else if (propertyType == 2) {
                arrayList.add(new EscherRGBProperty(s2, i4));
            } else if (propertyType == 3) {
                arrayList.add(new EscherShapePathProperty(s2, i4));
            } else if (!z) {
                arrayList.add(new EscherSimpleProperty(s2, i4));
            } else if (propertyType == 5) {
                arrayList.add(new EscherArrayProperty(s2, new byte[i4]));
            } else {
                arrayList.add(new EscherComplexProperty(s2, new byte[i4]));
            }
            i += 6;
        }
        for (EscherProperty escherProperty : arrayList) {
            if (escherProperty instanceof EscherComplexProperty) {
                if (escherProperty instanceof EscherArrayProperty) {
                    i2 = ((EscherArrayProperty) escherProperty).setArrayData(bArr, i);
                } else {
                    byte[] complexData = ((EscherComplexProperty) escherProperty).getComplexData();
                    System.arraycopy(bArr, i, complexData, 0, complexData.length);
                    i2 = complexData.length;
                }
                i += i2;
            }
        }
        return arrayList;
    }
}
