package com.app.office.fc.ppt.attribute;

import com.itextpdf.text.html.HtmlTags;
import com.app.office.constant.wp.AttrIDConstant;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.ppt.bulletnumber.BulletNumberManage;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.Style;
import com.app.office.simpletext.model.StyleManage;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.system.IControl;

public class ParaAttr {
    public static final float POINT_PER_LINE_PER_FONTSIZE = 1.2f;
    private static ParaAttr kit = new ParaAttr();

    public static ParaAttr instance() {
        return kit;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x009c, code lost:
        r2 = r4.attributeValue("lvl");
     */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x022c  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0248  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int processParagraph(com.app.office.system.IControl r33, com.app.office.pg.model.PGMaster r34, com.app.office.pg.model.PGLayout r35, com.app.office.pg.model.PGStyle r36, com.app.office.simpletext.model.SectionElement r37, com.app.office.fc.dom4j.Element r38, com.app.office.fc.dom4j.Element r39, java.lang.String r40, int r41) {
        /*
            r32 = this;
            r10 = r32
            r11 = r34
            r12 = r35
            r13 = r36
            r14 = r38
            r0 = r39
            r15 = r40
            r9 = r41
            r8 = 0
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r8)
            java.lang.String r2 = "bodyPr"
            com.app.office.fc.dom4j.Element r2 = r0.element((java.lang.String) r2)
            r3 = 100
            if (r2 == 0) goto L_0x0063
            java.lang.String r4 = "normAutofit"
            com.app.office.fc.dom4j.Element r2 = r2.element((java.lang.String) r4)
            if (r2 == 0) goto L_0x0063
            java.lang.String r4 = "fontScale"
            com.app.office.fc.dom4j.Attribute r5 = r2.attribute((java.lang.String) r4)
            if (r5 == 0) goto L_0x0041
            java.lang.String r4 = r2.attributeValue((java.lang.String) r4)
            if (r4 == 0) goto L_0x0041
            int r5 = r4.length()
            if (r5 <= 0) goto L_0x0041
            int r3 = java.lang.Integer.parseInt(r4)
            int r3 = r3 / 1000
        L_0x0041:
            java.lang.String r4 = "lnSpcReduction"
            com.app.office.fc.dom4j.Attribute r5 = r2.attribute((java.lang.String) r4)
            if (r5 == 0) goto L_0x005e
            java.lang.String r2 = r2.attributeValue((java.lang.String) r4)
            if (r2 == 0) goto L_0x005e
            int r4 = r2.length()
            if (r4 <= 0) goto L_0x005e
            int r2 = java.lang.Integer.parseInt(r2)
            r16 = r2
            r17 = r3
            goto L_0x0067
        L_0x005e:
            r17 = r3
            r16 = 0
            goto L_0x0067
        L_0x0063:
            r16 = 0
            r17 = 100
        L_0x0067:
            java.lang.String r2 = "subTitle"
            boolean r18 = r2.equals(r15)
            java.lang.String r2 = "lstStyle"
            com.app.office.fc.dom4j.Element r7 = r0.element((java.lang.String) r2)
            java.lang.String r2 = "p"
            java.util.List r0 = r0.elements((java.lang.String) r2)
            java.util.Iterator r19 = r0.iterator()
            r6 = 0
        L_0x007e:
            boolean r0 = r19.hasNext()
            if (r0 == 0) goto L_0x0284
            java.lang.Object r0 = r19.next()
            r5 = r0
            com.app.office.fc.dom4j.Element r5 = (com.app.office.fc.dom4j.Element) r5
            java.lang.String r0 = "pPr"
            com.app.office.fc.dom4j.Element r4 = r5.element((java.lang.String) r0)
            r0 = 1
            if (r4 == 0) goto L_0x00af
            java.lang.String r2 = "lvl"
            com.app.office.fc.dom4j.Attribute r3 = r4.attribute((java.lang.String) r2)
            if (r3 == 0) goto L_0x00af
            java.lang.String r2 = r4.attributeValue((java.lang.String) r2)
            if (r2 == 0) goto L_0x00af
            int r3 = r2.length()
            if (r3 <= 0) goto L_0x00af
            int r2 = java.lang.Integer.parseInt(r2)
            int r2 = r2 + r0
            r3 = r2
            goto L_0x00b0
        L_0x00af:
            r3 = 1
        L_0x00b0:
            r2 = -1
            if (r12 == 0) goto L_0x00ba
            int r20 = r12.getStyleID(r15, r9, r3)
            r21 = r20
            goto L_0x00bc
        L_0x00ba:
            r21 = -1
        L_0x00bc:
            if (r11 == 0) goto L_0x00c2
            int r2 = r11.getTextStyle(r15, r9, r3)
        L_0x00c2:
            if (r2 >= 0) goto L_0x00d3
            if (r13 == 0) goto L_0x00d3
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            int r1 = r13.getStyle(r3)
            r20 = r0
            r22 = r1
            goto L_0x00d7
        L_0x00d3:
            r20 = r1
            r22 = r2
        L_0x00d7:
            com.app.office.simpletext.model.ParagraphElement r2 = new com.app.office.simpletext.model.ParagraphElement
            r2.<init>()
            long r0 = (long) r6
            r2.setStartOffset(r0)
            r0 = 0
            if (r7 == 0) goto L_0x018f
            java.lang.String r1 = "defPPr"
            if (r3 > 0) goto L_0x00f1
            com.app.office.fc.dom4j.Element r23 = r7.element((java.lang.String) r1)
            if (r23 != 0) goto L_0x00ee
            goto L_0x00f1
        L_0x00ee:
            r23 = r3
            goto L_0x00f3
        L_0x00f1:
            int r23 = r3 + 1
        L_0x00f3:
            switch(r23) {
                case 1: goto L_0x0137;
                case 2: goto L_0x0130;
                case 3: goto L_0x0129;
                case 4: goto L_0x0122;
                case 5: goto L_0x011b;
                case 6: goto L_0x0114;
                case 7: goto L_0x010d;
                case 8: goto L_0x0106;
                case 9: goto L_0x00ff;
                case 10: goto L_0x00f8;
                default: goto L_0x00f6;
            }
        L_0x00f6:
            r1 = r0
            goto L_0x013b
        L_0x00f8:
            java.lang.String r1 = "lvl9pPr"
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r1)
            goto L_0x013b
        L_0x00ff:
            java.lang.String r1 = "lvl8pPr"
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r1)
            goto L_0x013b
        L_0x0106:
            java.lang.String r1 = "lvl7pPr"
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r1)
            goto L_0x013b
        L_0x010d:
            java.lang.String r1 = "lvl6pPr"
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r1)
            goto L_0x013b
        L_0x0114:
            java.lang.String r1 = "lvl5pPr"
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r1)
            goto L_0x013b
        L_0x011b:
            java.lang.String r1 = "lvl4pPr"
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r1)
            goto L_0x013b
        L_0x0122:
            java.lang.String r1 = "lvl3pPr"
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r1)
            goto L_0x013b
        L_0x0129:
            java.lang.String r1 = "lvl2pPr"
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r1)
            goto L_0x013b
        L_0x0130:
            java.lang.String r1 = "lvl1pPr"
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r1)
            goto L_0x013b
        L_0x0137:
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r1)
        L_0x013b:
            if (r1 == 0) goto L_0x018f
            com.app.office.simpletext.model.AttributeSetImpl r0 = new com.app.office.simpletext.model.AttributeSetImpl
            r0.<init>()
            r23 = 0
            r24 = -1
            r25 = -1
            r26 = 0
            r27 = 1
            r39 = r0
            r0 = r32
            r28 = r1
            r1 = r33
            r29 = r2
            r2 = r28
            r30 = r3
            r3 = r39
            r31 = r4
            r4 = r23
            r23 = r5
            r5 = r24
            r24 = r6
            r6 = r25
            r25 = r7
            r7 = r26
            r8 = r27
            r9 = r18
            r0.setParaAttribute(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            java.lang.String r0 = "defRPr"
            r8 = r28
            com.app.office.fc.dom4j.Element r2 = r8.element((java.lang.String) r0)
            com.app.office.fc.ppt.attribute.RunAttr r0 = com.app.office.fc.ppt.attribute.RunAttr.instance()
            r4 = 0
            r5 = 100
            r6 = -1
            r7 = 0
            r1 = r34
            r0.setRunAttribute(r1, r2, r3, r4, r5, r6, r7)
            r0 = r39
            r10.processParaWithPct(r8, r0)
            goto L_0x019b
        L_0x018f:
            r29 = r2
            r30 = r3
            r31 = r4
            r23 = r5
            r24 = r6
            r25 = r7
        L_0x019b:
            r8 = r21
            if (r0 != 0) goto L_0x01b0
            if (r8 <= 0) goto L_0x01b0
            com.app.office.simpletext.model.StyleManage r1 = com.app.office.simpletext.model.StyleManage.instance()
            com.app.office.simpletext.model.Style r1 = r1.getStyle(r8)
            if (r1 == 0) goto L_0x01fc
            com.app.office.simpletext.model.IAttributeSet r0 = r1.getAttrbuteSet()
            goto L_0x01fc
        L_0x01b0:
            if (r0 != 0) goto L_0x01da
            if (r14 == 0) goto L_0x01da
            java.lang.String r1 = "fontRef"
            com.app.office.fc.dom4j.Element r1 = r14.element((java.lang.String) r1)
            java.util.List r2 = r1.elements()
            int r2 = r2.size()
            if (r2 <= 0) goto L_0x01fc
            com.app.office.fc.ppt.reader.ReaderKit r0 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            int r0 = r0.getColor(r11, r1)
            com.app.office.simpletext.model.AttributeSetImpl r1 = new com.app.office.simpletext.model.AttributeSetImpl
            r1.<init>()
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            r2.setFontColor(r1, r0)
            r0 = r1
            goto L_0x01fc
        L_0x01da:
            boolean r1 = r20.booleanValue()
            if (r1 == 0) goto L_0x01fc
            if (r0 != 0) goto L_0x01fc
            if (r13 == 0) goto L_0x01fc
            r2 = r30
            java.lang.String r1 = r13.getDefaultFontColor(r2)
            if (r1 == 0) goto L_0x01fc
            com.app.office.simpletext.model.AttributeSetImpl r0 = new com.app.office.simpletext.model.AttributeSetImpl
            r0.<init>()
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r11.getColor(r1)
            r2.setFontColor(r0, r1)
        L_0x01fc:
            r9 = r0
            com.app.office.fc.ppt.attribute.RunAttr r0 = com.app.office.fc.ppt.attribute.RunAttr.instance()
            r1 = r34
            r2 = r29
            r3 = r23
            r4 = r9
            r5 = r24
            r6 = r17
            r7 = r22
            int r7 = r0.processRun(r1, r2, r3, r4, r5, r6, r7)
            java.lang.String r0 = "r"
            r1 = r23
            java.util.List r0 = r1.elements((java.lang.String) r0)
            int r0 = r0.size()
            if (r0 != 0) goto L_0x0248
            java.lang.String r0 = "fld"
            java.util.List r0 = r1.elements((java.lang.String) r0)
            int r0 = r0.size()
            if (r0 != 0) goto L_0x0248
            com.app.office.simpletext.model.IAttributeSet r3 = r29.getAttribute()
            r21 = 0
            r0 = r32
            r1 = r33
            r2 = r31
            r4 = r9
            r5 = r8
            r6 = r22
            r9 = r7
            r7 = r16
            r8 = r21
            r11 = r9
            r9 = r18
            r0.setParaAttribute(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0262
        L_0x0248:
            r11 = r7
            com.app.office.simpletext.model.IAttributeSet r3 = r29.getAttribute()
            r21 = 1
            r0 = r32
            r1 = r33
            r2 = r31
            r4 = r9
            r5 = r8
            r6 = r22
            r7 = r16
            r8 = r21
            r9 = r18
            r0.setParaAttribute(r1, r2, r3, r4, r5, r6, r7, r8, r9)
        L_0x0262:
            com.app.office.simpletext.model.IAttributeSet r0 = r29.getAttribute()
            r1 = r31
            r10.processParaWithPct(r1, r0)
            long r0 = (long) r11
            r2 = r29
            r2.setEndOffset(r0)
            r0 = 0
            r3 = r37
            r3.appendParagraph(r2, r0)
            r9 = r41
            r6 = r11
            r1 = r20
            r7 = r25
            r8 = 0
            r11 = r34
            goto L_0x007e
        L_0x0284:
            r24 = r6
            com.app.office.fc.ppt.bulletnumber.BulletNumberManage r0 = com.app.office.fc.ppt.bulletnumber.BulletNumberManage.instance()
            r0.clearData()
            com.app.office.fc.ppt.attribute.RunAttr r0 = com.app.office.fc.ppt.attribute.RunAttr.instance()
            r1 = 0
            r0.setMaxFontSize(r1)
            return r24
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.attribute.ParaAttr.processParagraph(com.app.office.system.IControl, com.app.office.pg.model.PGMaster, com.app.office.pg.model.PGLayout, com.app.office.pg.model.PGStyle, com.app.office.simpletext.model.SectionElement, com.app.office.fc.dom4j.Element, com.app.office.fc.dom4j.Element, java.lang.String, int):int");
    }

    public void setParaAlign(IAttributeSet iAttributeSet, String str) {
        if (str.equals("l")) {
            AttrManage.instance().setParaHorizontalAlign(iAttributeSet, 0);
        } else if (str.equals("ctr")) {
            AttrManage.instance().setParaHorizontalAlign(iAttributeSet, 1);
        } else if (str.equals("r")) {
            AttrManage.instance().setParaHorizontalAlign(iAttributeSet, 2);
        }
    }

    public void setParaHorizontalAlign(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 4102)) {
            AttrManage.instance().setParaHorizontalAlign(iAttributeSet2, AttrManage.instance().getParaHorizontalAlign(iAttributeSet));
        }
    }

    public void setParaBefore(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PARA_BEFORE_ID)) {
            AttrManage.instance().setParaBefore(iAttributeSet2, AttrManage.instance().getParaBefore(iAttributeSet));
        }
    }

    public void setParaAfter(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PARA_AFTER_ID)) {
            AttrManage.instance().setParaAfter(iAttributeSet2, AttrManage.instance().getParaAfter(iAttributeSet));
        }
    }

    public void setParaLineSpace(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null) {
            if (AttrManage.instance().hasAttribute(iAttributeSet, 4106)) {
                AttrManage.instance().setParaLineSpaceType(iAttributeSet2, AttrManage.instance().getParaLineSpaceType(iAttributeSet));
            }
            if (AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PARA_LINESPACE_ID)) {
                AttrManage.instance().setParaLineSpace(iAttributeSet2, AttrManage.instance().getParaLineSpace(iAttributeSet));
            }
        }
    }

    public void setParaIndentLeft(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 4097)) {
            AttrManage.instance().setParaIndentLeft(iAttributeSet2, AttrManage.instance().getParaIndentLeft(iAttributeSet));
        }
    }

    public void setParaIndentRight(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 4099)) {
            AttrManage.instance().setParaIndentRight(iAttributeSet2, AttrManage.instance().getParaIndentRight(iAttributeSet));
        }
    }

    public void setParaSpecialIndent(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PARA_SPECIALINDENT_ID)) {
            AttrManage.instance().setParaSpecialIndent(iAttributeSet2, AttrManage.instance().getParaSpecialIndent(iAttributeSet));
        }
    }

    public void setParaAttribute(IControl iControl, Element element, IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2, int i, int i2, int i3, boolean z, boolean z2) {
        String attributeValue;
        String attributeValue2;
        String attributeValue3;
        String attributeValue4;
        Element element2 = element;
        IAttributeSet iAttributeSet3 = iAttributeSet;
        IAttributeSet iAttributeSet4 = iAttributeSet2;
        int i4 = i;
        int i5 = i2;
        if (element2 != null) {
            if (element2.attribute("algn") != null) {
                setParaAlign(iAttributeSet3, element2.attributeValue("algn"));
            } else {
                setParaHorizontalAlign(iAttributeSet4, iAttributeSet3);
            }
            Element element3 = element2.element("spcBef");
            if (element3 != null) {
                Element element4 = element3.element("spcPts");
                if (!(element4 == null || element4.attribute("val") == null || (attributeValue4 = element4.attributeValue("val")) == null || attributeValue4.length() <= 0)) {
                    AttrManage.instance().setParaBefore(iAttributeSet3, (int) (((float) (Integer.parseInt(attributeValue4) / 100)) * 20.0f));
                }
            } else {
                setParaBefore(iAttributeSet4, iAttributeSet3);
            }
            Element element5 = element2.element("spcAft");
            if (element5 != null) {
                Element element6 = element5.element("spcPts");
                if (!(element6 == null || element6.attribute("val") == null || (attributeValue3 = element6.attributeValue("val")) == null || attributeValue3.length() <= 0)) {
                    AttrManage.instance().setParaAfter(iAttributeSet3, (int) (((float) (Integer.parseInt(attributeValue3) / 100)) * 20.0f));
                }
            } else {
                setParaAfter(iAttributeSet4, iAttributeSet3);
            }
            Element element7 = element2.element("lnSpc");
            if (element7 != null) {
                Element element8 = element7.element("spcPts");
                if (!(element8 == null || element8.attribute("val") == null || (attributeValue2 = element8.attributeValue("val")) == null || attributeValue2.length() <= 0)) {
                    AttrManage.instance().setParaLineSpaceType(iAttributeSet3, 4);
                    AttrManage.instance().setParaLineSpace(iAttributeSet3, (float) ((int) (((float) (Integer.parseInt(attributeValue2) / 100)) * 20.0f)));
                }
                Element element9 = element7.element("spcPct");
                if (!(element9 == null || element9.attribute("val") == null || (attributeValue = element9.attributeValue("val")) == null || attributeValue.length() <= 0)) {
                    AttrManage.instance().setParaLineSpaceType(iAttributeSet3, 5);
                    AttrManage.instance().setParaLineSpace(iAttributeSet3, ((float) (Integer.parseInt(attributeValue) - i3)) / 100000.0f);
                }
            } else if (i3 > 0) {
                AttrManage.instance().setParaLineSpaceType(iAttributeSet3, 5);
                AttrManage.instance().setParaLineSpace(iAttributeSet3, ((float) (100000 - i3)) / 100000.0f);
            } else {
                setParaLineSpace(iAttributeSet4, iAttributeSet3);
            }
            if (element2.attribute("marR") != null) {
                String attributeValue5 = element2.attributeValue("marR");
                if (attributeValue5 != null && attributeValue5.length() > 0) {
                    AttrManage.instance().setParaIndentRight(iAttributeSet3, (int) (((((float) Integer.parseInt(attributeValue5)) * 72.0f) / 914400.0f) * 20.0f));
                }
            } else {
                setParaIndentRight(iAttributeSet4, iAttributeSet3);
            }
        } else {
            setParaHorizontalAlign(iAttributeSet4, iAttributeSet3);
            setParaBefore(iAttributeSet4, iAttributeSet3);
            setParaAfter(iAttributeSet4, iAttributeSet3);
            if (i3 > 0) {
                AttrManage.instance().setParaLineSpaceType(iAttributeSet3, 5);
                AttrManage.instance().setParaLineSpace(iAttributeSet3, ((float) (100000 - i3)) / 100000.0f);
            } else {
                setParaLineSpace(iAttributeSet4, iAttributeSet3);
            }
            setParaIndentLeft(iAttributeSet4, iAttributeSet3);
            setParaIndentRight(iAttributeSet4, iAttributeSet3);
        }
        Style style = StyleManage.instance().getStyle(i5);
        int i6 = 0;
        if (element2 != null && element2.attribute("marL") != null) {
            String attributeValue6 = element2.attributeValue("marL");
            if (attributeValue6 != null && attributeValue6.length() > 0) {
                i6 = (int) (((((float) Integer.parseInt(attributeValue6)) * 72.0f) / 914400.0f) * 20.0f);
                AttrManage.instance().setParaIndentInitLeft(iAttributeSet3, i6);
                AttrManage.instance().setParaIndentLeft(iAttributeSet3, i6);
            }
        } else if (iAttributeSet4 != null) {
            if (AttrManage.instance().hasAttribute(iAttributeSet4, 4097)) {
                i6 = AttrManage.instance().getParaIndentInitLeft(iAttributeSet4);
                AttrManage.instance().setParaIndentLeft(iAttributeSet3, i6);
            }
        } else if (!(style == null || style.getAttrbuteSet() == null || !AttrManage.instance().hasAttribute(style.getAttrbuteSet(), 4097))) {
            i6 = AttrManage.instance().getParaIndentInitLeft(style.getAttrbuteSet());
            AttrManage.instance().setParaIndentLeft(iAttributeSet3, i6);
        }
        if (element2 != null && element2.attribute(HtmlTags.INDENT) != null) {
            String attributeValue7 = element2.attributeValue(HtmlTags.INDENT);
            if (attributeValue7 != null && attributeValue7.length() > 0) {
                setSpecialIndent(iAttributeSet3, i6, (int) (((((float) Integer.parseInt(attributeValue7)) * 72.0f) / 914400.0f) * 20.0f), true);
            }
        } else if (iAttributeSet4 != null) {
            if (AttrManage.instance().hasAttribute(iAttributeSet4, AttrIDConstant.PARA_SPECIALINDENT_ID)) {
                setSpecialIndent(iAttributeSet3, i6, AttrManage.instance().getParaSpecialIndent(iAttributeSet4), true);
            }
        } else if (!(style == null || style.getAttrbuteSet() == null || !AttrManage.instance().hasAttribute(style.getAttrbuteSet(), AttrIDConstant.PARA_SPECIALINDENT_ID))) {
            setSpecialIndent(iAttributeSet3, i6, AttrManage.instance().getParaSpecialIndent(style.getAttrbuteSet()), true);
        }
        if (z && (element2 == null || (element2 != null && element2.element("buNone") == null))) {
            int addBulletNumber = BulletNumberManage.instance().addBulletNumber(iControl, -1, element2);
            if (addBulletNumber == -1 && iAttributeSet4 != null) {
                addBulletNumber = AttrManage.instance().getPGParaBulletID(iAttributeSet4);
            }
            if (addBulletNumber == -1 && i4 >= 0) {
                addBulletNumber = BulletNumberManage.instance().getBulletID(i4);
            }
            if (addBulletNumber == -1 && i5 > 0 && !z2) {
                addBulletNumber = BulletNumberManage.instance().getBulletID(i5);
            }
            if (addBulletNumber >= 0) {
                AttrManage.instance().setPGParaBulletID(iAttributeSet3, addBulletNumber);
            }
        }
        if (i5 > 0) {
            AttrManage.instance().setParaStyleID(iAttributeSet3, i5);
        }
    }

    public void setSpecialIndent(IAttributeSet iAttributeSet, int i, int i2, boolean z) {
        if (i2 < 0 && Math.abs(i2) > i) {
            i2 = -i;
        }
        AttrManage.instance().setParaSpecialIndent(iAttributeSet, i2);
        if (z && i2 < 0) {
            AttrManage.instance().setParaIndentLeft(iAttributeSet, i + i2);
        }
    }

    public void setParaAttribute(CellStyle cellStyle, IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (cellStyle != null && iAttributeSet2 != null) {
            int indent = cellStyle.getIndent() * 34;
            switch (cellStyle.getHorizontalAlign()) {
                case 0:
                case 1:
                    iAttributeSet2.setAttribute(4097, Math.round(((float) indent) * 15.0f));
                    iAttributeSet2.setAttribute(4099, 0);
                    AttrManage.instance().setParaHorizontalAlign(iAttributeSet, 0);
                    break;
                case 2:
                case 4:
                case 5:
                case 6:
                    AttrManage.instance().setParaHorizontalAlign(iAttributeSet, 1);
                    break;
                case 3:
                    iAttributeSet2.setAttribute(4097, 0);
                    iAttributeSet2.setAttribute(4099, Math.round(((float) indent) * 15.0f));
                    AttrManage.instance().setParaHorizontalAlign(iAttributeSet, 2);
                    break;
            }
            setParaBefore(iAttributeSet2, iAttributeSet);
            setParaAfter(iAttributeSet2, iAttributeSet);
            setParaLineSpace(iAttributeSet2, iAttributeSet);
            setParaIndentLeft(iAttributeSet2, iAttributeSet);
            setParaIndentRight(iAttributeSet2, iAttributeSet);
            setParaSpecialIndent(iAttributeSet2, iAttributeSet);
        } else if (iAttributeSet2 != null) {
            setParaHorizontalAlign(iAttributeSet2, iAttributeSet);
            setParaBefore(iAttributeSet2, iAttributeSet);
            setParaAfter(iAttributeSet2, iAttributeSet);
            setParaLineSpace(iAttributeSet2, iAttributeSet);
        }
    }

    public void processParaWithPct(Element element, IAttributeSet iAttributeSet) {
        Element element2;
        String attributeValue;
        Element element3;
        String attributeValue2;
        int maxFontSize = RunAttr.instance().getMaxFontSize();
        if (element != null) {
            Element element4 = element.element("spcBef");
            if (!(element4 == null || (element3 = element4.element("spcPct")) == null || element3.attribute("val") == null || (attributeValue2 = element3.attributeValue("val")) == null || attributeValue2.length() <= 0)) {
                AttrManage.instance().setParaBefore(iAttributeSet, (int) ((((float) Integer.parseInt(attributeValue2)) / 100000.0f) * ((float) maxFontSize) * 1.2f * 20.0f));
            }
            Element element5 = element.element("spcAft");
            if (element5 != null && (element2 = element5.element("spcPct")) != null && element2.attribute("val") != null && (attributeValue = element2.attributeValue("val")) != null && attributeValue.length() > 0) {
                AttrManage.instance().setParaAfter(iAttributeSet, (int) ((((float) Integer.parseInt(attributeValue)) / 100000.0f) * ((float) maxFontSize) * 1.2f * 20.0f));
            }
        }
    }
}
