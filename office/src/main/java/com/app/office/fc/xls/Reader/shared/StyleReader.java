package com.app.office.fc.xls.Reader.shared;

import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.app.office.common.bg.AShader;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.bg.LinearGradientShader;
import com.app.office.common.bg.RadialGradientShader;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.ElementHandler;
import com.app.office.fc.dom4j.ElementPath;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.ss.util.CellUtil;
import com.app.office.simpletext.font.Font;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.style.BorderStyle;
import com.app.office.ss.model.style.BuiltinFormats;
import com.app.office.ss.model.style.CellBorder;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.ss.model.style.NumberFormat;
import com.app.office.ss.model.table.TableFormatManager;
import com.app.office.ss.util.ColorUtil;
import com.app.office.system.AbortReaderError;
import com.app.office.system.IReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StyleReader {
    private static StyleReader reader = new StyleReader();
    /* access modifiers changed from: private */
    public Workbook book;
    private int borderIndex;
    /* access modifiers changed from: private */
    public Map<Integer, CellBorder> cellBorders;
    private int fillIndex;
    /* access modifiers changed from: private */
    public Map<Integer, BackgroundAndFill> fills;
    private int fontIndex;
    /* access modifiers changed from: private */
    public IReader iReader;
    private int indexedColor;
    /* access modifiers changed from: private */
    public Map<Integer, NumberFormat> numFmts;
    private int styleIndex;
    private TableFormatManager tableFormatManager;

    static /* synthetic */ int access$1208(StyleReader styleReader) {
        int i = styleReader.styleIndex;
        styleReader.styleIndex = i + 1;
        return i;
    }

    static /* synthetic */ int access$1408(StyleReader styleReader) {
        int i = styleReader.indexedColor;
        styleReader.indexedColor = i + 1;
        return i;
    }

    static /* synthetic */ int access$308(StyleReader styleReader) {
        int i = styleReader.fontIndex;
        styleReader.fontIndex = i + 1;
        return i;
    }

    static /* synthetic */ int access$608(StyleReader styleReader) {
        int i = styleReader.fillIndex;
        styleReader.fillIndex = i + 1;
        return i;
    }

    static /* synthetic */ int access$908(StyleReader styleReader) {
        int i = styleReader.borderIndex;
        styleReader.borderIndex = i + 1;
        return i;
    }

    public static StyleReader instance() {
        return reader;
    }

    public void getWorkBookStyle(PackagePart packagePart, Workbook workbook, IReader iReader2) throws Exception {
        this.book = workbook;
        this.iReader = iReader2;
        this.fontIndex = 0;
        this.fillIndex = 0;
        this.borderIndex = 0;
        this.styleIndex = 0;
        this.indexedColor = 0;
        this.fills = new HashMap(5);
        this.cellBorders = new HashMap(5);
        getBuiltinNumberFormats();
        SAXReader sAXReader = new SAXReader();
        try {
            StyleSaxHandler styleSaxHandler = new StyleSaxHandler();
            sAXReader.addHandler("/styleSheet/numFmts/numFmt", styleSaxHandler);
            sAXReader.addHandler("/styleSheet/fonts/font", styleSaxHandler);
            sAXReader.addHandler("/styleSheet/fills/fill", styleSaxHandler);
            sAXReader.addHandler("/styleSheet/borders/border", styleSaxHandler);
            sAXReader.addHandler("/styleSheet/cellXfs/xf", styleSaxHandler);
            sAXReader.addHandler("/styleSheet/colors/indexedColors/rgbColor", styleSaxHandler);
            sAXReader.addHandler("/styleSheet/dxfs/dxf", styleSaxHandler);
            InputStream inputStream = packagePart.getInputStream();
            sAXReader.read(inputStream);
            inputStream.close();
            dispose();
        } finally {
            sAXReader.resetHandlers();
        }
    }

    private void getBuiltinNumberFormats() {
        String[] all = BuiltinFormats.getAll();
        int length = all.length;
        this.numFmts = new HashMap(length + 20);
        for (int i = 0; i < length; i++) {
            this.numFmts.put(Integer.valueOf(i), new NumberFormat((short) i, all[i]));
        }
    }

    private short getColorIndex(Element element) {
        int i;
        int i2 = 0;
        if (element != null) {
            if (element.attribute("theme") != null) {
                i2 = this.book.getThemeColorIndex(Integer.parseInt(element.attributeValue("theme")));
                if (element.attribute("tint") != null) {
                    double parseDouble = Double.parseDouble(element.attributeValue("tint"));
                    i = this.book.addColor(ColorUtil.instance().getColorWithTint(this.book.getColor(i2), parseDouble));
                }
            } else if (element.attribute("rgb") != null) {
                String attributeValue = element.attributeValue("rgb");
                if (attributeValue.length() > 6) {
                    attributeValue = attributeValue.substring(attributeValue.length() - 6);
                }
                i2 = this.book.addColor(Integer.parseInt(attributeValue, 16) | -16777216);
            } else if (!(element.attribute("indexed") == null || (i = Integer.parseInt(element.attributeValue("indexed"))) == 64)) {
                if (i > 64) {
                    i2 = 9;
                }
            }
            i2 = i;
        }
        return (short) i2;
    }

    /* access modifiers changed from: private */
    public NumberFormat processNumberFormat(Element element) {
        return new NumberFormat((short) Integer.parseInt(element.attribute("numFmtId").getValue()), element.attribute("formatCode").getValue());
    }

    /* access modifiers changed from: private */
    public Font processFont(Element element) {
        Font font = new Font();
        font.setIndex(this.fontIndex);
        Element element2 = element.element("fontElement");
        boolean z = true;
        if (element2 != null) {
            String attributeValue = element2.attributeValue("val");
            if (attributeValue.equalsIgnoreCase("superscript")) {
                font.setSuperSubScript((byte) 1);
            } else if (attributeValue.equalsIgnoreCase("subscript")) {
                font.setSuperSubScript((byte) 2);
            } else {
                font.setSuperSubScript((byte) 0);
            }
        } else {
            font.setSuperSubScript((byte) 0);
        }
        Element element3 = element.element("sz");
        font.setFontSize(element3 != null ? Double.parseDouble(element3.attributeValue("val")) : 12.0d);
        font.setColorIndex(getColorIndex(element.element("color")));
        if (element.element("name") != null) {
            font.setName(element.element("name").attributeValue("val"));
        }
        Element element4 = element.element(HtmlTags.B);
        if (element4 != null) {
            font.setBold(element4.attributeValue("val") == null ? true : Boolean.parseBoolean(element4.attributeValue("val")));
        }
        Element element5 = element.element("i");
        if (element5 != null) {
            font.setItalic(element5.attributeValue("val") == null ? true : Boolean.parseBoolean(element5.attributeValue("val")));
        }
        Element element6 = element.element(HtmlTags.U);
        if (element6 != null) {
            if (element6.attributeValue("val") != null) {
                font.setUnderline(element6.attributeValue("val"));
            } else {
                font.setUnderline(1);
            }
        }
        Element element7 = element.element(HtmlTags.STRIKE);
        if (element7 != null) {
            if (element7.attributeValue("val") != null) {
                z = Boolean.parseBoolean(element7.attributeValue("val"));
            }
            font.setStrikeline(z);
        }
        return font;
    }

    /* access modifiers changed from: private */
    public BackgroundAndFill processFill(Element element) {
        AShader aShader;
        Element element2 = element.element("patternFill");
        int i = 0;
        if (element2 != null) {
            BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
            if ("none".equalsIgnoreCase(element2.attributeValue("patternType"))) {
                return null;
            }
            Element element3 = element2.element("fgColor");
            if (element3 != null) {
                backgroundAndFill.setForegroundColor(this.book.getColor(getColorIndex(element3)));
                backgroundAndFill.setFillType((byte) 0);
            }
            Element element4 = element2.element("bgColor");
            if (element4 != null) {
                backgroundAndFill.setBackgoundColor(this.book.getColor(getColorIndex(element4)));
            }
            return backgroundAndFill;
        } else if (element.element("gradientFill") == null) {
            return null;
        } else {
            Element element5 = element.element("gradientFill");
            List elements = element5.elements("stop");
            int[] iArr = new int[elements.size()];
            float[] fArr = new float[elements.size()];
            for (int i2 = 0; i2 < elements.size(); i2++) {
                Element element6 = (Element) elements.get(i2);
                fArr[i2] = Float.parseFloat(element6.attributeValue("position"));
                iArr[i2] = this.book.getColor(getColorIndex(element6.element("color")));
            }
            BackgroundAndFill backgroundAndFill2 = new BackgroundAndFill();
            if (!"path".equalsIgnoreCase(element5.attributeValue(DublinCoreProperties.TYPE))) {
                backgroundAndFill2.setFillType((byte) 7);
                if (element5.attributeValue("degree") != null) {
                    i = Integer.parseInt(element5.attributeValue("degree"));
                }
                aShader = new LinearGradientShader((float) i, iArr, fArr);
            } else {
                backgroundAndFill2.setFillType((byte) 4);
                aShader = new RadialGradientShader(getRadialCenterType(element5), iArr, fArr);
            }
            backgroundAndFill2.setShader(aShader);
            return backgroundAndFill2;
        }
    }

    private static int getRadialCenterType(Element element) {
        if (element == null) {
            return 0;
        }
        String attributeValue = element.attributeValue(HtmlTags.ALIGN_LEFT);
        String attributeValue2 = element.attributeValue(HtmlTags.ALIGN_TOP);
        String attributeValue3 = element.attributeValue(HtmlTags.ALIGN_RIGHT);
        String attributeValue4 = element.attributeValue(HtmlTags.ALIGN_BOTTOM);
        if ("1".equalsIgnoreCase(attributeValue) && "1".equalsIgnoreCase(attributeValue3) && "1".equalsIgnoreCase(attributeValue4) && "1".equalsIgnoreCase(attributeValue2)) {
            return 3;
        }
        if ("1".equalsIgnoreCase(attributeValue4) && "1".equalsIgnoreCase(attributeValue2)) {
            return 2;
        }
        if (!"1".equalsIgnoreCase(attributeValue) || !"1".equalsIgnoreCase(attributeValue3)) {
            return (!"0.5".equalsIgnoreCase(attributeValue) || !"0.5".equalsIgnoreCase(attributeValue2) || !"0.5".equalsIgnoreCase(attributeValue3) || !"0.5".equalsIgnoreCase(attributeValue4)) ? 0 : 4;
        }
        return 1;
    }

    /* access modifiers changed from: private */
    public CellBorder processBorder(Element element) {
        CellBorder cellBorder = new CellBorder();
        Element element2 = element.element(HtmlTags.ALIGN_LEFT);
        if (element2 != null) {
            cellBorder.setLeftBorder(new BorderStyle(element2.attributeValue(HtmlTags.STYLE), getColorIndex(element2.element("color"))));
        }
        Element element3 = element.element(HtmlTags.ALIGN_TOP);
        if (element3 != null) {
            cellBorder.setTopBorder(new BorderStyle(element3.attributeValue(HtmlTags.STYLE), getColorIndex(element3.element("color"))));
        }
        Element element4 = element.element(HtmlTags.ALIGN_RIGHT);
        if (element4 != null) {
            cellBorder.setRightBorder(new BorderStyle(element4.attributeValue(HtmlTags.STYLE), getColorIndex(element4.element("color"))));
        }
        Element element5 = element.element(HtmlTags.ALIGN_BOTTOM);
        if (element5 != null) {
            cellBorder.setBottomBorder(new BorderStyle(element5.attributeValue(HtmlTags.STYLE), getColorIndex(element5.element("color"))));
        }
        return cellBorder;
    }

    private void processCellStyleAlignment(CellStyle cellStyle, Element element) {
        if (element.attributeValue("vertical") != null) {
            cellStyle.setVerticalAlign(element.attributeValue("vertical"));
        }
        if (element.attributeValue("horizontal") != null) {
            cellStyle.setHorizontalAlign(element.attributeValue("horizontal"));
        }
        if (element.attributeValue("textRotation") != null) {
            try {
                cellStyle.setRotation((short) Integer.parseInt(element.attributeValue("textRotation")));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (element.attributeValue(CellUtil.WRAP_TEXT) != null) {
            try {
                cellStyle.setWrapText(Integer.parseInt(element.attributeValue(CellUtil.WRAP_TEXT)) != 0);
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
        }
        if (element.attributeValue(HtmlTags.INDENT) != null) {
            try {
                cellStyle.setIndent((short) Integer.parseInt(element.attributeValue(HtmlTags.INDENT)));
            } catch (NumberFormatException e3) {
                e3.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public CellStyle processCellStyle(Element element) {
        int i;
        int i2;
        int i3;
        CellStyle cellStyle = new CellStyle();
        String attributeValue = element.attributeValue("numFmtId");
        int i4 = 0;
        if (attributeValue == null) {
            i = 0;
        } else {
            i = Integer.parseInt(attributeValue);
        }
        if (this.numFmts.get(Integer.valueOf(i)) != null) {
            cellStyle.setNumberFormat(this.numFmts.get(Integer.valueOf(i)));
        }
        String attributeValue2 = element.attributeValue("fontId");
        if (attributeValue2 == null) {
            i2 = 0;
        } else {
            i2 = Integer.parseInt(attributeValue2);
        }
        cellStyle.setFontIndex((short) i2);
        String attributeValue3 = element.attributeValue("fillId");
        if (attributeValue3 == null) {
            i3 = 0;
        } else {
            i3 = Integer.parseInt(attributeValue3);
        }
        cellStyle.setFillPattern(this.fills.get(Integer.valueOf(i3)));
        String attributeValue4 = element.attributeValue("borderId");
        if (attributeValue4 != null) {
            i4 = Integer.parseInt(attributeValue4);
        }
        cellStyle.setBorder(this.cellBorders.get(Integer.valueOf(i4)));
        Element element2 = element.element(CellUtil.ALIGNMENT);
        if (element2 != null) {
            processCellStyleAlignment(cellStyle, element2);
        }
        return cellStyle;
    }

    /* access modifiers changed from: private */
    public int processIndexedColors(Element element) {
        String attributeValue = element.attributeValue("rgb");
        if (attributeValue.length() > 6) {
            attributeValue = attributeValue.substring(attributeValue.length() - 6);
        }
        return Integer.parseInt(attributeValue, 16) | -16777216;
    }

    /* access modifiers changed from: private */
    public void processTableFormat(Element element) {
        if (this.tableFormatManager == null) {
            TableFormatManager tableFormatManager2 = new TableFormatManager(5);
            this.tableFormatManager = tableFormatManager2;
            this.book.setTableFormatManager(tableFormatManager2);
        }
        CellStyle cellStyle = new CellStyle();
        Element element2 = element.element("numFmt");
        if (element2 != null) {
            cellStyle.setNumberFormat(processNumberFormat(element2));
        }
        Element element3 = element.element("font");
        if (element3 != null) {
            this.book.addFont(this.fontIndex, processFont(element3));
            int i = this.fontIndex;
            this.fontIndex = i + 1;
            cellStyle.setFontIndex((short) i);
        }
        Element element4 = element.element("fill");
        if (element4 != null) {
            cellStyle.setFillPattern(processFill(element4));
        }
        Element element5 = element.element(HtmlTags.BORDER);
        if (element5 != null) {
            cellStyle.setBorder(processBorder(element5));
        }
        Element element6 = element.element(CellUtil.ALIGNMENT);
        if (element6 != null) {
            processCellStyleAlignment(cellStyle, element6);
        }
        this.tableFormatManager.addFormat(cellStyle);
    }

    private void dispose() {
        this.book = null;
        this.iReader = null;
        this.tableFormatManager = null;
        Map<Integer, NumberFormat> map = this.numFmts;
        if (map != null) {
            map.clear();
            this.numFmts = null;
        }
        Map<Integer, CellBorder> map2 = this.cellBorders;
        if (map2 != null) {
            map2.clear();
            this.cellBorders = null;
        }
        Map<Integer, BackgroundAndFill> map3 = this.fills;
        if (map3 != null) {
            map3.clear();
            this.fills = null;
        }
    }

    class StyleSaxHandler implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        StyleSaxHandler() {
        }

        public void onEnd(ElementPath elementPath) {
            if (!StyleReader.this.iReader.isAborted()) {
                Element current = elementPath.getCurrent();
                String name = current.getName();
                if (name.equals("numFmt")) {
                    NumberFormat access$100 = StyleReader.this.processNumberFormat(current);
                    StyleReader.this.numFmts.put(Integer.valueOf(access$100.getNumberFormatID()), access$100);
                } else if (name.equals("font")) {
                    StyleReader.this.book.addFont(StyleReader.access$308(StyleReader.this), StyleReader.this.processFont(current));
                } else if (name.equals("fill")) {
                    StyleReader.this.fills.put(Integer.valueOf(StyleReader.access$608(StyleReader.this)), StyleReader.this.processFill(current));
                } else if (name.equals(HtmlTags.BORDER)) {
                    StyleReader.this.cellBorders.put(Integer.valueOf(StyleReader.access$908(StyleReader.this)), StyleReader.this.processBorder(current));
                } else if (name.equals("xf")) {
                    StyleReader.this.book.addCellStyle(StyleReader.access$1208(StyleReader.this), StyleReader.this.processCellStyle(current));
                } else if (name.equals("rgbColor")) {
                    StyleReader.this.book.addColor(StyleReader.access$1408(StyleReader.this), StyleReader.this.processIndexedColors(current));
                } else if (name.equals("dxf")) {
                    StyleReader.this.processTableFormat(current);
                }
                current.detach();
                return;
            }
            throw new AbortReaderError("abort Reader");
        }
    }
}
