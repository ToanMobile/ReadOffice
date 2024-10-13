package com.app.office.thirdpart.emf.font;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;

public class TTFFile extends TTFFont {
    private static final String mode = "r";
    private int entrySelector = this.ttf.readUnsignedShort();
    private String fileName;
    private int numberOfTables = this.ttf.readUnsignedShort();
    private int rangeShift = this.ttf.readUnsignedShort();
    private int searchRange = this.ttf.readUnsignedShort();
    private int sfntMajorVersion = this.ttf.readUnsignedShort();
    private int sfntMinorVersion = this.ttf.readUnsignedShort();
    private RandomAccessFile ttf;

    public TTFFile(String str) throws FileNotFoundException, IOException {
        this.fileName = str;
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, mode);
        this.ttf = randomAccessFile;
        randomAccessFile.seek(0);
        for (int i = 0; i < this.numberOfTables; i++) {
            this.ttf.seek((long) ((i * 16) + 12));
            byte[] bArr = new byte[4];
            this.ttf.readFully(bArr);
            String str2 = new String(bArr);
            int readInt = this.ttf.readInt();
            newTable(str2, new TTFFileInput(this.ttf, (long) this.ttf.readInt(), (long) this.ttf.readInt(), (long) readInt));
        }
    }

    public int getFontVersion() {
        return this.sfntMajorVersion;
    }

    public void close() throws IOException {
        super.close();
        this.ttf.close();
    }

    public void show() {
        super.show();
        PrintStream printStream = System.out;
        printStream.println("Font: " + this.fileName);
        PrintStream printStream2 = System.out;
        printStream2.println("  sfnt: " + this.sfntMajorVersion + "." + this.sfntMinorVersion);
        PrintStream printStream3 = System.out;
        StringBuilder sb = new StringBuilder();
        sb.append("  numTables: ");
        sb.append(this.numberOfTables);
        printStream3.println(sb.toString());
        PrintStream printStream4 = System.out;
        printStream4.println("  searchRange: " + this.searchRange);
        PrintStream printStream5 = System.out;
        printStream5.println("  entrySelector: " + this.entrySelector);
        PrintStream printStream6 = System.out;
        printStream6.println("  rangeShift: " + this.rangeShift);
    }
}
