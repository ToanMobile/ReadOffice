package com.app.office.fc.hslf.model;

import com.app.office.fc.hslf.model.textproperties.TextProp;
import com.app.office.fc.hslf.record.SheetContainer;

public abstract class MasterSheet extends Sheet {
    public abstract TextProp getStyleAttribute(int i, int i2, String str, boolean z);

    public MasterSheet(SheetContainer sheetContainer, int i) {
        super(sheetContainer, i);
    }

    public static boolean isPlaceholder(Shape shape) {
        if ((shape instanceof TextShape) && ((TextShape) shape).getPlaceholderAtom() != null) {
            return true;
        }
        return false;
    }
}
