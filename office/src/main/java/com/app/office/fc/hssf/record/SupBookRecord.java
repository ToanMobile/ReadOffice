package com.app.office.fc.hssf.record;

import androidx.core.view.InputDeviceCompat;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class SupBookRecord extends StandardRecord {
    private static final short SMALL_RECORD_SIZE = 4;
    private static final short TAG_ADD_IN_FUNCTIONS = 14849;
    private static final short TAG_INTERNAL_REFERENCES = 1025;
    public static final short sid = 430;
    private boolean _isAddInFunctions;
    private short field_1_number_of_sheets;
    private String field_2_encoded_url;
    private String[] field_3_sheet_names;

    public short getSid() {
        return sid;
    }

    public static SupBookRecord createInternalReferences(short s) {
        return new SupBookRecord(false, s);
    }

    public static SupBookRecord createAddInFunctions() {
        return new SupBookRecord(true, 1);
    }

    public static SupBookRecord createExternalReferences(String str, String[] strArr) {
        return new SupBookRecord(str, strArr);
    }

    private SupBookRecord(boolean z, short s) {
        this.field_1_number_of_sheets = s;
        this.field_2_encoded_url = null;
        this.field_3_sheet_names = null;
        this._isAddInFunctions = z;
    }

    public SupBookRecord(String str, String[] strArr) {
        this.field_1_number_of_sheets = (short) strArr.length;
        this.field_2_encoded_url = str;
        this.field_3_sheet_names = strArr;
        this._isAddInFunctions = false;
    }

    public boolean isExternalReferences() {
        return this.field_3_sheet_names != null;
    }

    public boolean isInternalReferences() {
        return this.field_3_sheet_names == null && !this._isAddInFunctions;
    }

    public boolean isAddInFunctions() {
        return this.field_3_sheet_names == null && this._isAddInFunctions;
    }

    public SupBookRecord(RecordInputStream recordInputStream) {
        int remaining = recordInputStream.remaining();
        this.field_1_number_of_sheets = recordInputStream.readShort();
        if (remaining > 4) {
            this._isAddInFunctions = false;
            this.field_2_encoded_url = recordInputStream.readString();
            int i = this.field_1_number_of_sheets;
            String[] strArr = new String[i];
            for (int i2 = 0; i2 < i; i2++) {
                strArr[i2] = recordInputStream.readString();
            }
            this.field_3_sheet_names = strArr;
            return;
        }
        this.field_2_encoded_url = null;
        this.field_3_sheet_names = null;
        short readShort = recordInputStream.readShort();
        if (readShort == 1025) {
            this._isAddInFunctions = false;
        } else if (readShort == 14849) {
            this._isAddInFunctions = true;
            if (this.field_1_number_of_sheets != 1) {
                throw new RuntimeException("Expected 0x0001 for number of sheets field in 'Add-In Functions' but got (" + this.field_1_number_of_sheets + ")");
            }
        } else {
            throw new RuntimeException("invalid EXTERNALBOOK code (" + Integer.toHexString(readShort) + ")");
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [SUPBOOK ");
        if (isExternalReferences()) {
            stringBuffer.append("External References");
            stringBuffer.append(" nSheets=");
            stringBuffer.append(this.field_1_number_of_sheets);
            stringBuffer.append(" url=");
            stringBuffer.append(this.field_2_encoded_url);
        } else if (this._isAddInFunctions) {
            stringBuffer.append("Add-In Functions");
        } else {
            stringBuffer.append("Internal References ");
            stringBuffer.append(" nSheets= ");
            stringBuffer.append(this.field_1_number_of_sheets);
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        if (!isExternalReferences()) {
            return 4;
        }
        int encodedSize = StringUtil.getEncodedSize(this.field_2_encoded_url) + 2;
        int i = 0;
        while (true) {
            String[] strArr = this.field_3_sheet_names;
            if (i >= strArr.length) {
                return encodedSize;
            }
            encodedSize += StringUtil.getEncodedSize(strArr[i]);
            i++;
        }
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_number_of_sheets);
        if (isExternalReferences()) {
            StringUtil.writeUnicodeString(littleEndianOutput, this.field_2_encoded_url);
            int i = 0;
            while (true) {
                String[] strArr = this.field_3_sheet_names;
                if (i < strArr.length) {
                    StringUtil.writeUnicodeString(littleEndianOutput, strArr[i]);
                    i++;
                } else {
                    return;
                }
            }
        } else {
            littleEndianOutput.writeShort(this._isAddInFunctions ? 14849 : InputDeviceCompat.SOURCE_GAMEPAD);
        }
    }

    public void setNumberOfSheets(short s) {
        this.field_1_number_of_sheets = s;
    }

    public short getNumberOfSheets() {
        return this.field_1_number_of_sheets;
    }

    public String getURL() {
        String str = this.field_2_encoded_url;
        char charAt = str.charAt(0);
        if (charAt == 0) {
            return str.substring(1);
        }
        if (charAt == 1) {
            return decodeFileName(str);
        }
        if (charAt != 2) {
            return str;
        }
        return str.substring(1);
    }

    private static String decodeFileName(String str) {
        return str.substring(1);
    }

    public String[] getSheetNames() {
        return (String[]) this.field_3_sheet_names.clone();
    }
}
