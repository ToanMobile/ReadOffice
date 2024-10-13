package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;

@Internal
public final class GenericPropertyNode extends PropertyNode<GenericPropertyNode> {
    public GenericPropertyNode(int i, int i2, byte[] bArr) {
        super(i, i2, bArr);
    }

    public byte[] getBytes() {
        return (byte[]) this._buf;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("GenericPropertyNode [");
        sb.append(getStart());
        sb.append("; ");
        sb.append(getEnd());
        sb.append(") ");
        if (getBytes() != null) {
            str = getBytes().length + " byte(s)";
        } else {
            str = "null";
        }
        sb.append(str);
        return sb.toString();
    }
}
