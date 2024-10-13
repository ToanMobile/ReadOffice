package com.app.office.fc.hwpf.sprm;

import com.google.firebase.messaging.Constants;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.Arrays;

@Internal
public final class SprmBuffer implements Cloneable {
    byte[] _buf;
    boolean _istd;
    int _offset;
    private final int _sprmsStartOffset;

    @Deprecated
    public SprmBuffer() {
        this(0);
    }

    @Deprecated
    public SprmBuffer(byte[] bArr) {
        this(bArr, 0);
    }

    @Deprecated
    public SprmBuffer(byte[] bArr, boolean z) {
        this(bArr, z, 0);
    }

    public SprmBuffer(byte[] bArr, boolean z, int i) {
        this._offset = bArr.length;
        this._buf = bArr;
        this._istd = z;
        this._sprmsStartOffset = i;
    }

    public SprmBuffer(byte[] bArr, int i) {
        this(bArr, false, i);
    }

    public SprmBuffer(int i) {
        this._buf = new byte[(i + 4)];
        this._offset = i;
        this._sprmsStartOffset = i;
    }

    public void addSprm(short s, byte b) {
        ensureCapacity(3);
        LittleEndian.putShort(this._buf, this._offset, s);
        int i = this._offset + 2;
        this._offset = i;
        byte[] bArr = this._buf;
        this._offset = i + 1;
        bArr[i] = b;
    }

    public void addSprm(short s, byte[] bArr) {
        ensureCapacity(bArr.length + 3);
        LittleEndian.putShort(this._buf, this._offset, s);
        int i = this._offset + 2;
        this._offset = i;
        byte[] bArr2 = this._buf;
        int i2 = i + 1;
        this._offset = i2;
        bArr2[i] = (byte) bArr.length;
        System.arraycopy(bArr, 0, bArr2, i2, bArr.length);
    }

    public void addSprm(short s, int i) {
        ensureCapacity(6);
        LittleEndian.putShort(this._buf, this._offset, s);
        int i2 = this._offset + 2;
        this._offset = i2;
        LittleEndian.putInt(this._buf, i2, i);
        this._offset += 4;
    }

    public void addSprm(short s, short s2) {
        ensureCapacity(4);
        LittleEndian.putShort(this._buf, this._offset, s);
        int i = this._offset + 2;
        this._offset = i;
        LittleEndian.putShort(this._buf, i, s2);
        this._offset += 2;
    }

    public void append(byte[] bArr) {
        append(bArr, 0);
    }

    public void append(byte[] bArr, int i) {
        ensureCapacity(bArr.length - i);
        System.arraycopy(bArr, i, this._buf, this._offset, bArr.length - i);
        this._offset += bArr.length - i;
    }

    public Object clone() throws CloneNotSupportedException {
        SprmBuffer sprmBuffer = (SprmBuffer) super.clone();
        byte[] bArr = new byte[this._buf.length];
        sprmBuffer._buf = bArr;
        byte[] bArr2 = this._buf;
        System.arraycopy(bArr2, 0, bArr, 0, bArr2.length);
        return sprmBuffer;
    }

    private void ensureCapacity(int i) {
        int i2 = this._offset;
        int i3 = i2 + i;
        byte[] bArr = this._buf;
        if (i3 >= bArr.length) {
            byte[] bArr2 = new byte[(i2 + i)];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            this._buf = bArr2;
        }
    }

    public boolean equals(Object obj) {
        return Arrays.equals(this._buf, ((SprmBuffer) obj)._buf);
    }

    public SprmOperation findSprm(short s) {
        int operationFromOpcode = SprmOperation.getOperationFromOpcode(s);
        int typeFromOpcode = SprmOperation.getTypeFromOpcode(s);
        SprmIterator sprmIterator = new SprmIterator(this._buf, 2);
        while (sprmIterator.hasNext()) {
            SprmOperation next = sprmIterator.next();
            if (next.getOperation() == operationFromOpcode && next.getType() == typeFromOpcode) {
                return next;
            }
        }
        return null;
    }

    private int findSprmOffset(short s) {
        SprmOperation findSprm = findSprm(s);
        if (findSprm == null) {
            return -1;
        }
        return findSprm.getGrpprlOffset();
    }

    public byte[] toByteArray() {
        return this._buf;
    }

    public SprmIterator iterator() {
        return new SprmIterator(this._buf, this._sprmsStartOffset);
    }

    public void updateSprm(short s, byte b) {
        int findSprmOffset = findSprmOffset(s);
        if (findSprmOffset != -1) {
            this._buf[findSprmOffset] = b;
        } else {
            addSprm(s, b);
        }
    }

    public void updateSprm(short s, boolean z) {
        int findSprmOffset = findSprmOffset(s);
        if (findSprmOffset != -1) {
            this._buf[findSprmOffset] = z ? (byte) 1 : 0;
        } else {
            addSprm(s, z ? 1 : 0);
        }
    }

    public void updateSprm(short s, int i) {
        int findSprmOffset = findSprmOffset(s);
        if (findSprmOffset != -1) {
            LittleEndian.putInt(this._buf, findSprmOffset, i);
        } else {
            addSprm(s, i);
        }
    }

    public void updateSprm(short s, short s2) {
        int findSprmOffset = findSprmOffset(s);
        if (findSprmOffset != -1) {
            LittleEndian.putShort(this._buf, findSprmOffset, s2);
        } else {
            addSprm(s, s2);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sprms (");
        sb.append(this._buf.length);
        sb.append(" byte(s)): ");
        SprmIterator it = iterator();
        while (it.hasNext()) {
            try {
                sb.append(it.next());
            } catch (Exception unused) {
                sb.append(Constants.IPC_BUNDLE_KEY_SEND_ERROR);
            }
            sb.append("; ");
        }
        return sb.toString();
    }
}
