package com.app.office.pg.model;

import com.app.office.java.awt.Dimension;
import com.app.office.pg.model.tableStyle.TableStyle;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.STDocument;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PGModel {
    private IDocument doc = new STDocument();
    private boolean omitTitleSlide = false;
    private Dimension pageSize;
    private List<PGSlide> slideMasters = new ArrayList();
    private int slideNumberOffset = 0;
    private List<PGSlide> slides = new ArrayList();
    private Map<String, TableStyle> tableStyleMap;
    private int total = 0;

    public synchronized void appendSlide(PGSlide pGSlide) {
        List<PGSlide> list = this.slides;
        if (list != null) {
            list.add(pGSlide);
        }
    }

    public PGSlide getSlide(int i) {
        if (i < 0 || i >= this.slides.size()) {
            return null;
        }
        return this.slides.get(i);
    }

    public PGSlide getSlideForSlideNo(int i) {
        for (PGSlide next : this.slides) {
            if (next.getSlideNo() == i) {
                return next;
            }
        }
        return null;
    }

    public int getRealSlideCount() {
        List<PGSlide> list = this.slides;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public int getSlideCount() {
        return this.total;
    }

    public void setSlideCount(int i) {
        this.total = i;
    }

    public IDocument getRenderersDoc() {
        return this.doc;
    }

    public Dimension getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Dimension dimension) {
        this.pageSize = dimension;
    }

    public int appendSlideMaster(PGSlide pGSlide) {
        int size = this.slideMasters.size();
        this.slideMasters.add(pGSlide);
        return size;
    }

    public PGSlide getSlideMaster(int i) {
        if (i < 0 || i >= this.slideMasters.size()) {
            return null;
        }
        return this.slideMasters.get(i);
    }

    public int getSlideMasterCount() {
        List<PGSlide> list = this.slideMasters;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void putTableStyle(String str, TableStyle tableStyle) {
        if (this.tableStyleMap == null) {
            this.tableStyleMap = new HashMap();
        }
        if (str != null && tableStyle != null) {
            this.tableStyleMap.put(str, tableStyle);
        }
    }

    public TableStyle getTableStyle(String str) {
        Map<String, TableStyle> map = this.tableStyleMap;
        if (map == null || str == null) {
            return null;
        }
        return map.get(str);
    }

    public int getSlideNumberOffset() {
        return this.slideNumberOffset;
    }

    public void setSlideNumberOffset(int i) {
        this.slideNumberOffset = i;
    }

    public boolean isOmitTitleSlide() {
        return this.omitTitleSlide;
    }

    public void setOmitTitleSlide(boolean z) {
        this.omitTitleSlide = z;
    }

    public synchronized void dispose() {
        IDocument iDocument = this.doc;
        if (iDocument != null) {
            iDocument.dispose();
            this.doc = null;
        }
        List<PGSlide> list = this.slides;
        if (list != null) {
            for (PGSlide dispose : list) {
                dispose.dispose();
            }
            this.slides.clear();
            this.slides = null;
        }
        List<PGSlide> list2 = this.slideMasters;
        if (list2 != null) {
            for (PGSlide dispose2 : list2) {
                dispose2.dispose();
            }
            this.slideMasters.clear();
            this.slideMasters = null;
        }
        Map<String, TableStyle> map = this.tableStyleMap;
        if (map != null) {
            map.clear();
            this.tableStyleMap = null;
        }
    }
}
