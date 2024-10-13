package com.app.office.common.shape;

import com.app.office.simpletext.model.SectionElement;
import com.app.office.simpletext.view.STRoot;

public class TextBox extends AbstractShape {
    public static final byte MC_DateTime = 2;
    public static final byte MC_Footer = 4;
    public static final byte MC_GenericDate = 3;
    public static final byte MC_None = 0;
    public static final byte MC_RTFDateTime = 5;
    public static final byte MC_SlideNumber = 1;
    private SectionElement elem;
    private boolean isEditor;
    private boolean isWordArt;
    private boolean isWrapLine;
    private byte mcType = 0;
    private STRoot rootView;

    public short getType() {
        return 1;
    }

    public TextBox() {
        setPlaceHolderID(-1);
    }

    public SectionElement getElement() {
        return this.elem;
    }

    public void setElement(SectionElement sectionElement) {
        this.elem = sectionElement;
    }

    public void setData(Object obj) {
        if (obj instanceof SectionElement) {
            this.elem = (SectionElement) obj;
        }
    }

    public Object getData() {
        return this.elem;
    }

    public boolean isEditor() {
        return this.isEditor;
    }

    public void setEditor(boolean z) {
        this.isEditor = z;
    }

    public STRoot getRootView() {
        return this.rootView;
    }

    public void setRootView(STRoot sTRoot) {
        this.rootView = sTRoot;
    }

    public boolean isWrapLine() {
        return this.isWrapLine;
    }

    public void setWrapLine(boolean z) {
        this.isWrapLine = z;
    }

    public byte getMCType() {
        return this.mcType;
    }

    public void setMCType(byte b) {
        this.mcType = b;
    }

    public void setWordArt(boolean z) {
        this.isWordArt = z;
    }

    public boolean isWordArt() {
        return this.isWordArt;
    }

    public void dispose() {
        super.dispose();
        SectionElement sectionElement = this.elem;
        if (sectionElement != null) {
            sectionElement.dispose();
            this.elem = null;
        }
    }
}
