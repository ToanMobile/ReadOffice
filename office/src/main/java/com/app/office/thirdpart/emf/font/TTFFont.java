package com.app.office.thirdpart.emf.font;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public abstract class TTFFont {
    private Map entry = new HashMap();

    public void close() throws IOException {
    }

    public abstract int getFontVersion();

    /* access modifiers changed from: package-private */
    public void newTable(String str, TTFInput tTFInput) throws IOException {
        this.entry.put(str, initTable(str, tTFInput));
    }

    private Object initTable(String str, TTFInput tTFInput) throws IOException {
        int i = 0;
        while (i < TTFTable.TT_TAGS.length) {
            if (str.equals(TTFTable.TT_TAGS[i])) {
                try {
                    TTFTable tTFTable = (TTFTable) TTFTable.TABLE_CLASSES[i].newInstance();
                    tTFTable.init(this, tTFInput);
                    return tTFTable;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                i++;
            }
        }
        PrintStream printStream = System.err;
        printStream.println("Table '" + str + "' ignored.");
        return null;
    }

    public void show() {
        System.out.println("Tables:");
        for (Object println : this.entry.values()) {
            System.out.println(println);
        }
    }

    public TTFTable getTable(String str) throws IOException {
        TTFTable tTFTable = (TTFTable) this.entry.get(str);
        if (!tTFTable.isRead()) {
            tTFTable.read();
        }
        return tTFTable;
    }

    public void readAll() throws IOException {
        for (TTFTable tTFTable : this.entry.values()) {
            if (tTFTable != null && !tTFTable.isRead()) {
                tTFTable.read();
            }
        }
    }
}
