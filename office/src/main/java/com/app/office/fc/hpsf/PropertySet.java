package com.app.office.fc.hpsf;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PropertySet {
    static final byte[] BYTE_ORDER_ASSERTION = {-2, -1};
    static final byte[] FORMAT_ASSERTION = {0, 0};
    public static final int OS_MACINTOSH = 1;
    public static final int OS_WIN16 = 0;
    public static final int OS_WIN32 = 2;
    protected int byteOrder;
    protected ClassID classID;
    protected int format;
    protected int osVersion;
    protected List sections;

    public int getByteOrder() {
        return this.byteOrder;
    }

    public int getFormat() {
        return this.format;
    }

    public int getOSVersion() {
        return this.osVersion;
    }

    public ClassID getClassID() {
        return this.classID;
    }

    public int getSectionCount() {
        return this.sections.size();
    }

    public List getSections() {
        return this.sections;
    }

    protected PropertySet() {
    }

    public PropertySet(InputStream inputStream) throws NoPropertySetStreamException, MarkUnsupportedException, IOException, UnsupportedEncodingException {
        if (isPropertySetStream(inputStream)) {
            int available = inputStream.available();
            byte[] bArr = new byte[available];
            inputStream.read(bArr, 0, available);
            init(bArr, 0, available);
            return;
        }
        throw new NoPropertySetStreamException();
    }

    public PropertySet(byte[] bArr, int i, int i2) throws NoPropertySetStreamException, UnsupportedEncodingException {
        if (isPropertySetStream(bArr, i, i2)) {
            init(bArr, i, i2);
            return;
        }
        throw new NoPropertySetStreamException();
    }

    public PropertySet(byte[] bArr) throws NoPropertySetStreamException, UnsupportedEncodingException {
        this(bArr, 0, bArr.length);
    }

    public static boolean isPropertySetStream(InputStream inputStream) throws MarkUnsupportedException, IOException {
        if (inputStream.markSupported()) {
            inputStream.mark(50);
            byte[] bArr = new byte[50];
            boolean isPropertySetStream = isPropertySetStream(bArr, 0, inputStream.read(bArr, 0, Math.min(50, inputStream.available())));
            inputStream.reset();
            return isPropertySetStream;
        }
        throw new MarkUnsupportedException(inputStream.getClass().getName());
    }

    public static boolean isPropertySetStream(byte[] bArr, int i, int i2) {
        int uShort = LittleEndian.getUShort(bArr, i);
        int i3 = i + 2;
        byte[] bArr2 = new byte[2];
        LittleEndian.putShort(bArr2, (short) uShort);
        if (!Util.equal(bArr2, BYTE_ORDER_ASSERTION)) {
            return false;
        }
        int uShort2 = LittleEndian.getUShort(bArr, i3);
        int i4 = i3 + 2;
        byte[] bArr3 = new byte[2];
        LittleEndian.putShort(bArr3, (short) uShort2);
        if (Util.equal(bArr3, FORMAT_ASSERTION) && LittleEndian.getUInt(bArr, i4 + 4 + 16) >= 0) {
            return true;
        }
        return false;
    }

    private void init(byte[] bArr, int i, int i2) throws UnsupportedEncodingException {
        this.byteOrder = LittleEndian.getUShort(bArr, i);
        int i3 = i + 2;
        this.format = LittleEndian.getUShort(bArr, i3);
        int i4 = i3 + 2;
        this.osVersion = (int) LittleEndian.getUInt(bArr, i4);
        int i5 = i4 + 4;
        this.classID = new ClassID(bArr, i5);
        int i6 = i5 + 16;
        int i7 = LittleEndian.getInt(bArr, i6);
        int i8 = i6 + 4;
        if (i7 >= 0) {
            this.sections = new ArrayList(i7);
            for (int i9 = 0; i9 < i7; i9++) {
                Section section = new Section(bArr, i8);
                i8 += 20;
                this.sections.add(section);
            }
            return;
        }
        throw new HPSFRuntimeException("Section count " + i7 + " is negative.");
    }

    public boolean isSummaryInformation() {
        if (this.sections.size() <= 0) {
            return false;
        }
        return Util.equal(((Section) this.sections.get(0)).getFormatID().getBytes(), SectionIDMap.SUMMARY_INFORMATION_ID);
    }

    public boolean isDocumentSummaryInformation() {
        if (this.sections.size() <= 0) {
            return false;
        }
        return Util.equal(((Section) this.sections.get(0)).getFormatID().getBytes(), SectionIDMap.DOCUMENT_SUMMARY_INFORMATION_ID[0]);
    }

    public Property[] getProperties() throws NoSingleSectionException {
        return getFirstSection().getProperties();
    }

    /* access modifiers changed from: protected */
    public Object getProperty(int i) throws NoSingleSectionException {
        return getFirstSection().getProperty((long) i);
    }

    /* access modifiers changed from: protected */
    public boolean getPropertyBooleanValue(int i) throws NoSingleSectionException {
        return getFirstSection().getPropertyBooleanValue(i);
    }

    /* access modifiers changed from: protected */
    public int getPropertyIntValue(int i) throws NoSingleSectionException {
        return getFirstSection().getPropertyIntValue((long) i);
    }

    public boolean wasNull() throws NoSingleSectionException {
        return getFirstSection().wasNull();
    }

    public Section getFirstSection() {
        if (getSectionCount() >= 1) {
            return (Section) this.sections.get(0);
        }
        throw new MissingSectionException("Property set does not contain any sections.");
    }

    public Section getSingleSection() {
        int sectionCount = getSectionCount();
        if (sectionCount == 1) {
            return (Section) this.sections.get(0);
        }
        throw new NoSingleSectionException("Property set contains " + sectionCount + " sections.");
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof PropertySet)) {
            PropertySet propertySet = (PropertySet) obj;
            int byteOrder2 = propertySet.getByteOrder();
            int byteOrder3 = getByteOrder();
            ClassID classID2 = propertySet.getClassID();
            ClassID classID3 = getClassID();
            int format2 = propertySet.getFormat();
            int format3 = getFormat();
            int oSVersion = propertySet.getOSVersion();
            int oSVersion2 = getOSVersion();
            int sectionCount = propertySet.getSectionCount();
            int sectionCount2 = getSectionCount();
            if (byteOrder2 == byteOrder3 && classID2.equals(classID3) && format2 == format3 && oSVersion == oSVersion2 && sectionCount == sectionCount2) {
                return Util.equals((Collection<?>) getSections(), (Collection<?>) propertySet.getSections());
            }
        }
        return false;
    }

    public int hashCode() {
        throw new UnsupportedOperationException("FIXME: Not yet implemented.");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        int sectionCount = getSectionCount();
        stringBuffer.append(getClass().getName());
        stringBuffer.append('[');
        stringBuffer.append("byteOrder: ");
        stringBuffer.append(getByteOrder());
        stringBuffer.append(", classID: ");
        stringBuffer.append(getClassID());
        stringBuffer.append(", format: ");
        stringBuffer.append(getFormat());
        stringBuffer.append(", OSVersion: ");
        stringBuffer.append(getOSVersion());
        stringBuffer.append(", sectionCount: ");
        stringBuffer.append(sectionCount);
        stringBuffer.append(", sections: [\n");
        List sections2 = getSections();
        for (int i = 0; i < sectionCount; i++) {
            stringBuffer.append(((Section) sections2.get(i)).toString());
        }
        stringBuffer.append(']');
        stringBuffer.append(']');
        return stringBuffer.toString();
    }
}
