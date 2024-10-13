package com.app.office.fc.hpsf;

import com.app.office.fc.util.LittleEndian;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class Section {
    protected Map<Long, String> dictionary;
    protected ClassID formatID;
    protected long offset;
    protected Property[] properties;
    protected int size;
    private boolean wasNull;

    public ClassID getFormatID() {
        return this.formatID;
    }

    public long getOffset() {
        return this.offset;
    }

    public int getSize() {
        return this.size;
    }

    public int getPropertyCount() {
        return this.properties.length;
    }

    public Property[] getProperties() {
        return this.properties;
    }

    protected Section() {
    }

    public Section(byte[] bArr, int i) throws UnsupportedEncodingException {
        int i2;
        this.formatID = new ClassID(bArr, i);
        long uInt = LittleEndian.getUInt(bArr, i + 16);
        this.offset = uInt;
        int i3 = (int) uInt;
        this.size = (int) LittleEndian.getUInt(bArr, i3);
        int i4 = i3 + 4;
        int uInt2 = (int) LittleEndian.getUInt(bArr, i4);
        int i5 = i4 + 4;
        this.properties = new Property[uInt2];
        ArrayList<PropertyListEntry> arrayList = new ArrayList<>(uInt2);
        int i6 = 0;
        for (int i7 = 0; i7 < this.properties.length; i7++) {
            PropertyListEntry propertyListEntry = new PropertyListEntry();
            propertyListEntry.id = (int) LittleEndian.getUInt(bArr, i5);
            int i8 = i5 + 4;
            propertyListEntry.offset = (int) LittleEndian.getUInt(bArr, i8);
            i5 = i8 + 4;
            arrayList.add(propertyListEntry);
        }
        Collections.sort(arrayList);
        int i9 = 0;
        while (true) {
            i2 = uInt2 - 1;
            if (i9 >= i2) {
                break;
            }
            PropertyListEntry propertyListEntry2 = (PropertyListEntry) arrayList.get(i9);
            i9++;
            propertyListEntry2.length = ((PropertyListEntry) arrayList.get(i9)).offset - propertyListEntry2.offset;
        }
        if (uInt2 > 0) {
            PropertyListEntry propertyListEntry3 = (PropertyListEntry) arrayList.get(i2);
            propertyListEntry3.length = this.size - propertyListEntry3.offset;
        }
        Iterator it = arrayList.iterator();
        int i10 = -1;
        while (i10 == -1 && it.hasNext()) {
            PropertyListEntry propertyListEntry4 = (PropertyListEntry) it.next();
            if (propertyListEntry4.id == 1) {
                int i11 = (int) (this.offset + ((long) propertyListEntry4.offset));
                long uInt3 = LittleEndian.getUInt(bArr, i11);
                int i12 = i11 + 4;
                if (uInt3 == 2) {
                    i10 = LittleEndian.getUShort(bArr, i12);
                } else {
                    throw new HPSFRuntimeException("Value type of property ID 1 is not VT_I2 but " + uInt3 + ".");
                }
            }
        }
        for (PropertyListEntry propertyListEntry5 : arrayList) {
            Property property = new Property((long) propertyListEntry5.id, bArr, this.offset + ((long) propertyListEntry5.offset), propertyListEntry5.length, i10);
            if (property.getID() == 1) {
                property = new Property(property.getID(), property.getType(), Integer.valueOf(i10));
            }
            this.properties[i6] = property;
            i6++;
        }
        this.dictionary = (Map) getProperty(0);
    }

    class PropertyListEntry implements Comparable<PropertyListEntry> {
        int id;
        int length;
        int offset;

        PropertyListEntry() {
        }

        public int compareTo(PropertyListEntry propertyListEntry) {
            int i = propertyListEntry.offset;
            int i2 = this.offset;
            if (i2 < i) {
                return -1;
            }
            return i2 == i ? 0 : 1;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(getClass().getName());
            stringBuffer.append("[id=");
            stringBuffer.append(this.id);
            stringBuffer.append(", offset=");
            stringBuffer.append(this.offset);
            stringBuffer.append(", length=");
            stringBuffer.append(this.length);
            stringBuffer.append(']');
            return stringBuffer.toString();
        }
    }

    public Object getProperty(long j) {
        int i = 0;
        this.wasNull = false;
        while (true) {
            Property[] propertyArr = this.properties;
            if (i >= propertyArr.length) {
                this.wasNull = true;
                return null;
            } else if (j == propertyArr[i].getID()) {
                return this.properties[i].getValue();
            } else {
                i++;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getPropertyIntValue(long j) {
        Object property = getProperty(j);
        if (property == null) {
            return 0;
        }
        if ((property instanceof Long) || (property instanceof Integer)) {
            return ((Number) property).intValue();
        }
        throw new HPSFRuntimeException("This property is not an integer type, but " + property.getClass().getName() + ".");
    }

    /* access modifiers changed from: protected */
    public boolean getPropertyBooleanValue(int i) {
        Boolean bool = (Boolean) getProperty((long) i);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean wasNull() {
        return this.wasNull;
    }

    public String getPIDString(long j) {
        Map<Long, String> map = this.dictionary;
        String str = map != null ? map.get(Long.valueOf(j)) : null;
        if (str == null) {
            str = SectionIDMap.getPIDString(getFormatID().getBytes(), j);
        }
        return str == null ? SectionIDMap.UNDEFINED : str;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (obj != null && (obj instanceof Section)) {
            Section section = (Section) obj;
            if (!section.getFormatID().equals(getFormatID())) {
                return false;
            }
            int length = getProperties().length;
            Property[] propertyArr = new Property[length];
            int length2 = section.getProperties().length;
            Property[] propertyArr2 = new Property[length2];
            System.arraycopy(getProperties(), 0, propertyArr, 0, length);
            System.arraycopy(section.getProperties(), 0, propertyArr2, 0, length2);
            Property property = null;
            Property property2 = null;
            int i = 0;
            while (true) {
                z = true;
                if (i >= propertyArr.length) {
                    break;
                }
                long id = propertyArr[i].getID();
                if (id == 0) {
                    property2 = propertyArr[i];
                    propertyArr = remove(propertyArr, i);
                    i--;
                }
                if (id == 1) {
                    propertyArr = remove(propertyArr, i);
                    i--;
                }
                i++;
            }
            int i2 = 0;
            while (i2 < propertyArr2.length) {
                long id2 = propertyArr2[i2].getID();
                if (id2 == 0) {
                    property = propertyArr2[i2];
                    propertyArr2 = remove(propertyArr2, i2);
                    i2--;
                }
                if (id2 == 1) {
                    propertyArr2 = remove(propertyArr2, i2);
                    i2--;
                }
                i2++;
            }
            if (propertyArr.length != propertyArr2.length) {
                return false;
            }
            if (property2 != null && property != null) {
                z = property2.getValue().equals(property.getValue());
            } else if (!(property2 == null && property == null)) {
                z = false;
            }
            if (z) {
                return Util.equals((Object[]) propertyArr, (Object[]) propertyArr2);
            }
        }
        return false;
    }

    private Property[] remove(Property[] propertyArr, int i) {
        int length = propertyArr.length - 1;
        Property[] propertyArr2 = new Property[length];
        if (i > 0) {
            System.arraycopy(propertyArr, 0, propertyArr2, 0, i);
        }
        System.arraycopy(propertyArr, i + 1, propertyArr2, i, length - i);
        return propertyArr2;
    }

    public int hashCode() {
        long hashCode = ((long) getFormatID().hashCode()) + 0;
        Property[] properties2 = getProperties();
        for (Property hashCode2 : properties2) {
            hashCode += (long) hashCode2.hashCode();
        }
        return (int) (hashCode & 4294967295L);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Property[] properties2 = getProperties();
        stringBuffer.append(getClass().getName());
        stringBuffer.append('[');
        stringBuffer.append("formatID: ");
        stringBuffer.append(getFormatID());
        stringBuffer.append(", offset: ");
        stringBuffer.append(getOffset());
        stringBuffer.append(", propertyCount: ");
        stringBuffer.append(getPropertyCount());
        stringBuffer.append(", size: ");
        stringBuffer.append(getSize());
        stringBuffer.append(", properties: [\n");
        for (Property property : properties2) {
            stringBuffer.append(property.toString());
            stringBuffer.append(",\n");
        }
        stringBuffer.append(']');
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    public Map<Long, String> getDictionary() {
        return this.dictionary;
    }

    public int getCodepage() {
        Integer num = (Integer) getProperty(1);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }
}
