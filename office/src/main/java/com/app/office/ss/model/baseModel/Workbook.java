package com.app.office.ss.model.baseModel;

import android.os.Message;
import com.app.office.common.picture.Picture;
import com.app.office.simpletext.font.Font;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.ss.model.table.TableFormatManager;
import com.app.office.system.ReaderHandler;
import java.util.HashMap;
import java.util.Map;

public class Workbook {
    public static final int MAXCOLUMN_03 = 256;
    public static final int MAXCOLUMN_07 = 16384;
    public static final int MAXROW_03 = 65536;
    public static final int MAXROW_07 = 1048576;
    private boolean before07;
    protected Map<Integer, CellStyle> cellStyles = new HashMap(80);
    protected Map<Integer, Integer> colors = new HashMap(20);
    protected Map<Integer, Font> fonts = new HashMap(20);
    protected boolean isUsing1904DateWindowing;
    private Map<Integer, Picture> pictures = new HashMap();
    protected ReaderHandler readerHandler;
    private Map<String, Integer> schemeColor = new HashMap(20);
    protected Map<Integer, Object> sharedString = new HashMap(20);
    /* access modifiers changed from: protected */
    public Map<Integer, Sheet> sheets = new HashMap(5);
    private TableFormatManager tableFormatManager;
    private Map<Integer, Integer> themeColor = new HashMap(20);

    public Workbook(boolean z) {
        this.before07 = z;
    }

    public static boolean isValidateStyle(CellStyle cellStyle) {
        if (cellStyle == null) {
            return false;
        }
        return cellStyle.getBorderLeft() > 0 || cellStyle.getBorderTop() > 0 || cellStyle.getBorderRight() > 0 || cellStyle.getBorderBottom() > 0 || cellStyle.getFillPatternType() != -1;
    }

    public void addSheet(int i, Sheet sheet) {
        this.sheets.put(Integer.valueOf(i), sheet);
    }

    public void setReaderHandler(ReaderHandler readerHandler2) {
        this.readerHandler = readerHandler2;
    }

    public ReaderHandler getReaderHandler() {
        return this.readerHandler;
    }

    public Sheet getSheet(String str) {
        for (Sheet next : this.sheets.values()) {
            if (next.getSheetName().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public int getSheetIndex(Sheet sheet) {
        for (Integer intValue : this.sheets.keySet()) {
            int intValue2 = intValue.intValue();
            if (this.sheets.get(Integer.valueOf(intValue2)).equals(sheet)) {
                return intValue2;
            }
        }
        return -1;
    }

    public Sheet getSheet(int i) {
        if (i < 0 || i >= this.sheets.size()) {
            return null;
        }
        return this.sheets.get(Integer.valueOf(i));
    }

    public int getSheetCount() {
        return this.sheets.size();
    }

    public void addFont(int i, Font font) {
        this.fonts.put(Integer.valueOf(i), font);
    }

    public Font getFont(int i) {
        return this.fonts.get(Integer.valueOf(i));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003b, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int addColor(int r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.colors     // Catch:{ all -> 0x0062 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0062 }
            boolean r0 = r0.containsValue(r1)     // Catch:{ all -> 0x0062 }
            if (r0 == 0) goto L_0x003c
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.colors     // Catch:{ all -> 0x0062 }
            java.util.Set r0 = r0.keySet()     // Catch:{ all -> 0x0062 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0062 }
            r1 = 0
        L_0x0018:
            boolean r2 = r0.hasNext()     // Catch:{ all -> 0x0062 }
            if (r2 == 0) goto L_0x003a
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x0062 }
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch:{ all -> 0x0062 }
            int r1 = r1.intValue()     // Catch:{ all -> 0x0062 }
            java.util.Map<java.lang.Integer, java.lang.Integer> r2 = r4.colors     // Catch:{ all -> 0x0062 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x0062 }
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x0062 }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ all -> 0x0062 }
            int r2 = r2.intValue()     // Catch:{ all -> 0x0062 }
            if (r2 != r5) goto L_0x0018
        L_0x003a:
            monitor-exit(r4)
            return r1
        L_0x003c:
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.colors     // Catch:{ all -> 0x0062 }
            int r0 = r0.size()     // Catch:{ all -> 0x0062 }
            int r0 = r0 + -1
        L_0x0044:
            java.util.Map<java.lang.Integer, java.lang.Integer> r1 = r4.colors     // Catch:{ all -> 0x0062 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x0062 }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x0062 }
            if (r1 == 0) goto L_0x0053
            int r0 = r0 + 1
            goto L_0x0044
        L_0x0053:
            java.util.Map<java.lang.Integer, java.lang.Integer> r1 = r4.colors     // Catch:{ all -> 0x0062 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x0062 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0062 }
            r1.put(r2, r5)     // Catch:{ all -> 0x0062 }
            monitor-exit(r4)
            return r0
        L_0x0062:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.model.baseModel.Workbook.addColor(int):int");
    }

    public synchronized void addColor(int i, int i2) {
        this.colors.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public int getColor(int i) {
        return getColor(i, false);
    }

    public synchronized int getColor(int i, boolean z) {
        Integer num = this.colors.get(Integer.valueOf(i));
        if (num == null && i >= 0 && i <= 7) {
            num = this.colors.get(8);
        }
        if (num != null) {
            return num.intValue();
        } else if (z) {
            return -16777216;
        } else {
            return -1;
        }
    }

    public void addCellStyle(int i, CellStyle cellStyle) {
        this.cellStyles.put(Integer.valueOf(i), cellStyle);
    }

    public int getNumStyles() {
        return this.cellStyles.size();
    }

    public CellStyle getCellStyle(int i) {
        return this.cellStyles.get(Integer.valueOf(i));
    }

    public int addSharedString(Object obj) {
        if (obj == null) {
            return -1;
        }
        Map<Integer, Object> map = this.sharedString;
        map.put(Integer.valueOf(map.size()), obj);
        return this.sharedString.size() - 1;
    }

    public void addSharedString(int i, Object obj) {
        this.sharedString.put(Integer.valueOf(i), obj);
    }

    public String getSharedString(int i) {
        Object obj = this.sharedString.get(Integer.valueOf(i));
        if (obj instanceof SectionElement) {
            return ((SectionElement) obj).getText((IDocument) null);
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    public Object getSharedItem(int i) {
        return this.sharedString.get(Integer.valueOf(i));
    }

    public synchronized void addThemeColorIndex(int i, int i2) {
        this.themeColor.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public synchronized int getThemeColorIndex(int i) {
        Integer num = this.themeColor.get(Integer.valueOf(i));
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public synchronized int getThemeColor(int i) {
        Integer num = this.colors.get(this.themeColor.get(Integer.valueOf(i)));
        if (num == null) {
            return -16777216;
        }
        return num.intValue();
    }

    public synchronized void addSchemeColorIndex(String str, int i) {
        this.schemeColor.put(str, Integer.valueOf(i));
    }

    public synchronized int getSchemeColorIndex(String str) {
        Integer num = this.schemeColor.get(str);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public synchronized int getSchemeColor(String str) {
        Integer num = this.colors.get(this.schemeColor.get(str));
        if (num == null) {
            return -16777216;
        }
        return num.intValue();
    }

    public boolean isUsing1904DateWindowing() {
        return this.isUsing1904DateWindowing;
    }

    public void setUsing1904DateWindowing(boolean z) {
        this.isUsing1904DateWindowing = z;
    }

    public void addPicture(int i, Picture picture) {
        this.pictures.put(Integer.valueOf(i), picture);
    }

    public int addPicture(Picture picture) {
        int i = 0;
        for (Integer intValue : this.pictures.keySet()) {
            i = intValue.intValue();
            if (this.pictures.get(Integer.valueOf(i)).getTempFilePath().equals(picture.getTempFilePath())) {
                return i;
            }
        }
        int i2 = i + 1;
        this.pictures.put(Integer.valueOf(i2), picture);
        return i2;
    }

    public Picture getPicture(int i) {
        return this.pictures.get(Integer.valueOf(i));
    }

    public boolean isBefore07Version() {
        return this.before07;
    }

    public int getMaxRow() {
        return this.before07 ? 65536 : 1048576;
    }

    public int getMaxColumn() {
        return this.before07 ? 256 : 16384;
    }

    public void setTableFormatManager(TableFormatManager tableFormatManager2) {
        this.tableFormatManager = tableFormatManager2;
    }

    public TableFormatManager getTableFormatManager() {
        return this.tableFormatManager;
    }

    public void destroy() {
        if (this.readerHandler != null) {
            Message message = new Message();
            message.what = 4;
            this.readerHandler.handleMessage(message);
            this.readerHandler = null;
        }
        Map<Integer, Sheet> map = this.sheets;
        if (map != null) {
            for (Sheet dispose : map.values()) {
                dispose.dispose();
            }
            this.sheets.clear();
            this.sheets = null;
        }
        Map<Integer, Font> map2 = this.fonts;
        if (map2 != null) {
            for (Font dispose2 : map2.values()) {
                dispose2.dispose();
            }
            this.fonts.clear();
            this.fonts = null;
        }
        Map<Integer, Integer> map3 = this.colors;
        if (map3 != null) {
            map3.clear();
            this.colors = null;
        }
        Map<Integer, Picture> map4 = this.pictures;
        if (map4 != null) {
            map4.clear();
            this.pictures = null;
        }
        Map<Integer, CellStyle> map5 = this.cellStyles;
        if (map5 != null) {
            for (CellStyle dispose3 : map5.values()) {
                dispose3.dispose();
            }
            this.cellStyles.clear();
            this.cellStyles = null;
        }
        Map<Integer, Object> map6 = this.sharedString;
        if (map6 != null) {
            map6.clear();
            this.sharedString = null;
        }
        Map<Integer, Integer> map7 = this.themeColor;
        if (map7 != null) {
            map7.clear();
            this.themeColor = null;
        }
        Map<String, Integer> map8 = this.schemeColor;
        if (map8 != null) {
            map8.clear();
            this.schemeColor = null;
        }
    }

    public void dispose() {
        synchronized (this) {
            destroy();
        }
    }
}
