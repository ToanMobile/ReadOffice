package com.app.office.wp.model;

import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.constant.wp.WPModelConstant;
import com.app.office.simpletext.model.ElementCollectionImpl;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.model.STDocument;
import com.app.office.simpletext.model.SectionElement;

public class WPDocument extends STDocument {
    private BackgroundAndFill pageBG;
    private ElementCollectionImpl[] para = new ElementCollectionImpl[6];
    private ElementCollectionImpl[] root = new ElementCollectionImpl[6];
    private ElementCollectionImpl[] table = new ElementCollectionImpl[4];

    public IElement getFEElement(long j) {
        return null;
    }

    public WPDocument() {
        initRoot();
    }

    private void initRoot() {
        this.root[0] = new ElementCollectionImpl(5);
        this.root[1] = new ElementCollectionImpl(3);
        this.root[2] = new ElementCollectionImpl(3);
        this.root[3] = new ElementCollectionImpl(5);
        this.root[4] = new ElementCollectionImpl(5);
        this.root[5] = new ElementCollectionImpl(5);
        this.para[0] = new ElementCollectionImpl(100);
        this.para[1] = new ElementCollectionImpl(3);
        this.para[2] = new ElementCollectionImpl(3);
        this.para[3] = new ElementCollectionImpl(5);
        this.para[4] = new ElementCollectionImpl(5);
        this.para[5] = new ElementCollectionImpl(5);
        this.table[0] = new ElementCollectionImpl(5);
        this.table[1] = new ElementCollectionImpl(5);
        this.table[2] = new ElementCollectionImpl(5);
        this.table[3] = new ElementCollectionImpl(5);
    }

    public IElement getSection(long j) {
        return this.root[0].getElement(j);
    }

    public void appendSection(IElement iElement) {
        this.root[0].addElement(iElement);
    }

    public void appendElement(IElement iElement, long j) {
        if (iElement.getType() == 1) {
            appendParagraph(iElement, j);
        }
        ElementCollectionImpl rootCollection = getRootCollection(j);
        if (rootCollection != null) {
            rootCollection.addElement(iElement);
        }
    }

    public IElement getHFElement(long j, byte b) {
        ElementCollectionImpl rootCollection = getRootCollection(j);
        if (rootCollection != null) {
            return rootCollection.getElement(j);
        }
        return null;
    }

    public IElement getParagraph(long j) {
        IElement textboxSectionElement;
        if ((WPModelConstant.AREA_MASK & j) == WPModelConstant.TEXTBOX && (textboxSectionElement = getTextboxSectionElement(j)) != null) {
            return ((SectionElement) textboxSectionElement).getParaCollection().getElement(j);
        }
        ElementCollectionImpl paraCollection = getParaCollection(j);
        if (paraCollection != null) {
            return paraCollection.getElement(j);
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0012, code lost:
        r1 = getTableCollection(r4);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.simpletext.model.IElement getParagraph0(long r4) {
        /*
            r3 = this;
            com.app.office.simpletext.model.IElement r0 = r3.getParagraph(r4)
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r2 = r0.getAttribute()
            int r1 = r1.getParaLevel(r2)
            if (r1 < 0) goto L_0x001d
            com.app.office.simpletext.model.ElementCollectionImpl r1 = r3.getTableCollection(r4)
            if (r1 == 0) goto L_0x001d
            com.app.office.simpletext.model.IElement r4 = r1.getElement(r4)
            return r4
        L_0x001d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.wp.model.WPDocument.getParagraph0(long):com.app.office.simpletext.model.IElement");
    }

    public IElement getParagraphForIndex(int i, long j) {
        IElement textboxSectionElement;
        if ((WPModelConstant.AREA_MASK & j) == WPModelConstant.TEXTBOX && (textboxSectionElement = getTextboxSectionElement(j)) != null) {
            return ((SectionElement) textboxSectionElement).getParaCollection().getElementForIndex(i);
        }
        ElementCollectionImpl paraCollection = getParaCollection(j);
        if (paraCollection != null) {
            return paraCollection.getElementForIndex(i);
        }
        return null;
    }

    public void appendParagraph(IElement iElement, long j) {
        IElement textboxSectionElement;
        if (iElement.getType() == 2) {
            ElementCollectionImpl tableCollection = getTableCollection(j);
            if (tableCollection != null) {
                tableCollection.addElement(iElement);
            }
        } else if ((WPModelConstant.AREA_MASK & j) != WPModelConstant.TEXTBOX || (textboxSectionElement = getTextboxSectionElement(j)) == null) {
            ElementCollectionImpl paraCollection = getParaCollection(j);
            if (paraCollection != null) {
                paraCollection.addElement(iElement);
            }
        } else {
            ((SectionElement) textboxSectionElement).appendParagraph(iElement, j);
        }
    }

    public int getParaCount(long j) {
        IElement textboxSectionElement;
        if ((WPModelConstant.AREA_MASK & j) == WPModelConstant.TEXTBOX && (textboxSectionElement = getTextboxSectionElement(j)) != null) {
            return ((SectionElement) textboxSectionElement).getParaCollection().size();
        }
        ElementCollectionImpl paraCollection = getParaCollection(j);
        if (paraCollection != null) {
            return paraCollection.size();
        }
        return 0;
    }

    private ElementCollectionImpl getRootCollection(long j) {
        long j2 = j & WPModelConstant.AREA_MASK;
        if (j2 == 0) {
            return this.root[0];
        }
        if (j2 == 1152921504606846976L) {
            return this.root[1];
        }
        if (j2 == 2305843009213693952L) {
            return this.root[2];
        }
        if (j2 == WPModelConstant.FOOTNOTE) {
            return this.root[3];
        }
        if (j2 == WPModelConstant.ENDNOTE) {
            return this.root[4];
        }
        if (j2 == WPModelConstant.TEXTBOX) {
            return this.root[5];
        }
        return null;
    }

    public ElementCollectionImpl getParaCollection(long j) {
        long j2 = j & WPModelConstant.AREA_MASK;
        if (j2 == 0) {
            return this.para[0];
        }
        if (j2 == 1152921504606846976L) {
            return this.para[1];
        }
        if (j2 == 2305843009213693952L) {
            return this.para[2];
        }
        if (j2 == WPModelConstant.FOOTNOTE) {
            return this.para[3];
        }
        if (j2 == WPModelConstant.ENDNOTE) {
            return this.para[4];
        }
        if (j2 == WPModelConstant.TEXTBOX) {
            return this.para[5];
        }
        return null;
    }

    public ElementCollectionImpl getTableCollection(long j) {
        long j2 = j & WPModelConstant.AREA_MASK;
        if (j2 == 0) {
            return this.table[0];
        }
        if (j2 == 1152921504606846976L) {
            return this.table[1];
        }
        if (j2 == 2305843009213693952L) {
            return this.table[2];
        }
        if (j2 == WPModelConstant.TEXTBOX) {
            return this.table[3];
        }
        return null;
    }

    public long getLength(long j) {
        IElement textboxSectionElement;
        ElementCollectionImpl rootCollection = getRootCollection(j);
        if (rootCollection == null) {
            return 0;
        }
        if ((WPModelConstant.AREA_MASK & j) != WPModelConstant.TEXTBOX || (textboxSectionElement = getTextboxSectionElement(j)) == null) {
            return rootCollection.getElementForIndex(rootCollection.size() - 1).getEndOffset() - rootCollection.getElementForIndex(0).getStartOffset();
        }
        return textboxSectionElement.getEndOffset() - textboxSectionElement.getStartOffset();
    }

    private IElement getTextboxSectionElement(long j) {
        long j2 = (j & WPModelConstant.TEXTBOX_MASK) >> 32;
        ElementCollectionImpl[] elementCollectionImplArr = this.root;
        if (elementCollectionImplArr[5] != null) {
            return elementCollectionImplArr[5].getElementForIndex((int) j2);
        }
        return null;
    }

    public IElement getTextboxSectionElementForIndex(int i) {
        ElementCollectionImpl[] elementCollectionImplArr = this.root;
        if (elementCollectionImplArr[5] != null) {
            return elementCollectionImplArr[5].getElementForIndex(i);
        }
        return null;
    }

    public void setPageBackground(BackgroundAndFill backgroundAndFill) {
        this.pageBG = backgroundAndFill;
    }

    public BackgroundAndFill getPageBackground() {
        return this.pageBG;
    }

    public void dispose() {
        super.dispose();
        int i = 0;
        if (this.root != null) {
            int i2 = 0;
            while (true) {
                ElementCollectionImpl[] elementCollectionImplArr = this.root;
                if (i2 >= elementCollectionImplArr.length) {
                    break;
                }
                elementCollectionImplArr[i2].dispose();
                this.root[i2] = null;
                i2++;
            }
            this.root = null;
        }
        if (this.para != null) {
            int i3 = 0;
            while (true) {
                ElementCollectionImpl[] elementCollectionImplArr2 = this.para;
                if (i3 >= elementCollectionImplArr2.length) {
                    break;
                }
                elementCollectionImplArr2[i3].dispose();
                this.para[i3] = null;
                i3++;
            }
            this.para = null;
        }
        if (this.table != null) {
            while (true) {
                ElementCollectionImpl[] elementCollectionImplArr3 = this.table;
                if (i < elementCollectionImplArr3.length) {
                    elementCollectionImplArr3[i].dispose();
                    this.table[i] = null;
                    i++;
                } else {
                    this.table = null;
                    return;
                }
            }
        }
    }
}
