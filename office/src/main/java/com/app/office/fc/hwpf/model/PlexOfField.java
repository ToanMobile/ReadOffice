package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;
import java.text.MessageFormat;

@Internal
public class PlexOfField {
    private final FieldDescriptor fld;
    private final GenericPropertyNode propertyNode;

    @Deprecated
    public PlexOfField(int i, int i2, byte[] bArr) {
        this.propertyNode = new GenericPropertyNode(i, i2, bArr);
        this.fld = new FieldDescriptor(bArr);
    }

    public PlexOfField(GenericPropertyNode genericPropertyNode) {
        this.propertyNode = genericPropertyNode;
        this.fld = new FieldDescriptor(genericPropertyNode.getBytes());
    }

    public int getFcStart() {
        return this.propertyNode.getStart();
    }

    public int getFcEnd() {
        return this.propertyNode.getEnd();
    }

    public FieldDescriptor getFld() {
        return this.fld;
    }

    public String toString() {
        return MessageFormat.format("[{0}, {1}) - FLD - 0x{2}; 0x{3}", new Object[]{Integer.valueOf(getFcStart()), Integer.valueOf(getFcEnd()), Integer.toHexString(this.fld.getBoundaryType() & 255), Integer.toHexString(this.fld.getFlt() & 255)});
    }
}
