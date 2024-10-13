package com.app.office.ss.model.XLSModel;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.core.net.MailTo;
import com.app.office.common.autoshape.ExtendPath;
import com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import com.app.office.common.hyperlink.Hyperlink;
import com.app.office.common.picture.Picture;
import com.app.office.common.pictureefftect.PictureEffectInfoFactory;
import com.app.office.common.shape.AChart;
import com.app.office.common.shape.ArbitraryPolygonShape;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.GroupShape;
import com.app.office.common.shape.IShape;
import com.app.office.common.shape.LineShape;
import com.app.office.common.shape.PictureShape;
import com.app.office.common.shape.TextBox;
import com.app.office.fc.hssf.model.InternalSheet;
import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.record.BlankRecord;
import com.app.office.fc.hssf.record.CellValueRecordInterface;
import com.app.office.fc.hssf.record.EscherAggregate;
import com.app.office.fc.hssf.record.HyperlinkRecord;
import com.app.office.fc.hssf.record.RecordBase;
import com.app.office.fc.hssf.record.RowRecord;
import com.app.office.fc.hssf.usermodel.HSSFAutoShape;
import com.app.office.fc.hssf.usermodel.HSSFChart;
import com.app.office.fc.hssf.usermodel.HSSFChildAnchor;
import com.app.office.fc.hssf.usermodel.HSSFClientAnchor;
import com.app.office.fc.hssf.usermodel.HSSFFreeform;
import com.app.office.fc.hssf.usermodel.HSSFLine;
import com.app.office.fc.hssf.usermodel.HSSFPatriarch;
import com.app.office.fc.hssf.usermodel.HSSFPicture;
import com.app.office.fc.hssf.usermodel.HSSFPictureData;
import com.app.office.fc.hssf.usermodel.HSSFRichTextString;
import com.app.office.fc.hssf.usermodel.HSSFShape;
import com.app.office.fc.hssf.usermodel.HSSFShapeGroup;
import com.app.office.fc.hssf.usermodel.HSSFTextbox;
import com.app.office.fc.hssf.util.ColumnInfo;
import com.app.office.fc.hssf.util.HSSFPaneInformation;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import com.app.office.fc.xls.ChartConverter;
import com.app.office.java.awt.Rectangle;
import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.drawing.AnchorPoint;
import com.app.office.ss.model.drawing.CellAnchor;
import com.app.office.ss.model.sheetProperty.PaneInformation;
import com.app.office.ss.util.ModelUtil;
import com.app.office.ss.util.SectionElementFactory;
import com.app.office.system.AbortReaderError;
import com.app.office.system.AbstractReader;
import com.app.office.system.IControl;
import com.app.office.thirdpart.achartengine.chart.AbstractChart;
import com.app.office.thirdpart.achartengine.chart.RoundChart;
import com.app.office.thirdpart.achartengine.chart.XYChart;
import com.app.office.thirdpart.achartengine.renderers.DefaultRenderer;
import java.util.Iterator;
import java.util.List;

public class ASheet extends Sheet implements com.app.office.fc.ss.usermodel.Sheet {
    private boolean initRowFinished;
    private InternalSheet sheet;

    protected ASheet(AWorkbook aWorkbook, InternalSheet internalSheet) {
        this.sheet = internalSheet;
        this.book = aWorkbook;
        int numMergedRegions = internalSheet.getNumMergedRegions();
        for (int i = 0; i < numMergedRegions; i++) {
            HSSFCellRangeAddress mergedRegionAt = internalSheet.getMergedRegionAt(i);
            addMergeRange(new CellRangeAddress(mergedRegionAt.getFirstRow(), mergedRegionAt.getFirstColumn(), mergedRegionAt.getLastRow(), mergedRegionAt.getLastColumn()));
        }
        HSSFPaneInformation paneInformation = internalSheet.getPaneInformation();
        if (paneInformation != null) {
            setPaneInformation(new PaneInformation(paneInformation.getHorizontalSplitTopRow(), paneInformation.getVerticalSplitLeftColumn(), paneInformation.isFreezePane()));
        }
        List<ColumnInfo> columnInfo = internalSheet.getColumnInfo();
        if (columnInfo != null) {
            for (ColumnInfo next : columnInfo) {
                addColumnInfo(new com.app.office.ss.model.sheetProperty.ColumnInfo(next.getFirstCol(), next.getLastCol(), (float) ((int) ((((double) next.getColWidth()) / 256.0d) * 6.0d * 1.3333333730697632d)), next.getStyle(), next.isHidden()));
            }
        }
    }

    public void processSheet(AbstractReader abstractReader) {
        if (getSheetType() != 1 && !this.initRowFinished) {
            processRowsAndCells(this.sheet, abstractReader);
            processMergedCells();
            processHyperlinkfromSheet(this.sheet);
            this.initRowFinished = true;
        }
    }

    private void processHyperlinkfromSheet(InternalSheet internalSheet) {
        try {
            for (RecordBase next : internalSheet.getRecords()) {
                if (next instanceof HyperlinkRecord) {
                    HyperlinkRecord hyperlinkRecord = (HyperlinkRecord) next;
                    Hyperlink hyperlink = new Hyperlink();
                    if (hyperlinkRecord.isFileLink()) {
                        hyperlink.setLinkType(4);
                    } else if (hyperlinkRecord.isDocumentLink()) {
                        hyperlink.setLinkType(2);
                    } else if (hyperlinkRecord.getAddress() == null || !hyperlinkRecord.getAddress().startsWith(MailTo.MAILTO_SCHEME)) {
                        hyperlink.setLinkType(1);
                    } else {
                        hyperlink.setLinkType(3);
                    }
                    hyperlink.setAddress(hyperlinkRecord.getAddress());
                    hyperlink.setTitle(hyperlinkRecord.getLabel());
                    Row row = getRow(hyperlinkRecord.getFirstRow());
                    if (row == null) {
                        ARow aRow = new ARow(this.book, this, new RowRecord(hyperlinkRecord.getFirstRow()));
                        aRow.setRowPixelHeight(18.0f);
                        this.rows.put(Integer.valueOf(hyperlinkRecord.getFirstRow()), aRow);
                        row = aRow;
                    }
                    Cell cell = row.getCell(hyperlinkRecord.getFirstColumn());
                    if (cell == null) {
                        BlankRecord blankRecord = new BlankRecord();
                        blankRecord.setRow(hyperlinkRecord.getFirstRow());
                        blankRecord.setColumn((short) hyperlinkRecord.getFirstColumn());
                        blankRecord.setXFIndex((short) row.getRowStyle());
                        ACell aCell = new ACell(this, blankRecord);
                        row.addCell(aCell);
                        cell = aCell;
                    }
                    cell.setHyperLink(hyperlink);
                }
            }
        } catch (Exception unused) {
        }
    }

    private void processRowsAndCells(InternalSheet internalSheet, AbstractReader abstractReader) {
        ARow aRow;
        RowRecord nextRow = internalSheet.getNextRow();
        while (nextRow != null) {
            if (!abstractReader.isAborted()) {
                createValidateRowFromRecord(nextRow);
                nextRow = internalSheet.getNextRow();
            } else {
                throw new AbortReaderError("abort Reader");
            }
        }
        Iterator<CellValueRecordInterface> cellValueIterator = internalSheet.getCellValueIterator();
        ARow aRow2 = null;
        while (cellValueIterator.hasNext()) {
            if (!abstractReader.isAborted()) {
                CellValueRecordInterface next = cellValueIterator.next();
                cellValueIterator.remove();
                if (aRow2 == null || aRow2.getRowNumber() != next.getRow()) {
                    if (aRow2 != null) {
                        aRow2.completed();
                    }
                    aRow2 = (ARow) getRow(next.getRow());
                    if (aRow2 == null) {
                        aRow = aRow2;
                        aRow2 = createRowFromRecord(new RowRecord(next.getRow()));
                        aRow2.createCellFromRecord(next);
                        aRow2 = aRow;
                    }
                }
                aRow = aRow2;
                aRow2.createCellFromRecord(next);
                aRow2 = aRow;
            } else {
                throw new AbortReaderError("abort Reader");
            }
        }
        if (aRow2 != null) {
            aRow2.completed();
        }
    }

    private boolean isValidateRow(RowRecord rowRecord) {
        if (rowRecord.getFirstCol() != rowRecord.getLastCol() || rowRecord.getHeight() != 255) {
            return true;
        }
        short xFIndex = rowRecord.getXFIndex();
        if (xFIndex > this.book.getNumStyles()) {
            xFIndex &= 255;
        }
        if (Workbook.isValidateStyle(this.book.getCellStyle(xFIndex))) {
            return true;
        }
        return false;
    }

    private ARow createValidateRowFromRecord(RowRecord rowRecord) {
        Row row = getRow(rowRecord.getRowNumber());
        if (row != null) {
            return (ARow) row;
        }
        if (!isValidateRow(rowRecord)) {
            return null;
        }
        ARow aRow = new ARow(this.book, this, rowRecord);
        addRow(aRow);
        return aRow;
    }

    private ARow createRowFromRecord(RowRecord rowRecord) {
        Row row = getRow(rowRecord.getRowNumber());
        if (row != null) {
            return (ARow) row;
        }
        ARow aRow = new ARow(this.book, this, rowRecord);
        addRow(aRow);
        return aRow;
    }

    private void processMergedCells() {
        int mergeRangeCount = getMergeRangeCount();
        for (int i = 0; i < mergeRangeCount; i++) {
            CellRangeAddress mergeRange = getMergeRange(i);
            if (!(mergeRange.getLastRow() - mergeRange.getFirstRow() == 65535 || mergeRange.getLastColumn() - mergeRange.getFirstColumn() == 255)) {
                for (int firstRow = mergeRange.getFirstRow(); firstRow <= mergeRange.getLastRow(); firstRow++) {
                    Row row = getRow(firstRow);
                    if (row == null) {
                        ARow aRow = new ARow(this.book, this, new RowRecord(firstRow));
                        aRow.setRowPixelHeight(18.0f);
                        addRow(aRow);
                        row = aRow;
                    }
                    for (int firstColumn = mergeRange.getFirstColumn(); firstColumn <= mergeRange.getLastColumn(); firstColumn++) {
                        Cell cell = row.getCell(firstColumn);
                        if (cell == null) {
                            BlankRecord blankRecord = new BlankRecord();
                            blankRecord.setRow(firstRow);
                            blankRecord.setColumn((short) firstColumn);
                            blankRecord.setXFIndex((short) row.getRowStyle());
                            ACell aCell = new ACell(this, blankRecord);
                            row.addCell(aCell);
                            cell = aCell;
                        }
                        cell.setRangeAddressIndex(i);
                    }
                }
            }
        }
    }

    public EscherAggregate getDrawingEscherAggregate(InternalSheet internalSheet) {
        InternalWorkbook internalWorkbook = ((AWorkbook) this.book).getInternalWorkbook();
        internalWorkbook.findDrawingGroup();
        if (internalWorkbook.getDrawingManager() == null || internalSheet.aggregateDrawingRecords(internalWorkbook.getDrawingManager(), false) == -1) {
            return null;
        }
        return (EscherAggregate) internalSheet.findFirstRecordBySid(EscherAggregate.sid);
    }

    public HSSFPatriarch getDrawingPatriarch(InternalSheet internalSheet) {
        EscherAggregate drawingEscherAggregate = getDrawingEscherAggregate(internalSheet);
        if (drawingEscherAggregate == null) {
            return null;
        }
        HSSFPatriarch hSSFPatriarch = new HSSFPatriarch(this, drawingEscherAggregate);
        drawingEscherAggregate.setPatriarch(hSSFPatriarch);
        drawingEscherAggregate.convertRecordsToUserModel(getAWorkbook());
        return hSSFPatriarch;
    }

    public Iterator<Row> rowIterator() {
        return this.rows.values().iterator();
    }

    private BackgroundAndFill converFill(HSSFShape hSSFShape, IControl iControl) {
        if (hSSFShape == null) {
            return null;
        }
        if (hSSFShape.isGradientTile()) {
            return hSSFShape.getGradientTileBackground((AWorkbook) this.book, iControl);
        }
        if (hSSFShape.getFillType() == 3) {
            byte[] bGPictureData = hSSFShape.getBGPictureData();
            if (bGPictureData == null) {
                return null;
            }
            Picture picture = new Picture();
            picture.setData(bGPictureData);
            int addPicture = iControl.getSysKit().getPictureManage().addPicture(picture);
            BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
            backgroundAndFill.setFillType((byte) 3);
            backgroundAndFill.setPictureIndex(addPicture);
            return backgroundAndFill;
        }
        BackgroundAndFill backgroundAndFill2 = new BackgroundAndFill();
        backgroundAndFill2.setFillType((byte) 0);
        backgroundAndFill2.setForegroundColor(hSSFShape.getFillColor());
        return backgroundAndFill2;
    }

    public void processRotationAndFlip(HSSFShape hSSFShape, IShape iShape) {
        float rotation = (float) hSSFShape.getRotation();
        if (hSSFShape.getFlipH()) {
            iShape.setFlipHorizontal(true);
            rotation = -rotation;
        }
        if (hSSFShape.getFlipV()) {
            iShape.setFlipVertical(true);
            rotation = -rotation;
        }
        if ((iShape instanceof LineShape) && ((rotation == 45.0f || rotation == 135.0f || rotation == 225.0f) && !iShape.getFlipHorizontal() && !iShape.getFlipVertical())) {
            rotation -= 90.0f;
        }
        iShape.setRotation(rotation);
    }

    public void processSheetShapes(IControl iControl) {
        short sheetType = getSheetType();
        DefaultRenderer defaultRenderer = null;
        if (sheetType == 0) {
            HSSFPatriarch drawingPatriarch = getDrawingPatriarch(this.sheet);
            if (drawingPatriarch != null) {
                for (HSSFShape next : drawingPatriarch.getChildren()) {
                    if (!((AWorkbook) this.book).getAbstractReader().isAborted()) {
                        processShape(iControl, (GroupShape) null, (HSSFShapeGroup) null, next, (Rectangle) null);
                    } else {
                        throw new AbortReaderError("abort Reader");
                    }
                }
                drawingPatriarch.dispose();
            }
            this.sheet = null;
        } else if (sheetType != 1) {
        } else {
            if (!((AWorkbook) this.book).getAbstractReader().isAborted()) {
                HSSFChart chart = this.sheet.getChart();
                AChart aChart = new AChart();
                AbstractChart converter = ChartConverter.instance().converter(this, chart);
                if (converter != null) {
                    if (converter instanceof XYChart) {
                        defaultRenderer = ((XYChart) converter).getRenderer();
                    } else if (converter instanceof RoundChart) {
                        defaultRenderer = ((RoundChart) converter).getRenderer();
                    }
                    if (defaultRenderer != null && !chart.isNoBorder()) {
                        defaultRenderer.setChartFrame(chart.getLine());
                    }
                    aChart.setAChart(converter);
                    this.shapesList.add(aChart);
                    return;
                }
                return;
            }
            throw new AbortReaderError("abort Reader");
        }
    }

    private CellAnchor ClientAnchorToTwoCellAnchor(HSSFClientAnchor hSSFClientAnchor) {
        AnchorPoint anchorPoint = new AnchorPoint();
        AnchorPoint anchorPoint2 = new AnchorPoint();
        anchorPoint.setColumn(hSSFClientAnchor.getCol1());
        anchorPoint.setRow(hSSFClientAnchor.getRow1());
        anchorPoint2.setRow(hSSFClientAnchor.getRow2());
        anchorPoint2.setColumn(hSSFClientAnchor.getCol2());
        anchorPoint.setDX(Math.round((((float) hSSFClientAnchor.getDx1()) / 1024.0f) * getColumnPixelWidth(hSSFClientAnchor.getCol1())));
        anchorPoint2.setDX(Math.round((((float) hSSFClientAnchor.getDx2()) / 1024.0f) * getColumnPixelWidth(hSSFClientAnchor.getCol2())));
        Row row = getRow(hSSFClientAnchor.getRow1());
        anchorPoint.setDY(Math.round((((float) hSSFClientAnchor.getDy1()) / 256.0f) * (row == null ? (float) getDefaultRowHeight() : row.getRowPixelHeight())));
        Row row2 = getRow(hSSFClientAnchor.getRow2());
        anchorPoint2.setDY(Math.round((((float) hSSFClientAnchor.getDy2()) / 256.0f) * (row2 == null ? (float) getDefaultRowHeight() : row2.getRowPixelHeight())));
        CellAnchor cellAnchor = new CellAnchor(1);
        cellAnchor.setStart(anchorPoint);
        cellAnchor.setEnd(anchorPoint2);
        return cellAnchor;
    }

    private void processShape(IControl iControl, GroupShape groupShape, HSSFShapeGroup hSSFShapeGroup, HSSFShape hSSFShape, Rectangle rectangle) {
        Rectangle rectangle2;
        if (getSheetType() == 0) {
            if (groupShape == null) {
                HSSFClientAnchor hSSFClientAnchor = (HSSFClientAnchor) hSSFShape.getAnchor();
                if (hSSFClientAnchor != null) {
                    rectangle2 = ModelUtil.instance().getCellAnchor((Sheet) this, ClientAnchorToTwoCellAnchor(hSSFClientAnchor));
                    if (rectangle2 != null) {
                        rectangle2 = ModelUtil.processRect(rectangle2, (float) hSSFShape.getRotation());
                    }
                } else {
                    return;
                }
            } else {
                HSSFChildAnchor hSSFChildAnchor = (HSSFChildAnchor) hSSFShape.getAnchor();
                if (hSSFChildAnchor != null) {
                    Rectangle rectangle3 = new Rectangle();
                    rectangle3.x = rectangle.x + Math.round((((float) (hSSFChildAnchor.getDx1() - hSSFShapeGroup.getX1())) / ((float) (hSSFShapeGroup.getX2() - hSSFShapeGroup.getX1()))) * ((float) rectangle.width));
                    rectangle3.y = rectangle.y + Math.round((((float) (hSSFChildAnchor.getDy1() - hSSFShapeGroup.getY1())) / ((float) (hSSFShapeGroup.getY2() - hSSFShapeGroup.getY1()))) * ((float) rectangle.height));
                    rectangle3.width = Math.round((((float) (hSSFChildAnchor.getDx2() - hSSFChildAnchor.getDx1())) / ((float) (hSSFShapeGroup.getX2() - hSSFShapeGroup.getX1()))) * ((float) rectangle.width));
                    rectangle3.height = Math.round((((float) (hSSFChildAnchor.getDy2() - hSSFChildAnchor.getDy1())) / ((float) (hSSFShapeGroup.getY2() - hSSFShapeGroup.getY1()))) * ((float) rectangle.height));
                    rectangle2 = ModelUtil.processRect(rectangle3, (float) hSSFShape.getRotation());
                } else {
                    return;
                }
            }
            int shapeType = hSSFShape.getShapeType();
            if (!(shapeType == 20 || shapeType == 32 || (rectangle2.width != 0 && rectangle2.height != 0))) {
                return;
            }
        } else {
            rectangle2 = null;
        }
        if (hSSFShape instanceof HSSFShapeGroup) {
            GroupShape groupShape2 = new GroupShape();
            groupShape2.setBounds(rectangle2);
            HSSFShapeGroup hSSFShapeGroup2 = (HSSFShapeGroup) hSSFShape;
            for (HSSFShape processShape : hSSFShapeGroup2.getChildren()) {
                processShape(iControl, groupShape2, hSSFShapeGroup2, processShape, rectangle2);
            }
            if (groupShape == null) {
                this.shapesList.add(groupShape2);
            } else {
                groupShape.appendShapes(groupShape2);
            }
        } else {
            processSingleShape(iControl, groupShape, hSSFShape, rectangle2);
        }
    }

    private void processSingleShape(IControl iControl, GroupShape groupShape, HSSFShape hSSFShape, Rectangle rectangle) {
        String string;
        PointF pointF;
        PointF pointF2;
        ArrowPathAndTail endArrowPath;
        BackgroundAndFill backgroundAndFill;
        ArrowPathAndTail startArrowPath;
        BackgroundAndFill backgroundAndFill2;
        DefaultRenderer defaultRenderer;
        IControl iControl2 = iControl;
        GroupShape groupShape2 = groupShape;
        HSSFShape hSSFShape2 = hSSFShape;
        Rectangle rectangle2 = rectangle;
        if (hSSFShape2 instanceof HSSFPicture) {
            HSSFPicture hSSFPicture = (HSSFPicture) hSSFShape2;
            HSSFPictureData pictureData = hSSFPicture.getPictureData();
            if (pictureData != null) {
                byte[] data = pictureData.getData();
                if (data != null) {
                    Picture picture = new Picture();
                    picture.setData(data);
                    byte b = 6;
                    int format = pictureData.getFormat();
                    if (format == 2) {
                        b = 2;
                    } else if (format == 3) {
                        b = 3;
                    }
                    picture.setPictureType(b);
                    int addPicture = iControl.getSysKit().getPictureManage().addPicture(picture);
                    PictureShape pictureShape = new PictureShape();
                    pictureShape.setPictureIndex(addPicture);
                    pictureShape.setBounds(rectangle2);
                    pictureShape.setPictureEffectInfor(PictureEffectInfoFactory.getPictureEffectInfor(hSSFPicture.getEscherOptRecord()));
                    processRotationAndFlip(hSSFShape2, pictureShape);
                    if (!hSSFShape.isNoBorder()) {
                        pictureShape.setLine(hSSFShape.getLine());
                    }
                    if (!hSSFShape.isNoFill()) {
                        pictureShape.setBackgroundAndFill(converFill(hSSFShape2, iControl2));
                    }
                    if (groupShape2 == null) {
                        this.shapesList.add(pictureShape);
                    } else {
                        groupShape2.appendShapes(pictureShape);
                    }
                }
            } else if (!hSSFShape.isNoBorder() || !hSSFShape.isNoFill()) {
                AutoShape autoShape = new AutoShape(1);
                autoShape.setAuotShape07(false);
                autoShape.setBounds(rectangle2);
                if (!hSSFShape.isNoBorder()) {
                    autoShape.setLine(hSSFShape.getLine());
                }
                if (!hSSFShape.isNoFill()) {
                    autoShape.setBackgroundAndFill(converFill(hSSFShape2, iControl2));
                }
                processRotationAndFlip(hSSFShape2, autoShape);
                if (groupShape2 == null) {
                    this.shapesList.add(autoShape);
                } else {
                    groupShape2.appendShapes(autoShape);
                }
            }
        } else if (hSSFShape2 instanceof HSSFChart) {
            HSSFChart hSSFChart = (HSSFChart) hSSFShape2;
            AChart aChart = new AChart();
            aChart.setBounds(rectangle2);
            AbstractChart converter = ChartConverter.instance().converter(this, hSSFChart);
            if (converter != null) {
                if (converter instanceof XYChart) {
                    defaultRenderer = ((XYChart) converter).getRenderer();
                } else {
                    defaultRenderer = converter instanceof RoundChart ? ((RoundChart) converter).getRenderer() : null;
                }
                if (defaultRenderer != null) {
                    if (!hSSFChart.isNoBorder()) {
                        defaultRenderer.setChartFrame(hSSFChart.getLine());
                    }
                    if (!hSSFChart.isNoFill()) {
                        defaultRenderer.setBackgroundAndFill(converFill(hSSFChart, iControl2));
                    }
                }
                aChart.setAChart(converter);
                if (groupShape2 == null) {
                    this.shapesList.add(aChart);
                } else {
                    groupShape2.appendShapes(aChart);
                }
            }
        } else if (hSSFShape2 instanceof HSSFLine) {
            if (!hSSFShape.isNoBorder()) {
                LineShape lineShape = new LineShape();
                lineShape.setAuotShape07(false);
                lineShape.setShapeType(hSSFShape.getShapeType());
                lineShape.setBounds(rectangle2);
                lineShape.setLine(hSSFShape.getLine());
                HSSFLine hSSFLine = (HSSFLine) hSSFShape2;
                Float[] adjustmentValue = hSSFLine.getAdjustmentValue();
                if (lineShape.getShapeType() == 33 && adjustmentValue == null) {
                    lineShape.setAdjustData(new Float[]{Float.valueOf(1.0f)});
                } else {
                    lineShape.setAdjustData(adjustmentValue);
                }
                if (hSSFLine.getStartArrowType() > 0) {
                    lineShape.createStartArrow((byte) hSSFShape.getStartArrowType(), hSSFShape.getStartArrowWidth(), hSSFShape.getStartArrowLength());
                }
                if (hSSFLine.getEndArrowType() > 0) {
                    lineShape.createEndArrow((byte) hSSFShape.getEndArrowType(), hSSFShape.getEndArrowWidth(), hSSFShape.getEndArrowLength());
                }
                processRotationAndFlip(hSSFShape2, lineShape);
                if (groupShape2 == null) {
                    this.shapesList.add(lineShape);
                } else {
                    groupShape2.appendShapes(lineShape);
                }
            }
        } else if (hSSFShape2 instanceof HSSFFreeform) {
            if (!hSSFShape.isNoBorder() || !hSSFShape.isNoFill()) {
                ArbitraryPolygonShape arbitraryPolygonShape = new ArbitraryPolygonShape();
                arbitraryPolygonShape.setShapeType(233);
                arbitraryPolygonShape.setBounds(rectangle2);
                Line line = hSSFShape.getLine();
                HSSFFreeform hSSFFreeform = (HSSFFreeform) hSSFShape2;
                int startArrowType = hSSFFreeform.getStartArrowType();
                if (startArrowType <= 0 || (startArrowPath = hSSFFreeform.getStartArrowPath(rectangle2)) == null || startArrowPath.getArrowPath() == null) {
                    pointF = null;
                } else {
                    pointF = startArrowPath.getArrowTailCenter();
                    ExtendPath extendPath = new ExtendPath();
                    extendPath.setPath(startArrowPath.getArrowPath());
                    extendPath.setArrowFlag(true);
                    if (startArrowType != 5) {
                        if (hSSFShape.isNoFill()) {
                            backgroundAndFill2 = new BackgroundAndFill();
                            backgroundAndFill2.setFillType((byte) 0);
                            backgroundAndFill2.setForegroundColor(hSSFShape.getLineStyleColor());
                        } else {
                            backgroundAndFill2 = line != null ? line.getBackgroundAndFill() : null;
                        }
                        extendPath.setBackgroundAndFill(backgroundAndFill2);
                    } else {
                        extendPath.setLine(line);
                    }
                    arbitraryPolygonShape.appendPath(extendPath);
                }
                int endArrowType = hSSFFreeform.getEndArrowType();
                if (endArrowType <= 0 || (endArrowPath = hSSFFreeform.getEndArrowPath(rectangle2)) == null || endArrowPath.getArrowPath() == null) {
                    pointF2 = null;
                } else {
                    pointF2 = endArrowPath.getArrowTailCenter();
                    ExtendPath extendPath2 = new ExtendPath();
                    extendPath2.setPath(endArrowPath.getArrowPath());
                    extendPath2.setArrowFlag(true);
                    if (endArrowType != 5) {
                        if (hSSFShape.isNoFill()) {
                            backgroundAndFill = new BackgroundAndFill();
                            backgroundAndFill.setFillType((byte) 0);
                            backgroundAndFill.setForegroundColor(hSSFShape.getLineStyleColor());
                        } else {
                            backgroundAndFill = line != null ? line.getBackgroundAndFill() : null;
                        }
                        extendPath2.setBackgroundAndFill(backgroundAndFill);
                    } else {
                        extendPath2.setLine(line);
                    }
                    arbitraryPolygonShape.appendPath(extendPath2);
                }
                Path[] freeformPath = hSSFFreeform.getFreeformPath(rectangle, pointF, (byte) startArrowType, pointF2, (byte) endArrowType);
                for (Path path : freeformPath) {
                    ExtendPath extendPath3 = new ExtendPath();
                    extendPath3.setPath(path);
                    if (!hSSFShape.isNoBorder()) {
                        extendPath3.setLine(line);
                    }
                    if (!hSSFShape.isNoFill()) {
                        extendPath3.setBackgroundAndFill(converFill(hSSFShape2, iControl2));
                    }
                    arbitraryPolygonShape.appendPath(extendPath3);
                }
                processRotationAndFlip(hSSFShape2, arbitraryPolygonShape);
                if (groupShape2 == null) {
                    this.shapesList.add(arbitraryPolygonShape);
                } else {
                    groupShape2.appendShapes(arbitraryPolygonShape);
                }
            }
        } else if (hSSFShape2 instanceof HSSFAutoShape) {
            if (!hSSFShape.isNoBorder() || !hSSFShape.isNoFill()) {
                AutoShape autoShape2 = new AutoShape(hSSFShape.getShapeType());
                autoShape2.setAuotShape07(false);
                autoShape2.setBounds(rectangle2);
                if (!hSSFShape.isNoBorder()) {
                    autoShape2.setLine(hSSFShape.getLine());
                }
                if (!hSSFShape.isNoFill()) {
                    autoShape2.setBackgroundAndFill(converFill(hSSFShape2, iControl2));
                }
                if (hSSFShape.getShapeType() != 202) {
                    autoShape2.setAdjustData(((HSSFAutoShape) hSSFShape2).getAdjustmentValue());
                }
                processRotationAndFlip(hSSFShape2, autoShape2);
                if (groupShape2 == null) {
                    this.shapesList.add(autoShape2);
                } else {
                    groupShape2.appendShapes(autoShape2);
                }
            }
            HSSFTextbox hSSFTextbox = (HSSFTextbox) hSSFShape2;
            HSSFRichTextString string2 = hSSFTextbox.getString();
            if (string2 != null && (string = string2.getString()) != null && string.length() > 0) {
                TextBox textBox = new TextBox();
                textBox.setElement(SectionElementFactory.getSectionElement(this.book, hSSFTextbox, rectangle2));
                textBox.setWrapLine(hSSFTextbox.isTextboxWrapLine());
                textBox.setBounds(rectangle2);
                processRotationAndFlip(hSSFShape2, textBox);
                if (groupShape2 == null) {
                    this.shapesList.add(textBox);
                } else {
                    groupShape2.appendShapes(textBox);
                }
            }
        }
    }

    public AWorkbook getAWorkbook() {
        return (AWorkbook) this.book;
    }

    public InternalSheet getInternalSheet() {
        return this.sheet;
    }

    public void dispose() {
        super.dispose();
        this.sheet.dispose();
        this.sheet = null;
    }
}
