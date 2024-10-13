package com.app.office.pg.model;

import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.IAttributeSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PGMaster {
    private BackgroundAndFill bgFill;
    private PGStyle bodyStyle;
    private int index = -1;
    private PGStyle otherStyle;
    private Map<String, Integer> schemeColor = new HashMap();
    private Map<Integer, PGStyle> styleByIdx = new HashMap();
    private Map<String, PGStyle> styleByType = new HashMap();
    private PGStyle titleStyle;
    private Map<Integer, Integer> titlebodyID;

    public int getColor(String str) {
        return this.schemeColor.get(str).intValue();
    }

    public void addColor(String str, int i) {
        this.schemeColor.put(str, Integer.valueOf(i));
    }

    public BackgroundAndFill getBackgroundAndFill() {
        return this.bgFill;
    }

    public void setBackgroundAndFill(BackgroundAndFill backgroundAndFill) {
        this.bgFill = backgroundAndFill;
    }

    public void addStyleByType(String str, PGStyle pGStyle) {
        this.styleByType.put(str, pGStyle);
    }

    public void addStyleByIdx(int i, PGStyle pGStyle) {
        this.styleByIdx.put(Integer.valueOf(i), pGStyle);
    }

    public void setTitleStyle(PGStyle pGStyle) {
        this.titleStyle = pGStyle;
    }

    public void setBodyStyle(PGStyle pGStyle) {
        this.bodyStyle = pGStyle;
    }

    public void setDefaultStyle(PGStyle pGStyle) {
        this.otherStyle = pGStyle;
    }

    public Rectangle getAnchor(String str, int i) {
        String checkTypeName = PGPlaceholderUtil.instance().checkTypeName(str);
        if (!PGPlaceholderUtil.instance().isBody(checkTypeName)) {
            PGStyle pGStyle = this.styleByType.get(checkTypeName);
            if (pGStyle != null) {
                return pGStyle.getAnchor();
            }
            return null;
        } else if (i <= 0) {
            return null;
        } else {
            PGStyle pGStyle2 = this.styleByIdx.get(Integer.valueOf(i));
            if (pGStyle2 == null) {
                Iterator<Integer> it = this.styleByIdx.keySet().iterator();
                if (it.hasNext()) {
                    pGStyle2 = this.styleByIdx.get(it.next());
                }
            }
            if (pGStyle2 != null) {
                return pGStyle2.getAnchor();
            }
            return null;
        }
    }

    public IAttributeSet getSectionAttr(String str, int i) {
        String checkTypeName = PGPlaceholderUtil.instance().checkTypeName(str);
        if (!PGPlaceholderUtil.instance().isBody(checkTypeName)) {
            PGStyle pGStyle = this.styleByType.get(checkTypeName);
            if (pGStyle != null) {
                return pGStyle.getSectionAttr();
            }
            return null;
        } else if (i <= 0) {
            return null;
        } else {
            PGStyle pGStyle2 = this.styleByIdx.get(Integer.valueOf(i));
            if (pGStyle2 == null) {
                Iterator<Integer> it = this.styleByIdx.keySet().iterator();
                if (it.hasNext()) {
                    pGStyle2 = this.styleByIdx.get(it.next());
                }
            }
            if (pGStyle2 != null) {
                return pGStyle2.getSectionAttr();
            }
            return null;
        }
    }

    public int getTextStyle(String str, int i, int i2) {
        int style;
        int style2;
        String checkTypeName = PGPlaceholderUtil.instance().checkTypeName(str);
        if (!PGPlaceholderUtil.instance().isBody(checkTypeName)) {
            PGStyle pGStyle = this.styleByType.get(checkTypeName);
            if (pGStyle != null && (style2 = pGStyle.getStyle(i2)) >= 0) {
                return style2;
            }
            if ("title".equals(checkTypeName)) {
                PGStyle pGStyle2 = this.titleStyle;
                if (pGStyle2 != null) {
                    return pGStyle2.getStyle(i2);
                }
                return -1;
            }
            PGStyle pGStyle3 = this.otherStyle;
            if (pGStyle3 != null) {
                return pGStyle3.getStyle(i2);
            }
            return -1;
        } else if (i <= 0) {
            return -1;
        } else {
            PGStyle pGStyle4 = this.styleByIdx.get(Integer.valueOf(i));
            if (pGStyle4 == null) {
                Iterator<Integer> it = this.styleByIdx.keySet().iterator();
                if (it.hasNext()) {
                    pGStyle4 = this.styleByIdx.get(it.next());
                }
            }
            if (pGStyle4 != null && (style = pGStyle4.getStyle(i2)) >= 0) {
                return style;
            }
            PGStyle pGStyle5 = this.bodyStyle;
            if (pGStyle5 != null) {
                return pGStyle5.getStyle(i2);
            }
            return -1;
        }
    }

    public Map<String, Integer> getSchemeColor() {
        return this.schemeColor;
    }

    public int getSlideMasterIndex() {
        return this.index;
    }

    public void setSlideMasterIndex(int i) {
        this.index = i;
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

    public void dispose() {
        BackgroundAndFill backgroundAndFill = this.bgFill;
        if (backgroundAndFill != null) {
            backgroundAndFill.dispose();
            this.bgFill = null;
        }
        this.schemeColor.clear();
        this.schemeColor = null;
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
        PGStyle pGStyle = this.titleStyle;
        if (pGStyle != null) {
            pGStyle.dispose();
            this.titleStyle = null;
        }
        PGStyle pGStyle2 = this.bodyStyle;
        if (pGStyle2 != null) {
            pGStyle2.dispose();
            this.bodyStyle = null;
        }
        PGStyle pGStyle3 = this.otherStyle;
        if (pGStyle3 != null) {
            pGStyle3.dispose();
            this.otherStyle = null;
        }
        Map<Integer, Integer> map3 = this.titlebodyID;
        if (map3 != null) {
            map3.clear();
            this.titlebodyID = null;
        }
    }
}
