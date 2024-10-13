package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.types.FLDAbstractType;
import com.app.office.fc.util.Internal;

@Internal
public final class FieldDescriptor extends FLDAbstractType {
    public static final int FIELD_BEGIN_MARK = 19;
    public static final int FIELD_END_MARK = 21;
    public static final int FIELD_SEPARATOR_MARK = 20;

    public FieldDescriptor(byte[] bArr) {
        fillFields(bArr, 0);
    }

    public int getBoundaryType() {
        return getCh();
    }

    public int getFieldType() {
        if (getCh() == 19) {
            return getFlt();
        }
        throw new UnsupportedOperationException("This field is only defined for begin marks.");
    }

    public boolean isZombieEmbed() {
        if (getCh() == 21) {
            return isFZombieEmbed();
        }
        throw new UnsupportedOperationException("This field is only defined for end marks.");
    }

    public boolean isResultDirty() {
        if (getCh() == 21) {
            return isFResultDirty();
        }
        throw new UnsupportedOperationException("This field is only defined for end marks.");
    }

    public boolean isResultEdited() {
        if (getCh() == 21) {
            return isFResultEdited();
        }
        throw new UnsupportedOperationException("This field is only defined for end marks.");
    }

    public boolean isLocked() {
        if (getCh() == 21) {
            return isFLocked();
        }
        throw new UnsupportedOperationException("This field is only defined for end marks.");
    }

    public boolean isPrivateResult() {
        if (getCh() == 21) {
            return isFPrivateResult();
        }
        throw new UnsupportedOperationException("This field is only defined for end marks.");
    }

    public boolean isNested() {
        if (getCh() == 21) {
            return isFNested();
        }
        throw new UnsupportedOperationException("This field is only defined for end marks.");
    }

    public boolean isHasSep() {
        if (getCh() == 21) {
            return isFHasSep();
        }
        throw new UnsupportedOperationException("This field is only defined for end marks.");
    }
}
