package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;

public class EscherSimpleProperty extends EscherProperty {
    protected int propertyValue;

    public int serializeComplexPart(byte[] bArr, int i) {
        return 0;
    }

    public EscherSimpleProperty(short s, int i) {
        super(s);
        this.propertyValue = i;
    }

    public EscherSimpleProperty(short s, boolean z, boolean z2, int i) {
        super(s, z, z2);
        this.propertyValue = i;
    }

    public int serializeSimplePart(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i, getId());
        LittleEndian.putInt(bArr, i + 2, this.propertyValue);
        return 6;
    }

    public int getPropertyValue() {
        return this.propertyValue;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EscherSimpleProperty)) {
            return false;
        }
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) obj;
        return this.propertyValue == escherSimpleProperty.propertyValue && getId() == escherSimpleProperty.getId();
    }

    public int hashCode() {
        return this.propertyValue;
    }

    public String toString() {
        return "propNum: " + getPropertyNumber() + ", RAW: 0x" + HexDump.toHex(getId()) + ", propName: " + EscherProperties.getPropertyName(getPropertyNumber()) + ", complex: " + isComplex() + ", blipId: " + isBlipId() + ", value: " + this.propertyValue + " (0x" + HexDump.toHex(this.propertyValue) + ")";
    }
}
