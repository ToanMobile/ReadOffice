package com.app.office.fc.hssf.record.common;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class FeatProtection implements SharedFeature {
    public static long HAS_SELF_RELATIVE_SECURITY_FEATURE = 1;
    public static long NO_SELF_RELATIVE_SECURITY_FEATURE;
    private int fSD;
    private int passwordVerifier;
    private byte[] securityDescriptor;
    private String title;

    public FeatProtection() {
        this.securityDescriptor = new byte[0];
    }

    public FeatProtection(RecordInputStream recordInputStream) {
        this.fSD = recordInputStream.readInt();
        this.passwordVerifier = recordInputStream.readInt();
        this.title = StringUtil.readUnicodeString(recordInputStream);
        this.securityDescriptor = recordInputStream.readRemainder();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" [FEATURE PROTECTION]\n");
        stringBuffer.append("   Self Relative = " + this.fSD);
        stringBuffer.append("   Password Verifier = " + this.passwordVerifier);
        stringBuffer.append("   Title = " + this.title);
        stringBuffer.append("   Security Descriptor Size = " + this.securityDescriptor.length);
        stringBuffer.append(" [/FEATURE PROTECTION]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(this.fSD);
        littleEndianOutput.writeInt(this.passwordVerifier);
        StringUtil.writeUnicodeString(littleEndianOutput, this.title);
        littleEndianOutput.write(this.securityDescriptor);
    }

    public int getDataSize() {
        return StringUtil.getEncodedSize(this.title) + 8 + this.securityDescriptor.length;
    }

    public int getPasswordVerifier() {
        return this.passwordVerifier;
    }

    public void setPasswordVerifier(int i) {
        this.passwordVerifier = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public int getFSD() {
        return this.fSD;
    }
}
