package com.app.office.simpletext.font;

import android.graphics.Typeface;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FontTypefaceManage {
    private static FontTypefaceManage kit;
    private List<String> sysFontName;
    private LinkedHashMap<String, Typeface> tfs;

    public void dispose() {
    }

    public static FontTypefaceManage instance() {
        if (kit == null) {
            kit = new FontTypefaceManage();
        }
        return kit;
    }

    public int addFontName(String str) {
        if (this.sysFontName == null) {
            this.sysFontName = new ArrayList();
        }
        int indexOf = this.sysFontName.indexOf(str);
        if (indexOf >= 0) {
            return indexOf;
        }
        int size = this.sysFontName.size();
        this.sysFontName.add(str);
        return size;
    }

    public Typeface getFontTypeface(int i) {
        String str;
        if (this.tfs == null) {
            this.tfs = new LinkedHashMap<>();
        }
        String str2 = "sans-serif";
        if (i < 0) {
            str = str2;
        } else {
            str = this.sysFontName.get(i);
        }
        if (str != null) {
            str2 = str;
        }
        Typeface typeface = this.tfs.get(str2);
        if (typeface == null) {
            typeface = Typeface.create(str2, 0);
            if (typeface == null) {
                typeface = Typeface.DEFAULT;
            }
            this.tfs.put(str2, typeface);
        }
        return typeface;
    }
}
