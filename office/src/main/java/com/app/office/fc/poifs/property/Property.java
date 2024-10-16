package com.app.office.fc.poifs.property;

import com.app.office.fc.hpsf.ClassID;
import com.app.office.fc.util.ByteField;
import com.app.office.fc.util.IntegerField;
import com.app.office.fc.util.ShortField;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public abstract class Property implements Child {
    protected static final byte _NODE_BLACK = 1;
    protected static final byte _NODE_RED = 0;
    protected static final int _NO_INDEX = -1;
    private static final int _big_block_minimum_bytes = 4096;
    private static final int _child_property_offset = 76;
    private static final int _days_1_offset = 104;
    private static final int _days_2_offset = 112;
    private static final byte _default_fill = 0;
    private static final int _max_name_length = 31;
    private static final int _name_size_offset = 64;
    private static final int _next_property_offset = 72;
    private static final int _node_color_offset = 67;
    private static final int _previous_property_offset = 68;
    private static final int _seconds_1_offset = 100;
    private static final int _seconds_2_offset = 108;
    private static final int _size_offset = 120;
    private static final int _start_block_offset = 116;
    private static final int _storage_clsid_offset = 80;
    private static final int _user_flags_offset = 96;
    private IntegerField _child_property;
    private IntegerField _days_1;
    private IntegerField _days_2;
    private int _index;
    private String _name;
    private ShortField _name_size;
    private Child _next_child;
    private IntegerField _next_property;
    private ByteField _node_color;
    private Child _previous_child;
    private IntegerField _previous_property;
    private ByteField _property_type;
    private byte[] _raw_data;
    private IntegerField _seconds_1;
    private IntegerField _seconds_2;
    private IntegerField _size;
    private IntegerField _start_block;
    private ClassID _storage_clsid;
    private IntegerField _user_flags;

    public static boolean isSmall(int i) {
        return i < 4096;
    }

    static boolean isValidIndex(int i) {
        return i != -1;
    }

    public abstract boolean isDirectory();

    /* access modifiers changed from: protected */
    public abstract void preWrite();

    public boolean preferArray() {
        return true;
    }

    protected Property() {
        byte[] bArr = new byte[128];
        this._raw_data = bArr;
        Arrays.fill(bArr, (byte) 0);
        this._name_size = new ShortField(64);
        this._property_type = new ByteField(66);
        this._node_color = new ByteField(67);
        this._previous_property = new IntegerField(68, -1, this._raw_data);
        this._next_property = new IntegerField(72, -1, this._raw_data);
        this._child_property = new IntegerField(76, -1, this._raw_data);
        this._storage_clsid = new ClassID(this._raw_data, 80);
        this._user_flags = new IntegerField(96, 0, this._raw_data);
        this._seconds_1 = new IntegerField(100, 0, this._raw_data);
        this._days_1 = new IntegerField(104, 0, this._raw_data);
        this._seconds_2 = new IntegerField(108, 0, this._raw_data);
        this._days_2 = new IntegerField(112, 0, this._raw_data);
        this._start_block = new IntegerField(116);
        this._size = new IntegerField(120, 0, this._raw_data);
        this._index = -1;
        setName("");
        setNextChild((Child) null);
        setPreviousChild((Child) null);
    }

    protected Property(int i, byte[] bArr, int i2) {
        byte[] bArr2 = new byte[128];
        this._raw_data = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, 128);
        this._name_size = new ShortField(64, this._raw_data);
        this._property_type = new ByteField(66, this._raw_data);
        this._node_color = new ByteField(67, this._raw_data);
        this._previous_property = new IntegerField(68, this._raw_data);
        this._next_property = new IntegerField(72, this._raw_data);
        this._child_property = new IntegerField(76, this._raw_data);
        this._storage_clsid = new ClassID(this._raw_data, 80);
        this._user_flags = new IntegerField(96, 0, this._raw_data);
        this._seconds_1 = new IntegerField(100, this._raw_data);
        this._days_1 = new IntegerField(104, this._raw_data);
        this._seconds_2 = new IntegerField(108, this._raw_data);
        this._days_2 = new IntegerField(112, this._raw_data);
        this._start_block = new IntegerField(116, this._raw_data);
        this._size = new IntegerField(120, this._raw_data);
        this._index = i;
        int i3 = (this._name_size.get() / 2) - 1;
        if (i3 < 1) {
            this._name = "";
        } else {
            char[] cArr = new char[i3];
            int i4 = 0;
            for (int i5 = 0; i5 < i3; i5++) {
                cArr[i5] = (char) new ShortField(i4, this._raw_data).get();
                i4 += 2;
            }
            this._name = new String(cArr, 0, i3);
        }
        this._next_child = null;
        this._previous_child = null;
    }

    public void writeData(OutputStream outputStream) throws IOException {
        outputStream.write(this._raw_data);
    }

    public void setStartBlock(int i) {
        this._start_block.set(i, this._raw_data);
    }

    public int getStartBlock() {
        return this._start_block.get();
    }

    public int getSize() {
        return this._size.get();
    }

    public boolean shouldUseSmallBlocks() {
        return isSmall(this._size.get());
    }

    public String getName() {
        return this._name;
    }

    public ClassID getStorageClsid() {
        return this._storage_clsid;
    }

    /* access modifiers changed from: protected */
    public void setName(String str) {
        char[] charArray = str.toCharArray();
        int min = Math.min(charArray.length, 31);
        this._name = new String(charArray, 0, min);
        int i = 0;
        short s = 0;
        while (i < min) {
            new ShortField(s, (short) charArray[i], this._raw_data);
            s = (short) (s + 2);
            i++;
        }
        while (i < 32) {
            new ShortField(s, 0, this._raw_data);
            s = (short) (s + 2);
            i++;
        }
        this._name_size.set((short) ((min + 1) * 2), this._raw_data);
    }

    public void setStorageClsid(ClassID classID) {
        this._storage_clsid = classID;
        if (classID == null) {
            Arrays.fill(this._raw_data, 80, 96, (byte) 0);
        } else {
            classID.write(this._raw_data, 80);
        }
    }

    /* access modifiers changed from: protected */
    public void setPropertyType(byte b) {
        this._property_type.set(b, this._raw_data);
    }

    /* access modifiers changed from: protected */
    public void setNodeColor(byte b) {
        this._node_color.set(b, this._raw_data);
    }

    /* access modifiers changed from: protected */
    public void setChildProperty(int i) {
        this._child_property.set(i, this._raw_data);
    }

    /* access modifiers changed from: protected */
    public int getChildIndex() {
        return this._child_property.get();
    }

    /* access modifiers changed from: protected */
    public void setSize(int i) {
        this._size.set(i, this._raw_data);
    }

    /* access modifiers changed from: protected */
    public void setIndex(int i) {
        this._index = i;
    }

    /* access modifiers changed from: protected */
    public int getIndex() {
        return this._index;
    }

    /* access modifiers changed from: package-private */
    public int getNextChildIndex() {
        return this._next_property.get();
    }

    /* access modifiers changed from: package-private */
    public int getPreviousChildIndex() {
        return this._previous_property.get();
    }

    public Child getNextChild() {
        return this._next_child;
    }

    public Child getPreviousChild() {
        return this._previous_child;
    }

    public void setNextChild(Child child) {
        this._next_child = child;
        this._next_property.set(child == null ? -1 : ((Property) child).getIndex(), this._raw_data);
    }

    public void setPreviousChild(Child child) {
        this._previous_child = child;
        this._previous_property.set(child == null ? -1 : ((Property) child).getIndex(), this._raw_data);
    }

    public Object[] getViewableArray() {
        long j = (((long) this._days_1.get()) << 32) + (((long) this._seconds_1.get()) & 65535);
        long j2 = (((long) this._days_2.get()) << 32) + (((long) this._seconds_2.get()) & 65535);
        return new Object[]{"Name          = \"" + getName() + "\"", "Property Type = " + this._property_type.get(), "Node Color    = " + this._node_color.get(), "Time 1        = " + j, "Time 2        = " + j2};
    }

    public Iterator getViewableIterator() {
        return Collections.EMPTY_LIST.iterator();
    }

    public String getShortDescription() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Property: \"");
        stringBuffer.append(getName());
        stringBuffer.append("\"");
        return stringBuffer.toString();
    }
}
