package com.app.office.fc.hssf.formula.udf;

import com.app.office.fc.hssf.formula.function.FreeRefFunction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class AggregatingUDFFinder implements UDFFinder {
    private final Collection<UDFFinder> _usedToolPacks;

    public AggregatingUDFFinder(UDFFinder... uDFFinderArr) {
        ArrayList arrayList = new ArrayList(uDFFinderArr.length);
        this._usedToolPacks = arrayList;
        arrayList.addAll(Arrays.asList(uDFFinderArr));
    }

    public FreeRefFunction findFunction(String str) {
        for (UDFFinder findFunction : this._usedToolPacks) {
            FreeRefFunction findFunction2 = findFunction.findFunction(str);
            if (findFunction2 != null) {
                return findFunction2;
            }
        }
        return null;
    }

    public void add(UDFFinder uDFFinder) {
        this._usedToolPacks.add(uDFFinder);
    }
}
