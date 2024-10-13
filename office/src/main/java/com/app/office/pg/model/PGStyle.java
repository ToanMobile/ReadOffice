package com.app.office.pg.model;

import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.IAttributeSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class PGStyle {
    private Rectangle anchor;
    private IAttributeSet attr;
    private Map<Integer, String> defaultFontColor;
    private Map<Integer, Integer> lvlStyleIDs = new HashMap();

    public Rectangle getAnchor() {
        return this.anchor;
    }

    public void setAnchor(Rectangle rectangle) {
        this.anchor = rectangle;
    }

    public IAttributeSet getSectionAttr() {
        return this.attr;
    }

    public void setSectionAttr(IAttributeSet iAttributeSet) {
        this.attr = iAttributeSet;
    }

    public int getStyle(int i) {
        Integer num;
        if (this.lvlStyleIDs.isEmpty() || (num = this.lvlStyleIDs.get(Integer.valueOf(i))) == null) {
            return -1;
        }
        return num.intValue();
    }

    public void addStyle(int i, int i2) {
        this.lvlStyleIDs.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void addDefaultFontColor(int i, String str) {
        if (i > 0 && str != null) {
            if (this.defaultFontColor == null) {
                this.defaultFontColor = new Hashtable();
            }
            this.defaultFontColor.put(Integer.valueOf(i), str);
        }
    }

    public String getDefaultFontColor(int i) {
        Map<Integer, String> map = this.defaultFontColor;
        if (map != null) {
            return map.get(Integer.valueOf(i));
        }
        return null;
    }

    public void dispose() {
        this.anchor = null;
        IAttributeSet iAttributeSet = this.attr;
        if (iAttributeSet != null) {
            iAttributeSet.dispose();
            this.attr = null;
        }
        Map<Integer, Integer> map = this.lvlStyleIDs;
        if (map != null) {
            map.clear();
            this.lvlStyleIDs = null;
        }
        Map<Integer, String> map2 = this.defaultFontColor;
        if (map2 != null) {
            map2.clear();
            this.defaultFontColor = null;
        }
    }
}
