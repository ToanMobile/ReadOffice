package com.app.office.fc.hpsf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Property {
    protected long id;
    protected long type;
    protected Object value;

    private boolean typesAreEqual(long j, long j2) {
        if (j == j2) {
            return true;
        }
        if (j == 30 && j2 == 31) {
            return true;
        }
        return j2 == 30 && j == 31;
    }

    public long getID() {
        return this.id;
    }

    public long getType() {
        return this.type;
    }

    public Object getValue() {
        return this.value;
    }

    public Property(long j, long j2, Object obj) {
        this.id = j;
        this.type = j2;
        this.value = obj;
    }

    public Property(long j, byte[] bArr, long j2, int i, int i2) throws UnsupportedEncodingException {
        this.id = j;
        if (j == 0) {
            this.value = readDictionary(bArr, j2, i, i2);
            return;
        }
        int i3 = (int) j2;
        long uInt = LittleEndian.getUInt(bArr, i3);
        this.type = uInt;
        try {
            this.value = VariantSupport.read(bArr, i3 + 4, i, (long) ((int) uInt), i2);
        } catch (UnsupportedVariantTypeException e) {
            VariantSupport.writeUnsupportedTypeMessage(e);
            this.value = e.getValue();
        }
    }

    protected Property() {
    }

    /* access modifiers changed from: protected */
    public Map readDictionary(byte[] bArr, long j, int i, int i2) throws UnsupportedEncodingException {
        long j2;
        long j3;
        byte[] bArr2 = bArr;
        long j4 = j;
        int i3 = i2;
        if (j4 < 0 || j4 > ((long) bArr2.length)) {
            throw new HPSFRuntimeException("Illegal offset " + j4 + " while HPSF stream contains " + i + " bytes.");
        }
        int i4 = (int) j4;
        long uInt = LittleEndian.getUInt(bArr2, i4);
        int i5 = i4 + 4;
        HashMap hashMap = new HashMap((int) uInt, 1.0f);
        int i6 = 0;
        while (((long) i6) < uInt) {
            try {
                Long valueOf = Long.valueOf(LittleEndian.getUInt(bArr2, i5));
                int i7 = i5 + 4;
                long uInt2 = LittleEndian.getUInt(bArr2, i7);
                int i8 = i7 + 4;
                StringBuffer stringBuffer = new StringBuffer();
                if (i3 == -1) {
                    j2 = uInt;
                    stringBuffer.append(new String(bArr2, i8, (int) uInt2));
                } else if (i3 != 1200) {
                    stringBuffer.append(new String(bArr2, i8, (int) uInt2, VariantSupport.codepageToEncoding(i2)));
                    j2 = uInt;
                } else {
                    j2 = uInt;
                    int i9 = (int) (uInt2 * 2);
                    byte[] bArr3 = new byte[i9];
                    for (int i10 = 0; i10 < i9; i10 += 2) {
                        int i11 = i8 + i10;
                        bArr3[i10] = bArr2[i11 + 1];
                        bArr3[i10 + 1] = bArr2[i11];
                    }
                    stringBuffer.append(new String(bArr3, 0, i9, VariantSupport.codepageToEncoding(i2)));
                }
                while (stringBuffer.length() > 0 && stringBuffer.charAt(stringBuffer.length() - 1) == 0) {
                    stringBuffer.setLength(stringBuffer.length() - 1);
                }
                if (i3 == 1200) {
                    if (uInt2 % 2 == 1) {
                        uInt2++;
                    }
                    j3 = (long) i8;
                    uInt2 += uInt2;
                } else {
                    j3 = (long) i8;
                }
                i5 = (int) (j3 + uInt2);
                hashMap.put(valueOf, stringBuffer.toString());
                i6++;
                uInt = j2;
            } catch (RuntimeException e) {
                POILogger logger = POILogFactory.getLogger((Class) getClass());
                int i12 = POILogger.WARN;
                logger.log(i12, (Object) "The property set's dictionary contains bogus data. All dictionary entries starting with the one with ID " + this.id + " will be ignored.", (Throwable) e);
            }
        }
        return hashMap;
    }

    /* access modifiers changed from: protected */
    public int getSize() throws WritingNotSupportedException {
        int variantLength = VariantSupport.getVariantLength(this.type);
        if (variantLength >= 0) {
            return variantLength;
        }
        if (variantLength != -2) {
            int i = (int) this.type;
            if (i == 0) {
                return variantLength;
            }
            if (i == 30) {
                int length = ((String) this.value).length() + 1;
                int i2 = length % 4;
                if (i2 > 0) {
                    length += 4 - i2;
                }
                return variantLength + length;
            }
            throw new WritingNotSupportedException(this.type, this.value);
        }
        throw new WritingNotSupportedException(this.type, (Object) null);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Property)) {
            return false;
        }
        Property property = (Property) obj;
        Object value2 = property.getValue();
        long id2 = property.getID();
        long j = this.id;
        if (j == id2 && (j == 0 || typesAreEqual(this.type, property.getType()))) {
            Object obj2 = this.value;
            if (obj2 == null && value2 == null) {
                return true;
            }
            if (!(obj2 == null || value2 == null)) {
                Class<?> cls = obj2.getClass();
                Class<?> cls2 = value2.getClass();
                if (!cls.isAssignableFrom(cls2) && !cls2.isAssignableFrom(cls)) {
                    return false;
                }
                Object obj3 = this.value;
                if (obj3 instanceof byte[]) {
                    return Util.equal((byte[]) obj3, (byte[]) value2);
                }
                return obj3.equals(value2);
            }
        }
        return false;
    }

    public int hashCode() {
        long j = this.id + 0 + this.type;
        Object obj = this.value;
        if (obj != null) {
            j += (long) obj.hashCode();
        }
        return (int) (j & 4294967295L);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append('[');
        stringBuffer.append("id: ");
        stringBuffer.append(getID());
        stringBuffer.append(", type: ");
        stringBuffer.append(getType());
        Object value2 = getValue();
        stringBuffer.append(", value: ");
        stringBuffer.append(value2.toString());
        if (value2 instanceof String) {
            String str = (String) value2;
            int length = str.length();
            byte[] bArr = new byte[(length * 2)];
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                int i2 = i * 2;
                bArr[i2] = (byte) ((65280 & charAt) >> 8);
                bArr[i2 + 1] = (byte) ((charAt & 255) >> 0);
            }
            String dump = HexDump.dump(bArr, 0, 0);
            stringBuffer.append(" [");
            stringBuffer.append(dump);
            stringBuffer.append("]");
        }
        stringBuffer.append(']');
        return stringBuffer.toString();
    }
}
