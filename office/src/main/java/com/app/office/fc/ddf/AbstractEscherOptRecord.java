package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractEscherOptRecord extends EscherRecord {
    protected List<EscherProperty> properties = new ArrayList();

    public void addEscherProperty(EscherProperty escherProperty) {
        this.properties.add(escherProperty);
    }

    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        int readHeader = readHeader(bArr, i);
        this.properties = new EscherPropertyFactory().createProperties(bArr, i + 8, getInstance());
        return readHeader + 8;
    }

    public List<EscherProperty> getEscherProperties() {
        return this.properties;
    }

    public EscherProperty getEscherProperty(int i) {
        return this.properties.get(i);
    }

    private int getPropertiesSize() {
        int i = 0;
        for (EscherProperty propertySize : this.properties) {
            i += propertySize.getPropertySize();
        }
        return i;
    }

    public int getRecordSize() {
        return getPropertiesSize() + 8;
    }

    public <T extends EscherProperty> T lookup(int i) {
        Iterator<EscherProperty> it = this.properties.iterator();
        while (it.hasNext()) {
            T t = (EscherProperty) it.next();
            if (t.getPropertyNumber() == i) {
                return t;
            }
        }
        return null;
    }

    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        LittleEndian.putShort(bArr, i + 2, getRecordId());
        LittleEndian.putInt(bArr, i + 4, getPropertiesSize());
        int i2 = i + 8;
        for (EscherProperty serializeSimplePart : this.properties) {
            i2 += serializeSimplePart.serializeSimplePart(bArr, i2);
        }
        for (EscherProperty serializeComplexPart : this.properties) {
            i2 += serializeComplexPart.serializeComplexPart(bArr, i2);
        }
        int i3 = i2 - i;
        escherSerializationListener.afterRecordSerialize(i2, getRecordId(), i3, this);
        return i3;
    }

    public void sortProperties() {
        Collections.sort(this.properties, new Comparator<EscherProperty>() {
            public int compare(EscherProperty escherProperty, EscherProperty escherProperty2) {
                short propertyNumber = escherProperty.getPropertyNumber();
                short propertyNumber2 = escherProperty2.getPropertyNumber();
                if (propertyNumber < propertyNumber2) {
                    return -1;
                }
                return propertyNumber == propertyNumber2 ? 0 : 1;
            }
        });
    }

    public String toString() {
        String property = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(":");
        sb.append(property);
        sb.append("  isContainer: ");
        sb.append(isContainerRecord());
        sb.append(property);
        sb.append("  options: 0x");
        sb.append(HexDump.toHex(getOptions()));
        sb.append(property);
        sb.append("  recordId: 0x");
        sb.append(HexDump.toHex(getRecordId()));
        sb.append(property);
        sb.append("  numchildren: ");
        sb.append(getChildRecords().size());
        sb.append(property);
        sb.append("  properties:");
        sb.append(property);
        for (EscherProperty obj : this.properties) {
            sb.append("    " + obj.toString() + property);
        }
        return sb.toString();
    }
}
