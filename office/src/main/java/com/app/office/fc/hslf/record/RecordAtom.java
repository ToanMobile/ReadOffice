package com.app.office.fc.hslf.record;

import com.app.office.fc.hslf.model.textproperties.AutoNumberTextProp;
import java.util.LinkedList;

public abstract class RecordAtom extends Record {
    public Record[] getChildRecords() {
        return null;
    }

    public LinkedList<AutoNumberTextProp> getExtendedParagraphPropList() {
        return null;
    }

    public boolean isAnAtom() {
        return true;
    }
}
