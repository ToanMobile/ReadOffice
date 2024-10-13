package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.record.common.UnicodeString;
import com.app.office.fc.util.IntMapper;
import java.io.PrintStream;

class SSTDeserializer {
    private IntMapper<UnicodeString> strings;

    public SSTDeserializer(IntMapper<UnicodeString> intMapper) {
        this.strings = intMapper;
    }

    public void manufactureStrings(int i, RecordInputStream recordInputStream) {
        UnicodeString unicodeString;
        for (int i2 = 0; i2 < i; i2++) {
            if (recordInputStream.available() != 0 || recordInputStream.hasNextRecord()) {
                unicodeString = new UnicodeString(recordInputStream);
            } else {
                PrintStream printStream = System.err;
                printStream.println("Ran out of data before creating all the strings! String at index " + i2 + "");
                unicodeString = new UnicodeString("");
            }
            addToStringTable(this.strings, unicodeString);
        }
    }

    public static void addToStringTable(IntMapper<UnicodeString> intMapper, UnicodeString unicodeString) {
        intMapper.add(unicodeString);
    }
}
