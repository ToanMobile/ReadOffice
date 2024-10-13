package com.app.office.fc.ppt.reader;

import android.graphics.Color;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.app.office.common.shape.IShape;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.ss.util.CellUtil;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.model.PGMaster;
import com.app.office.pg.model.PGPlaceholderUtil;
import com.app.office.ss.util.ColorUtil;
import kotlinx.coroutines.DebugKt;

public class ReaderKit {
    private static ReaderKit kit = new ReaderKit();

    public static ReaderKit instance() {
        return kit;
    }

    public String getPlaceholderName(Element element) {
        Element element2;
        Element element3;
        if (element != null) {
            String name = element.getName();
            if (name.equals("sp")) {
                element2 = element.element("nvSpPr");
            } else if (name.equals(PGPlaceholderUtil.PICTURE)) {
                element2 = element.element("nvPicPr");
            } else if (name.equals("graphicFrame")) {
                element2 = element.element("nvGraphicFramePr");
            } else {
                element2 = name.equals("grpSp") ? element.element("nvGrpSpPr") : null;
            }
            if (!(element2 == null || (element3 = element2.element("cNvPr")) == null || element3.attribute("name") == null)) {
                return element3.attributeValue("name");
            }
        }
        return null;
    }

    public String getPlaceholderType(Element element) {
        Element element2;
        Element element3;
        Element element4;
        if (element != null) {
            String name = element.getName();
            if (name.equals("sp")) {
                element2 = element.element("nvSpPr");
            } else if (name.equals(PGPlaceholderUtil.PICTURE)) {
                element2 = element.element("nvPicPr");
            } else if (name.equals("graphicFrame")) {
                element2 = element.element("nvGraphicFramePr");
            } else {
                element2 = name.equals("grpSp") ? element.element("nvGrpSpPr") : null;
            }
            if (!(element2 == null || (element3 = element2.element("nvPr")) == null || (element4 = element3.element("ph")) == null || element4.attribute(DublinCoreProperties.TYPE) == null)) {
                return element4.attributeValue(DublinCoreProperties.TYPE);
            }
        }
        return null;
    }

    public int getPlaceholderIdx(Element element) {
        Element element2;
        Element element3;
        if (element == null) {
            return -1;
        }
        Element element4 = null;
        String name = element.getName();
        if (name.equals("sp")) {
            element4 = element.element("nvSpPr");
        } else if (name.equals(PGPlaceholderUtil.PICTURE)) {
            element4 = element.element("nvPicPr");
        } else if (name.equals("graphicFrame")) {
            element4 = element.element("nvGraphicFramePr");
        } else if (name.equals("grpSp")) {
            element4 = element.element("nvGrpSpPr");
        }
        if (element4 == null || (element2 = element4.element("nvPr")) == null || (element3 = element2.element("ph")) == null || element3.attributeValue("idx") == null) {
            return -1;
        }
        return (int) Double.parseDouble(element3.attributeValue("idx"));
    }

    public Rectangle getShapeAnchor(Element element, float f, float f2) {
        String attributeValue;
        String attributeValue2;
        String attributeValue3;
        String attributeValue4;
        if (element == null) {
            return null;
        }
        Rectangle rectangle = new Rectangle();
        Element element2 = element.element(DebugKt.DEBUG_PROPERTY_VALUE_OFF);
        if (element2 != null) {
            if (!(element2.attribute("x") == null || (attributeValue4 = element2.attributeValue("x")) == null || attributeValue4.length() <= 0)) {
                if (isDecimal(attributeValue4)) {
                    rectangle.x = (int) (((((float) Integer.parseInt(attributeValue4)) * f) * 96.0f) / 914400.0f);
                } else {
                    rectangle.x = (int) (((((float) Integer.parseInt(attributeValue4, 16)) * f) * 96.0f) / 914400.0f);
                }
            }
            if (!(element2.attribute("y") == null || (attributeValue3 = element2.attributeValue("y")) == null || attributeValue3.length() <= 0)) {
                if (isDecimal(attributeValue3)) {
                    rectangle.y = (int) (((((float) Integer.parseInt(attributeValue3)) * f2) * 96.0f) / 914400.0f);
                } else {
                    rectangle.y = (int) (((((float) Integer.parseInt(attributeValue3, 16)) * f2) * 96.0f) / 914400.0f);
                }
            }
        }
        Element element3 = element.element("ext");
        if (element3 != null) {
            if (!(element3.attribute("cx") == null || (attributeValue2 = element3.attributeValue("cx")) == null || attributeValue2.length() <= 0)) {
                if (isDecimal(attributeValue2)) {
                    rectangle.width = (int) (((((float) Integer.parseInt(attributeValue2)) * f) * 96.0f) / 914400.0f);
                } else {
                    rectangle.width = (int) (((((float) Integer.parseInt(attributeValue2, 16)) * f) * 96.0f) / 914400.0f);
                }
            }
            if (!(element3.attributeValue("cy") == null || (attributeValue = element3.attributeValue("cy")) == null || attributeValue.length() <= 0)) {
                if (isDecimal(attributeValue)) {
                    rectangle.height = (int) (((((float) Integer.parseInt(attributeValue)) * f2) * 96.0f) / 914400.0f);
                } else {
                    rectangle.height = (int) (((((float) Integer.parseInt(attributeValue, 16)) * f2) * 96.0f) / 914400.0f);
                }
            }
        }
        return rectangle;
    }

    public Rectangle getChildShapeAnchor(Element element, float f, float f2) {
        String attributeValue;
        String attributeValue2;
        String attributeValue3;
        String attributeValue4;
        if (element == null) {
            return null;
        }
        Rectangle rectangle = new Rectangle();
        Element element2 = element.element("chOff");
        if (element2 != null) {
            if (!(element2.attribute("x") == null || (attributeValue4 = element2.attributeValue("x")) == null || attributeValue4.length() <= 0)) {
                if (isDecimal(attributeValue4)) {
                    rectangle.x = (int) (((((float) Integer.parseInt(attributeValue4)) * f) * 96.0f) / 914400.0f);
                } else {
                    rectangle.x = (int) (((((float) Integer.parseInt(attributeValue4, 16)) * f) * 96.0f) / 914400.0f);
                }
            }
            if (!(element2.attribute("y") == null || (attributeValue3 = element2.attributeValue("y")) == null || attributeValue3.length() <= 0)) {
                if (isDecimal(attributeValue3)) {
                    rectangle.y = (int) (((((float) Integer.parseInt(attributeValue3)) * f2) * 96.0f) / 914400.0f);
                } else {
                    rectangle.y = (int) (((((float) Integer.parseInt(attributeValue3, 16)) * f2) * 96.0f) / 914400.0f);
                }
            }
        }
        Element element3 = element.element("chExt");
        if (element3 != null) {
            if (!(element3.attribute("cx") == null || (attributeValue2 = element3.attributeValue("cx")) == null || attributeValue2.length() <= 0)) {
                if (isDecimal(attributeValue2)) {
                    rectangle.width = (int) (((((float) Integer.parseInt(attributeValue2)) * f) * 96.0f) / 914400.0f);
                } else {
                    rectangle.width = (int) (((((float) Integer.parseInt(attributeValue2, 16)) * f) * 96.0f) / 914400.0f);
                }
            }
            if (!(element3.attributeValue("cy") == null || (attributeValue = element3.attributeValue("cy")) == null || attributeValue.length() <= 0)) {
                if (isDecimal(attributeValue)) {
                    rectangle.height = (int) (((((float) Integer.parseInt(attributeValue)) * f2) * 96.0f) / 914400.0f);
                } else {
                    rectangle.height = (int) (((((float) Integer.parseInt(attributeValue, 16)) * f2) * 96.0f) / 914400.0f);
                }
            }
        }
        return rectangle;
    }

    public String getNotes(Element element) {
        Element element2;
        Element element3 = element.element("cSld");
        if (element3 == null || (element2 = element3.element("spTree")) == null) {
            return null;
        }
        for (Element element4 : element2.elements("sp")) {
            if ("body".equals(getPlaceholderType(element4))) {
                String str = "";
                Element element5 = element4.element("txBody");
                if (element5 != null) {
                    for (Element elements : element5.elements("p")) {
                        for (Element element6 : elements.elements("r")) {
                            Element element7 = element6.element("t");
                            if (element7 != null) {
                                str = str + element7.getText();
                            }
                        }
                        str = str + 10;
                    }
                }
                String trim = str.trim();
                if (trim.length() > 0) {
                    return trim;
                }
            }
        }
        return null;
    }

    public int getColor(PGMaster pGMaster, Element element) {
        return getColor(pGMaster, element, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00ae, code lost:
        r10 = r10.element("alpha").attributeValue("val");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int processColorAttribute(com.app.office.fc.dom4j.Element r10, int r11, boolean r12) {
        /*
            r9 = this;
            java.lang.String r0 = "tint"
            com.app.office.fc.dom4j.Element r1 = r10.element((java.lang.String) r0)
            r2 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r4 = 4681608360884174848(0x40f86a0000000000, double:100000.0)
            java.lang.String r6 = "val"
            if (r1 == 0) goto L_0x0043
            if (r12 == 0) goto L_0x002c
            com.app.office.ss.util.ColorUtil r12 = com.app.office.ss.util.ColorUtil.instance()
            com.app.office.fc.dom4j.Element r0 = r10.element((java.lang.String) r0)
            java.lang.String r0 = r0.attributeValue((java.lang.String) r6)
            int r0 = java.lang.Integer.parseInt(r0)
            double r0 = (double) r0
            double r0 = r0 / r4
            double r2 = r2 - r0
            int r11 = r12.getColorWithTint(r11, r2)
            goto L_0x00a6
        L_0x002c:
            com.app.office.ss.util.ColorUtil r12 = com.app.office.ss.util.ColorUtil.instance()
            com.app.office.fc.dom4j.Element r0 = r10.element((java.lang.String) r0)
            java.lang.String r0 = r0.attributeValue((java.lang.String) r6)
            int r0 = java.lang.Integer.parseInt(r0)
            double r0 = (double) r0
            double r0 = r0 / r4
            int r11 = r12.getColorWithTint(r11, r0)
            goto L_0x00a6
        L_0x0043:
            java.lang.String r12 = "lumOff"
            com.app.office.fc.dom4j.Element r0 = r10.element((java.lang.String) r12)
            if (r0 == 0) goto L_0x0062
            com.app.office.ss.util.ColorUtil r0 = com.app.office.ss.util.ColorUtil.instance()
            com.app.office.fc.dom4j.Element r12 = r10.element((java.lang.String) r12)
            java.lang.String r12 = r12.attributeValue((java.lang.String) r6)
            int r12 = java.lang.Integer.parseInt(r12)
            double r1 = (double) r12
            double r1 = r1 / r4
            int r11 = r0.getColorWithTint(r11, r1)
            goto L_0x00a6
        L_0x0062:
            java.lang.String r12 = "lumMod"
            com.app.office.fc.dom4j.Element r0 = r10.element((java.lang.String) r12)
            if (r0 == 0) goto L_0x0082
            com.app.office.ss.util.ColorUtil r0 = com.app.office.ss.util.ColorUtil.instance()
            com.app.office.fc.dom4j.Element r12 = r10.element((java.lang.String) r12)
            java.lang.String r12 = r12.attributeValue((java.lang.String) r6)
            int r12 = java.lang.Integer.parseInt(r12)
            double r7 = (double) r12
            double r7 = r7 / r4
            double r7 = r7 - r2
            int r11 = r0.getColorWithTint(r11, r7)
            goto L_0x00a6
        L_0x0082:
            java.lang.String r12 = "shade"
            com.app.office.fc.dom4j.Element r0 = r10.element((java.lang.String) r12)
            if (r0 == 0) goto L_0x00a6
            com.app.office.ss.util.ColorUtil r0 = com.app.office.ss.util.ColorUtil.instance()
            com.app.office.fc.dom4j.Element r12 = r10.element((java.lang.String) r12)
            java.lang.String r12 = r12.attributeValue((java.lang.String) r6)
            int r12 = java.lang.Integer.parseInt(r12)
            int r12 = -r12
            double r1 = (double) r12
            r3 = 4686111960511545344(0x41086a0000000000, double:200000.0)
            double r1 = r1 / r3
            int r11 = r0.getColorWithTint(r11, r1)
        L_0x00a6:
            java.lang.String r12 = "alpha"
            com.app.office.fc.dom4j.Element r0 = r10.element((java.lang.String) r12)
            if (r0 == 0) goto L_0x00cd
            com.app.office.fc.dom4j.Element r10 = r10.element((java.lang.String) r12)
            java.lang.String r10 = r10.attributeValue((java.lang.String) r6)
            if (r10 == 0) goto L_0x00cd
            int r10 = java.lang.Integer.parseInt(r10)
            float r10 = (float) r10
            r12 = 1203982336(0x47c35000, float:100000.0)
            float r10 = r10 / r12
            r12 = 1132396544(0x437f0000, float:255.0)
            float r10 = r10 * r12
            int r10 = (int) r10
            r12 = 16777215(0xffffff, float:2.3509886E-38)
            r11 = r11 & r12
            int r10 = r10 << 24
            r11 = r11 | r10
        L_0x00cd:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.reader.ReaderKit.processColorAttribute(com.app.office.fc.dom4j.Element, int, boolean):int");
    }

    public int getColor(PGMaster pGMaster, Element element, boolean z) {
        int i = -1;
        if (element != null) {
            Element element2 = element.element("srgbClr");
            if (element2 == null || element2.attribute("val") == null) {
                Element element3 = element.element("scrgbClr");
                if (element3 != null) {
                    return processColorAttribute(element3, ColorUtil.rgb((Integer.parseInt(element3.attributeValue("r")) * 255) / 100, (Integer.parseInt(element3.attributeValue("g")) * 255) / 100, (Integer.parseInt(element3.attributeValue(HtmlTags.B)) * 255) / 100), z);
                }
                Element element4 = element.element("schemeClr");
                if (element4 == null || element4.attribute("val") == null) {
                    Element element5 = element.element("sysClr");
                    if (element5 != null) {
                        String attributeValue = element5.attributeValue("lastClr");
                        if (attributeValue != null && attributeValue.length() > 0) {
                            return Color.parseColor("#" + attributeValue);
                        }
                    } else {
                        Element element6 = element.element("prstClr");
                        if (element6 != null) {
                            String attributeValue2 = element6.attributeValue("val");
                            if (attributeValue2.contains("gray")) {
                                return -7829368;
                            }
                            if (attributeValue2.contains("white")) {
                                return -1;
                            }
                            if (attributeValue2.contains("red")) {
                                return SupportMenu.CATEGORY_MASK;
                            }
                            if (attributeValue2.contains("green")) {
                                return -16711936;
                            }
                            if (attributeValue2.contains("blue")) {
                                return -16776961;
                            }
                            if (attributeValue2.contains("yellow")) {
                                return InputDeviceCompat.SOURCE_ANY;
                            }
                            return attributeValue2.contains("cyan") ? -16711681 : -16777216;
                        }
                    }
                } else {
                    String attributeValue3 = element4.attributeValue("val");
                    if (attributeValue3 != null && attributeValue3.length() > 0) {
                        if (pGMaster != null) {
                            i = pGMaster.getColor(attributeValue3);
                        }
                        return processColorAttribute(element4, i, z);
                    }
                }
            } else {
                String attributeValue4 = element2.attributeValue("val");
                if (attributeValue4 != null && attributeValue4.length() > 0) {
                    return processColorAttribute(element2, Color.parseColor("#" + attributeValue4), z);
                }
            }
        }
        return -1;
    }

    public boolean isDecimal(String str) {
        for (int i = 0; i < 12; i++) {
            if (str.indexOf("abcdefABCDEF".charAt(i)) > -1) {
                return false;
            }
        }
        return true;
    }

    public boolean isUserDrawn(Element element) {
        Element element2;
        Element element3;
        String attributeValue;
        String name = element.getName();
        if (name.equals("sp")) {
            element2 = element.element("nvSpPr");
        } else if (name.equals(PGPlaceholderUtil.PICTURE)) {
            element2 = element.element("nvPicPr");
        } else if (name.equals("graphicFrame")) {
            element2 = element.element("nvGraphicFramePr");
        } else {
            element2 = name.equals("grpSp") ? element.element("nvGrpSpPr") : null;
        }
        if (element2 == null || (element3 = element2.element("nvPr")) == null) {
            return false;
        }
        if (element3.element("ph") == null) {
            return true;
        }
        if (element3.attribute("userDrawn") == null || (attributeValue = element3.attributeValue("userDrawn")) == null || attributeValue.length() <= 0 || Integer.parseInt(attributeValue) <= 0) {
            return false;
        }
        return true;
    }

    public float[] getAnchorFitZoom(Element element) {
        float f;
        float f2;
        float f3;
        float f4;
        String attributeValue;
        int i;
        String attributeValue2;
        int i2;
        String attributeValue3;
        int i3;
        String attributeValue4;
        int i4;
        float[] fArr = {1.0f, 1.0f};
        if (element != null) {
            Element element2 = element.element("ext");
            if (element2 != null) {
                if (element2.attribute("cx") == null || (attributeValue4 = element2.attributeValue("cx")) == null || attributeValue4.length() <= 0) {
                    f = 0.0f;
                } else {
                    if (isDecimal(attributeValue4)) {
                        i4 = Integer.parseInt(attributeValue4);
                    } else {
                        i4 = Integer.parseInt(attributeValue4, 16);
                    }
                    f = (float) i4;
                }
                if (element2.attributeValue("cy") == null || (attributeValue3 = element2.attributeValue("cy")) == null || attributeValue3.length() <= 0) {
                    f2 = 0.0f;
                } else {
                    if (isDecimal(attributeValue3)) {
                        i3 = Integer.parseInt(attributeValue3);
                    } else {
                        i3 = Integer.parseInt(attributeValue3, 16);
                    }
                    f2 = (float) i3;
                }
            } else {
                f2 = 0.0f;
                f = 0.0f;
            }
            Element element3 = element.element("chExt");
            if (element3 != null) {
                if (element3.attribute("cx") == null || (attributeValue2 = element3.attributeValue("cx")) == null || attributeValue2.length() <= 0) {
                    f4 = 0.0f;
                } else {
                    if (isDecimal(attributeValue2)) {
                        i2 = Integer.parseInt(attributeValue2);
                    } else {
                        i2 = Integer.parseInt(attributeValue2, 16);
                    }
                    f4 = (float) i2;
                }
                if (element3.attributeValue("cy") == null || (attributeValue = element3.attributeValue("cy")) == null || attributeValue.length() <= 0) {
                    f3 = 0.0f;
                } else {
                    if (isDecimal(attributeValue)) {
                        i = Integer.parseInt(attributeValue);
                    } else {
                        i = Integer.parseInt(attributeValue, 16);
                    }
                    f3 = (float) i;
                }
            } else {
                f3 = 0.0f;
                f4 = 0.0f;
            }
            if (!(f4 == 0.0f || f3 == 0.0f)) {
                fArr[0] = f / f4;
                fArr[1] = f2 / f3;
            }
        }
        return fArr;
    }

    public void processRotation(Element element, IShape iShape) {
        if (element != null) {
            processRotation(iShape, element.element("xfrm"));
        }
    }

    public void processRotation(IShape iShape, Element element) {
        String attributeValue;
        String attributeValue2;
        String attributeValue3;
        if (element != null) {
            if (element.attribute("flipH") != null && (attributeValue3 = element.attributeValue("flipH")) != null && attributeValue3.length() > 0 && Integer.parseInt(attributeValue3) == 1) {
                iShape.setFlipHorizontal(true);
            }
            if (element.attribute("flipV") != null && (attributeValue2 = element.attributeValue("flipV")) != null && attributeValue2.length() > 0 && Integer.parseInt(attributeValue2) == 1) {
                iShape.setFlipVertical(true);
            }
            if (element.attribute("rot") != null && (attributeValue = element.attributeValue("rot")) != null && attributeValue.length() > 0) {
                iShape.setRotation(Float.parseFloat(attributeValue) / 60000.0f);
            }
        }
    }

    public boolean isHidden(Element element) {
        Element element2;
        Element element3;
        String name = element.getName();
        if (name.equals("sp")) {
            element2 = element.element("nvSpPr");
        } else if (name.equals(PGPlaceholderUtil.PICTURE)) {
            element2 = element.element("nvPicPr");
        } else if (name.equals("graphicFrame")) {
            element2 = element.element("nvGraphicFramePr");
        } else {
            element2 = name.equals("grpSp") ? element.element("nvGrpSpPr") : null;
        }
        return (element2 == null || (element3 = element2.element("cNvPr")) == null || element3.attribute(CellUtil.HIDDEN) == null || Integer.parseInt(element3.attributeValue(CellUtil.HIDDEN)) <= 0) ? false : true;
    }
}
