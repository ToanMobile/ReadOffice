package com.app.office.pg.model;

import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.IAttributeSet;
import java.util.HashMap;
import java.util.Map;

public class PGLayout {
    private boolean addShapes = true;
    private BackgroundAndFill bgFill;
    private int index = -1;
    private Map<Integer, String> shapeType;
    private Map<Integer, PGStyle> styleByIdx = new HashMap();
    private Map<String, PGStyle> styleByType = new HashMap();
    private Map<Integer, Integer> titlebodyID;

    public Rectangle getAnchor(String str, int i) {
        PGStyle pGStyle;
        if (!PGPlaceholderUtil.instance().isBody(str)) {
            PGStyle pGStyle2 = this.styleByType.get(str);
            if (pGStyle2 != null) {
                return pGStyle2.getAnchor();
            }
            return null;
        } else if (i <= 0 || (pGStyle = this.styleByIdx.get(Integer.valueOf(i))) == null) {
            return null;
        } else {
            return pGStyle.getAnchor();
        }
    }

    public IAttributeSet getSectionAttr(String str, int i) {
        PGStyle pGStyle;
        if (!PGPlaceholderUtil.instance().isBody(str)) {
            PGStyle pGStyle2 = this.styleByType.get(str);
            if (pGStyle2 != null) {
                return pGStyle2.getSectionAttr();
            }
            return null;
        } else if (i <= 0 || (pGStyle = this.styleByIdx.get(Integer.valueOf(i))) == null) {
            return null;
        } else {
            return pGStyle.getSectionAttr();
        }
    }

    public int getStyleID(String str, int i, int i2) {
        PGStyle pGStyle;
        if (!PGPlaceholderUtil.instance().isBody(str)) {
            PGStyle pGStyle2 = this.styleByType.get(str);
            if (pGStyle2 != null) {
                return pGStyle2.getStyle(i2);
            }
            return -1;
        } else if (i <= 0 || (pGStyle = this.styleByIdx.get(Integer.valueOf(i))) == null) {
            return -1;
        } else {
            return pGStyle.getStyle(i2);
        }
    }

    public void setStyleByType(String str, PGStyle pGStyle) {
        this.styleByType.put(str, pGStyle);
    }

    public void setStyleByIdx(int i, PGStyle pGStyle) {
        this.styleByIdx.put(Integer.valueOf(i), pGStyle);
    }

    public BackgroundAndFill getBackgroundAndFill() {
        return this.bgFill;
    }

    public void setBackgroundAndFill(BackgroundAndFill backgroundAndFill) {
        this.bgFill = backgroundAndFill;
    }

    public int getSlideMasterIndex() {
        return this.index;
    }

    public void setSlideMasterIndex(int i) {
        this.index = i;
    }

    public boolean isAddShapes() {
        return this.addShapes;
    }

    public void setAddShapes(boolean z) {
        this.addShapes = z;
    }

    public void addShapeType(int i, String str) {
        if (this.shapeType == null) {
            this.shapeType = new HashMap();
        }
        this.shapeType.put(Integer.valueOf(i), str);
    }

    public String getShapeType(int i) {
        Map<Integer, String> map = this.shapeType;
        if (map != null) {
            return map.get(Integer.valueOf(i));
        }
        return null;
    }

    public void addTitleBodyID(int i, int i2) {
        if (this.titlebodyID == null) {
            this.titlebodyID = new HashMap();
        }
        this.titlebodyID.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public Integer getTitleBodyID(int i) {
        Map<Integer, Integer> map = this.titlebodyID;
        if (map != null) {
            return map.get(Integer.valueOf(i));
        }
        return null;
    }

    public void disposs() {
        BackgroundAndFill backgroundAndFill = this.bgFill;
        if (backgroundAndFill != null) {
            backgroundAndFill.dispose();
            this.bgFill = null;
        }
        Map<String, PGStyle> map = this.styleByType;
        if (map != null) {
            for (String str : map.keySet()) {
                this.styleByType.get(str).dispose();
            }
            this.styleByType.clear();
            this.styleByType = null;
        }
        Map<Integer, PGStyle> map2 = this.styleByIdx;
        if (map2 != null) {
            for (Integer num : map2.keySet()) {
                this.styleByIdx.get(num).dispose();
            }
            this.styleByIdx.clear();
            this.styleByIdx = null;
        }
        Map<Integer, String> map3 = this.shapeType;
        if (map3 != null) {
            map3.clear();
            this.shapeType = null;
        }
        Map<Integer, Integer> map4 = this.titlebodyID;
        if (map4 != null) {
            map4.clear();
            this.titlebodyID = null;
        }
    }
}
