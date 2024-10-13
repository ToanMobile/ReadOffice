package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherArrayProperty;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherTertiaryOptRecord;
import com.app.office.fc.util.LittleEndian;
import com.app.office.java.awt.Rectangle;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class Table extends ShapeGroup {
    protected static final int BORDERS_ALL = 5;
    protected static final int BORDERS_INSIDE = 7;
    protected static final int BORDERS_NONE = 8;
    protected static final int BORDERS_OUTSIDE = 6;
    protected static final int BORDER_BOTTOM = 3;
    protected static final int BORDER_LEFT = 4;
    protected static final int BORDER_RIGHT = 2;
    protected static final int BORDER_TOP = 1;
    protected Line[] borders;
    protected TableCell[][] cells;

    public Table(int i, int i2) {
        if (i < 1) {
            throw new IllegalArgumentException("The number of rows must be greater than 1");
        } else if (i2 >= 1) {
            int[] iArr = new int[2];
            iArr[1] = i2;
            iArr[0] = i;
            this.cells = (TableCell[][]) Array.newInstance(TableCell.class, iArr);
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < this.cells.length; i5++) {
                i3 = 0;
                int i6 = 0;
                while (true) {
                    TableCell[][] tableCellArr = this.cells;
                    if (i6 >= tableCellArr[i5].length) {
                        break;
                    }
                    tableCellArr[i5][i6] = new TableCell(this);
                    this.cells[i5][i6].setAnchor(new Rectangle(i3, i4, 100, 40));
                    i3 += 100;
                    i6++;
                }
                i4 += 40;
            }
            setAnchor(new Rectangle(0, 0, i3, i4));
            EscherContainerRecord escherContainerRecord = (EscherContainerRecord) getSpContainer().getChild(0);
            EscherOptRecord escherOptRecord = new EscherOptRecord();
            escherOptRecord.setRecordId(EscherTertiaryOptRecord.RECORD_ID);
            escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.GROUPSHAPE__TABLEPROPERTIES, 1));
            EscherArrayProperty escherArrayProperty = new EscherArrayProperty(17312, false, (byte[]) null);
            escherArrayProperty.setSizeOfElements(4);
            escherArrayProperty.setNumberOfElementsInArray(i);
            escherArrayProperty.setNumberOfElementsInMemory(i);
            escherOptRecord.addEscherProperty(escherArrayProperty);
            List<EscherRecord> childRecords = escherContainerRecord.getChildRecords();
            childRecords.add(childRecords.size() - 1, escherOptRecord);
            escherContainerRecord.setChildRecords(childRecords);
        } else {
            throw new IllegalArgumentException("The number of columns must be greater than 1");
        }
    }

    public Table(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    public TableCell getCell(int i, int i2) {
        return this.cells[i][i2];
    }

    public int getNumberOfColumns() {
        return this.cells[0].length;
    }

    public int getNumberOfRows() {
        return this.cells.length;
    }

    /* access modifiers changed from: protected */
    public void afterInsert(Sheet sheet) {
        super.afterInsert(sheet);
        List<EscherRecord> childRecords = ((EscherContainerRecord) getSpContainer().getChild(0)).getChildRecords();
        EscherArrayProperty escherArrayProperty = (EscherArrayProperty) ((EscherOptRecord) childRecords.get(childRecords.size() - 2)).getEscherProperty(1);
        int i = 0;
        while (true) {
            TableCell[][] tableCellArr = this.cells;
            if (i < tableCellArr.length) {
                byte[] bArr = new byte[4];
                LittleEndian.putInt(bArr, (int) (((float) (tableCellArr[i][0].getAnchor().height * ShapeKit.MASTER_DPI)) / 72.0f));
                escherArrayProperty.setElement(i, bArr);
                int i2 = 0;
                while (true) {
                    TableCell[][] tableCellArr2 = this.cells;
                    if (i2 >= tableCellArr2[i].length) {
                        break;
                    }
                    TableCell tableCell = tableCellArr2[i][i2];
                    addShape(tableCell);
                    Line borderTop = tableCell.getBorderTop();
                    if (borderTop != null) {
                        addShape(borderTop);
                    }
                    Line borderRight = tableCell.getBorderRight();
                    if (borderRight != null) {
                        addShape(borderRight);
                    }
                    Line borderBottom = tableCell.getBorderBottom();
                    if (borderBottom != null) {
                        addShape(borderBottom);
                    }
                    Line borderLeft = tableCell.getBorderLeft();
                    if (borderLeft != null) {
                        addShape(borderLeft);
                    }
                    i2++;
                }
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initTable() {
        Shape[] shapes = getShapes();
        Arrays.sort(shapes, new Comparator() {
            public int compare(Object obj, Object obj2) {
                Rectangle anchor = ((Shape) obj).getAnchor();
                Rectangle anchor2 = ((Shape) obj2).getAnchor();
                int i = anchor.y - anchor2.y;
                return i == 0 ? anchor.x - anchor2.x : i;
            }
        });
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i = -1;
        ArrayList arrayList3 = null;
        int i2 = 0;
        for (int i3 = 0; i3 < shapes.length; i3++) {
            if (shapes[i3] instanceof TextShape) {
                Rectangle anchor = shapes[i3].getAnchor();
                if (anchor.y != i) {
                    i = anchor.y;
                    arrayList3 = new ArrayList();
                    arrayList.add(arrayList3);
                }
                arrayList3.add(shapes[i3]);
                i2 = Math.max(i2, arrayList3.size());
            } else if (shapes[i3] instanceof Line) {
                arrayList2.add(shapes[i3]);
            }
        }
        int size = arrayList.size();
        int[] iArr = new int[2];
        iArr[1] = i2;
        iArr[0] = size;
        this.cells = (TableCell[][]) Array.newInstance(TableCell.class, iArr);
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            ArrayList arrayList4 = (ArrayList) arrayList.get(i4);
            for (int i5 = 0; i5 < arrayList4.size(); i5++) {
                TextShape textShape = (TextShape) arrayList4.get(i5);
                this.cells[i4][i5] = new TableCell(textShape.getSpContainer(), getParent());
                this.cells[i4][i5].setSheet(textShape.getSheet());
            }
        }
        this.borders = new Line[arrayList2.size()];
        for (int i6 = 0; i6 < arrayList2.size(); i6++) {
            this.borders[i6] = (Line) arrayList2.get(i6);
        }
    }

    public Line[] getTableBorders() {
        return this.borders;
    }

    public void setSheet(Sheet sheet) {
        super.setSheet(sheet);
        if (this.cells == null) {
            initTable();
        }
    }

    public void setRowHeight(int i, int i2) {
        int i3 = i2 - this.cells[i][0].getAnchor().height;
        for (int i4 = i; i4 < this.cells.length; i4++) {
            int i5 = 0;
            while (true) {
                TableCell[][] tableCellArr = this.cells;
                if (i5 >= tableCellArr[i4].length) {
                    break;
                }
                Rectangle anchor = tableCellArr[i4][i5].getAnchor();
                if (i4 == i) {
                    anchor.height = i2;
                } else {
                    anchor.y += i3;
                }
                this.cells[i4][i5].setAnchor(anchor);
                i5++;
            }
        }
        Rectangle anchor2 = getAnchor();
        anchor2.height += i3;
        setAnchor(anchor2);
    }

    public void setColumnWidth(int i, int i2) {
        int i3 = 0;
        int i4 = i2 - this.cells[0][i].getAnchor().width;
        while (true) {
            TableCell[][] tableCellArr = this.cells;
            if (i3 < tableCellArr.length) {
                Rectangle anchor = tableCellArr[i3][i].getAnchor();
                anchor.width = i2;
                this.cells[i3][i].setAnchor(anchor);
                if (i < this.cells[i3].length - 1) {
                    int i5 = i + 1;
                    while (true) {
                        TableCell[][] tableCellArr2 = this.cells;
                        if (i5 >= tableCellArr2[i3].length) {
                            break;
                        }
                        Rectangle anchor2 = tableCellArr2[i3][i5].getAnchor();
                        anchor2.x += i4;
                        this.cells[i3][i5].setAnchor(anchor2);
                        i5++;
                    }
                }
                i3++;
            } else {
                Rectangle anchor3 = getAnchor();
                anchor3.width += i4;
                setAnchor(anchor3);
                return;
            }
        }
    }

    public void setAllBorders(Line line) {
        for (int i = 0; i < this.cells.length; i++) {
            int i2 = 0;
            while (true) {
                TableCell[][] tableCellArr = this.cells;
                if (i2 >= tableCellArr[i].length) {
                    break;
                }
                TableCell tableCell = tableCellArr[i][i2];
                tableCell.setBorderTop(cloneBorder(line));
                tableCell.setBorderLeft(cloneBorder(line));
                if (i2 == this.cells[i].length - 1) {
                    tableCell.setBorderRight(cloneBorder(line));
                }
                if (i == this.cells.length - 1) {
                    tableCell.setBorderBottom(cloneBorder(line));
                }
                i2++;
            }
        }
    }

    public void setOutsideBorders(Line line) {
        for (int i = 0; i < this.cells.length; i++) {
            int i2 = 0;
            while (true) {
                TableCell[][] tableCellArr = this.cells;
                if (i2 >= tableCellArr[i].length) {
                    break;
                }
                TableCell tableCell = tableCellArr[i][i2];
                if (i2 == 0) {
                    tableCell.setBorderLeft(cloneBorder(line));
                }
                if (i2 == this.cells[i].length - 1) {
                    tableCell.setBorderRight(cloneBorder(line));
                } else {
                    tableCell.setBorderLeft((Line) null);
                    tableCell.setBorderLeft((Line) null);
                }
                if (i == 0) {
                    tableCell.setBorderTop(cloneBorder(line));
                } else if (i == this.cells.length - 1) {
                    tableCell.setBorderBottom(cloneBorder(line));
                } else {
                    tableCell.setBorderTop((Line) null);
                    tableCell.setBorderBottom((Line) null);
                }
                i2++;
            }
        }
    }

    public void setInsideBorders(Line line) {
        for (int i = 0; i < this.cells.length; i++) {
            int i2 = 0;
            while (true) {
                TableCell[][] tableCellArr = this.cells;
                if (i2 >= tableCellArr[i].length) {
                    break;
                }
                TableCell tableCell = tableCellArr[i][i2];
                if (i2 != tableCellArr[i].length - 1) {
                    tableCell.setBorderRight(cloneBorder(line));
                } else {
                    tableCell.setBorderLeft((Line) null);
                    tableCell.setBorderLeft((Line) null);
                }
                if (i != this.cells.length - 1) {
                    tableCell.setBorderBottom(cloneBorder(line));
                } else {
                    tableCell.setBorderTop((Line) null);
                    tableCell.setBorderBottom((Line) null);
                }
                i2++;
            }
        }
    }

    private Line cloneBorder(Line line) {
        Line createBorder = createBorder();
        createBorder.setLineWidth(line.getLineWidth());
        createBorder.setLineStyle(line.getLineStyle());
        createBorder.setLineDashing(line.getLineDashing());
        createBorder.setLineColor(line.getLineColor());
        return createBorder;
    }

    public Line createBorder() {
        Line line = new Line(this);
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(line.getSpContainer(), -4085);
        setEscherProperty(escherOptRecord, EscherProperties.GEOMETRY__SHAPEPATH, -1);
        setEscherProperty(escherOptRecord, EscherProperties.GEOMETRY__FILLOK, -1);
        setEscherProperty(escherOptRecord, EscherProperties.SHADOWSTYLE__SHADOWOBSURED, 131072);
        setEscherProperty(escherOptRecord, EscherProperties.THREED__LIGHTFACE, 524288);
        return line;
    }
}
