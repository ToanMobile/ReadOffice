package com.app.office.thirdpart.emf.font;

import java.io.IOException;
import java.io.PrintStream;

public class TTFCMapTable extends TTFTable {
    public EncodingTable[] encodingTable;
    public int version;

    public String getTag() {
        return "cmap";
    }

    public class EncodingTable {
        public int encodingID;
        public int format;
        public int length;
        public long offset;
        public int platformID;
        public TableFormat tableFormat;
        public int version;

        public EncodingTable() {
        }

        public void readHeader() throws IOException {
            this.platformID = TTFCMapTable.this.ttf.readUShort();
            this.encodingID = TTFCMapTable.this.ttf.readUShort();
            this.offset = TTFCMapTable.this.ttf.readULong();
        }

        public void readBody() throws IOException {
            TTFCMapTable.this.ttf.seek(this.offset);
            this.format = TTFCMapTable.this.ttf.readUShort();
            this.length = TTFCMapTable.this.ttf.readUShort();
            this.version = TTFCMapTable.this.ttf.readUShort();
            int i = this.format;
            if (i != 0) {
                if (i != 2) {
                    if (i == 4) {
                        this.tableFormat = new TableFormat4();
                    } else if (i != 6) {
                        PrintStream printStream = System.err;
                        printStream.println("Illegal value for encoding table format: " + this.format);
                    }
                }
                PrintStream printStream2 = System.err;
                printStream2.println("Unimplementet encoding table format: " + this.format);
            } else {
                this.tableFormat = new TableFormat0();
            }
            TableFormat tableFormat2 = this.tableFormat;
            if (tableFormat2 != null) {
                tableFormat2.read();
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[encoding] PID:");
            sb.append(this.platformID);
            sb.append(" EID:");
            sb.append(this.encodingID);
            sb.append(" format:");
            sb.append(this.format);
            sb.append(" v");
            sb.append(this.version);
            TableFormat tableFormat2 = this.tableFormat;
            sb.append(tableFormat2 != null ? tableFormat2.toString() : " [no data read]");
            return sb.toString();
        }
    }

    public abstract class TableFormat {
        public abstract int getGlyphIndex(int i);

        public abstract void read() throws IOException;

        public TableFormat() {
        }
    }

    public class TableFormat0 extends TableFormat {
        public int[] glyphIdArray = new int[256];

        public TableFormat0() {
            super();
        }

        public void read() throws IOException {
            int i = 0;
            while (true) {
                int[] iArr = this.glyphIdArray;
                if (i < iArr.length) {
                    iArr[i] = TTFCMapTable.this.ttf.readByte();
                    i++;
                } else {
                    return;
                }
            }
        }

        public String toString() {
            String str = "";
            for (int i = 0; i < this.glyphIdArray.length; i++) {
                if (i % 16 == 0) {
                    str = str + "\n    " + Integer.toHexString(i / 16) + "x: ";
                }
                String str2 = this.glyphIdArray[i] + "";
                while (str2.length() < 3) {
                    str2 = " " + str2;
                }
                str = str + str2 + " ";
            }
            return str;
        }

        public int getGlyphIndex(int i) {
            return this.glyphIdArray[i];
        }
    }

    public class TableFormat4 extends TableFormat {
        public int[] endCount;
        public short[] idDelta;
        public int[] idRangeOffset;
        public int segCount;
        public int[] startCount;

        public int getGlyphIndex(int i) {
            return 0;
        }

        public TableFormat4() {
            super();
        }

        public void read() throws IOException {
            this.segCount = TTFCMapTable.this.ttf.readUShort() / 2;
            TTFCMapTable.this.ttf.readUShort();
            TTFCMapTable.this.ttf.readUShort();
            TTFCMapTable.this.ttf.readUShort();
            this.endCount = TTFCMapTable.this.ttf.readUShortArray(this.segCount);
            int readUShort = TTFCMapTable.this.ttf.readUShort();
            if (readUShort != 0) {
                PrintStream printStream = System.err;
                printStream.println("reservedPad not 0, but " + readUShort + ".");
            }
            this.startCount = TTFCMapTable.this.ttf.readUShortArray(this.endCount.length);
            this.idDelta = TTFCMapTable.this.ttf.readShortArray(this.endCount.length);
            this.idRangeOffset = TTFCMapTable.this.ttf.readUShortArray(this.endCount.length);
        }

        public String toString() {
            String str = "\n   " + this.endCount.length + " sections:";
            for (int i = 0; i < this.endCount.length; i++) {
                str = str + "\n    " + this.startCount[i] + " to " + this.endCount[i] + " : " + this.idDelta[i] + " (" + this.idRangeOffset[i] + ")";
            }
            return str;
        }
    }

    public void readTable() throws IOException {
        this.version = this.ttf.readUShort();
        this.encodingTable = new EncodingTable[this.ttf.readUShort()];
        int i = 0;
        int i2 = 0;
        while (true) {
            EncodingTable[] encodingTableArr = this.encodingTable;
            if (i2 >= encodingTableArr.length) {
                break;
            }
            encodingTableArr[i2] = new EncodingTable();
            this.encodingTable[i2].readHeader();
            i2++;
        }
        while (true) {
            EncodingTable[] encodingTableArr2 = this.encodingTable;
            if (i < encodingTableArr2.length) {
                encodingTableArr2[i].readBody();
                i++;
            } else {
                return;
            }
        }
    }

    public String toString() {
        String str = super.toString() + " v" + this.version;
        for (int i = 0; i < this.encodingTable.length; i++) {
            str = str + "\n  " + this.encodingTable[i];
        }
        return str;
    }
}
