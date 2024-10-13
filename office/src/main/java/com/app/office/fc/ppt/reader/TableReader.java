package com.app.office.fc.ppt.reader;

import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import com.app.office.common.shape.TableCell;
import com.app.office.common.shape.TableShape;
import com.app.office.common.shape.TextBox;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.Rectanglef;
import com.app.office.pg.model.PGMaster;
import com.app.office.pg.model.tableStyle.TableCellBorders;
import com.app.office.pg.model.tableStyle.TableCellStyle;
import com.app.office.pg.model.tableStyle.TableStyle;
import com.app.office.system.IControl;
import java.util.List;

public class TableReader {
    public static final int DEFAULT_CELL_HEIGHT = 40;
    public static final int DEFAULT_CELL_WIDTH = 100;
    private static TableReader kit = new TableReader();

    public static TableReader instance() {
        return kit;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: com.app.office.common.shape.TableShape} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: com.app.office.common.shape.TableShape} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: com.app.office.common.shape.TableShape} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v1, resolved type: com.app.office.pg.model.tableStyle.TableStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: com.app.office.common.shape.TableShape} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: com.app.office.common.shape.TableShape} */
    /* JADX WARNING: type inference failed for: r2v6, types: [com.app.office.pg.model.tableStyle.TableStyle] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.common.shape.TableShape getTable(com.app.office.system.IControl r17, com.app.office.fc.openxml4j.opc.ZipPackage r18, com.app.office.fc.openxml4j.opc.PackagePart r19, com.app.office.pg.model.PGModel r20, com.app.office.pg.model.PGMaster r21, com.app.office.fc.dom4j.Element r22, com.app.office.java.awt.Rectangle r23) throws java.lang.Exception {
        /*
            r16 = this;
            r0 = r22
            com.app.office.fc.ppt.attribute.RunAttr r1 = com.app.office.fc.ppt.attribute.RunAttr.instance()
            r2 = 1
            r1.setTable(r2)
            java.lang.String r1 = "tblGrid"
            com.app.office.fc.dom4j.Element r1 = r0.element((java.lang.String) r1)
            r2 = 0
            r3 = 0
            if (r1 == 0) goto L_0x0111
            java.lang.String r4 = "gridCol"
            java.util.List r1 = r1.elements((java.lang.String) r4)
            int r4 = r1.size()
            int[] r13 = new int[r4]
            java.util.Iterator r1 = r1.iterator()
            r5 = 0
        L_0x0025:
            boolean r6 = r1.hasNext()
            r7 = 1230978560(0x495f3e00, float:914400.0)
            r8 = 1119879168(0x42c00000, float:96.0)
            if (r6 == 0) goto L_0x0055
            java.lang.Object r6 = r1.next()
            com.app.office.fc.dom4j.Element r6 = (com.app.office.fc.dom4j.Element) r6
            java.lang.String r9 = "w"
            java.lang.String r6 = r6.attributeValue((java.lang.String) r9)
            int r6 = java.lang.Integer.parseInt(r6)
            float r6 = (float) r6
            float r6 = r6 * r8
            float r6 = r6 / r7
            int r6 = (int) r6
            if (r6 <= 0) goto L_0x004d
            int r7 = r5 + 1
            r13[r5] = r6
            r5 = r7
            goto L_0x0025
        L_0x004d:
            int r6 = r5 + 1
            r7 = 133(0x85, float:1.86E-43)
            r13[r5] = r7
            r5 = r6
            goto L_0x0025
        L_0x0055:
            java.lang.String r1 = "tr"
            java.util.List r10 = r0.elements((java.lang.String) r1)
            int r1 = r10.size()
            int[] r14 = new int[r1]
            java.util.Iterator r5 = r10.iterator()
            r6 = 0
        L_0x0066:
            boolean r9 = r5.hasNext()
            if (r9 == 0) goto L_0x0091
            java.lang.Object r9 = r5.next()
            com.app.office.fc.dom4j.Element r9 = (com.app.office.fc.dom4j.Element) r9
            java.lang.String r11 = "h"
            java.lang.String r9 = r9.attributeValue((java.lang.String) r11)
            int r9 = java.lang.Integer.parseInt(r9)
            float r9 = (float) r9
            float r9 = r9 * r8
            float r9 = r9 / r7
            int r9 = (int) r9
            if (r9 <= 0) goto L_0x0089
            int r11 = r6 + 1
            r14[r6] = r9
            r6 = r11
            goto L_0x0066
        L_0x0089:
            int r9 = r6 + 1
            r11 = 53
            r14[r6] = r11
            r6 = r9
            goto L_0x0066
        L_0x0091:
            com.app.office.common.shape.TableShape r15 = new com.app.office.common.shape.TableShape
            r15.<init>(r1, r4)
            java.lang.String r1 = "tblPr"
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r1)
            java.lang.String r1 = "tableStyleId"
            com.app.office.fc.dom4j.Element r1 = r0.element((java.lang.String) r1)
            if (r1 == 0) goto L_0x00fe
            java.lang.String r1 = r1.getText()
            r2 = r20
            com.app.office.pg.model.tableStyle.TableStyle r2 = r2.getTableStyle(r1)
            java.lang.String r1 = "firstRow"
            java.lang.String r1 = r0.attributeValue((java.lang.String) r1)
            java.lang.String r4 = "1"
            boolean r1 = r4.equalsIgnoreCase(r1)
            r15.setFirstRow(r1)
            java.lang.String r1 = "lastRow"
            java.lang.String r1 = r0.attributeValue((java.lang.String) r1)
            boolean r1 = r4.equalsIgnoreCase(r1)
            r15.setLastRow(r1)
            java.lang.String r1 = "firstCol"
            java.lang.String r1 = r0.attributeValue((java.lang.String) r1)
            boolean r1 = r4.equalsIgnoreCase(r1)
            r15.setFirstCol(r1)
            java.lang.String r1 = "lastCol"
            java.lang.String r1 = r0.attributeValue((java.lang.String) r1)
            boolean r1 = r4.equalsIgnoreCase(r1)
            r15.setLastCol(r1)
            java.lang.String r1 = "bandRow"
            java.lang.String r1 = r0.attributeValue((java.lang.String) r1)
            boolean r1 = r4.equalsIgnoreCase(r1)
            r15.setBandRow(r1)
            java.lang.String r1 = "bandCol"
            java.lang.String r0 = r0.attributeValue((java.lang.String) r1)
            boolean r0 = r4.equalsIgnoreCase(r0)
            r15.setBandCol(r0)
        L_0x00fe:
            r5 = r16
            r6 = r17
            r7 = r18
            r8 = r19
            r9 = r21
            r11 = r23
            r12 = r15
            r0 = r15
            r15 = r2
            r5.processTable(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            r2 = r0
        L_0x0111:
            com.app.office.fc.ppt.attribute.RunAttr r0 = com.app.office.fc.ppt.attribute.RunAttr.instance()
            r0.setTable(r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.reader.TableReader.getTable(com.app.office.system.IControl, com.app.office.fc.openxml4j.opc.ZipPackage, com.app.office.fc.openxml4j.opc.PackagePart, com.app.office.pg.model.PGModel, com.app.office.pg.model.PGMaster, com.app.office.fc.dom4j.Element, com.app.office.java.awt.Rectangle):com.app.office.common.shape.TableShape");
    }

    private Line processLine(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, TableStyle tableStyle, Element element, int i) {
        BackgroundAndFill backgroundAndFill;
        boolean z = false;
        int i2 = 1;
        if (element != null) {
            try {
                if (element.element("noFill") == null) {
                    int round = element.attributeValue("w") != null ? Math.round((((float) Integer.parseInt(element.attributeValue("w"))) * 96.0f) / 914400.0f) : 1;
                    Element element2 = element.element("prstDash");
                    if (element2 != null && !"solid".equalsIgnoreCase(element2.attributeValue("val"))) {
                        z = true;
                    }
                    backgroundAndFill = BackgroundReader.instance().processBackground(iControl, zipPackage, packagePart, pGMaster, element);
                    i2 = round;
                    Line line = new Line();
                    line.setBackgroundAndFill(backgroundAndFill);
                    line.setLineWidth(i2);
                    line.setDash(z);
                    return line;
                }
            } catch (Exception unused) {
                return null;
            }
        }
        backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setForegroundColor(i);
        Line line2 = new Line();
        line2.setBackgroundAndFill(backgroundAndFill);
        line2.setLineWidth(i2);
        line2.setDash(z);
        return line2;
    }

    private void processTable(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, List<Element> list, Rectangle rectangle, TableShape tableShape, int[] iArr, int[] iArr2, TableStyle tableStyle) throws Exception {
        int i;
        Element element;
        Rectanglef rectanglef;
        Element element2;
        TableCell tableCell;
        Rectangle rectangle2 = rectangle;
        TableShape tableShape2 = tableShape;
        int[] iArr3 = iArr;
        TableStyle tableStyle2 = tableStyle;
        int i2 = 0;
        for (Element elements : list) {
            int i3 = 0;
            for (Element element3 : elements.elements("tc")) {
                if (element3.attribute("hMerge") == null && element3.attribute("vMerge") == null) {
                    TableCell tableCell2 = new TableCell();
                    Rectanglef rectanglef2 = new Rectanglef((float) rectangle2.x, (float) rectangle2.y, 0.0f, 0.0f);
                    for (int i4 = 0; i4 < i3; i4++) {
                        rectanglef2.setX(rectanglef2.getX() + ((float) iArr3[i4]));
                    }
                    for (int i5 = 0; i5 < i2; i5++) {
                        rectanglef2.setY(rectanglef2.getY() + ((float) iArr2[i5]));
                    }
                    int i6 = iArr3[i3];
                    int i7 = iArr2[i2];
                    if (element3.attribute("rowSpan") != null) {
                        int parseInt = Integer.parseInt(element3.attributeValue("rowSpan"));
                        for (int i8 = 1; i8 < parseInt; i8++) {
                            i7 += iArr2[i2 + i8];
                        }
                    }
                    if (element3.attribute("gridSpan") != null) {
                        int parseInt2 = Integer.parseInt(element3.attributeValue("gridSpan"));
                        for (int i9 = 1; i9 < parseInt2; i9++) {
                            i6 += iArr3[i3 + i9];
                        }
                    }
                    rectanglef2.setWidth((float) i6);
                    rectanglef2.setHeight((float) i7);
                    tableCell2.setBounds(rectanglef2);
                    TableCellStyle tableCellBorders = getTableCellBorders(tableStyle2, i2, i3, tableShape2);
                    Element element4 = element3.element("tcPr");
                    if (element4 != null) {
                        element2 = element4;
                        rectanglef = rectanglef2;
                        tableCell = tableCell2;
                        element = element3;
                        i = i3;
                        tableCell.setLeftLine(processLine(iControl, zipPackage, packagePart, pGMaster, tableStyle, element4.element("lnL"), getTableCellLeftBorderColor(zipPackage, packagePart, pGMaster, tableStyle, tableCellBorders)));
                        tableCell.setRightLine(processLine(iControl, zipPackage, packagePart, pGMaster, tableStyle, element2.element("lnR"), getTableCellRightBorderColor(zipPackage, packagePart, pGMaster, tableStyle, tableCellBorders)));
                        tableCell.setTopLine(processLine(iControl, zipPackage, packagePart, pGMaster, tableStyle, element2.element("lnT"), getTableCellTopBorderColor(zipPackage, packagePart, pGMaster, tableStyle, tableCellBorders)));
                        tableCell.setBottomLine(processLine(iControl, zipPackage, packagePart, pGMaster, tableStyle, element2.element("lnB"), getTableCellBottomBorderColor(zipPackage, packagePart, pGMaster, tableStyle, tableCellBorders)));
                    } else {
                        element2 = element4;
                        rectanglef = rectanglef2;
                        tableCell = tableCell2;
                        element = element3;
                        i = i3;
                        if (tableCellBorders != null) {
                            tableCell.setLeftLine(processLine(iControl, zipPackage, packagePart, pGMaster, tableStyle, (Element) null, getTableCellLeftBorderColor(zipPackage, packagePart, pGMaster, tableStyle, tableCellBorders)));
                            tableCell.setRightLine(processLine(iControl, zipPackage, packagePart, pGMaster, tableStyle, (Element) null, getTableCellRightBorderColor(zipPackage, packagePart, pGMaster, tableStyle, tableCellBorders)));
                            tableCell.setTopLine(processLine(iControl, zipPackage, packagePart, pGMaster, tableStyle, (Element) null, getTableCellTopBorderColor(zipPackage, packagePart, pGMaster, tableStyle, tableCellBorders)));
                            tableCell.setBottomLine(processLine(iControl, zipPackage, packagePart, pGMaster, tableStyle, (Element) null, getTableCellBottomBorderColor(zipPackage, packagePart, pGMaster, tableStyle, tableCellBorders)));
                        } else {
                            Line processLine = processLine(iControl, zipPackage, packagePart, pGMaster, tableStyle, (Element) null, -16777216);
                            tableCell.setLeftLine(processLine);
                            tableCell.setRightLine(processLine);
                            tableCell.setTopLine(processLine);
                            tableCell.setBottomLine(processLine);
                        }
                    }
                    BackgroundAndFill processBackground = BackgroundReader.instance().processBackground(iControl, zipPackage, packagePart, pGMaster, element2);
                    if (processBackground == null && tableCellBorders != null) {
                        processBackground = getTableCellFill(iControl, zipPackage, packagePart, pGMaster, tableStyle, tableCellBorders);
                    }
                    tableCell.setBackgroundAndFill(processBackground);
                    TextBox textBox = new TextBox();
                    Rectangle rectangle3 = new Rectangle((int) rectanglef.getX(), (int) rectanglef.getY(), (int) rectanglef.getWidth(), (int) rectanglef.getHeight());
                    textBox.setBounds(rectangle3);
                    processCellSection(iControl, pGMaster, textBox, rectangle3, element, (tableStyle2 == null || !(tableCellBorders == null || tableCellBorders.getFontAttributeSet() == null)) ? tableCellBorders : tableStyle.getWholeTable());
                    tableCell.setText(textBox);
                    tableShape2.addCell((iArr3.length * i2) + i, tableCell);
                } else {
                    i = i3;
                }
                i3 = i + 1;
                rectangle2 = rectangle;
            }
            i2++;
            rectangle2 = rectangle;
        }
    }

    private BackgroundAndFill getTableCellFill(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, TableStyle tableStyle, TableCellStyle tableCellStyle) {
        try {
            Element tableCellBgFill = tableCellStyle.getTableCellBgFill();
            if (tableCellBgFill == null) {
                tableCellBgFill = tableStyle.getWholeTable().getTableCellBgFill();
            }
            return BackgroundReader.instance().processBackground(iControl, zipPackage, packagePart, pGMaster, tableCellBgFill, true);
        } catch (Exception unused) {
            return null;
        }
    }

    private int getTableCellLeftBorderColor(ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, TableStyle tableStyle, TableCellStyle tableCellStyle) {
        Element element;
        Element leftBorder;
        try {
            TableCellBorders tableCellBorders = tableCellStyle.getTableCellBorders();
            if (tableCellBorders == null) {
                leftBorder = tableStyle.getWholeTable().getTableCellBorders().getLeftBorder();
            } else {
                Element leftBorder2 = tableCellBorders.getLeftBorder();
                if (leftBorder2 == null) {
                    leftBorder = tableStyle.getWholeTable().getTableCellBorders().getLeftBorder();
                } else {
                    element = leftBorder2;
                    return BackgroundReader.instance().getBackgroundColor(zipPackage, packagePart, pGMaster, element, true);
                }
            }
            element = leftBorder;
            return BackgroundReader.instance().getBackgroundColor(zipPackage, packagePart, pGMaster, element, true);
        } catch (Exception unused) {
            return -16777216;
        }
    }

    private int getTableCellRightBorderColor(ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, TableStyle tableStyle, TableCellStyle tableCellStyle) {
        Element element;
        Element rightBorder;
        try {
            TableCellBorders tableCellBorders = tableCellStyle.getTableCellBorders();
            if (tableCellBorders == null) {
                rightBorder = tableStyle.getWholeTable().getTableCellBorders().getRightBorder();
            } else {
                Element rightBorder2 = tableCellBorders.getRightBorder();
                if (rightBorder2 == null) {
                    rightBorder = tableStyle.getWholeTable().getTableCellBorders().getRightBorder();
                } else {
                    element = rightBorder2;
                    return BackgroundReader.instance().getBackgroundColor(zipPackage, packagePart, pGMaster, element, true);
                }
            }
            element = rightBorder;
            return BackgroundReader.instance().getBackgroundColor(zipPackage, packagePart, pGMaster, element, true);
        } catch (Exception unused) {
            return -16777216;
        }
    }

    private int getTableCellTopBorderColor(ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, TableStyle tableStyle, TableCellStyle tableCellStyle) {
        Element element;
        Element topBorder;
        try {
            TableCellBorders tableCellBorders = tableCellStyle.getTableCellBorders();
            if (tableCellBorders == null) {
                topBorder = tableStyle.getWholeTable().getTableCellBorders().getTopBorder();
            } else {
                Element topBorder2 = tableCellBorders.getTopBorder();
                if (topBorder2 == null) {
                    topBorder = tableStyle.getWholeTable().getTableCellBorders().getTopBorder();
                } else {
                    element = topBorder2;
                    return BackgroundReader.instance().getBackgroundColor(zipPackage, packagePart, pGMaster, element, true);
                }
            }
            element = topBorder;
            return BackgroundReader.instance().getBackgroundColor(zipPackage, packagePart, pGMaster, element, true);
        } catch (Exception unused) {
            return -16777216;
        }
    }

    private int getTableCellBottomBorderColor(ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, TableStyle tableStyle, TableCellStyle tableCellStyle) {
        Element element;
        Element bottomBorder;
        try {
            TableCellBorders tableCellBorders = tableCellStyle.getTableCellBorders();
            if (tableCellBorders == null) {
                bottomBorder = tableStyle.getWholeTable().getTableCellBorders().getBottomBorder();
            } else {
                Element bottomBorder2 = tableCellBorders.getBottomBorder();
                if (bottomBorder2 == null) {
                    bottomBorder = tableStyle.getWholeTable().getTableCellBorders().getBottomBorder();
                } else {
                    element = bottomBorder2;
                    return BackgroundReader.instance().getBackgroundColor(zipPackage, packagePart, pGMaster, element, true);
                }
            }
            element = bottomBorder;
            return BackgroundReader.instance().getBackgroundColor(zipPackage, packagePart, pGMaster, element, true);
        } catch (Exception unused) {
            return -16777216;
        }
    }

    private TableCellStyle getTableCellBorders(TableStyle tableStyle, int i, int i2, TableShape tableShape) {
        if (tableStyle == null) {
            return null;
        }
        if (tableShape.isFirstRow() && tableShape.isFirstCol()) {
            return getTableCellBorders_FirstRowFirstColumn(tableStyle, i, i2, tableShape);
        }
        if (tableShape.isFirstRow() && !tableShape.isFirstCol()) {
            return getTableCellBorders_FirstRow(tableStyle, i, i2, tableShape);
        }
        if (!tableShape.isFirstRow() && tableShape.isFirstCol()) {
            return getTableCellBorders_FirstColumn(tableStyle, i, i2, tableShape);
        }
        if (tableShape.isFirstRow() || tableShape.isFirstCol()) {
            return null;
        }
        return getTableCellBorders_NotFirstRowFirstColumn(tableStyle, i, i2, tableShape);
    }

    private TableCellStyle getTableCellBorders_FirstRowFirstColumn(TableStyle tableStyle, int i, int i2, TableShape tableShape) {
        TableCellStyle tableCellStyle;
        if (i == 0) {
            tableCellStyle = tableStyle.getFirstRow();
        } else if (tableShape.isLastRow() && i == tableShape.getRowCount() - 1) {
            tableCellStyle = tableStyle.getLastRow();
        } else if (i2 == 0) {
            tableCellStyle = tableStyle.getFirstCol();
        } else if (!tableShape.isLastCol() || i2 != tableShape.getColumnCount() - 1) {
            if (tableShape.isBandRow()) {
                if (i % 2 != 0) {
                    tableCellStyle = tableStyle.getBand1H();
                } else if (tableShape.isBandCol() && i2 % 2 != 0) {
                    tableCellStyle = tableStyle.getBand1V();
                }
            } else if (tableShape.isBandCol() && i2 % 2 != 0) {
                tableCellStyle = tableStyle.getBand1V();
            }
            tableCellStyle = null;
        } else {
            tableCellStyle = tableStyle.getLastCol();
        }
        return tableCellStyle == null ? tableStyle.getWholeTable() : tableCellStyle;
    }

    private TableCellStyle getTableCellBorders_FirstRow(TableStyle tableStyle, int i, int i2, TableShape tableShape) {
        TableCellStyle tableCellStyle;
        if (i == 0) {
            tableCellStyle = tableStyle.getFirstRow();
        } else if (tableShape.isLastRow() && i == tableShape.getRowCount() - 1) {
            tableCellStyle = tableStyle.getLastRow();
        } else if (!tableShape.isLastCol() || i2 != tableShape.getColumnCount() - 1) {
            if (tableShape.isBandRow()) {
                if (i % 2 != 0) {
                    tableCellStyle = tableStyle.getBand1H();
                } else if (tableShape.isBandCol() && i2 % 2 == 0) {
                    tableCellStyle = tableStyle.getBand1V();
                }
            } else if (tableShape.isBandCol() && i2 % 2 == 0) {
                tableCellStyle = tableStyle.getBand1V();
            }
            tableCellStyle = null;
        } else {
            tableCellStyle = tableStyle.getLastCol();
        }
        return tableCellStyle == null ? tableStyle.getWholeTable() : tableCellStyle;
    }

    private TableCellStyle getTableCellBorders_FirstColumn(TableStyle tableStyle, int i, int i2, TableShape tableShape) {
        TableCellStyle tableCellStyle;
        if (tableShape.isLastRow() && i == tableShape.getRowCount() - 1) {
            tableCellStyle = tableStyle.getLastRow();
        } else if (i2 == 0) {
            tableCellStyle = tableStyle.getFirstCol();
        } else if (!tableShape.isLastCol() || i2 != tableShape.getColumnCount() - 1) {
            if (tableShape.isBandRow()) {
                if (i % 2 == 0) {
                    tableCellStyle = tableStyle.getBand1H();
                } else if (tableShape.isBandCol() && i2 % 2 != 0) {
                    tableCellStyle = tableStyle.getBand1V();
                }
            } else if (tableShape.isBandCol() && i2 % 2 != 0) {
                tableCellStyle = tableStyle.getBand1V();
            }
            tableCellStyle = null;
        } else {
            tableCellStyle = tableStyle.getLastCol();
        }
        return tableCellStyle == null ? tableStyle.getWholeTable() : tableCellStyle;
    }

    private TableCellStyle getTableCellBorders_NotFirstRowFirstColumn(TableStyle tableStyle, int i, int i2, TableShape tableShape) {
        TableCellStyle tableCellStyle;
        if (tableShape.isLastRow() && i == tableShape.getRowCount() - 1) {
            tableCellStyle = tableStyle.getLastRow();
        } else if (!tableShape.isLastCol() || i2 != tableShape.getColumnCount() - 1) {
            if (tableShape.isBandRow()) {
                if (i % 2 == 0) {
                    tableCellStyle = tableStyle.getBand1H();
                } else if (tableShape.isBandCol() && i2 % 2 == 0) {
                    tableCellStyle = tableStyle.getBand1V();
                }
            } else if (tableShape.isBandCol() && i2 % 2 == 0) {
                tableCellStyle = tableStyle.getBand1V();
            }
            tableCellStyle = null;
        } else {
            tableCellStyle = tableStyle.getLastCol();
        }
        return tableCellStyle == null ? tableStyle.getWholeTable() : tableCellStyle;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0112, code lost:
        if (r8.equals("dist") != false) goto L_0x00f7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processCellSection(com.app.office.system.IControl r18, com.app.office.pg.model.PGMaster r19, com.app.office.common.shape.TextBox r20, com.app.office.java.awt.Rectangle r21, com.app.office.fc.dom4j.Element r22, com.app.office.pg.model.tableStyle.TableCellStyle r23) {
        /*
            r17 = this;
            r0 = r20
            r1 = r21
            r2 = r22
            com.app.office.simpletext.model.SectionElement r6 = new com.app.office.simpletext.model.SectionElement
            r6.<init>()
            r3 = 0
            r6.setStartOffset(r3)
            r0.setElement(r6)
            com.app.office.simpletext.model.IAttributeSet r3 = r6.getAttribute()
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            int r5 = r1.width
            float r5 = (float) r5
            r13 = 1097859072(0x41700000, float:15.0)
            float r5 = r5 * r13
            int r5 = (int) r5
            r4.setPageWidth(r3, r5)
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r1.height
            float r1 = (float) r1
            float r1 = r1 * r13
            int r1 = (int) r1
            r4.setPageHeight(r3, r1)
            java.lang.String r1 = "txBody"
            com.app.office.fc.dom4j.Element r4 = r2.element((java.lang.String) r1)
            if (r4 == 0) goto L_0x015e
            com.app.office.fc.ppt.attribute.SectionAttr r7 = com.app.office.fc.ppt.attribute.SectionAttr.instance()
            java.lang.String r1 = "bodyPr"
            com.app.office.fc.dom4j.Element r8 = r4.element((java.lang.String) r1)
            r10 = 0
            r11 = 0
            r12 = 1
            r9 = r3
            r7.setSectionAttribute(r8, r9, r10, r11, r12)
            java.lang.String r5 = "tcPr"
            com.app.office.fc.dom4j.Element r2 = r2.element((java.lang.String) r5)
            r5 = 0
            r7 = 1
            if (r2 == 0) goto L_0x0133
            java.lang.String r8 = "marL"
            java.lang.String r9 = r2.attributeValue((java.lang.String) r8)
            r10 = 144(0x90, float:2.02E-43)
            r11 = 1230978560(0x495f3e00, float:914400.0)
            r12 = 1119879168(0x42c00000, float:96.0)
            if (r9 == 0) goto L_0x0075
            java.lang.String r8 = r2.attributeValue((java.lang.String) r8)
            int r8 = java.lang.Integer.parseInt(r8)
            float r8 = (float) r8
            float r8 = r8 * r12
            float r8 = r8 / r11
            float r8 = r8 * r13
            int r8 = (int) r8
            goto L_0x0077
        L_0x0075:
            r8 = 144(0x90, float:2.02E-43)
        L_0x0077:
            java.lang.String r9 = "marT"
            java.lang.String r14 = r2.attributeValue((java.lang.String) r9)
            r15 = 72
            if (r14 == 0) goto L_0x0091
            java.lang.String r9 = r2.attributeValue((java.lang.String) r9)
            int r9 = java.lang.Integer.parseInt(r9)
            float r9 = (float) r9
            float r9 = r9 * r12
            float r9 = r9 / r11
            float r9 = r9 * r13
            int r9 = (int) r9
            goto L_0x0093
        L_0x0091:
            r9 = 72
        L_0x0093:
            java.lang.String r14 = "marR"
            java.lang.String r16 = r2.attributeValue((java.lang.String) r14)
            if (r16 == 0) goto L_0x00aa
            java.lang.String r10 = r2.attributeValue((java.lang.String) r14)
            int r10 = java.lang.Integer.parseInt(r10)
            float r10 = (float) r10
            float r10 = r10 * r12
            float r10 = r10 / r11
            float r10 = r10 * r13
            int r10 = (int) r10
        L_0x00aa:
            java.lang.String r14 = "marB"
            java.lang.String r16 = r2.attributeValue((java.lang.String) r14)
            if (r16 == 0) goto L_0x00c1
            java.lang.String r14 = r2.attributeValue((java.lang.String) r14)
            int r14 = java.lang.Integer.parseInt(r14)
            float r14 = (float) r14
            float r14 = r14 * r12
            float r14 = r14 / r11
            float r14 = r14 * r13
            int r15 = (int) r14
        L_0x00c1:
            com.app.office.simpletext.model.AttrManage r11 = com.app.office.simpletext.model.AttrManage.instance()
            r11.setPageMarginTop(r3, r9)
            com.app.office.simpletext.model.AttrManage r9 = com.app.office.simpletext.model.AttrManage.instance()
            r9.setPageMarginBottom(r3, r15)
            com.app.office.simpletext.model.AttrManage r9 = com.app.office.simpletext.model.AttrManage.instance()
            r9.setPageMarginLeft(r3, r8)
            com.app.office.simpletext.model.AttrManage r8 = com.app.office.simpletext.model.AttrManage.instance()
            r8.setPageMarginRight(r3, r10)
            java.lang.String r8 = "anchor"
            java.lang.String r8 = r2.attributeValue((java.lang.String) r8)
            if (r8 == 0) goto L_0x011c
            java.lang.String r9 = "t"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x00ef
        L_0x00ed:
            r8 = 0
            goto L_0x0115
        L_0x00ef:
            java.lang.String r9 = "ctr"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x00f9
        L_0x00f7:
            r8 = 1
            goto L_0x0115
        L_0x00f9:
            java.lang.String r9 = "b"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x0103
            r8 = 2
            goto L_0x0115
        L_0x0103:
            java.lang.String r9 = "just"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x010c
            goto L_0x00f7
        L_0x010c:
            java.lang.String r9 = "dist"
            boolean r8 = r8.equals(r9)
            if (r8 == 0) goto L_0x00ed
            goto L_0x00f7
        L_0x0115:
            com.app.office.simpletext.model.AttrManage r9 = com.app.office.simpletext.model.AttrManage.instance()
            r9.setPageVerticalAlign(r3, r8)
        L_0x011c:
            java.lang.String r8 = "anchorCtr"
            java.lang.String r2 = r2.attributeValue((java.lang.String) r8)
            if (r2 == 0) goto L_0x0133
            java.lang.String r8 = "1"
            boolean r2 = r2.equals(r8)
            if (r2 == 0) goto L_0x0133
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            r2.setPageHorizontalAlign(r3, r7)
        L_0x0133:
            com.app.office.fc.dom4j.Element r1 = r4.element((java.lang.String) r1)
            if (r1 == 0) goto L_0x014d
            java.lang.String r2 = "wrap"
            java.lang.String r1 = r1.attributeValue((java.lang.String) r2)
            if (r1 == 0) goto L_0x0149
            java.lang.String r2 = "square"
            boolean r1 = r2.equalsIgnoreCase(r1)
            if (r1 == 0) goto L_0x014a
        L_0x0149:
            r5 = 1
        L_0x014a:
            r0.setWrapLine(r5)
        L_0x014d:
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r6
            r5 = r23
            int r0 = r0.processParagraph(r1, r2, r3, r4, r5)
            long r0 = (long) r0
            r6.setEndOffset(r0)
        L_0x015e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.reader.TableReader.processCellSection(com.app.office.system.IControl, com.app.office.pg.model.PGMaster, com.app.office.common.shape.TextBox, com.app.office.java.awt.Rectangle, com.app.office.fc.dom4j.Element, com.app.office.pg.model.tableStyle.TableCellStyle):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001a, code lost:
        r1 = (r1 = r1.element("normAutofit")).attributeValue("lnSpcReduction");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int processParagraph(com.app.office.system.IControl r19, com.app.office.pg.model.PGMaster r20, com.app.office.simpletext.model.SectionElement r21, com.app.office.fc.dom4j.Element r22, com.app.office.pg.model.tableStyle.TableCellStyle r23) {
        /*
            r18 = this;
            r0 = r22
            java.lang.String r1 = "bodyPr"
            com.app.office.fc.dom4j.Element r1 = r0.element((java.lang.String) r1)
            if (r1 == 0) goto L_0x002b
            java.lang.String r3 = "normAutofit"
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r3)
            if (r1 == 0) goto L_0x002b
            java.lang.String r3 = "lnSpcReduction"
            com.app.office.fc.dom4j.Attribute r4 = r1.attribute((java.lang.String) r3)
            if (r4 == 0) goto L_0x002b
            java.lang.String r1 = r1.attributeValue((java.lang.String) r3)
            if (r1 == 0) goto L_0x002b
            int r3 = r1.length()
            if (r3 <= 0) goto L_0x002b
            int r1 = java.lang.Integer.parseInt(r1)
            goto L_0x002c
        L_0x002b:
            r1 = 0
        L_0x002c:
            java.lang.String r3 = "p"
            java.util.List r0 = r0.elements((java.lang.String) r3)
            r13 = 0
            r14 = 0
        L_0x0034:
            int r3 = r0.size()
            if (r13 >= r3) goto L_0x00c8
            java.lang.Object r3 = r0.get(r13)
            r15 = r3
            com.app.office.fc.dom4j.Element r15 = (com.app.office.fc.dom4j.Element) r15
            com.app.office.simpletext.model.ParagraphElement r12 = new com.app.office.simpletext.model.ParagraphElement
            r12.<init>()
            long r3 = (long) r14
            r12.setStartOffset(r3)
            com.app.office.fc.ppt.attribute.ParaAttr r3 = com.app.office.fc.ppt.attribute.ParaAttr.instance()
            java.lang.String r11 = "pPr"
            com.app.office.fc.dom4j.Element r5 = r15.element((java.lang.String) r11)
            com.app.office.simpletext.model.IAttributeSet r6 = r12.getAttribute()
            r7 = 0
            r8 = -1
            r9 = -1
            r16 = 1
            r17 = 0
            r4 = r19
            r10 = r1
            r2 = r11
            r11 = r16
            r22 = r12
            r12 = r17
            r3.setParaAttribute(r4, r5, r6, r7, r8, r9, r10, r11, r12)
            r3 = 0
            if (r23 == 0) goto L_0x0073
            com.app.office.simpletext.model.IAttributeSet r3 = r23.getFontAttributeSet()
        L_0x0073:
            r7 = r3
            com.app.office.fc.ppt.attribute.RunAttr r3 = com.app.office.fc.ppt.attribute.RunAttr.instance()
            r9 = 100
            r10 = -1
            r4 = r20
            r5 = r22
            r6 = r15
            r8 = r14
            int r14 = r3.processRun(r4, r5, r6, r7, r8, r9, r10)
            com.app.office.fc.ppt.attribute.ParaAttr r3 = com.app.office.fc.ppt.attribute.ParaAttr.instance()
            com.app.office.fc.dom4j.Element r2 = r15.element((java.lang.String) r2)
            com.app.office.simpletext.model.IAttributeSet r4 = r22.getAttribute()
            r3.processParaWithPct(r2, r4)
            if (r13 != 0) goto L_0x00a3
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r3 = r22.getAttribute()
            r4 = 0
            r2.setParaBefore(r3, r4)
            goto L_0x00b7
        L_0x00a3:
            r4 = 0
            int r2 = r0.size()
            int r2 = r2 + -1
            if (r13 != r2) goto L_0x00b7
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r3 = r22.getAttribute()
            r2.setParaAfter(r3, r4)
        L_0x00b7:
            long r2 = (long) r14
            r5 = r22
            r5.setEndOffset(r2)
            r2 = 0
            r6 = r21
            r6.appendParagraph(r5, r2)
            int r13 = r13 + 1
            goto L_0x0034
        L_0x00c8:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.reader.TableReader.processParagraph(com.app.office.system.IControl, com.app.office.pg.model.PGMaster, com.app.office.simpletext.model.SectionElement, com.app.office.fc.dom4j.Element, com.app.office.pg.model.tableStyle.TableCellStyle):int");
    }
}
