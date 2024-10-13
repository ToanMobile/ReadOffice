package com.app.office.fc;

import com.app.office.common.autoshape.AutoShapeDataKit;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.fc.ppt.reader.BackgroundReader;
import com.app.office.pg.model.PGMaster;
import com.app.office.system.IControl;
import java.util.Map;

public class LineKit {
    public static Line createLine(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, Element element) throws Exception {
        if (element == null || element.element("noFill") != null) {
            return null;
        }
        boolean z = true;
        int round = element.attributeValue("w") != null ? Math.round((((float) Integer.parseInt(element.attributeValue("w"))) * 96.0f) / 914400.0f) : 1;
        Element element2 = element.element("prstDash");
        if (element2 == null || "solid".equalsIgnoreCase(element2.attributeValue("val"))) {
            z = false;
        }
        BackgroundAndFill processBackground = BackgroundReader.instance().processBackground(iControl, zipPackage, packagePart, pGMaster, element);
        if (processBackground == null) {
            return null;
        }
        Line line = new Line();
        line.setBackgroundAndFill(processBackground);
        line.setLineWidth(round);
        line.setDash(z);
        return line;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v0, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: int} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00c2 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.app.office.common.borders.Line createShapeLine(com.app.office.system.IControl r11, com.app.office.fc.openxml4j.opc.ZipPackage r12, com.app.office.fc.openxml4j.opc.PackagePart r13, com.app.office.pg.model.PGMaster r14, com.app.office.fc.dom4j.Element r15) throws java.lang.Exception {
        /*
            java.lang.String r0 = "spPr"
            com.app.office.fc.dom4j.Element r0 = r15.element((java.lang.String) r0)
            java.lang.String r1 = "ln"
            com.app.office.fc.dom4j.Element r7 = r0.element((java.lang.String) r1)
            java.lang.String r0 = "style"
            com.app.office.fc.dom4j.Element r15 = r15.element((java.lang.String) r0)
            r0 = 0
            r1 = 1
            r8 = 0
            java.lang.String r9 = "lnRef"
            if (r7 == 0) goto L_0x0089
            java.lang.String r2 = "noFill"
            com.app.office.fc.dom4j.Element r2 = r7.element((java.lang.String) r2)
            if (r2 != 0) goto L_0x00b0
            java.lang.String r2 = "w"
            java.lang.String r3 = r7.attributeValue((java.lang.String) r2)
            if (r3 == 0) goto L_0x0040
            java.lang.String r2 = r7.attributeValue((java.lang.String) r2)
            int r2 = java.lang.Integer.parseInt(r2)
            float r2 = (float) r2
            r3 = 1119879168(0x42c00000, float:96.0)
            float r2 = r2 * r3
            r3 = 1230978560(0x495f3e00, float:914400.0)
            float r2 = r2 / r3
            int r2 = java.lang.Math.round(r2)
            r10 = r2
            goto L_0x0041
        L_0x0040:
            r10 = 1
        L_0x0041:
            java.lang.String r2 = "prstDash"
            com.app.office.fc.dom4j.Element r2 = r7.element((java.lang.String) r2)
            if (r2 == 0) goto L_0x0058
            java.lang.String r3 = "val"
            java.lang.String r2 = r2.attributeValue((java.lang.String) r3)
            java.lang.String r3 = "solid"
            boolean r2 = r3.equalsIgnoreCase(r2)
            if (r2 != 0) goto L_0x0058
            goto L_0x0059
        L_0x0058:
            r1 = 0
        L_0x0059:
            com.app.office.fc.ppt.reader.BackgroundReader r2 = com.app.office.fc.ppt.reader.BackgroundReader.instance()
            r3 = r11
            r4 = r12
            r5 = r13
            r6 = r14
            com.app.office.common.bg.BackgroundAndFill r11 = r2.processBackground(r3, r4, r5, r6, r7)
            if (r11 != 0) goto L_0x0086
            if (r15 == 0) goto L_0x0086
            com.app.office.fc.dom4j.Element r12 = r15.element((java.lang.String) r9)
            if (r12 == 0) goto L_0x0086
            com.app.office.common.bg.BackgroundAndFill r11 = new com.app.office.common.bg.BackgroundAndFill
            r11.<init>()
            r11.setFillType(r8)
            com.app.office.fc.ppt.reader.ReaderKit r12 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            com.app.office.fc.dom4j.Element r13 = r15.element((java.lang.String) r9)
            int r12 = r12.getColor(r14, r13)
            r11.setForegroundColor(r12)
        L_0x0086:
            r8 = r1
            r1 = r10
            goto L_0x00b1
        L_0x0089:
            if (r15 == 0) goto L_0x00b0
            com.app.office.fc.dom4j.Element r11 = r15.element((java.lang.String) r9)
            if (r11 == 0) goto L_0x00b0
            com.app.office.fc.ppt.reader.ReaderKit r11 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            com.app.office.fc.dom4j.Element r12 = r15.element((java.lang.String) r9)
            int r11 = r11.getColor(r14, r12)
            r12 = 16777215(0xffffff, float:2.3509886E-38)
            r12 = r12 & r11
            if (r12 == 0) goto L_0x00b0
            com.app.office.common.bg.BackgroundAndFill r12 = new com.app.office.common.bg.BackgroundAndFill
            r12.<init>()
            r12.setFillType(r8)
            r12.setForegroundColor(r11)
            r11 = r12
            goto L_0x00b1
        L_0x00b0:
            r11 = r0
        L_0x00b1:
            if (r11 == 0) goto L_0x00c2
            com.app.office.common.borders.Line r12 = new com.app.office.common.borders.Line
            r12.<init>()
            r12.setBackgroundAndFill(r11)
            r12.setLineWidth(r1)
            r12.setDash(r8)
            return r12
        L_0x00c2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.LineKit.createShapeLine(com.app.office.system.IControl, com.app.office.fc.openxml4j.opc.ZipPackage, com.app.office.fc.openxml4j.opc.PackagePart, com.app.office.pg.model.PGMaster, com.app.office.fc.dom4j.Element):com.app.office.common.borders.Line");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: int} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.app.office.common.borders.Line createLine(com.app.office.system.IControl r6, com.app.office.fc.openxml4j.opc.ZipPackage r7, com.app.office.fc.openxml4j.opc.PackagePart r8, com.app.office.fc.dom4j.Element r9, java.util.Map<java.lang.String, java.lang.Integer> r10) {
        /*
            r0 = 0
            r1 = 1
            r2 = 0
            if (r9 == 0) goto L_0x004d
            java.lang.String r3 = "w"
            java.lang.String r4 = r9.attributeValue((java.lang.String) r3)
            if (r4 == 0) goto L_0x0023
            java.lang.String r3 = r9.attributeValue((java.lang.String) r3)
            int r3 = java.lang.Integer.parseInt(r3)
            float r3 = (float) r3
            r4 = 1119879168(0x42c00000, float:96.0)
            float r3 = r3 * r4
            r4 = 1230978560(0x495f3e00, float:914400.0)
            float r3 = r3 / r4
            int r3 = java.lang.Math.round(r3)
            goto L_0x0024
        L_0x0023:
            r3 = 1
        L_0x0024:
            java.lang.String r4 = "prstDash"
            com.app.office.fc.dom4j.Element r4 = r9.element((java.lang.String) r4)
            if (r4 == 0) goto L_0x003b
            java.lang.String r5 = "val"
            java.lang.String r4 = r4.attributeValue((java.lang.String) r5)
            java.lang.String r5 = "solid"
            boolean r4 = r5.equalsIgnoreCase(r4)
            if (r4 != 0) goto L_0x003b
            goto L_0x003c
        L_0x003b:
            r1 = 0
        L_0x003c:
            java.lang.String r2 = "noFill"
            com.app.office.fc.dom4j.Element r2 = r9.element((java.lang.String) r2)
            if (r2 != 0) goto L_0x0049
            com.app.office.common.bg.BackgroundAndFill r6 = com.app.office.common.autoshape.AutoShapeDataKit.processBackground(r6, r7, r8, r9, r10)
            goto L_0x004a
        L_0x0049:
            r6 = r0
        L_0x004a:
            r2 = r1
            r1 = r3
            goto L_0x004e
        L_0x004d:
            r6 = r0
        L_0x004e:
            if (r6 == 0) goto L_0x005f
            com.app.office.common.borders.Line r7 = new com.app.office.common.borders.Line
            r7.<init>()
            r7.setBackgroundAndFill(r6)
            r7.setLineWidth(r1)
            r7.setDash(r2)
            return r7
        L_0x005f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.LineKit.createLine(com.app.office.system.IControl, com.app.office.fc.openxml4j.opc.ZipPackage, com.app.office.fc.openxml4j.opc.PackagePart, com.app.office.fc.dom4j.Element, java.util.Map):com.app.office.common.borders.Line");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: int} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.app.office.common.borders.Line createShapeLine(com.app.office.system.IControl r8, com.app.office.fc.openxml4j.opc.ZipPackage r9, com.app.office.fc.openxml4j.opc.PackagePart r10, com.app.office.fc.dom4j.Element r11, java.util.Map<java.lang.String, java.lang.Integer> r12) {
        /*
            java.lang.String r0 = "spPr"
            com.app.office.fc.dom4j.Element r0 = r11.element((java.lang.String) r0)
            java.lang.String r1 = "ln"
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r1)
            java.lang.String r1 = "style"
            com.app.office.fc.dom4j.Element r11 = r11.element((java.lang.String) r1)
            r1 = 0
            r2 = 1
            r3 = 0
            java.lang.String r4 = "lnRef"
            if (r0 == 0) goto L_0x007e
            java.lang.String r5 = "w"
            java.lang.String r6 = r0.attributeValue((java.lang.String) r5)
            if (r6 == 0) goto L_0x0037
            java.lang.String r5 = r0.attributeValue((java.lang.String) r5)
            int r5 = java.lang.Integer.parseInt(r5)
            float r5 = (float) r5
            r6 = 1119879168(0x42c00000, float:96.0)
            float r5 = r5 * r6
            r6 = 1230978560(0x495f3e00, float:914400.0)
            float r5 = r5 / r6
            int r5 = java.lang.Math.round(r5)
            goto L_0x0038
        L_0x0037:
            r5 = 1
        L_0x0038:
            java.lang.String r6 = "prstDash"
            com.app.office.fc.dom4j.Element r6 = r0.element((java.lang.String) r6)
            if (r6 == 0) goto L_0x004f
            java.lang.String r7 = "val"
            java.lang.String r6 = r6.attributeValue((java.lang.String) r7)
            java.lang.String r7 = "solid"
            boolean r6 = r7.equalsIgnoreCase(r6)
            if (r6 != 0) goto L_0x004f
            goto L_0x0050
        L_0x004f:
            r2 = 0
        L_0x0050:
            java.lang.String r6 = "noFill"
            com.app.office.fc.dom4j.Element r6 = r0.element((java.lang.String) r6)
            if (r6 != 0) goto L_0x007a
            com.app.office.common.bg.BackgroundAndFill r8 = com.app.office.common.autoshape.AutoShapeDataKit.processBackground(r8, r9, r10, r0, r12)
            if (r8 != 0) goto L_0x007b
            if (r11 == 0) goto L_0x007b
            com.app.office.fc.dom4j.Element r9 = r11.element((java.lang.String) r4)
            if (r9 == 0) goto L_0x007b
            com.app.office.common.bg.BackgroundAndFill r8 = new com.app.office.common.bg.BackgroundAndFill
            r8.<init>()
            r8.setFillType(r3)
            com.app.office.fc.dom4j.Element r9 = r11.element((java.lang.String) r4)
            int r9 = com.app.office.common.autoshape.AutoShapeDataKit.getColor(r12, r9)
            r8.setForegroundColor(r9)
            goto L_0x007b
        L_0x007a:
            r8 = r1
        L_0x007b:
            r3 = r2
            r2 = r5
            goto L_0x009b
        L_0x007e:
            if (r11 == 0) goto L_0x009a
            com.app.office.fc.dom4j.Element r8 = r11.element((java.lang.String) r4)
            if (r8 == 0) goto L_0x009a
            com.app.office.common.bg.BackgroundAndFill r8 = new com.app.office.common.bg.BackgroundAndFill
            r8.<init>()
            r8.setFillType(r3)
            com.app.office.fc.dom4j.Element r9 = r11.element((java.lang.String) r4)
            int r9 = com.app.office.common.autoshape.AutoShapeDataKit.getColor(r12, r9)
            r8.setForegroundColor(r9)
            goto L_0x009b
        L_0x009a:
            r8 = r1
        L_0x009b:
            if (r8 == 0) goto L_0x00ac
            com.app.office.common.borders.Line r9 = new com.app.office.common.borders.Line
            r9.<init>()
            r9.setBackgroundAndFill(r8)
            r9.setLineWidth(r2)
            r9.setDash(r3)
            return r9
        L_0x00ac:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.LineKit.createShapeLine(com.app.office.system.IControl, com.app.office.fc.openxml4j.opc.ZipPackage, com.app.office.fc.openxml4j.opc.PackagePart, com.app.office.fc.dom4j.Element, java.util.Map):com.app.office.common.borders.Line");
    }

    public static Line createChartLine(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, Element element, Map<String, Integer> map) {
        BackgroundAndFill processBackground;
        boolean z = false;
        if (element != null) {
            int round = element.attributeValue("w") != null ? Math.round((((float) Integer.parseInt(element.attributeValue("w"))) * 96.0f) / 914400.0f) : 1;
            Element element2 = element.element("prstDash");
            if (element2 != null && !"solid".equalsIgnoreCase(element2.attributeValue("val"))) {
                z = true;
            }
            if (element.element("noFill") != null || (processBackground = AutoShapeDataKit.processBackground(iControl, zipPackage, packagePart, element, map)) == null) {
                return null;
            }
            Line line = new Line();
            line.setBackgroundAndFill(processBackground);
            line.setLineWidth(round);
            line.setDash(z);
            return line;
        }
        Line line2 = new Line();
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        backgroundAndFill.setForegroundColor(-9145228);
        line2.setBackgroundAndFill(backgroundAndFill);
        line2.setLineWidth(1);
        return line2;
    }
}
