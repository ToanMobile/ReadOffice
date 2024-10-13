package com.app.office.simpletext.view;

import com.app.office.simpletext.model.IDocument;
import java.util.ArrayList;
import java.util.List;

public class ViewContainer {
    private List<IView> paras = new ArrayList();

    public synchronized void add(IView iView) {
        if (iView != null) {
            if (iView.getEndOffset((IDocument) null) < 1152921504606846976L) {
                this.paras.add(iView);
            }
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void sort() {
        /*
            r2 = this;
            monitor-enter(r2)
            java.util.List<com.app.office.simpletext.view.IView> r0 = r2.paras     // Catch:{ Exception -> 0x000f, all -> 0x000c }
            com.app.office.simpletext.view.ViewContainer$1 r1 = new com.app.office.simpletext.view.ViewContainer$1     // Catch:{ Exception -> 0x000f, all -> 0x000c }
            r1.<init>()     // Catch:{ Exception -> 0x000f, all -> 0x000c }
            java.util.Collections.sort(r0, r1)     // Catch:{ Exception -> 0x000f, all -> 0x000c }
            goto L_0x000f
        L_0x000c:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        L_0x000f:
            monitor-exit(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.simpletext.view.ViewContainer.sort():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0051, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.app.office.simpletext.view.IView getParagraph(long r10, boolean r12) {
        /*
            r9 = this;
            monitor-enter(r9)
            java.util.List<com.app.office.simpletext.view.IView> r12 = r9.paras     // Catch:{ all -> 0x0052 }
            int r12 = r12.size()     // Catch:{ all -> 0x0052 }
            r0 = 0
            if (r12 == 0) goto L_0x0050
            r1 = 0
            int r3 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r3 < 0) goto L_0x0050
            java.util.List<com.app.office.simpletext.view.IView> r1 = r9.paras     // Catch:{ all -> 0x0052 }
            int r2 = r12 + -1
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x0052 }
            com.app.office.simpletext.view.IView r1 = (com.app.office.simpletext.view.IView) r1     // Catch:{ all -> 0x0052 }
            long r1 = r1.getEndOffset(r0)     // Catch:{ all -> 0x0052 }
            int r3 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r3 < 0) goto L_0x0023
            goto L_0x0050
        L_0x0023:
            r1 = 0
        L_0x0024:
            int r2 = r12 + r1
            int r2 = r2 / 2
            java.util.List<com.app.office.simpletext.view.IView> r3 = r9.paras     // Catch:{ all -> 0x0052 }
            java.lang.Object r3 = r3.get(r2)     // Catch:{ all -> 0x0052 }
            com.app.office.simpletext.view.IView r3 = (com.app.office.simpletext.view.IView) r3     // Catch:{ all -> 0x0052 }
            long r4 = r3.getStartOffset(r0)     // Catch:{ all -> 0x0052 }
            long r6 = r3.getEndOffset(r0)     // Catch:{ all -> 0x0052 }
            int r8 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r8 < 0) goto L_0x0042
            int r8 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x0042
            monitor-exit(r9)
            return r3
        L_0x0042:
            int r3 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r3 <= 0) goto L_0x0049
            int r12 = r2 + -1
            goto L_0x0024
        L_0x0049:
            int r3 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r3 > 0) goto L_0x0024
            int r1 = r2 + 1
            goto L_0x0024
        L_0x0050:
            monitor-exit(r9)
            return r0
        L_0x0052:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.simpletext.view.ViewContainer.getParagraph(long, boolean):com.app.office.simpletext.view.IView");
    }

    public synchronized void clear() {
        List<IView> list = this.paras;
        if (list != null) {
            list.clear();
        }
    }

    public synchronized void dispose() {
        List<IView> list = this.paras;
        if (list != null) {
            list.clear();
            this.paras = null;
        }
    }
}
