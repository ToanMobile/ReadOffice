package com.app.office.fc.hssf.formula.udf;

import com.app.office.fc.hssf.formula.function.FreeRefFunction;
import java.util.HashMap;
import java.util.Map;

public final class DefaultUDFFinder implements UDFFinder {
    private final Map<String, FreeRefFunction> _functionsByName;

    public DefaultUDFFinder(String[] strArr, FreeRefFunction[] freeRefFunctionArr) {
        int length = strArr.length;
        if (freeRefFunctionArr.length == length) {
            HashMap hashMap = new HashMap((length * 3) / 2);
            for (int i = 0; i < freeRefFunctionArr.length; i++) {
                hashMap.put(strArr[i].toUpperCase(), freeRefFunctionArr[i]);
            }
            this._functionsByName = hashMap;
            return;
        }
        throw new IllegalArgumentException("Mismatch in number of function names and implementations");
    }

    public FreeRefFunction findFunction(String str) {
        return this._functionsByName.get(str.toUpperCase());
    }
}
