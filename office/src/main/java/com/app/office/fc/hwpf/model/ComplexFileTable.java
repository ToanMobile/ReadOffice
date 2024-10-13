package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.sprm.SprmBuffer;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.util.LinkedList;

@Internal
public final class ComplexFileTable {
    private static final byte GRPPRL_TYPE = 1;
    private static final byte TEXT_PIECE_TABLE_TYPE = 2;
    private SprmBuffer[] _grpprls;
    protected TextPieceTable _tpt;

    public ComplexFileTable() {
        this._tpt = new TextPieceTable();
    }

    public ComplexFileTable(byte[] bArr, byte[] bArr2, int i, int i2) throws IOException {
        LinkedList linkedList = new LinkedList();
        while (bArr2[i] == 1) {
            int i3 = i + 1;
            short s = LittleEndian.getShort(bArr2, i3);
            int i4 = i3 + 2;
            byte[] byteArray = LittleEndian.getByteArray(bArr2, i4, s);
            i = i4 + s;
            linkedList.add(new SprmBuffer(byteArray, false, 0));
        }
        this._grpprls = (SprmBuffer[]) linkedList.toArray(new SprmBuffer[linkedList.size()]);
        if (bArr2[i] == 2) {
            int i5 = i + 1;
            byte[] bArr3 = bArr;
            byte[] bArr4 = bArr2;
            this._tpt = new TextPieceTable(bArr3, bArr4, i5 + 4, LittleEndian.getInt(bArr2, i5), i2);
            return;
        }
        throw new IOException("The text piece table is corrupted");
    }

    public TextPieceTable getTextPieceTable() {
        return this._tpt;
    }

    public SprmBuffer[] getGrpprls() {
        return this._grpprls;
    }
}
