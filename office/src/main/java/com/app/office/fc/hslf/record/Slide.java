package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public final class Slide extends SheetContainer {
    private static long _type = 1006;
    private ColorSchemeAtom _colorScheme;
    private byte[] _header;
    private HeadersFootersContainer headersFootersContainer;
    private PPDrawing ppDrawing;
    private SlideProgTagsContainer propTagsContainer;
    private SlideAtom slideAtom;
    private SlideShowSlideInfoAtom ssSlideInfoAtom;

    public SlideAtom getSlideAtom() {
        return this.slideAtom;
    }

    public PPDrawing getPPDrawing() {
        return this.ppDrawing;
    }

    public HeadersFootersContainer getHeadersFootersContainer() {
        return this.headersFootersContainer;
    }

    protected Slide(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        for (int i3 = 0; i3 < this._children.length; i3++) {
            if (this._children[i3] instanceof SlideAtom) {
                this.slideAtom = (SlideAtom) this._children[i3];
            } else if (this._children[i3] instanceof PPDrawing) {
                this.ppDrawing = (PPDrawing) this._children[i3];
            } else if (this._children[i3] instanceof SlideShowSlideInfoAtom) {
                this.ssSlideInfoAtom = (SlideShowSlideInfoAtom) this._children[i3];
            } else if (this._children[i3] instanceof SlideProgTagsContainer) {
                this.propTagsContainer = (SlideProgTagsContainer) this._children[i3];
            } else if (this._children[i3] instanceof HeadersFootersContainer) {
                this.headersFootersContainer = (HeadersFootersContainer) this._children[i3];
            }
            if (this.ppDrawing != null && (this._children[i3] instanceof ColorSchemeAtom)) {
                this._colorScheme = (ColorSchemeAtom) this._children[i3];
            }
        }
    }

    public Slide() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putUShort(bArr, 0, 15);
        LittleEndian.putUShort(this._header, 2, (int) _type);
        LittleEndian.putInt(this._header, 4, 0);
        this.slideAtom = new SlideAtom();
        this.ppDrawing = new PPDrawing();
        this._children = new Record[]{this.slideAtom, this.ppDrawing, new ColorSchemeAtom()};
    }

    public long getRecordType() {
        return _type;
    }

    public ColorSchemeAtom getColorScheme() {
        return this._colorScheme;
    }

    public SlideShowSlideInfoAtom getSlideShowSlideInfoAtom() {
        return this.ssSlideInfoAtom;
    }

    public SlideProgTagsContainer getSlideProgTagsContainer() {
        return this.propTagsContainer;
    }

    public void dispose() {
        super.dispose();
        this._header = null;
        SlideAtom slideAtom2 = this.slideAtom;
        if (slideAtom2 != null) {
            slideAtom2.dispose();
            this.slideAtom = null;
        }
        PPDrawing pPDrawing = this.ppDrawing;
        if (pPDrawing != null) {
            pPDrawing.dispose();
            this.ppDrawing = null;
        }
        ColorSchemeAtom colorSchemeAtom = this._colorScheme;
        if (colorSchemeAtom != null) {
            colorSchemeAtom.dispose();
            this._colorScheme = null;
        }
        SlideShowSlideInfoAtom slideShowSlideInfoAtom = this.ssSlideInfoAtom;
        if (slideShowSlideInfoAtom != null) {
            slideShowSlideInfoAtom.dispose();
            this.ssSlideInfoAtom = null;
        }
        SlideProgTagsContainer slideProgTagsContainer = this.propTagsContainer;
        if (slideProgTagsContainer != null) {
            slideProgTagsContainer.dispose();
            this.propTagsContainer = null;
        }
    }
}
