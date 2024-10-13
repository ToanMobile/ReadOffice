package com.app.office.ss.model.table;

import com.app.office.ss.model.style.CellStyle;
import java.util.HashMap;
import java.util.Map;

public class TableFormatManager {
    private Map<Integer, CellStyle> formats;

    public TableFormatManager(int i) {
        this.formats = new HashMap(i);
    }

    public int addFormat(CellStyle cellStyle) {
        int size = this.formats.size();
        this.formats.put(Integer.valueOf(size), cellStyle);
        return size;
    }

    public CellStyle getFormat(int i) {
        if (i < 0 || i >= this.formats.size()) {
            return null;
        }
        return this.formats.get(Integer.valueOf(i));
    }

    public void dispose() {
        this.formats.clear();
        this.formats = null;
    }
}
