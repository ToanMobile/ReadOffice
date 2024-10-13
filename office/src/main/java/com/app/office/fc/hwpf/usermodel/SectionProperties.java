package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.types.SEPAbstractType;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

public final class SectionProperties extends SEPAbstractType {
    public SectionProperties() {
        this.field_20_brcTop = new BorderCode();
        this.field_21_brcLeft = new BorderCode();
        this.field_22_brcBottom = new BorderCode();
        this.field_23_brcRight = new BorderCode();
        this.field_26_dttmPropRMark = new DateAndTime();
    }

    public Object clone() throws CloneNotSupportedException {
        SectionProperties sectionProperties = (SectionProperties) super.clone();
        sectionProperties.field_20_brcTop = (BorderCode) this.field_20_brcTop.clone();
        sectionProperties.field_21_brcLeft = (BorderCode) this.field_21_brcLeft.clone();
        sectionProperties.field_22_brcBottom = (BorderCode) this.field_22_brcBottom.clone();
        sectionProperties.field_23_brcRight = (BorderCode) this.field_23_brcRight.clone();
        sectionProperties.field_26_dttmPropRMark = (DateAndTime) this.field_26_dttmPropRMark.clone();
        return sectionProperties;
    }

    public BorderCode getTopBorder() {
        return this.field_20_brcTop;
    }

    public BorderCode getBottomBorder() {
        return this.field_22_brcBottom;
    }

    public BorderCode getLeftBorder() {
        return this.field_21_brcLeft;
    }

    public BorderCode getRightBorder() {
        return this.field_23_brcRight;
    }

    public int getSectionBorder() {
        return getPgbProp();
    }

    public boolean equals(Object obj) {
        Field[] declaredFields = SectionProperties.class.getSuperclass().getDeclaredFields();
        AccessibleObject.setAccessible(declaredFields, true);
        int i = 0;
        while (i < declaredFields.length) {
            try {
                Object obj2 = declaredFields[i].get(this);
                Object obj3 = declaredFields[i].get(obj);
                if (obj2 != null || obj3 != null) {
                    if (!obj2.equals(obj3)) {
                        return false;
                    }
                }
                i++;
            } catch (Exception unused) {
                return false;
            }
        }
        return true;
    }
}
