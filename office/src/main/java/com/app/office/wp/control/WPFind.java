package com.app.office.wp.control;

import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.IView;
import com.app.office.system.IFind;

public class WPFind implements IFind {
    protected IElement findElement;
    protected String findString;
    private boolean isSetPointToVisible;
    protected int pageIndex;
    protected String query;
    protected Rectangle rect = new Rectangle();
    protected int relativeParaIndex;
    protected Word word;

    public void resetSearchResult() {
    }

    public WPFind(Word word2) {
        this.word = word2;
    }

    public boolean find(String str) {
        if (str == null) {
            return false;
        }
        this.isSetPointToVisible = false;
        this.query = str;
        float zoom = this.word.getZoom();
        long j = 0;
        if (this.word.getCurrentRootType() == 2) {
            IView currentPageView = this.word.getPrintWord().getCurrentPageView();
            while (currentPageView != null && currentPageView.getType() != 5) {
                currentPageView = currentPageView.getChildView();
            }
            if (currentPageView != null) {
                j = currentPageView.getStartOffset((IDocument) null);
            }
        } else {
            Word word2 = this.word;
            j = word2.viewToModel((int) (((float) word2.getScrollX()) / zoom), (int) (((float) this.word.getScrollY()) / zoom), false);
        }
        IDocument document = this.word.getDocument();
        this.findElement = document.getParagraph(j);
        while (true) {
            IElement iElement = this.findElement;
            if (iElement != null) {
                String text = iElement.getText(document);
                this.findString = text;
                int indexOf = text.indexOf(str);
                if (indexOf >= 0) {
                    addHighlight(indexOf, str.length());
                    return true;
                }
                this.findElement = document.getParagraph(this.findElement.getEndOffset());
            } else {
                this.findString = null;
                return false;
            }
        }
    }

    public boolean findBackward() {
        if (this.query == null) {
            return false;
        }
        this.isSetPointToVisible = false;
        IDocument document = this.word.getDocument();
        String str = this.findString;
        if (str != null) {
            String str2 = this.query;
            int lastIndexOf = str.lastIndexOf(str2, this.relativeParaIndex - (str2.length() * 2));
            if (lastIndexOf >= 0) {
                addHighlight(lastIndexOf, this.query.length());
                return true;
            }
        }
        IElement iElement = this.findElement;
        this.findElement = document.getParagraph((iElement == null ? document.getLength(0) : iElement.getStartOffset()) - 1);
        while (true) {
            IElement iElement2 = this.findElement;
            if (iElement2 != null) {
                String text = iElement2.getText(document);
                this.findString = text;
                int lastIndexOf2 = text.lastIndexOf(this.query);
                if (lastIndexOf2 >= 0 && isSameSelectPosition(lastIndexOf2)) {
                    String str3 = this.findString;
                    String str4 = this.query;
                    lastIndexOf2 = str3.lastIndexOf(str4, lastIndexOf2 - str4.length());
                }
                if (lastIndexOf2 >= 0) {
                    addHighlight(lastIndexOf2, this.query.length());
                    return true;
                }
                this.findElement = document.getParagraph(this.findElement.getStartOffset() - 1);
            } else {
                this.findString = null;
                return false;
            }
        }
    }

    public boolean findForward() {
        int indexOf;
        if (this.query == null) {
            return false;
        }
        this.isSetPointToVisible = false;
        IDocument document = this.word.getDocument();
        String str = this.findString;
        if (str == null || (indexOf = str.indexOf(this.query, this.relativeParaIndex)) < 0) {
            IElement iElement = this.findElement;
            this.findElement = document.getParagraph(iElement == null ? 0 : iElement.getEndOffset());
            while (true) {
                IElement iElement2 = this.findElement;
                if (iElement2 != null) {
                    String text = iElement2.getText(document);
                    this.findString = text;
                    int indexOf2 = text.indexOf(this.query);
                    if (indexOf2 >= 0 && isSameSelectPosition(indexOf2)) {
                        String str2 = this.findString;
                        String str3 = this.query;
                        indexOf2 = str2.indexOf(str3, indexOf2 + str3.length());
                    }
                    if (indexOf2 >= 0) {
                        addHighlight(indexOf2, this.query.length());
                        return true;
                    }
                    this.findElement = document.getParagraph(this.findElement.getEndOffset());
                } else {
                    this.findString = null;
                    return false;
                }
            }
        } else {
            addHighlight(indexOf, this.query.length());
            return true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void addHighlight(int r7, int r8) {
        /*
            r6 = this;
            r6.relativeParaIndex = r7
            com.app.office.simpletext.model.IElement r0 = r6.findElement
            long r0 = r0.getStartOffset()
            long r2 = (long) r7
            long r0 = r0 + r2
            com.app.office.wp.control.Word r7 = r6.word
            com.app.office.simpletext.control.IHighlight r7 = r7.getHighlight()
            long r2 = (long) r8
            long r2 = r2 + r0
            r7.addHighlight(r0, r2)
            int r7 = r6.relativeParaIndex
            int r7 = r7 + r8
            r6.relativeParaIndex = r7
            com.app.office.wp.control.Word r7 = r6.word
            int r7 = r7.getCurrentRootType()
            r8 = 2
            r2 = 0
            r3 = 0
            if (r7 != r8) goto L_0x00dc
            com.app.office.wp.control.Word r7 = r6.word
            com.app.office.simpletext.view.IView r7 = r7.getRoot(r3)
            r8 = 1
            if (r7 == 0) goto L_0x00d3
            short r4 = r7.getType()
            if (r4 != 0) goto L_0x00d3
            com.app.office.wp.view.PageRoot r7 = (com.app.office.wp.view.PageRoot) r7
            com.app.office.simpletext.view.ViewContainer r7 = r7.getViewContainer()
            com.app.office.simpletext.view.IView r7 = r7.getParagraph(r0, r3)
        L_0x003e:
            if (r7 == 0) goto L_0x004c
            short r4 = r7.getType()
            r5 = 4
            if (r4 == r5) goto L_0x004c
            com.app.office.simpletext.view.IView r7 = r7.getParentView()
            goto L_0x003e
        L_0x004c:
            if (r7 == 0) goto L_0x00d3
            r4 = r7
            com.app.office.wp.view.PageView r4 = (com.app.office.wp.view.PageView) r4
            int r4 = r4.getPageNumber()
            int r4 = r4 - r8
            r6.pageIndex = r4
            com.app.office.wp.control.Word r5 = r6.word
            int r5 = r5.getCurrentPageNumber()
            int r5 = r5 - r8
            if (r4 == r5) goto L_0x006c
            com.app.office.wp.control.Word r7 = r6.word
            int r0 = r6.pageIndex
            r1 = -1
            r7.showPage(r0, r1)
            r6.isSetPointToVisible = r8
            goto L_0x00d4
        L_0x006c:
            com.app.office.java.awt.Rectangle r4 = r6.rect
            r4.setBounds(r3, r3, r3, r3)
            com.app.office.wp.control.Word r4 = r6.word
            com.app.office.java.awt.Rectangle r5 = r6.rect
            r4.modelToView(r0, r5, r3)
            com.app.office.java.awt.Rectangle r0 = r6.rect
            int r1 = r0.x
            int r4 = r7.getX()
            int r1 = r1 - r4
            r0.x = r1
            com.app.office.java.awt.Rectangle r0 = r6.rect
            int r1 = r0.y
            int r7 = r7.getY()
            int r1 = r1 - r7
            r0.y = r1
            com.app.office.wp.control.Word r7 = r6.word
            com.app.office.wp.control.PrintWord r7 = r7.getPrintWord()
            com.app.office.system.beans.pagelist.APageListView r7 = r7.getListView()
            com.app.office.java.awt.Rectangle r0 = r6.rect
            int r0 = r0.x
            com.app.office.java.awt.Rectangle r1 = r6.rect
            int r1 = r1.y
            boolean r7 = r7.isPointVisibleOnScreen(r0, r1)
            if (r7 != 0) goto L_0x00bc
            com.app.office.wp.control.Word r7 = r6.word
            com.app.office.wp.control.PrintWord r7 = r7.getPrintWord()
            com.app.office.system.beans.pagelist.APageListView r7 = r7.getListView()
            com.app.office.java.awt.Rectangle r8 = r6.rect
            int r8 = r8.x
            com.app.office.java.awt.Rectangle r0 = r6.rect
            int r0 = r0.y
            r7.setItemPointVisibleOnScreen(r8, r0)
            goto L_0x00d4
        L_0x00bc:
            com.app.office.wp.control.Word r7 = r6.word
            com.app.office.wp.control.PrintWord r7 = r7.getPrintWord()
            com.app.office.wp.control.Word r0 = r6.word
            com.app.office.wp.control.PrintWord r0 = r0.getPrintWord()
            com.app.office.system.beans.pagelist.APageListView r0 = r0.getListView()
            com.app.office.system.beans.pagelist.APageListItem r0 = r0.getCurrentPageView()
            r7.exportImage(r0, r2)
        L_0x00d3:
            r3 = 1
        L_0x00d4:
            if (r3 == 0) goto L_0x00db
            com.app.office.wp.control.Word r7 = r6.word
            r7.postInvalidate()
        L_0x00db:
            return
        L_0x00dc:
            com.app.office.java.awt.Rectangle r7 = r6.rect
            r7.setBounds(r3, r3, r3, r3)
            com.app.office.wp.control.Word r7 = r6.word
            com.app.office.java.awt.Rectangle r4 = r6.rect
            r7.modelToView(r0, r4, r3)
            com.app.office.wp.control.Word r7 = r6.word
            com.app.office.java.awt.Rectangle r7 = r7.getVisibleRect()
            com.app.office.wp.control.Word r0 = r6.word
            float r0 = r0.getZoom()
            com.app.office.java.awt.Rectangle r1 = r6.rect
            int r1 = r1.x
            float r1 = (float) r1
            float r1 = r1 * r0
            int r1 = (int) r1
            com.app.office.java.awt.Rectangle r3 = r6.rect
            int r3 = r3.y
            float r3 = (float) r3
            float r3 = r3 * r0
            int r3 = (int) r3
            boolean r4 = r7.contains(r1, r3)
            if (r4 != 0) goto L_0x014d
            int r4 = r7.width
            int r4 = r4 + r1
            float r4 = (float) r4
            com.app.office.wp.control.Word r5 = r6.word
            int r5 = r5.getWordWidth()
            float r5 = (float) r5
            float r5 = r5 * r0
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 <= 0) goto L_0x0128
            com.app.office.wp.control.Word r1 = r6.word
            int r1 = r1.getWordWidth()
            float r1 = (float) r1
            float r1 = r1 * r0
            int r1 = (int) r1
            int r4 = r7.width
            int r1 = r1 - r4
        L_0x0128:
            int r4 = r7.height
            int r4 = r4 + r3
            float r4 = (float) r4
            com.app.office.wp.control.Word r5 = r6.word
            int r5 = r5.getWordHeight()
            float r5 = (float) r5
            float r5 = r5 * r0
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 <= 0) goto L_0x0147
            com.app.office.wp.control.Word r3 = r6.word
            int r3 = r3.getWordHeight()
            float r3 = (float) r3
            float r3 = r3 * r0
            int r0 = (int) r3
            int r7 = r7.height
            int r3 = r0 - r7
        L_0x0147:
            com.app.office.wp.control.Word r7 = r6.word
            r7.scrollTo(r1, r3)
            goto L_0x0152
        L_0x014d:
            com.app.office.wp.control.Word r7 = r6.word
            r7.postInvalidate()
        L_0x0152:
            com.app.office.wp.control.Word r7 = r6.word
            com.app.office.system.IControl r7 = r7.getControl()
            r0 = 20
            r7.actionEvent(r0, r2)
            com.app.office.wp.control.Word r7 = r6.word
            int r7 = r7.getCurrentRootType()
            if (r7 == r8) goto L_0x0171
            com.app.office.wp.control.Word r7 = r6.word
            com.app.office.system.IControl r7 = r7.getControl()
            r8 = 536870922(0x2000000a, float:1.0842035E-19)
            r7.actionEvent(r8, r2)
        L_0x0171:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.wp.control.WPFind.addHighlight(int, int):void");
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    private boolean isSameSelectPosition(int i) {
        return this.word.getHighlight().isSelectText() && this.findElement.getStartOffset() + ((long) i) == this.word.getHighlight().getSelectStart();
    }

    public boolean isSetPointToVisible() {
        return this.isSetPointToVisible;
    }

    public void setSetPointToVisible(boolean z) {
        this.isSetPointToVisible = z;
    }

    public void dispose() {
        this.findElement = null;
        this.word = null;
        this.rect = null;
    }
}
