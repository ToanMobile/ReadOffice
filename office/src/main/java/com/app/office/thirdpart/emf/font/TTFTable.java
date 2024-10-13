package com.app.office.thirdpart.emf.font;

import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import java.io.IOException;
import java.io.PrintStream;

public abstract class TTFTable {
    public static final Class[] TABLE_CLASSES = {TTFCMapTable.class, TTFGlyfTable.class, TTFHeadTable.class, TTFHHeaTable.class, TTFHMtxTable.class, TTFLocaTable.class, TTFMaxPTable.class, TTFNameTable.class, TTFOS_2Table.class, TTFPostTable.class};
    public static final String[] TT_TAGS = {"cmap", "glyf", "head", "hhea", "hmtx", "loca", "maxp", "name", "OS/2", "post"};
    private boolean isRead = false;
    TTFInput ttf;
    private TTFFont ttfFont;

    public abstract String getTag();

    public abstract void readTable() throws IOException;

    public void init(TTFFont tTFFont, TTFInput tTFInput) throws IOException {
        this.ttfFont = tTFFont;
        this.ttf = tTFInput;
    }

    public void read() throws IOException {
        this.ttf.pushPos();
        PrintStream printStream = System.out;
        printStream.print("[" + getTag());
        this.ttf.seek(0);
        readTable();
        this.isRead = true;
        System.out.print("]");
        this.ttf.popPos();
    }

    public boolean isRead() {
        return this.isRead;
    }

    public TTFTable getTable(String str) throws IOException {
        return this.ttfFont.getTable(str);
    }

    public String toString() {
        return this.ttf + ": [" + getTag() + PackagingURIHelper.FORWARD_SLASH_STRING + getClass().getName() + "]";
    }
}
