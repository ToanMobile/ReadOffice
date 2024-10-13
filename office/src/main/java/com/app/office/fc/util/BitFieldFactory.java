package com.app.office.fc.util;

import java.util.HashMap;
import java.util.Map;

public class BitFieldFactory {
    private static Map instances = new HashMap();

    public static BitField getInstance(int i) {
        BitField bitField = (BitField) instances.get(Integer.valueOf(i));
        if (bitField != null) {
            return bitField;
        }
        BitField bitField2 = new BitField(i);
        instances.put(Integer.valueOf(i), bitField2);
        return bitField2;
    }
}
