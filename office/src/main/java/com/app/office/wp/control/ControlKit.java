package com.app.office.wp.control;

import com.app.office.simpletext.model.IDocument;

public class ControlKit {
    private static ControlKit kit = new ControlKit();

    public static ControlKit instance() {
        return kit;
    }

    public void internetSearch(Word word) {
        IDocument document = word.getDocument();
        long selectStart = word.getHighlight().getSelectStart();
        long selectEnd = word.getHighlight().getSelectEnd();
        word.getControl().getSysKit().internetSearch(selectStart != selectEnd ? document.getText(selectStart, selectEnd) : "", word.getControl().getMainFrame().getActivity());
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void gotoOffset(com.app.office.wp.control.Word r8, long r9) {
        /*
            r7 = this;
            com.app.office.java.awt.Rectangle r0 = new com.app.office.java.awt.Rectangle
            r0.<init>()
            int r1 = r8.getCurrentRootType()
            r2 = 2
            r3 = 0
            r4 = 0
            if (r1 != r2) goto L_0x009d
            com.app.office.simpletext.view.IView r1 = r8.getRoot(r4)
            r2 = 1
            if (r1 == 0) goto L_0x0096
            short r5 = r1.getType()
            if (r5 != 0) goto L_0x0096
            com.app.office.wp.view.PageRoot r1 = (com.app.office.wp.view.PageRoot) r1
            com.app.office.simpletext.view.ViewContainer r1 = r1.getViewContainer()
            com.app.office.simpletext.view.IView r1 = r1.getParagraph(r9, r4)
        L_0x0025:
            if (r1 == 0) goto L_0x0033
            short r5 = r1.getType()
            r6 = 4
            if (r5 == r6) goto L_0x0033
            com.app.office.simpletext.view.IView r1 = r1.getParentView()
            goto L_0x0025
        L_0x0033:
            if (r1 == 0) goto L_0x0096
            r5 = r1
            com.app.office.wp.view.PageView r5 = (com.app.office.wp.view.PageView) r5
            int r5 = r5.getPageNumber()
            int r5 = r5 - r2
            int r6 = r8.getCurrentPageNumber()
            int r6 = r6 - r2
            if (r5 == r6) goto L_0x0049
            r9 = -1
            r8.showPage(r5, r9)
            goto L_0x0097
        L_0x0049:
            r0.setBounds(r4, r4, r4, r4)
            r8.modelToView(r9, r0, r4)
            int r9 = r0.x
            int r10 = r1.getX()
            int r9 = r9 - r10
            r0.x = r9
            int r9 = r0.y
            int r10 = r1.getY()
            int r9 = r9 - r10
            r0.y = r9
            com.app.office.wp.control.PrintWord r9 = r8.getPrintWord()
            com.app.office.system.beans.pagelist.APageListView r9 = r9.getListView()
            int r10 = r0.x
            int r1 = r0.y
            boolean r9 = r9.isPointVisibleOnScreen(r10, r1)
            if (r9 != 0) goto L_0x0083
            com.app.office.wp.control.PrintWord r9 = r8.getPrintWord()
            com.app.office.system.beans.pagelist.APageListView r9 = r9.getListView()
            int r10 = r0.x
            int r0 = r0.y
            r9.setItemPointVisibleOnScreen(r10, r0)
            goto L_0x0097
        L_0x0083:
            com.app.office.wp.control.PrintWord r9 = r8.getPrintWord()
            com.app.office.wp.control.PrintWord r10 = r8.getPrintWord()
            com.app.office.system.beans.pagelist.APageListView r10 = r10.getListView()
            com.app.office.system.beans.pagelist.APageListItem r10 = r10.getCurrentPageView()
            r9.exportImage(r10, r3)
        L_0x0096:
            r4 = 1
        L_0x0097:
            if (r4 == 0) goto L_0x009c
            r8.postInvalidate()
        L_0x009c:
            return
        L_0x009d:
            r0.setBounds(r4, r4, r4, r4)
            r8.modelToView(r9, r0, r4)
            com.app.office.java.awt.Rectangle r9 = r8.getVisibleRect()
            float r10 = r8.getZoom()
            int r1 = r0.x
            float r1 = (float) r1
            float r1 = r1 * r10
            int r1 = (int) r1
            int r0 = r0.y
            float r0 = (float) r0
            float r0 = r0 * r10
            int r0 = (int) r0
            boolean r4 = r9.contains(r1, r0)
            if (r4 != 0) goto L_0x00f6
            int r4 = r9.width
            int r4 = r4 + r1
            float r4 = (float) r4
            int r5 = r8.getWordWidth()
            float r5 = (float) r5
            float r5 = r5 * r10
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 <= 0) goto L_0x00d7
            int r1 = r8.getWordWidth()
            float r1 = (float) r1
            float r1 = r1 * r10
            int r1 = (int) r1
            int r4 = r9.width
            int r1 = r1 - r4
        L_0x00d7:
            int r4 = r9.height
            int r4 = r4 + r0
            float r4 = (float) r4
            int r5 = r8.getWordHeight()
            float r5 = (float) r5
            float r5 = r5 * r10
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 <= 0) goto L_0x00f2
            int r0 = r8.getWordHeight()
            float r0 = (float) r0
            float r0 = r0 * r10
            int r10 = (int) r0
            int r9 = r9.height
            int r0 = r10 - r9
        L_0x00f2:
            r8.scrollTo(r1, r0)
            goto L_0x00f9
        L_0x00f6:
            r8.postInvalidate()
        L_0x00f9:
            com.app.office.system.IControl r9 = r8.getControl()
            r10 = 20
            r9.actionEvent(r10, r3)
            int r9 = r8.getCurrentRootType()
            if (r9 == r2) goto L_0x0112
            com.app.office.system.IControl r8 = r8.getControl()
            r9 = 536870922(0x2000000a, float:1.0842035E-19)
            r8.actionEvent(r9, r3)
        L_0x0112:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.wp.control.ControlKit.gotoOffset(com.app.office.wp.control.Word, long):void");
    }
}
