package com.app.office.thirdpart.emf.font;

import com.itextpdf.text.pdf.PdfObject;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;

public class TTFNameTable extends TTFTable {
    private int format;
    private String[][] name = ((String[][]) Array.newInstance(String.class, new int[]{4, 19}));
    private int numberOfNameRecords;
    private int stringStorage;

    public String getTag() {
        return "name";
    }

    public void readTable() throws IOException {
        this.format = this.ttf.readUShort();
        this.numberOfNameRecords = this.ttf.readUShort();
        this.stringStorage = this.ttf.readUShort();
        for (int i = 0; i < this.numberOfNameRecords; i++) {
            int readUShort = this.ttf.readUShort();
            int readUShort2 = this.ttf.readUShort();
            int readUShort3 = this.ttf.readUShort();
            int readUShort4 = this.ttf.readUShort();
            int readUShort5 = this.ttf.readUShort();
            int readUShort6 = this.ttf.readUShort();
            this.ttf.pushPos();
            this.ttf.seek((long) (this.stringStorage + readUShort6));
            byte[] bArr = new byte[readUShort5];
            this.ttf.readFully(bArr);
            if (readUShort == 0) {
                this.name[readUShort][readUShort4] = new String(bArr, PdfObject.TEXT_UNICODE);
            } else if (readUShort == 1 && readUShort2 == 0) {
                if (readUShort3 == 0) {
                    this.name[readUShort][readUShort4] = new String(bArr, "ISO8859-1");
                }
            } else if (readUShort != 3 || readUShort2 != 1) {
                PrintStream printStream = System.out;
                printStream.println("Unimplemented PID, EID, LID scheme: " + readUShort + ", " + readUShort2 + ", " + readUShort3);
                PrintStream printStream2 = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("NID = ");
                sb.append(readUShort4);
                printStream2.println(sb.toString());
                this.name[readUShort][readUShort4] = new String(bArr, "Default");
            } else if (readUShort3 == 1033) {
                this.name[readUShort][readUShort4] = new String(bArr, PdfObject.TEXT_UNICODE);
            }
            this.ttf.popPos();
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString() + "\n");
        stringBuffer.append("  format: " + this.format);
        for (int i = 0; i < this.name.length; i++) {
            int i2 = 0;
            while (true) {
                String[][] strArr = this.name;
                if (i2 >= strArr[i].length) {
                    break;
                }
                if (strArr[i][i2] != null) {
                    stringBuffer.append("\n  name[" + i + "][" + i2 + "]: " + this.name[i][i2]);
                }
                i2++;
            }
        }
        return stringBuffer.toString();
    }
}
