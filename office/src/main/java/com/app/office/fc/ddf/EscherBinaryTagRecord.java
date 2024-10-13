package com.app.office.fc.ddf;

public class EscherBinaryTagRecord extends EscherTextboxRecord {
    public static final short RECORD_ID = 5003;

    public String getRecordName() {
        return "BinaryTagData";
    }
}
