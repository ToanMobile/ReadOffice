package com.app.office.fc.hslf.model;

import com.app.office.fc.hslf.exceptions.HSLFException;
import java.lang.reflect.Field;
import java.util.HashMap;

public final class ShapeTypes implements com.app.office.common.shape.ShapeTypes {
    public static HashMap types = new HashMap();

    public static String typeName(int i) {
        return (String) types.get(Integer.valueOf(i));
    }

    static {
        try {
            Field[] fields = com.app.office.common.shape.ShapeTypes.class.getFields();
            for (int i = 0; i < fields.length; i++) {
                Object obj = fields[i].get((Object) null);
                if (obj instanceof Integer) {
                    types.put(obj, fields[i].getName());
                }
            }
        } catch (IllegalAccessException unused) {
            throw new HSLFException("Failed to initialize shape types");
        }
    }
}
