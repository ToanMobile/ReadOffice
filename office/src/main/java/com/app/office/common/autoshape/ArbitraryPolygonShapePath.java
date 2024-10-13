package com.app.office.common.autoshape;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail;
import com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.shape.ArbitraryPolygonShape;
import com.app.office.common.shape.Arrow;
import com.app.office.fc.dom4j.Element;
import com.app.office.java.awt.Rectangle;
import java.util.List;

public class ArbitraryPolygonShapePath {
    /* JADX WARNING: Removed duplicated region for block: B:103:0x02a3  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x02b0  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x02b6  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x02c0 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0183  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void processArbitraryPolygonShape(com.app.office.common.shape.ArbitraryPolygonShape r24, com.app.office.fc.dom4j.Element r25, com.app.office.common.bg.BackgroundAndFill r26, boolean r27, com.app.office.common.bg.BackgroundAndFill r28, com.app.office.fc.dom4j.Element r29, com.app.office.java.awt.Rectangle r30) throws java.lang.Exception {
        /*
            r7 = r24
            r8 = r26
            r0 = r28
            r1 = r29
            r9 = r30
            if (r7 != 0) goto L_0x000d
            return
        L_0x000d:
            r10 = 1230978560(0x495f3e00, float:914400.0)
            r11 = 1119879168(0x42c00000, float:96.0)
            r2 = 1
            java.lang.String r12 = "w"
            if (r1 == 0) goto L_0x002e
            java.lang.String r3 = r1.attributeValue((java.lang.String) r12)
            if (r3 == 0) goto L_0x002e
            java.lang.String r3 = r1.attributeValue((java.lang.String) r12)
            int r3 = java.lang.Integer.parseInt(r3)
            float r3 = (float) r3
            float r3 = r3 * r11
            float r3 = r3 / r10
            int r3 = java.lang.Math.round(r3)
            goto L_0x002f
        L_0x002e:
            r3 = 1
        L_0x002f:
            com.app.office.common.borders.Line r13 = r24.createLine()
            r13.setBackgroundAndFill(r0)
            r13.setLineWidth(r3)
            r7.setBounds(r9)
            java.lang.String r4 = "spPr"
            r5 = r25
            com.app.office.fc.dom4j.Element r4 = r5.element((java.lang.String) r4)
            java.lang.String r5 = "custGeom"
            com.app.office.fc.dom4j.Element r4 = r4.element((java.lang.String) r5)
            java.lang.String r5 = "pathLst"
            com.app.office.fc.dom4j.Element r4 = r4.element((java.lang.String) r5)
            java.lang.String r5 = "path"
            java.util.List r14 = r4.elements((java.lang.String) r5)
            int r4 = r14.size()
            java.lang.String r15 = "h"
            java.lang.String r6 = "none"
            java.lang.String r10 = "stroke"
            r11 = 0
            if (r4 != r2) goto L_0x01ce
            if (r1 == 0) goto L_0x01ce
            java.lang.Object r4 = r14.get(r11)
            com.app.office.fc.dom4j.Element r4 = (com.app.office.fc.dom4j.Element) r4
            java.lang.String r4 = r4.attributeValue((java.lang.String) r12)
            java.lang.Object r18 = r14.get(r11)
            r5 = r18
            com.app.office.fc.dom4j.Element r5 = (com.app.office.fc.dom4j.Element) r5
            java.lang.String r5 = r5.attributeValue((java.lang.String) r15)
            if (r4 == 0) goto L_0x00a6
            if (r5 == 0) goto L_0x00a6
            int r4 = java.lang.Integer.parseInt(r4)
            float r4 = (float) r4
            r17 = 1119879168(0x42c00000, float:96.0)
            float r4 = r4 * r17
            r16 = 1230978560(0x495f3e00, float:914400.0)
            float r4 = r4 / r16
            int r5 = java.lang.Integer.parseInt(r5)
            float r5 = (float) r5
            float r5 = r5 * r17
            float r5 = r5 / r16
            float r3 = (float) r3
            int r2 = r9.width
            float r2 = (float) r2
            float r4 = r4 / r2
            int r2 = r9.height
            float r2 = (float) r2
            float r5 = r5 / r2
            float r2 = java.lang.Math.min(r4, r5)
            float r3 = r3 * r2
            int r3 = (int) r3
        L_0x00a6:
            java.lang.String r2 = "headEnd"
            com.app.office.fc.dom4j.Element r2 = r1.element((java.lang.String) r2)
            java.lang.String r5 = "len"
            java.lang.String r4 = "type"
            if (r2 == 0) goto L_0x0137
            com.app.office.fc.dom4j.Attribute r19 = r2.attribute((java.lang.String) r4)
            if (r19 == 0) goto L_0x0137
            java.lang.String r11 = r2.attributeValue((java.lang.String) r4)
            boolean r11 = r6.equals(r11)
            if (r11 != 0) goto L_0x0137
            java.lang.String r11 = r2.attributeValue((java.lang.String) r4)
            byte r11 = com.app.office.common.shape.Arrow.getArrowType(r11)
            java.lang.String r20 = r2.attributeValue((java.lang.String) r12)
            int r9 = getArrowSize(r20)
            java.lang.String r2 = r2.attributeValue((java.lang.String) r5)
            int r2 = getArrowSize(r2)
            r7.createStartArrow(r11, r9, r2)
            r2 = 0
            java.lang.Object r9 = r14.get(r2)
            com.app.office.fc.dom4j.Element r9 = (com.app.office.fc.dom4j.Element) r9
            com.app.office.common.shape.Arrow r2 = r24.getStartArrow()
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r2 = getPathHeadArrowPath(r2, r3, r9)
            if (r2 == 0) goto L_0x0137
            r20 = r15
            android.graphics.Path r15 = r2.getArrowPath()
            android.graphics.PointF r2 = r2.getArrowTailCenter()
            r21 = r2
            if (r15 == 0) goto L_0x0134
            com.app.office.common.autoshape.ExtendPath r2 = new com.app.office.common.autoshape.ExtendPath
            r2.<init>()
            r22 = r3
            r3 = 1
            r2.setArrowFlag(r3)
            r2.setPath(r15)
            if (r8 != 0) goto L_0x010e
            if (r27 == 0) goto L_0x0130
        L_0x010e:
            if (r27 == 0) goto L_0x012b
            com.app.office.fc.dom4j.Attribute r3 = r9.attribute((java.lang.String) r10)
            if (r3 == 0) goto L_0x0120
            java.lang.String r3 = r9.attributeValue((java.lang.String) r10)
            int r3 = java.lang.Integer.parseInt(r3)
            if (r3 == 0) goto L_0x012b
        L_0x0120:
            r3 = 5
            if (r11 == r3) goto L_0x0127
            r2.setBackgroundAndFill(r0)
            goto L_0x0130
        L_0x0127:
            r2.setLine((com.app.office.common.borders.Line) r13)
            goto L_0x0130
        L_0x012b:
            if (r8 == 0) goto L_0x0130
            r2.setBackgroundAndFill(r8)
        L_0x0130:
            r3 = r2
            r2 = r21
            goto L_0x013d
        L_0x0134:
            r22 = r3
            goto L_0x013c
        L_0x0137:
            r22 = r3
            r20 = r15
            r2 = 0
        L_0x013c:
            r3 = 0
        L_0x013d:
            java.lang.String r9 = "tailEnd"
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r9)
            if (r1 == 0) goto L_0x01c9
            com.app.office.fc.dom4j.Attribute r9 = r1.attribute((java.lang.String) r4)
            if (r9 == 0) goto L_0x01c9
            java.lang.String r9 = r1.attributeValue((java.lang.String) r4)
            boolean r9 = r6.equals(r9)
            if (r9 != 0) goto L_0x01c9
            java.lang.String r4 = r1.attributeValue((java.lang.String) r4)
            byte r4 = com.app.office.common.shape.Arrow.getArrowType(r4)
            java.lang.String r9 = r1.attributeValue((java.lang.String) r12)
            int r9 = getArrowSize(r9)
            java.lang.String r1 = r1.attributeValue((java.lang.String) r5)
            int r1 = getArrowSize(r1)
            r7.createEndArrow(r4, r9, r1)
            r1 = 0
            java.lang.Object r5 = r14.get(r1)
            com.app.office.fc.dom4j.Element r5 = (com.app.office.fc.dom4j.Element) r5
            com.app.office.common.shape.Arrow r1 = r24.getEndArrow()
            r9 = r22
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r1 = getPathTailArrowPath(r1, r9, r5)
            if (r1 == 0) goto L_0x01c9
            android.graphics.Path r9 = r1.getArrowPath()
            android.graphics.PointF r1 = r1.getArrowTailCenter()
            if (r9 == 0) goto L_0x01c4
            com.app.office.common.autoshape.ExtendPath r11 = new com.app.office.common.autoshape.ExtendPath
            r11.<init>()
            r15 = 1
            r11.setArrowFlag(r15)
            r11.setPath(r9)
            if (r8 != 0) goto L_0x019d
            if (r27 == 0) goto L_0x01bf
        L_0x019d:
            if (r27 == 0) goto L_0x01ba
            com.app.office.fc.dom4j.Attribute r9 = r5.attribute((java.lang.String) r10)
            if (r9 == 0) goto L_0x01af
            java.lang.String r5 = r5.attributeValue((java.lang.String) r10)
            int r5 = java.lang.Integer.parseInt(r5)
            if (r5 == 0) goto L_0x01ba
        L_0x01af:
            r5 = 5
            if (r4 == r5) goto L_0x01b6
            r11.setBackgroundAndFill(r0)
            goto L_0x01bf
        L_0x01b6:
            r11.setLine((com.app.office.common.borders.Line) r13)
            goto L_0x01bf
        L_0x01ba:
            if (r8 == 0) goto L_0x01bf
            r11.setBackgroundAndFill(r8)
        L_0x01bf:
            r9 = r1
            r15 = r3
            r5 = r11
            r11 = r2
            goto L_0x01d4
        L_0x01c4:
            r9 = r1
            r11 = r2
            r15 = r3
            r5 = 0
            goto L_0x01d4
        L_0x01c9:
            r11 = r2
            r15 = r3
            r5 = 0
            r9 = 0
            goto L_0x01d4
        L_0x01ce:
            r20 = r15
            r5 = 0
            r9 = 0
            r11 = 0
            r15 = 0
        L_0x01d4:
            r4 = 0
        L_0x01d5:
            int r0 = r14.size()
            if (r4 >= r0) goto L_0x02ca
            com.app.office.common.autoshape.ExtendPath r3 = new com.app.office.common.autoshape.ExtendPath
            r3.<init>()
            java.lang.Object r0 = r14.get(r4)
            r1 = r0
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            r0 = r24
            r2 = r30
            r28 = r15
            r15 = r3
            r3 = r26
            r7 = r4
            r4 = r27
            r23 = r5
            r18 = r13
            r13 = 0
            r5 = r11
            r13 = r6
            r6 = r9
            android.graphics.Path r0 = getArrowPath(r0, r1, r2, r3, r4, r5, r6)
            r15.setPath(r0)
            java.lang.Object r1 = r14.get(r7)
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            java.lang.String r2 = r1.attributeValue((java.lang.String) r12)
            r3 = r20
            java.lang.String r4 = r1.attributeValue((java.lang.String) r3)
            android.graphics.Matrix r5 = new android.graphics.Matrix
            r5.<init>()
            if (r2 == 0) goto L_0x0245
            if (r4 == 0) goto L_0x0245
            int r2 = java.lang.Integer.parseInt(r2)
            float r2 = (float) r2
            r6 = 1119879168(0x42c00000, float:96.0)
            float r2 = r2 * r6
            r16 = 1230978560(0x495f3e00, float:914400.0)
            float r2 = r2 / r16
            int r4 = java.lang.Integer.parseInt(r4)
            float r4 = (float) r4
            float r4 = r4 * r6
            float r4 = r4 / r16
            r6 = r30
            r20 = r3
            int r3 = r6.width
            float r3 = (float) r3
            float r3 = r3 / r2
            int r2 = r6.height
            float r2 = (float) r2
            float r2 = r2 / r4
            r5.postScale(r3, r2)
            r0.transform(r5)
            goto L_0x024c
        L_0x0245:
            r6 = r30
            r20 = r3
            r16 = 1230978560(0x495f3e00, float:914400.0)
        L_0x024c:
            if (r8 != 0) goto L_0x025a
            if (r27 == 0) goto L_0x0251
            goto L_0x025a
        L_0x0251:
            r0 = 0
            r1 = 0
        L_0x0253:
            r3 = r24
            r19 = r7
            r2 = r18
            goto L_0x029e
        L_0x025a:
            if (r8 == 0) goto L_0x0278
            java.lang.String r0 = "fill"
            com.app.office.fc.dom4j.Attribute r2 = r1.attribute((java.lang.String) r0)
            if (r2 == 0) goto L_0x0273
            java.lang.String r0 = r1.attributeValue((java.lang.String) r0)
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x0273
            r0 = 0
            r15.setBackgroundAndFill(r0)
            goto L_0x0279
        L_0x0273:
            r0 = 0
            r15.setBackgroundAndFill(r8)
            goto L_0x0279
        L_0x0278:
            r0 = 0
        L_0x0279:
            if (r27 == 0) goto L_0x0297
            com.app.office.fc.dom4j.Attribute r2 = r1.attribute((java.lang.String) r10)
            if (r2 == 0) goto L_0x0290
            java.lang.String r1 = r1.attributeValue((java.lang.String) r10)
            int r1 = java.lang.Integer.parseInt(r1)
            if (r1 != 0) goto L_0x0290
            r1 = 0
            r15.setLine((boolean) r1)
            goto L_0x0253
        L_0x0290:
            r1 = 0
            r2 = r18
            r15.setLine((com.app.office.common.borders.Line) r2)
            goto L_0x029a
        L_0x0297:
            r2 = r18
            r1 = 0
        L_0x029a:
            r3 = r24
            r19 = r7
        L_0x029e:
            r3.appendPath(r15)
            if (r28 == 0) goto L_0x02b0
            android.graphics.Path r4 = r28.getPath()
            r4.transform(r5)
            r4 = r28
            r3.appendPath(r4)
            goto L_0x02b2
        L_0x02b0:
            r4 = r28
        L_0x02b2:
            r7 = r23
            if (r7 == 0) goto L_0x02c0
            android.graphics.Path r15 = r7.getPath()
            r15.transform(r5)
            r3.appendPath(r7)
        L_0x02c0:
            int r5 = r19 + 1
            r15 = r4
            r4 = r5
            r5 = r7
            r6 = r13
            r13 = r2
            r7 = r3
            goto L_0x01d5
        L_0x02ca:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.ArbitraryPolygonShapePath.processArbitraryPolygonShape(com.app.office.common.shape.ArbitraryPolygonShape, com.app.office.fc.dom4j.Element, com.app.office.common.bg.BackgroundAndFill, boolean, com.app.office.common.bg.BackgroundAndFill, com.app.office.fc.dom4j.Element, com.app.office.java.awt.Rectangle):void");
    }

    private static Path getArrowPath(ArbitraryPolygonShape arbitraryPolygonShape, Element element, Rectangle rectangle, BackgroundAndFill backgroundAndFill, boolean z, PointF pointF, PointF pointF2) {
        List list;
        int i;
        int i2;
        int i3;
        PointF pointF3;
        Rectangle rectangle2 = rectangle;
        Path path = new Path();
        List elements = element.elements();
        int size = elements.size();
        int i4 = size - 1;
        Element element2 = (Element) elements.get(i4);
        PointF pointF4 = pointF;
        PointF pointF5 = pointF2;
        int i5 = 0;
        while (i5 < size) {
            Element element3 = (Element) elements.get(i5);
            if (pointF4 == null || i5 != 0 || !element3.getName().equals("moveTo")) {
                list = elements;
                i3 = size;
                PointF pointF6 = pointF4;
                Object obj = "moveTo";
                String str = "swAng";
                if (pointF5 == null || i5 != i4) {
                    i2 = i4;
                    String str2 = str;
                    pointF3 = pointF5;
                    i = i5;
                    if (element3.getName().equals(obj)) {
                        Element element4 = element3.element("pt");
                        path.moveTo((((float) Integer.parseInt(element4.attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(element4.attributeValue("y"))) * 96.0f) / 914400.0f);
                    } else if (element3.getName().equals("lnTo")) {
                        Element element5 = element3.element("pt");
                        path.lineTo((((float) Integer.parseInt(element5.attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(element5.attributeValue("y"))) * 96.0f) / 914400.0f);
                    } else if (element3.getName().equals("quadBezTo")) {
                        List elements2 = element3.elements();
                        if (elements2.size() != 2) {
                            break;
                        }
                        path.quadTo((((float) Integer.parseInt(((Element) elements2.get(0)).attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements2.get(0)).attributeValue("y"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements2.get(1)).attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements2.get(1)).attributeValue("y"))) * 96.0f) / 914400.0f);
                    } else {
                        if (element3.getName().equals("cubicBezTo")) {
                            List elements3 = element3.elements();
                            if (elements3.size() != 3) {
                                break;
                            }
                            path.cubicTo((((float) Integer.parseInt(((Element) elements3.get(0)).attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements3.get(0)).attributeValue("y"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements3.get(1)).attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements3.get(1)).attributeValue("y"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements3.get(2)).attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements3.get(2)).attributeValue("y"))) * 96.0f) / 914400.0f);
                        } else if (element3.getName().equals("arcTo")) {
                            float parseInt = (((float) Integer.parseInt(element3.attributeValue("wR"))) * 96.0f) / 914400.0f;
                            float parseInt2 = (((float) Integer.parseInt(element3.attributeValue("hR"))) * 96.0f) / 914400.0f;
                            path.arcTo(new RectF((((float) rectangle.getCenterX()) - parseInt) - ((float) rectangle2.x), (((float) rectangle.getCenterY()) - parseInt2) - ((float) rectangle2.y), (((float) rectangle.getCenterX()) + parseInt) - ((float) rectangle2.x), (((float) rectangle.getCenterY()) + parseInt2) - ((float) rectangle2.y)), ((float) Integer.parseInt(element3.attributeValue("stAng"))) / 60000.0f, ((float) Integer.parseInt(element3.attributeValue(str2))) / 60000.0f);
                        } else if (element3.getName().equals("close")) {
                            path.close();
                        }
                        pointF4 = pointF6;
                        pointF5 = pointF3;
                        i5 = i + 1;
                        elements = list;
                        size = i3;
                        i4 = i2;
                    }
                } else {
                    i2 = i4;
                    if (element3.getName().equals("lnTo")) {
                        PointF referencedPosition = LineArrowPathBuilder.getReferencedPosition(element3.element("pt"), pointF5, arbitraryPolygonShape.getEndArrowType());
                        path.lineTo(referencedPosition.x, referencedPosition.y);
                        pointF4 = pointF6;
                        pointF5 = referencedPosition;
                    } else if (element3.getName().equals("quadBezTo")) {
                        List elements4 = element3.elements();
                        if (elements4.size() != 2) {
                            break;
                        }
                        PointF referencedPosition2 = LineArrowPathBuilder.getReferencedPosition((Element) elements4.get(1), pointF5, arbitraryPolygonShape.getEndArrowType());
                        path.quadTo((((float) Integer.parseInt(((Element) elements4.get(0)).attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements4.get(0)).attributeValue("y"))) * 96.0f) / 914400.0f, referencedPosition2.x, referencedPosition2.y);
                        pointF4 = pointF6;
                        pointF5 = referencedPosition2;
                    } else if (element3.getName().equals("cubicBezTo")) {
                        List elements5 = element3.elements();
                        if (elements5.size() != 3) {
                            break;
                        }
                        PointF referencedPosition3 = LineArrowPathBuilder.getReferencedPosition((Element) elements5.get(2), pointF5, arbitraryPolygonShape.getEndArrowType());
                        path.cubicTo((((float) Integer.parseInt(((Element) elements5.get(0)).attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements5.get(0)).attributeValue("y"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements5.get(1)).attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements5.get(1)).attributeValue("y"))) * 96.0f) / 914400.0f, referencedPosition3.x, referencedPosition3.y);
                        pointF4 = pointF6;
                        pointF5 = referencedPosition3;
                    } else {
                        if (element3.getName().equals("arcTo")) {
                            float parseInt3 = (((float) Integer.parseInt(element3.attributeValue("wR"))) * 96.0f) / 914400.0f;
                            float parseInt4 = (((float) Integer.parseInt(element3.attributeValue("hR"))) * 96.0f) / 914400.0f;
                            path.arcTo(new RectF((((float) rectangle.getCenterX()) - parseInt3) - ((float) rectangle2.x), (((float) rectangle.getCenterY()) - parseInt4) - ((float) rectangle2.y), (((float) rectangle.getCenterX()) + parseInt3) - ((float) rectangle2.x), (((float) rectangle.getCenterY()) + parseInt4) - ((float) rectangle2.y)), ((float) Integer.parseInt(element3.attributeValue("stAng"))) / 60000.0f, ((float) Integer.parseInt(element3.attributeValue(str))) / 60000.0f);
                        }
                        pointF3 = pointF5;
                        i = i5;
                    }
                }
                pointF4 = pointF6;
                pointF5 = pointF3;
                i5 = i + 1;
                elements = list;
                size = i3;
                i4 = i2;
            } else {
                PointF referencedPosition4 = LineArrowPathBuilder.getReferencedPosition(element3.element("pt"), pointF4, arbitraryPolygonShape.getStartArrowType());
                path.moveTo(referencedPosition4.x, referencedPosition4.y);
                pointF4 = referencedPosition4;
                list = elements;
                i3 = size;
                i2 = i4;
            }
            i = i5;
            i5 = i + 1;
            elements = list;
            size = i3;
            i4 = i2;
        }
        return path;
    }

    public static ArrowPathAndTail getPathHeadArrowPath(Arrow arrow, int i, Element element) {
        List elements = element.elements();
        if (elements == null || elements.size() < 2) {
            return null;
        }
        Element element2 = ((Element) elements.get(0)).element("pt");
        float parseInt = (((float) Integer.parseInt(element2.attributeValue("x"))) * 96.0f) / 914400.0f;
        float parseInt2 = (((float) Integer.parseInt(element2.attributeValue("y"))) * 96.0f) / 914400.0f;
        Element element3 = (Element) elements.get(1);
        if (element3.getName().equals("lnTo")) {
            Element element4 = element3.element("pt");
            return LineArrowPathBuilder.getDirectLineArrowPath((((float) Integer.parseInt(element4.attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(element4.attributeValue("y"))) * 96.0f) / 914400.0f, parseInt, parseInt2, arrow, i);
        } else if (element3.getName().equals("quadBezTo")) {
            List elements2 = element3.elements();
            if (elements2.size() != 2) {
                return null;
            }
            return LineArrowPathBuilder.getQuadBezArrowPath((((float) Integer.parseInt(((Element) elements2.get(1)).attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements2.get(1)).attributeValue("y"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements2.get(0)).attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements2.get(0)).attributeValue("y"))) * 96.0f) / 914400.0f, parseInt, parseInt2, arrow, i);
        } else if (!element3.getName().equals("cubicBezTo")) {
            return null;
        } else {
            List elements3 = element3.elements();
            if (elements3.size() != 3) {
                return null;
            }
            float parseInt3 = (((float) Integer.parseInt(((Element) elements3.get(0)).attributeValue("x"))) * 96.0f) / 914400.0f;
            float parseInt4 = (((float) Integer.parseInt(((Element) elements3.get(0)).attributeValue("y"))) * 96.0f) / 914400.0f;
            return LineArrowPathBuilder.getCubicBezArrowPath((((float) Integer.parseInt(((Element) elements3.get(2)).attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements3.get(2)).attributeValue("y"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements3.get(1)).attributeValue("x"))) * 96.0f) / 914400.0f, (((float) Integer.parseInt(((Element) elements3.get(1)).attributeValue("y"))) * 96.0f) / 914400.0f, parseInt3, parseInt4, parseInt, parseInt2, arrow, i);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0115  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail getPathTailArrowPath(com.app.office.common.shape.Arrow r27, int r28, com.app.office.fc.dom4j.Element r29) {
        /*
            java.util.List r0 = r29.elements()
            r1 = 0
            if (r0 == 0) goto L_0x020e
            int r2 = r0.size()
            r3 = 2
            if (r2 < r3) goto L_0x020e
            int r4 = r2 + -1
            java.lang.Object r5 = r0.get(r4)
            com.app.office.fc.dom4j.Element r5 = (com.app.office.fc.dom4j.Element) r5
            java.lang.String r5 = r5.getName()
            java.lang.String r6 = "close"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0024
            goto L_0x020e
        L_0x0024:
            int r2 = r2 - r3
            java.lang.Object r2 = r0.get(r2)
            com.app.office.fc.dom4j.Element r2 = (com.app.office.fc.dom4j.Element) r2
            java.lang.String r5 = r2.getName()
            java.lang.String r6 = "lnTo"
            boolean r5 = r5.equals(r6)
            r7 = 3
            java.lang.String r8 = "cubicBezTo"
            java.lang.String r9 = "pt"
            java.lang.String r10 = "quadBezTo"
            r11 = 0
            r12 = 1
            java.lang.String r13 = "y"
            java.lang.String r14 = "x"
            r15 = 1230978560(0x495f3e00, float:914400.0)
            r16 = 1119879168(0x42c00000, float:96.0)
            if (r5 == 0) goto L_0x006b
            com.app.office.fc.dom4j.Element r2 = r2.element((java.lang.String) r9)
            java.lang.String r5 = r2.attributeValue((java.lang.String) r14)
            int r5 = java.lang.Integer.parseInt(r5)
            float r5 = (float) r5
            float r5 = r5 * r16
            float r11 = r5 / r15
            java.lang.String r2 = r2.attributeValue((java.lang.String) r13)
            int r2 = java.lang.Integer.parseInt(r2)
        L_0x0062:
            float r2 = (float) r2
            float r2 = r2 * r16
            float r2 = r2 / r15
        L_0x0066:
            r18 = r2
            r17 = r11
            goto L_0x00dd
        L_0x006b:
            java.lang.String r5 = r2.getName()
            boolean r5 = r5.equals(r10)
            if (r5 == 0) goto L_0x00a3
            java.util.List r2 = r2.elements()
            int r5 = r2.size()
            if (r5 != r3) goto L_0x00a1
            java.lang.Object r5 = r2.get(r12)
            com.app.office.fc.dom4j.Element r5 = (com.app.office.fc.dom4j.Element) r5
            java.lang.String r5 = r5.attributeValue((java.lang.String) r14)
            int r5 = java.lang.Integer.parseInt(r5)
            float r5 = (float) r5
            float r5 = r5 * r16
            float r11 = r5 / r15
            java.lang.Object r2 = r2.get(r12)
            com.app.office.fc.dom4j.Element r2 = (com.app.office.fc.dom4j.Element) r2
            java.lang.String r2 = r2.attributeValue((java.lang.String) r13)
            int r2 = java.lang.Integer.parseInt(r2)
            goto L_0x0062
        L_0x00a1:
            r2 = 0
            goto L_0x0066
        L_0x00a3:
            java.lang.String r5 = r2.getName()
            boolean r5 = r5.equals(r8)
            if (r5 == 0) goto L_0x00d9
            java.util.List r2 = r2.elements()
            int r5 = r2.size()
            if (r5 != r7) goto L_0x00d9
            java.lang.Object r5 = r2.get(r3)
            com.app.office.fc.dom4j.Element r5 = (com.app.office.fc.dom4j.Element) r5
            java.lang.String r5 = r5.attributeValue((java.lang.String) r14)
            int r5 = java.lang.Integer.parseInt(r5)
            float r5 = (float) r5
            float r5 = r5 * r16
            float r11 = r5 / r15
            java.lang.Object r2 = r2.get(r3)
            com.app.office.fc.dom4j.Element r2 = (com.app.office.fc.dom4j.Element) r2
            java.lang.String r2 = r2.attributeValue((java.lang.String) r13)
            int r2 = java.lang.Integer.parseInt(r2)
            goto L_0x0062
        L_0x00d9:
            r17 = 0
            r18 = 0
        L_0x00dd:
            java.lang.Object r0 = r0.get(r4)
            com.app.office.fc.dom4j.Element r0 = (com.app.office.fc.dom4j.Element) r0
            java.lang.String r2 = r0.getName()
            boolean r2 = r2.equals(r6)
            if (r2 == 0) goto L_0x0115
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r9)
            java.lang.String r1 = r0.attributeValue((java.lang.String) r14)
            int r1 = java.lang.Integer.parseInt(r1)
            float r1 = (float) r1
            float r1 = r1 * r16
            float r19 = r1 / r15
            java.lang.String r0 = r0.attributeValue((java.lang.String) r13)
            int r0 = java.lang.Integer.parseInt(r0)
            float r0 = (float) r0
            float r0 = r0 * r16
            float r20 = r0 / r15
            r21 = r27
            r22 = r28
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r1 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getDirectLineArrowPath(r17, r18, r19, r20, r21, r22)
            goto L_0x020e
        L_0x0115:
            java.lang.String r2 = r0.getName()
            boolean r2 = r2.equals(r10)
            r4 = 0
            if (r2 == 0) goto L_0x0180
            java.util.List r0 = r0.elements()
            int r2 = r0.size()
            if (r2 != r3) goto L_0x020e
            java.lang.Object r1 = r0.get(r4)
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            java.lang.String r1 = r1.attributeValue((java.lang.String) r14)
            int r1 = java.lang.Integer.parseInt(r1)
            float r1 = (float) r1
            float r1 = r1 * r16
            float r19 = r1 / r15
            java.lang.Object r1 = r0.get(r4)
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            java.lang.String r1 = r1.attributeValue((java.lang.String) r13)
            int r1 = java.lang.Integer.parseInt(r1)
            float r1 = (float) r1
            float r1 = r1 * r16
            float r20 = r1 / r15
            java.lang.Object r1 = r0.get(r12)
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            java.lang.String r1 = r1.attributeValue((java.lang.String) r14)
            int r1 = java.lang.Integer.parseInt(r1)
            float r1 = (float) r1
            float r1 = r1 * r16
            float r21 = r1 / r15
            java.lang.Object r0 = r0.get(r12)
            com.app.office.fc.dom4j.Element r0 = (com.app.office.fc.dom4j.Element) r0
            java.lang.String r0 = r0.attributeValue((java.lang.String) r13)
            int r0 = java.lang.Integer.parseInt(r0)
            float r0 = (float) r0
            float r0 = r0 * r16
            float r22 = r0 / r15
            r23 = r27
            r24 = r28
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r1 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getQuadBezArrowPath(r17, r18, r19, r20, r21, r22, r23, r24)
            goto L_0x020e
        L_0x0180:
            java.lang.String r2 = r0.getName()
            boolean r2 = r2.equals(r8)
            if (r2 == 0) goto L_0x020e
            java.util.List r0 = r0.elements()
            int r2 = r0.size()
            if (r2 != r7) goto L_0x020e
            java.lang.Object r1 = r0.get(r4)
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            java.lang.String r1 = r1.attributeValue((java.lang.String) r14)
            int r1 = java.lang.Integer.parseInt(r1)
            float r1 = (float) r1
            float r1 = r1 * r16
            float r19 = r1 / r15
            java.lang.Object r1 = r0.get(r4)
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            java.lang.String r1 = r1.attributeValue((java.lang.String) r13)
            int r1 = java.lang.Integer.parseInt(r1)
            float r1 = (float) r1
            float r1 = r1 * r16
            float r20 = r1 / r15
            java.lang.Object r1 = r0.get(r12)
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            java.lang.String r1 = r1.attributeValue((java.lang.String) r14)
            int r1 = java.lang.Integer.parseInt(r1)
            float r1 = (float) r1
            float r1 = r1 * r16
            float r21 = r1 / r15
            java.lang.Object r1 = r0.get(r12)
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            java.lang.String r1 = r1.attributeValue((java.lang.String) r13)
            int r1 = java.lang.Integer.parseInt(r1)
            float r1 = (float) r1
            float r1 = r1 * r16
            float r22 = r1 / r15
            java.lang.Object r1 = r0.get(r3)
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            java.lang.String r1 = r1.attributeValue((java.lang.String) r14)
            int r1 = java.lang.Integer.parseInt(r1)
            float r1 = (float) r1
            float r1 = r1 * r16
            float r23 = r1 / r15
            java.lang.Object r0 = r0.get(r3)
            com.app.office.fc.dom4j.Element r0 = (com.app.office.fc.dom4j.Element) r0
            java.lang.String r0 = r0.attributeValue((java.lang.String) r13)
            int r0 = java.lang.Integer.parseInt(r0)
            float r0 = (float) r0
            float r0 = r0 * r16
            float r24 = r0 / r15
            r25 = r27
            r26 = r28
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r1 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getCubicBezArrowPath(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26)
        L_0x020e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.ArbitraryPolygonShapePath.getPathTailArrowPath(com.app.office.common.shape.Arrow, int, com.app.office.fc.dom4j.Element):com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail");
    }

    public static int getArrowSize(String str) {
        if (str != null && !str.equals("med")) {
            if (str.equals("sm")) {
                return 0;
            }
            if (str.equals("lg")) {
                return 2;
            }
        }
        return 1;
    }
}
