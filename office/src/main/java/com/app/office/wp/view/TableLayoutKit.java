package com.app.office.wp.view;

import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.DocAttr;
import com.app.office.simpletext.view.IRoot;
import com.app.office.simpletext.view.IView;
import com.app.office.simpletext.view.PageAttr;
import com.app.office.simpletext.view.ParaAttr;
import com.app.office.simpletext.view.TableAttr;
import com.app.office.simpletext.view.ViewKit;
import com.app.office.system.IControl;
import com.app.office.wp.model.CellElement;
import com.app.office.wp.model.RowElement;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class TableLayoutKit {
    private Map<Integer, BreakPagesCell> breakPagesCell = new LinkedHashMap();
    private RowElement breakRowElement;
    private RowView breakRowView;
    private boolean isRowBreakPages;
    private Vector<CellView> mergedCell = new Vector<>();
    private short rowIndex;
    private TableAttr tableAttr = new TableAttr();

    /* JADX WARNING: type inference failed for: r0v19, types: [com.app.office.simpletext.view.IView] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int layoutTable(com.app.office.system.IControl r28, com.app.office.simpletext.model.IDocument r29, com.app.office.simpletext.view.IRoot r30, com.app.office.simpletext.view.DocAttr r31, com.app.office.simpletext.view.PageAttr r32, com.app.office.simpletext.view.ParaAttr r33, com.app.office.wp.view.TableView r34, long r35, int r37, int r38, int r39, int r40, int r41, boolean r42) {
        /*
            r27 = this;
            r15 = r27
            r14 = r34
            java.util.Vector<com.app.office.wp.view.CellView> r0 = r15.mergedCell
            r0.clear()
            com.app.office.simpletext.model.IElement r0 = r34.getElement()
            r13 = r0
            com.app.office.wp.model.TableElement r13 = (com.app.office.wp.model.TableElement) r13
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.view.TableAttr r1 = r15.tableAttr
            com.app.office.simpletext.model.IAttributeSet r2 = r13.getAttribute()
            r0.fillTableAttr(r1, r2)
            com.app.office.simpletext.view.ViewKit r0 = com.app.office.simpletext.view.ViewKit.instance()
            r12 = 2
            r11 = 1
            r1 = r41
            int r10 = r0.setBitValue(r1, r12, r11)
            com.app.office.simpletext.view.ViewKit r0 = com.app.office.simpletext.view.ViewKit.instance()
            r8 = 0
            boolean r0 = r0.getBitValue(r10, r8)
            long r16 = r13.getEndOffset()
            r9 = 0
            r6 = r35
            r19 = r42
            r18 = r0
            r1 = r9
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r0 = r40
        L_0x0044:
            int r20 = (r6 > r16 ? 1 : (r6 == r16 ? 0 : -1))
            if (r20 >= 0) goto L_0x004a
            if (r0 > 0) goto L_0x0050
        L_0x004a:
            com.app.office.wp.model.RowElement r12 = r15.breakRowElement
            if (r12 == 0) goto L_0x0190
            if (r19 == 0) goto L_0x0190
        L_0x0050:
            r15.isRowBreakPages = r8
            com.app.office.wp.model.RowElement r12 = r15.breakRowElement
            if (r12 == 0) goto L_0x005b
            if (r19 == 0) goto L_0x005b
            r15.breakRowElement = r9
            goto L_0x0066
        L_0x005b:
            short r12 = r15.rowIndex
            int r9 = r12 + 1
            short r9 = (short) r9
            r15.rowIndex = r9
            com.app.office.simpletext.model.IElement r12 = r13.getElementForIndex(r12)
        L_0x0066:
            if (r12 != 0) goto L_0x0073
            r8 = r5
            r41 = r6
            r22 = r13
            r6 = r14
            r5 = r15
            r2 = 1
            r9 = 0
            goto L_0x0198
        L_0x0073:
            if (r1 == 0) goto L_0x00b2
            r0 = r12
            com.app.office.wp.model.RowElement r0 = (com.app.office.wp.model.RowElement) r0
            r15.layoutMergedCell(r1, r0, r8)
            int r2 = r1.getY()
            int r3 = r1.getLayoutSpan(r11)
            int r2 = r2 + r3
            int r3 = r40 - r2
            if (r3 > 0) goto L_0x00ac
            boolean r3 = r15.isBreakPages(r1)
            if (r3 == 0) goto L_0x009d
            short r0 = r15.rowIndex
            int r0 = r0 - r11
            short r0 = (short) r0
            r15.rowIndex = r0
            com.app.office.simpletext.model.IElement r0 = r1.getElement()
            com.app.office.wp.model.RowElement r0 = (com.app.office.wp.model.RowElement) r0
            r15.breakRowElement = r0
            goto L_0x009f
        L_0x009d:
            r15.breakRowElement = r0
        L_0x009f:
            r3 = r2
            r11 = r4
            r8 = r5
            r41 = r6
            r22 = r13
            r6 = r14
            r5 = r15
            r2 = 1
            r9 = 0
            goto L_0x0199
        L_0x00ac:
            r9 = r2
            r21 = r9
            r20 = r3
            goto L_0x00b7
        L_0x00b2:
            r20 = r0
            r9 = r2
            r21 = r3
        L_0x00b7:
            r0 = 10
            r4 = r28
            r3 = 0
            com.app.office.simpletext.view.IView r0 = com.app.office.wp.view.ViewFactory.createView(r4, r12, r3, r0)
            r2 = r0
            com.app.office.wp.view.RowView r2 = (com.app.office.wp.view.RowView) r2
            r14.appendChlidView(r2)
            r2.setStartOffset(r6)
            r2.setLocation(r8, r9)
            r22 = 0
            r0 = r27
            r1 = r28
            r35 = r2
            r2 = r29
            r23 = r3
            r3 = r30
            r4 = r31
            r24 = r5
            r5 = r32
            r41 = r6
            r6 = r33
            r7 = r35
            r23 = r9
            r8 = r41
            r25 = r10
            r10 = r22
            r11 = r23
            r26 = r12
            r12 = r39
            r22 = r13
            r13 = r20
            r14 = r25
            r15 = r19
            int r4 = r0.layoutRow(r1, r2, r3, r4, r5, r6, r7, r8, r10, r11, r12, r13, r14, r15)
            r0 = r35
            r2 = 1
            int r1 = r0.getLayoutSpan(r2)
            if (r1 == 0) goto L_0x010d
            int r3 = r20 - r1
            if (r3 >= 0) goto L_0x014c
        L_0x010d:
            if (r18 != 0) goto L_0x014c
            com.app.office.simpletext.view.IView r1 = r0.getPreView()
            com.app.office.wp.view.RowView r1 = (com.app.office.wp.view.RowView) r1
            r5 = r27
            if (r1 == 0) goto L_0x0133
            boolean r3 = r5.isBreakPages(r1)
            if (r3 == 0) goto L_0x0133
            short r3 = r5.rowIndex
            int r3 = r3 - r2
            short r3 = (short) r3
            r5.rowIndex = r3
            com.app.office.simpletext.model.IElement r3 = r1.getElement()
            com.app.office.wp.model.RowElement r3 = (com.app.office.wp.model.RowElement) r3
            r5.breakRowElement = r3
            r12 = r26
            r5.clearCurrentRowBreakPageCell(r12)
            goto L_0x0140
        L_0x0133:
            r12 = r26
            com.app.office.simpletext.model.IElement r3 = r0.getElement()
            com.app.office.wp.model.RowElement r3 = (com.app.office.wp.model.RowElement) r3
            r5.breakRowElement = r3
            r5.clearCurrentRowBreakPageCell(r12)
        L_0x0140:
            r6 = r34
            r6.deleteView(r0, r2)
            r3 = r21
            r8 = r24
            r9 = 0
            r11 = 1
            goto L_0x0199
        L_0x014c:
            r5 = r27
            r6 = r34
            if (r4 != r2) goto L_0x015a
            java.util.Map<java.lang.Integer, com.app.office.wp.view.BreakPagesCell> r3 = r5.breakPagesCell
            int r3 = r3.size()
            if (r3 > 0) goto L_0x015e
        L_0x015a:
            boolean r3 = r5.isRowBreakPages
            if (r3 == 0) goto L_0x0166
        L_0x015e:
            com.app.office.simpletext.model.IElement r3 = r0.getElement()
            com.app.office.wp.model.RowElement r3 = (com.app.office.wp.model.RowElement) r3
            r5.breakRowElement = r3
        L_0x0166:
            r3 = 0
            int r7 = r0.getLayoutSpan(r3)
            r8 = r24
            int r7 = java.lang.Math.max(r8, r7)
            int r8 = r23 + r1
            int r1 = r21 + r1
            r9 = 0
            long r10 = r0.getEndOffset(r9)
            r3 = r1
            r15 = r5
            r14 = r6
            r5 = r7
            r2 = r8
            r6 = r10
            r13 = r22
            r10 = r25
            r8 = 0
            r11 = 1
            r12 = 2
            r18 = 0
            r19 = 0
            r1 = r0
            r0 = r20
            goto L_0x0044
        L_0x0190:
            r8 = r5
            r41 = r6
            r22 = r13
            r6 = r14
            r5 = r15
            r2 = 1
        L_0x0198:
            r11 = r4
        L_0x0199:
            r5.layoutMergedCell(r1, r9, r2)
            r9 = r41
            r6.setEndOffset(r9)
            r6.setSize(r8, r3)
            r0 = r31
            byte r0 = r0.rootType
            if (r0 != 0) goto L_0x01ec
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r3 = r22.getAttribute()
            int r0 = r0.getParaHorizontalAlign(r3)
            byte r0 = (byte) r0
            int r3 = r39 - r8
            if (r0 == r2) goto L_0x01e0
            r4 = 2
            if (r0 != r4) goto L_0x01bf
            goto L_0x01e0
        L_0x01bf:
            int r0 = r34.getX()
            com.app.office.simpletext.view.TableAttr r2 = r5.tableAttr
            int r2 = r2.leftMargin
            int r0 = r0 - r2
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r3 = r22.getAttribute()
            int r2 = r2.getParaIndentLeft(r3)
            float r2 = (float) r2
            r3 = 1032358025(0x3d888889, float:0.06666667)
            float r2 = r2 * r3
            int r2 = (int) r2
            int r0 = r0 + r2
            r6.setX(r0)
            goto L_0x01ec
        L_0x01e0:
            if (r0 != r2) goto L_0x01e4
            int r3 = r3 / 2
        L_0x01e4:
            int r0 = r34.getX()
            int r0 = r0 + r3
            r6.setX(r0)
        L_0x01ec:
            r5.breakRowView = r1
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.wp.view.TableLayoutKit.layoutTable(com.app.office.system.IControl, com.app.office.simpletext.model.IDocument, com.app.office.simpletext.view.IRoot, com.app.office.simpletext.view.DocAttr, com.app.office.simpletext.view.PageAttr, com.app.office.simpletext.view.ParaAttr, com.app.office.wp.view.TableView, long, int, int, int, int, int, boolean):int");
    }

    private void clearCurrentRowBreakPageCell(IElement iElement) {
        Vector vector = new Vector();
        for (Integer next : this.breakPagesCell.keySet()) {
            BreakPagesCell breakPagesCell2 = this.breakPagesCell.get(next);
            if (breakPagesCell2.getCell().getStartOffset() >= iElement.getStartOffset() && breakPagesCell2.getCell().getEndOffset() <= iElement.getEndOffset()) {
                vector.add(next);
            }
        }
        Iterator it = vector.iterator();
        while (it.hasNext()) {
            this.breakPagesCell.remove((Integer) it.next());
        }
    }

    public int layoutRow(IControl iControl, IDocument iDocument, IRoot iRoot, DocAttr docAttr, PageAttr pageAttr, ParaAttr paraAttr, RowView rowView, long j, int i, int i2, int i3, int i4, int i5, boolean z) {
        long j2;
        RowElement rowElement;
        long j3;
        IElement iElement;
        boolean z2;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        long j4;
        RowElement rowElement2;
        CellView cellView;
        boolean z3;
        CellView cellView2;
        RowView rowView2;
        CellView cellView3;
        TableLayoutKit tableLayoutKit = this;
        RowView rowView3 = rowView;
        boolean z4 = z;
        RowElement rowElement3 = (RowElement) rowView.getElement();
        long endOffset = rowElement3.getEndOffset();
        int i11 = 0;
        int i12 = i3;
        int tableRowHeight = (int) (((float) AttrManage.instance().getTableRowHeight(rowElement3.getAttribute())) * 0.06666667f);
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        boolean z5 = true;
        long j5 = j;
        while (true) {
            j2 = endOffset;
            if (i17 >= rowElement3.getCellNumber()) {
                break;
            }
            if (!z4 || tableLayoutKit.breakPagesCell.size() <= 0) {
                iElement = rowElement3.getElementForIndex(i17);
                if (iElement == null) {
                    break;
                }
                long startOffset = iElement.getStartOffset();
                z2 = startOffset == iElement.getEndOffset();
                if (!z2 && (rowView2 = tableLayoutKit.breakRowView) != null && z4 && (cellView3 = rowView2.getCellView((short) i17)) != null) {
                    z2 = cellView3.getEndOffset((IDocument) null) == iElement.getEndOffset();
                }
                rowElement = rowElement3;
                j3 = startOffset;
            } else if (tableLayoutKit.breakPagesCell.containsKey(Integer.valueOf(i17))) {
                BreakPagesCell remove = tableLayoutKit.breakPagesCell.remove(Integer.valueOf(i17));
                iElement = remove.getCell();
                rowElement = rowElement3;
                j3 = remove.getBreakOffset();
                z2 = false;
            } else {
                rowElement = rowElement3;
                j3 = j5;
                iElement = rowElement3.getElementForIndex(i17);
                z2 = true;
            }
            CellView cellView4 = (CellView) ViewFactory.createView(iControl, iElement, (IElement) null, 11);
            rowView3.appendChlidView(cellView4);
            cellView4.setStartOffset(j3);
            cellView4.setLocation(i15, i11);
            cellView4.setColumn((short) i17);
            if (z2) {
                cellView4.setFirstMergedCell(z4);
                CellView cellView5 = cellView4;
                i6 = i14;
                i7 = i15;
                i8 = i16;
                i9 = i17;
                i10 = tableRowHeight;
                j4 = j2;
                rowElement2 = rowElement;
                i13 = layoutCellForNull(iDocument, iRoot, docAttr, pageAttr, paraAttr, cellView5, j3, i7, 0, i12, i4, i5, i9, z);
                cellView2 = cellView5;
            } else {
                CellView cellView6 = cellView4;
                i6 = i14;
                i7 = i15;
                i8 = i16;
                i9 = i17;
                i10 = tableRowHeight;
                j4 = j2;
                rowElement2 = rowElement;
                if (z || AttrManage.instance().isTableVerFirstMerged(iElement.getAttribute())) {
                    cellView = cellView6;
                    z3 = true;
                } else {
                    cellView = cellView6;
                    z3 = false;
                }
                cellView.setFirstMergedCell(z3);
                cellView.setMergedCell(AttrManage.instance().isTableVerMerged(iElement.getAttribute()));
                i13 = layoutCell(iControl, iDocument, iRoot, docAttr, pageAttr, paraAttr, cellView, j3, i7, 0, i12, i4, i5, i9, z);
                cellView2 = cellView;
            }
            int layoutSpan = cellView2.getLayoutSpan((byte) 0);
            int layoutSpan2 = cellView2.getLayoutSpan((byte) 1);
            z5 = z5 && layoutSpan2 == 0;
            int i18 = i7 + layoutSpan;
            int i19 = i8 + layoutSpan;
            i12 -= layoutSpan;
            tableRowHeight = !cellView2.isMergedCell() ? Math.max(i10, layoutSpan2) : i10;
            if (cellView2.isFirstMergedCell()) {
                this.mergedCell.add(cellView2);
            }
            i14 = Math.max(i6, layoutSpan2);
            rowView3 = rowView;
            z4 = z;
            i15 = i18;
            i16 = i19;
            tableLayoutKit = this;
            rowElement3 = rowElement2;
            i11 = 0;
            i17 = i9 + 1;
            j5 = cellView2.getEndOffset((IDocument) null);
            endOffset = j4;
        }
        int i20 = i16;
        int i21 = tableRowHeight;
        TableLayoutKit tableLayoutKit2 = tableLayoutKit;
        long j6 = j2;
        for (CellView cellView7 = (CellView) rowView.getChildView(); cellView7 != null; cellView7 = (CellView) cellView7.getNextView()) {
            if (!cellView7.isMergedCell() && cellView7.getLayoutSpan((byte) 1) < i21) {
                cellView7.setHeight((i21 - cellView7.getTopIndent()) - cellView7.getBottomIndent());
                CellElement cellElement = (CellElement) cellView7.getElement();
                if (cellElement != null) {
                    tableLayoutKit2.tableAttr.cellVerticalAlign = (byte) AttrManage.instance().getTableCellVerAlign(cellElement.getAttribute());
                }
                tableLayoutKit2.layoutCellVerticalAlign(cellView7);
            }
        }
        RowView rowView4 = rowView;
        rowView4.setEndOffset(j6);
        rowView4.setSize(i20, z5 ? Integer.MAX_VALUE : i21);
        tableLayoutKit2.breakRowView = null;
        return i13;
    }

    public int layoutCell(IControl iControl, IDocument iDocument, IRoot iRoot, DocAttr docAttr, PageAttr pageAttr, ParaAttr paraAttr, CellView cellView, long j, int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        int i7;
        int i8;
        long j2;
        CellElement cellElement;
        int i9;
        boolean z2;
        long j3;
        int i10;
        CellView cellView2 = cellView;
        CellElement cellElement2 = (CellElement) cellView.getElement();
        AttrManage.instance().fillTableAttr(this.tableAttr, cellElement2.getAttribute());
        cellView2.setBackground(this.tableAttr.cellBackground);
        cellView2.setIndent(this.tableAttr.leftMargin, this.tableAttr.topMargin, this.tableAttr.rightMargin, this.tableAttr.bottomMargin);
        int i11 = this.tableAttr.leftMargin;
        int i12 = this.tableAttr.topMargin;
        long endOffset = cellElement2.getEndOffset();
        int i13 = (i4 - this.tableAttr.topMargin) - this.tableAttr.bottomMargin;
        int i14 = (this.tableAttr.cellWidth - this.tableAttr.leftMargin) - this.tableAttr.rightMargin;
        long j4 = j;
        int i15 = i5;
        int i16 = i12;
        int i17 = i13;
        int i18 = 0;
        int i19 = 0;
        while (true) {
            i7 = (j4 > endOffset ? 1 : (j4 == endOffset ? 0 : -1));
            if (i7 >= 0 || i17 <= 0 || i18 == 1) {
                cellElement = cellElement2;
                i9 = i19;
                j2 = j4;
                i8 = i14;
                z2 = true;
            } else {
                IElement paragraph = iDocument.getParagraph(j4);
                cellElement = cellElement2;
                ParagraphView paragraphView = (ParagraphView) ViewFactory.createView(iControl, paragraph, (IElement) null, 5);
                cellView2.appendChlidView(paragraphView);
                paragraphView.setStartOffset(j4);
                paragraphView.setLocation(i11, i16);
                AttrManage.instance().fillParaAttr(cellView.getControl(), paraAttr, paragraph.getAttribute());
                ParagraphView paragraphView2 = paragraphView;
                z2 = true;
                int i20 = i19;
                int i21 = i16;
                j2 = j4;
                int i22 = i15;
                i8 = i14;
                i18 = LayoutKit.instance().layoutPara(iControl, iDocument, docAttr, pageAttr, paraAttr, paragraphView2, j4, i11, i21, i14, i17, i22);
                ParagraphView paragraphView3 = paragraphView2;
                int layoutSpan = paragraphView3.getLayoutSpan((byte) 1);
                if (paragraphView3.getChildView() == null) {
                    cellView2.deleteView(paragraphView3, true);
                    i9 = i20;
                    break;
                }
                if (iRoot.getViewContainer() != null) {
                    iRoot.getViewContainer().add(paragraphView3);
                }
                i16 = i21 + layoutSpan;
                i19 = i20 + layoutSpan;
                i17 -= layoutSpan;
                j4 = paragraphView3.getEndOffset((IDocument) null);
                paragraphView3.setEndOffset(j4);
                i15 = ViewKit.instance().setBitValue(i22, 0, false);
                cellElement2 = cellElement;
                i14 = i8;
            }
        }
        if (i7 >= 0 || this.breakPagesCell.containsKey(Integer.valueOf(i6))) {
            j3 = j2;
            i10 = i8;
        } else {
            i10 = i8;
            if (i10 > 0) {
                j3 = j2;
                this.breakPagesCell.put(Integer.valueOf(i6), new BreakPagesCell(cellElement, j3));
                this.isRowBreakPages = z2;
            } else {
                j3 = j2;
            }
        }
        cellView2.setEndOffset(j3);
        cellView2.setSize(i10, i9);
        return i18;
    }

    public int layoutCellForNull(IDocument iDocument, IRoot iRoot, DocAttr docAttr, PageAttr pageAttr, ParaAttr paraAttr, CellView cellView, long j, int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        AttrManage.instance().fillTableAttr(this.tableAttr, ((CellElement) cellView.getElement()).getAttribute());
        cellView.setIndent(this.tableAttr.leftMargin, this.tableAttr.topMargin, this.tableAttr.rightMargin, this.tableAttr.bottomMargin);
        cellView.setSize((this.tableAttr.cellWidth - this.tableAttr.leftMargin) - this.tableAttr.rightMargin, 0);
        return 0;
    }

    private void layoutMergedCell(RowView rowView, RowElement rowElement, boolean z) {
        if (rowView != null) {
            int y = rowView.getY() + rowView.getLayoutSpan((byte) 1);
            if (z) {
                Iterator<CellView> it = this.mergedCell.iterator();
                while (it.hasNext()) {
                    CellView next = it.next();
                    if (next.getParentView() != null) {
                        next.setHeight(y - next.getParentView().getY());
                        layoutCellVerticalAlign(next);
                    }
                }
                this.mergedCell.clear();
                return;
            }
            Iterator<CellView> it2 = this.mergedCell.iterator();
            while (it2.hasNext()) {
                CellView next2 = it2.next();
                y = Math.max(y, next2.getParentView().getY() + next2.getLayoutSpan((byte) 1));
            }
            Vector vector = new Vector();
            Iterator<CellView> it3 = this.mergedCell.iterator();
            while (it3.hasNext()) {
                CellView next3 = it3.next();
                IElement elementForIndex = rowElement.getElementForIndex(next3.getColumn());
                if (elementForIndex != null && (!AttrManage.instance().isTableVerMerged(elementForIndex.getAttribute()) || AttrManage.instance().isTableVerFirstMerged(elementForIndex.getAttribute()))) {
                    if (next3.getParentView().getY() + next3.getLayoutSpan((byte) 1) < y) {
                        next3.setHeight(y - next3.getParentView().getY());
                        layoutCellVerticalAlign(next3);
                    } else {
                        rowView.setHeight(y - rowView.getY());
                        for (CellView cellView = (CellView) rowView.getChildView(); cellView != null; cellView = (CellView) cellView.getNextView()) {
                            if (!cellView.isMergedCell()) {
                                int height = cellView.getHeight();
                                cellView.setHeight(y - cellView.getParentView().getY());
                                if (height != cellView.getHeight()) {
                                    layoutCellVerticalAlign(cellView);
                                }
                            }
                        }
                    }
                    vector.add(next3);
                }
            }
            Iterator it4 = vector.iterator();
            while (it4.hasNext()) {
                CellView cellView2 = (CellView) it4.next();
                if (cellView2.getParentView().getY() + cellView2.getLayoutSpan((byte) 1) > rowView.getY() + rowView.getLayoutSpan((byte) 1)) {
                    cellView2.setHeight((rowView.getY() + rowView.getLayoutSpan((byte) 1)) - cellView2.getY());
                }
                this.mergedCell.remove(cellView2);
            }
        }
    }

    private boolean isBreakPages(RowView rowView) {
        for (IView childView = rowView.getChildView(); childView != null; childView = childView.getNextView()) {
            if (childView.getEndOffset((IDocument) null) != childView.getElement().getEndOffset() && childView.getWidth() > 0) {
                return true;
            }
        }
        return false;
    }

    private void layoutCellVerticalAlign(CellView cellView) {
        if (this.tableAttr.cellVerticalAlign != 0) {
            int i = 0;
            for (IView childView = cellView.getChildView(); childView != null; childView = childView.getNextView()) {
                i += childView.getLayoutSpan((byte) 1);
            }
            int layoutSpan = cellView.getLayoutSpan((byte) 1) - i;
            int tableCellVerAlign = AttrManage.instance().getTableCellVerAlign(cellView.getElement().getAttribute());
            if (tableCellVerAlign == 1 || tableCellVerAlign == 2) {
                if (tableCellVerAlign == 1) {
                    layoutSpan /= 2;
                }
                for (IView childView2 = cellView.getChildView(); childView2 != null; childView2 = childView2.getNextView()) {
                    childView2.setY(childView2.getY() + layoutSpan);
                }
            }
        }
    }

    public boolean isTableBreakPages() {
        return this.breakPagesCell.size() > 0 || this.breakRowElement != null;
    }

    public void clearBreakPages() {
        this.rowIndex = 0;
        this.breakRowElement = null;
        this.breakPagesCell.clear();
        this.breakRowView = null;
    }

    public void dispose() {
        this.breakRowElement = null;
        this.breakPagesCell.clear();
        this.breakRowView = null;
    }
}
