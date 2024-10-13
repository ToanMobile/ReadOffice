package com.app.office.fc.xls.Reader.drawing;

import com.itextpdf.text.html.HtmlTags;
import com.app.office.constant.SchemeClrConstant;
import com.app.office.fc.dom4j.Element;
import com.app.office.ss.model.drawing.TextParagraph;
import com.app.office.ss.util.ColorUtil;
import com.app.office.thirdpart.achartengine.ChartFactory;
import com.app.office.thirdpart.achartengine.chart.AbstractChart;
import com.app.office.thirdpart.achartengine.chart.ColumnBarChart;
import com.app.office.thirdpart.achartengine.chart.PointStyle;
import com.app.office.thirdpart.achartengine.model.CategorySeries;
import com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset;
import com.app.office.thirdpart.achartengine.model.XYSeries;
import com.app.office.thirdpart.achartengine.renderers.DefaultRenderer;
import com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYSeriesRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartReader {
    private static final short AxisPosition_Bottom = 0;
    private static final short AxisPosition_Left = 1;
    private static final short AxisPosition_Right = 2;
    private static final short AxisPosition_Top = 3;
    private static ChartReader reader = new ChartReader();
    private static final String[] themeIndex = {SchemeClrConstant.SCHEME_ACCENT1, SchemeClrConstant.SCHEME_ACCENT2, SchemeClrConstant.SCHEME_ACCENT3, SchemeClrConstant.SCHEME_ACCENT4, SchemeClrConstant.SCHEME_ACCENT5, SchemeClrConstant.SCHEME_ACCENT6};
    private static final double[] tints = {-0.25d, 0.0d, 0.4d, 0.6d, 0.8d, -0.5d};
    private Element chart;
    private boolean hasMaxX;
    private boolean hasMaxY;
    private boolean hasMinX;
    private boolean hasMinY;
    private double maxX;
    private double maxY;
    private double minX;
    private double minY;
    private Map<String, Integer> schemeColor;
    private short type;

    public static ChartReader instance() {
        return reader;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: com.app.office.common.bg.BackgroundAndFill} */
    /* JADX WARNING: type inference failed for: r5v1 */
    /* JADX WARNING: type inference failed for: r5v2, types: [com.app.office.thirdpart.achartengine.renderers.DefaultRenderer] */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: type inference failed for: r5v9 */
    /* JADX WARNING: type inference failed for: r5v10 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x010e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.thirdpart.achartengine.chart.AbstractChart read(com.app.office.system.IControl r17, com.app.office.fc.openxml4j.opc.ZipPackage r18, com.app.office.fc.openxml4j.opc.PackagePart r19, java.util.Map<java.lang.String, java.lang.Integer> r20, byte r21) throws java.lang.Exception {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r0.schemeColor = r4
            r5 = 10
            r0.type = r5
            r5 = 0
            r0.chart = r5
            r6 = 1
            r0.maxX = r6
            r8 = 9218868437227405311(0x7fefffffffffffff, double:1.7976931348623157E308)
            r0.minX = r8
            r0.maxY = r6
            r0.minY = r8
            r6 = 0
            r0.hasMaxX = r6
            r0.hasMinX = r6
            r0.hasMaxY = r6
            r0.hasMinY = r6
            com.app.office.fc.dom4j.io.SAXReader r7 = new com.app.office.fc.dom4j.io.SAXReader
            r7.<init>()
            java.io.InputStream r8 = r19.getInputStream()
            com.app.office.fc.dom4j.Document r7 = r7.read((java.io.InputStream) r8)
            r8.close()
            com.app.office.fc.dom4j.Element r7 = r7.getRootElement()
            java.lang.String r8 = "spPr"
            com.app.office.fc.dom4j.Element r9 = r7.element((java.lang.String) r8)
            java.lang.String r10 = "noFill"
            r11 = 1
            r12 = -9145228(0xffffffffff747474, float:-3.249363E38)
            r13 = -1
            java.lang.String r14 = "ln"
            if (r9 == 0) goto L_0x008f
            com.app.office.fc.dom4j.Element r15 = r9.element((java.lang.String) r10)
            if (r15 != 0) goto L_0x0068
            com.app.office.common.bg.BackgroundAndFill r15 = com.app.office.common.autoshape.AutoShapeDataKit.processBackground(r1, r2, r3, r9, r4)
            if (r15 != 0) goto L_0x0069
            com.app.office.common.bg.BackgroundAndFill r15 = new com.app.office.common.bg.BackgroundAndFill
            r15.<init>()
            r15.setFillType(r6)
            r15.setForegroundColor(r13)
            goto L_0x0069
        L_0x0068:
            r15 = r5
        L_0x0069:
            com.app.office.fc.dom4j.Element r13 = r9.element((java.lang.String) r14)
            if (r13 == 0) goto L_0x0078
            com.app.office.fc.dom4j.Element r6 = r9.element((java.lang.String) r14)
            com.app.office.common.borders.Line r6 = com.app.office.fc.LineKit.createChartLine(r1, r2, r3, r6, r4)
            goto L_0x00b1
        L_0x0078:
            com.app.office.common.borders.Line r9 = new com.app.office.common.borders.Line
            r9.<init>()
            com.app.office.common.bg.BackgroundAndFill r13 = new com.app.office.common.bg.BackgroundAndFill
            r13.<init>()
            r13.setFillType(r6)
            r13.setForegroundColor(r12)
            r9.setBackgroundAndFill(r13)
            r9.setLineWidth(r11)
            goto L_0x00b0
        L_0x008f:
            com.app.office.common.bg.BackgroundAndFill r15 = new com.app.office.common.bg.BackgroundAndFill
            r15.<init>()
            r15.setFillType(r6)
            r15.setForegroundColor(r13)
            com.app.office.common.borders.Line r9 = new com.app.office.common.borders.Line
            r9.<init>()
            com.app.office.common.bg.BackgroundAndFill r13 = new com.app.office.common.bg.BackgroundAndFill
            r13.<init>()
            r13.setFillType(r6)
            r13.setForegroundColor(r12)
            r9.setBackgroundAndFill(r13)
            r9.setLineWidth(r11)
        L_0x00b0:
            r6 = r9
        L_0x00b1:
            java.lang.String r9 = "txPr"
            com.app.office.fc.dom4j.Element r9 = r7.element((java.lang.String) r9)
            float r9 = r0.getTextSize(r9)
            java.lang.String r11 = "chart"
            com.app.office.fc.dom4j.Element r12 = r7.element((java.lang.String) r11)
            r13 = r21
            com.app.office.thirdpart.achartengine.chart.AbstractChart r12 = r0.buildAChart(r12, r9, r13)
            boolean r13 = r12 instanceof com.app.office.thirdpart.achartengine.chart.XYChart
            if (r13 == 0) goto L_0x0101
            com.app.office.fc.dom4j.Element r7 = r7.element((java.lang.String) r11)
            java.lang.String r11 = "plotArea"
            com.app.office.fc.dom4j.Element r7 = r7.element((java.lang.String) r11)
            com.app.office.fc.dom4j.Element r7 = r7.element((java.lang.String) r8)
            if (r7 == 0) goto L_0x00ee
            com.app.office.fc.dom4j.Element r8 = r7.element((java.lang.String) r10)
            if (r8 != 0) goto L_0x00e5
            com.app.office.common.bg.BackgroundAndFill r5 = com.app.office.common.autoshape.AutoShapeDataKit.processBackground(r1, r2, r3, r7, r4)
        L_0x00e5:
            com.app.office.fc.dom4j.Element r7 = r7.element((java.lang.String) r14)
            com.app.office.common.borders.Line r1 = com.app.office.fc.LineKit.createChartLine(r1, r2, r3, r7, r4)
            goto L_0x00ef
        L_0x00ee:
            r1 = r5
        L_0x00ef:
            r2 = r12
            com.app.office.thirdpart.achartengine.chart.XYChart r2 = (com.app.office.thirdpart.achartengine.chart.XYChart) r2
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r2 = r2.getRenderer()
            r3 = r2
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r3 = (com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer) r3
            r3.setSeriesBackgroundColor(r5)
            r3.setSeriesFrame(r1)
            r5 = r2
            goto L_0x010c
        L_0x0101:
            boolean r1 = r12 instanceof com.app.office.thirdpart.achartengine.chart.RoundChart
            if (r1 == 0) goto L_0x010c
            r1 = r12
            com.app.office.thirdpart.achartengine.chart.RoundChart r1 = (com.app.office.thirdpart.achartengine.chart.RoundChart) r1
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r5 = r1.getRenderer()
        L_0x010c:
            if (r5 == 0) goto L_0x0117
            r5.setDefaultFontSize(r9)
            r5.setBackgroundAndFill(r15)
            r5.setChartFrame(r6)
        L_0x0117:
            r16.dispose()
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.xls.Reader.drawing.ChartReader.read(com.app.office.system.IControl, com.app.office.fc.openxml4j.opc.ZipPackage, com.app.office.fc.openxml4j.opc.PackagePart, java.util.Map, byte):com.app.office.thirdpart.achartengine.chart.AbstractChart");
    }

    private float getTextSize(Element element) {
        Element element2;
        Element element3;
        Element element4;
        String attributeValue;
        if (element == null || (element2 = element.element("p")) == null || (element3 = element2.element("pPr")) == null || (element4 = element3.element("defRPr")) == null || (attributeValue = element4.attributeValue("sz")) == null || attributeValue.length() <= 0) {
            return 12.0f;
        }
        return ((float) Integer.parseInt(attributeValue)) / 100.0f;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0048  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processLegend(com.app.office.fc.dom4j.Element r5, com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r6, com.app.office.thirdpart.achartengine.chart.AbstractChart r7) {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x0053
            if (r6 == 0) goto L_0x0053
            r0 = 1
            r6.setShowLegend(r0)
            r1 = 2
            java.lang.String r2 = "legendPos"
            com.app.office.fc.dom4j.Element r3 = r5.element((java.lang.String) r2)
            if (r3 == 0) goto L_0x0038
            com.app.office.fc.dom4j.Element r2 = r5.element((java.lang.String) r2)
            java.lang.String r3 = "val"
            java.lang.String r2 = r2.attributeValue((java.lang.String) r3)
            java.lang.String r3 = "l"
            boolean r3 = r3.equalsIgnoreCase(r2)
            if (r3 == 0) goto L_0x0025
            r0 = 0
            goto L_0x0039
        L_0x0025:
            java.lang.String r3 = "t"
            boolean r3 = r3.equalsIgnoreCase(r2)
            if (r3 == 0) goto L_0x002e
            goto L_0x0039
        L_0x002e:
            java.lang.String r0 = "b"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0038
            r0 = 3
            goto L_0x0039
        L_0x0038:
            r0 = 2
        L_0x0039:
            r7.setLegendPosition(r0)
            float r7 = r6.getDefaultFontSize()
            java.lang.String r0 = "txPr"
            com.app.office.fc.dom4j.Element r1 = r5.element((java.lang.String) r0)
            if (r1 == 0) goto L_0x0050
            com.app.office.fc.dom4j.Element r5 = r5.element((java.lang.String) r0)
            float r7 = r4.getTextSize(r5)
        L_0x0050:
            r6.setLegendTextSize(r7)
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.xls.Reader.drawing.ChartReader.processLegend(com.app.office.fc.dom4j.Element, com.app.office.thirdpart.achartengine.renderers.DefaultRenderer, com.app.office.thirdpart.achartengine.chart.AbstractChart):void");
    }

    private AbstractChart buildAChart(Element element, float f, byte b) {
        AbstractChart abstractChart;
        XYMultipleSeriesDataset xYMultipleSeriesDataset;
        CategorySeries categorySeries;
        float f2;
        XYMultipleSeriesRenderer xYMultipleSeriesRenderer;
        Element element2 = element;
        float f3 = f;
        byte b2 = b;
        Element element3 = element2.element("plotArea");
        PointStyle[] pointStyleArr = {PointStyle.DIAMOND, PointStyle.SQUARE, PointStyle.TRIANGLE, PointStyle.X, PointStyle.CIRCLE};
        getChartInfo(element3);
        XYMultipleSeriesRenderer xYMultipleSeriesRenderer2 = null;
        switch (this.type) {
            case 0:
            case 1:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                xYMultipleSeriesRenderer = buildXYMultipleSeriesRenderer(element3, f3, b2);
                XYMultipleSeriesRenderer xYMultipleSeriesRenderer3 = xYMultipleSeriesRenderer;
                xYMultipleSeriesDataset = getXYMultipleSeriesDataset(this.chart, this.type, xYMultipleSeriesRenderer3);
                abstractChart = ChartFactory.getColumnBarChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer3, ColumnBarChart.Type.DEFAULT);
                break;
            case 2:
                xYMultipleSeriesRenderer = buildXYMultipleSeriesRenderer(element3, f3, b2);
                XYMultipleSeriesRenderer xYMultipleSeriesRenderer4 = xYMultipleSeriesRenderer;
                xYMultipleSeriesDataset = getXYMultipleSeriesDataset(this.chart, this.type, xYMultipleSeriesRenderer4, pointStyleArr);
                xYMultipleSeriesRenderer4.setYLabels(10);
                abstractChart = ChartFactory.getLineChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer4);
                break;
            case 3:
                DefaultRenderer buildDefaultRenderer = buildDefaultRenderer();
                buildDefaultRenderer.setZoomEnabled(true);
                CategorySeries buildCategoryDataset = buildCategoryDataset(this.chart, buildDefaultRenderer);
                abstractChart = ChartFactory.getPieChart(buildCategoryDataset, buildDefaultRenderer);
                xYMultipleSeriesRenderer2 = buildDefaultRenderer;
                categorySeries = buildCategoryDataset;
                xYMultipleSeriesDataset = null;
                break;
            case 4:
                xYMultipleSeriesRenderer = buildXYMultipleSeriesRenderer(element3, f3, b2);
                XYMultipleSeriesRenderer xYMultipleSeriesRenderer5 = xYMultipleSeriesRenderer;
                xYMultipleSeriesDataset = getXYMultipleSeriesDataset(this.chart, this.type, xYMultipleSeriesRenderer5, pointStyleArr);
                xYMultipleSeriesRenderer5.setXLabels(10);
                xYMultipleSeriesRenderer5.setYLabels(10);
                for (int i = 0; i < xYMultipleSeriesRenderer.getSeriesRendererCount(); i++) {
                    ((XYSeriesRenderer) xYMultipleSeriesRenderer.getSeriesRendererAt(i)).setFillPoints(true);
                }
                abstractChart = ChartFactory.getScatterChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer5);
                break;
            default:
                categorySeries = null;
                xYMultipleSeriesDataset = null;
                abstractChart = null;
                break;
        }
        xYMultipleSeriesRenderer2 = xYMultipleSeriesRenderer;
        categorySeries = null;
        Element element4 = element2.element("title");
        if (element4 != null) {
            List<TextParagraph> title = getTitle(element4);
            String str = "";
            if (title == null || title.size() <= 0) {
                f2 = 0.0f;
            } else {
                f2 = 0.0f;
                for (int i2 = 0; i2 < title.size(); i2++) {
                    str = str + title.get(i2).getTextRun();
                    if (title.get(i2).getFont() != null) {
                        f2 = Math.max(f2, (float) ((int) title.get(i2).getFont().getFontSize()));
                    }
                }
            }
            xYMultipleSeriesRenderer2.setShowChartTitle(true);
            if (f2 == 0.0f) {
                f2 = f3;
            }
            xYMultipleSeriesRenderer2.setChartTitleTextSize(f2);
            if (str.length() == 0) {
                if (xYMultipleSeriesDataset != null) {
                    str = (xYMultipleSeriesDataset == null || xYMultipleSeriesDataset.getSeriesCount() != 1) ? "Chart Title" : xYMultipleSeriesDataset.getSeriesAt(0).getTitle();
                } else if (categorySeries != null) {
                    str = categorySeries.getTitle();
                }
            }
            xYMultipleSeriesRenderer2.setChartTitle(str);
        } else {
            xYMultipleSeriesRenderer2.setShowChartTitle(false);
        }
        if (element2.element("legend") != null) {
            processLegend(element2.element("legend"), xYMultipleSeriesRenderer2, abstractChart);
        } else {
            xYMultipleSeriesRenderer2.setShowLegend(false);
        }
        if (abstractChart != null) {
            abstractChart.setCategoryAxisTextColor(this.schemeColor.get(SchemeClrConstant.SCHEME_TX1).intValue());
        }
        return abstractChart;
    }

    private List<TextParagraph> getTitle(Element element) {
        if (element == null || element.element("tx") == null || element.element("tx").element("rich") == null) {
            return null;
        }
        Element element2 = element.element("tx").element("rich");
        Element element3 = element2.element("bodyPr");
        short verticalByString = element3 != null ? DrawingReader.getVerticalByString(element3.attributeValue("anchor")) : -1;
        ArrayList arrayList = new ArrayList();
        for (Element textParagraph : element2.elements("p")) {
            TextParagraph textParagraph2 = DrawingReader.getTextParagraph(textParagraph);
            if (textParagraph2 != null) {
                if (verticalByString > -1) {
                    textParagraph2.setVerticalAlign(verticalByString);
                }
                arrayList.add(textParagraph2);
            }
        }
        return arrayList;
    }

    private int getColor(Element element) {
        int parseInt;
        if (element.element("srgbClr") != null) {
            String attributeValue = element.element("srgbClr").attributeValue("val");
            if (attributeValue.length() > 6) {
                attributeValue = attributeValue.substring(attributeValue.length() - 6);
            }
            parseInt = Integer.parseInt(attributeValue, 16);
        } else if (element.element("schemeClr") != null) {
            Element element2 = element.element("schemeClr");
            int intValue = this.schemeColor.get(element2.attributeValue("val")).intValue();
            if (element2.element("lumMod") == null) {
                return intValue;
            }
            return ColorUtil.instance().getColorWithTint(intValue, element2.element("lumOff") != null ? ((double) Integer.parseInt(element2.element("lumOff").attributeValue("val"))) / 100000.0d : (((double) Integer.parseInt(element2.element("lumMod").attributeValue("val"))) / 100000.0d) - 1.0d);
        } else if (element.element("sysClr") == null) {
            return -1;
        } else {
            parseInt = Integer.parseInt(element.element("sysClr").attributeValue("lastClr"), 16);
        }
        return parseInt | -16777216;
    }

    private void getChartInfo(Element element) {
        if (element.element("barChart") != null) {
            this.chart = element.element("barChart");
            this.type = 1;
        } else if (element.element("bar3DChart") != null) {
            this.chart = element.element("bar3DChart");
            this.type = 1;
        } else if (element.element("pieChart") != null) {
            this.chart = element.element("pieChart");
            this.type = 3;
        } else if (element.element("pie3DChart") != null) {
            this.chart = element.element("pie3DChart");
            this.type = 3;
        } else if (element.element("ofPieChart") != null) {
            this.chart = element.element("ofPieChart");
            this.type = 3;
        } else if (element.element("lineChart") != null) {
            this.chart = element.element("lineChart");
            this.type = 2;
        } else if (element.element("line3DChart") != null) {
            this.chart = element.element("line3DChart");
            this.type = 2;
        } else if (element.element("scatterChart") != null) {
            this.chart = element.element("scatterChart");
            this.type = 4;
        } else if (element.element("areaChart") != null) {
            this.chart = element.element("areaChart");
            this.type = 0;
        } else if (element.element("area3DChart") != null) {
            this.chart = element.element("area3DChart");
            this.type = 0;
        } else if (element.element("stockChart") != null) {
            this.chart = element.element("stockChart");
            this.type = 5;
        } else if (element.element("surfaceChart") != null) {
            this.chart = element.element("surfaceChart");
            this.type = 6;
        } else if (element.element("surface3DChart") != null) {
            this.chart = element.element("surface3DChart");
            this.type = 6;
        } else if (element.element("doughnutChart") != null) {
            this.chart = element.element("doughnutChart");
            this.type = 7;
        } else if (element.element("bubbleChart") != null) {
            this.chart = element.element("bubbleChart");
            this.type = 4;
        } else if (element.element("radarChart") != null) {
            this.chart = element.element("radarChart");
            this.type = 9;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0070, code lost:
        if (r3 != 3) goto L_0x00c6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer buildXYMultipleSeriesRenderer(com.app.office.fc.dom4j.Element r10, float r11, byte r12) {
        /*
            r9 = this;
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r12 = new com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer
            r12.<init>()
            r12.setXTitleTextSize(r11)
            r12.setYTitleTextSize(r11)
            r12.setLabelsTextSize(r11)
            java.lang.String r11 = "valAx"
            java.util.List r11 = r10.elements((java.lang.String) r11)
            java.lang.String r0 = "catAx"
            java.util.List r10 = r10.elements((java.lang.String) r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            r2 = 0
        L_0x0021:
            int r3 = r11.size()
            if (r2 >= r3) goto L_0x0033
            java.lang.Object r3 = r11.get(r2)
            com.app.office.fc.dom4j.Element r3 = (com.app.office.fc.dom4j.Element) r3
            r0.add(r3)
            int r2 = r2 + 1
            goto L_0x0021
        L_0x0033:
            r11 = 0
        L_0x0034:
            int r2 = r10.size()
            if (r11 >= r2) goto L_0x0046
            java.lang.Object r2 = r10.get(r11)
            com.app.office.fc.dom4j.Element r2 = (com.app.office.fc.dom4j.Element) r2
            r0.add(r2)
            int r11 = r11 + 1
            goto L_0x0034
        L_0x0046:
            r10 = 0
            r11 = r10
            r2 = 0
        L_0x0049:
            int r3 = r0.size()
            if (r2 >= r3) goto L_0x00d9
            java.lang.Object r3 = r0.get(r2)
            com.app.office.fc.dom4j.Element r3 = (com.app.office.fc.dom4j.Element) r3
            java.lang.String r4 = "axPos"
            com.app.office.fc.dom4j.Element r3 = r3.element((java.lang.String) r4)
            if (r3 == 0) goto L_0x00d5
            short r3 = r9.getAxisPosition(r3)
            java.lang.String r4 = "minorGridlines"
            java.lang.String r5 = "majorGridlines"
            java.lang.String r6 = "title"
            r7 = 1
            if (r3 == 0) goto L_0x009d
            if (r3 == r7) goto L_0x0073
            r8 = 2
            if (r3 == r8) goto L_0x0073
            r8 = 3
            if (r3 == r8) goto L_0x009d
            goto L_0x00c6
        L_0x0073:
            java.lang.Object r11 = r0.get(r2)
            com.app.office.fc.dom4j.Element r11 = (com.app.office.fc.dom4j.Element) r11
            com.app.office.fc.dom4j.Element r11 = r11.element((java.lang.String) r6)
            java.util.List r11 = r9.getTitle(r11)
            java.lang.Object r6 = r0.get(r2)
            com.app.office.fc.dom4j.Element r6 = (com.app.office.fc.dom4j.Element) r6
            com.app.office.fc.dom4j.Element r5 = r6.element((java.lang.String) r5)
            if (r5 != 0) goto L_0x0099
            java.lang.Object r5 = r0.get(r2)
            com.app.office.fc.dom4j.Element r5 = (com.app.office.fc.dom4j.Element) r5
            com.app.office.fc.dom4j.Element r4 = r5.element((java.lang.String) r4)
            if (r4 == 0) goto L_0x00c6
        L_0x0099:
            r12.setShowGridH(r7)
            goto L_0x00c6
        L_0x009d:
            java.lang.Object r10 = r0.get(r2)
            com.app.office.fc.dom4j.Element r10 = (com.app.office.fc.dom4j.Element) r10
            com.app.office.fc.dom4j.Element r10 = r10.element((java.lang.String) r6)
            java.util.List r10 = r9.getTitle(r10)
            java.lang.Object r6 = r0.get(r2)
            com.app.office.fc.dom4j.Element r6 = (com.app.office.fc.dom4j.Element) r6
            com.app.office.fc.dom4j.Element r5 = r6.element((java.lang.String) r5)
            if (r5 != 0) goto L_0x00c3
            java.lang.Object r5 = r0.get(r2)
            com.app.office.fc.dom4j.Element r5 = (com.app.office.fc.dom4j.Element) r5
            com.app.office.fc.dom4j.Element r4 = r5.element((java.lang.String) r4)
            if (r4 == 0) goto L_0x00c6
        L_0x00c3:
            r12.setShowGridV(r7)
        L_0x00c6:
            java.lang.Object r4 = r0.get(r2)
            com.app.office.fc.dom4j.Element r4 = (com.app.office.fc.dom4j.Element) r4
            java.lang.String r5 = "scaling"
            com.app.office.fc.dom4j.Element r4 = r4.element((java.lang.String) r5)
            r9.getMaxMinValue(r4, r3)
        L_0x00d5:
            int r2 = r2 + 1
            goto L_0x0049
        L_0x00d9:
            if (r10 == 0) goto L_0x00ee
            int r0 = r10.size()
            if (r0 <= 0) goto L_0x00ee
            java.lang.Object r10 = r10.get(r1)
            com.app.office.ss.model.drawing.TextParagraph r10 = (com.app.office.ss.model.drawing.TextParagraph) r10
            java.lang.String r10 = r10.getTextRun()
            r12.setXTitle(r10)
        L_0x00ee:
            if (r11 == 0) goto L_0x0103
            int r10 = r11.size()
            if (r10 <= 0) goto L_0x0103
            java.lang.Object r10 = r11.get(r1)
            com.app.office.ss.model.drawing.TextParagraph r10 = (com.app.office.ss.model.drawing.TextParagraph) r10
            java.lang.String r10 = r10.getTextRun()
            r12.setYTitle(r10)
        L_0x0103:
            java.util.Map<java.lang.String, java.lang.Integer> r10 = r9.schemeColor
            java.lang.String r11 = "tx1"
            java.lang.Object r10 = r10.get(r11)
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r10 = r10.intValue()
            r12.setLabelsColor(r10)
            java.util.Map<java.lang.String, java.lang.Integer> r10 = r9.schemeColor
            java.lang.Object r10 = r10.get(r11)
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r10 = r10.intValue()
            r12.setGridColor(r10)
            java.util.Map<java.lang.String, java.lang.Integer> r10 = r9.schemeColor
            java.lang.Object r10 = r10.get(r11)
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r10 = r10.intValue()
            r12.setAxesColor(r10)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.xls.Reader.drawing.ChartReader.buildXYMultipleSeriesRenderer(com.app.office.fc.dom4j.Element, float, byte):com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer");
    }

    private short getAxisPosition(Element element) {
        String attributeValue;
        if (!(element == null || (attributeValue = element.attributeValue("val")) == null)) {
            if (HtmlTags.B.equalsIgnoreCase(attributeValue)) {
                return 0;
            }
            if ("l".equalsIgnoreCase(attributeValue)) {
                return 1;
            }
            if ("r".equalsIgnoreCase(attributeValue)) {
                return 2;
            }
            if ("t".equalsIgnoreCase(attributeValue)) {
                return 3;
            }
        }
        return 1;
    }

    private void getMaxMinValue(Element element, short s) {
        if (s != 0) {
            if (s == 1 || s == 2) {
                if (element.element("max") != null) {
                    this.hasMaxY = true;
                    this.maxY = Double.parseDouble(element.element("max").attributeValue("val"));
                }
                if (element.element("min") != null) {
                    this.hasMinY = true;
                    this.minY = Double.parseDouble(element.element("min").attributeValue("val"));
                    return;
                }
                return;
            } else if (s != 3) {
                return;
            }
        }
        if (element.element("max") != null) {
            this.hasMaxX = true;
            this.maxX = Double.parseDouble(element.element("max").attributeValue("val"));
        }
        if (element.element("min") != null) {
            this.hasMinX = true;
            this.minX = Double.parseDouble(element.element("min").attributeValue("val"));
        }
    }

    private String getSeriesTitle(Element element) {
        if (element == null) {
            return null;
        }
        if (element.element("strRef") != null) {
            Element element2 = element.element("strRef").element("strCache");
            if (element2.element("pt") != null) {
                return element2.element("pt").element("v").getText();
            }
        } else if (element.element("v") != null) {
            return element.element("v").getText();
        }
        return null;
    }

    private void setSeriesRendererProp(XYMultipleSeriesRenderer xYMultipleSeriesRenderer, Element element, PointStyle[] pointStyleArr) {
        int parseInt = Integer.parseInt(element.element("order").attributeValue("val"));
        XYSeriesRenderer xYSeriesRenderer = new XYSeriesRenderer();
        String[] strArr = themeIndex;
        xYSeriesRenderer.setColor(ColorUtil.instance().getColorWithTint(this.schemeColor.get(strArr[parseInt % strArr.length]).intValue(), tints[parseInt / strArr.length]));
        int i = 1;
        if (pointStyleArr != null && pointStyleArr.length > 0) {
            xYSeriesRenderer.setPointStyle(pointStyleArr[parseInt % (pointStyleArr.length - 1)]);
        }
        xYMultipleSeriesRenderer.addSeriesRenderer(xYSeriesRenderer);
        Element element2 = null;
        if (element.element("cat") != null) {
            if (element.element("cat").element("numRef") != null) {
                element2 = element.element("cat").element("numRef").element("numCache");
            } else if (element.element("cat").element("strRef") != null) {
                element2 = element.element("cat").element("strRef").element("strCache");
            }
        } else if (!(element.element("xVal") == null || element.element("xVal").element("strRef") == null)) {
            element2 = element.element("xVal").element("strRef").element("strCache");
        }
        if (element2 != null) {
            for (Element element3 : element2.elements("pt")) {
                xYMultipleSeriesRenderer.addXTextLabel((double) i, element3.element("v").getText());
                i++;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x0142  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.Object getSeries(com.app.office.fc.dom4j.Element r10, short r11) {
        /*
            r9 = this;
            java.lang.String r0 = "tx"
            com.app.office.fc.dom4j.Element r1 = r10.element((java.lang.String) r0)
            java.lang.String r1 = r9.getSeriesTitle(r1)
            java.lang.String r2 = "val"
            if (r1 == 0) goto L_0x0017
            com.app.office.fc.dom4j.Element r0 = r10.element((java.lang.String) r0)
            java.lang.String r0 = r9.getSeriesTitle(r0)
            goto L_0x0038
        L_0x0017:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Series "
            r0.append(r1)
            java.lang.String r1 = "order"
            com.app.office.fc.dom4j.Element r1 = r10.element((java.lang.String) r1)
            java.lang.String r1 = r1.attributeValue((java.lang.String) r2)
            int r1 = java.lang.Integer.parseInt(r1)
            int r1 = r1 + 1
            r0.append(r1)
            java.lang.String r0 = r0.toString()
        L_0x0038:
            r1 = 4
            java.lang.String r3 = "numCache"
            java.lang.String r4 = "v"
            java.lang.String r5 = "pt"
            java.lang.String r6 = "numLit"
            r7 = 0
            java.lang.String r8 = "numRef"
            if (r11 == r1) goto L_0x009f
            com.app.office.fc.dom4j.Element r11 = r10.element((java.lang.String) r2)
            if (r11 == 0) goto L_0x016c
            com.app.office.thirdpart.achartengine.model.CategorySeries r11 = new com.app.office.thirdpart.achartengine.model.CategorySeries
            r11.<init>(r0)
            com.app.office.fc.dom4j.Element r0 = r10.element((java.lang.String) r2)
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r8)
            if (r0 == 0) goto L_0x0068
            com.app.office.fc.dom4j.Element r10 = r10.element((java.lang.String) r2)
            com.app.office.fc.dom4j.Element r10 = r10.element((java.lang.String) r8)
            com.app.office.fc.dom4j.Element r7 = r10.element((java.lang.String) r3)
            goto L_0x007a
        L_0x0068:
            com.app.office.fc.dom4j.Element r0 = r10.element((java.lang.String) r2)
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r6)
            if (r0 == 0) goto L_0x007a
            com.app.office.fc.dom4j.Element r10 = r10.element((java.lang.String) r2)
            com.app.office.fc.dom4j.Element r7 = r10.element((java.lang.String) r6)
        L_0x007a:
            java.util.List r10 = r7.elements((java.lang.String) r5)
            java.util.Iterator r10 = r10.iterator()
        L_0x0082:
            boolean r0 = r10.hasNext()
            if (r0 == 0) goto L_0x009e
            java.lang.Object r0 = r10.next()
            com.app.office.fc.dom4j.Element r0 = (com.app.office.fc.dom4j.Element) r0
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r4)
            java.lang.String r0 = r0.getText()
            double r0 = java.lang.Double.parseDouble(r0)
            r11.add(r0)
            goto L_0x0082
        L_0x009e:
            return r11
        L_0x009f:
            java.lang.String r11 = "xVal"
            com.app.office.fc.dom4j.Element r1 = r10.element((java.lang.String) r11)
            if (r1 == 0) goto L_0x00be
            com.app.office.fc.dom4j.Element r1 = r10.element((java.lang.String) r11)
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r8)
            if (r1 == 0) goto L_0x00be
            com.app.office.fc.dom4j.Element r11 = r10.element((java.lang.String) r11)
            com.app.office.fc.dom4j.Element r11 = r11.element((java.lang.String) r8)
            com.app.office.fc.dom4j.Element r11 = r11.element((java.lang.String) r3)
            goto L_0x00bf
        L_0x00be:
            r11 = r7
        L_0x00bf:
            java.lang.String r1 = "yVal"
            com.app.office.fc.dom4j.Element r2 = r10.element((java.lang.String) r1)
            if (r2 == 0) goto L_0x00f1
            com.app.office.fc.dom4j.Element r2 = r10.element((java.lang.String) r1)
            com.app.office.fc.dom4j.Element r2 = r2.element((java.lang.String) r8)
            if (r2 == 0) goto L_0x00de
            com.app.office.fc.dom4j.Element r10 = r10.element((java.lang.String) r1)
            com.app.office.fc.dom4j.Element r10 = r10.element((java.lang.String) r8)
            com.app.office.fc.dom4j.Element r10 = r10.element((java.lang.String) r3)
            goto L_0x00f2
        L_0x00de:
            com.app.office.fc.dom4j.Element r2 = r10.element((java.lang.String) r1)
            com.app.office.fc.dom4j.Element r2 = r2.element((java.lang.String) r6)
            if (r2 == 0) goto L_0x00f1
            com.app.office.fc.dom4j.Element r10 = r10.element((java.lang.String) r1)
            com.app.office.fc.dom4j.Element r10 = r10.element((java.lang.String) r6)
            goto L_0x00f2
        L_0x00f1:
            r10 = r7
        L_0x00f2:
            if (r11 == 0) goto L_0x0140
            if (r10 == 0) goto L_0x0140
            com.app.office.thirdpart.achartengine.model.XYSeries r1 = new com.app.office.thirdpart.achartengine.model.XYSeries
            r1.<init>(r0)
            java.util.List r11 = r11.elements((java.lang.String) r5)
            java.util.Iterator r11 = r11.iterator()
            java.util.List r10 = r10.elements((java.lang.String) r5)
            java.util.Iterator r10 = r10.iterator()
        L_0x010b:
            boolean r0 = r11.hasNext()
            if (r0 == 0) goto L_0x013f
            boolean r0 = r10.hasNext()
            if (r0 == 0) goto L_0x013f
            java.lang.Object r0 = r11.next()
            com.app.office.fc.dom4j.Element r0 = (com.app.office.fc.dom4j.Element) r0
            java.lang.Object r2 = r10.next()
            com.app.office.fc.dom4j.Element r2 = (com.app.office.fc.dom4j.Element) r2
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r4)
            java.lang.String r0 = r0.getText()
            double r5 = java.lang.Double.parseDouble(r0)
            com.app.office.fc.dom4j.Element r0 = r2.element((java.lang.String) r4)
            java.lang.String r0 = r0.getText()
            double r2 = java.lang.Double.parseDouble(r0)
            r1.add(r5, r2)
            goto L_0x010b
        L_0x013f:
            return r1
        L_0x0140:
            if (r10 == 0) goto L_0x016c
            com.app.office.thirdpart.achartengine.model.CategorySeries r11 = new com.app.office.thirdpart.achartengine.model.CategorySeries
            r11.<init>(r0)
            java.util.List r10 = r10.elements((java.lang.String) r5)
            java.util.Iterator r10 = r10.iterator()
        L_0x014f:
            boolean r0 = r10.hasNext()
            if (r0 == 0) goto L_0x016b
            java.lang.Object r0 = r10.next()
            com.app.office.fc.dom4j.Element r0 = (com.app.office.fc.dom4j.Element) r0
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r4)
            java.lang.String r0 = r0.getText()
            double r0 = java.lang.Double.parseDouble(r0)
            r11.add(r0)
            goto L_0x014f
        L_0x016b:
            return r11
        L_0x016c:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.xls.Reader.drawing.ChartReader.getSeries(com.app.office.fc.dom4j.Element, short):java.lang.Object");
    }

    private XYMultipleSeriesDataset getXYMultipleSeriesDataset(Element element, short s, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        return getXYMultipleSeriesDataset(element, s, xYMultipleSeriesRenderer, (PointStyle[]) null);
    }

    private XYMultipleSeriesDataset getXYMultipleSeriesDataset(Element element, short s, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, PointStyle[] pointStyleArr) {
        XYMultipleSeriesDataset xYMultipleSeriesDataset = new XYMultipleSeriesDataset();
        List elements = element.elements("ser");
        int size = elements.size();
        for (int i = 0; i < size; i++) {
            setSeriesRendererProp(xYMultipleSeriesRenderer, (Element) elements.get(i), pointStyleArr);
            Object series = getSeries((Element) elements.get(i), s);
            if (series == null) {
                return null;
            }
            if (series instanceof CategorySeries) {
                xYMultipleSeriesDataset.addSeries(((CategorySeries) series).toXYSeries());
            } else if (series instanceof XYSeries) {
                xYMultipleSeriesDataset.addSeries((XYSeries) series);
            }
            if (!this.hasMaxY) {
                this.maxY = Math.max(this.maxY, xYMultipleSeriesDataset.getSeriesAt(i).getMaxY());
            }
            if (!this.hasMinY) {
                this.minY = Math.min(this.minY, xYMultipleSeriesDataset.getSeriesAt(i).getMinY());
            }
        }
        double d = 2.147483647E9d;
        double d2 = -2.147483648E9d;
        for (int i2 = 0; i2 < xYMultipleSeriesDataset.getSeriesCount(); i2++) {
            d = Math.min(d, xYMultipleSeriesDataset.getSeriesAt(i2).getMinX());
            d2 = Math.max(d2, xYMultipleSeriesDataset.getSeriesAt(i2).getMaxX());
        }
        if (this.hasMinX) {
            xYMultipleSeriesRenderer.setXAxisMin(this.minX);
        } else if (s != 4) {
            xYMultipleSeriesRenderer.setXAxisMin(0.5d);
        } else {
            xYMultipleSeriesRenderer.setXAxisMin(d);
        }
        if (this.hasMaxX) {
            xYMultipleSeriesRenderer.setXAxisMax(this.maxX);
        } else if (s != 4) {
            xYMultipleSeriesRenderer.setXAxisMax(d2 + 0.5d);
        } else {
            xYMultipleSeriesRenderer.setXAxisMax(d2);
        }
        if (Math.abs(this.minY - Double.MAX_VALUE) < 0.10000000149011612d) {
            this.minY = 0.0d;
        }
        if (Math.abs(this.maxY - Double.MIN_VALUE) < 0.10000000149011612d) {
            this.maxY = 0.0d;
        }
        xYMultipleSeriesRenderer.setYAxisMin(this.minY);
        xYMultipleSeriesRenderer.setYAxisMax(this.maxY);
        return xYMultipleSeriesDataset;
    }

    /* access modifiers changed from: protected */
    public DefaultRenderer buildDefaultRenderer() {
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        defaultRenderer.setShowGridH(true);
        defaultRenderer.setLabelsColor(this.schemeColor.get(SchemeClrConstant.SCHEME_TX1).intValue());
        defaultRenderer.setAxesColor(this.schemeColor.get(SchemeClrConstant.SCHEME_TX1).intValue());
        return defaultRenderer;
    }

    /* access modifiers changed from: protected */
    public CategorySeries buildCategoryDataset(Element element, DefaultRenderer defaultRenderer) {
        CategorySeries categorySeries;
        if (element.element("ser") == null) {
            return null;
        }
        new CategorySeries("");
        Element element2 = element.element("ser");
        if (element2.element("tx") != null) {
            categorySeries = new CategorySeries(getSeriesTitle(element2.element("tx")));
        } else {
            categorySeries = new CategorySeries("");
        }
        ArrayList arrayList = new ArrayList(10);
        if (element2.element("cat") != null) {
            for (Element element3 : element2.element("cat").element("strRef").element("strCache").elements("pt")) {
                arrayList.add(element3.element("v").getText());
            }
        }
        ArrayList arrayList2 = new ArrayList(10);
        if (element2.element("val") != null) {
            for (Element element4 : element2.element("val").element("numRef").element("numCache").elements("pt")) {
                arrayList2.add(Double.valueOf(Double.parseDouble(element4.element("v").getText())));
            }
        }
        int i = 0;
        if (arrayList.size() <= 0 || arrayList.size() != arrayList2.size()) {
            while (i < arrayList2.size()) {
                SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
                String[] strArr = themeIndex;
                simpleSeriesRenderer.setColor(ColorUtil.instance().getColorWithTint(this.schemeColor.get(strArr[i % strArr.length]).intValue(), tints[i / strArr.length]));
                defaultRenderer.addSeriesRenderer(simpleSeriesRenderer);
                categorySeries.add(((Double) arrayList2.get(i)).doubleValue());
                i++;
            }
        } else {
            while (i < arrayList.size()) {
                SimpleSeriesRenderer simpleSeriesRenderer2 = new SimpleSeriesRenderer();
                String[] strArr2 = themeIndex;
                simpleSeriesRenderer2.setColor(ColorUtil.instance().getColorWithTint(this.schemeColor.get(strArr2[i % strArr2.length]).intValue(), tints[i / strArr2.length]));
                defaultRenderer.addSeriesRenderer(simpleSeriesRenderer2);
                categorySeries.add((String) arrayList.get(i), ((Double) arrayList2.get(i)).doubleValue());
                i++;
            }
        }
        return categorySeries;
    }

    private void dispose() {
        this.schemeColor = null;
        this.chart = null;
    }
}
