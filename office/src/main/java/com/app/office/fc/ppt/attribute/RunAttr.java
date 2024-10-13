package com.app.office.fc.ppt.attribute;

import androidx.core.view.ViewCompat;
import com.itextpdf.text.html.HtmlTags;
import com.onesignal.outcomes.OSOutcomeConstants;
import com.app.office.constant.SchemeClrConstant;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.ppt.reader.HyperlinkReader;
import com.app.office.fc.ppt.reader.ReaderKit;
import com.app.office.fc.xls.Reader.SchemeColorUtil;
import com.app.office.pg.model.PGMaster;
import com.app.office.simpletext.font.Font;
import com.app.office.simpletext.font.FontTypefaceManage;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.Style;
import com.app.office.simpletext.model.StyleManage;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.ss.util.ColorUtil;

public class RunAttr {
    private static RunAttr kit = new RunAttr();
    private int maxFontSize = 0;
    private boolean slide;
    private boolean table;

    public static RunAttr instance() {
        return kit;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: com.app.office.simpletext.model.IDocument} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: com.app.office.simpletext.model.IDocument} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: com.app.office.simpletext.model.IDocument} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v24, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v25, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v26, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int processRun(com.app.office.pg.model.PGMaster r21, com.app.office.simpletext.model.ParagraphElement r22, com.app.office.fc.dom4j.Element r23, com.app.office.simpletext.model.IAttributeSet r24, int r25, int r26, int r27) {
        /*
            r20 = this;
            r8 = r20
            r9 = r22
            r0 = r23
            r10 = r25
            r1 = 0
            r8.maxFontSize = r1
            java.lang.String r1 = "pPr"
            com.app.office.fc.dom4j.Element r1 = r0.element((java.lang.String) r1)
            java.lang.String r11 = "r"
            java.util.List r2 = r0.elements((java.lang.String) r11)
            int r2 = r2.size()
            java.lang.String r12 = "rPr"
            java.lang.String r13 = "\n"
            java.lang.String r14 = "br"
            java.lang.String r15 = "fld"
            if (r2 != 0) goto L_0x0082
            java.util.List r2 = r0.elements((java.lang.String) r15)
            int r2 = r2.size()
            if (r2 != 0) goto L_0x0082
            java.util.List r2 = r0.elements((java.lang.String) r14)
            int r2 = r2.size()
            if (r2 != 0) goto L_0x0082
            com.app.office.simpletext.model.LeafElement r11 = new com.app.office.simpletext.model.LeafElement
            r11.<init>(r13)
            if (r1 == 0) goto L_0x0044
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r12)
        L_0x0044:
            if (r1 != 0) goto L_0x004e
            java.lang.String r1 = "endParaRPr"
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r1)
            r2 = r0
            goto L_0x004f
        L_0x004e:
            r2 = r1
        L_0x004f:
            com.app.office.simpletext.model.IAttributeSet r3 = r11.getAttribute()
            r7 = 1
            r0 = r20
            r1 = r21
            r4 = r24
            r5 = r26
            r6 = r27
            r0.setRunAttribute(r1, r2, r3, r4, r5, r6, r7)
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r1 = r22.getAttribute()
            com.app.office.simpletext.model.IAttributeSet r2 = r11.getAttribute()
            int r0 = r0.getFontSize(r1, r2)
            r8.setMaxFontSize(r0)
            long r0 = (long) r10
            r11.setStartOffset(r0)
            int r0 = r10 + 1
            long r1 = (long) r0
            r11.setEndOffset(r1)
            r9.appendLeaf(r11)
            return r0
        L_0x0082:
            java.util.Iterator r16 = r23.elementIterator()
            r7 = 0
            r0 = r7
        L_0x0088:
            boolean r1 = r16.hasNext()
            if (r1 == 0) goto L_0x0158
            java.lang.Object r1 = r16.next()
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            java.lang.String r2 = r1.getName()
            boolean r3 = r2.equals(r11)
            if (r3 != 0) goto L_0x00b0
            boolean r3 = r2.equals(r15)
            if (r3 != 0) goto L_0x00b0
            boolean r3 = r2.equals(r14)
            if (r3 == 0) goto L_0x00ab
            goto L_0x00b0
        L_0x00ab:
            r19 = r11
            r11 = r7
            goto L_0x0153
        L_0x00b0:
            boolean r3 = r2.equals(r15)
            if (r3 == 0) goto L_0x00de
            java.lang.String r3 = "type"
            java.lang.String r4 = r1.attributeValue((java.lang.String) r3)
            if (r4 == 0) goto L_0x00de
            java.lang.String r3 = r1.attributeValue((java.lang.String) r3)
            java.lang.String r4 = "datetime"
            boolean r3 = r3.contains(r4)
            if (r3 == 0) goto L_0x00de
            com.app.office.ss.util.format.NumericFormatter r2 = com.app.office.ss.util.format.NumericFormatter.instance()
            java.util.Date r3 = new java.util.Date
            long r4 = java.lang.System.currentTimeMillis()
            r3.<init>(r4)
            java.lang.String r4 = "yyyy/m/d"
            java.lang.String r2 = r2.getFormatContents(r4, r3)
            goto L_0x00f9
        L_0x00de:
            java.lang.String r3 = "t"
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r3)
            boolean r2 = r2.equals(r14)
            if (r2 == 0) goto L_0x00f1
            r2 = 11
            java.lang.String r2 = java.lang.String.valueOf(r2)
            goto L_0x00f9
        L_0x00f1:
            if (r3 == 0) goto L_0x00f8
            java.lang.String r2 = r3.getText()
            goto L_0x00f9
        L_0x00f8:
            r2 = r7
        L_0x00f9:
            if (r2 == 0) goto L_0x00ab
            r3 = 160(0xa0, float:2.24E-43)
            r4 = 32
            java.lang.String r2 = r2.replace(r3, r4)
            int r17 = r2.length()
            if (r17 <= 0) goto L_0x00ab
            com.app.office.simpletext.model.LeafElement r6 = new com.app.office.simpletext.model.LeafElement
            r6.<init>(r2)
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r12)
            com.app.office.simpletext.model.IAttributeSet r4 = r6.getAttribute()
            boolean r18 = r13.equals(r2)
            r0 = r20
            r1 = r21
            r2 = r3
            r3 = r4
            r4 = r24
            r5 = r26
            r23 = r6
            r6 = r27
            r19 = r11
            r11 = r7
            r7 = r18
            r0.setRunAttribute(r1, r2, r3, r4, r5, r6, r7)
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r1 = r22.getAttribute()
            com.app.office.simpletext.model.IAttributeSet r2 = r23.getAttribute()
            int r0 = r0.getFontSize(r1, r2)
            r8.setMaxFontSize(r0)
            long r0 = (long) r10
            r2 = r23
            r2.setStartOffset(r0)
            int r10 = r10 + r17
            long r0 = (long) r10
            r2.setEndOffset(r0)
            r9.appendLeaf(r2)
            r0 = r2
        L_0x0153:
            r7 = r11
            r11 = r19
            goto L_0x0088
        L_0x0158:
            r11 = r7
            if (r0 == 0) goto L_0x0173
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r0.getText(r11)
            r1.append(r2)
            r1.append(r13)
            java.lang.String r1 = r1.toString()
            r0.setText(r1)
            int r10 = r10 + 1
        L_0x0173:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.attribute.RunAttr.processRun(com.app.office.pg.model.PGMaster, com.app.office.simpletext.model.ParagraphElement, com.app.office.fc.dom4j.Element, com.app.office.simpletext.model.IAttributeSet, int, int, int):int");
    }

    private void setFontSize(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 1)) {
            AttrManage.instance().setFontSize(iAttributeSet2, AttrManage.instance().getFontSize((IAttributeSet) null, iAttributeSet));
        }
    }

    private void setFontTypeface(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 2)) {
            AttrManage.instance().setFontName(iAttributeSet2, AttrManage.instance().getFontName((IAttributeSet) null, iAttributeSet));
        }
    }

    private void setFontColor(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 3)) {
            AttrManage.instance().setFontColor(iAttributeSet2, AttrManage.instance().getFontColor((IAttributeSet) null, iAttributeSet));
        }
    }

    private void setFontBold(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 4)) {
            AttrManage.instance().setFontBold(iAttributeSet2, AttrManage.instance().getFontBold((IAttributeSet) null, iAttributeSet));
        }
    }

    private void setFontItalic(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 5)) {
            AttrManage.instance().setFontItalic(iAttributeSet2, AttrManage.instance().getFontItalic((IAttributeSet) null, iAttributeSet));
        }
    }

    private void setFontStrike(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 6)) {
            AttrManage.instance().setFontStrike(iAttributeSet2, AttrManage.instance().getFontStrike((IAttributeSet) null, iAttributeSet));
        }
    }

    private void setFontDoubleStrike(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 7)) {
            AttrManage.instance().setFontDoubleStrike(iAttributeSet2, AttrManage.instance().getFontDoubleStrike((IAttributeSet) null, iAttributeSet));
        }
    }

    private void setFontUnderline(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 8)) {
            AttrManage.instance().setFontUnderline(iAttributeSet2, AttrManage.instance().getFontUnderline((IAttributeSet) null, iAttributeSet));
            if (AttrManage.instance().hasAttribute(iAttributeSet, 9)) {
                AttrManage.instance().setFontUnderlineColr(iAttributeSet2, AttrManage.instance().getFontUnderlineColor((IAttributeSet) null, iAttributeSet));
            } else if (AttrManage.instance().hasAttribute(iAttributeSet, 3)) {
                AttrManage.instance().setFontUnderlineColr(iAttributeSet2, AttrManage.instance().getFontColor((IAttributeSet) null, iAttributeSet));
            }
        }
    }

    private void setFontScript(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 10)) {
            AttrManage.instance().setFontScript(iAttributeSet2, AttrManage.instance().getFontScript((IAttributeSet) null, iAttributeSet));
        }
    }

    private void setHyperlinkID(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, 12)) {
            AttrManage.instance().setHyperlinkID(iAttributeSet2, AttrManage.instance().getHperlinkID(iAttributeSet));
        }
    }

    public void setRunAttribute(PGMaster pGMaster, Element element, IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2, int i, int i2, boolean z) {
        int parseInt;
        Element element2;
        int addFontName;
        if (element != null) {
            if (element.attribute("sz") != null) {
                String attributeValue = element.attributeValue("sz");
                if (attributeValue != null && attributeValue.length() > 0) {
                    AttrManage.instance().setFontSize(iAttributeSet, (int) (Float.parseFloat(attributeValue) / 100.0f));
                }
            } else {
                setFontSize(iAttributeSet2, iAttributeSet);
            }
            if (!z) {
                Element element3 = element.element("latin");
                if (element3 == null && element.element("ea") == null) {
                    setFontTypeface(iAttributeSet2, iAttributeSet);
                } else {
                    if (element3 == null) {
                        element3 = element.element("ea");
                    }
                    String attributeValue2 = element3.attributeValue("typeface");
                    if (attributeValue2 != null && (addFontName = FontTypefaceManage.instance().addFontName(attributeValue2)) >= 0) {
                        AttrManage.instance().setFontName(iAttributeSet, addFontName);
                    }
                }
                Element element4 = element.element("solidFill");
                Integer num = null;
                if (element4 != null) {
                    num = Integer.valueOf(ReaderKit.instance().getColor(pGMaster, element4));
                    AttrManage.instance().setFontColor(iAttributeSet, num.intValue());
                } else {
                    Element element5 = element.element("gradFill");
                    if (element5 != null) {
                        Element element6 = element5.element("gsLst");
                        if (element6 != null) {
                            num = Integer.valueOf(ReaderKit.instance().getColor(pGMaster, element6.element("gs")));
                            AttrManage.instance().setFontColor(iAttributeSet, num.intValue());
                        }
                    } else {
                        setFontColor(iAttributeSet2, iAttributeSet);
                    }
                }
                if (element.attribute(HtmlTags.B) != null) {
                    String attributeValue3 = element.attributeValue(HtmlTags.B);
                    if (attributeValue3 != null && attributeValue3.length() > 0 && Integer.parseInt(attributeValue3) > 0) {
                        AttrManage.instance().setFontBold(iAttributeSet, true);
                    }
                } else {
                    setFontBold(iAttributeSet2, iAttributeSet);
                }
                if (element.attribute("i") != null) {
                    String attributeValue4 = element.attributeValue("i");
                    if (attributeValue4 != null && attributeValue4.length() > 0) {
                        AttrManage.instance().setFontItalic(iAttributeSet, Integer.parseInt(attributeValue4) > 0);
                    }
                } else {
                    setFontItalic(iAttributeSet2, iAttributeSet);
                }
                if (element.attribute(HtmlTags.U) != null) {
                    String attributeValue5 = element.attributeValue(HtmlTags.U);
                    if (attributeValue5 != null && attributeValue5.length() > 0 && !attributeValue5.equalsIgnoreCase("none")) {
                        AttrManage.instance().setFontUnderline(iAttributeSet, 1);
                        Element element7 = element.element("uFill");
                        if (element7 != null && (element2 = element7.element("solidFill")) != null) {
                            AttrManage.instance().setFontUnderlineColr(iAttributeSet, ReaderKit.instance().getColor(pGMaster, element2));
                        } else if (num != null) {
                            AttrManage.instance().setFontUnderlineColr(iAttributeSet, num.intValue());
                        }
                    }
                } else {
                    setFontUnderline(iAttributeSet2, iAttributeSet);
                }
                if (element.attribute(HtmlTags.STRIKE) != null) {
                    String attributeValue6 = element.attributeValue(HtmlTags.STRIKE);
                    if (attributeValue6.equals("dblStrike")) {
                        AttrManage.instance().setFontDoubleStrike(iAttributeSet, true);
                    } else if (attributeValue6.equals("sngStrike")) {
                        AttrManage.instance().setFontStrike(iAttributeSet, true);
                    }
                } else {
                    setFontStrike(iAttributeSet2, iAttributeSet);
                    setFontDoubleStrike(iAttributeSet2, iAttributeSet);
                }
                if (element.attribute(HtmlTags.ALIGN_BASELINE) != null) {
                    String attributeValue7 = element.attributeValue(HtmlTags.ALIGN_BASELINE);
                    if (!(attributeValue7 == null || attributeValue7.length() <= 0 || (parseInt = Integer.parseInt(attributeValue7)) == 0)) {
                        AttrManage.instance().setFontScript(iAttributeSet, parseInt > 0 ? 1 : 2);
                    }
                } else {
                    setFontScript(iAttributeSet2, iAttributeSet);
                }
                Element element8 = element.element("hlinkClick");
                if (element8 != null) {
                    int i3 = -16776961;
                    if (pGMaster != null) {
                        i3 = pGMaster.getSchemeColor().get(SchemeClrConstant.SCHEME_HLINK).intValue();
                    }
                    AttrManage.instance().setFontColor(iAttributeSet, i3);
                    AttrManage.instance().setFontUnderline(iAttributeSet, 1);
                    AttrManage.instance().setFontUnderlineColr(iAttributeSet, i3);
                    String attributeValue8 = element8.attributeValue(OSOutcomeConstants.OUTCOME_ID);
                    if (attributeValue8 != null && attributeValue8.length() > 0) {
                        AttrManage.instance().setHyperlinkID(iAttributeSet, HyperlinkReader.instance().getLinkIndex(attributeValue8));
                    }
                } else {
                    setHyperlinkID(iAttributeSet2, iAttributeSet);
                }
            }
        } else if (iAttributeSet2 != null) {
            setFontSize(iAttributeSet2, iAttributeSet);
            if (!z) {
                setFontTypeface(iAttributeSet2, iAttributeSet);
                setFontColor(iAttributeSet2, iAttributeSet);
                setFontBold(iAttributeSet2, iAttributeSet);
                setFontItalic(iAttributeSet2, iAttributeSet);
                setFontUnderline(iAttributeSet2, iAttributeSet);
                setFontStrike(iAttributeSet2, iAttributeSet);
                setFontDoubleStrike(iAttributeSet2, iAttributeSet);
                setFontScript(iAttributeSet2, iAttributeSet);
                setHyperlinkID(iAttributeSet2, iAttributeSet);
            }
        }
        AttrManage.instance().setFontScale(iAttributeSet, i);
        if (!AttrManage.instance().hasAttribute(iAttributeSet, 1)) {
            Style style = StyleManage.instance().getStyle(i2);
            if ((style == null || style.getAttrbuteSet() == null || !AttrManage.instance().hasAttribute(style.getAttrbuteSet(), 1)) && !this.table && this.slide) {
                AttrManage.instance().setFontSize(iAttributeSet, 18);
            }
        }
    }

    private int getRunPropColor(Workbook workbook, Element element) {
        int i;
        if (element.attributeValue("indexed") != null) {
            i = workbook.getColor(Integer.parseInt(element.attributeValue("indexed")));
        } else if (element.attributeValue("theme") != null) {
            i = SchemeColorUtil.getThemeColor(workbook, Integer.parseInt(element.attributeValue("theme")));
        } else {
            i = element.attributeValue("rgb") != null ? (int) Long.parseLong(element.attributeValue("rgb"), 16) : -1;
        }
        if (element.attributeValue("tint") == null) {
            return i;
        }
        return ColorUtil.instance().getColorWithTint(i, Double.parseDouble(element.attributeValue("tint")));
    }

    public int getColor(Workbook workbook, Element element) {
        String attributeValue;
        if (element.element("srgbClr") != null) {
            return ((int) Long.parseLong(element.element("srgbClr").attributeValue("val"), 16)) | -16777216;
        }
        if (element.element("schemeClr") != null) {
            Element element2 = element.element("schemeClr");
            int intValue = SchemeColorUtil.getSchemeColor(workbook).get(element2.attributeValue("val")).intValue();
            if (element2.element("tint") != null) {
                intValue = ColorUtil.instance().getColorWithTint(intValue, ((double) Integer.parseInt(element2.element("tint").attributeValue("val"))) / 100000.0d);
            } else if (element2.element("lumOff") != null) {
                intValue = ColorUtil.instance().getColorWithTint(intValue, ((double) Integer.parseInt(element2.element("lumOff").attributeValue("val"))) / 100000.0d);
            } else if (element2.element("lumMod") != null) {
                intValue = ColorUtil.instance().getColorWithTint(intValue, (((double) Integer.parseInt(element2.element("lumMod").attributeValue("val"))) / 100000.0d) - 1.0d);
            } else if (element2.element("shade") != null) {
                intValue = ColorUtil.instance().getColorWithTint(intValue, ((double) (-Integer.parseInt(element2.element("shade").attributeValue("val")))) / 200000.0d);
            }
            if (element2.element("alpha") == null || (attributeValue = element2.element("alpha").attributeValue("val")) == null) {
                return intValue;
            }
            return (intValue & ViewCompat.MEASURED_SIZE_MASK) | (((int) ((((float) Integer.parseInt(attributeValue)) / 100000.0f) * 255.0f)) << 24);
        } else if (element.element("sysClr") != null) {
            return Integer.parseInt(element.element("sysClr").attributeValue("lastClr"), 16) | -16777216;
        } else {
            return -1;
        }
    }

    public void setRunAttribute(Sheet sheet, Element element, IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        Element element2;
        if (element != null) {
            if (element.attribute("sz") != null) {
                String attributeValue = element.attributeValue("sz");
                if (attributeValue != null && attributeValue.length() > 0) {
                    AttrManage.instance().setFontSize(iAttributeSet, (int) (Float.parseFloat(attributeValue) / 100.0f));
                }
            } else {
                setFontSize(iAttributeSet2, iAttributeSet);
            }
            Element element3 = element.element("solidFill");
            if (element3 != null) {
                AttrManage.instance().setFontColor(iAttributeSet, getColor(sheet.getWorkbook(), element3));
            } else {
                setFontColor(iAttributeSet2, iAttributeSet);
            }
            boolean z = false;
            if (element.attribute(HtmlTags.B) != null) {
                AttrManage.instance().setFontBold(iAttributeSet, Integer.parseInt(element.attributeValue(HtmlTags.B)) == 1);
            } else {
                setFontBold(iAttributeSet2, iAttributeSet);
            }
            if (element.attribute("i") != null) {
                AttrManage instance = AttrManage.instance();
                if (Integer.parseInt(element.attributeValue("i")) == 1) {
                    z = true;
                }
                instance.setFontItalic(iAttributeSet, z);
            } else {
                setFontItalic(iAttributeSet2, iAttributeSet);
            }
            if (element.attributeValue(HtmlTags.U) == null || element.attributeValue(HtmlTags.U).equalsIgnoreCase("none")) {
                setFontUnderline(iAttributeSet2, iAttributeSet);
            } else {
                AttrManage.instance().setFontUnderline(iAttributeSet, 1);
                Element element4 = element.element("uFill");
                if (!(element4 == null || (element2 = element4.element("solidFill")) == null)) {
                    AttrManage.instance().setFontUnderlineColr(iAttributeSet, getColor(sheet.getWorkbook(), element2));
                }
            }
            if (element.attribute(HtmlTags.STRIKE) != null) {
                String attributeValue2 = element.attributeValue(HtmlTags.STRIKE);
                if (attributeValue2.equals("dblStrike")) {
                    AttrManage.instance().setFontDoubleStrike(iAttributeSet, true);
                } else if (attributeValue2.equals("sngStrike")) {
                    AttrManage.instance().setFontStrike(iAttributeSet, true);
                }
            } else {
                setFontStrike(iAttributeSet2, iAttributeSet);
                setFontDoubleStrike(iAttributeSet2, iAttributeSet);
            }
            if (element.attribute(HtmlTags.ALIGN_BASELINE) != null) {
                String attributeValue3 = element.attributeValue(HtmlTags.ALIGN_BASELINE);
                if (attributeValue3 != null && !attributeValue3.equalsIgnoreCase("0")) {
                    AttrManage.instance().setFontScript(iAttributeSet, Integer.parseInt(attributeValue3) > 0 ? 1 : 2);
                }
            } else {
                setFontScript(iAttributeSet2, iAttributeSet);
            }
            Element element5 = element.element("hlinkClick");
            if (element5 == null || element5.attribute(OSOutcomeConstants.OUTCOME_ID) == null) {
                setHyperlinkID(iAttributeSet2, iAttributeSet);
                return;
            }
            String attributeValue4 = element5.attributeValue(OSOutcomeConstants.OUTCOME_ID);
            if (attributeValue4 != null && attributeValue4.length() > 0) {
                AttrManage.instance().setFontColor(iAttributeSet, -16776961);
                AttrManage.instance().setFontUnderline(iAttributeSet, 1);
                AttrManage.instance().setFontUnderlineColr(iAttributeSet, -16776961);
                AttrManage.instance().setHyperlinkID(iAttributeSet, HyperlinkReader.instance().getLinkIndex(attributeValue4));
            }
        } else if (iAttributeSet2 != null) {
            setFontSize(iAttributeSet2, iAttributeSet);
            setFontColor(iAttributeSet2, iAttributeSet);
            setFontBold(iAttributeSet2, iAttributeSet);
            setFontItalic(iAttributeSet2, iAttributeSet);
            setFontUnderline(iAttributeSet2, iAttributeSet);
            setFontStrike(iAttributeSet2, iAttributeSet);
            setFontDoubleStrike(iAttributeSet2, iAttributeSet);
            setFontScript(iAttributeSet2, iAttributeSet);
            setHyperlinkID(iAttributeSet2, iAttributeSet);
        }
    }

    public void setRunAttribute(Workbook workbook, int i, Element element, IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (element != null) {
            Element element2 = element.element("sz");
            if (element2 != null) {
                String attributeValue = element2.attributeValue("val");
                if (attributeValue != null && attributeValue.length() > 0) {
                    AttrManage.instance().setFontSize(iAttributeSet, (int) Float.parseFloat(attributeValue));
                }
            } else {
                setFontSize(iAttributeSet2, iAttributeSet);
            }
            Element element3 = element.element("color");
            if (element3 != null) {
                AttrManage.instance().setFontColor(iAttributeSet, getRunPropColor(workbook, element3));
            } else {
                setFontColor(iAttributeSet2, iAttributeSet);
            }
            if (element.element(HtmlTags.B) != null) {
                AttrManage.instance().setFontBold(iAttributeSet, true);
            } else {
                setFontBold(iAttributeSet2, iAttributeSet);
            }
            if (element.element("i") != null) {
                AttrManage.instance().setFontItalic(iAttributeSet, true);
            } else {
                setFontItalic(iAttributeSet2, iAttributeSet);
            }
            if (element.element(HtmlTags.U) != null) {
                AttrManage.instance().setFontUnderline(iAttributeSet, 1);
            } else {
                setFontUnderline(iAttributeSet2, iAttributeSet);
            }
            if (element.element(HtmlTags.STRIKE) != null) {
                AttrManage.instance().setFontStrike(iAttributeSet, true);
                setFontDoubleStrike(iAttributeSet2, iAttributeSet);
            } else {
                setFontStrike(iAttributeSet2, iAttributeSet);
                setFontDoubleStrike(iAttributeSet2, iAttributeSet);
            }
            Element element4 = element.element("vertAlign");
            if (element4 != null) {
                String attributeValue2 = element4.attributeValue("val");
                if (attributeValue2.equalsIgnoreCase("superscript")) {
                    AttrManage.instance().setFontScript(iAttributeSet, 1);
                } else if (attributeValue2.equalsIgnoreCase("subscript")) {
                    AttrManage.instance().setFontScript(iAttributeSet, 2);
                } else {
                    AttrManage.instance().setFontScript(iAttributeSet, 0);
                }
            } else {
                setFontScript(iAttributeSet2, iAttributeSet);
            }
            setHyperlinkID(iAttributeSet2, iAttributeSet);
        } else if (iAttributeSet2 != null) {
            Font font = workbook.getFont(i);
            if (font != null) {
                AttrManage.instance().setFontSize(iAttributeSet, (int) font.getFontSize());
                AttrManage.instance().setFontColor(iAttributeSet, workbook.getColor(font.getColorIndex()));
                AttrManage.instance().setFontBold(iAttributeSet, font.isBold());
                AttrManage.instance().setFontItalic(iAttributeSet, font.isItalic());
                AttrManage.instance().setFontUnderline(iAttributeSet, font.getUnderline());
                AttrManage.instance().setFontStrike(iAttributeSet, font.isStrikeline());
                setFontDoubleStrike(iAttributeSet2, iAttributeSet);
                AttrManage.instance().setFontScript(iAttributeSet, font.getSuperSubScript());
                setHyperlinkID(iAttributeSet2, iAttributeSet);
                return;
            }
            setFontSize(iAttributeSet2, iAttributeSet);
            setFontColor(iAttributeSet2, iAttributeSet);
            setFontBold(iAttributeSet2, iAttributeSet);
            setFontItalic(iAttributeSet2, iAttributeSet);
            setFontUnderline(iAttributeSet2, iAttributeSet);
            setFontStrike(iAttributeSet2, iAttributeSet);
            setFontDoubleStrike(iAttributeSet2, iAttributeSet);
            setFontScript(iAttributeSet2, iAttributeSet);
            setHyperlinkID(iAttributeSet2, iAttributeSet);
        }
    }

    public void setRunAttribute(Sheet sheet, Cell cell, IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (cell != null) {
            CellStyle cellStyle = cell.getCellStyle();
            Workbook workbook = sheet.getWorkbook();
            Font font = workbook.getFont(cellStyle.getFontIndex());
            AttrManage.instance().setFontSize(iAttributeSet, (int) (font.getFontSize() + 0.5d));
            AttrManage.instance().setFontColor(iAttributeSet, workbook.getColor(font.getColorIndex()));
            AttrManage.instance().setFontBold(iAttributeSet, font.isBold());
            AttrManage.instance().setFontItalic(iAttributeSet, font.isItalic());
            AttrManage.instance().setFontUnderline(iAttributeSet, font.getUnderline());
            AttrManage.instance().setFontStrike(iAttributeSet, font.isStrikeline());
        } else if (iAttributeSet2 != null) {
            setFontSize(iAttributeSet2, iAttributeSet);
            setFontColor(iAttributeSet2, iAttributeSet);
            setFontBold(iAttributeSet2, iAttributeSet);
            setFontItalic(iAttributeSet2, iAttributeSet);
            setFontUnderline(iAttributeSet2, iAttributeSet);
            setFontStrike(iAttributeSet2, iAttributeSet);
            setFontDoubleStrike(iAttributeSet2, iAttributeSet);
            setFontScript(iAttributeSet2, iAttributeSet);
            setHyperlinkID(iAttributeSet2, iAttributeSet);
        }
    }

    public void setRunAttribute(Sheet sheet, Font font, IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (font != null) {
            Workbook workbook = sheet.getWorkbook();
            AttrManage.instance().setFontSize(iAttributeSet, (int) (font.getFontSize() + 0.5d));
            AttrManage.instance().setFontColor(iAttributeSet, workbook.getColor(font.getColorIndex()));
            AttrManage.instance().setFontBold(iAttributeSet, font.isBold());
            AttrManage.instance().setFontItalic(iAttributeSet, font.isItalic());
            AttrManage.instance().setFontUnderline(iAttributeSet, font.getUnderline());
            AttrManage.instance().setFontStrike(iAttributeSet, font.isStrikeline());
        } else if (iAttributeSet2 != null) {
            setFontSize(iAttributeSet2, iAttributeSet);
            setFontColor(iAttributeSet2, iAttributeSet);
            setFontBold(iAttributeSet2, iAttributeSet);
            setFontItalic(iAttributeSet2, iAttributeSet);
            setFontUnderline(iAttributeSet2, iAttributeSet);
            setFontStrike(iAttributeSet2, iAttributeSet);
            setFontDoubleStrike(iAttributeSet2, iAttributeSet);
            setFontScript(iAttributeSet2, iAttributeSet);
            setHyperlinkID(iAttributeSet2, iAttributeSet);
        }
    }

    public int getMaxFontSize() {
        return this.maxFontSize;
    }

    public void setMaxFontSize(int i) {
        if (i > this.maxFontSize) {
            this.maxFontSize = i;
        }
    }

    public void resetMaxFontSize() {
        this.maxFontSize = 0;
    }

    public void setTable(boolean z) {
        this.table = z;
    }

    public void setSlide(boolean z) {
        this.slide = z;
    }

    public boolean isTable() {
        return this.table;
    }

    public boolean isSlide() {
        return this.slide;
    }

    public void dispose() {
        this.maxFontSize = 0;
    }
}
