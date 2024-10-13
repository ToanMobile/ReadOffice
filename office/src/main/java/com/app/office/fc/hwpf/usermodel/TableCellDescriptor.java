package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.types.TCAbstractType;
import com.app.office.fc.util.LittleEndian;

public final class TableCellDescriptor extends TCAbstractType implements Cloneable {
    public static final int SIZE = 20;
    protected short field_x_unused;

    public TableCellDescriptor() {
        setBrcTop(new BorderCode());
        setBrcLeft(new BorderCode());
        setBrcBottom(new BorderCode());
        setBrcRight(new BorderCode());
    }

    /* access modifiers changed from: protected */
    public void fillFields(byte[] bArr, int i) {
        this.field_1_rgf = LittleEndian.getShort(bArr, i + 0);
        this.field_x_unused = LittleEndian.getShort(bArr, i + 2);
        setBrcTop(new BorderCode(bArr, i + 4));
        setBrcLeft(new BorderCode(bArr, i + 8));
        setBrcBottom(new BorderCode(bArr, i + 12));
        setBrcRight(new BorderCode(bArr, i + 16));
    }

    public void serialize(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i + 0, this.field_1_rgf);
        LittleEndian.putShort(bArr, i + 2, this.field_x_unused);
        getBrcTop().serialize(bArr, i + 4);
        getBrcLeft().serialize(bArr, i + 8);
        getBrcBottom().serialize(bArr, i + 12);
        getBrcRight().serialize(bArr, i + 16);
    }

    public Object clone() throws CloneNotSupportedException {
        TableCellDescriptor tableCellDescriptor = (TableCellDescriptor) super.clone();
        tableCellDescriptor.setBrcTop((BorderCode) getBrcTop().clone());
        tableCellDescriptor.setBrcLeft((BorderCode) getBrcLeft().clone());
        tableCellDescriptor.setBrcBottom((BorderCode) getBrcBottom().clone());
        tableCellDescriptor.setBrcRight((BorderCode) getBrcRight().clone());
        return tableCellDescriptor;
    }

    public static TableCellDescriptor convertBytesToTC(byte[] bArr, int i) {
        TableCellDescriptor tableCellDescriptor = new TableCellDescriptor();
        tableCellDescriptor.fillFields(bArr, i);
        return tableCellDescriptor;
    }
}
