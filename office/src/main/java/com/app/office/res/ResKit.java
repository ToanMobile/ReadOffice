package com.app.office.res;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ResKit {
    private static ResKit kit = new ResKit();
    private Map<String, String> res;

    public ResKit() {
        try {
            this.res = new HashMap();
            for (Field field : Class.forName("com.app.office.res.ResConstant").getDeclaredFields()) {
                this.res.put(field.getName(), (String) field.get((Object) null));
            }
        } catch (Exception unused) {
        }
    }

    public static ResKit instance() {
        return kit;
    }

    public boolean hasResName(String str) {
        return this.res.containsKey(str);
    }

    public String getLocalString(String str) {
        return this.res.get(str);
    }
}
