package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;

public final class PlexOfCps {
    private int _cbStruct;
    private int _iMac;
    private int _offset;
    private ArrayList<GenericPropertyNode> _props;

    public PlexOfCps(int i) {
        this._props = new ArrayList<>();
        this._cbStruct = i;
    }

    public PlexOfCps(byte[] bArr, int i, int i2, int i3) {
        this._iMac = (i2 - 4) / (i3 + 4);
        this._cbStruct = i3;
        this._props = new ArrayList<>(this._iMac);
        for (int i4 = 0; i4 < this._iMac; i4++) {
            this._props.add(getProperty(i4, bArr, i));
        }
    }

    public GenericPropertyNode getProperty(int i) {
        return this._props.get(i);
    }

    public void addProperty(GenericPropertyNode genericPropertyNode) {
        this._props.add(genericPropertyNode);
    }

    public byte[] toByteArray() {
        int size = this._props.size();
        int i = (size + 1) * 4;
        byte[] bArr = new byte[((this._cbStruct * size) + i)];
        GenericPropertyNode genericPropertyNode = null;
        for (int i2 = 0; i2 < size; i2++) {
            genericPropertyNode = this._props.get(i2);
            LittleEndian.putInt(bArr, i2 * 4, genericPropertyNode.getStart());
            byte[] bytes = genericPropertyNode.getBytes();
            int i3 = this._cbStruct;
            System.arraycopy(bytes, 0, bArr, (i2 * i3) + i, i3);
        }
        LittleEndian.putInt(bArr, size * 4, genericPropertyNode.getEnd());
        return bArr;
    }

    private GenericPropertyNode getProperty(int i, byte[] bArr, int i2) {
        int i3 = LittleEndian.getInt(bArr, (i * 4) + i2);
        int i4 = LittleEndian.getInt(bArr, ((i + 1) * 4) + i2);
        byte[] bArr2 = new byte[this._cbStruct];
        System.arraycopy(bArr, i2 + getStructOffset(i), bArr2, 0, this._cbStruct);
        return new GenericPropertyNode(i3, i4, bArr2);
    }

    public int length() {
        return this._iMac;
    }

    private int getStructOffset(int i) {
        return ((this._iMac + 1) * 4) + (this._cbStruct * i);
    }

    /* access modifiers changed from: package-private */
    public GenericPropertyNode[] toPropertiesArray() {
        ArrayList<GenericPropertyNode> arrayList = this._props;
        if (arrayList == null || arrayList.isEmpty()) {
            return new GenericPropertyNode[0];
        }
        ArrayList<GenericPropertyNode> arrayList2 = this._props;
        return (GenericPropertyNode[]) arrayList2.toArray(new GenericPropertyNode[arrayList2.size()]);
    }

    public String toString() {
        return "PLCF (cbStruct: " + this._cbStruct + "; iMac: " + this._iMac + ")";
    }
}
