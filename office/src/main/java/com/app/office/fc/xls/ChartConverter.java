package com.app.office.fc.xls;

import com.app.office.fc.hssf.formula.EvaluationWorkbook;
import com.app.office.fc.hssf.formula.eval.AreaEval;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.ptg.Area3DPtg;
import com.app.office.fc.hssf.formula.ptg.MemFuncPtg;
import com.app.office.fc.hssf.formula.ptg.NameXPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.ptg.Ref3DPtg;
import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.chart.ObjectLinkRecord;
import com.app.office.fc.hssf.record.chart.SeriesTextRecord;
import com.app.office.fc.hssf.record.chart.ValueRangeRecord;
import com.app.office.fc.hssf.usermodel.HSSFChart;
import com.app.office.fc.hssf.usermodel.HSSFDataFormat;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.ss.model.XLSModel.ASheet;
import com.app.office.ss.model.XLSModel.AWorkbook;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.ss.util.format.NumericFormatter;
import com.app.office.thirdpart.achartengine.chart.AbstractChart;
import com.app.office.thirdpart.achartengine.chart.PointStyle;
import com.app.office.thirdpart.achartengine.model.CategorySeries;
import com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset;
import com.app.office.thirdpart.achartengine.model.XYSeries;
import com.app.office.thirdpart.achartengine.renderers.DefaultRenderer;
import com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartConverter {
    private static ChartConverter converter = new ChartConverter();
    private AbstractChart chart;
    private Map<SeriesTextRecord, Record> chartSeriesText;
    double maxY;
    double minY;
    private InternalWorkbook workbook;

    public static ChartConverter instance() {
        return converter;
    }

    public AbstractChart converter(ASheet aSheet, HSSFChart hSSFChart) {
        this.workbook = aSheet.getAWorkbook().getInternalWorkbook();
        this.minY = Double.MAX_VALUE;
        this.maxY = -1.7976931348623157E308d;
        this.chartSeriesText = hSSFChart.getSeriesText();
        AbstractChart convertToAChart = convertToAChart(aSheet, hSSFChart);
        dispose();
        return convertToAChart;
    }

    public short getChartType(HSSFChart hSSFChart) {
        int ordinal = hSSFChart.getType().ordinal();
        if (ordinal == HSSFChart.HSSFChartType.Area.ordinal()) {
            return 0;
        }
        if (ordinal == HSSFChart.HSSFChartType.Bar.ordinal()) {
            return 1;
        }
        if (ordinal == HSSFChart.HSSFChartType.Line.ordinal()) {
            return 2;
        }
        if (ordinal == HSSFChart.HSSFChartType.Pie.ordinal()) {
            return 3;
        }
        return ordinal == HSSFChart.HSSFChartType.Scatter.ordinal() ? (short) 4 : 10;
    }

    private String getFormatContents(Sheet sheet, Cell cell) {
        CellStyle cellStyle = cell.getCellStyle();
        short cellType = cell.getCellType();
        if (cellType == 0) {
            String formatCode = HSSFDataFormat.getFormatCode(this.workbook, cellStyle.getNumberFormatID());
            short numericCellType = NumericFormatter.instance().getNumericCellType(formatCode);
            if (numericCellType == 10) {
                return NumericFormatter.instance().getFormatContents(formatCode, cell.getDateCellValue(sheet.getWorkbook().isUsing1904DateWindowing()));
            }
            return NumericFormatter.instance().getFormatContents(formatCode, cell.getNumberValue(), numericCellType);
        } else if (cellType == 1) {
            Object sharedItem = sheet.getWorkbook().getSharedItem(cell.getStringCellValueIndex());
            if (sharedItem instanceof SectionElement) {
                return ((SectionElement) sharedItem).getText((IDocument) null);
            }
            return (String) sharedItem;
        } else if (cellType != 4) {
            return "";
        } else {
            return String.valueOf(cell.getBooleanValue());
        }
    }

    private double getCellNumericValue(Sheet sheet, Cell cell) {
        if (cell == null) {
            return 0.0d;
        }
        if (cell.getCellType() == 0) {
            return cell.getNumberValue();
        }
        if (cell.getCellType() == 3) {
        }
        return 0.0d;
    }

    private List<Double> getData(ASheet aSheet, Ptg[] ptgArr) {
        Sheet sheet;
        Sheet sheet2;
        Sheet sheet3;
        if (ptgArr == null || ptgArr.length <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        double d = 0.0d;
        if (ptgArr[0] instanceof Area3DPtg) {
            Area3DPtg area3DPtg = ptgArr[0];
            EvaluationWorkbook.ExternalSheet externalSheet = this.workbook.getExternalSheet(area3DPtg.getExternSheetIndex());
            if (externalSheet == null) {
                sheet3 = aSheet.getWorkbook().getSheet(this.workbook.getSheetIndexFromExternSheetIndex(area3DPtg.getExternSheetIndex()));
            } else {
                sheet3 = aSheet.getWorkbook().getSheet(externalSheet.getSheetName());
            }
            if (area3DPtg.getFirstRow() == area3DPtg.getLastRow()) {
                Row row = sheet3.getRow(area3DPtg.getFirstRow());
                for (int firstColumn = area3DPtg.getFirstColumn(); firstColumn <= area3DPtg.getLastColumn(); firstColumn++) {
                    arrayList.add(Double.valueOf(row != null ? getCellNumericValue(sheet3, row.getCell(firstColumn)) : 0.0d));
                }
            } else if (area3DPtg.getFirstColumn() == area3DPtg.getLastColumn()) {
                for (int firstRow = area3DPtg.getFirstRow(); firstRow <= area3DPtg.getLastRow(); firstRow++) {
                    Row row2 = sheet3.getRow(firstRow);
                    arrayList.add(Double.valueOf(row2 != null ? getCellNumericValue(sheet3, row2.getCell(area3DPtg.getFirstColumn())) : 0.0d));
                }
            }
        } else if (ptgArr[0] instanceof MemFuncPtg) {
            for (int i = 0; i < ptgArr.length; i++) {
                if (ptgArr[i] instanceof Ref3DPtg) {
                    Ref3DPtg ref3DPtg = ptgArr[i];
                    EvaluationWorkbook.ExternalSheet externalSheet2 = this.workbook.getExternalSheet(ref3DPtg.getExternSheetIndex());
                    if (externalSheet2 == null) {
                        sheet2 = aSheet.getWorkbook().getSheet(this.workbook.getSheetIndexFromExternSheetIndex(ref3DPtg.getExternSheetIndex()));
                    } else {
                        sheet2 = aSheet.getWorkbook().getSheet(externalSheet2.getSheetName());
                    }
                    Row row3 = sheet2.getRow(ref3DPtg.getRow());
                    arrayList.add(Double.valueOf(row3 != null ? getCellNumericValue(sheet2, row3.getCell(ref3DPtg.getColumn())) : 0.0d));
                }
            }
        } else if (ptgArr[0] instanceof NameXPtg) {
            try {
                ValueEval evaluate = evaluate(aSheet, ((AWorkbook) aSheet.getWorkbook()).getNameAt(ptgArr[0].getNameIndex()));
                if (evaluate instanceof AreaEval) {
                    AreaEval areaEval = (AreaEval) evaluate;
                    if (areaEval.getFirstRow() == areaEval.getLastRow()) {
                        for (int firstColumn2 = areaEval.getFirstColumn(); firstColumn2 <= areaEval.getLastColumn(); firstColumn2++) {
                            arrayList.add(Double.valueOf(((NumberEval) areaEval.getAbsoluteValue(areaEval.getFirstRow(), firstColumn2)).getNumberValue()));
                        }
                    } else if (areaEval.getFirstColumn() == areaEval.getLastColumn()) {
                        for (int firstRow2 = areaEval.getFirstRow(); firstRow2 <= areaEval.getLastRow(); firstRow2++) {
                            arrayList.add(Double.valueOf(((NumberEval) areaEval.getAbsoluteValue(firstRow2, areaEval.getFirstColumn())).getNumberValue()));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (ptgArr.length > 0 && (ptgArr[0] instanceof Ref3DPtg)) {
            Ref3DPtg ref3DPtg2 = ptgArr[0];
            EvaluationWorkbook.ExternalSheet externalSheet3 = this.workbook.getExternalSheet(ref3DPtg2.getExternSheetIndex());
            if (externalSheet3 == null) {
                sheet = aSheet.getWorkbook().getSheet(this.workbook.getSheetIndexFromExternSheetIndex(ref3DPtg2.getExternSheetIndex()));
            } else {
                sheet = aSheet.getWorkbook().getSheet(externalSheet3.getSheetName());
            }
            Row row4 = sheet.getRow(ref3DPtg2.getRow());
            if (row4 != null) {
                d = getCellNumericValue(sheet, row4.getCell(ref3DPtg2.getColumn()));
            }
            arrayList.add(Double.valueOf(d));
        }
        return arrayList;
    }

    private XYMultipleSeriesDataset getXYMultipleSeriesDataset(ASheet aSheet, HSSFChart hSSFChart, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, short s) {
        String str;
        int i;
        ASheet aSheet2 = aSheet;
        short s2 = s;
        XYMultipleSeriesDataset xYMultipleSeriesDataset = new XYMultipleSeriesDataset();
        HSSFChart.HSSFSeries[] series = hSSFChart.getSeries();
        SimpleSeriesRenderer[] seriesRenderers = xYMultipleSeriesRenderer.getSeriesRenderers();
        int length = series.length;
        int i2 = 0;
        while (i2 < length) {
            if (series[i2].getSeriesTitle() != null) {
                str = series[i2].getSeriesTitle();
            } else {
                str = "Series " + (i2 + 1);
            }
            List list = null;
            boolean z = true;
            if (s2 == 4) {
                list = new ArrayList();
                Ptg[] formulaOfLink = series[i2].getDataCategoryLabels().getFormulaOfLink();
                if (formulaOfLink.length > 0) {
                    list = getData(aSheet2, formulaOfLink);
                    int i3 = 0;
                    while (true) {
                        if (i3 >= list.size() - 1) {
                            break;
                        }
                        i3++;
                        if (Math.abs(((Double) list.get(i3)).doubleValue() - ((Double) list.get(i3)).doubleValue()) < 9.999999717180685E-10d) {
                            z = false;
                            break;
                        }
                    }
                }
            }
            Ptg[] formulaOfLink2 = series[i2].getDataValues().getFormulaOfLink();
            if (formulaOfLink2.length <= 0) {
                xYMultipleSeriesRenderer.removeSeriesRenderer(seriesRenderers[i2]);
                hSSFChart.removeSeries(series[i2]);
            } else {
                HSSFChart hSSFChart2 = hSSFChart;
                XYMultipleSeriesRenderer xYMultipleSeriesRenderer2 = xYMultipleSeriesRenderer;
                List<Double> data = getData(aSheet2, formulaOfLink2);
                if (s2 != 4 || !z) {
                    i = i2;
                    CategorySeries categorySeries = new CategorySeries(str);
                    for (Double next : data) {
                        categorySeries.add(next.doubleValue());
                        this.minY = Math.min(next.doubleValue(), this.minY);
                        this.maxY = Math.max(next.doubleValue(), this.maxY);
                    }
                    xYMultipleSeriesDataset.addSeries(categorySeries.toXYSeries());
                    i2 = i + 1;
                    aSheet2 = aSheet;
                    s2 = s;
                } else if (!(list == null || data == null || list.size() != data.size())) {
                    XYSeries xYSeries = new XYSeries(str);
                    int i4 = 0;
                    while (i4 < list.size()) {
                        xYSeries.add(((Double) list.get(i4)).doubleValue(), data.get(i4).doubleValue());
                        this.minY = Math.min(data.get(i4).doubleValue(), this.minY);
                        this.maxY = Math.max(data.get(i4).doubleValue(), this.maxY);
                        i4++;
                        ASheet aSheet3 = aSheet;
                        short s3 = s;
                        i2 = i2;
                    }
                    i = i2;
                    xYMultipleSeriesDataset.addSeries(xYSeries);
                    i2 = i + 1;
                    aSheet2 = aSheet;
                    s2 = s;
                }
            }
            i = i2;
            i2 = i + 1;
            aSheet2 = aSheet;
            s2 = s;
        }
        return xYMultipleSeriesDataset;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0041 A[LOOP:0: B:1:0x0008->B:12:0x0041, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0035 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.fc.hssf.formula.eval.ValueEval evaluate(com.app.office.ss.model.XLSModel.ASheet r8, com.app.office.fc.hssf.usermodel.HSSFName r9) {
        /*
            r7 = this;
            int r0 = r8.getFirstRowNum()
            int r1 = r8.getLastRowNum()
        L_0x0008:
            r2 = 0
            if (r0 > r1) goto L_0x0044
            int r3 = r8.getLastRowNum()
            com.app.office.ss.model.baseModel.Row r3 = r8.getRow(r3)
            com.app.office.ss.model.XLSModel.ARow r3 = (com.app.office.ss.model.XLSModel.ARow) r3
            r4 = -1
            int r5 = r3.getFirstCol()
            if (r5 <= 0) goto L_0x0024
            int r3 = r3.getFirstCol()
            int r3 = r3 + -1
        L_0x0022:
            short r4 = (short) r3
            goto L_0x0033
        L_0x0024:
            int r5 = r3.getLastCol()
            int r6 = com.app.office.fc.hssf.usermodel.HSSFCell.LAST_COLUMN_NUMBER
            if (r5 >= r6) goto L_0x0033
            int r3 = r3.getLastCol()
            int r3 = r3 + 1
            goto L_0x0022
        L_0x0033:
            if (r4 < 0) goto L_0x0041
            com.app.office.ss.model.XLSModel.ACell r1 = new com.app.office.ss.model.XLSModel.ACell
            com.app.office.ss.model.baseModel.Workbook r3 = r8.getWorkbook()
            com.app.office.ss.model.XLSModel.AWorkbook r3 = (com.app.office.ss.model.XLSModel.AWorkbook) r3
            r1.<init>(r3, r8, r0, r4)
            goto L_0x0045
        L_0x0041:
            int r0 = r0 + 1
            goto L_0x0008
        L_0x0044:
            r1 = r2
        L_0x0045:
            if (r1 == 0) goto L_0x0061
            com.app.office.fc.hssf.formula.ptg.Ptg[] r9 = r9.getRefersToFormulaDefinition()
            r1.setCellFormula(r9)
            com.app.office.fc.hssf.usermodel.HSSFFormulaEvaluator r9 = new com.app.office.fc.hssf.usermodel.HSSFFormulaEvaluator
            com.app.office.ss.model.baseModel.Workbook r8 = r8.getWorkbook()
            com.app.office.ss.model.XLSModel.AWorkbook r8 = (com.app.office.ss.model.XLSModel.AWorkbook) r8
            r9.<init>(r8)
            com.app.office.fc.hssf.formula.eval.ValueEval r8 = r9.evaluateFormulaValueEval((com.app.office.ss.model.XLSModel.ACell) r1)
            r1.dispose()
            return r8
        L_0x0061:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.xls.ChartConverter.evaluate(com.app.office.ss.model.XLSModel.ASheet, com.app.office.fc.hssf.usermodel.HSSFName):com.app.office.fc.hssf.formula.eval.ValueEval");
    }

    private XYMultipleSeriesRenderer buildXYMultipleSeriesRenderer(ASheet aSheet, HSSFChart hSSFChart) {
        return buildXYMultipleSeriesRenderer(aSheet, hSSFChart, (PointStyle[]) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0160  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer buildXYMultipleSeriesRenderer(com.app.office.ss.model.XLSModel.ASheet r12, com.app.office.fc.hssf.usermodel.HSSFChart r13, com.app.office.thirdpart.achartengine.chart.PointStyle[] r14) {
        /*
            r11 = this;
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = new com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer
            r0.<init>()
            r1 = 1098907648(0x41800000, float:16.0)
            r0.setXTitleTextSize(r1)
            r0.setYTitleTextSize(r1)
            r1 = 1101004800(0x41a00000, float:20.0)
            r0.setChartTitleTextSize(r1)
            r1 = 1097859072(0x41700000, float:15.0)
            r0.setLabelsTextSize(r1)
            r0.setLegendTextSize(r1)
            r1 = 1
            r0.setShowGridH(r1)
            short r2 = r11.getChartType(r13)
            r3 = 3
            r4 = 2
            r5 = 0
            if (r2 == 0) goto L_0x0035
            if (r2 == r1) goto L_0x0035
            if (r2 == r4) goto L_0x0032
            if (r2 == r3) goto L_0x0035
            r6 = 4
            if (r2 == r6) goto L_0x0032
            r2 = 0
            goto L_0x0037
        L_0x0032:
            r2 = 32
            goto L_0x0037
        L_0x0035:
            r2 = 24
        L_0x0037:
            com.app.office.fc.hssf.usermodel.HSSFChart$HSSFSeries[] r13 = r13.getSeries()
            if (r14 == 0) goto L_0x0065
            int r6 = r14.length
            if (r6 <= 0) goto L_0x0065
            r6 = 0
            r7 = 0
        L_0x0042:
            int r8 = r13.length
            if (r6 >= r8) goto L_0x0081
            com.app.office.thirdpart.achartengine.renderers.XYSeriesRenderer r8 = new com.app.office.thirdpart.achartengine.renderers.XYSeriesRenderer
            r8.<init>()
            r9 = r14[r7]
            r8.setPointStyle(r9)
            int r7 = r7 + r1
            int r9 = r14.length
            int r7 = r7 % r9
            com.app.office.ss.model.baseModel.Workbook r9 = r12.getWorkbook()
            int r10 = r6 + r2
            int r9 = r9.getColor(r10)
            r8.setColor(r9)
            r0.addSeriesRenderer(r8)
            int r6 = r6 + 1
            goto L_0x0042
        L_0x0065:
            r14 = 0
        L_0x0066:
            int r2 = r13.length
            if (r14 >= r2) goto L_0x0081
            com.app.office.thirdpart.achartengine.renderers.XYSeriesRenderer r2 = new com.app.office.thirdpart.achartengine.renderers.XYSeriesRenderer
            r2.<init>()
            com.app.office.ss.model.baseModel.Workbook r6 = r12.getWorkbook()
            int r7 = r14 + 24
            int r6 = r6.getColor(r7)
            r2.setColor(r6)
            r0.addSeriesRenderer(r2)
            int r14 = r14 + 1
            goto L_0x0066
        L_0x0081:
            int r14 = r13.length
            if (r14 <= 0) goto L_0x0150
            r13 = r13[r5]
            com.app.office.fc.hssf.record.chart.LinkedDataRecord r13 = r13.getDataCategoryLabels()
            com.app.office.fc.hssf.formula.ptg.Ptg[] r13 = r13.getFormulaOfLink()
            int r14 = r13.length
            if (r14 <= 0) goto L_0x0150
            r14 = r13[r5]
            boolean r14 = r14 instanceof com.app.office.fc.hssf.formula.ptg.Area3DPtg
            if (r14 == 0) goto L_0x0150
            r13 = r13[r5]
            com.app.office.fc.hssf.formula.ptg.Area3DPtg r13 = (com.app.office.fc.hssf.formula.ptg.Area3DPtg) r13
            com.app.office.fc.hssf.model.InternalWorkbook r14 = r11.workbook
            int r2 = r13.getExternSheetIndex()
            com.app.office.fc.hssf.formula.EvaluationWorkbook$ExternalSheet r14 = r14.getExternalSheet(r2)
            if (r14 != 0) goto L_0x00ba
            com.app.office.fc.hssf.model.InternalWorkbook r14 = r11.workbook
            int r2 = r13.getExternSheetIndex()
            int r14 = r14.getSheetIndexFromExternSheetIndex(r2)
            com.app.office.ss.model.baseModel.Workbook r12 = r12.getWorkbook()
            com.app.office.ss.model.baseModel.Sheet r12 = r12.getSheet((int) r14)
            goto L_0x00c6
        L_0x00ba:
            com.app.office.ss.model.baseModel.Workbook r12 = r12.getWorkbook()
            java.lang.String r14 = r14.getSheetName()
            com.app.office.ss.model.baseModel.Sheet r12 = r12.getSheet((java.lang.String) r14)
        L_0x00c6:
            int r14 = r13.getFirstRow()
            int r2 = r13.getLastRow()
            if (r14 != r2) goto L_0x0109
            int r14 = r13.getFirstRow()
            com.app.office.ss.model.baseModel.Row r14 = r12.getRow(r14)
            int r2 = r13.getFirstColumn()
            r5 = 1
        L_0x00dd:
            int r6 = r13.getLastColumn()
            if (r2 > r6) goto L_0x0150
            if (r14 == 0) goto L_0x00f4
            com.app.office.ss.model.baseModel.Cell r6 = r14.getCell(r2)
            if (r6 == 0) goto L_0x00f4
            com.app.office.ss.model.baseModel.Cell r6 = r14.getCell(r2)
            java.lang.String r6 = r11.getFormatContents(r12, r6)
            goto L_0x00ff
        L_0x00f4:
            int r6 = r13.getFirstColumn()
            int r6 = r2 - r6
            int r6 = r6 + r1
            java.lang.String r6 = java.lang.String.valueOf(r6)
        L_0x00ff:
            int r7 = r5 + 1
            double r8 = (double) r5
            r0.addXTextLabel(r8, r6)
            int r2 = r2 + 1
            r5 = r7
            goto L_0x00dd
        L_0x0109:
            int r14 = r13.getFirstColumn()
            int r2 = r13.getLastColumn()
            if (r14 != r2) goto L_0x0150
            int r14 = r13.getFirstRow()
            r2 = 1
        L_0x0118:
            int r5 = r13.getLastRow()
            if (r14 > r5) goto L_0x0150
            com.app.office.ss.model.baseModel.Row r5 = r12.getRow(r14)
            if (r5 == 0) goto L_0x013b
            int r6 = r13.getFirstColumn()
            com.app.office.ss.model.baseModel.Cell r6 = r5.getCell(r6)
            if (r6 == 0) goto L_0x013b
            int r6 = r13.getFirstColumn()
            com.app.office.ss.model.baseModel.Cell r5 = r5.getCell(r6)
            java.lang.String r5 = r11.getFormatContents(r12, r5)
            goto L_0x0146
        L_0x013b:
            int r5 = r13.getFirstRow()
            int r5 = r14 - r5
            int r5 = r5 + r1
            java.lang.String r5 = java.lang.String.valueOf(r5)
        L_0x0146:
            int r6 = r2 + 1
            double r7 = (double) r2
            r0.addXTextLabel(r7, r5)
            int r14 = r14 + 1
            r2 = r6
            goto L_0x0118
        L_0x0150:
            java.util.Map<com.app.office.fc.hssf.record.chart.SeriesTextRecord, com.app.office.fc.hssf.record.Record> r12 = r11.chartSeriesText
            java.util.Set r12 = r12.keySet()
            java.util.Iterator r12 = r12.iterator()
        L_0x015a:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x0197
            java.lang.Object r13 = r12.next()
            com.app.office.fc.hssf.record.chart.SeriesTextRecord r13 = (com.app.office.fc.hssf.record.chart.SeriesTextRecord) r13
            java.util.Map<com.app.office.fc.hssf.record.chart.SeriesTextRecord, com.app.office.fc.hssf.record.Record> r14 = r11.chartSeriesText
            java.lang.Object r14 = r14.get(r13)
            com.app.office.fc.hssf.record.Record r14 = (com.app.office.fc.hssf.record.Record) r14
            boolean r2 = r14 instanceof com.app.office.fc.hssf.record.chart.ObjectLinkRecord
            if (r2 == 0) goto L_0x015a
            com.app.office.fc.hssf.record.chart.ObjectLinkRecord r14 = (com.app.office.fc.hssf.record.chart.ObjectLinkRecord) r14
            short r14 = r14.getAnchorId()
            if (r14 == r1) goto L_0x018f
            if (r14 == r4) goto L_0x0187
            if (r14 == r3) goto L_0x017f
            goto L_0x015a
        L_0x017f:
            java.lang.String r13 = r13.getText()
            r0.setXTitle(r13)
            goto L_0x015a
        L_0x0187:
            java.lang.String r13 = r13.getText()
            r0.setYTitle(r13)
            goto L_0x015a
        L_0x018f:
            java.lang.String r13 = r13.getText()
            r0.setChartTitle(r13)
            goto L_0x015a
        L_0x0197:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.xls.ChartConverter.buildXYMultipleSeriesRenderer(com.app.office.ss.model.XLSModel.ASheet, com.app.office.fc.hssf.usermodel.HSSFChart, com.app.office.thirdpart.achartengine.chart.PointStyle[]):com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer");
    }

    private void setChartSettings(XYMultipleSeriesRenderer xYMultipleSeriesRenderer, XYMultipleSeriesDataset xYMultipleSeriesDataset, HSSFChart hSSFChart) {
        ValueRangeRecord valueRangeRecord;
        int i = -1;
        for (int i2 = 0; i2 < xYMultipleSeriesDataset.getSeriesCount(); i2++) {
            i = Math.max(i, xYMultipleSeriesDataset.getSeriesAt(i2).getItemCount());
        }
        List<ValueRangeRecord> valueRangeRecord2 = hSSFChart.getValueRangeRecord();
        if (valueRangeRecord2.size() > 0) {
            if (getChartType(hSSFChart) != 4) {
                xYMultipleSeriesRenderer.setXAxisMin(0.5d);
                xYMultipleSeriesRenderer.setXAxisMax(((double) i) + 0.5d);
                valueRangeRecord = valueRangeRecord2.get(0);
            } else {
                double minX = xYMultipleSeriesDataset.getSeriesAt(0).getMinX();
                double maxX = xYMultipleSeriesDataset.getSeriesAt(0).getMaxX();
                ValueRangeRecord valueRangeRecord3 = valueRangeRecord2.get(0);
                if (!valueRangeRecord3.isAutomaticMinimum()) {
                    minX = valueRangeRecord3.getMinimumAxisValue();
                }
                if (!valueRangeRecord3.isAutomaticMaximum()) {
                    maxX = valueRangeRecord3.getMaximumAxisValue();
                }
                xYMultipleSeriesRenderer.setXAxisMin(minX);
                xYMultipleSeriesRenderer.setXAxisMax(maxX);
                valueRangeRecord = valueRangeRecord2.get(1);
            }
            if (!valueRangeRecord.isAutomaticMinimum()) {
                this.minY = valueRangeRecord.getMinimumAxisValue();
            }
            if (!valueRangeRecord.isAutomaticMaximum()) {
                this.maxY = valueRangeRecord.getMaximumAxisValue();
            }
        } else if (getChartType(hSSFChart) != 4) {
            xYMultipleSeriesRenderer.setXAxisMin(0.5d);
            xYMultipleSeriesRenderer.setXAxisMax(((double) i) + 0.5d);
        } else {
            double minX2 = xYMultipleSeriesDataset.getSeriesAt(0).getMinX();
            double maxX2 = xYMultipleSeriesDataset.getSeriesAt(0).getMaxX();
            xYMultipleSeriesRenderer.setXAxisMin(minX2);
            xYMultipleSeriesRenderer.setXAxisMax(maxX2);
        }
        xYMultipleSeriesRenderer.setYAxisMin(this.minY);
        xYMultipleSeriesRenderer.setYAxisMax(this.maxY);
    }

    /* access modifiers changed from: protected */
    public DefaultRenderer buildDefaultRenderer(Sheet sheet, HSSFChart hSSFChart) {
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        defaultRenderer.setLabelsTextSize(15.0f);
        defaultRenderer.setLegendTextSize(15.0f);
        defaultRenderer.setShowGridH(true);
        if (hSSFChart.getMarginColorFormat() != null) {
            defaultRenderer.setBackgroundColor(sheet.getWorkbook().getColor(hSSFChart.getMarginColorFormat().getForecolorIndex()));
        }
        HSSFChart.HSSFSeries[] series = hSSFChart.getSeries();
        if (series.length > 0) {
            Ptg[] formulaOfLink = series[0].getDataValues().getFormulaOfLink();
            if (formulaOfLink.length > 0 && (formulaOfLink[0] instanceof Area3DPtg)) {
                Area3DPtg area3DPtg = (Area3DPtg) formulaOfLink[0];
                if (area3DPtg.getFirstColumn() == area3DPtg.getLastColumn()) {
                    for (int firstRow = area3DPtg.getFirstRow(); firstRow <= area3DPtg.getLastRow(); firstRow++) {
                        SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
                        simpleSeriesRenderer.setColor(sheet.getWorkbook().getColor((firstRow - area3DPtg.getFirstRow()) + 24));
                        defaultRenderer.addSeriesRenderer(simpleSeriesRenderer);
                    }
                } else if (area3DPtg.getFirstRow() == area3DPtg.getLastRow()) {
                    for (int firstColumn = area3DPtg.getFirstColumn(); firstColumn <= area3DPtg.getLastColumn(); firstColumn++) {
                        SimpleSeriesRenderer simpleSeriesRenderer2 = new SimpleSeriesRenderer();
                        simpleSeriesRenderer2.setColor(sheet.getWorkbook().getColor((firstColumn - area3DPtg.getFirstColumn()) + 24));
                        defaultRenderer.addSeriesRenderer(simpleSeriesRenderer2);
                    }
                }
            }
        }
        for (SeriesTextRecord next : this.chartSeriesText.keySet()) {
            Record record = this.chartSeriesText.get(next);
            if ((record instanceof ObjectLinkRecord) && ((ObjectLinkRecord) record).getAnchorId() == 1) {
                defaultRenderer.setChartTitle(next.getText());
            }
        }
        return defaultRenderer;
    }

    private String getCategory(Sheet sheet, Area3DPtg area3DPtg, int i) {
        Cell cell;
        Cell cell2;
        if (area3DPtg.getFirstColumn() == area3DPtg.getLastColumn()) {
            Row row = sheet.getRow(area3DPtg.getFirstRow() + i);
            if (row == null || (cell2 = row.getCell(area3DPtg.getFirstColumn())) == null) {
                return String.valueOf(i + 1);
            }
            return getFormatContents(sheet, cell2);
        } else if (area3DPtg.getFirstRow() != area3DPtg.getLastRow()) {
            return "";
        } else {
            Row row2 = sheet.getRow(area3DPtg.getFirstRow());
            if (row2 == null || (cell = row2.getCell(area3DPtg.getFirstColumn() + i)) == null) {
                return String.valueOf(i + 1);
            }
            return getFormatContents(sheet, cell);
        }
    }

    /* access modifiers changed from: protected */
    public CategorySeries buildCategoryDataset(Sheet sheet, HSSFChart hSSFChart) {
        CategorySeries categorySeries;
        Sheet sheet2;
        new CategorySeries("");
        if (hSSFChart.getSeries().length > 0) {
            HSSFChart.HSSFSeries hSSFSeries = hSSFChart.getSeries()[0];
            if (hSSFSeries.getSeriesTitle() != null) {
                categorySeries = new CategorySeries(hSSFSeries.getSeriesTitle());
            } else {
                categorySeries = new CategorySeries("");
            }
            Ptg[] formulaOfLink = hSSFSeries.getDataCategoryLabels().getFormulaOfLink();
            Ptg[] formulaOfLink2 = hSSFSeries.getDataValues().getFormulaOfLink();
            if (formulaOfLink2.length > 0 && (formulaOfLink2[0] instanceof Area3DPtg)) {
                Area3DPtg area3DPtg = (Area3DPtg) formulaOfLink2[0];
                EvaluationWorkbook.ExternalSheet externalSheet = this.workbook.getExternalSheet(area3DPtg.getExternSheetIndex());
                if (externalSheet == null) {
                    sheet2 = sheet.getWorkbook().getSheet(this.workbook.getSheetIndexFromExternSheetIndex(area3DPtg.getExternSheetIndex()));
                } else {
                    sheet2 = sheet.getWorkbook().getSheet(externalSheet.getSheetName());
                }
                if (formulaOfLink.length > 0 && (formulaOfLink[0] instanceof Area3DPtg)) {
                    Area3DPtg area3DPtg2 = (Area3DPtg) formulaOfLink[0];
                    if (area3DPtg.getFirstColumn() == area3DPtg.getLastColumn()) {
                        for (int firstRow = area3DPtg.getFirstRow(); firstRow <= area3DPtg.getLastRow(); firstRow++) {
                            String category = getCategory(sheet2, area3DPtg2, firstRow - area3DPtg.getFirstRow());
                            Row row = sheet2.getRow(firstRow);
                            categorySeries.add(category, row != null ? getCellNumericValue(sheet2, row.getCell(area3DPtg.getFirstColumn())) : 0.0d);
                        }
                    } else if (area3DPtg.getFirstRow() == area3DPtg.getLastRow()) {
                        Row row2 = sheet2.getRow(area3DPtg.getFirstRow());
                        for (int firstColumn = area3DPtg.getFirstColumn(); firstColumn <= area3DPtg.getLastColumn(); firstColumn++) {
                            categorySeries.add(getCategory(sheet2, area3DPtg2, firstColumn - area3DPtg.getFirstColumn()), row2 != null ? getCellNumericValue(sheet2, row2.getCell(firstColumn)) : 0.0d);
                        }
                    }
                } else if (area3DPtg.getFirstColumn() == area3DPtg.getLastColumn()) {
                    for (int firstRow2 = area3DPtg.getFirstRow(); firstRow2 <= area3DPtg.getLastRow(); firstRow2++) {
                        Row row3 = sheet2.getRow(firstRow2);
                        categorySeries.add(row3 != null ? getCellNumericValue(sheet2, row3.getCell(area3DPtg.getFirstColumn())) : 0.0d);
                    }
                } else if (area3DPtg.getFirstRow() == area3DPtg.getLastRow()) {
                    Row row4 = sheet2.getRow(area3DPtg.getFirstRow());
                    for (int firstColumn2 = area3DPtg.getFirstColumn(); firstColumn2 <= area3DPtg.getLastColumn(); firstColumn2++) {
                        categorySeries.add(row4 != null ? getCellNumericValue(sheet2, row4.getCell(firstColumn2)) : 0.0d);
                    }
                }
                return categorySeries;
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004f, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0090, code lost:
        r0 = r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.thirdpart.achartengine.chart.AbstractChart convertToAChart(com.app.office.ss.model.XLSModel.ASheet r9, com.app.office.fc.hssf.usermodel.HSSFChart r10) {
        /*
            r8 = this;
            r0 = 5
            com.app.office.thirdpart.achartengine.chart.PointStyle[] r0 = new com.app.office.thirdpart.achartengine.chart.PointStyle[r0]
            com.app.office.thirdpart.achartengine.chart.PointStyle r1 = com.app.office.thirdpart.achartengine.chart.PointStyle.DIAMOND
            r2 = 0
            r0[r2] = r1
            com.app.office.thirdpart.achartengine.chart.PointStyle r1 = com.app.office.thirdpart.achartengine.chart.PointStyle.SQUARE
            r3 = 1
            r0[r3] = r1
            com.app.office.thirdpart.achartengine.chart.PointStyle r1 = com.app.office.thirdpart.achartengine.chart.PointStyle.TRIANGLE
            r4 = 2
            r0[r4] = r1
            com.app.office.thirdpart.achartengine.chart.PointStyle r1 = com.app.office.thirdpart.achartengine.chart.PointStyle.X
            r5 = 3
            r0[r5] = r1
            com.app.office.thirdpart.achartengine.chart.PointStyle r1 = com.app.office.thirdpart.achartengine.chart.PointStyle.CIRCLE
            r6 = 4
            r0[r6] = r1
            short r1 = r8.getChartType(r10)
            r7 = 0
            if (r1 == r4) goto L_0x0089
            if (r1 == r5) goto L_0x0073
            if (r1 == r6) goto L_0x0048
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r8.buildXYMultipleSeriesRenderer(r9, r10)     // Catch:{ Exception -> 0x0047 }
            if (r0 != 0) goto L_0x002e
            return r7
        L_0x002e:
            r1 = r0
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = (com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer) r1     // Catch:{ Exception -> 0x0047 }
            com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset r9 = r8.getXYMultipleSeriesDataset(r9, r10, r1, r3)     // Catch:{ Exception -> 0x0047 }
            if (r9 != 0) goto L_0x0038
            return r7
        L_0x0038:
            r1 = r0
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = (com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer) r1     // Catch:{ Exception -> 0x0047 }
            r8.setChartSettings(r1, r9, r10)     // Catch:{ Exception -> 0x0047 }
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = (com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer) r0     // Catch:{ Exception -> 0x0047 }
            com.app.office.thirdpart.achartengine.chart.ColumnBarChart$Type r10 = com.app.office.thirdpart.achartengine.chart.ColumnBarChart.Type.DEFAULT     // Catch:{ Exception -> 0x0047 }
            com.app.office.thirdpart.achartengine.chart.AbstractChart r9 = com.app.office.thirdpart.achartengine.ChartFactory.getColumnBarChart(r9, r0, r10)     // Catch:{ Exception -> 0x0047 }
            return r9
        L_0x0047:
            return r7
        L_0x0048:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r8.buildXYMultipleSeriesRenderer(r9, r10, r0)
            if (r0 != 0) goto L_0x004f
            return r7
        L_0x004f:
            r1 = r0
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = (com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer) r1
            com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset r9 = r8.getXYMultipleSeriesDataset(r9, r10, r1, r6)
            if (r9 != 0) goto L_0x0059
            return r7
        L_0x0059:
            r8.setChartSettings(r1, r9, r10)
        L_0x005c:
            int r10 = r0.getSeriesRendererCount()
            if (r2 >= r10) goto L_0x006e
            com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer r10 = r0.getSeriesRendererAt(r2)
            com.app.office.thirdpart.achartengine.renderers.XYSeriesRenderer r10 = (com.app.office.thirdpart.achartengine.renderers.XYSeriesRenderer) r10
            r10.setFillPoints(r3)
            int r2 = r2 + 1
            goto L_0x005c
        L_0x006e:
            com.app.office.thirdpart.achartengine.chart.AbstractChart r9 = com.app.office.thirdpart.achartengine.ChartFactory.getScatterChart(r9, r1)
            return r9
        L_0x0073:
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r0 = r8.buildDefaultRenderer(r9, r10)
            if (r0 != 0) goto L_0x007a
            return r7
        L_0x007a:
            r0.setZoomEnabled(r3)
            com.app.office.thirdpart.achartengine.model.CategorySeries r9 = r8.buildCategoryDataset(r9, r10)
            if (r9 != 0) goto L_0x0084
            return r7
        L_0x0084:
            com.app.office.thirdpart.achartengine.chart.AbstractChart r9 = com.app.office.thirdpart.achartengine.ChartFactory.getPieChart(r9, r0)
            return r9
        L_0x0089:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r8.buildXYMultipleSeriesRenderer(r9, r10, r0)
            if (r0 != 0) goto L_0x0090
            return r7
        L_0x0090:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = (com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer) r0
            com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset r9 = r8.getXYMultipleSeriesDataset(r9, r10, r0, r4)
            if (r9 != 0) goto L_0x0099
            return r7
        L_0x0099:
            r8.setChartSettings(r0, r9, r10)
            r10 = 10
            r0.setYLabels(r10)
            com.app.office.thirdpart.achartengine.chart.AbstractChart r9 = com.app.office.thirdpart.achartengine.ChartFactory.getLineChart(r9, r0)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.xls.ChartConverter.convertToAChart(com.app.office.ss.model.XLSModel.ASheet, com.app.office.fc.hssf.usermodel.HSSFChart):com.app.office.thirdpart.achartengine.chart.AbstractChart");
    }

    public AbstractChart getAChart() {
        return this.chart;
    }

    private void dispose() {
        this.workbook = null;
        this.chart = null;
        this.chartSeriesText = null;
    }
}
