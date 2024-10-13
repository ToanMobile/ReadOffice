package com.app.office.common.autoshape;

import com.itextpdf.text.html.HtmlTags;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.bg.TileShader;
import com.app.office.common.pictureefftect.PictureStretchInfo;
import com.app.office.common.shape.AbstractShape;
import com.app.office.common.shape.PictureShape;
import com.app.office.fc.LineKit;
import com.app.office.fc.ShaderKit;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.java.awt.Rectangle;
import com.app.office.system.IControl;
import java.util.Map;

public class AutoShapeDataKit {
    /* JADX WARNING: Removed duplicated region for block: B:55:0x017b  */
    /* JADX WARNING: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getColor(java.util.Map<java.lang.String, java.lang.Integer> r8, com.app.office.fc.dom4j.Element r9) {
        /*
            java.lang.String r0 = "srgbClr"
            com.app.office.fc.dom4j.Element r1 = r9.element((java.lang.String) r0)
            r2 = 16
            r3 = -1
            r4 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            java.lang.String r5 = "val"
            if (r1 == 0) goto L_0x0020
            com.app.office.fc.dom4j.Element r8 = r9.element((java.lang.String) r0)
            java.lang.String r8 = r8.attributeValue((java.lang.String) r5)
            long r8 = java.lang.Long.parseLong(r8, r2)
            int r9 = (int) r8
            r3 = r9 | r4
            goto L_0x0191
        L_0x0020:
            java.lang.String r0 = "scrgbClr"
            com.app.office.fc.dom4j.Element r0 = r9.element((java.lang.String) r0)
            if (r0 == 0) goto L_0x0057
            java.lang.String r8 = "r"
            java.lang.String r8 = r0.attributeValue((java.lang.String) r8)
            int r8 = java.lang.Integer.parseInt(r8)
            int r8 = r8 * 255
            int r8 = r8 / 100
            java.lang.String r9 = "g"
            java.lang.String r9 = r0.attributeValue((java.lang.String) r9)
            int r9 = java.lang.Integer.parseInt(r9)
            int r9 = r9 * 255
            int r9 = r9 / 100
            java.lang.String r1 = "b"
            java.lang.String r0 = r0.attributeValue((java.lang.String) r1)
            int r0 = java.lang.Integer.parseInt(r0)
            int r0 = r0 * 255
            int r0 = r0 / 100
            int r8 = com.app.office.ss.util.ColorUtil.rgb((int) r8, (int) r9, (int) r0)
            return r8
        L_0x0057:
            java.lang.String r0 = "schemeClr"
            com.app.office.fc.dom4j.Element r1 = r9.element((java.lang.String) r0)
            java.lang.String r6 = "prstClr"
            if (r1 != 0) goto L_0x0082
            com.app.office.fc.dom4j.Element r1 = r9.element((java.lang.String) r6)
            if (r1 == 0) goto L_0x0068
            goto L_0x0082
        L_0x0068:
            java.lang.String r8 = "sysClr"
            com.app.office.fc.dom4j.Element r0 = r9.element((java.lang.String) r8)
            if (r0 == 0) goto L_0x0191
            com.app.office.fc.dom4j.Element r8 = r9.element((java.lang.String) r8)
            java.lang.String r9 = "lastClr"
            java.lang.String r8 = r8.attributeValue((java.lang.String) r9)
            int r8 = java.lang.Integer.parseInt(r8, r2)
            r3 = r8 | r4
            goto L_0x0191
        L_0x0082:
            if (r8 == 0) goto L_0x0191
            int r1 = r8.size()
            if (r1 <= 0) goto L_0x0191
            com.app.office.fc.dom4j.Element r0 = r9.element((java.lang.String) r0)
            if (r0 != 0) goto L_0x0094
            com.app.office.fc.dom4j.Element r0 = r9.element((java.lang.String) r6)
        L_0x0094:
            java.lang.String r9 = r0.attributeValue((java.lang.String) r5)
            java.lang.String r1 = "black"
            boolean r1 = r1.equals(r9)
            if (r1 == 0) goto L_0x00a1
            goto L_0x00d1
        L_0x00a1:
            java.lang.String r1 = "red"
            boolean r1 = r1.equals(r9)
            if (r1 == 0) goto L_0x00ac
            r4 = -65536(0xffffffffffff0000, float:NaN)
            goto L_0x00d1
        L_0x00ac:
            java.lang.String r1 = "gray"
            boolean r1 = r1.equals(r9)
            if (r1 == 0) goto L_0x00b8
            r4 = -7829368(0xffffffffff888888, float:NaN)
            goto L_0x00d1
        L_0x00b8:
            java.lang.String r1 = "blue"
            boolean r1 = r1.equals(r9)
            if (r1 == 0) goto L_0x00c4
            r4 = -16776961(0xffffffffff0000ff, float:-1.7014636E38)
            goto L_0x00d1
        L_0x00c4:
            java.lang.String r1 = "green"
            boolean r1 = r1.equals(r9)
            if (r1 == 0) goto L_0x00d0
            r4 = -16711936(0xffffffffff00ff00, float:-1.7146522E38)
            goto L_0x00d1
        L_0x00d0:
            r4 = -1
        L_0x00d1:
            if (r4 != r3) goto L_0x00dd
            java.lang.Object r8 = r8.get(r9)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r4 = r8.intValue()
        L_0x00dd:
            java.lang.String r8 = "tint"
            com.app.office.fc.dom4j.Element r9 = r0.element((java.lang.String) r8)
            r1 = 4681608360884174848(0x40f86a0000000000, double:100000.0)
            if (r9 == 0) goto L_0x0102
            com.app.office.ss.util.ColorUtil r9 = com.app.office.ss.util.ColorUtil.instance()
            com.app.office.fc.dom4j.Element r8 = r0.element((java.lang.String) r8)
            java.lang.String r8 = r8.attributeValue((java.lang.String) r5)
            int r8 = java.lang.Integer.parseInt(r8)
            double r6 = (double) r8
            double r6 = r6 / r1
            int r8 = r9.getColorWithTint(r4, r6)
        L_0x0100:
            r3 = r8
            goto L_0x0169
        L_0x0102:
            java.lang.String r8 = "lumOff"
            com.app.office.fc.dom4j.Element r9 = r0.element((java.lang.String) r8)
            if (r9 == 0) goto L_0x0121
            com.app.office.ss.util.ColorUtil r9 = com.app.office.ss.util.ColorUtil.instance()
            com.app.office.fc.dom4j.Element r8 = r0.element((java.lang.String) r8)
            java.lang.String r8 = r8.attributeValue((java.lang.String) r5)
            int r8 = java.lang.Integer.parseInt(r8)
            double r6 = (double) r8
            double r6 = r6 / r1
            int r8 = r9.getColorWithTint(r4, r6)
            goto L_0x0100
        L_0x0121:
            java.lang.String r8 = "lumMod"
            com.app.office.fc.dom4j.Element r9 = r0.element((java.lang.String) r8)
            if (r9 == 0) goto L_0x0143
            com.app.office.ss.util.ColorUtil r9 = com.app.office.ss.util.ColorUtil.instance()
            com.app.office.fc.dom4j.Element r8 = r0.element((java.lang.String) r8)
            java.lang.String r8 = r8.attributeValue((java.lang.String) r5)
            int r8 = java.lang.Integer.parseInt(r8)
            double r6 = (double) r8
            double r6 = r6 / r1
            r1 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r6 = r6 - r1
            int r8 = r9.getColorWithTint(r4, r6)
            goto L_0x0100
        L_0x0143:
            java.lang.String r8 = "shade"
            com.app.office.fc.dom4j.Element r9 = r0.element((java.lang.String) r8)
            if (r9 == 0) goto L_0x0168
            com.app.office.ss.util.ColorUtil r9 = com.app.office.ss.util.ColorUtil.instance()
            com.app.office.fc.dom4j.Element r8 = r0.element((java.lang.String) r8)
            java.lang.String r8 = r8.attributeValue((java.lang.String) r5)
            int r8 = java.lang.Integer.parseInt(r8)
            int r8 = -r8
            double r1 = (double) r8
            r6 = 4686111960511545344(0x41086a0000000000, double:200000.0)
            double r1 = r1 / r6
            int r8 = r9.getColorWithTint(r4, r1)
            goto L_0x0100
        L_0x0168:
            r3 = r4
        L_0x0169:
            java.lang.String r8 = "alpha"
            com.app.office.fc.dom4j.Element r9 = r0.element((java.lang.String) r8)
            if (r9 == 0) goto L_0x0191
            com.app.office.fc.dom4j.Element r8 = r0.element((java.lang.String) r8)
            java.lang.String r8 = r8.attributeValue((java.lang.String) r5)
            if (r8 == 0) goto L_0x0191
            int r8 = java.lang.Integer.parseInt(r8)
            float r8 = (float) r8
            r9 = 1203982336(0x47c35000, float:100000.0)
            float r8 = r8 / r9
            r9 = 1132396544(0x437f0000, float:255.0)
            float r8 = r8 * r9
            int r8 = (int) r8
            r9 = 16777215(0xffffff, float:2.3509886E-38)
            r9 = r9 & r3
            int r8 = r8 << 24
            r3 = r9 | r8
        L_0x0191:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.AutoShapeDataKit.getColor(java.util.Map, com.app.office.fc.dom4j.Element):int");
    }

    public static BackgroundAndFill processBackground(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, Element element, Map<String, Integer> map) {
        String attributeValue;
        PackageRelationship relationship;
        PackagePart part;
        String attributeValue2;
        Element element2;
        if (element != null) {
            try {
                BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
                Element element3 = element.element("solidFill");
                boolean z = false;
                if (element3 != null) {
                    backgroundAndFill.setFillType((byte) 0);
                    backgroundAndFill.setForegroundColor(getColor(map, element3));
                    return backgroundAndFill;
                }
                Element element4 = element.element("blipFill");
                if (element4 != null) {
                    Element element5 = element4.element("blip");
                    if (!(element5 == null || element5.attribute("embed") == null || (attributeValue = element5.attributeValue("embed")) == null || (relationship = packagePart.getRelationship(attributeValue)) == null || (part = zipPackage.getPart(relationship.getTargetURI())) == null)) {
                        Element element6 = element4.element("tile");
                        if (element6 == null) {
                            backgroundAndFill.setFillType((byte) 3);
                            Element element7 = element4.element("stretch");
                            if (!(element7 == null || (element2 = element7.element("fillRect")) == null)) {
                                PictureStretchInfo pictureStretchInfo = new PictureStretchInfo();
                                String attributeValue3 = element2.attributeValue("l");
                                boolean z2 = true;
                                if (attributeValue3 != null) {
                                    pictureStretchInfo.setLeftOffset(Float.parseFloat(attributeValue3) / 100000.0f);
                                    z = true;
                                }
                                String attributeValue4 = element2.attributeValue("r");
                                if (attributeValue4 != null) {
                                    pictureStretchInfo.setRightOffset(Float.parseFloat(attributeValue4) / 100000.0f);
                                    z = true;
                                }
                                String attributeValue5 = element2.attributeValue("t");
                                if (attributeValue5 != null) {
                                    pictureStretchInfo.setTopOffset(Float.parseFloat(attributeValue5) / 100000.0f);
                                    z = true;
                                }
                                String attributeValue6 = element2.attributeValue(HtmlTags.B);
                                if (attributeValue6 != null) {
                                    pictureStretchInfo.setBottomOffset(Float.parseFloat(attributeValue6) / 100000.0f);
                                } else {
                                    z2 = z;
                                }
                                if (z2) {
                                    backgroundAndFill.setStretch(pictureStretchInfo);
                                }
                            }
                            backgroundAndFill.setPictureIndex(iControl.getSysKit().getPictureManage().addPicture(part));
                        } else {
                            int addPicture = iControl.getSysKit().getPictureManage().addPicture(part);
                            backgroundAndFill.setFillType((byte) 2);
                            TileShader readTile = ShaderKit.readTile(iControl.getSysKit().getPictureManage().getPicture(addPicture), element6);
                            Element element8 = element5.element("alphaModFix");
                            if (!(element8 == null || (attributeValue2 = element8.attributeValue("amt")) == null)) {
                                readTile.setAlpha(Math.round((((float) Integer.parseInt(attributeValue2)) / 100000.0f) * 255.0f));
                            }
                            backgroundAndFill.setShader(readTile);
                        }
                        return backgroundAndFill;
                    }
                } else {
                    Element element9 = element.element("gradFill");
                    if (element9 != null) {
                        element9.element("gsLst");
                        backgroundAndFill.setFillType(ShaderKit.getGradientType(element9));
                        backgroundAndFill.setShader(ShaderKit.readGradient(map, element9));
                        return backgroundAndFill;
                    }
                    Element element10 = element.element("fillRef");
                    if (element10 != null) {
                        backgroundAndFill.setFillType((byte) 0);
                        backgroundAndFill.setForegroundColor(getColor(map, element10));
                        return backgroundAndFill;
                    }
                    Element element11 = element.element("pattFill");
                    if (element11 != null) {
                        Element element12 = element11.element("bgClr");
                        backgroundAndFill.setFillType((byte) 0);
                        backgroundAndFill.setForegroundColor(getColor(map, element12));
                        return backgroundAndFill;
                    }
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static AbstractShape getAutoShape(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, Element element, Rectangle rectangle, Map<String, Integer> map, int i) throws Exception {
        return getAutoShape(iControl, zipPackage, packagePart, element, rectangle, map, i, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:107:0x01a8  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x01ae  */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x01c1  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0127  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.app.office.common.shape.AbstractShape getAutoShape(com.app.office.system.IControl r17, com.app.office.fc.openxml4j.opc.ZipPackage r18, com.app.office.fc.openxml4j.opc.PackagePart r19, com.app.office.fc.dom4j.Element r20, com.app.office.java.awt.Rectangle r21, java.util.Map<java.lang.String, java.lang.Integer> r22, int r23, boolean r24) throws java.lang.Exception {
        /*
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r6 = r21
            r4 = r22
            if (r6 == 0) goto L_0x0227
            if (r3 != 0) goto L_0x0012
            goto L_0x0227
        L_0x0012:
            java.lang.String r7 = "spPr"
            com.app.office.fc.dom4j.Element r7 = r3.element((java.lang.String) r7)
            if (r7 == 0) goto L_0x0225
            com.app.office.fc.ppt.reader.ReaderKit r8 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            java.lang.String r8 = r8.getPlaceholderName(r3)
            java.lang.String r9 = r20.getName()
            java.lang.String r10 = "cxnSp"
            boolean r11 = r9.equals(r10)
            r14 = 233(0xe9, float:3.27E-43)
            if (r11 == 0) goto L_0x0033
            r8 = 20
            goto L_0x0049
        L_0x0033:
            if (r8 == 0) goto L_0x0047
            java.lang.String r11 = "Text Box"
            boolean r11 = r8.contains(r11)
            if (r11 != 0) goto L_0x0045
            java.lang.String r11 = "TextBox"
            boolean r8 = r8.contains(r11)
            if (r8 == 0) goto L_0x0047
        L_0x0045:
            r8 = 1
            goto L_0x0049
        L_0x0047:
            r8 = 233(0xe9, float:3.27E-43)
        L_0x0049:
            java.lang.String r11 = "prstGeom"
            com.app.office.fc.dom4j.Element r11 = r7.element((java.lang.String) r11)
            if (r11 == 0) goto L_0x00b2
            java.lang.String r12 = "prst"
            com.app.office.fc.dom4j.Attribute r16 = r11.attribute((java.lang.String) r12)
            if (r16 == 0) goto L_0x006d
            java.lang.String r12 = r11.attributeValue((java.lang.String) r12)
            if (r12 == 0) goto L_0x006d
            int r16 = r12.length()
            if (r16 <= 0) goto L_0x006d
            com.app.office.common.autoshape.AutoShapeTypes r8 = com.app.office.common.autoshape.AutoShapeTypes.instance()
            int r8 = r8.getAutoShapeType(r12)
        L_0x006d:
            java.lang.String r12 = "avLst"
            com.app.office.fc.dom4j.Element r11 = r11.element((java.lang.String) r12)
            if (r11 == 0) goto L_0x00b0
            java.lang.String r12 = "gd"
            java.util.List r11 = r11.elements((java.lang.String) r12)
            int r12 = r11.size()
            if (r12 <= 0) goto L_0x00b0
            int r12 = r11.size()
            java.lang.Float[] r12 = new java.lang.Float[r12]
            r15 = 0
        L_0x0088:
            int r5 = r11.size()
            if (r15 >= r5) goto L_0x00bd
            java.lang.Object r5 = r11.get(r15)
            com.app.office.fc.dom4j.Element r5 = (com.app.office.fc.dom4j.Element) r5
            java.lang.String r13 = "fmla"
            java.lang.String r5 = r5.attributeValue((java.lang.String) r13)
            r13 = 4
            java.lang.String r5 = r5.substring(r13)
            float r5 = java.lang.Float.parseFloat(r5)
            r13 = 1203982336(0x47c35000, float:100000.0)
            float r5 = r5 / r13
            java.lang.Float r5 = java.lang.Float.valueOf(r5)
            r12[r15] = r5
            int r15 = r15 + 1
            goto L_0x0088
        L_0x00b0:
            r12 = 0
            goto L_0x00bd
        L_0x00b2:
            java.lang.String r5 = "custGeom"
            com.app.office.fc.dom4j.Element r5 = r7.element((java.lang.String) r5)
            if (r5 == 0) goto L_0x00b0
            r8 = 233(0xe9, float:3.27E-43)
            goto L_0x00b0
        L_0x00bd:
            java.lang.String r5 = "noFill"
            com.app.office.fc.dom4j.Element r11 = r7.element((java.lang.String) r5)
            java.lang.String r13 = "style"
            if (r11 != 0) goto L_0x00fa
            boolean r9 = r9.equals(r10)
            if (r9 != 0) goto L_0x00fa
            com.app.office.common.bg.BackgroundAndFill r9 = processBackground(r0, r1, r2, r7, r4)
            if (r9 != 0) goto L_0x00fb
            r10 = 19
            if (r8 == r10) goto L_0x00fb
            r10 = 185(0xb9, float:2.59E-43)
            if (r8 == r10) goto L_0x00fb
            r10 = 85
            if (r8 == r10) goto L_0x00fb
            r10 = 86
            if (r8 == r10) goto L_0x00fb
            r10 = 186(0xba, float:2.6E-43)
            if (r8 == r10) goto L_0x00fb
            r10 = 87
            if (r8 == r10) goto L_0x00fb
            r10 = 88
            if (r8 == r10) goto L_0x00fb
            if (r8 == r14) goto L_0x00fb
            com.app.office.fc.dom4j.Element r9 = r3.element((java.lang.String) r13)
            com.app.office.common.bg.BackgroundAndFill r9 = processBackground(r0, r1, r2, r9, r4)
            goto L_0x00fb
        L_0x00fa:
            r9 = 0
        L_0x00fb:
            com.app.office.common.borders.Line r10 = com.app.office.fc.LineKit.createShapeLine((com.app.office.system.IControl) r0, (com.app.office.fc.openxml4j.opc.ZipPackage) r1, (com.app.office.fc.openxml4j.opc.PackagePart) r2, (com.app.office.fc.dom4j.Element) r3, (java.util.Map<java.lang.String, java.lang.Integer>) r4)
            java.lang.String r0 = "ln"
            com.app.office.fc.dom4j.Element r11 = r7.element((java.lang.String) r0)
            com.app.office.fc.dom4j.Element r0 = r3.element((java.lang.String) r13)
            if (r11 == 0) goto L_0x0114
            com.app.office.fc.dom4j.Element r0 = r11.element((java.lang.String) r5)
            if (r0 == 0) goto L_0x011f
        L_0x0111:
            r16 = 0
            goto L_0x0121
        L_0x0114:
            if (r0 == 0) goto L_0x0111
            java.lang.String r1 = "lnRef"
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r1)
            if (r0 != 0) goto L_0x011f
            goto L_0x0111
        L_0x011f:
            r16 = 1
        L_0x0121:
            r0 = 32
            r1 = 20
            if (r8 == r1) goto L_0x0137
            if (r8 == r0) goto L_0x0135
            if (r6 == 0) goto L_0x0135
            int r1 = r6.width
            if (r1 == 0) goto L_0x0133
            int r1 = r6.height
            if (r1 != 0) goto L_0x0135
        L_0x0133:
            r0 = 0
            return r0
        L_0x0135:
            r1 = 20
        L_0x0137:
            if (r8 == r1) goto L_0x01a6
            if (r8 == r0) goto L_0x01a6
            r0 = 34
            if (r8 == r0) goto L_0x01a6
            r0 = 38
            if (r8 != r0) goto L_0x0145
            goto L_0x01a6
        L_0x0145:
            if (r8 != r14) goto L_0x0178
            if (r23 != 0) goto L_0x014f
            com.app.office.common.shape.WPAutoShape r0 = new com.app.office.common.shape.WPAutoShape
            r0.<init>()
            goto L_0x0154
        L_0x014f:
            com.app.office.common.shape.ArbitraryPolygonShape r0 = new com.app.office.common.shape.ArbitraryPolygonShape
            r0.<init>()
        L_0x0154:
            r12 = r0
            if (r10 == 0) goto L_0x015d
            com.app.office.common.bg.BackgroundAndFill r0 = r10.getBackgroundAndFill()
            r4 = r0
            goto L_0x015e
        L_0x015d:
            r4 = 0
        L_0x015e:
            r0 = r12
            r1 = r20
            r2 = r9
            r3 = r16
            r5 = r11
            r6 = r21
            com.app.office.common.autoshape.ArbitraryPolygonShapePath.processArbitraryPolygonShape(r0, r1, r2, r3, r4, r5, r6)
            r12.setShapeType(r8)
            r12.setLine((com.app.office.common.borders.Line) r10)
            com.app.office.fc.ppt.reader.ReaderKit r0 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            r0.processRotation((com.app.office.fc.dom4j.Element) r7, (com.app.office.common.shape.IShape) r12)
            return r12
        L_0x0178:
            if (r24 != 0) goto L_0x017e
            if (r9 != 0) goto L_0x017e
            if (r16 == 0) goto L_0x0225
        L_0x017e:
            if (r23 != 0) goto L_0x0189
            com.app.office.common.shape.WPAutoShape r0 = new com.app.office.common.shape.WPAutoShape
            r0.<init>()
            r0.setShapeType(r8)
            goto L_0x018e
        L_0x0189:
            com.app.office.common.shape.AutoShape r0 = new com.app.office.common.shape.AutoShape
            r0.<init>(r8)
        L_0x018e:
            r0.setBounds(r6)
            if (r9 == 0) goto L_0x0196
            r0.setBackgroundAndFill(r9)
        L_0x0196:
            if (r10 == 0) goto L_0x019b
            r0.setLine((com.app.office.common.borders.Line) r10)
        L_0x019b:
            r0.setAdjustData(r12)
            com.app.office.fc.ppt.reader.ReaderKit r1 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            r1.processRotation((com.app.office.fc.dom4j.Element) r7, (com.app.office.common.shape.IShape) r0)
            return r0
        L_0x01a6:
            if (r23 != 0) goto L_0x01ae
            com.app.office.common.shape.WPAutoShape r0 = new com.app.office.common.shape.WPAutoShape
            r0.<init>()
            goto L_0x01b3
        L_0x01ae:
            com.app.office.common.shape.LineShape r0 = new com.app.office.common.shape.LineShape
            r0.<init>()
        L_0x01b3:
            r0.setShapeType(r8)
            r0.setBounds(r6)
            r0.setAdjustData(r12)
            r0.setLine((com.app.office.common.borders.Line) r10)
            if (r11 == 0) goto L_0x021d
            java.lang.String r1 = "headEnd"
            com.app.office.fc.dom4j.Element r1 = r11.element((java.lang.String) r1)
            java.lang.String r2 = "len"
            java.lang.String r3 = "w"
            java.lang.String r4 = "type"
            if (r1 == 0) goto L_0x01f2
            com.app.office.fc.dom4j.Attribute r5 = r1.attribute((java.lang.String) r4)
            if (r5 == 0) goto L_0x01f2
            java.lang.String r5 = r1.attributeValue((java.lang.String) r4)
            byte r5 = com.app.office.common.shape.Arrow.getArrowType(r5)
            if (r5 == 0) goto L_0x01f2
            java.lang.String r6 = r1.attributeValue((java.lang.String) r3)
            int r6 = com.app.office.common.shape.Arrow.getArrowSize(r6)
            java.lang.String r1 = r1.attributeValue((java.lang.String) r2)
            int r1 = com.app.office.common.shape.Arrow.getArrowSize(r1)
            r0.createStartArrow(r5, r6, r1)
        L_0x01f2:
            java.lang.String r1 = "tailEnd"
            com.app.office.fc.dom4j.Element r1 = r11.element((java.lang.String) r1)
            if (r1 == 0) goto L_0x021d
            com.app.office.fc.dom4j.Attribute r5 = r1.attribute((java.lang.String) r4)
            if (r5 == 0) goto L_0x021d
            java.lang.String r4 = r1.attributeValue((java.lang.String) r4)
            byte r4 = com.app.office.common.shape.Arrow.getArrowType(r4)
            if (r4 == 0) goto L_0x021d
            java.lang.String r3 = r1.attributeValue((java.lang.String) r3)
            int r3 = com.app.office.common.shape.Arrow.getArrowSize(r3)
            java.lang.String r1 = r1.attributeValue((java.lang.String) r2)
            int r1 = com.app.office.common.shape.Arrow.getArrowSize(r1)
            r0.createEndArrow(r4, r3, r1)
        L_0x021d:
            com.app.office.fc.ppt.reader.ReaderKit r1 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            r1.processRotation((com.app.office.fc.dom4j.Element) r7, (com.app.office.common.shape.IShape) r0)
            return r0
        L_0x0225:
            r0 = 0
            return r0
        L_0x0227:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.AutoShapeDataKit.getAutoShape(com.app.office.system.IControl, com.app.office.fc.openxml4j.opc.ZipPackage, com.app.office.fc.openxml4j.opc.PackagePart, com.app.office.fc.dom4j.Element, com.app.office.java.awt.Rectangle, java.util.Map, int, boolean):com.app.office.common.shape.AbstractShape");
    }

    public static void processPictureShape(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, Element element, Map<String, Integer> map, PictureShape pictureShape) {
        if (pictureShape != null && element != null) {
            pictureShape.setBackgroundAndFill(processBackground(iControl, zipPackage, packagePart, element, map));
            pictureShape.setLine(LineKit.createLine(iControl, zipPackage, packagePart, element.element("ln"), map));
        }
    }
}
