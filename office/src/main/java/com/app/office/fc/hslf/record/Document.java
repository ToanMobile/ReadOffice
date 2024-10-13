package com.app.office.fc.hslf.record;

import java.util.ArrayList;

public final class Document extends PositionDependentRecordContainer {
    private static long _type = 1000;
    private byte[] _header;
    private DocumentAtom documentAtom;
    private Environment environment;
    private ExObjList exObjList;
    private List list;
    private PPDrawingGroup ppDrawing;
    private SlideListWithText[] slwts;

    public DocumentAtom getDocumentAtom() {
        return this.documentAtom;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public PPDrawingGroup getPPDrawingGroup() {
        return this.ppDrawing;
    }

    public ExObjList getExObjList() {
        return this.exObjList;
    }

    public SlideListWithText[] getSlideListWithTexts() {
        return this.slwts;
    }

    public SlideListWithText getMasterSlideListWithText() {
        int i = 0;
        while (true) {
            SlideListWithText[] slideListWithTextArr = this.slwts;
            if (i >= slideListWithTextArr.length) {
                return null;
            }
            if (slideListWithTextArr[i].getInstance() == 1) {
                return this.slwts[i];
            }
            i++;
        }
    }

    public SlideListWithText getSlideSlideListWithText() {
        int i = 0;
        while (true) {
            SlideListWithText[] slideListWithTextArr = this.slwts;
            if (i >= slideListWithTextArr.length) {
                return null;
            }
            if (slideListWithTextArr[i].getInstance() == 0) {
                return this.slwts[i];
            }
            i++;
        }
    }

    public SlideListWithText getNotesSlideListWithText() {
        int i = 0;
        while (true) {
            SlideListWithText[] slideListWithTextArr = this.slwts;
            if (i >= slideListWithTextArr.length) {
                return null;
            }
            if (slideListWithTextArr[i].getInstance() == 2) {
                return this.slwts[i];
            }
            i++;
        }
    }

    public List getList() {
        return this.list;
    }

    protected Document(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        int i3 = 0;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        if (this._children[0] instanceof DocumentAtom) {
            this.documentAtom = (DocumentAtom) this._children[0];
            int i4 = 0;
            for (int i5 = 1; i5 < this._children.length; i5++) {
                if (this._children[i5] instanceof SlideListWithText) {
                    i4++;
                } else if (this._children[i5] instanceof Environment) {
                    this.environment = (Environment) this._children[i5];
                } else if (this._children[i5] instanceof PPDrawingGroup) {
                    this.ppDrawing = (PPDrawingGroup) this._children[i5];
                } else if (this._children[i5] instanceof ExObjList) {
                    this.exObjList = (ExObjList) this._children[i5];
                } else if (this._children[i5] instanceof List) {
                    this.list = (List) this._children[i5];
                }
            }
            this.slwts = new SlideListWithText[i4];
            for (int i6 = 1; i6 < this._children.length; i6++) {
                if (this._children[i6] instanceof SlideListWithText) {
                    this.slwts[i3] = (SlideListWithText) this._children[i6];
                    i3++;
                }
            }
            return;
        }
        throw new IllegalStateException("The first child of a Document must be a DocumentAtom");
    }

    public void addSlideListWithText(SlideListWithText slideListWithText) {
        Record record = this._children[this._children.length - 1];
        if (record.getRecordType() == ((long) RecordTypes.EndDocument.typeID)) {
            addChildBefore(slideListWithText, record);
            SlideListWithText[] slideListWithTextArr = this.slwts;
            int length = slideListWithTextArr.length + 1;
            SlideListWithText[] slideListWithTextArr2 = new SlideListWithText[length];
            System.arraycopy(slideListWithTextArr, 0, slideListWithTextArr2, 0, slideListWithTextArr.length);
            slideListWithTextArr2[length - 1] = slideListWithText;
            this.slwts = slideListWithTextArr2;
            return;
        }
        throw new IllegalStateException("The last child record of a Document should be EndDocument, but it was " + record);
    }

    public void removeSlideListWithText(SlideListWithText slideListWithText) {
        ArrayList arrayList = new ArrayList();
        for (SlideListWithText slideListWithText2 : this.slwts) {
            if (slideListWithText2 != slideListWithText) {
                arrayList.add(slideListWithText2);
            } else {
                removeChild(slideListWithText);
            }
        }
        this.slwts = (SlideListWithText[]) arrayList.toArray(new SlideListWithText[arrayList.size()]);
    }

    public long getRecordType() {
        return _type;
    }

    public void dispose() {
        this._header = null;
        DocumentAtom documentAtom2 = this.documentAtom;
        if (documentAtom2 != null) {
            documentAtom2.dispose();
            this.documentAtom = null;
        }
        Environment environment2 = this.environment;
        if (environment2 != null) {
            environment2.dispose();
            this.environment = null;
        }
        PPDrawingGroup pPDrawingGroup = this.ppDrawing;
        if (pPDrawingGroup != null) {
            pPDrawingGroup.dispose();
            this.ppDrawing = null;
        }
        SlideListWithText[] slideListWithTextArr = this.slwts;
        if (slideListWithTextArr != null) {
            for (SlideListWithText dispose : slideListWithTextArr) {
                dispose.dispose();
            }
            this.slwts = null;
        }
        ExObjList exObjList2 = this.exObjList;
        if (exObjList2 != null) {
            exObjList2.dispose();
            this.exObjList = null;
        }
        List list2 = this.list;
        if (list2 != null) {
            list2.dispose();
            this.list = null;
        }
    }
}
