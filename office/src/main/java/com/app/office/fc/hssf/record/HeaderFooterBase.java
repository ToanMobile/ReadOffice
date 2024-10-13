package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public abstract class HeaderFooterBase extends StandardRecord {
    private boolean field_2_hasMultibyte;
    private String field_3_text;

    protected HeaderFooterBase(String str) {
        setText(str);
    }

    protected HeaderFooterBase(RecordInputStream recordInputStream) {
        if (recordInputStream.remaining() > 0) {
            short readShort = recordInputStream.readShort();
            boolean z = recordInputStream.readByte() != 0;
            this.field_2_hasMultibyte = z;
            if (z) {
                this.field_3_text = recordInputStream.readUnicodeLEString(readShort);
            } else {
                this.field_3_text = recordInputStream.readCompressedUnicode(readShort);
            }
        } else {
            this.field_3_text = "";
        }
    }

    public final void setText(String str) {
        if (str != null) {
            this.field_2_hasMultibyte = StringUtil.hasMultibyte(str);
            this.field_3_text = str;
            if (getDataSize() > 8224) {
                throw new IllegalArgumentException("Header/Footer string too long (limit is 8224 bytes)");
            }
            return;
        }
        throw new IllegalArgumentException("text must not be null");
    }

    private int getTextLength() {
        return this.field_3_text.length();
    }

    public final String getText() {
        return this.field_3_text;
    }

    public final void serialize(LittleEndianOutput littleEndianOutput) {
        if (getTextLength() > 0) {
            littleEndianOutput.writeShort(getTextLength());
            littleEndianOutput.writeByte(this.field_2_hasMultibyte ? 1 : 0);
            if (this.field_2_hasMultibyte) {
                StringUtil.putUnicodeLE(this.field_3_text, littleEndianOutput);
            } else {
                StringUtil.putCompressedUnicode(this.field_3_text, littleEndianOutput);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final int getDataSize() {
        int i = 1;
        if (getTextLength() < 1) {
            return 0;
        }
        int textLength = getTextLength();
        if (this.field_2_hasMultibyte) {
            i = 2;
        }
        return (textLength * i) + 3;
    }
}
