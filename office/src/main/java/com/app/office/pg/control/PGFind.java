package com.app.office.pg.control;

import com.app.office.common.shape.IShape;
import com.app.office.common.shape.TextBox;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.model.PGSlide;
import com.app.office.simpletext.control.IHighlight;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.system.IFind;

public class PGFind implements IFind {
    private boolean isSetPointToVisible;
    private Presentation presentation;
    private String query;
    protected Rectangle rect;
    private int shapeIndex = -1;
    private int slideIndex = -1;
    private int startOffset = -1;

    public void resetSearchResult() {
    }

    public PGFind(Presentation presentation2) {
        this.presentation = presentation2;
        this.rect = new Rectangle();
    }

    public boolean find(String str) {
        if (str == null) {
            return false;
        }
        this.query = str;
        this.startOffset = -1;
        this.shapeIndex = -1;
        int currentIndex = this.presentation.getCurrentIndex();
        while (!findSlideForward(currentIndex)) {
            currentIndex++;
            if (currentIndex == this.presentation.getRealSlideCount()) {
                currentIndex = 0;
            }
            if (currentIndex == this.presentation.getCurrentIndex()) {
                return false;
            }
        }
        return true;
    }

    public boolean findBackward() {
        if (this.query == null) {
            return false;
        }
        int currentIndex = this.presentation.getCurrentIndex();
        while (!findSlideBackward(currentIndex)) {
            this.startOffset = -1;
            this.shapeIndex = -1;
            currentIndex--;
            if (currentIndex < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean findForward() {
        if (this.query == null) {
            return false;
        }
        int currentIndex = this.presentation.getCurrentIndex();
        while (!findSlideForward(currentIndex)) {
            this.startOffset = -1;
            this.shapeIndex = -1;
            currentIndex++;
            if (currentIndex == this.presentation.getRealSlideCount()) {
                return false;
            }
        }
        return true;
    }

    private boolean findSlideBackward(int i) {
        int i2;
        PGSlide slide = this.presentation.getSlide(i);
        int i3 = this.shapeIndex;
        if (i3 < 0) {
            i3 = slide.getShapeCountForFind() - 1;
        }
        while (i3 >= 0) {
            IShape shapeForFind = slide.getShapeForFind(i3);
            if (shapeForFind != null && shapeForFind.getType() == 1) {
                int i4 = (this.shapeIndex == i3 && this.presentation.getCurrentIndex() == i) ? this.startOffset : -1;
                TextBox textBox = (TextBox) shapeForFind;
                SectionElement element = textBox.getElement();
                if (element != null && ((i4 < 0 || i4 >= this.query.length()) && element.getEndOffset() - element.getStartOffset() != 0)) {
                    if (i4 >= 0) {
                        String text = element.getText(this.presentation.getRenderersDoc());
                        String str = this.query;
                        i2 = text.lastIndexOf(str, Math.max(this.startOffset - str.length(), 0));
                    } else {
                        i2 = element.getText(this.presentation.getRenderersDoc()).lastIndexOf(this.query);
                    }
                    if (i2 >= 0) {
                        this.startOffset = i2;
                        this.shapeIndex = i3;
                        addHighlight(i, textBox);
                        return true;
                    }
                }
            }
            i3--;
        }
        return false;
    }

    private boolean findSlideForward(int i) {
        TextBox textBox;
        SectionElement element;
        int i2;
        PGSlide slide = this.presentation.getSlide(i);
        int max = Math.max(0, this.shapeIndex);
        while (max < slide.getShapeCountForFind()) {
            IShape shapeForFind = slide.getShapeForFind(max);
            if (!(shapeForFind == null || shapeForFind.getType() != 1 || (element = textBox.getElement()) == null || element.getEndOffset() - element.getStartOffset() == 0)) {
                if (((this.shapeIndex == max && this.presentation.getCurrentIndex() == i) ? this.startOffset : -1) >= 0) {
                    String text = element.getText(this.presentation.getRenderersDoc());
                    String str = this.query;
                    i2 = text.indexOf(str, this.startOffset + str.length());
                } else {
                    i2 = element.getText(this.presentation.getRenderersDoc()).indexOf(this.query);
                }
                if (i2 >= 0) {
                    this.startOffset = i2;
                    this.shapeIndex = max;
                    addHighlight(i, (textBox = (TextBox) shapeForFind));
                    return true;
                }
            }
            max++;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x006e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addHighlight(int r8, com.app.office.common.shape.TextBox r9) {
        /*
            r7 = this;
            com.app.office.pg.control.Presentation r0 = r7.presentation
            int r0 = r0.getCurrentIndex()
            r1 = 0
            r2 = 1
            r3 = 0
            if (r8 == r0) goto L_0x0014
            com.app.office.pg.control.Presentation r0 = r7.presentation
            r0.showSlide(r8, r2)
            r7.isSetPointToVisible = r2
        L_0x0012:
            r2 = 0
            goto L_0x006c
        L_0x0014:
            com.app.office.java.awt.Rectangle r0 = r7.rect
            r0.setBounds(r3, r3, r3, r3)
            com.app.office.pg.control.Presentation r0 = r7.presentation
            com.app.office.pg.control.PGEditor r0 = r0.getEditor()
            int r4 = r7.startOffset
            long r4 = (long) r4
            com.app.office.java.awt.Rectangle r6 = r7.rect
            r0.modelToView(r4, r6, r3)
            com.app.office.pg.control.Presentation r0 = r7.presentation
            com.app.office.pg.control.PGPrintMode r0 = r0.getPrintMode()
            com.app.office.system.beans.pagelist.APageListView r0 = r0.getListView()
            com.app.office.java.awt.Rectangle r4 = r7.rect
            int r4 = r4.x
            com.app.office.java.awt.Rectangle r5 = r7.rect
            int r5 = r5.y
            boolean r0 = r0.isPointVisibleOnScreen(r4, r5)
            if (r0 != 0) goto L_0x0055
            com.app.office.pg.control.Presentation r0 = r7.presentation
            com.app.office.pg.control.PGPrintMode r0 = r0.getPrintMode()
            com.app.office.system.beans.pagelist.APageListView r0 = r0.getListView()
            com.app.office.java.awt.Rectangle r2 = r7.rect
            int r2 = r2.x
            com.app.office.java.awt.Rectangle r4 = r7.rect
            int r4 = r4.y
            r0.setItemPointVisibleOnScreen(r2, r4)
            goto L_0x0012
        L_0x0055:
            com.app.office.pg.control.Presentation r0 = r7.presentation
            com.app.office.pg.control.PGPrintMode r0 = r0.getPrintMode()
            com.app.office.pg.control.Presentation r3 = r7.presentation
            com.app.office.pg.control.PGPrintMode r3 = r3.getPrintMode()
            com.app.office.system.beans.pagelist.APageListView r3 = r3.getListView()
            com.app.office.system.beans.pagelist.APageListItem r3 = r3.getCurrentPageView()
            r0.exportImage(r3, r1)
        L_0x006c:
            if (r2 == 0) goto L_0x0073
            com.app.office.pg.control.Presentation r0 = r7.presentation
            r0.postInvalidate()
        L_0x0073:
            r7.slideIndex = r8
            com.app.office.pg.control.Presentation r8 = r7.presentation
            com.app.office.pg.control.PGEditor r8 = r8.getEditor()
            r8.setEditorTextBox(r9)
            com.app.office.pg.control.Presentation r8 = r7.presentation
            com.app.office.pg.control.PGEditor r8 = r8.getEditor()
            com.app.office.simpletext.control.IHighlight r8 = r8.getHighlight()
            int r9 = r7.startOffset
            long r2 = (long) r9
            java.lang.String r0 = r7.query
            int r0 = r0.length()
            int r9 = r9 + r0
            long r4 = (long) r9
            r8.addHighlight(r2, r4)
            com.app.office.pg.control.Presentation r8 = r7.presentation
            com.app.office.system.IControl r8 = r8.getControl()
            r9 = 20
            r8.actionEvent(r9, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.pg.control.PGFind.addHighlight(int, com.app.office.common.shape.TextBox):void");
    }

    public void onConfigurationChanged() {
        PGSlide currentSlide = this.presentation.getCurrentSlide();
        int i = this.shapeIndex;
        if (i >= 0 && i < currentSlide.getShapeCountForFind()) {
            IHighlight highlight = this.presentation.getEditor().getHighlight();
            int i2 = this.startOffset;
            highlight.addHighlight((long) i2, (long) (i2 + this.query.length()));
            this.presentation.postInvalidate();
        }
    }

    public boolean isSetPointToVisible() {
        return this.isSetPointToVisible;
    }

    public void setSetPointToVisible(boolean z) {
        this.isSetPointToVisible = z;
    }

    public int getPageIndex() {
        return this.slideIndex;
    }

    public void dispose() {
        this.presentation = null;
        this.query = null;
    }
}
