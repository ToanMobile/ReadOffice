package com.app.office.fc.doc;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.itextpdf.text.Annotation;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.office.pdf.documentreader.uitilities.Constants;
import com.onesignal.outcomes.OSOutcomeConstants;
import com.app.office.common.PaintKit;
import com.app.office.common.autoshape.AutoShapeDataKit;
import com.app.office.common.autoshape.ExtendPath;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Border;
import com.app.office.common.borders.Borders;
import com.app.office.common.borders.Line;
import com.app.office.common.bulletnumber.ListData;
import com.app.office.common.bulletnumber.ListLevel;
import com.app.office.common.picture.Picture;
import com.app.office.common.pictureefftect.PictureEffectInfo;
import com.app.office.common.pictureefftect.PictureEffectInfoFactory;
import com.app.office.common.shape.AbstractShape;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.GroupShape;
import com.app.office.common.shape.IShape;
import com.app.office.common.shape.PictureShape;
import com.app.office.common.shape.ShapeTypes;
import com.app.office.common.shape.WPAbstractShape;
import com.app.office.common.shape.WPAutoShape;
import com.app.office.common.shape.WPChartShape;
import com.app.office.common.shape.WPGroupShape;
import com.app.office.common.shape.WPPictureShape;
import com.app.office.common.shape.WatermarkShape;
import com.app.office.constant.SchemeClrConstant;
import com.app.office.constant.wp.WPModelConstant;
import com.app.office.fc.FCKit;
import com.app.office.fc.LineKit;
import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.ElementHandler;
import com.app.office.fc.dom4j.ElementPath;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.PackageRelationshipTypes;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.fc.ppt.reader.ReaderKit;
import com.app.office.fc.ppt.reader.ThemeReader;
import com.app.office.fc.xls.Reader.drawing.ChartReader;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.model.PGPlaceholderUtil;
import com.app.office.simpletext.font.FontTypefaceManage;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.LeafElement;
import com.app.office.simpletext.model.ParagraphElement;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.simpletext.model.Style;
import com.app.office.simpletext.model.StyleManage;
import com.app.office.system.AbortReaderError;
import com.app.office.system.AbstractReader;
import com.app.office.system.IControl;
import com.app.office.thirdpart.achartengine.chart.AbstractChart;
import com.app.office.wp.model.CellElement;
import com.app.office.wp.model.HFElement;
import com.app.office.wp.model.RowElement;
import com.app.office.wp.model.TableElement;
import com.app.office.wp.model.WPDocument;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlinx.coroutines.DebugKt;

public class DOCXReader extends AbstractReader {
    Hashtable<String, String> bulletNumbersID = new Hashtable<>();
    private String filePath;
    private PackagePart hfPart;
    private boolean isProcessHF;
    private boolean isProcessSectionAttribute;
    private boolean isProcessWatermark;
    /* access modifiers changed from: private */
    public long offset;
    private PackagePart packagePart;
    private List<IShape> relativeType = new ArrayList();
    private Map<IShape, int[]> relativeValue = new HashMap();
    private SectionElement secElem;
    private int styleID;
    private Map<String, Integer> styleStrID = new HashMap();
    private Map<Integer, Integer> tableGridCol = new HashMap();
    private Map<String, Integer> tableStyle = new HashMap();
    private long textboxIndex;
    private Map<String, Integer> themeColor;
    /* access modifiers changed from: private */
    public WPDocument wpdoc;
    private ZipPackage zipPackage;

    public DOCXReader(IControl iControl, String str) {
        this.control = iControl;
        this.filePath = str;
    }

    public Object getModel() throws Exception {
        WPDocument wPDocument = this.wpdoc;
        if (wPDocument != null) {
            return wPDocument;
        }
        this.wpdoc = new WPDocument();
        openFile();
        return this.wpdoc;
    }

    private void openFile() throws Exception {
        ZipPackage zipPackage2 = new ZipPackage(this.filePath);
        this.zipPackage = zipPackage2;
        if (zipPackage2.getParts().size() != 0) {
            PackageRelationship relationship = this.zipPackage.getRelationshipsByType(PackageRelationshipTypes.CORE_DOCUMENT).getRelationship(0);
            if (relationship.getTargetURI().toString().equals("/word/document.xml")) {
                this.packagePart = this.zipPackage.getPart(relationship);
                processThemeColor();
                processBulletNumber();
                processStyle();
                this.secElem = new SectionElement();
                this.offset = 0;
                SAXReader sAXReader = new SAXReader();
                DOCXSaxHandler dOCXSaxHandler = new DOCXSaxHandler();
                sAXReader.addHandler("/document/body/tbl", dOCXSaxHandler);
                sAXReader.addHandler("/document/body/p", dOCXSaxHandler);
                sAXReader.addHandler("/document/body/sdt", dOCXSaxHandler);
                Document read = sAXReader.read(this.packagePart.getInputStream());
                Element element = read.getRootElement().element("background");
                if (element != null) {
                    BackgroundAndFill backgroundAndFill = null;
                    if (element.element("background") != null) {
                        backgroundAndFill = processBackgroundAndFill(element.element("background"));
                    } else {
                        String attributeValue = element.attributeValue("color");
                        if (attributeValue != null) {
                            backgroundAndFill = new BackgroundAndFill();
                            backgroundAndFill.setForegroundColor(Color.parseColor("#aabb" + attributeValue));
                        }
                    }
                    this.wpdoc.setPageBackground(backgroundAndFill);
                }
                processSection(read.getRootElement().element("body"));
                processRelativeShapeSize();
                return;
            }
            throw new Exception("Format error");
        }
        throw new Exception("Format error");
    }

    private void processStyle() throws Exception {
        PackagePart part;
        Element element;
        Element element2;
        Element element3;
        String attributeValue;
        Element element4;
        Element element5;
        PackageRelationship relationship = this.packagePart.getRelationshipsByType(PackageRelationshipTypes.STYLE_PART).getRelationship(0);
        if (relationship != null && (part = this.zipPackage.getPart(relationship.getTargetURI())) != null) {
            SAXReader sAXReader = new SAXReader();
            InputStream inputStream = part.getInputStream();
            Element rootElement = sAXReader.read(inputStream).getRootElement();
            List<Element> elements = rootElement.elements(HtmlTags.STYLE);
            Element element6 = rootElement.element("docDefaults");
            if (element6 != null) {
                Style style = new Style();
                this.styleStrID.put("docDefaults", Integer.valueOf(this.styleID));
                style.setId(this.styleID);
                this.styleID++;
                style.setType((byte) 0);
                style.setName("docDefaults");
                Element element7 = element6.element("pPrDefault");
                if (!(element7 == null || (element5 = element7.element("pPr")) == null)) {
                    processParaAttribute(element5, style.getAttrbuteSet(), 0);
                }
                Element element8 = element6.element("rPrDefault");
                if (!(element8 == null || (element4 = element8.element("rPr")) == null)) {
                    processRunAttribute(element4, style.getAttrbuteSet());
                }
                StyleManage.instance().addStyle(style);
            }
            for (Element element9 : elements) {
                if (this.abortReader) {
                    break;
                }
                if (!(!HtmlTags.TABLE.equals(element9.attributeValue(DublinCoreProperties.TYPE)) || (element = element9.element("tblStylePr")) == null || !"firstRow".equals(element.attributeValue(DublinCoreProperties.TYPE)) || (element2 = element.element("tcPr")) == null || (element3 = element2.element("shd")) == null || (attributeValue = element3.attributeValue("fill")) == null)) {
                    this.tableStyle.put(element9.attributeValue("styleId"), Integer.valueOf(Color.parseColor("#" + attributeValue)));
                }
                Style style2 = new Style();
                String attributeValue2 = element9.attributeValue("styleId");
                if (attributeValue2 != null) {
                    Integer num = this.styleStrID.get(attributeValue2);
                    if (num == null) {
                        this.styleStrID.put(attributeValue2, Integer.valueOf(this.styleID));
                        style2.setId(this.styleID);
                        this.styleID++;
                    } else {
                        style2.setId(num.intValue());
                    }
                }
                style2.setType(element9.attributeValue(DublinCoreProperties.TYPE).equals("paragraph") ^ true ? (byte) 1 : 0);
                Element element10 = element9.element("name");
                if (element10 != null) {
                    style2.setName(element10.attributeValue("val"));
                }
                Element element11 = element9.element("basedOn");
                if (element11 != null) {
                    String attributeValue3 = element11.attributeValue("val");
                    if (attributeValue3 != null) {
                        Integer num2 = this.styleStrID.get(attributeValue3);
                        if (num2 == null) {
                            this.styleStrID.put(attributeValue3, Integer.valueOf(this.styleID));
                            style2.setBaseID(this.styleID);
                            this.styleID++;
                        } else {
                            style2.setBaseID(num2.intValue());
                        }
                    }
                } else if ("1".equals(element9.attributeValue("default"))) {
                    style2.setBaseID(0);
                }
                Element element12 = element9.element("pPr");
                if (element12 != null) {
                    processParaAttribute(element12, style2.getAttrbuteSet(), 0);
                }
                Element element13 = element9.element("rPr");
                if (element13 != null) {
                    processRunAttribute(element13, style2.getAttrbuteSet());
                }
                StyleManage.instance().addStyle(style2);
            }
            inputStream.close();
        }
    }

    private void processBulletNumber() throws Exception {
        PackagePart part;
        Element element;
        String attributeValue;
        Integer num;
        PackageRelationship relationship = this.packagePart.getRelationshipsByType(PackageRelationshipTypes.BULLET_NUMBER_PART).getRelationship(0);
        if (relationship != null && (part = this.zipPackage.getPart(relationship.getTargetURI())) != null) {
            SAXReader sAXReader = new SAXReader();
            InputStream inputStream = part.getInputStream();
            Element rootElement = sAXReader.read(inputStream).getRootElement();
            for (Element element2 : rootElement.elements("num")) {
                Element element3 = element2.element("abstractNumId");
                if (element3 != null) {
                    String attributeValue2 = element3.attributeValue("val");
                    this.bulletNumbersID.put(element2.attributeValue("numId"), attributeValue2);
                }
            }
            for (Element element4 : rootElement.elements("abstractNum")) {
                ListData listData = new ListData();
                String attributeValue3 = element4.attributeValue("abstractNumId");
                if (attributeValue3 != null) {
                    listData.setListID(Integer.parseInt(attributeValue3));
                }
                List elements = element4.elements("lvl");
                int size = elements.size();
                ListLevel[] listLevelArr = new ListLevel[size];
                byte b = (byte) size;
                listData.setSimpleList(b);
                for (int i = 0; i < size; i++) {
                    listLevelArr[i] = new ListLevel();
                    processListLevel(listLevelArr[i], (Element) elements.get(i));
                }
                listData.setLevels(listLevelArr);
                listData.setSimpleList(b);
                if (!(size != 0 || (element = element4.element("numStyleLink")) == null || (attributeValue = element.attributeValue("val")) == null || (num = this.styleStrID.get(attributeValue)) == null)) {
                    listData.setLinkStyleID(num.shortValue());
                    Style style = StyleManage.instance().getStyle(num.intValue());
                    int paraListID = AttrManage.instance().getParaListID(style.getAttrbuteSet());
                    if (paraListID >= 0) {
                        AttrManage.instance().setParaListID(style.getAttrbuteSet(), Integer.parseInt(this.bulletNumbersID.get(String.valueOf(paraListID))));
                    }
                }
                this.control.getSysKit().getListManage().putListData(Integer.valueOf(listData.getListID()), listData);
            }
            inputStream.close();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00e5, code lost:
        if (r6 >= 61440) goto L_0x00b7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processListLevel(com.app.office.common.bulletnumber.ListLevel r10, com.app.office.fc.dom4j.Element r11) {
        /*
            r9 = this;
            java.lang.String r0 = "start"
            com.app.office.fc.dom4j.Element r0 = r11.element((java.lang.String) r0)
            java.lang.String r1 = "val"
            if (r0 == 0) goto L_0x0015
            java.lang.String r0 = r0.attributeValue((java.lang.String) r1)
            int r0 = java.lang.Integer.parseInt(r0)
            r10.setStartAt(r0)
        L_0x0015:
            java.lang.String r0 = "lvlJc"
            com.app.office.fc.dom4j.Element r0 = r11.element((java.lang.String) r0)
            java.lang.String r2 = "left"
            r3 = 2
            r4 = 0
            r5 = 1
            if (r0 == 0) goto L_0x0047
            java.lang.String r0 = r0.attributeValue((java.lang.String) r1)
            boolean r6 = r2.equals(r0)
            if (r6 == 0) goto L_0x0030
            r10.setAlign(r4)
            goto L_0x0047
        L_0x0030:
            java.lang.String r6 = "center"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x003c
            r10.setAlign(r5)
            goto L_0x0047
        L_0x003c:
            java.lang.String r6 = "right"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0047
            r10.setAlign(r3)
        L_0x0047:
            java.lang.String r0 = "suff"
            com.app.office.fc.dom4j.Element r0 = r11.element((java.lang.String) r0)
            if (r0 == 0) goto L_0x006a
            java.lang.String r0 = r0.attributeValue((java.lang.String) r1)
            java.lang.String r6 = "space"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x005f
            r10.setFollowChar(r5)
            goto L_0x006a
        L_0x005f:
            java.lang.String r6 = "nothing"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x006a
            r10.setFollowChar(r3)
        L_0x006a:
            java.lang.String r0 = "numFmt"
            com.app.office.fc.dom4j.Element r0 = r11.element((java.lang.String) r0)
            if (r0 == 0) goto L_0x007d
            java.lang.String r0 = r0.attributeValue((java.lang.String) r1)
            int r0 = r9.convertedNumberFormat(r0)
            r10.setNumberFormat(r0)
        L_0x007d:
            java.lang.String r0 = "lvlText"
            com.app.office.fc.dom4j.Element r0 = r11.element((java.lang.String) r0)
            if (r0 == 0) goto L_0x00fd
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r0 = r0.attributeValue((java.lang.String) r1)
            r1 = 0
        L_0x008f:
            int r6 = r0.length()
            if (r1 >= r6) goto L_0x00ed
            char r6 = r0.charAt(r1)
            r7 = 37
            if (r6 != r7) goto L_0x00b0
            int r6 = r1 + 1
            int r1 = r1 + 2
            java.lang.String r1 = r0.substring(r6, r1)
            int r1 = java.lang.Integer.parseInt(r1)
            int r1 = r1 - r5
            char r1 = (char) r1
            r3.append(r1)
            r1 = r6
            goto L_0x00eb
        L_0x00b0:
            r7 = 61548(0xf06c, float:8.6247E-41)
            r8 = 9679(0x25cf, float:1.3563E-41)
            if (r6 != r7) goto L_0x00ba
        L_0x00b7:
            r6 = 9679(0x25cf, float:1.3563E-41)
            goto L_0x00e8
        L_0x00ba:
            r7 = 61550(0xf06e, float:8.625E-41)
            if (r6 != r7) goto L_0x00c2
            r6 = 9632(0x25a0, float:1.3497E-41)
            goto L_0x00e8
        L_0x00c2:
            r7 = 61557(0xf075, float:8.626E-41)
            if (r6 != r7) goto L_0x00ca
            r6 = 9670(0x25c6, float:1.355E-41)
            goto L_0x00e8
        L_0x00ca:
            r7 = 61692(0xf0fc, float:8.6449E-41)
            if (r6 != r7) goto L_0x00d2
            r6 = 8730(0x221a, float:1.2233E-41)
            goto L_0x00e8
        L_0x00d2:
            r7 = 61656(0xf0d8, float:8.6398E-41)
            if (r6 != r7) goto L_0x00da
            r6 = 9733(0x2605, float:1.3639E-41)
            goto L_0x00e8
        L_0x00da:
            r7 = 61618(0xf0b2, float:8.6345E-41)
            if (r6 != r7) goto L_0x00e2
            r6 = 9734(0x2606, float:1.364E-41)
            goto L_0x00e8
        L_0x00e2:
            r7 = 61440(0xf000, float:8.6096E-41)
            if (r6 < r7) goto L_0x00e8
            goto L_0x00b7
        L_0x00e8:
            r3.append(r6)
        L_0x00eb:
            int r1 = r1 + r5
            goto L_0x008f
        L_0x00ed:
            int r0 = r3.length()
            char[] r0 = new char[r0]
            int r1 = r3.length()
            r3.getChars(r4, r1, r0, r4)
            r10.setNumberText(r0)
        L_0x00fd:
            java.lang.String r0 = "pPr"
            com.app.office.fc.dom4j.Element r11 = r11.element((java.lang.String) r0)
            if (r11 == 0) goto L_0x012a
            java.lang.String r0 = "ind"
            com.app.office.fc.dom4j.Element r11 = r11.element((java.lang.String) r0)
            if (r11 == 0) goto L_0x012a
            java.lang.String r0 = "hanging"
            java.lang.String r0 = r11.attributeValue((java.lang.String) r0)
            if (r0 == 0) goto L_0x011d
            int r0 = java.lang.Integer.parseInt(r0)
            int r0 = -r0
            r10.setSpecialIndent(r0)
        L_0x011d:
            java.lang.String r11 = r11.attributeValue((java.lang.String) r2)
            if (r11 == 0) goto L_0x012a
            int r11 = java.lang.Integer.parseInt(r11)
            r10.setTextIndent(r11)
        L_0x012a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCXReader.processListLevel(com.app.office.common.bulletnumber.ListLevel, com.app.office.fc.dom4j.Element):void");
    }

    private int convertedNumberFormat(String str) {
        if ("decimal".equalsIgnoreCase(str)) {
            return 0;
        }
        if ("upperRoman".equalsIgnoreCase(str)) {
            return 1;
        }
        if ("lowerRoman".equalsIgnoreCase(str)) {
            return 2;
        }
        if ("upperLetter".equalsIgnoreCase(str)) {
            return 3;
        }
        if ("lowerLetter".equalsIgnoreCase(str)) {
            return 4;
        }
        if ("chineseCountingThousand".equalsIgnoreCase(str)) {
            return 39;
        }
        if ("chineseLegalSimplified".equalsIgnoreCase(str)) {
            return 38;
        }
        if ("ideographTraditional".equalsIgnoreCase(str)) {
            return 30;
        }
        if ("ideographZodiac".equalsIgnoreCase(str)) {
            return 31;
        }
        if ("ordinal".equalsIgnoreCase(str)) {
            return 5;
        }
        if ("cardinalText".equalsIgnoreCase(str)) {
            return 6;
        }
        if ("ordinalText".equalsIgnoreCase(str)) {
            return 7;
        }
        if ("decimalZero".equalsIgnoreCase(str)) {
            return 22;
        }
        return 0;
    }

    private void processHeaderAndFooter(PackageRelationship packageRelationship, boolean z) throws Exception {
        if (packageRelationship != null) {
            PackagePart part = this.zipPackage.getPart(packageRelationship.getTargetURI());
            this.hfPart = part;
            if (part != null) {
                this.isProcessHF = true;
                this.offset = z ? 1152921504606846976L : 2305843009213693952L;
                SAXReader sAXReader = new SAXReader();
                InputStream inputStream = this.hfPart.getInputStream();
                List elements = sAXReader.read(inputStream).getRootElement().elements();
                HFElement hFElement = new HFElement(z ? (short) 5 : 6, (byte) 1);
                hFElement.setStartOffset(this.offset);
                processParagraphs(elements);
                hFElement.setEndOffset(this.offset);
                this.wpdoc.appendElement(hFElement, this.offset);
                inputStream.close();
                this.isProcessHF = false;
            }
        }
    }

    private void processSection(Element element) throws Exception {
        this.secElem.setStartOffset(0);
        this.secElem.setEndOffset(this.offset);
        this.wpdoc.appendSection(this.secElem);
        processSectionAttribute(element.element("sectPr"));
    }

    private void processSectionAttribute(Element element) {
        String str;
        String attributeValue;
        if (element != null && !this.isProcessSectionAttribute) {
            IAttributeSet attribute = this.secElem.getAttribute();
            Element element2 = element.element("pgSz");
            if (element2 != null) {
                AttrManage.instance().setPageWidth(attribute, Integer.parseInt(element2.attributeValue("w")));
                AttrManage.instance().setPageHeight(attribute, Integer.parseInt(element2.attributeValue("h")));
            }
            Element element3 = element.element("pgMar");
            if (element3 != null) {
                AttrManage.instance().setPageMarginLeft(attribute, Integer.parseInt(element3.attributeValue(HtmlTags.ALIGN_LEFT)));
                AttrManage.instance().setPageMarginRight(attribute, Integer.parseInt(element3.attributeValue(HtmlTags.ALIGN_RIGHT)));
                AttrManage.instance().setPageMarginTop(attribute, Integer.parseInt(element3.attributeValue(HtmlTags.ALIGN_TOP)));
                AttrManage.instance().setPageMarginBottom(attribute, Integer.parseInt(element3.attributeValue(HtmlTags.ALIGN_BOTTOM)));
                if (element3.attributeValue(Constants.HEADER) != null) {
                    AttrManage.instance().setPageHeaderMargin(attribute, Integer.parseInt(element3.attributeValue(Constants.HEADER)));
                }
                if (element3.attributeValue("footer") != null) {
                    AttrManage.instance().setPageFooterMargin(attribute, Integer.parseInt(element3.attributeValue("footer")));
                }
            }
            Element element4 = element.element("pgBorders");
            if (element4 != null) {
                Borders borders = new Borders();
                if (Annotation.PAGE.equals(element4.attributeValue("offsetFrom"))) {
                    borders.setOnType((byte) 1);
                }
                Element element5 = element4.element(HtmlTags.ALIGN_TOP);
                if (element5 != null) {
                    Border border = new Border();
                    processBorder(element5, border);
                    borders.setTopBorder(border);
                }
                Element element6 = element4.element(HtmlTags.ALIGN_LEFT);
                if (element6 != null) {
                    Border border2 = new Border();
                    processBorder(element6, border2);
                    borders.setLeftBorder(border2);
                }
                Element element7 = element4.element(HtmlTags.ALIGN_RIGHT);
                if (element7 != null) {
                    Border border3 = new Border();
                    processBorder(element7, border3);
                    borders.setRightBorder(border3);
                }
                Element element8 = element4.element(HtmlTags.ALIGN_BOTTOM);
                if (element8 != null) {
                    Border border4 = new Border();
                    processBorder(element8, border4);
                    borders.setBottomBorder(border4);
                }
                AttrManage.instance().setPageBorder(attribute, this.control.getSysKit().getBordersManage().addBorders(borders));
            }
            Element element9 = element.element("docGrid");
            if (element9 != null) {
                String attributeValue2 = element9.attributeValue(DublinCoreProperties.TYPE);
                if (("lines".equals(attributeValue2) || "linesAndChars".equals(attributeValue2) || "snapToChars".equals(attributeValue2)) && (attributeValue = element9.attributeValue("linePitch")) != null && attributeValue.length() > 0) {
                    AttrManage.instance().setPageLinePitch(attribute, Integer.parseInt(attributeValue));
                    for (int i = 0; ((long) i) < this.textboxIndex; i++) {
                        AttrManage.instance().setPageLinePitch(this.wpdoc.getTextboxSectionElementForIndex(i).getAttribute(), AttrManage.instance().getPageLinePitch(this.secElem.getAttribute()));
                    }
                }
            }
            long j = this.offset;
            List elements = element.elements("headerReference");
            String str2 = "";
            if (elements != null && elements.size() > 0) {
                if (elements.size() != 1) {
                    Iterator it = elements.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            str = str2;
                            break;
                        }
                        Element element10 = (Element) it.next();
                        if ("default".equals(element10.attributeValue(DublinCoreProperties.TYPE))) {
                            str = element10.attributeValue(OSOutcomeConstants.OUTCOME_ID);
                            break;
                        }
                    }
                } else {
                    str = ((Element) elements.get(0)).attributeValue(OSOutcomeConstants.OUTCOME_ID);
                }
                if (str != null && str.length() > 0) {
                    try {
                        PackageRelationship relationshipByID = this.packagePart.getRelationshipsByType(PackageRelationshipTypes.HEADER_PART).getRelationshipByID(str);
                        if (relationshipByID != null) {
                            processHeaderAndFooter(relationshipByID, true);
                        }
                    } catch (Exception e) {
                        this.control.getSysKit().getErrorKit().writerLog(e, true);
                    }
                }
            }
            List elements2 = element.elements("footerReference");
            if (elements2 != null && elements2.size() > 0) {
                if (elements2.size() != 1) {
                    Iterator it2 = elements2.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        Element element11 = (Element) it2.next();
                        if ("default".equals(element11.attributeValue(DublinCoreProperties.TYPE))) {
                            str2 = element11.attributeValue(OSOutcomeConstants.OUTCOME_ID);
                            break;
                        }
                    }
                } else {
                    str2 = ((Element) elements2.get(0)).attributeValue(OSOutcomeConstants.OUTCOME_ID);
                }
                if (str2 != null && str2.length() > 0) {
                    try {
                        PackageRelationship relationshipByID2 = this.packagePart.getRelationshipsByType(PackageRelationshipTypes.FOOTER_PART).getRelationshipByID(str2);
                        if (relationshipByID2 != null) {
                            processHeaderAndFooter(relationshipByID2, false);
                        }
                    } catch (Exception e2) {
                        this.control.getSysKit().getErrorKit().writerLog(e2, true);
                    }
                }
            }
            this.offset = j;
            this.isProcessSectionAttribute = true;
        }
    }

    private void processBorder(Element element, Border border) {
        String attributeValue = element.attributeValue("color");
        if (attributeValue == null || DebugKt.DEBUG_PROPERTY_VALUE_AUTO.equals(attributeValue)) {
            border.setColor(-16777216);
        } else {
            border.setColor(Color.parseColor("#" + attributeValue));
        }
        String attributeValue2 = element.attributeValue("space");
        if (attributeValue2 == null) {
            border.setSpace(32);
        } else {
            border.setSpace((short) ((int) (((float) Integer.parseInt(attributeValue2)) * 1.3333334f)));
        }
    }

    /* access modifiers changed from: private */
    public void processTable(Element element) {
        List elements;
        String attributeValue;
        TableElement tableElement = new TableElement();
        tableElement.setStartOffset(this.offset);
        Element element2 = element.element("tblPr");
        String str = "";
        if (element2 != null) {
            processTableAttribute(element2, tableElement.getAttribute());
            Element element3 = element2.element("tblStyle");
            if (!(element3 == null || (attributeValue = element3.attributeValue("val")) == null)) {
                str = attributeValue;
            }
        }
        Element element4 = element.element("tblGrid");
        if (!(element4 == null || (elements = element4.elements("gridCol")) == null)) {
            for (int i = 0; i < elements.size(); i++) {
                this.tableGridCol.put(Integer.valueOf(i), Integer.valueOf(Integer.parseInt(((Element) elements.get(i)).attributeValue("w"))));
            }
        }
        boolean z = true;
        for (Element processRow : element.elements(HtmlTags.TR)) {
            processRow(processRow, tableElement, z, str);
            z = false;
        }
        tableElement.setEndOffset(this.offset);
        this.wpdoc.appendParagraph(tableElement, this.offset);
    }

    private void processTableAttribute(Element element, IAttributeSet iAttributeSet) {
        String attributeValue;
        Element element2 = element.element("jc");
        if (element2 != null) {
            String attributeValue2 = element2.attributeValue("val");
            if (HtmlTags.ALIGN_CENTER.equals(attributeValue2)) {
                AttrManage.instance().setParaHorizontalAlign(iAttributeSet, 1);
            } else if (HtmlTags.ALIGN_RIGHT.equals(attributeValue2)) {
                AttrManage.instance().setParaHorizontalAlign(iAttributeSet, 2);
            }
        }
        Element element3 = element.element("tblInd");
        if (element3 != null && (attributeValue = element3.attributeValue("w")) != null) {
            AttrManage.instance().setParaIndentLeft(iAttributeSet, Integer.parseInt(attributeValue));
        }
    }

    private void processRow(Element element, TableElement tableElement, boolean z, String str) {
        RowElement rowElement = new RowElement();
        rowElement.setStartOffset(this.offset);
        Element element2 = element.element("trPr");
        if (element2 != null) {
            processRowAttribute(element2, rowElement.getAttribute());
        }
        int i = 0;
        for (Element processCell : element.elements("tc")) {
            i += processCell(processCell, rowElement, i, z, str);
        }
        rowElement.setEndOffset(this.offset);
        tableElement.appendRow(rowElement);
    }

    private void processRowAttribute(Element element, IAttributeSet iAttributeSet) {
        Element element2 = element.element("trHeight");
        if (element2 != null) {
            AttrManage.instance().setTableRowHeight(iAttributeSet, Integer.parseInt(element2.attributeValue("val")));
        }
    }

    private int processCell(Element element, RowElement rowElement, int i, boolean z, String str) {
        CellElement cellElement = new CellElement();
        cellElement.setStartOffset(this.offset);
        Element element2 = element.element("tcPr");
        int processCellAttribute = element2 != null ? processCellAttribute(element2, cellElement.getAttribute(), i) : 0;
        processParagraphs_Table(element.elements(), 1);
        cellElement.setEndOffset(this.offset);
        rowElement.appendCell(cellElement);
        if (z && this.tableStyle.containsKey(str)) {
            AttrManage.instance().setTableCellBackground(cellElement.getAttribute(), this.tableStyle.get(str).intValue());
        }
        if (processCellAttribute > 0) {
            for (int i2 = 1; i2 < processCellAttribute; i2++) {
                rowElement.appendCell(new CellElement());
            }
        }
        return processCellAttribute;
    }

    private int processCellAttribute(Element element, IAttributeSet iAttributeSet, int i) {
        Element element2 = element.element("gridSpan");
        int parseInt = element2 != null ? Integer.parseInt(element2.attributeValue("val")) : 1;
        Element element3 = element.element("tcW");
        int i2 = 0;
        if (element3 != null) {
            int parseInt2 = Integer.parseInt(element3.attributeValue("w"));
            String attributeValue = element3.attributeValue(DublinCoreProperties.TYPE);
            if ("pct".equals(attributeValue) || DebugKt.DEBUG_PROPERTY_VALUE_AUTO.equals(attributeValue)) {
                for (int i3 = i; i3 < i + parseInt; i3++) {
                    i2 += this.tableGridCol.get(Integer.valueOf(i3)).intValue();
                }
                parseInt2 = Math.max(i2, parseInt2);
            }
            AttrManage.instance().setTableCellWidth(iAttributeSet, parseInt2);
        } else {
            for (int i4 = i; i4 < i + parseInt; i4++) {
                i2 += this.tableGridCol.get(Integer.valueOf(i4)).intValue();
            }
            AttrManage.instance().setTableCellWidth(iAttributeSet, i2);
        }
        Element element4 = element.element("vMerge");
        if (element4 != null) {
            AttrManage.instance().setTableVerMerged(iAttributeSet, true);
            if (element4.attributeValue("val") != null) {
                AttrManage.instance().setTableVerFirstMerged(iAttributeSet, true);
            }
        }
        Element element5 = element.element("vAlign");
        if (element5 != null) {
            String attributeValue2 = element5.attributeValue("val");
            if (HtmlTags.ALIGN_CENTER.equals(attributeValue2)) {
                AttrManage.instance().setTableCellVerAlign(iAttributeSet, 1);
            } else if (HtmlTags.ALIGN_BOTTOM.equals(attributeValue2)) {
                AttrManage.instance().setTableCellVerAlign(iAttributeSet, 2);
            }
        }
        return parseInt;
    }

    private void processParagraphs_Table(List<Element> list, int i) {
        Iterator<Element> it = list.iterator();
        while (it.hasNext()) {
            Element next = it.next();
            if ("sdt".equals(next.getName()) && (next = next.element("sdtContent")) != null) {
                processParagraphs_Table(next.elements(), i);
            }
            if ("p".equals(next.getName())) {
                processParagraph(next, i);
            } else if (PGPlaceholderUtil.TABLE.equals(next.getName())) {
                processEmbeddedTable(next, i);
            }
        }
    }

    private void processEmbeddedTable(Element element, int i) {
        for (Element elements : element.elements(HtmlTags.TR)) {
            for (Element elements2 : elements.elements("tc")) {
                processParagraphs_Table(elements2.elements(), i);
            }
        }
    }

    /* access modifiers changed from: private */
    public void processParagraph(Element element, int i) {
        ParagraphElement paragraphElement = new ParagraphElement();
        long j = this.offset;
        paragraphElement.setStartOffset(j);
        processParaAttribute(element.element("pPr"), paragraphElement.getAttribute(), i);
        processRun(element, paragraphElement, true);
        paragraphElement.setEndOffset(this.offset);
        long j2 = this.offset;
        if (j2 > j) {
            this.wpdoc.appendParagraph(paragraphElement, j2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:111:0x0252  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0269  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0296  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x02ad  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x02dc  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x0308  */
    /* JADX WARNING: Removed duplicated region for block: B:168:0x0383  */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x03be  */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x041a  */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x044a  */
    /* JADX WARNING: Removed duplicated region for block: B:211:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processParaAttribute(com.app.office.fc.dom4j.Element r17, com.app.office.simpletext.model.IAttributeSet r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            if (r3 <= 0) goto L_0x0011
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            r4.setParaLevel(r2, r3)
        L_0x0011:
            if (r1 != 0) goto L_0x0014
            return
        L_0x0014:
            java.lang.String r3 = "pStyle"
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r3)
            r4 = 0
            java.lang.String r5 = "val"
            if (r3 == 0) goto L_0x003f
            java.lang.String r3 = r3.attributeValue((java.lang.String) r5)
            if (r3 == 0) goto L_0x0046
            int r6 = r3.length()
            if (r6 <= 0) goto L_0x0046
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            java.util.Map<java.lang.String, java.lang.Integer> r7 = r0.styleStrID
            java.lang.Object r3 = r7.get(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            r6.setParaStyleID(r2, r3)
            goto L_0x0046
        L_0x003f:
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            r3.setParaStyleID(r2, r4)
        L_0x0046:
            java.lang.String r3 = "spacing"
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r3)
            r6 = 1120403456(0x42c80000, float:100.0)
            if (r3 == 0) goto L_0x01c0
            java.lang.String r7 = "line"
            java.lang.String r8 = r3.attributeValue((java.lang.String) r7)
            java.lang.String r9 = "afterLines"
            java.lang.String r10 = "after"
            java.lang.String r11 = "beforeLines"
            java.lang.String r12 = "before"
            r13 = 1131413504(0x43700000, float:240.0)
            if (r8 == 0) goto L_0x00d7
            int r8 = java.lang.Integer.parseInt(r8)
            if (r8 != 0) goto L_0x006a
            r8 = 240(0xf0, float:3.36E-43)
        L_0x006a:
            java.lang.String r11 = r3.attributeValue((java.lang.String) r11)
            if (r11 == 0) goto L_0x0087
            int r14 = r11.length()
            if (r14 <= 0) goto L_0x0087
            com.app.office.simpletext.model.AttrManage r14 = com.app.office.simpletext.model.AttrManage.instance()
            int r15 = java.lang.Integer.parseInt(r11)
            float r15 = (float) r15
            float r15 = r15 / r6
            float r4 = (float) r8
            float r15 = r15 * r4
            int r4 = (int) r15
            r14.setParaBefore(r2, r4)
        L_0x0087:
            if (r11 != 0) goto L_0x00a0
            java.lang.String r4 = r3.attributeValue((java.lang.String) r12)
            if (r4 == 0) goto L_0x00a0
            int r11 = r4.length()
            if (r11 <= 0) goto L_0x00a0
            com.app.office.simpletext.model.AttrManage r11 = com.app.office.simpletext.model.AttrManage.instance()
            int r4 = java.lang.Integer.parseInt(r4)
            r11.setParaBefore(r2, r4)
        L_0x00a0:
            java.lang.String r4 = r3.attributeValue((java.lang.String) r9)
            if (r4 == 0) goto L_0x00bd
            int r9 = r4.length()
            if (r9 <= 0) goto L_0x00bd
            com.app.office.simpletext.model.AttrManage r9 = com.app.office.simpletext.model.AttrManage.instance()
            int r11 = java.lang.Integer.parseInt(r4)
            float r11 = (float) r11
            float r11 = r11 / r6
            float r8 = (float) r8
            float r11 = r11 * r8
            int r8 = (int) r11
            r9.setParaAfter(r2, r8)
        L_0x00bd:
            if (r4 != 0) goto L_0x013f
            java.lang.String r4 = r3.attributeValue((java.lang.String) r10)
            if (r4 == 0) goto L_0x013f
            int r8 = r4.length()
            if (r8 <= 0) goto L_0x013f
            com.app.office.simpletext.model.AttrManage r8 = com.app.office.simpletext.model.AttrManage.instance()
            int r4 = java.lang.Integer.parseInt(r4)
            r8.setParaAfter(r2, r4)
            goto L_0x013f
        L_0x00d7:
            java.lang.String r4 = r3.attributeValue((java.lang.String) r12)
            if (r4 == 0) goto L_0x00ef
            int r8 = r4.length()
            if (r8 <= 0) goto L_0x00ef
            com.app.office.simpletext.model.AttrManage r8 = com.app.office.simpletext.model.AttrManage.instance()
            int r4 = java.lang.Integer.parseInt(r4)
            r8.setParaBefore(r2, r4)
            goto L_0x010b
        L_0x00ef:
            java.lang.String r4 = r3.attributeValue((java.lang.String) r11)
            if (r4 == 0) goto L_0x010b
            int r8 = r4.length()
            if (r8 <= 0) goto L_0x010b
            com.app.office.simpletext.model.AttrManage r8 = com.app.office.simpletext.model.AttrManage.instance()
            int r4 = java.lang.Integer.parseInt(r4)
            float r4 = (float) r4
            float r4 = r4 / r6
            float r4 = r4 * r13
            int r4 = (int) r4
            r8.setParaBefore(r2, r4)
        L_0x010b:
            java.lang.String r4 = r3.attributeValue((java.lang.String) r10)
            if (r4 == 0) goto L_0x0123
            int r8 = r4.length()
            if (r8 <= 0) goto L_0x0123
            com.app.office.simpletext.model.AttrManage r8 = com.app.office.simpletext.model.AttrManage.instance()
            int r4 = java.lang.Integer.parseInt(r4)
            r8.setParaAfter(r2, r4)
            goto L_0x013f
        L_0x0123:
            java.lang.String r4 = r3.attributeValue((java.lang.String) r9)
            if (r4 == 0) goto L_0x013f
            int r8 = r4.length()
            if (r8 <= 0) goto L_0x013f
            com.app.office.simpletext.model.AttrManage r8 = com.app.office.simpletext.model.AttrManage.instance()
            int r4 = java.lang.Integer.parseInt(r4)
            float r4 = (float) r4
            float r4 = r4 / r6
            float r4 = r4 * r13
            int r4 = (int) r4
            r8.setParaAfter(r2, r4)
        L_0x013f:
            java.lang.String r4 = "lineRule"
            java.lang.String r4 = r3.attributeValue((java.lang.String) r4)
            java.lang.String r8 = r3.attributeValue((java.lang.String) r7)
            r9 = 0
            if (r8 == 0) goto L_0x0155
            java.lang.String r3 = r3.attributeValue((java.lang.String) r7)
            float r3 = java.lang.Float.parseFloat(r3)
            goto L_0x0156
        L_0x0155:
            r3 = 0
        L_0x0156:
            java.lang.String r7 = "auto"
            boolean r7 = r7.equals(r4)
            r8 = 5
            if (r7 == 0) goto L_0x0173
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            r4.setParaLineSpaceType(r2, r8)
            int r4 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r4 == 0) goto L_0x01c0
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            float r3 = r3 / r13
            r4.setParaLineSpace(r2, r3)
            goto L_0x01c0
        L_0x0173:
            java.lang.String r7 = "atLeast"
            boolean r7 = r7.equals(r4)
            if (r7 == 0) goto L_0x018f
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            r7 = 3
            r4.setParaLineSpaceType(r2, r7)
            int r4 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r4 == 0) goto L_0x01c0
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            r4.setParaLineSpace(r2, r3)
            goto L_0x01c0
        L_0x018f:
            java.lang.String r7 = "exact"
            boolean r4 = r7.equals(r4)
            if (r4 == 0) goto L_0x01ac
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            r7 = 4
            r4.setParaLineSpaceType(r2, r7)
            int r4 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r4 == 0) goto L_0x01c0
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            float r3 = -r3
            r4.setParaLineSpace(r2, r3)
            goto L_0x01c0
        L_0x01ac:
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            r4.setParaLineSpaceType(r2, r8)
            int r4 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r4 == 0) goto L_0x01c0
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            float r3 = -r3
            float r3 = r3 / r13
            r4.setParaLineSpace(r2, r3)
        L_0x01c0:
            java.lang.String r3 = "ind"
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r3)
            java.lang.String r4 = "right"
            java.lang.String r7 = "left"
            if (r3 == 0) goto L_0x032e
            r8 = 12
            com.app.office.simpletext.model.StyleManage r9 = com.app.office.simpletext.model.StyleManage.instance()
            java.util.Map<java.lang.String, java.lang.Integer> r10 = r0.styleStrID
            java.lang.String r11 = "docDefaults"
            java.lang.Object r10 = r10.get(r11)
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r10 = r10.intValue()
            com.app.office.simpletext.model.Style r9 = r9.getStyle(r10)
            if (r9 == 0) goto L_0x01f4
            com.app.office.simpletext.model.IAttributeSet r9 = r9.getAttrbuteSet()
            if (r9 == 0) goto L_0x01f4
            com.app.office.simpletext.model.AttrManage r8 = com.app.office.simpletext.model.AttrManage.instance()
            int r8 = r8.getFontSize(r9, r9)
        L_0x01f4:
            java.lang.String r9 = "leftChars"
            java.lang.String r9 = r3.attributeValue((java.lang.String) r9)
            r10 = 1101004800(0x41a00000, float:20.0)
            java.lang.String r11 = "0"
            if (r9 == 0) goto L_0x0223
            int r12 = r9.length()
            if (r12 <= 0) goto L_0x0223
            boolean r12 = r9.equals(r11)
            if (r12 != 0) goto L_0x0223
            int r9 = java.lang.Integer.parseInt(r9)
            float r9 = (float) r9
            float r9 = r9 / r6
            com.app.office.simpletext.model.AttrManage r12 = com.app.office.simpletext.model.AttrManage.instance()
            float r13 = (float) r8
            float r13 = r13 * r9
            float r13 = r13 * r10
            int r9 = java.lang.Math.round(r13)
            r12.setParaIndentLeft(r2, r9)
            goto L_0x023d
        L_0x0223:
            java.lang.String r9 = r3.attributeValue((java.lang.String) r7)
            if (r9 == 0) goto L_0x023d
            if (r9 == 0) goto L_0x023d
            int r12 = r9.length()
            if (r12 <= 0) goto L_0x023d
            int r9 = java.lang.Integer.parseInt(r9)
            com.app.office.simpletext.model.AttrManage r12 = com.app.office.simpletext.model.AttrManage.instance()
            r12.setParaIndentLeft(r2, r9)
            goto L_0x023e
        L_0x023d:
            r9 = 0
        L_0x023e:
            java.lang.String r12 = "rightChars"
            java.lang.String r12 = r3.attributeValue((java.lang.String) r12)
            if (r12 == 0) goto L_0x0269
            int r13 = r12.length()
            if (r13 <= 0) goto L_0x0269
            boolean r13 = r12.equals(r11)
            if (r13 != 0) goto L_0x0269
            int r12 = java.lang.Integer.parseInt(r12)
            float r12 = (float) r12
            float r12 = r12 / r6
            com.app.office.simpletext.model.AttrManage r13 = com.app.office.simpletext.model.AttrManage.instance()
            float r14 = (float) r8
            float r14 = r14 * r12
            float r14 = r14 * r10
            int r12 = java.lang.Math.round(r14)
            r13.setParaIndentRight(r2, r12)
            goto L_0x0282
        L_0x0269:
            java.lang.String r12 = r3.attributeValue((java.lang.String) r4)
            if (r12 == 0) goto L_0x0282
            if (r12 == 0) goto L_0x0282
            int r13 = r12.length()
            if (r13 <= 0) goto L_0x0282
            com.app.office.simpletext.model.AttrManage r13 = com.app.office.simpletext.model.AttrManage.instance()
            int r12 = java.lang.Integer.parseInt(r12)
            r13.setParaIndentRight(r2, r12)
        L_0x0282:
            java.lang.String r12 = "firstLineChars"
            java.lang.String r12 = r3.attributeValue((java.lang.String) r12)
            if (r12 == 0) goto L_0x02ad
            int r13 = r12.length()
            if (r13 <= 0) goto L_0x02ad
            boolean r13 = r12.equals(r11)
            if (r13 != 0) goto L_0x02ad
            int r12 = java.lang.Integer.parseInt(r12)
            float r12 = (float) r12
            float r12 = r12 / r6
            com.app.office.simpletext.model.AttrManage r13 = com.app.office.simpletext.model.AttrManage.instance()
            float r14 = (float) r8
            float r14 = r14 * r12
            float r14 = r14 * r10
            int r12 = java.lang.Math.round(r14)
            r13.setParaSpecialIndent(r2, r12)
            goto L_0x02c8
        L_0x02ad:
            java.lang.String r12 = "firstLine"
            java.lang.String r12 = r3.attributeValue((java.lang.String) r12)
            if (r12 == 0) goto L_0x02c8
            if (r12 == 0) goto L_0x02c8
            int r13 = r12.length()
            if (r13 <= 0) goto L_0x02c8
            com.app.office.simpletext.model.AttrManage r13 = com.app.office.simpletext.model.AttrManage.instance()
            int r12 = java.lang.Integer.parseInt(r12)
            r13.setParaSpecialIndent(r2, r12)
        L_0x02c8:
            java.lang.String r12 = "hangingChars"
            java.lang.String r12 = r3.attributeValue((java.lang.String) r12)
            if (r12 == 0) goto L_0x0308
            int r13 = r12.length()
            if (r13 <= 0) goto L_0x0308
            boolean r11 = r12.equals(r11)
            if (r11 != 0) goto L_0x0308
            int r3 = java.lang.Integer.parseInt(r12)
            float r3 = (float) r3
            float r3 = r3 / r6
            float r6 = (float) r8
            float r6 = r6 * r3
            float r6 = r6 * r10
            int r3 = java.lang.Math.round(r6)
            int r3 = -r3
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setParaSpecialIndent(r2, r3)
            if (r9 != 0) goto L_0x02fd
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            int r9 = r6.getParaIndentLeft(r2)
        L_0x02fd:
            if (r3 >= 0) goto L_0x032e
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            int r9 = r9 + r3
            r6.setParaIndentLeft(r2, r9)
            goto L_0x032e
        L_0x0308:
            java.lang.String r6 = "hanging"
            java.lang.String r3 = r3.attributeValue((java.lang.String) r6)
            if (r3 == 0) goto L_0x032e
            if (r3 == 0) goto L_0x032e
            int r6 = r3.length()
            if (r6 <= 0) goto L_0x032e
            int r3 = java.lang.Integer.parseInt(r3)
            int r3 = -r3
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setParaSpecialIndent(r2, r3)
            if (r3 >= 0) goto L_0x032e
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            int r9 = r9 + r3
            r6.setParaIndentLeft(r2, r9)
        L_0x032e:
            java.lang.String r3 = "jc"
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r3)
            if (r3 == 0) goto L_0x037a
            java.lang.String r3 = r3.attributeValue((java.lang.String) r5)
            boolean r6 = r7.equals(r3)
            if (r6 != 0) goto L_0x0371
            java.lang.String r6 = "both"
            boolean r6 = r6.equals(r3)
            if (r6 != 0) goto L_0x0371
            java.lang.String r6 = "distribute"
            boolean r6 = r6.equals(r3)
            if (r6 == 0) goto L_0x0351
            goto L_0x0371
        L_0x0351:
            java.lang.String r6 = "center"
            boolean r6 = r6.equals(r3)
            if (r6 == 0) goto L_0x0362
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            r4 = 1
            r3.setParaHorizontalAlign(r2, r4)
            goto L_0x037a
        L_0x0362:
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x037a
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            r4 = 2
            r3.setParaHorizontalAlign(r2, r4)
            goto L_0x037a
        L_0x0371:
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            r4 = 0
            r3.setParaHorizontalAlign(r2, r4)
            goto L_0x037b
        L_0x037a:
            r4 = 0
        L_0x037b:
            java.lang.String r3 = "numPr"
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r3)
            if (r3 == 0) goto L_0x03be
            java.lang.String r4 = "ilvl"
            com.app.office.fc.dom4j.Element r4 = r3.element((java.lang.String) r4)
            if (r4 == 0) goto L_0x039a
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            java.lang.String r4 = r4.attributeValue((java.lang.String) r5)
            int r4 = java.lang.Integer.parseInt(r4)
            r6.setParaListLevel(r2, r4)
        L_0x039a:
            java.lang.String r4 = "numId"
            com.app.office.fc.dom4j.Element r3 = r3.element((java.lang.String) r4)
            if (r3 == 0) goto L_0x0400
            java.lang.String r3 = r3.attributeValue((java.lang.String) r5)
            if (r3 == 0) goto L_0x0400
            java.util.Hashtable<java.lang.String, java.lang.String> r4 = r0.bulletNumbersID
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            if (r3 == 0) goto L_0x0400
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            int r3 = java.lang.Integer.parseInt(r3)
            r4.setParaListID(r2, r3)
            goto L_0x0400
        L_0x03be:
            com.app.office.simpletext.model.StyleManage r3 = com.app.office.simpletext.model.StyleManage.instance()
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            int r6 = r6.getParaStyleID(r2)
            com.app.office.simpletext.model.Style r3 = r3.getStyle(r6)
            if (r3 == 0) goto L_0x0400
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r7 = r3.getAttrbuteSet()
            int r6 = r6.getParaListLevel(r7)
            com.app.office.simpletext.model.AttrManage r7 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r3 = r3.getAttrbuteSet()
            int r3 = r7.getParaListID(r3)
            r7 = -1
            if (r3 <= r7) goto L_0x03f7
            if (r6 >= 0) goto L_0x03ee
            goto L_0x03ef
        L_0x03ee:
            r4 = r6
        L_0x03ef:
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setParaListID(r2, r3)
            r6 = r4
        L_0x03f7:
            if (r6 <= r7) goto L_0x0400
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            r3.setParaListLevel(r2, r6)
        L_0x0400:
            java.lang.String r3 = "tabs"
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r3)
            if (r3 == 0) goto L_0x0446
            java.lang.String r4 = "tab"
            java.util.List r3 = r3.elements((java.lang.String) r4)
            if (r3 == 0) goto L_0x0446
            java.util.Iterator r3 = r3.iterator()
        L_0x0414:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0446
            java.lang.Object r4 = r3.next()
            com.app.office.fc.dom4j.Element r4 = (com.app.office.fc.dom4j.Element) r4
            java.lang.String r6 = r4.attributeValue((java.lang.String) r5)
            java.lang.String r7 = "clear"
            boolean r6 = r7.equals(r6)
            if (r6 == 0) goto L_0x0414
            java.lang.String r6 = "pos"
            java.lang.String r4 = r4.attributeValue((java.lang.String) r6)
            if (r4 == 0) goto L_0x0414
            int r6 = r4.length()
            if (r6 <= 0) goto L_0x0414
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            int r4 = java.lang.Integer.parseInt(r4)
            r6.setParaTabsClearPostion(r2, r4)
            goto L_0x0414
        L_0x0446:
            boolean r2 = r0.isProcessSectionAttribute
            if (r2 != 0) goto L_0x0453
            java.lang.String r2 = "sectPr"
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r2)
            r0.processSectionAttribute(r1)
        L_0x0453:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCXReader.processParaAttribute(com.app.office.fc.dom4j.Element, com.app.office.simpletext.model.IAttributeSet, int):void");
    }

    private boolean processRun(Element element, ParagraphElement paragraphElement, boolean z) {
        return processRun(element, paragraphElement, (byte) -1, z);
    }

    /* JADX WARNING: Removed duplicated region for block: B:185:0x03c5  */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x0406  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean processRun(com.app.office.fc.dom4j.Element r25, com.app.office.simpletext.model.ParagraphElement r26, byte r27, boolean r28) {
        /*
            r24 = this;
            r6 = r24
            r7 = r26
            r8 = r27
            java.util.List r0 = r25.elements()
            java.util.Iterator r9 = r0.iterator()
            java.lang.String r10 = ""
            r12 = 0
            r4 = r10
            r5 = r4
            r0 = 0
            r14 = 0
            r15 = 0
        L_0x0016:
            r16 = 0
        L_0x0018:
            boolean r1 = r9.hasNext()
            java.lang.String r2 = "rPr"
            if (r1 == 0) goto L_0x0420
            java.lang.Object r1 = r9.next()
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            java.lang.String r3 = r1.getName()
            java.lang.String r11 = "smartTag"
            boolean r11 = r11.equals(r3)
            if (r11 == 0) goto L_0x003d
            boolean r1 = r6.processRun(r1, r7, r12)
        L_0x0036:
            r14 = r1
        L_0x0037:
            r17 = r4
        L_0x0039:
            r3 = 0
            r4 = 1
            goto L_0x041b
        L_0x003d:
            java.lang.String r11 = "hyperlink"
            boolean r11 = r11.equals(r3)
            if (r11 == 0) goto L_0x004f
            com.app.office.simpletext.model.LeafElement r0 = r6.processHyperlinkRun(r1, r7)
            if (r0 == 0) goto L_0x004d
            r14 = 1
            goto L_0x0018
        L_0x004d:
            r14 = 0
            goto L_0x0018
        L_0x004f:
            java.lang.String r11 = "bookmarkStart"
            boolean r11 = r11.equals(r3)
            if (r11 == 0) goto L_0x0083
            java.lang.String r2 = "name"
            java.lang.String r18 = r1.attributeValue((java.lang.String) r2)
            if (r18 == 0) goto L_0x007c
            com.app.office.system.IControl r1 = r6.control
            com.app.office.system.SysKit r1 = r1.getSysKit()
            com.app.office.common.bookmark.BookmarkManage r1 = r1.getBookmarkManage()
            com.app.office.common.bookmark.Bookmark r2 = new com.app.office.common.bookmark.Bookmark
            r23 = r14
            long r13 = r6.offset
            r17 = r2
            r19 = r13
            r21 = r13
            r17.<init>(r18, r19, r21)
            r1.addBookmark(r2)
            goto L_0x007e
        L_0x007c:
            r23 = r14
        L_0x007e:
            r17 = r4
            r14 = r5
            goto L_0x02f4
        L_0x0083:
            r23 = r14
            java.lang.String r13 = "fldSimple"
            boolean r13 = r13.equals(r3)
            java.lang.String r14 = "PAGE"
            java.lang.String r11 = "NUMPAGES"
            r18 = 2
            r19 = -1
            if (r13 == 0) goto L_0x00b7
            java.lang.String r0 = "instr"
            java.lang.String r0 = r1.attributeValue((java.lang.String) r0)
            if (r0 == 0) goto L_0x00ad
            boolean r2 = r0.contains(r11)
            if (r2 == 0) goto L_0x00a5
            r11 = 2
            goto L_0x00ae
        L_0x00a5:
            boolean r0 = r0.contains(r14)
            if (r0 == 0) goto L_0x00ad
            r11 = 1
            goto L_0x00ae
        L_0x00ad:
            r11 = -1
        L_0x00ae:
            boolean r0 = r6.processRun(r1, r7, r11, r12)
            r14 = r0
            r17 = r4
            r0 = 0
            goto L_0x0039
        L_0x00b7:
            java.lang.String r13 = "sdt"
            boolean r13 = r13.equals(r3)
            if (r13 == 0) goto L_0x010a
            java.lang.String r2 = "sdtContent"
            com.app.office.fc.dom4j.Element r2 = r1.element((java.lang.String) r2)
            if (r2 == 0) goto L_0x0106
            java.lang.String r0 = "sdtPr"
            com.app.office.fc.dom4j.Element r0 = r1.element((java.lang.String) r0)
            if (r0 == 0) goto L_0x00ff
            java.lang.String r1 = "docPartObj"
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r1)
            if (r0 == 0) goto L_0x00ff
            java.lang.String r1 = "docPartGallery"
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r1)
            if (r0 == 0) goto L_0x00ff
            java.lang.String r1 = "val"
            java.lang.String r0 = r0.attributeValue((java.lang.String) r1)
            if (r0 == 0) goto L_0x00ff
            boolean r1 = r6.isProcessHF
            if (r1 == 0) goto L_0x00f4
            java.lang.String r1 = "Margins"
            boolean r1 = r0.contains(r1)
            if (r1 == 0) goto L_0x00f4
            return r12
        L_0x00f4:
            java.lang.String r1 = "Watermarks"
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L_0x00ff
            r0 = 1
            r6.isProcessWatermark = r0
        L_0x00ff:
            boolean r14 = r6.processRun(r2, r7, r12)
            r0 = 0
            goto L_0x0037
        L_0x0106:
            r14 = r23
            goto L_0x0037
        L_0x010a:
            java.lang.String r13 = "ins"
            boolean r13 = r13.equals(r3)
            if (r13 == 0) goto L_0x0118
            boolean r1 = r6.processRun(r1, r7, r12)
            goto L_0x0036
        L_0x0118:
            java.lang.String r13 = "r"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0414
            java.lang.String r3 = "fldChar"
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r3)
            if (r3 == 0) goto L_0x0228
            java.lang.String r12 = "fldCharType"
            java.lang.String r3 = r3.attributeValue((java.lang.String) r12)
            java.lang.String r12 = "begin"
            boolean r12 = r12.equals(r3)
            if (r12 == 0) goto L_0x013d
            r14 = r23
            r12 = 0
            r16 = 1
            goto L_0x0018
        L_0x013d:
            java.lang.String r12 = "separate"
            boolean r12 = r12.equals(r3)
            if (r12 == 0) goto L_0x0147
            goto L_0x007e
        L_0x0147:
            java.lang.String r12 = "end"
            boolean r3 = r12.equals(r3)
            if (r3 == 0) goto L_0x0228
            if (r5 == 0) goto L_0x0223
            r3 = 9675(0x25cb, float:1.3558E-41)
            int r3 = r5.indexOf(r3)
            java.lang.String r12 = ","
            if (r3 <= 0) goto L_0x016d
            int r3 = r5.indexOf(r12)
            r13 = 1
            int r3 = r3 + r13
            int r4 = r5.length()
            int r4 = r4 - r13
            java.lang.String r4 = r5.substring(r3, r4)
            r3 = -1
            r11 = 0
            goto L_0x01cc
        L_0x016d:
            r13 = 1
            r3 = 9633(0x25a1, float:1.3499E-41)
            int r3 = r5.indexOf(r3)
            if (r3 <= 0) goto L_0x0187
            int r3 = r5.indexOf(r12)
            int r3 = r3 + r13
            int r4 = r5.length()
            int r4 = r4 - r13
            java.lang.String r4 = r5.substring(r3, r4)
            r3 = -1
            r11 = 1
            goto L_0x01cc
        L_0x0187:
            r3 = 9651(0x25b3, float:1.3524E-41)
            int r3 = r5.indexOf(r3)
            if (r3 <= 0) goto L_0x01a0
            int r3 = r5.indexOf(r12)
            int r3 = r3 + r13
            int r4 = r5.length()
            int r4 = r4 - r13
            java.lang.String r4 = r5.substring(r3, r4)
            r3 = -1
            r11 = 2
            goto L_0x01cc
        L_0x01a0:
            r3 = 9671(0x25c7, float:1.3552E-41)
            int r3 = r5.indexOf(r3)
            if (r3 <= 0) goto L_0x01b9
            int r3 = r5.indexOf(r12)
            int r3 = r3 + r13
            int r4 = r5.length()
            int r4 = r4 - r13
            java.lang.String r4 = r5.substring(r3, r4)
            r11 = 3
            r3 = -1
            goto L_0x01cc
        L_0x01b9:
            boolean r3 = r5.contains(r11)
            if (r3 == 0) goto L_0x01c2
            r3 = 2
        L_0x01c0:
            r11 = -1
            goto L_0x01cc
        L_0x01c2:
            boolean r3 = r5.contains(r14)
            if (r3 == 0) goto L_0x01ca
            r3 = 1
            goto L_0x01c0
        L_0x01ca:
            r3 = -1
            goto L_0x01c0
        L_0x01cc:
            int r5 = r4.length()
            if (r5 <= 0) goto L_0x021e
            com.app.office.simpletext.model.LeafElement r0 = new com.app.office.simpletext.model.LeafElement
            r0.<init>(r4)
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r2)
            if (r1 == 0) goto L_0x01e4
            com.app.office.simpletext.model.IAttributeSet r2 = r0.getAttribute()
            r6.processRunAttribute(r1, r2)
        L_0x01e4:
            long r1 = r6.offset
            r0.setStartOffset(r1)
            long r1 = r6.offset
            int r4 = r4.length()
            long r4 = (long) r4
            long r1 = r1 + r4
            r6.offset = r1
            r0.setEndOffset(r1)
            if (r11 < 0) goto L_0x0204
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r2 = r0.getAttribute()
            r1.setEncloseChanacterType(r2, r11)
            goto L_0x0219
        L_0x0204:
            boolean r1 = r6.isProcessHF
            if (r1 == 0) goto L_0x0219
            com.app.office.fc.openxml4j.opc.PackagePart r1 = r6.hfPart
            if (r1 == 0) goto L_0x0219
            if (r3 <= 0) goto L_0x0219
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r2 = r0.getAttribute()
            r1.setFontPageNumberType(r2, r3)
        L_0x0219:
            r7.appendLeaf(r0)
            r14 = 1
            goto L_0x0220
        L_0x021e:
            r14 = r23
        L_0x0220:
            r4 = r10
            r5 = r4
            goto L_0x0225
        L_0x0223:
            r14 = r23
        L_0x0225:
            r12 = 0
            goto L_0x0016
        L_0x0228:
            java.lang.String r3 = "t"
            if (r16 == 0) goto L_0x0264
            java.lang.String r2 = "instrText"
            com.app.office.fc.dom4j.Element r2 = r1.element((java.lang.String) r2)
            if (r2 == 0) goto L_0x0249
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r5)
            java.lang.String r2 = r2.getText()
            r1.append(r2)
            java.lang.String r5 = r1.toString()
            goto L_0x02f7
        L_0x0249:
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r3)
            if (r1 == 0) goto L_0x02f7
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r4)
            java.lang.String r1 = r1.getText()
            r2.append(r1)
            java.lang.String r4 = r2.toString()
            goto L_0x02f7
        L_0x0264:
            java.lang.String r11 = "object"
            com.app.office.fc.dom4j.Element r11 = r1.element((java.lang.String) r11)
            if (r11 == 0) goto L_0x0296
            java.util.Iterator r11 = r11.elementIterator()
        L_0x0270:
            boolean r0 = r11.hasNext()
            if (r0 == 0) goto L_0x0292
            java.lang.Object r0 = r11.next()
            r1 = r0
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            r3 = 0
            r13 = 1065353216(0x3f800000, float:1.0)
            r14 = 1065353216(0x3f800000, float:1.0)
            r0 = r24
            r2 = r26
            r17 = r4
            r4 = r13
            r13 = r5
            r5 = r14
            r0.processAutoShapeForPict(r1, r2, r3, r4, r5)
            r5 = r13
            r4 = r17
            goto L_0x0270
        L_0x0292:
            r17 = r4
            r13 = r5
            goto L_0x02a7
        L_0x0296:
            r17 = r4
            r14 = r5
            java.lang.String r4 = "drawing"
            com.app.office.fc.dom4j.Element r4 = r1.element((java.lang.String) r4)
            if (r4 == 0) goto L_0x02ac
            r6.processPictureAndDiagram(r4, r7)
        L_0x02a4:
            r5 = r14
            r4 = r17
        L_0x02a7:
            r14 = r23
            r0 = 0
            goto L_0x041d
        L_0x02ac:
            java.lang.String r4 = "pict"
            com.app.office.fc.dom4j.Element r4 = r1.element((java.lang.String) r4)
            if (r4 == 0) goto L_0x02d2
            java.util.Iterator r11 = r4.elementIterator()
        L_0x02b8:
            boolean r0 = r11.hasNext()
            if (r0 == 0) goto L_0x02a4
            java.lang.Object r0 = r11.next()
            r1 = r0
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            r3 = 0
            r4 = 1065353216(0x3f800000, float:1.0)
            r5 = 1065353216(0x3f800000, float:1.0)
            r0 = r24
            r2 = r26
            r0.processAutoShapeForPict(r1, r2, r3, r4, r5)
            goto L_0x02b8
        L_0x02d2:
            java.lang.String r4 = "AlternateContent"
            com.app.office.fc.dom4j.Element r4 = r1.element((java.lang.String) r4)
            if (r4 == 0) goto L_0x02de
            r6.processAlternateContent(r4, r7)
            goto L_0x02a4
        L_0x02de:
            java.lang.String r4 = "ruby"
            com.app.office.fc.dom4j.Element r4 = r1.element((java.lang.String) r4)
            if (r4 == 0) goto L_0x02fb
            java.lang.String r5 = "rubyBase"
            com.app.office.fc.dom4j.Element r4 = r4.element((java.lang.String) r5)
            if (r4 == 0) goto L_0x02fb
            com.app.office.fc.dom4j.Element r1 = r4.element((java.lang.String) r13)
            if (r1 != 0) goto L_0x02fb
        L_0x02f4:
            r5 = r14
            r4 = r17
        L_0x02f7:
            r14 = r23
            goto L_0x041d
        L_0x02fb:
            java.lang.String r4 = "br"
            com.app.office.fc.dom4j.Element r4 = r1.element((java.lang.String) r4)
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r3)
            r5 = 11
            if (r3 == 0) goto L_0x0323
            java.lang.String r3 = r3.getText()
            if (r4 == 0) goto L_0x0341
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r4.append(r5)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            goto L_0x0341
        L_0x0323:
            if (r4 == 0) goto L_0x0340
            java.lang.String r3 = "type"
            java.lang.String r3 = r4.attributeValue((java.lang.String) r3)
            java.lang.String r4 = "page"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x033b
            r3 = 12
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r15 = 1
            goto L_0x0341
        L_0x033b:
            java.lang.String r3 = java.lang.String.valueOf(r5)
            goto L_0x0341
        L_0x0340:
            r3 = r10
        L_0x0341:
            int r4 = r3.length()
            if (r4 <= 0) goto L_0x0410
            com.app.office.simpletext.model.LeafElement r0 = new com.app.office.simpletext.model.LeafElement
            r0.<init>(r3)
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r2)
            if (r1 == 0) goto L_0x0359
            com.app.office.simpletext.model.IAttributeSet r2 = r0.getAttribute()
            r6.processRunAttribute(r1, r2)
        L_0x0359:
            boolean r1 = r6.isProcessHF
            if (r1 == 0) goto L_0x036e
            com.app.office.fc.openxml4j.opc.PackagePart r1 = r6.hfPart
            if (r1 == 0) goto L_0x036e
            if (r8 <= 0) goto L_0x036e
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r2 = r0.getAttribute()
            r1.setFontPageNumberType(r2, r8)
        L_0x036e:
            long r1 = r6.offset
            r0.setStartOffset(r1)
            long r1 = r6.offset
            long r3 = (long) r4
            long r1 = r1 + r3
            r6.offset = r1
            r0.setEndOffset(r1)
            r7.appendLeaf(r0)
            if (r14 == 0) goto L_0x040b
            java.lang.String r1 = "mailto"
            int r2 = r14.indexOf(r1)
            java.lang.String r3 = "\""
            if (r2 < 0) goto L_0x039f
            int r1 = r14.indexOf(r1)
            java.lang.String r1 = r14.substring(r1)
            int r2 = r1.indexOf(r3)
            if (r2 <= 0) goto L_0x03bf
            r3 = 0
            java.lang.String r1 = r1.substring(r3, r2)
            goto L_0x03bf
        L_0x039f:
            java.lang.String r1 = "HYPERLINK"
            int r1 = r14.indexOf(r1)
            if (r1 < 0) goto L_0x03c1
            int r1 = r14.indexOf(r3)
            r2 = 1
            int r1 = r1 + r2
            java.lang.String r1 = r14.substring(r1)
            java.lang.String r2 = "/"
            int r2 = r1.lastIndexOf(r2)
            if (r2 <= 0) goto L_0x03bf
            r3 = 0
            java.lang.String r1 = r1.substring(r3, r2)
            goto L_0x03c3
        L_0x03bf:
            r3 = 0
            goto L_0x03c3
        L_0x03c1:
            r3 = 0
            r1 = 0
        L_0x03c3:
            if (r1 == 0) goto L_0x0406
            com.app.office.system.IControl r2 = r6.control
            com.app.office.system.SysKit r2 = r2.getSysKit()
            com.app.office.common.hyperlink.HyperlinkManage r2 = r2.getHyperlinkManage()
            r4 = 1
            int r1 = r2.addHyperlink(r1, r4)
            if (r1 < 0) goto L_0x0407
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r5 = r0.getAttribute()
            r11 = -16776961(0xffffffffff0000ff, float:-1.7014636E38)
            r2.setFontColor(r5, r11)
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r5 = r0.getAttribute()
            r2.setFontUnderline(r5, r4)
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r5 = r0.getAttribute()
            r2.setFontUnderlineColr(r5, r11)
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r5 = r0.getAttribute()
            r2.setHyperlinkID(r5, r1)
            goto L_0x0407
        L_0x0406:
            r4 = 1
        L_0x0407:
            r5 = r10
            r17 = r5
            goto L_0x040e
        L_0x040b:
            r3 = 0
            r4 = 1
            r5 = r14
        L_0x040e:
            r14 = 1
            goto L_0x041b
        L_0x0410:
            r3 = 0
            r4 = 1
            r5 = r14
            goto L_0x0419
        L_0x0414:
            r17 = r4
            r14 = r5
            r3 = 0
            r4 = 1
        L_0x0419:
            r14 = r23
        L_0x041b:
            r4 = r17
        L_0x041d:
            r12 = 0
            goto L_0x0018
        L_0x0420:
            r23 = r14
            r3 = 1
            java.lang.String r1 = "\n"
            if (r23 != 0) goto L_0x0455
            com.app.office.simpletext.model.LeafElement r0 = new com.app.office.simpletext.model.LeafElement
            r0.<init>(r1)
            java.lang.String r1 = "pPr"
            r5 = r25
            com.app.office.fc.dom4j.Element r1 = r5.element((java.lang.String) r1)
            if (r1 == 0) goto L_0x043b
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r2)
        L_0x043b:
            if (r1 == 0) goto L_0x0444
            com.app.office.simpletext.model.IAttributeSet r2 = r0.getAttribute()
            r6.processRunAttribute(r1, r2)
        L_0x0444:
            long r1 = r6.offset
            r0.setStartOffset(r1)
            long r1 = r6.offset
            long r1 = r1 + r3
            r6.offset = r1
            r0.setEndOffset(r1)
            r7.appendLeaf(r0)
            return r23
        L_0x0455:
            if (r28 == 0) goto L_0x0478
            if (r0 == 0) goto L_0x0478
            if (r15 != 0) goto L_0x0478
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            com.app.office.wp.model.WPDocument r5 = r6.wpdoc
            java.lang.String r5 = r0.getText(r5)
            r2.append(r5)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.setText(r1)
            long r0 = r6.offset
            long r0 = r0 + r3
            r6.offset = r0
        L_0x0478:
            return r23
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCXReader.processRun(com.app.office.fc.dom4j.Element, com.app.office.simpletext.model.ParagraphElement, byte, boolean):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x006b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.simpletext.model.LeafElement processHyperlinkRun(com.app.office.fc.dom4j.Element r14, com.app.office.simpletext.model.ParagraphElement r15) {
        /*
            r13 = this;
            r0 = 1
            r1 = 0
            java.lang.String r2 = "id"
            java.lang.String r2 = r14.attributeValue((java.lang.String) r2)     // Catch:{ InvalidFormatException -> 0x0017 }
            if (r2 == 0) goto L_0x0025
            com.app.office.fc.openxml4j.opc.PackagePart r3 = r13.packagePart     // Catch:{ InvalidFormatException -> 0x0017 }
            java.lang.String r4 = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/hyperlink"
            com.app.office.fc.openxml4j.opc.PackageRelationshipCollection r3 = r3.getRelationshipsByType(r4)     // Catch:{ InvalidFormatException -> 0x0017 }
            com.app.office.fc.openxml4j.opc.PackageRelationship r2 = r3.getRelationshipByID(r2)     // Catch:{ InvalidFormatException -> 0x0017 }
            goto L_0x0026
        L_0x0017:
            r2 = move-exception
            com.app.office.system.IControl r3 = r13.control
            com.app.office.system.SysKit r3 = r3.getSysKit()
            com.app.office.system.ErrorUtil r3 = r3.getErrorKit()
            r3.writerLog(r2, r0)
        L_0x0025:
            r2 = r1
        L_0x0026:
            r3 = -1
            if (r2 == 0) goto L_0x0040
            com.app.office.system.IControl r4 = r13.control
            com.app.office.system.SysKit r4 = r4.getSysKit()
            com.app.office.common.hyperlink.HyperlinkManage r4 = r4.getHyperlinkManage()
            java.net.URI r2 = r2.getTargetURI()
            java.lang.String r2 = r2.toString()
            int r2 = r4.addHyperlink(r2, r0)
            goto L_0x0041
        L_0x0040:
            r2 = -1
        L_0x0041:
            if (r2 != r3) goto L_0x005a
            java.lang.String r4 = "anchor"
            java.lang.String r4 = r14.attributeValue((java.lang.String) r4)
            if (r4 == 0) goto L_0x005a
            com.app.office.system.IControl r2 = r13.control
            com.app.office.system.SysKit r2 = r2.getSysKit()
            com.app.office.common.hyperlink.HyperlinkManage r2 = r2.getHyperlinkManage()
            r5 = 5
            int r2 = r2.addHyperlink(r4, r5)
        L_0x005a:
            java.lang.String r4 = "r"
            java.util.List r14 = r14.elements((java.lang.String) r4)
            java.util.Iterator r14 = r14.iterator()
        L_0x0064:
            r5 = r1
        L_0x0065:
            boolean r6 = r14.hasNext()
            if (r6 == 0) goto L_0x0105
            java.lang.Object r6 = r14.next()
            com.app.office.fc.dom4j.Element r6 = (com.app.office.fc.dom4j.Element) r6
            java.lang.String r7 = "instrText"
            com.app.office.fc.dom4j.Element r7 = r6.element((java.lang.String) r7)
            if (r7 == 0) goto L_0x0088
            java.lang.String r7 = r7.getText()
            if (r7 == 0) goto L_0x0088
            java.lang.String r8 = "PAGEREF"
            boolean r7 = r7.contains(r8)
            if (r7 == 0) goto L_0x0088
            r2 = -1
        L_0x0088:
            java.lang.String r7 = "ruby"
            com.app.office.fc.dom4j.Element r7 = r6.element((java.lang.String) r7)
            if (r7 == 0) goto L_0x009f
            java.lang.String r8 = "rubyBase"
            com.app.office.fc.dom4j.Element r7 = r7.element((java.lang.String) r8)
            if (r7 == 0) goto L_0x009f
            com.app.office.fc.dom4j.Element r6 = r7.element((java.lang.String) r4)
            if (r6 != 0) goto L_0x009f
            goto L_0x0065
        L_0x009f:
            java.lang.String r7 = "t"
            com.app.office.fc.dom4j.Element r7 = r6.element((java.lang.String) r7)
            if (r7 != 0) goto L_0x00b3
            java.lang.String r7 = "drawing"
            com.app.office.fc.dom4j.Element r6 = r6.element((java.lang.String) r7)
            if (r6 == 0) goto L_0x0065
            r13.processPictureAndDiagram(r6, r15)
            goto L_0x0064
        L_0x00b3:
            java.lang.String r7 = r7.getText()
            int r8 = r7.length()
            if (r8 <= 0) goto L_0x0065
            com.app.office.simpletext.model.LeafElement r5 = new com.app.office.simpletext.model.LeafElement
            r5.<init>(r7)
            com.app.office.simpletext.model.IAttributeSet r7 = r5.getAttribute()
            java.lang.String r9 = "rPr"
            com.app.office.fc.dom4j.Element r6 = r6.element((java.lang.String) r9)
            if (r6 == 0) goto L_0x00d1
            r13.processRunAttribute(r6, r7)
        L_0x00d1:
            long r9 = r13.offset
            r5.setStartOffset(r9)
            long r9 = r13.offset
            long r11 = (long) r8
            long r9 = r9 + r11
            r13.offset = r9
            r5.setEndOffset(r9)
            r15.appendLeaf(r5)
            if (r2 < 0) goto L_0x0065
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r8 = -16776961(0xffffffffff0000ff, float:-1.7014636E38)
            r6.setFontColor(r7, r8)
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setFontUnderline(r7, r0)
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setFontUnderlineColr(r7, r8)
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setHyperlinkID(r7, r2)
            goto L_0x0065
        L_0x0105:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCXReader.processHyperlinkRun(com.app.office.fc.dom4j.Element, com.app.office.simpletext.model.ParagraphElement):com.app.office.simpletext.model.LeafElement");
    }

    private void addShape(AbstractShape abstractShape, ParagraphElement paragraphElement) {
        if (abstractShape != null && paragraphElement != null) {
            LeafElement leafElement = new LeafElement(String.valueOf(1));
            leafElement.setStartOffset(this.offset);
            long j = this.offset + 1;
            this.offset = j;
            leafElement.setEndOffset(j);
            paragraphElement.appendLeaf(leafElement);
            AttrManage.instance().setShapeID(leafElement.getAttribute(), this.control.getSysKit().getWPShapeManage().addShape(abstractShape));
        }
    }

    private PictureShape addPicture(Element element, Rectangle rectangle) {
        Element element2;
        String attributeValue;
        PackagePart packagePart2;
        PackagePart packagePart3;
        if (!(element == null || rectangle == null || (element2 = element.element("blipFill")) == null)) {
            PictureEffectInfo pictureEffectInfor = PictureEffectInfoFactory.getPictureEffectInfor(element2);
            Element element3 = element2.element("blip");
            if (!(element3 == null || (attributeValue = element3.attributeValue("embed")) == null)) {
                if (!this.isProcessHF || (packagePart3 = this.hfPart) == null) {
                    packagePart2 = this.zipPackage.getPart(this.packagePart.getRelationship(attributeValue).getTargetURI());
                } else {
                    packagePart2 = this.zipPackage.getPart(packagePart3.getRelationship(attributeValue).getTargetURI());
                }
                if (packagePart2 != null) {
                    PictureShape pictureShape = new PictureShape();
                    try {
                        pictureShape.setPictureIndex(this.control.getSysKit().getPictureManage().addPicture(packagePart2));
                    } catch (Exception e) {
                        this.control.getSysKit().getErrorKit().writerLog(e);
                    }
                    pictureShape.setZoomX(1000);
                    pictureShape.setZoomY(1000);
                    pictureShape.setPictureEffectInfor(pictureEffectInfor);
                    pictureShape.setBounds(rectangle);
                    return pictureShape;
                }
            }
        }
        return null;
    }

    private byte getRelative(String str) {
        if ("margin".equalsIgnoreCase(str)) {
            return 1;
        }
        if (Annotation.PAGE.equalsIgnoreCase(str)) {
            return 2;
        }
        if ("column".equalsIgnoreCase(str)) {
            return 0;
        }
        if (FirebaseAnalytics.Param.CHARACTER.equalsIgnoreCase(str)) {
            return 3;
        }
        if ("leftMargin".equalsIgnoreCase(str)) {
            return 4;
        }
        if ("rightMargin".equalsIgnoreCase(str)) {
            return 5;
        }
        if ("insideMargin".equalsIgnoreCase(str)) {
            return 8;
        }
        if ("outsideMargin".equalsIgnoreCase(str)) {
            return 9;
        }
        if ("paragraph".equalsIgnoreCase(str)) {
            return 10;
        }
        if ("line".equalsIgnoreCase(str)) {
            return 11;
        }
        if ("topMargin".equalsIgnoreCase(str)) {
            return 6;
        }
        if ("bottomMargin".equalsIgnoreCase(str)) {
            return 7;
        }
        return 0;
    }

    private byte getAlign(String str) {
        if (HtmlTags.ALIGN_LEFT.equalsIgnoreCase(str)) {
            return 1;
        }
        if (HtmlTags.ALIGN_RIGHT.equalsIgnoreCase(str)) {
            return 3;
        }
        if (HtmlTags.ALIGN_TOP.equalsIgnoreCase(str)) {
            return 4;
        }
        if (HtmlTags.ALIGN_BOTTOM.equalsIgnoreCase(str)) {
            return 5;
        }
        if (HtmlTags.ALIGN_CENTER.equalsIgnoreCase(str)) {
            return 2;
        }
        if ("inside".equalsIgnoreCase(str)) {
            return 6;
        }
        return "outside".equalsIgnoreCase(str) ? (byte) 7 : 0;
    }

    private void processWrapAndPosition_Drawing(WPAbstractShape wPAbstractShape, Element element, Rectangle rectangle) {
        if ("1".equalsIgnoreCase(element.attributeValue("behindDoc"))) {
            wPAbstractShape.setWrap(6);
        }
        wPAbstractShape.setWrap(getDrawingWrapType(element));
        Element element2 = null;
        Element element3 = null;
        for (Element element4 : element.elements("AlternateContent")) {
            Element element5 = element4.element("Choice");
            if (element5 != null) {
                if (element5.element("positionH") != null) {
                    element2 = element5.element("positionH");
                }
                if (element5.element("positionV") != null) {
                    element3 = element5.element("positionV");
                }
            }
        }
        if (element2 == null) {
            element2 = element.element("positionH");
        }
        if (element2 != null) {
            wPAbstractShape.setHorizontalRelativeTo(getRelative(element2.attributeValue("relativeFrom")));
            if (element2.element(HtmlTags.ALIGN) != null) {
                wPAbstractShape.setHorizontalAlignment(getAlign(element2.element(HtmlTags.ALIGN).getText()));
            } else if (element2.element("posOffset") != null) {
                rectangle.translate(Math.round((((float) Integer.parseInt(element2.element("posOffset").getText())) * 96.0f) / 914400.0f), 0);
            } else if (element2.element("pctPosHOffset") != null) {
                wPAbstractShape.setHorRelativeValue(Integer.parseInt(element2.element("pctPosHOffset").getText()) / 100);
                wPAbstractShape.setHorPositionType((byte) 1);
            }
        }
        if (element3 == null) {
            element3 = element.element("positionV");
        }
        if (element3 != null) {
            wPAbstractShape.setVerticalRelativeTo(getRelative(element3.attributeValue("relativeFrom")));
            if (element3.element(HtmlTags.ALIGN) != null) {
                wPAbstractShape.setVerticalAlignment(getAlign(element3.element(HtmlTags.ALIGN).getText()));
            } else if (element3.element("posOffset") != null) {
                rectangle.translate(0, Math.round((((float) Integer.parseInt(element3.element("posOffset").getText())) * 96.0f) / 914400.0f));
            } else if (element3.element("pctPosVOffset") != null) {
                wPAbstractShape.setVerRelativeValue(Integer.parseInt(element3.element("pctPosVOffset").getText()) / 100);
                wPAbstractShape.setVerPositionType((byte) 1);
            }
        }
    }

    private void processPictureAndDiagram(Element element, ParagraphElement paragraphElement) {
        boolean z;
        Element element2;
        Element element3;
        Element element4;
        Element element5;
        String attributeValue;
        String attributeValue2;
        String attributeValue3;
        PackageRelationship relationship;
        Element element6;
        Element element7 = element;
        ParagraphElement paragraphElement2 = paragraphElement;
        Element element8 = element7.element("inline");
        if (element8 == null) {
            element2 = element7.element("anchor");
            z = false;
        } else {
            element2 = element8;
            z = true;
        }
        if (element2 != null && (element3 = element2.element("graphic")) != null && (element4 = element3.element("graphicData")) != null) {
            Element element9 = element4.element(PGPlaceholderUtil.PICTURE);
            if (element9 != null) {
                Element element10 = element9.element("spPr");
                if (element10 != null) {
                    PackagePart packagePart2 = null;
                    if (!(element10.element("blipFill") == null || (element6 = element10.element("blipFill").element("blip")) == null || element6.attributeValue("embed") == null || (this.isProcessHF && (packagePart2 = this.hfPart) != null))) {
                        packagePart2 = this.packagePart;
                    }
                    PackagePart packagePart3 = packagePart2;
                    PictureShape addPicture = addPicture(element9, ReaderKit.instance().getShapeAnchor(element10.element("xfrm"), 1.0f, 1.0f));
                    if (addPicture != null) {
                        AutoShapeDataKit.processPictureShape(this.control, this.zipPackage, packagePart3, element10, this.themeColor, addPicture);
                        WPPictureShape wPPictureShape = new WPPictureShape();
                        wPPictureShape.setPictureShape(addPicture);
                        wPPictureShape.setBounds(addPicture.getBounds());
                        if (!z) {
                            processWrapAndPosition_Drawing(wPPictureShape, element2, addPicture.getBounds());
                        } else {
                            wPPictureShape.setWrap(2);
                        }
                        addShape(wPPictureShape, paragraphElement2);
                    }
                }
            } else if (element4.element("chart") != null) {
                Element element11 = element4.element("chart");
                if (element11 != null && element11.attribute(OSOutcomeConstants.OUTCOME_ID) != null && (relationship = this.packagePart.getRelationship(element11.attributeValue((String) OSOutcomeConstants.OUTCOME_ID))) != null) {
                    try {
                        AbstractChart read = ChartReader.instance().read(this.control, this.zipPackage, this.zipPackage.getPart(relationship.getTargetURI()), this.themeColor, (byte) 0);
                        if (read != null) {
                            Rectangle rectangle = new Rectangle();
                            Element element12 = element2.element("simplePos");
                            if (element12 != null) {
                                String attributeValue4 = element12.attributeValue("x");
                                if (attributeValue4 != null) {
                                    rectangle.x = (int) ((((float) Integer.parseInt(attributeValue4)) * 96.0f) / 914400.0f);
                                }
                                String attributeValue5 = element12.attributeValue("y");
                                if (attributeValue5 != null) {
                                    rectangle.y = (int) ((((float) Integer.parseInt(attributeValue5)) * 96.0f) / 914400.0f);
                                }
                            }
                            Element element13 = element2.element("extent");
                            if (element13 != null) {
                                String attributeValue6 = element13.attributeValue("cx");
                                if (attributeValue6 != null) {
                                    rectangle.width = (int) ((((float) Integer.parseInt(attributeValue6)) * 96.0f) / 914400.0f);
                                }
                                String attributeValue7 = element13.attributeValue("cy");
                                if (attributeValue7 != null) {
                                    rectangle.height = (int) ((((float) Integer.parseInt(attributeValue7)) * 96.0f) / 914400.0f);
                                }
                            }
                            WPChartShape wPChartShape = new WPChartShape();
                            wPChartShape.setAChart(read);
                            wPChartShape.setBounds(rectangle);
                            if (!z) {
                                processWrapAndPosition_Drawing(wPChartShape, element2, rectangle);
                            } else {
                                wPChartShape.setWrap(2);
                            }
                            addShape(wPChartShape, paragraphElement2);
                        }
                    } catch (Exception unused) {
                    }
                }
            } else if (element4.element("relIds") != null && (element5 = element4.element("relIds")) != null && (attributeValue = element5.attributeValue("dm")) != null) {
                try {
                    PackageRelationship relationshipByID = this.packagePart.getRelationshipsByType(PackageRelationshipTypes.DIAGRAM_DATA).getRelationshipByID(attributeValue);
                    if (relationshipByID != null) {
                        Rectangle rectangle2 = new Rectangle();
                        Element element14 = element2.element("extent");
                        if (element14 != null) {
                            if (!(element14.attribute("cx") == null || (attributeValue3 = element14.attributeValue("cx")) == null || attributeValue3.length() <= 0)) {
                                rectangle2.width = (int) ((((float) Integer.parseInt(attributeValue3)) * 96.0f) / 914400.0f);
                            }
                            if (!(element14.attribute("cy") == null || (attributeValue2 = element14.attributeValue("cy")) == null || attributeValue2.length() <= 0)) {
                                rectangle2.height = (int) ((((float) Integer.parseInt(attributeValue2)) * 96.0f) / 914400.0f);
                            }
                        }
                        PackagePart part = this.zipPackage.getPart(relationshipByID.getTargetURI());
                        processSmart(this.control, this.zipPackage, part, paragraphElement, element2, rectangle2, z);
                    }
                } catch (Exception unused2) {
                }
            }
        }
    }

    private void processSmart(IControl iControl, ZipPackage zipPackage2, PackagePart packagePart2, ParagraphElement paragraphElement, Element element, Rectangle rectangle, boolean z) throws Exception {
        InputStream inputStream;
        Element rootElement;
        Element element2;
        short s;
        Element element3;
        Element element4;
        String attributeValue;
        IControl iControl2 = iControl;
        ZipPackage zipPackage3 = zipPackage2;
        PackagePart packagePart3 = packagePart2;
        Element element5 = element;
        Rectangle rectangle2 = rectangle;
        if (packagePart3 != null && (inputStream = packagePart2.getInputStream()) != null) {
            SAXReader sAXReader = new SAXReader();
            Document read = sAXReader.read(inputStream);
            inputStream.close();
            Element rootElement2 = read.getRootElement();
            BackgroundAndFill processBackground = AutoShapeDataKit.processBackground(iControl2, zipPackage3, packagePart3, rootElement2.element("bg"), this.themeColor);
            Line createLine = LineKit.createLine(iControl2, zipPackage3, packagePart3, rootElement2.element("whole").element("ln"), this.themeColor);
            PackagePart packagePart4 = null;
            Element element6 = rootElement2.element("extLst");
            if (!(element6 == null || (element3 = element6.element("ext")) == null || (element4 = element3.element("dataModelExt")) == null || (attributeValue = element4.attributeValue("relId")) == null)) {
                packagePart4 = zipPackage3.getPart(this.packagePart.getRelationship(attributeValue).getTargetURI());
            }
            PackagePart packagePart5 = packagePart4;
            if (packagePart5 != null) {
                InputStream inputStream2 = packagePart5.getInputStream();
                Document read2 = sAXReader.read(inputStream2);
                inputStream2.close();
                if (read2 != null && (rootElement = read2.getRootElement()) != null && (element2 = rootElement.element("spTree")) != null) {
                    WPGroupShape wPGroupShape = new WPGroupShape();
                    WPAutoShape wPAutoShape = new WPAutoShape();
                    wPAutoShape.addGroupShape(wPGroupShape);
                    if (!z) {
                        processWrapAndPosition_Drawing(wPAutoShape, element5, rectangle2);
                        s = getDrawingWrapType(element5);
                    } else {
                        s = 2;
                    }
                    wPGroupShape.setBounds(rectangle2);
                    wPAutoShape.setBackgroundAndFill(processBackground);
                    wPAutoShape.setLine(createLine);
                    wPAutoShape.setShapeType(1);
                    if (s != 2) {
                        wPGroupShape.setWrapType(s);
                        wPAutoShape.setWrap(s);
                    } else {
                        wPGroupShape.setWrapType(2);
                        wPAutoShape.setWrap(2);
                    }
                    wPAutoShape.setBounds(rectangle2);
                    Iterator elementIterator = element2.elementIterator();
                    while (elementIterator.hasNext()) {
                        processAutoShape2010(packagePart5, paragraphElement, (Element) elementIterator.next(), wPGroupShape, 1.0f, 1.0f, 0, 0, false);
                    }
                    addShape(wPAutoShape, paragraphElement);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0146  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x017e  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x018c  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x019d A[LOOP:0: B:68:0x0197->B:70:0x019d, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x01bc  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0210  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0217  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processAutoShapeForPict(com.app.office.fc.dom4j.Element r18, com.app.office.simpletext.model.ParagraphElement r19, com.app.office.common.shape.WPGroupShape r20, float r21, float r22) {
        /*
            r17 = this;
            r7 = r17
            r8 = r18
            r6 = r20
            java.lang.String r0 = r18.getName()
            java.lang.String r1 = "group"
            boolean r1 = r1.equalsIgnoreCase(r0)
            java.lang.String r2 = "Footer"
            java.lang.String r3 = "Header"
            java.lang.String r4 = "Genko"
            java.lang.String r5 = "id"
            if (r1 == 0) goto L_0x0221
            java.lang.String r0 = r8.attributeValue((java.lang.String) r5)
            if (r0 == 0) goto L_0x0033
            boolean r1 = r0.startsWith(r4)
            if (r1 != 0) goto L_0x0032
            boolean r1 = r0.startsWith(r3)
            if (r1 != 0) goto L_0x0032
            boolean r0 = r0.startsWith(r2)
            if (r0 == 0) goto L_0x0033
        L_0x0032:
            return
        L_0x0033:
            com.app.office.common.shape.WPGroupShape r9 = new com.app.office.common.shape.WPGroupShape
            r9.<init>()
            if (r6 != 0) goto L_0x0047
            com.app.office.common.shape.WPAutoShape r0 = new com.app.office.common.shape.WPAutoShape
            r0.<init>()
            r1 = r0
            com.app.office.common.shape.WPAutoShape r1 = (com.app.office.common.shape.WPAutoShape) r1
            r1.addGroupShape(r9)
            r10 = r0
            goto L_0x0048
        L_0x0047:
            r10 = r9
        L_0x0048:
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r20
            r4 = r21
            r5 = r22
            com.app.office.java.awt.Rectangle r0 = r0.processAutoShapeStyle(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x021c
            r1 = 2
            float[] r11 = new float[r1]
            r11 = {1065353216, 1065353216} // fill-array
            com.app.office.java.awt.Rectangle r2 = new com.app.office.java.awt.Rectangle
            r2.<init>()
            java.lang.String r3 = "coordorigin"
            com.app.office.fc.dom4j.Attribute r4 = r8.attribute((java.lang.String) r3)
            java.lang.String r5 = ","
            r12 = 0
            r13 = 1
            r14 = 0
            if (r4 == 0) goto L_0x00a8
            java.lang.String r3 = r8.attributeValue((java.lang.String) r3)
            if (r3 == 0) goto L_0x00a8
            int r4 = r3.length()
            if (r4 <= 0) goto L_0x00a8
            java.lang.String[] r3 = r3.split(r5)
            if (r3 == 0) goto L_0x00a8
            int r4 = r3.length
            if (r4 != r1) goto L_0x009d
            r4 = r3[r14]
            int r4 = r4.length()
            if (r4 <= 0) goto L_0x0095
            r4 = r3[r14]
            float r4 = java.lang.Float.parseFloat(r4)
            goto L_0x0096
        L_0x0095:
            r4 = 0
        L_0x0096:
            r3 = r3[r13]
            float r3 = java.lang.Float.parseFloat(r3)
            goto L_0x00aa
        L_0x009d:
            int r4 = r3.length
            if (r4 != r13) goto L_0x00a8
            r3 = r3[r14]
            float r4 = java.lang.Float.parseFloat(r3)
            r3 = 0
            goto L_0x00aa
        L_0x00a8:
            r3 = 0
            r4 = 0
        L_0x00aa:
            java.lang.String r15 = "coordsize"
            com.app.office.fc.dom4j.Attribute r16 = r8.attribute((java.lang.String) r15)
            if (r16 == 0) goto L_0x00e8
            java.lang.String r15 = r8.attributeValue((java.lang.String) r15)
            if (r15 == 0) goto L_0x00e8
            int r16 = r15.length()
            if (r16 <= 0) goto L_0x00e8
            java.lang.String[] r5 = r15.split(r5)
            if (r5 == 0) goto L_0x00e8
            int r15 = r5.length
            if (r15 != r1) goto L_0x00de
            r1 = r5[r14]
            int r1 = r1.length()
            if (r1 <= 0) goto L_0x00d6
            r1 = r5[r14]
            float r1 = java.lang.Float.parseFloat(r1)
            goto L_0x00d7
        L_0x00d6:
            r1 = 0
        L_0x00d7:
            r5 = r5[r13]
            float r5 = java.lang.Float.parseFloat(r5)
            goto L_0x00ea
        L_0x00de:
            int r1 = r5.length
            if (r1 != r13) goto L_0x00e8
            r1 = r5[r14]
            float r1 = java.lang.Float.parseFloat(r1)
            goto L_0x00e9
        L_0x00e8:
            r1 = 0
        L_0x00e9:
            r5 = 0
        L_0x00ea:
            r15 = 1119879168(0x42c00000, float:96.0)
            int r16 = (r1 > r12 ? 1 : (r1 == r12 ? 0 : -1))
            if (r16 == 0) goto L_0x010d
            int r12 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r12 == 0) goto L_0x010d
            int r12 = r0.width
            r16 = 914400(0xdf3e0, float:1.281347E-39)
            int r12 = r12 * r16
            float r12 = (float) r12
            float r12 = r12 / r15
            float r12 = r12 / r21
            float r12 = r12 / r1
            r11[r14] = r12
            int r12 = r0.height
            int r12 = r12 * r16
            float r12 = (float) r12
            float r12 = r12 / r15
            float r12 = r12 / r22
            float r12 = r12 / r5
            r11[r13] = r12
        L_0x010d:
            com.app.office.java.awt.Rectangle r0 = r7.processGrpSpRect(r6, r0)
            r12 = r11[r14]
            float r4 = r4 * r12
            float r4 = r4 * r21
            float r4 = r4 * r15
            r12 = 1230978560(0x495f3e00, float:914400.0)
            float r4 = r4 / r12
            int r4 = (int) r4
            r2.x = r4
            r4 = r11[r13]
            float r3 = r3 * r4
            float r3 = r3 * r22
            float r3 = r3 * r15
            float r3 = r3 / r12
            int r3 = (int) r3
            r2.y = r3
            r3 = r11[r14]
            float r1 = r1 * r3
            float r1 = r1 * r21
            float r1 = r1 * r15
            float r1 = r1 / r12
            int r1 = (int) r1
            r2.width = r1
            r1 = r11[r13]
            float r5 = r5 * r1
            float r5 = r5 * r22
            float r5 = r5 * r15
            float r5 = r5 / r12
            int r1 = (int) r5
            r2.height = r1
            if (r6 != 0) goto L_0x0154
            int r1 = r2.x
            int r3 = r0.x
            int r1 = r1 + r3
            r2.x = r1
            int r1 = r2.y
            int r3 = r0.y
            int r1 = r1 + r3
            r2.y = r1
        L_0x0154:
            int r1 = r0.x
            int r3 = r2.x
            int r1 = r1 - r3
            int r3 = r0.y
            int r2 = r2.y
            int r3 = r3 - r2
            r9.setOffPostion(r1, r3)
            r9.setBounds(r0)
            r9.setParent(r6)
            float r0 = r10.getRotation()
            r9.setRotation(r0)
            boolean r0 = r10.getFlipHorizontal()
            r9.setFlipHorizontal(r0)
            boolean r0 = r10.getFlipVertical()
            r9.setFlipVertical(r0)
            if (r6 != 0) goto L_0x018c
            short r0 = r17.getShapeWrapType(r18)
            r9.setWrapType(r0)
            r1 = r10
            com.app.office.common.shape.WPAutoShape r1 = (com.app.office.common.shape.WPAutoShape) r1
            r1.setWrap(r0)
            goto L_0x0193
        L_0x018c:
            short r0 = r20.getWrapType()
            r9.setWrapType(r0)
        L_0x0193:
            java.util.Iterator r8 = r18.elementIterator()
        L_0x0197:
            boolean r0 = r8.hasNext()
            if (r0 == 0) goto L_0x01b5
            java.lang.Object r0 = r8.next()
            r1 = r0
            com.app.office.fc.dom4j.Element r1 = (com.app.office.fc.dom4j.Element) r1
            r0 = r11[r14]
            float r4 = r0 * r21
            r0 = r11[r13]
            float r5 = r0 * r22
            r0 = r17
            r2 = r19
            r3 = r9
            r0.processAutoShapeForPict(r1, r2, r3, r4, r5)
            goto L_0x0197
        L_0x01b5:
            com.app.office.common.shape.IShape[] r0 = r9.getShapes()
            int r1 = r0.length
        L_0x01ba:
            if (r14 >= r1) goto L_0x020e
            r2 = r0[r14]
            boolean r3 = r2 instanceof com.app.office.common.shape.WPAbstractShape
            if (r3 == 0) goto L_0x020b
            boolean r3 = r10 instanceof com.app.office.common.shape.WPAbstractShape
            if (r3 == 0) goto L_0x020b
            com.app.office.common.shape.WPAbstractShape r2 = (com.app.office.common.shape.WPAbstractShape) r2
            r3 = r10
            com.app.office.common.shape.WPAbstractShape r3 = (com.app.office.common.shape.WPAbstractShape) r3
            int r4 = r3.getWrap()
            short r4 = (short) r4
            r2.setWrap(r4)
            byte r4 = r3.getHorPositionType()
            r2.setHorPositionType(r4)
            byte r4 = r3.getHorizontalRelativeTo()
            r2.setHorizontalRelativeTo(r4)
            int r4 = r3.getHorRelativeValue()
            r2.setHorRelativeValue(r4)
            byte r4 = r3.getHorizontalAlignment()
            r2.setHorizontalAlignment(r4)
            byte r4 = r3.getVerPositionType()
            r2.setVerPositionType(r4)
            byte r4 = r3.getVerticalRelativeTo()
            r2.setVerticalRelativeTo(r4)
            int r4 = r3.getVerRelativeValue()
            r2.setVerRelativeValue(r4)
            byte r3 = r3.getVerticalAlignment()
            r2.setVerticalAlignment(r3)
        L_0x020b:
            int r14 = r14 + 1
            goto L_0x01ba
        L_0x020e:
            if (r6 != 0) goto L_0x0217
            r9 = r19
            r7.addShape(r10, r9)
            goto L_0x02ba
        L_0x0217:
            r6.appendShapes(r10)
            goto L_0x02ba
        L_0x021c:
            r10.dispose()
            goto L_0x02ba
        L_0x0221:
            r9 = r19
            java.lang.String r1 = r8.attributeValue((java.lang.String) r5)
            if (r1 == 0) goto L_0x023c
            boolean r4 = r1.startsWith(r4)
            if (r4 != 0) goto L_0x023b
            boolean r3 = r1.startsWith(r3)
            if (r3 != 0) goto L_0x023b
            boolean r1 = r1.startsWith(r2)
            if (r1 == 0) goto L_0x023c
        L_0x023b:
            return
        L_0x023c:
            java.lang.String r1 = "shape"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 == 0) goto L_0x026e
            java.lang.String r0 = "imagedata"
            com.app.office.fc.dom4j.Element r0 = r8.element((java.lang.String) r0)
            if (r0 == 0) goto L_0x0251
            r17.processPicture(r18, r19)
            goto L_0x02ba
        L_0x0251:
            boolean r10 = r17.hasTextbox2007(r18)
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r5 = r22
            r6 = r10
            com.app.office.common.shape.WPAutoShape r0 = r0.processAutoShape(r1, r2, r3, r4, r5, r6)
            if (r0 == 0) goto L_0x02ba
            com.app.office.fc.openxml4j.opc.PackagePart r1 = r7.packagePart
            r7.processTextbox2007(r1, r0, r8)
            goto L_0x02ba
        L_0x026e:
            java.lang.String r1 = "line"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 != 0) goto L_0x029e
            java.lang.String r1 = "polyline"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 != 0) goto L_0x029e
            java.lang.String r1 = "curve"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 != 0) goto L_0x029e
            java.lang.String r1 = "rect"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 != 0) goto L_0x029e
            java.lang.String r1 = "roundrect"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 != 0) goto L_0x029e
            java.lang.String r1 = "oval"
            boolean r0 = r1.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x02ba
        L_0x029e:
            boolean r10 = r17.hasTextbox2007(r18)
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r5 = r22
            r6 = r10
            com.app.office.common.shape.WPAutoShape r0 = r0.processAutoShape(r1, r2, r3, r4, r5, r6)
            if (r0 == 0) goto L_0x02ba
            com.app.office.fc.openxml4j.opc.PackagePart r1 = r7.packagePart
            r7.processTextbox2007(r1, r0, r8)
        L_0x02ba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCXReader.processAutoShapeForPict(com.app.office.fc.dom4j.Element, com.app.office.simpletext.model.ParagraphElement, com.app.office.common.shape.WPGroupShape, float, float):void");
    }

    private short getShapeWrapType(Element element) {
        Element element2 = element.element("wrap");
        if (element2 != null) {
            String attributeValue = element2.attributeValue(DublinCoreProperties.TYPE);
            if ("none".equalsIgnoreCase(attributeValue)) {
                return 2;
            }
            if ("square".equalsIgnoreCase(attributeValue)) {
                return 1;
            }
            if ("tight".equalsIgnoreCase(attributeValue)) {
                return 0;
            }
            if ("topAndBottom".equalsIgnoreCase(attributeValue)) {
                return 5;
            }
            if ("through".equalsIgnoreCase(attributeValue)) {
                return 4;
            }
        }
        String attributeValue2 = element.attributeValue(HtmlTags.STYLE);
        if (attributeValue2 == null) {
            return -1;
        }
        String[] split = attributeValue2.split(";");
        for (int length = split.length - 1; length >= 0; length--) {
            if (split[length].contains("z-index:")) {
                return Integer.parseInt(split[length].replace("z-index:", "")) > 0 ? (short) 3 : 6;
            }
        }
        return -1;
    }

    private short getDrawingWrapType(Element element) {
        if (element == null) {
            return -1;
        }
        if (element.element("wrapNone") != null) {
            return "1".equalsIgnoreCase(element.attributeValue("behindDoc")) ? (short) 6 : 3;
        }
        if (element.element("wrapSquare") != null) {
            return 1;
        }
        if (element.element("wrapTight") != null) {
            return 0;
        }
        if (element.element("wrapThrough") != null) {
            return 4;
        }
        return element.element("wrapTopAndBottom") != null ? (short) 5 : 2;
    }

    /* access modifiers changed from: private */
    public void processPicture(Element element, ParagraphElement paragraphElement) {
        String attributeValue;
        PackagePart packagePart2;
        AbstractShape abstractShape;
        PackagePart packagePart3;
        if (element != null) {
            Element element2 = element.element("imagedata");
            if (element2 == null && (element2 = element.element("rect")) != null) {
                Element element3 = element2;
                element2 = element2.element("fill");
                element = element3;
            }
            if (element2 != null && (attributeValue = element2.attributeValue((String) OSOutcomeConstants.OUTCOME_ID)) != null) {
                if (!this.isProcessHF || (packagePart3 = this.hfPart) == null) {
                    packagePart2 = this.zipPackage.getPart(this.packagePart.getRelationship(attributeValue).getTargetURI());
                } else {
                    packagePart2 = this.zipPackage.getPart(packagePart3.getRelationship(attributeValue).getTargetURI());
                }
                String attributeValue2 = element.attributeValue(HtmlTags.STYLE);
                if (packagePart2 != null && attributeValue2 != null) {
                    String attributeValue3 = element.attributeValue(OSOutcomeConstants.OUTCOME_ID);
                    if (attributeValue3 != null && attributeValue3.indexOf("PictureWatermark") > 0) {
                        this.isProcessWatermark = true;
                    }
                    try {
                        int addPicture = this.control.getSysKit().getPictureManage().addPicture(packagePart2);
                        short shapeWrapType = getShapeWrapType(element);
                        if (this.isProcessWatermark) {
                            abstractShape = new WatermarkShape();
                            String attributeValue4 = element2.attributeValue("blacklevel");
                            if (attributeValue4 != null) {
                                ((WatermarkShape) abstractShape).setBlacklevel(Float.parseFloat(attributeValue4) / 100000.0f);
                            }
                            String attributeValue5 = element2.attributeValue("gain");
                            if (attributeValue5 != null) {
                                ((WatermarkShape) abstractShape).setGain(Float.parseFloat(attributeValue5) / 100000.0f);
                            }
                            ((WatermarkShape) abstractShape).setWatermarkType((byte) 1);
                            ((WatermarkShape) abstractShape).setPictureIndex(addPicture);
                            ((WatermarkShape) abstractShape).setWrap(shapeWrapType);
                        } else {
                            PictureEffectInfo pictureEffectInfor_ImageData = PictureEffectInfoFactory.getPictureEffectInfor_ImageData(element2);
                            PictureShape pictureShape = new PictureShape();
                            pictureShape.setPictureIndex(addPicture);
                            pictureShape.setZoomX(1000);
                            pictureShape.setZoomY(1000);
                            pictureShape.setPictureEffectInfor(pictureEffectInfor_ImageData);
                            abstractShape = new WPPictureShape();
                            ((WPPictureShape) abstractShape).setPictureShape(pictureShape);
                            ((WPPictureShape) abstractShape).setWrap(shapeWrapType);
                        }
                        AbstractShape abstractShape2 = abstractShape;
                        Rectangle processAutoShapeStyle = processAutoShapeStyle(element, abstractShape2, (GroupShape) null, 1000.0f, 1000.0f);
                        if (!this.isProcessWatermark) {
                            PictureShape pictureShape2 = ((WPPictureShape) abstractShape2).getPictureShape();
                            pictureShape2.setBounds(processAutoShapeStyle);
                            pictureShape2.setBackgroundAndFill(processBackgroundAndFill(element));
                            pictureShape2.setLine(processLine(element));
                        }
                        addShape(abstractShape2, paragraphElement);
                        this.isProcessWatermark = false;
                    } catch (Exception e) {
                        this.control.getSysKit().getErrorKit().writerLog(e);
                    }
                }
            }
        }
    }

    private int getColor(String str, boolean z) {
        if (str == null) {
            return z ? -1 : -16777216;
        }
        int indexOf = str.indexOf(" ");
        if (indexOf > 0) {
            str = str.substring(0, indexOf);
        }
        if (str.charAt(0) == '#') {
            if (str.length() > 6) {
                return Color.parseColor(str);
            }
            if (str.length() == 4) {
                StringBuilder sb = new StringBuilder();
                sb.append('#');
                for (int i = 1; i < 4; i++) {
                    sb.append(str.charAt(i));
                    sb.append(str.charAt(i));
                }
                return Color.parseColor(sb.toString());
            }
        }
        if (!str.contains("black") && !str.contains("darken")) {
            if (str.contains("green")) {
                return -16744448;
            }
            if (str.contains("silver")) {
                return -4144960;
            }
            if (str.contains("lime")) {
                return -16711936;
            }
            if (str.contains("gray")) {
                return -8355712;
            }
            if (str.contains("olive")) {
                return -8355840;
            }
            if (str.contains("white")) {
                return -1;
            }
            if (str.contains("yellow")) {
                return InputDeviceCompat.SOURCE_ANY;
            }
            if (str.contains("maroon")) {
                return -8388608;
            }
            if (str.contains("navy")) {
                return -16777088;
            }
            if (str.contains("red")) {
                return SupportMenu.CATEGORY_MASK;
            }
            if (str.contains("blue")) {
                return -16776961;
            }
            if (str.contains("purple")) {
                return -8388480;
            }
            if (str.contains("teal")) {
                return -16744320;
            }
            if (str.contains("fuchsia")) {
                return -65281;
            }
            if (str.contains("aqua")) {
                return -16711681;
            }
            if (z) {
                return -1;
            }
        }
        return -16777216;
    }

    public void processRotation(AutoShape autoShape) {
        float rotation = autoShape.getRotation();
        if (autoShape.getFlipHorizontal()) {
            rotation = -rotation;
        }
        if (autoShape.getFlipVertical()) {
            rotation = -rotation;
        }
        int shapeType = autoShape.getShapeType();
        if ((shapeType == 32 || shapeType == 34 || shapeType == 38) && ((rotation == 45.0f || rotation == 135.0f || rotation == 225.0f) && !autoShape.getFlipHorizontal() && !autoShape.getFlipVertical())) {
            rotation -= 90.0f;
        }
        autoShape.setRotation(rotation);
    }

    private Rectangle processGrpSpRect(GroupShape groupShape, Rectangle rectangle) {
        if (groupShape != null) {
            rectangle.x += groupShape.getOffX();
            rectangle.y += groupShape.getOffY();
        }
        return rectangle;
    }

    private float processPolygonZoom(Element element, AbstractShape abstractShape, GroupShape groupShape, float f, float f2) {
        String attributeValue;
        String attributeValue2;
        float parseFloat;
        float parseFloat2;
        float parseFloat3;
        float parseFloat4;
        float parseFloat5;
        Element element2 = element;
        AbstractShape abstractShape2 = abstractShape;
        if (element2 == null || abstractShape2 == null || element2.attribute(HtmlTags.STYLE) == null || element2.attribute(HtmlTags.STYLE) == null || (attributeValue = element2.attributeValue((String) HtmlTags.STYLE)) == null) {
            return 1.0f;
        }
        String[] split = attributeValue.split(";");
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        for (String split2 : split) {
            String[] split3 = split2.split(":");
            if (!(split3 == null || split3[0] == null || split3[1] == null)) {
                if (HtmlTags.ALIGN_LEFT.equalsIgnoreCase(split3[0])) {
                    int indexOf = split3[1].indexOf("pt");
                    if (indexOf > 0) {
                        parseFloat4 = Float.parseFloat(split3[1].substring(0, indexOf));
                    } else {
                        int indexOf2 = split3[1].indexOf("in");
                        if (indexOf2 > 0) {
                            parseFloat5 = Float.parseFloat(split3[1].substring(0, indexOf2));
                            parseFloat4 = parseFloat5 * 72.0f;
                        } else {
                            parseFloat4 = ((Float.parseFloat(split3[1]) * f) * 72.0f) / 914400.0f;
                        }
                    }
                } else {
                    if (HtmlTags.ALIGN_TOP.equalsIgnoreCase(split3[0])) {
                        int indexOf3 = split3[1].indexOf("pt");
                        if (indexOf3 > 0) {
                            parseFloat3 = Float.parseFloat(split3[1].substring(0, indexOf3));
                        } else {
                            int indexOf4 = split3[1].indexOf("in");
                            if (indexOf4 > 0) {
                                parseFloat2 = Float.parseFloat(split3[1].substring(0, indexOf4));
                                parseFloat3 = parseFloat2 * 72.0f;
                            } else {
                                parseFloat = Float.parseFloat(split3[1]) * f2;
                                parseFloat3 = (parseFloat * 72.0f) / 914400.0f;
                            }
                        }
                    } else if ("margin-left".equalsIgnoreCase(split3[0])) {
                        int indexOf5 = split3[1].indexOf("pt");
                        if (indexOf5 > 0) {
                            parseFloat4 = Float.parseFloat(split3[1].substring(0, indexOf5));
                        } else {
                            int indexOf6 = split3[1].indexOf("in");
                            if (indexOf6 > 0) {
                                parseFloat5 = Float.parseFloat(split3[1].substring(0, indexOf6));
                                parseFloat4 = parseFloat5 * 72.0f;
                            } else {
                                parseFloat4 = (Float.parseFloat(split3[1]) * 72.0f) / 914400.0f;
                            }
                        }
                    } else if ("margin-top".equalsIgnoreCase(split3[0])) {
                        int indexOf7 = split3[1].indexOf("pt");
                        if (indexOf7 > 0) {
                            parseFloat3 = Float.parseFloat(split3[1].substring(0, indexOf7));
                        } else {
                            int indexOf8 = split3[1].indexOf("in");
                            if (indexOf8 > 0) {
                                parseFloat2 = Float.parseFloat(split3[1].substring(0, indexOf8));
                                parseFloat3 = parseFloat2 * 72.0f;
                            } else {
                                parseFloat = Float.parseFloat(split3[1]);
                                parseFloat3 = (parseFloat * 72.0f) / 914400.0f;
                            }
                        }
                    } else if (HtmlTags.WIDTH.equalsIgnoreCase(split3[0])) {
                        int indexOf9 = split3[1].indexOf("pt");
                        if (indexOf9 > 0) {
                            f5 = Float.parseFloat(split3[1].substring(0, indexOf9));
                        } else {
                            int indexOf10 = split3[1].indexOf("in");
                            if (indexOf10 > 0) {
                                f5 = Float.parseFloat(split3[1].substring(0, indexOf10)) * 72.0f;
                            } else {
                                f5 = ((Float.parseFloat(split3[1]) * f) * 72.0f) / 914400.0f;
                            }
                        }
                    } else if (HtmlTags.HEIGHT.equalsIgnoreCase(split3[0])) {
                        int indexOf11 = split3[1].indexOf("pt");
                        if (indexOf11 > 0) {
                            f6 = Float.parseFloat(split3[1].substring(0, indexOf11));
                        } else {
                            int indexOf12 = split3[1].indexOf("in");
                            if (indexOf12 > 0) {
                                f6 = Float.parseFloat(split3[1].substring(0, indexOf12)) * 72.0f;
                            } else {
                                f6 = ((Float.parseFloat(split3[1]) * f2) * 72.0f) / 914400.0f;
                            }
                        }
                    }
                    f4 += parseFloat3;
                }
                f3 += parseFloat4;
            }
        }
        Rectangle rectangle = new Rectangle();
        rectangle.x = (int) (f3 * 1.3333334f);
        rectangle.y = (int) (f4 * 1.3333334f);
        rectangle.width = (int) (f5 * 1.3333334f);
        rectangle.height = (int) (f6 * 1.3333334f);
        if (abstractShape.getType() != 7 && ((WPAutoShape) abstractShape2).getGroupShape() == null) {
            processGrpSpRect(groupShape, rectangle);
        }
        if (!(abstractShape2 instanceof WPAutoShape) || ((WPAutoShape) abstractShape2).getShapeType() != 233 || (attributeValue2 = element2.attributeValue("coordsize")) == null || attributeValue2.length() <= 0) {
            return 1.0f;
        }
        String[] split4 = attributeValue2.split(",");
        return Math.min(((float) Integer.parseInt(split4[0])) / ((float) rectangle.width), ((float) Integer.parseInt(split4[1])) / ((float) rectangle.height));
    }

    private float getValueInPt(String str, float f) {
        if (str.contains("pt")) {
            return Float.parseFloat(str.substring(0, str.indexOf("pt")));
        }
        if (str.contains("in")) {
            return Float.parseFloat(str.substring(0, str.indexOf("in"))) * 72.0f;
        }
        if (str.contains("mm")) {
            return Float.parseFloat(str.substring(0, str.indexOf("mm"))) * 2.835f;
        }
        return ((Float.parseFloat(str) * f) * 72.0f) / 914400.0f;
    }

    private Rectangle processAutoShapeStyle(Element element, AbstractShape abstractShape, GroupShape groupShape, float f, float f2) {
        String attributeValue;
        String attributeValue2;
        String[] strArr;
        float valueInPt;
        float valueInPt2;
        Element element2 = element;
        AbstractShape abstractShape2 = abstractShape;
        GroupShape groupShape2 = groupShape;
        float f3 = f;
        float f4 = f2;
        if (element2 == null || abstractShape2 == null || element2.attribute(HtmlTags.STYLE) == null || element2.attribute(HtmlTags.STYLE) == null || (attributeValue = element2.attributeValue((String) HtmlTags.STYLE)) == null) {
            return null;
        }
        String[] split = attributeValue.split(";");
        float f5 = 0.0f;
        char c = 0;
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        int i = 0;
        while (i < split.length) {
            String[] split2 = split[i].split(":");
            if (!(split2 == null || split2[c] == null || split2[1] == null || "position".equalsIgnoreCase(split2[c]))) {
                if (HtmlTags.ALIGN_LEFT.equalsIgnoreCase(split2[c])) {
                    valueInPt2 = getValueInPt(split2[1], f3);
                } else {
                    if (HtmlTags.ALIGN_TOP.equalsIgnoreCase(split2[c])) {
                        valueInPt = getValueInPt(split2[1], f4);
                    } else if (!HtmlTags.TEXTALIGN.equalsIgnoreCase(split2[0])) {
                        if ("margin-left".equalsIgnoreCase(split2[0])) {
                            valueInPt2 = getValueInPt(split2[1], 1.0f);
                        } else if ("margin-top".equalsIgnoreCase(split2[0])) {
                            valueInPt = getValueInPt(split2[1], 1.0f);
                        } else {
                            if (HtmlTags.WIDTH.equalsIgnoreCase(split2[0])) {
                                strArr = split;
                                f7 = getValueInPt(split2[1], f3);
                            } else if (HtmlTags.HEIGHT.equalsIgnoreCase(split2[0])) {
                                strArr = split;
                                f8 = getValueInPt(split2[1], f4);
                            } else if ("mso-width-percent".equalsIgnoreCase(split2[0])) {
                                if (!this.relativeType.contains(abstractShape2)) {
                                    int[] iArr = new int[4];
                                    iArr[0] = (int) Float.parseFloat(split2[1]);
                                    this.relativeType.add(abstractShape2);
                                    this.relativeValue.put(abstractShape2, iArr);
                                } else {
                                    this.relativeValue.get(abstractShape2)[0] = (int) Float.parseFloat(split2[1]);
                                }
                            } else if ("mso-height-percent".equalsIgnoreCase(split2[0])) {
                                if (!this.relativeType.contains(abstractShape2)) {
                                    int[] iArr2 = new int[4];
                                    iArr2[2] = (int) Float.parseFloat(split2[1]);
                                    this.relativeType.add(abstractShape2);
                                    this.relativeValue.put(abstractShape2, iArr2);
                                } else {
                                    this.relativeValue.get(abstractShape2)[2] = (int) Float.parseFloat(split2[1]);
                                }
                            } else if ("flip".equalsIgnoreCase(split2[0])) {
                                if ("x".equalsIgnoreCase(split2[1])) {
                                    abstractShape2.setFlipHorizontal(true);
                                } else if ("y".equalsIgnoreCase(split2[1])) {
                                    abstractShape2.setFlipVertical(true);
                                }
                            } else if ("rotation".equalsIgnoreCase(split2[0])) {
                                if (split2[1].indexOf("fd") > 0) {
                                    split2[1] = split2[1].substring(0, split2[1].length() - 2);
                                    abstractShape2.setRotation((float) (Integer.parseInt(split2[1]) / 60000));
                                } else {
                                    abstractShape2.setRotation((float) Integer.parseInt(split2[1]));
                                }
                            } else if (!"mso-width-relative".equalsIgnoreCase(split2[0]) && !"mso-height-relative".equalsIgnoreCase(split2[0])) {
                                if (groupShape2 == null && abstractShape.getType() != 7 && "mso-position-horizontal".equalsIgnoreCase(split2[0])) {
                                    ((WPAutoShape) abstractShape2).setHorizontalAlignment(getAlign(split2[1]));
                                } else if (groupShape2 != null || abstractShape.getType() == 7 || !"mso-left-percent".equalsIgnoreCase(split2[0])) {
                                    strArr = split;
                                    if (groupShape2 != null || abstractShape.getType() == 7 || !"mso-position-horizontal-relative".equalsIgnoreCase(split2[0])) {
                                        if (groupShape2 == null && abstractShape.getType() != 7 && "mso-position-vertical".equalsIgnoreCase(split2[0])) {
                                            ((WPAutoShape) abstractShape2).setVerticalAlignment(getAlign(split2[1]));
                                        } else if (groupShape2 == null && abstractShape.getType() != 7 && "mso-top-percent".equalsIgnoreCase(split2[0])) {
                                            WPAutoShape wPAutoShape = (WPAutoShape) abstractShape2;
                                            wPAutoShape.setVerRelativeValue(Integer.parseInt(split2[1]));
                                            wPAutoShape.setVerPositionType((byte) 1);
                                        } else if (groupShape2 == null && abstractShape.getType() != 7 && "mso-position-vertical-relative".equalsIgnoreCase(split2[0])) {
                                            if ("line".equalsIgnoreCase(split2[1])) {
                                                ((WPAutoShape) abstractShape2).setVerticalRelativeTo((byte) 11);
                                            } else if ("text".equalsIgnoreCase(split2[1])) {
                                                ((WPAutoShape) abstractShape2).setVerticalRelativeTo((byte) 10);
                                            } else if ("margin".equalsIgnoreCase(split2[1])) {
                                                ((WPAutoShape) abstractShape2).setVerticalRelativeTo((byte) 1);
                                            } else if (Annotation.PAGE.equalsIgnoreCase(split2[1])) {
                                                ((WPAutoShape) abstractShape2).setVerticalRelativeTo((byte) 2);
                                            } else if ("top-margin-area".equalsIgnoreCase(split2[1])) {
                                                ((WPAutoShape) abstractShape2).setVerticalRelativeTo((byte) 6);
                                            } else if ("bottom-margin-area".equalsIgnoreCase(split2[1])) {
                                                ((WPAutoShape) abstractShape2).setVerticalRelativeTo((byte) 7);
                                            } else if ("inner-margin-area".equalsIgnoreCase(split2[1])) {
                                                ((WPAutoShape) abstractShape2).setVerticalRelativeTo((byte) 8);
                                            } else if ("outer-margin-area".equalsIgnoreCase(split2[1])) {
                                                ((WPAutoShape) abstractShape2).setVerticalRelativeTo((byte) 9);
                                            }
                                        }
                                    } else if ("margin".equalsIgnoreCase(split2[1])) {
                                        ((WPAutoShape) abstractShape2).setHorizontalRelativeTo((byte) 1);
                                    } else if (Annotation.PAGE.equalsIgnoreCase(split2[1])) {
                                        ((WPAutoShape) abstractShape2).setHorizontalRelativeTo((byte) 2);
                                    } else if ("left-margin-area".equalsIgnoreCase(split2[1])) {
                                        ((WPAutoShape) abstractShape2).setHorizontalRelativeTo((byte) 4);
                                    } else if ("right-margin-area".equalsIgnoreCase(split2[1])) {
                                        ((WPAutoShape) abstractShape2).setHorizontalRelativeTo((byte) 5);
                                    } else if ("inner-margin-area".equalsIgnoreCase(split2[1])) {
                                        ((WPAutoShape) abstractShape2).setHorizontalRelativeTo((byte) 8);
                                    } else if ("outer-margin-area".equalsIgnoreCase(split2[1])) {
                                        ((WPAutoShape) abstractShape2).setHorizontalRelativeTo((byte) 9);
                                    } else if ("text".equalsIgnoreCase(split2[1])) {
                                        ((WPAutoShape) abstractShape2).setHorizontalRelativeTo((byte) 0);
                                    } else if ("char".equalsIgnoreCase(split2[1])) {
                                        ((WPAutoShape) abstractShape2).setHorizontalRelativeTo((byte) 3);
                                    }
                                } else {
                                    WPAutoShape wPAutoShape2 = (WPAutoShape) abstractShape2;
                                    wPAutoShape2.setHorRelativeValue(Integer.parseInt(split2[1]));
                                    wPAutoShape2.setHorPositionType((byte) 1);
                                }
                            }
                            i++;
                            Element element3 = element;
                            f3 = f;
                            f4 = f2;
                            split = strArr;
                            c = 0;
                        }
                    }
                    f6 += valueInPt;
                }
                f5 += valueInPt2;
            }
            strArr = split;
            i++;
            Element element32 = element;
            f3 = f;
            f4 = f2;
            split = strArr;
            c = 0;
        }
        Rectangle rectangle = new Rectangle();
        rectangle.x = (int) (f5 * 1.3333334f);
        rectangle.y = (int) (f6 * 1.3333334f);
        rectangle.width = (int) (f7 * 1.3333334f);
        rectangle.height = (int) (f8 * 1.3333334f);
        if (abstractShape.getType() != 7 && ((WPAutoShape) abstractShape2).getGroupShape() == null) {
            processGrpSpRect(groupShape2, rectangle);
        }
        if (abstractShape2 instanceof WPAutoShape) {
            WPAutoShape wPAutoShape3 = (WPAutoShape) abstractShape2;
            if (wPAutoShape3.getShapeType() == 233 && (attributeValue2 = element.attributeValue("coordsize")) != null && attributeValue2.length() > 0) {
                String[] split3 = attributeValue2.split(",");
                Matrix matrix = new Matrix();
                matrix.postScale(((float) rectangle.width) / ((float) Integer.parseInt(split3[0])), ((float) rectangle.height) / ((float) Integer.parseInt(split3[1])));
                for (ExtendPath path : wPAutoShape3.getPaths()) {
                    path.getPath().transform(matrix);
                }
            }
        }
        abstractShape2.setBounds(rectangle);
        return rectangle;
    }

    private byte getFillType(String str) {
        if ("gradient".equalsIgnoreCase(str)) {
            return 7;
        }
        if ("gradientRadial".equalsIgnoreCase(str)) {
            return 4;
        }
        if ("pattern".equalsIgnoreCase(str)) {
            return 1;
        }
        if ("tile".equalsIgnoreCase(str)) {
            return 2;
        }
        return TypedValues.AttributesType.S_FRAME.equalsIgnoreCase(str) ? (byte) 3 : 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0075 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int getRadialGradientPositionType(com.app.office.fc.dom4j.Element r7) {
        /*
            r6 = this;
            java.lang.String r0 = "focusposition"
            java.lang.String r7 = r7.attributeValue((java.lang.String) r0)
            r0 = 2
            r1 = 0
            r2 = 1
            if (r7 == 0) goto L_0x0075
            int r3 = r7.length()
            if (r3 <= 0) goto L_0x0075
            java.lang.String r3 = ","
            java.lang.String[] r7 = r7.split(r3)
            if (r7 == 0) goto L_0x0075
            int r3 = r7.length
            java.lang.String r4 = "1"
            if (r3 != r0) goto L_0x0068
            r3 = r7[r1]
            java.lang.String r5 = ".5"
            boolean r3 = r5.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0032
            r3 = r7[r2]
            boolean r3 = r5.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0032
            r0 = 4
            goto L_0x0076
        L_0x0032:
            r3 = r7[r1]
            boolean r3 = r4.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0044
            r3 = r7[r2]
            boolean r3 = r4.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0044
            r0 = 3
            goto L_0x0076
        L_0x0044:
            r3 = r7[r1]
            java.lang.String r5 = ""
            boolean r3 = r5.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0057
            r3 = r7[r2]
            boolean r3 = r4.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0057
            goto L_0x0076
        L_0x0057:
            r0 = r7[r1]
            boolean r0 = r4.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x0075
            r7 = r7[r2]
            boolean r7 = r5.equalsIgnoreCase(r7)
            if (r7 == 0) goto L_0x0075
            goto L_0x0073
        L_0x0068:
            int r0 = r7.length
            if (r0 != r2) goto L_0x0075
            r7 = r7[r1]
            boolean r7 = r4.equalsIgnoreCase(r7)
            if (r7 == 0) goto L_0x0075
        L_0x0073:
            r0 = 1
            goto L_0x0076
        L_0x0075:
            r0 = 0
        L_0x0076:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCXReader.getRadialGradientPositionType(com.app.office.fc.dom4j.Element):int");
    }

    private int getAutoShapeType(Element element) {
        int i;
        String name = element.getName();
        if (name.equals("rect")) {
            i = 1;
        } else if (name.equals("roundrect")) {
            i = 2;
        } else if (name.equals("oval")) {
            i = 3;
        } else if (name.equals("curve")) {
            i = ShapeTypes.Curve;
        } else if (name.equals("polyline")) {
            i = ShapeTypes.DirectPolygon;
        } else {
            i = name.equals("line") ? 247 : 0;
        }
        if (element.attribute(DublinCoreProperties.TYPE) != null) {
            String attributeValue = element.attributeValue(DublinCoreProperties.TYPE);
            if (attributeValue == null || attributeValue.length() <= 9) {
                return i;
            }
            return Integer.parseInt(attributeValue.substring(9));
        } else if (element.attribute("path") != null) {
            return 233;
        } else {
            return i;
        }
    }

    private float getValue(String str) {
        float f;
        int indexOf = str.indexOf("pt");
        if (indexOf > 0) {
            f = Float.parseFloat(str.substring(0, indexOf));
        } else {
            int indexOf2 = str.indexOf("in");
            if (indexOf2 > 0) {
                f = Float.parseFloat(str.substring(0, indexOf2)) * 72.0f;
            } else {
                f = (Float.parseFloat(str) * 72.0f) / 914400.0f;
            }
        }
        return f * 1.3333334f;
    }

    private PointF[] processPoints(String str) {
        ArrayList arrayList = new ArrayList();
        if (str != null) {
            String[] split = str.split(",");
            int length = split.length;
            for (int i = 0; i < length - 1; i += 2) {
                arrayList.add(new PointF(getValue(split[i]), getValue(split[i + 1])));
            }
        }
        return (PointF[]) arrayList.toArray(new PointF[arrayList.size()]);
    }

    private void processArrow(WPAutoShape wPAutoShape, Element element) {
        Element element2 = element.element("stroke");
        if (element2 != null) {
            byte arrowType = getArrowType(element2.attributeValue("startarrow"));
            if (arrowType > 0) {
                wPAutoShape.createStartArrow(arrowType, getArrowWidth(element2.attributeValue("startarrowwidth")), getArrowLength(element2.attributeValue("startarrowlength")));
            }
            byte arrowType2 = getArrowType(element2.attributeValue("endarrow"));
            if (arrowType2 > 0) {
                wPAutoShape.createEndArrow(arrowType2, getArrowWidth(element2.attributeValue("endarrowwidth")), getArrowLength(element2.attributeValue("endarrowlength")));
            }
        }
    }

    private ExtendPath getArrowExtendPath(Path path, BackgroundAndFill backgroundAndFill, Line line, boolean z, byte b) {
        ExtendPath extendPath = new ExtendPath();
        extendPath.setArrowFlag(true);
        extendPath.setPath(path);
        if (backgroundAndFill != null || z) {
            if (z) {
                if (b == 5) {
                    extendPath.setLine(line);
                } else if (line != null) {
                    extendPath.setBackgroundAndFill(line.getBackgroundAndFill());
                } else if (backgroundAndFill != null) {
                    extendPath.setBackgroundAndFill(backgroundAndFill);
                }
            } else if (backgroundAndFill != null) {
                extendPath.setBackgroundAndFill(backgroundAndFill);
            }
        }
        return extendPath;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0015, code lost:
        r2 = r7.attributeValue("adj");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.common.shape.WPAutoShape processAutoShape(com.app.office.fc.dom4j.Element r29, com.app.office.simpletext.model.ParagraphElement r30, com.app.office.common.shape.WPGroupShape r31, float r32, float r33, boolean r34) {
        /*
            r28 = this;
            r6 = r28
            r7 = r29
            r8 = r31
            r0 = 0
            if (r7 == 0) goto L_0x04c1
            int r1 = r28.getAutoShapeType(r29)
            java.lang.String r2 = "adj"
            com.app.office.fc.dom4j.Attribute r3 = r7.attribute((java.lang.String) r2)
            if (r3 == 0) goto L_0x0028
            java.lang.String r2 = r7.attributeValue((java.lang.String) r2)
            if (r2 == 0) goto L_0x0028
            int r3 = r2.length()
            if (r3 <= 0) goto L_0x0028
            java.lang.String r3 = ","
            java.lang.String[] r2 = r2.split(r3)
            goto L_0x0029
        L_0x0028:
            r2 = r0
        L_0x0029:
            r9 = 0
            if (r2 == 0) goto L_0x0054
            int r3 = r2.length
            if (r3 <= 0) goto L_0x0054
            int r3 = r2.length
            java.lang.Float[] r3 = new java.lang.Float[r3]
            r4 = 0
        L_0x0033:
            int r5 = r2.length
            if (r4 >= r5) goto L_0x0055
            r5 = r2[r4]
            if (r5 == 0) goto L_0x004f
            int r10 = r5.length()
            if (r10 <= 0) goto L_0x004f
            float r5 = java.lang.Float.parseFloat(r5)
            r10 = 1185464320(0x46a8c000, float:21600.0)
            float r5 = r5 / r10
            java.lang.Float r5 = java.lang.Float.valueOf(r5)
            r3[r4] = r5
            goto L_0x0051
        L_0x004f:
            r3[r4] = r0
        L_0x0051:
            int r4 = r4 + 1
            goto L_0x0033
        L_0x0054:
            r3 = r0
        L_0x0055:
            com.app.office.common.bg.BackgroundAndFill r10 = r28.processBackgroundAndFill(r29)
            com.app.office.common.borders.Line r11 = r28.processLine(r29)
            r2 = 32
            r4 = 33
            r12 = 1
            if (r1 == r2) goto L_0x044f
            if (r1 == r4) goto L_0x044f
            r2 = 34
            if (r1 == r2) goto L_0x044f
            r2 = 38
            if (r1 != r2) goto L_0x0070
            goto L_0x044f
        L_0x0070:
            r2 = 233(0xe9, float:3.27E-43)
            if (r1 != r2) goto L_0x0114
            com.app.office.common.shape.WPAutoShape r13 = new com.app.office.common.shape.WPAutoShape
            r13.<init>()
            r13.setShapeType(r2)
            r6.processArrow(r13, r7)
            java.lang.String r0 = "path"
            java.lang.String r14 = r7.attributeValue((java.lang.String) r0)
            r0 = r28
            r1 = r29
            r2 = r13
            r3 = r31
            r4 = r32
            r5 = r33
            float r0 = r0.processPolygonZoom(r1, r2, r3, r4, r5)
            if (r11 == 0) goto L_0x009b
            int r1 = r11.getLineWidth()
            goto L_0x009c
        L_0x009b:
            r1 = 1
        L_0x009c:
            float r1 = (float) r1
            float r1 = r1 * r0
            int r0 = java.lang.Math.round(r1)
            com.app.office.fc.doc.VMLPathParser r1 = com.app.office.fc.doc.VMLPathParser.instance()
            com.app.office.fc.doc.PathWithArrow r14 = r1.createPath(r13, r14, r0)
            if (r14 == 0) goto L_0x010d
            android.graphics.Path[] r0 = r14.getPolygonPath()
            if (r0 == 0) goto L_0x00d1
            r1 = 0
        L_0x00b4:
            int r2 = r0.length
            if (r1 >= r2) goto L_0x00d1
            com.app.office.common.autoshape.ExtendPath r2 = new com.app.office.common.autoshape.ExtendPath
            r2.<init>()
            r3 = r0[r1]
            r2.setPath(r3)
            if (r11 == 0) goto L_0x00c6
            r2.setLine((com.app.office.common.borders.Line) r11)
        L_0x00c6:
            if (r10 == 0) goto L_0x00cb
            r2.setBackgroundAndFill(r10)
        L_0x00cb:
            r13.appendPath(r2)
            int r1 = r1 + 1
            goto L_0x00b4
        L_0x00d1:
            android.graphics.Path r0 = r14.getStartArrow()
            if (r0 == 0) goto L_0x00ef
            android.graphics.Path r1 = r14.getStartArrow()
            com.app.office.common.shape.Arrow r0 = r13.getStartArrow()
            byte r5 = r0.getType()
            r4 = 1
            r0 = r28
            r2 = r10
            r3 = r11
            com.app.office.common.autoshape.ExtendPath r0 = r0.getArrowExtendPath(r1, r2, r3, r4, r5)
            r13.appendPath(r0)
        L_0x00ef:
            android.graphics.Path r0 = r14.getEndArrow()
            if (r0 == 0) goto L_0x010d
            android.graphics.Path r1 = r14.getEndArrow()
            com.app.office.common.shape.Arrow r0 = r13.getEndArrow()
            byte r5 = r0.getType()
            r4 = 1
            r0 = r28
            r2 = r10
            r3 = r11
            com.app.office.common.autoshape.ExtendPath r0 = r0.getArrowExtendPath(r1, r2, r3, r4, r5)
            r13.appendPath(r0)
        L_0x010d:
            r27 = r7
            r7 = r6
            r6 = r27
            goto L_0x047e
        L_0x0114:
            r2 = 247(0xf7, float:3.46E-43)
            r4 = 249(0xf9, float:3.49E-43)
            r5 = 248(0xf8, float:3.48E-43)
            if (r1 == r2) goto L_0x0157
            if (r1 == r5) goto L_0x0157
            if (r1 != r4) goto L_0x0121
            goto L_0x0157
        L_0x0121:
            java.lang.String r0 = "id"
            java.lang.String r0 = r7.attributeValue((java.lang.String) r0)
            if (r0 == 0) goto L_0x0133
            java.lang.String r2 = "WaterMarkObject"
            int r0 = r0.indexOf(r2)
            if (r0 <= 0) goto L_0x0133
            r6.isProcessWatermark = r12
        L_0x0133:
            boolean r0 = r6.isProcessWatermark
            if (r0 == 0) goto L_0x013d
            com.app.office.common.shape.WatermarkShape r0 = new com.app.office.common.shape.WatermarkShape
            r0.<init>()
            goto L_0x0142
        L_0x013d:
            com.app.office.common.shape.WPAutoShape r0 = new com.app.office.common.shape.WPAutoShape
            r0.<init>()
        L_0x0142:
            r13 = r0
            r13.setShapeType(r1)
            r6.processArrow(r13, r7)
            if (r10 == 0) goto L_0x014e
            r13.setBackgroundAndFill(r10)
        L_0x014e:
            if (r11 == 0) goto L_0x0153
            r13.setLine((com.app.office.common.borders.Line) r11)
        L_0x0153:
            r13.setAdjustData(r3)
            goto L_0x010d
        L_0x0157:
            com.app.office.common.shape.WPAutoShape r13 = new com.app.office.common.shape.WPAutoShape
            r13.<init>()
            r13.setShapeType(r1)
            r6.processArrow(r13, r7)
            android.graphics.Path r2 = new android.graphics.Path
            r2.<init>()
            if (r11 == 0) goto L_0x016e
            int r3 = r11.getLineWidth()
            goto L_0x016f
        L_0x016e:
            r3 = 1
        L_0x016f:
            r14 = 20
            java.lang.String r15 = "to"
            java.lang.String r0 = "from"
            if (r1 != r14) goto L_0x0225
            if (r11 == 0) goto L_0x017d
            com.app.office.common.bg.BackgroundAndFill r10 = r11.getBackgroundAndFill()
        L_0x017d:
            java.lang.String r0 = r7.attributeValue((java.lang.String) r0)
            android.graphics.PointF[] r0 = r6.processPoints(r0)
            r0 = r0[r9]
            java.lang.String r1 = r7.attributeValue((java.lang.String) r15)
            android.graphics.PointF[] r1 = r6.processPoints(r1)
            r1 = r1[r9]
            boolean r4 = r13.getStartArrowhead()
            if (r4 == 0) goto L_0x01b6
            float r14 = r1.x
            float r15 = r1.y
            float r4 = r0.x
            float r5 = r0.y
            com.app.office.common.shape.Arrow r18 = r13.getStartArrow()
            r16 = r4
            r17 = r5
            r19 = r3
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r4 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getDirectLineArrowPath(r14, r15, r16, r17, r18, r19)
            android.graphics.Path r5 = r4.getArrowPath()
            android.graphics.PointF r4 = r4.getArrowTailCenter()
            goto L_0x01b8
        L_0x01b6:
            r4 = 0
            r5 = 0
        L_0x01b8:
            boolean r14 = r13.getEndArrowhead()
            if (r14 == 0) goto L_0x01dd
            float r14 = r0.x
            float r15 = r0.y
            float r12 = r1.x
            float r9 = r1.y
            com.app.office.common.shape.Arrow r18 = r13.getEndArrow()
            r16 = r12
            r17 = r9
            r19 = r3
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r3 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getDirectLineArrowPath(r14, r15, r16, r17, r18, r19)
            android.graphics.Path r9 = r3.getArrowPath()
            android.graphics.PointF r3 = r3.getArrowTailCenter()
            goto L_0x01df
        L_0x01dd:
            r3 = 0
            r9 = 0
        L_0x01df:
            if (r4 == 0) goto L_0x01f5
            float r12 = r0.x
            float r0 = r0.y
            float r14 = r4.x
            float r4 = r4.y
            com.app.office.common.shape.Arrow r15 = r13.getStartArrow()
            byte r15 = r15.getType()
            android.graphics.PointF r0 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r12, r0, r14, r4, r15)
        L_0x01f5:
            if (r3 == 0) goto L_0x020b
            float r4 = r1.x
            float r1 = r1.y
            float r12 = r3.x
            float r3 = r3.y
            com.app.office.common.shape.Arrow r14 = r13.getEndArrow()
            byte r14 = r14.getType()
            android.graphics.PointF r1 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r4, r1, r12, r3, r14)
        L_0x020b:
            float r3 = r0.x
            float r0 = r0.y
            r2.moveTo(r3, r0)
            float r0 = r1.x
            float r1 = r1.y
            r2.lineTo(r0, r1)
            r1 = r5
            r24 = r9
            r26 = r11
            r27 = r7
            r7 = r6
            r6 = r27
            goto L_0x0409
        L_0x0225:
            if (r1 != r5) goto L_0x0327
            java.lang.String r0 = r7.attributeValue((java.lang.String) r0)
            android.graphics.PointF[] r0 = r6.processPoints(r0)
            r1 = 0
            r0 = r0[r1]
            java.lang.String r4 = "control1"
            java.lang.String r4 = r7.attributeValue((java.lang.String) r4)
            android.graphics.PointF[] r4 = r6.processPoints(r4)
            r4 = r4[r1]
            java.lang.String r5 = "control2"
            java.lang.String r5 = r7.attributeValue((java.lang.String) r5)
            android.graphics.PointF[] r5 = r6.processPoints(r5)
            r5 = r5[r1]
            java.lang.String r9 = r7.attributeValue((java.lang.String) r15)
            android.graphics.PointF[] r9 = r6.processPoints(r9)
            r9 = r9[r1]
            boolean r1 = r13.getStartArrowhead()
            if (r1 == 0) goto L_0x028d
            float r14 = r9.x
            float r15 = r9.y
            float r1 = r5.x
            float r12 = r5.y
            r25 = r10
            float r10 = r4.x
            float r8 = r4.y
            r26 = r11
            float r11 = r0.x
            float r6 = r0.y
            com.app.office.common.shape.Arrow r22 = r13.getStartArrow()
            r16 = r1
            r17 = r12
            r18 = r10
            r19 = r8
            r20 = r11
            r21 = r6
            r23 = r3
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r1 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getCubicBezArrowPath(r14, r15, r16, r17, r18, r19, r20, r21, r22, r23)
            android.graphics.Path r6 = r1.getArrowPath()
            android.graphics.PointF r1 = r1.getArrowTailCenter()
            goto L_0x0293
        L_0x028d:
            r25 = r10
            r26 = r11
            r1 = 0
            r6 = 0
        L_0x0293:
            boolean r8 = r13.getEndArrowhead()
            if (r8 == 0) goto L_0x02ca
            float r14 = r0.x
            float r15 = r0.y
            float r8 = r4.x
            float r10 = r4.y
            float r11 = r5.x
            float r12 = r5.y
            r34 = r6
            float r6 = r9.x
            float r7 = r9.y
            com.app.office.common.shape.Arrow r22 = r13.getEndArrow()
            r16 = r8
            r17 = r10
            r18 = r11
            r19 = r12
            r20 = r6
            r21 = r7
            r23 = r3
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r3 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getCubicBezArrowPath(r14, r15, r16, r17, r18, r19, r20, r21, r22, r23)
            android.graphics.Path r6 = r3.getArrowPath()
            android.graphics.PointF r3 = r3.getArrowTailCenter()
            goto L_0x02ce
        L_0x02ca:
            r34 = r6
            r3 = 0
            r6 = 0
        L_0x02ce:
            if (r1 == 0) goto L_0x02e4
            float r7 = r0.x
            float r0 = r0.y
            float r8 = r1.x
            float r1 = r1.y
            com.app.office.common.shape.Arrow r10 = r13.getStartArrow()
            byte r10 = r10.getType()
            android.graphics.PointF r0 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r7, r0, r8, r1, r10)
        L_0x02e4:
            if (r3 == 0) goto L_0x02fa
            float r1 = r9.x
            float r7 = r9.y
            float r8 = r3.x
            float r3 = r3.y
            com.app.office.common.shape.Arrow r9 = r13.getEndArrow()
            byte r9 = r9.getType()
            android.graphics.PointF r9 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r1, r7, r8, r3, r9)
        L_0x02fa:
            float r1 = r0.x
            float r0 = r0.y
            r2.moveTo(r1, r0)
            float r15 = r4.x
            float r0 = r4.y
            float r1 = r5.x
            float r3 = r5.y
            float r4 = r9.x
            float r5 = r9.y
            r14 = r2
            r16 = r0
            r17 = r1
            r18 = r3
            r19 = r4
            r20 = r5
            r14.cubicTo(r15, r16, r17, r18, r19, r20)
            r7 = r28
            r1 = r34
            r24 = r6
            r10 = r25
            r6 = r29
            goto L_0x0409
        L_0x0327:
            r25 = r10
            r26 = r11
            if (r1 != r4) goto L_0x0400
            java.lang.String r0 = "points"
            r6 = r29
            java.lang.String r0 = r6.attributeValue((java.lang.String) r0)
            r7 = r28
            android.graphics.PointF[] r0 = r7.processPoints(r0)
            int r1 = r0.length
            boolean r4 = r13.getStartArrowhead()
            if (r4 == 0) goto L_0x036b
            r4 = 1
            r5 = r0[r4]
            float r14 = r5.x
            r5 = r0[r4]
            float r15 = r5.y
            r4 = 0
            r5 = r0[r4]
            float r5 = r5.x
            r8 = r0[r4]
            float r4 = r8.y
            com.app.office.common.shape.Arrow r18 = r13.getStartArrow()
            r16 = r5
            r17 = r4
            r19 = r3
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r4 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getDirectLineArrowPath(r14, r15, r16, r17, r18, r19)
            android.graphics.Path r5 = r4.getArrowPath()
            android.graphics.PointF r4 = r4.getArrowTailCenter()
            goto L_0x036d
        L_0x036b:
            r4 = 0
            r5 = 0
        L_0x036d:
            boolean r8 = r13.getEndArrowhead()
            if (r8 == 0) goto L_0x03a0
            int r8 = r1 + -2
            r9 = r0[r8]
            float r14 = r9.x
            r8 = r0[r8]
            float r15 = r8.y
            int r8 = r1 + -1
            r9 = r0[r8]
            float r9 = r9.x
            r8 = r0[r8]
            float r8 = r8.y
            com.app.office.common.shape.Arrow r18 = r13.getEndArrow()
            r16 = r9
            r17 = r8
            r19 = r3
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r3 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getDirectLineArrowPath(r14, r15, r16, r17, r18, r19)
            android.graphics.Path r8 = r3.getArrowPath()
            android.graphics.PointF r3 = r3.getArrowTailCenter()
            r24 = r8
            goto L_0x03a3
        L_0x03a0:
            r3 = 0
            r24 = 0
        L_0x03a3:
            if (r4 == 0) goto L_0x03c0
            r8 = 0
            r9 = r0[r8]
            float r9 = r9.x
            r10 = r0[r8]
            float r10 = r10.y
            float r11 = r4.x
            float r4 = r4.y
            com.app.office.common.shape.Arrow r12 = r13.getStartArrow()
            byte r12 = r12.getType()
            android.graphics.PointF r4 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r9, r10, r11, r4, r12)
            r0[r8] = r4
        L_0x03c0:
            if (r3 == 0) goto L_0x03de
            r4 = 1
            int r1 = r1 - r4
            r4 = r0[r1]
            float r4 = r4.x
            r8 = r0[r1]
            float r8 = r8.y
            float r9 = r3.x
            float r3 = r3.y
            com.app.office.common.shape.Arrow r10 = r13.getEndArrow()
            byte r10 = r10.getType()
            android.graphics.PointF r3 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r4, r8, r9, r3, r10)
            r0[r1] = r3
        L_0x03de:
            r1 = 0
            r3 = r0[r1]
            float r3 = r3.x
            r4 = r0[r1]
            float r1 = r4.y
            r2.moveTo(r3, r1)
            r1 = 1
        L_0x03eb:
            int r3 = r0.length
            if (r1 >= r3) goto L_0x03fc
            r3 = r0[r1]
            float r3 = r3.x
            r4 = r0[r1]
            float r4 = r4.y
            r2.lineTo(r3, r4)
            int r1 = r1 + 1
            goto L_0x03eb
        L_0x03fc:
            r1 = r5
            r10 = r25
            goto L_0x0409
        L_0x0400:
            r7 = r28
            r6 = r29
            r10 = r25
            r1 = 0
            r24 = 0
        L_0x0409:
            com.app.office.common.autoshape.ExtendPath r0 = new com.app.office.common.autoshape.ExtendPath
            r0.<init>()
            r0.setPath(r2)
            r8 = r26
            if (r26 == 0) goto L_0x0418
            r0.setLine((com.app.office.common.borders.Line) r8)
        L_0x0418:
            if (r10 == 0) goto L_0x041d
            r0.setBackgroundAndFill(r10)
        L_0x041d:
            r13.appendPath(r0)
            if (r1 == 0) goto L_0x0436
            com.app.office.common.shape.Arrow r0 = r13.getStartArrow()
            byte r5 = r0.getType()
            r4 = 1
            r0 = r28
            r2 = r10
            r3 = r8
            com.app.office.common.autoshape.ExtendPath r0 = r0.getArrowExtendPath(r1, r2, r3, r4, r5)
            r13.appendPath(r0)
        L_0x0436:
            if (r24 == 0) goto L_0x047e
            com.app.office.common.shape.Arrow r0 = r13.getEndArrow()
            byte r5 = r0.getType()
            r4 = 1
            r0 = r28
            r1 = r24
            r2 = r10
            r3 = r8
            com.app.office.common.autoshape.ExtendPath r0 = r0.getArrowExtendPath(r1, r2, r3, r4, r5)
            r13.appendPath(r0)
            goto L_0x047e
        L_0x044f:
            r8 = r11
            r27 = r7
            r7 = r6
            r6 = r27
            com.app.office.common.shape.WPAutoShape r13 = new com.app.office.common.shape.WPAutoShape
            r13.<init>()
            r13.setShapeType(r1)
            r13.setLine((com.app.office.common.borders.Line) r8)
            r7.processArrow(r13, r6)
            int r0 = r13.getShapeType()
            if (r0 != r4) goto L_0x047b
            if (r3 != 0) goto L_0x047b
            r0 = 1
            java.lang.Float[] r1 = new java.lang.Float[r0]
            r0 = 1065353216(0x3f800000, float:1.0)
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            r2 = 0
            r1[r2] = r0
            r13.setAdjustData(r1)
            goto L_0x047e
        L_0x047b:
            r13.setAdjustData(r3)
        L_0x047e:
            if (r13 == 0) goto L_0x04c0
            r0 = 1
            r13.setAuotShape07(r0)
            r8 = r31
            if (r8 != 0) goto L_0x0490
            short r0 = r28.getShapeWrapType(r29)
            r13.setWrap(r0)
            goto L_0x0497
        L_0x0490:
            short r0 = r31.getWrapType()
            r13.setWrap(r0)
        L_0x0497:
            r0 = r28
            r1 = r29
            r2 = r13
            r3 = r31
            r4 = r32
            r5 = r33
            r0.processAutoShapeStyle(r1, r2, r3, r4, r5)
            r7.processRotation(r13)
            boolean r0 = r7.isProcessWatermark
            if (r0 == 0) goto L_0x04b5
            r0 = r13
            com.app.office.common.shape.WatermarkShape r0 = (com.app.office.common.shape.WatermarkShape) r0
            r7.processWatermark(r0, r6)
            r0 = 0
            r7.isProcessWatermark = r0
        L_0x04b5:
            if (r8 != 0) goto L_0x04bd
            r0 = r30
            r7.addShape(r13, r0)
            goto L_0x04c0
        L_0x04bd:
            r8.appendShapes(r13)
        L_0x04c0:
            return r13
        L_0x04c1:
            r7 = r6
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCXReader.processAutoShape(com.app.office.fc.dom4j.Element, com.app.office.simpletext.model.ParagraphElement, com.app.office.common.shape.WPGroupShape, float, float, boolean):com.app.office.common.shape.WPAutoShape");
    }

    private void processWatermark(WatermarkShape watermarkShape, Element element) {
        Element element2 = element.element("textpath");
        if (element2 != null) {
            watermarkShape.setWatermarkType((byte) 0);
            String attributeValue = element.attributeValue("fillcolor");
            if (attributeValue != null && attributeValue.length() > 0) {
                watermarkShape.setFontColor(getColor(attributeValue, false));
            }
            Element element3 = element.element("fill");
            if (element3 != null) {
                watermarkShape.setOpacity(Float.parseFloat(element3.attributeValue("opacity")));
            }
            watermarkShape.setWatermartString(element2.attributeValue(TypedValues.Custom.S_STRING));
            for (String split : element2.attributeValue(HtmlTags.STYLE).split(";")) {
                String[] split2 = split.split(":");
                if (HtmlTags.FONTSIZE.equalsIgnoreCase(split2[0])) {
                    int parseInt = Integer.parseInt(split2[1].replace("pt", ""));
                    if (parseInt == 1) {
                        watermarkShape.setAutoFontSize(true);
                    } else {
                        watermarkShape.setFontSize(parseInt);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00cf  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.common.borders.Line processLine(com.app.office.fc.dom4j.Element r10) {
        /*
            r9 = this;
            java.lang.String r0 = "stroked"
            java.lang.String r1 = r10.attributeValue((java.lang.String) r0)
            java.lang.String r2 = "f"
            boolean r3 = r2.equalsIgnoreCase(r1)
            r4 = 0
            if (r3 != 0) goto L_0x00f4
            java.lang.String r3 = "false"
            boolean r1 = r3.equalsIgnoreCase(r1)
            if (r1 == 0) goto L_0x0019
            goto L_0x00f4
        L_0x0019:
            java.lang.String r1 = "type"
            java.lang.String r1 = r10.attributeValue((java.lang.String) r1)
            com.app.office.fc.dom4j.Element r5 = r10.getParent()
            java.lang.String r6 = "shapetype"
            java.util.List r5 = r5.elements((java.lang.String) r6)
            if (r1 == 0) goto L_0x005b
            if (r5 == 0) goto L_0x005b
            java.util.Iterator r5 = r5.iterator()
        L_0x0031:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x005b
            java.lang.Object r6 = r5.next()
            com.app.office.fc.dom4j.Element r6 = (com.app.office.fc.dom4j.Element) r6
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "#"
            r7.append(r8)
            java.lang.String r8 = "id"
            java.lang.String r8 = r6.attributeValue((java.lang.String) r8)
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            boolean r7 = r1.equals(r7)
            if (r7 == 0) goto L_0x0031
            goto L_0x005c
        L_0x005b:
            r6 = r4
        L_0x005c:
            if (r6 == 0) goto L_0x006f
            java.lang.String r0 = r6.attributeValue((java.lang.String) r0)
            boolean r1 = r2.equalsIgnoreCase(r0)
            if (r1 != 0) goto L_0x006e
            boolean r0 = r3.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x006f
        L_0x006e:
            return r4
        L_0x006f:
            r0 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            java.lang.String r1 = "strokecolor"
            java.lang.String r1 = r10.attributeValue((java.lang.String) r1)
            r2 = 0
            if (r1 == 0) goto L_0x0084
            int r3 = r1.length()
            if (r3 <= 0) goto L_0x0084
            int r0 = r9.getColor(r1, r2)
        L_0x0084:
            com.app.office.common.bg.BackgroundAndFill r1 = new com.app.office.common.bg.BackgroundAndFill
            r1.<init>()
            r1.setForegroundColor(r0)
            java.lang.String r0 = "strokeweight"
            java.lang.String r3 = r10.attributeValue((java.lang.String) r0)
            java.lang.String r0 = r10.attributeValue((java.lang.String) r0)
            r4 = 1
            if (r0 == 0) goto L_0x00cf
            java.lang.String r0 = "pt"
            int r5 = r3.indexOf(r0)
            java.lang.String r6 = ""
            if (r5 < 0) goto L_0x00a8
            java.lang.String r3 = r3.replace(r0, r6)
            goto L_0x00c1
        L_0x00a8:
            java.lang.String r0 = "mm"
            int r5 = r3.indexOf(r0)
            if (r5 < 0) goto L_0x00b5
            java.lang.String r3 = r3.replace(r0, r6)
            goto L_0x00c1
        L_0x00b5:
            java.lang.String r0 = "cm"
            int r5 = r3.indexOf(r0)
            if (r5 < 0) goto L_0x00c1
            java.lang.String r3 = r3.replace(r0, r6)
        L_0x00c1:
            float r0 = java.lang.Float.parseFloat(r3)
            r3 = 1068149419(0x3faaaaab, float:1.3333334)
            float r0 = r0 * r3
            int r0 = java.lang.Math.round(r0)
            goto L_0x00d0
        L_0x00cf:
            r0 = 1
        L_0x00d0:
            java.lang.String r3 = "stroke"
            com.app.office.fc.dom4j.Element r5 = r10.element((java.lang.String) r3)
            if (r5 == 0) goto L_0x00e5
            com.app.office.fc.dom4j.Element r10 = r10.element((java.lang.String) r3)
            java.lang.String r3 = "dashstyle"
            java.lang.String r10 = r10.attributeValue((java.lang.String) r3)
            if (r10 == 0) goto L_0x00e5
            r2 = 1
        L_0x00e5:
            com.app.office.common.borders.Line r10 = new com.app.office.common.borders.Line
            r10.<init>()
            r10.setBackgroundAndFill(r1)
            r10.setLineWidth(r0)
            r10.setDash(r2)
            return r10
        L_0x00f4:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCXReader.processLine(com.app.office.fc.dom4j.Element):com.app.office.common.borders.Line");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        r0 = r14.attributeValue("filled");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.common.bg.BackgroundAndFill processBackgroundAndFill(com.app.office.fc.dom4j.Element r14) {
        /*
            r13 = this;
            java.lang.String r0 = "filled"
            com.app.office.fc.dom4j.Attribute r1 = r14.attribute((java.lang.String) r0)
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L_0x0028
            java.lang.String r0 = r14.attributeValue((java.lang.String) r0)
            if (r0 == 0) goto L_0x0028
            int r1 = r0.length()
            if (r1 <= 0) goto L_0x0028
            java.lang.String r1 = "f"
            boolean r1 = r0.equals(r1)
            if (r1 != 0) goto L_0x0026
            java.lang.String r1 = "false"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0028
        L_0x0026:
            r0 = 0
            goto L_0x0029
        L_0x0028:
            r0 = 1
        L_0x0029:
            r1 = 0
            if (r0 == 0) goto L_0x017c
            java.lang.String r0 = "fill"
            com.app.office.fc.dom4j.Element r0 = r14.element((java.lang.String) r0)
            java.lang.String r4 = "fillcolor"
            java.lang.String r5 = "type"
            r6 = 1065353216(0x3f800000, float:1.0)
            r7 = -1
            if (r0 == 0) goto L_0x0120
            java.lang.String r8 = "id"
            com.app.office.fc.dom4j.Attribute r9 = r0.attribute((java.lang.String) r8)
            if (r9 == 0) goto L_0x0120
            java.lang.String r8 = r0.attributeValue((java.lang.String) r8)
            if (r8 == 0) goto L_0x0120
            int r9 = r8.length()
            if (r9 <= 0) goto L_0x0120
            boolean r9 = r13.isProcessHF
            if (r9 == 0) goto L_0x0066
            com.app.office.fc.openxml4j.opc.PackagePart r9 = r13.hfPart
            if (r9 == 0) goto L_0x0066
            com.app.office.fc.openxml4j.opc.ZipPackage r10 = r13.zipPackage
            com.app.office.fc.openxml4j.opc.PackageRelationship r8 = r9.getRelationship(r8)
            java.net.URI r8 = r8.getTargetURI()
            com.app.office.fc.openxml4j.opc.PackagePart r8 = r10.getPart((java.net.URI) r8)
            goto L_0x0076
        L_0x0066:
            com.app.office.fc.openxml4j.opc.ZipPackage r9 = r13.zipPackage
            com.app.office.fc.openxml4j.opc.PackagePart r10 = r13.packagePart
            com.app.office.fc.openxml4j.opc.PackageRelationship r8 = r10.getRelationship(r8)
            java.net.URI r8 = r8.getTargetURI()
            com.app.office.fc.openxml4j.opc.PackagePart r8 = r9.getPart((java.net.URI) r8)
        L_0x0076:
            if (r8 == 0) goto L_0x0120
            com.app.office.common.bg.BackgroundAndFill r1 = new com.app.office.common.bg.BackgroundAndFill
            r1.<init>()
            java.lang.String r9 = r0.attributeValue((java.lang.String) r5)
            byte r9 = r13.getFillType(r9)
            r10 = 2
            if (r9 != r10) goto L_0x00b3
            r1.setFillType(r10)     // Catch:{ Exception -> 0x00b1 }
            com.app.office.system.IControl r9 = r13.control     // Catch:{ Exception -> 0x00b1 }
            com.app.office.system.SysKit r9 = r9.getSysKit()     // Catch:{ Exception -> 0x00b1 }
            com.app.office.common.picture.PictureManage r9 = r9.getPictureManage()     // Catch:{ Exception -> 0x00b1 }
            int r8 = r9.addPicture((com.app.office.fc.openxml4j.opc.PackagePart) r8)     // Catch:{ Exception -> 0x00b1 }
            com.app.office.common.bg.TileShader r9 = new com.app.office.common.bg.TileShader     // Catch:{ Exception -> 0x00b1 }
            com.app.office.system.IControl r10 = r13.control     // Catch:{ Exception -> 0x00b1 }
            com.app.office.system.SysKit r10 = r10.getSysKit()     // Catch:{ Exception -> 0x00b1 }
            com.app.office.common.picture.PictureManage r10 = r10.getPictureManage()     // Catch:{ Exception -> 0x00b1 }
            com.app.office.common.picture.Picture r8 = r10.getPicture(r8)     // Catch:{ Exception -> 0x00b1 }
            r9.<init>(r8, r2, r6, r6)     // Catch:{ Exception -> 0x00b1 }
            r1.setShader(r9)     // Catch:{ Exception -> 0x00b1 }
            goto L_0x0120
        L_0x00b1:
            r8 = move-exception
            goto L_0x0113
        L_0x00b3:
            if (r9 != r3) goto L_0x00fd
            java.lang.String r9 = r14.attributeValue((java.lang.String) r4)     // Catch:{ Exception -> 0x00b1 }
            if (r9 == 0) goto L_0x00c6
            int r10 = r9.length()     // Catch:{ Exception -> 0x00b1 }
            if (r10 <= 0) goto L_0x00c6
            int r9 = r13.getColor(r9, r2)     // Catch:{ Exception -> 0x00b1 }
            goto L_0x00c7
        L_0x00c6:
            r9 = -1
        L_0x00c7:
            java.lang.String r10 = "color2"
            java.lang.String r10 = r0.attributeValue((java.lang.String) r10)     // Catch:{ Exception -> 0x00b1 }
            if (r10 == 0) goto L_0x00d4
            int r10 = r13.getColor(r10, r3)     // Catch:{ Exception -> 0x00b1 }
            goto L_0x00d5
        L_0x00d4:
            r10 = -1
        L_0x00d5:
            r1.setFillType(r3)     // Catch:{ Exception -> 0x00b1 }
            com.app.office.system.IControl r11 = r13.control     // Catch:{ Exception -> 0x00b1 }
            com.app.office.system.SysKit r11 = r11.getSysKit()     // Catch:{ Exception -> 0x00b1 }
            com.app.office.common.picture.PictureManage r11 = r11.getPictureManage()     // Catch:{ Exception -> 0x00b1 }
            int r8 = r11.addPicture((com.app.office.fc.openxml4j.opc.PackagePart) r8)     // Catch:{ Exception -> 0x00b1 }
            com.app.office.common.bg.PatternShader r11 = new com.app.office.common.bg.PatternShader     // Catch:{ Exception -> 0x00b1 }
            com.app.office.system.IControl r12 = r13.control     // Catch:{ Exception -> 0x00b1 }
            com.app.office.system.SysKit r12 = r12.getSysKit()     // Catch:{ Exception -> 0x00b1 }
            com.app.office.common.picture.PictureManage r12 = r12.getPictureManage()     // Catch:{ Exception -> 0x00b1 }
            com.app.office.common.picture.Picture r8 = r12.getPicture(r8)     // Catch:{ Exception -> 0x00b1 }
            r11.<init>(r8, r10, r9)     // Catch:{ Exception -> 0x00b1 }
            r1.setShader(r11)     // Catch:{ Exception -> 0x00b1 }
            goto L_0x0120
        L_0x00fd:
            r9 = 3
            r1.setFillType(r9)     // Catch:{ Exception -> 0x00b1 }
            com.app.office.system.IControl r9 = r13.control     // Catch:{ Exception -> 0x00b1 }
            com.app.office.system.SysKit r9 = r9.getSysKit()     // Catch:{ Exception -> 0x00b1 }
            com.app.office.common.picture.PictureManage r9 = r9.getPictureManage()     // Catch:{ Exception -> 0x00b1 }
            int r8 = r9.addPicture((com.app.office.fc.openxml4j.opc.PackagePart) r8)     // Catch:{ Exception -> 0x00b1 }
            r1.setPictureIndex(r8)     // Catch:{ Exception -> 0x00b1 }
            goto L_0x0120
        L_0x0113:
            com.app.office.system.IControl r9 = r13.control
            com.app.office.system.SysKit r9 = r9.getSysKit()
            com.app.office.system.ErrorUtil r9 = r9.getErrorKit()
            r9.writerLog(r8)
        L_0x0120:
            if (r1 != 0) goto L_0x017c
            com.app.office.common.bg.BackgroundAndFill r1 = new com.app.office.common.bg.BackgroundAndFill
            r1.<init>()
            if (r0 == 0) goto L_0x0132
            java.lang.String r5 = r0.attributeValue((java.lang.String) r5)
            byte r5 = r13.getFillType(r5)
            goto L_0x0133
        L_0x0132:
            r5 = 0
        L_0x0133:
            if (r0 == 0) goto L_0x0143
            if (r5 != 0) goto L_0x0138
            goto L_0x0143
        L_0x0138:
            com.app.office.common.bg.Gradient r14 = r13.readGradient(r14, r0, r5)
            r1.setFillType(r5)
            r1.setShader(r14)
            goto L_0x017c
        L_0x0143:
            r1.setFillType(r2)
            java.lang.String r14 = r14.attributeValue((java.lang.String) r4)
            if (r14 == 0) goto L_0x0156
            int r2 = r14.length()
            if (r2 <= 0) goto L_0x0156
            int r7 = r13.getColor(r14, r3)
        L_0x0156:
            if (r0 == 0) goto L_0x0179
            java.lang.String r14 = "opacity"
            java.lang.String r14 = r0.attributeValue((java.lang.String) r14)
            if (r14 == 0) goto L_0x0179
            float r14 = java.lang.Float.parseFloat(r14)
            int r0 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
            if (r0 <= 0) goto L_0x016b
            r0 = 1199570944(0x47800000, float:65536.0)
            float r14 = r14 / r0
        L_0x016b:
            r0 = 1132396544(0x437f0000, float:255.0)
            float r14 = r14 * r0
            int r14 = (int) r14
            byte r14 = (byte) r14
            int r14 = r14 << 24
            r0 = 16777215(0xffffff, float:2.3509886E-38)
            r0 = r0 & r7
            r7 = r14 | r0
        L_0x0179:
            r1.setForegroundColor(r7)
        L_0x017c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCXReader.processBackgroundAndFill(com.app.office.fc.dom4j.Element):com.app.office.common.bg.BackgroundAndFill");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0031, code lost:
        if (r3 != 0) goto L_0x003c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.common.bg.Gradient readGradient(com.app.office.fc.dom4j.Element r12, com.app.office.fc.dom4j.Element r13, byte r14) {
        /*
            r11 = this;
            java.lang.String r0 = "focus"
            java.lang.String r0 = r13.attributeValue((java.lang.String) r0)
            java.lang.String r1 = ""
            r2 = 0
            if (r0 == 0) goto L_0x0016
            java.lang.String r3 = "%"
            java.lang.String r0 = r0.replace(r3, r1)
            int r0 = java.lang.Integer.parseInt(r0)
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            java.lang.String r3 = "angle"
            java.lang.String r3 = r13.attributeValue((java.lang.String) r3)
            if (r3 == 0) goto L_0x0024
            int r3 = java.lang.Integer.parseInt(r3)
            goto L_0x0025
        L_0x0024:
            r3 = 0
        L_0x0025:
            r4 = -135(0xffffffffffffff79, float:NaN)
            if (r3 == r4) goto L_0x003a
            r4 = -90
            if (r3 == r4) goto L_0x0037
            r4 = -45
            if (r3 == r4) goto L_0x0034
            if (r3 == 0) goto L_0x0037
            goto L_0x003c
        L_0x0034:
            r3 = 135(0x87, float:1.89E-43)
            goto L_0x003c
        L_0x0037:
            int r3 = r3 + 90
            goto L_0x003c
        L_0x003a:
            r3 = 45
        L_0x003c:
            java.lang.String r4 = "colors"
            java.lang.String r4 = r13.attributeValue((java.lang.String) r4)
            r5 = 1
            if (r4 == 0) goto L_0x0091
            java.lang.String r12 = ";"
            java.lang.String[] r12 = r4.split(r12)
            int r1 = r12.length
            int[] r4 = new int[r1]
            float[] r6 = new float[r1]
            r7 = 0
        L_0x0051:
            if (r7 >= r1) goto L_0x00d1
            r8 = r12[r7]
            java.lang.String r9 = " "
            int r8 = r8.indexOf(r9)
            r9 = r12[r7]
            java.lang.String r9 = r9.substring(r2, r8)
            java.lang.String r10 = "f"
            boolean r10 = r9.contains(r10)
            if (r10 == 0) goto L_0x0074
            float r9 = java.lang.Float.parseFloat(r9)
            r10 = 1203982336(0x47c35000, float:100000.0)
            float r9 = r9 / r10
            r6[r7] = r9
            goto L_0x007a
        L_0x0074:
            float r9 = java.lang.Float.parseFloat(r9)
            r6[r7] = r9
        L_0x007a:
            r9 = r12[r7]
            int r8 = r8 + 1
            r10 = r12[r7]
            int r10 = r10.length()
            java.lang.String r8 = r9.substring(r8, r10)
            int r8 = r11.getColor(r8, r5)
            r4[r7] = r8
            int r7 = r7 + 1
            goto L_0x0051
        L_0x0091:
            java.lang.String r4 = "fillcolor"
            java.lang.String r12 = r12.attributeValue((java.lang.String) r4)
            int r12 = r11.getColor(r12, r5)
            java.lang.String r4 = "color2"
            java.lang.String r4 = r13.attributeValue((java.lang.String) r4)
            if (r4 == 0) goto L_0x00c2
            java.lang.String r6 = "fill "
            java.lang.String r1 = r4.replace(r6, r1)
            java.lang.String r4 = "("
            int r4 = r1.indexOf(r4)
            java.lang.String r6 = ")"
            int r6 = r1.indexOf(r6)
            if (r4 < 0) goto L_0x00bd
            if (r6 < 0) goto L_0x00bd
            int r4 = r4 + r5
            r1.substring(r4, r6)
        L_0x00bd:
            int r1 = r11.getColor(r1, r5)
            goto L_0x00c3
        L_0x00c2:
            r1 = 0
        L_0x00c3:
            r4 = 2
            int[] r6 = new int[r4]
            r6[r2] = r12
            r6[r5] = r1
            float[] r12 = new float[r4]
            r12 = {0, 1065353216} // fill-array
            r4 = r6
            r6 = r12
        L_0x00d1:
            r12 = 0
            r1 = 7
            if (r14 != r1) goto L_0x00dc
            com.app.office.common.bg.LinearGradientShader r12 = new com.app.office.common.bg.LinearGradientShader
            float r13 = (float) r3
            r12.<init>(r13, r4, r6)
            goto L_0x00e9
        L_0x00dc:
            r1 = 4
            if (r14 != r1) goto L_0x00e9
            int r12 = r11.getRadialGradientPositionType(r13)
            com.app.office.common.bg.RadialGradientShader r13 = new com.app.office.common.bg.RadialGradientShader
            r13.<init>(r12, r4, r6)
            r12 = r13
        L_0x00e9:
            if (r12 == 0) goto L_0x00ee
            r12.setFocus(r0)
        L_0x00ee:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCXReader.readGradient(com.app.office.fc.dom4j.Element, com.app.office.fc.dom4j.Element, byte):com.app.office.common.bg.Gradient");
    }

    private AbstractShape processAutoShape2010(ParagraphElement paragraphElement, Element element, WPGroupShape wPGroupShape, float f, float f2, int i, int i2, boolean z) {
        return processAutoShape2010(this.packagePart, paragraphElement, element, wPGroupShape, f, f2, i, i2, z);
    }

    /* JADX WARNING: Removed duplicated region for block: B:72:0x017d  */
    /* JADX WARNING: Removed duplicated region for block: B:80:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.common.shape.AbstractShape processAutoShape2010(com.app.office.fc.openxml4j.opc.PackagePart r21, com.app.office.simpletext.model.ParagraphElement r22, com.app.office.fc.dom4j.Element r23, com.app.office.common.shape.WPGroupShape r24, float r25, float r26, int r27, int r28, boolean r29) {
        /*
            r20 = this;
            r11 = r20
            r0 = r21
            r9 = r23
            r12 = r24
            r13 = r25
            r14 = r26
            if (r9 == 0) goto L_0x019a
            java.lang.String r1 = r23.getName()
            java.lang.String r2 = "wsp"
            boolean r3 = r1.equals(r2)
            java.lang.String r4 = "grpSp"
            java.lang.String r5 = "grpSpPr"
            java.lang.String r6 = "wgp"
            java.lang.String r7 = "pic"
            java.lang.String r8 = "sp"
            if (r3 != 0) goto L_0x0045
            boolean r3 = r1.equals(r8)
            if (r3 != 0) goto L_0x0045
            boolean r3 = r1.equals(r7)
            if (r3 == 0) goto L_0x0031
            goto L_0x0045
        L_0x0031:
            boolean r3 = r1.equals(r6)
            if (r3 != 0) goto L_0x0040
            boolean r3 = r1.equals(r4)
            if (r3 == 0) goto L_0x003e
            goto L_0x0040
        L_0x003e:
            r3 = 0
            goto L_0x004b
        L_0x0040:
            com.app.office.fc.dom4j.Element r3 = r9.element((java.lang.String) r5)
            goto L_0x004b
        L_0x0045:
            java.lang.String r3 = "spPr"
            com.app.office.fc.dom4j.Element r3 = r9.element((java.lang.String) r3)
        L_0x004b:
            java.lang.String r15 = "xfrm"
            if (r3 == 0) goto L_0x0071
            com.app.office.fc.ppt.reader.ReaderKit r10 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            com.app.office.fc.dom4j.Element r3 = r3.element((java.lang.String) r15)
            com.app.office.java.awt.Rectangle r3 = r10.getShapeAnchor(r3, r13, r14)
            if (r3 == 0) goto L_0x006f
            if (r29 == 0) goto L_0x006b
            int r10 = r3.x
            int r10 = r10 + r27
            r3.x = r10
            int r10 = r3.y
            int r10 = r10 + r28
            r3.y = r10
        L_0x006b:
            com.app.office.java.awt.Rectangle r3 = r11.processGrpSpRect(r12, r3)
        L_0x006f:
            r10 = r3
            goto L_0x0072
        L_0x0071:
            r10 = 0
        L_0x0072:
            if (r10 == 0) goto L_0x0115
            boolean r3 = r1.equals(r6)
            if (r3 != 0) goto L_0x0080
            boolean r3 = r1.equals(r4)
            if (r3 == 0) goto L_0x0115
        L_0x0080:
            com.app.office.fc.dom4j.Element r1 = r9.element((java.lang.String) r5)
            if (r1 == 0) goto L_0x017a
            com.app.office.fc.ppt.reader.ReaderKit r2 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r15)
            float[] r16 = r2.getAnchorFitZoom(r3)
            com.app.office.fc.ppt.reader.ReaderKit r2 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r15)
            r15 = 0
            r4 = r16[r15]
            float r4 = r4 * r13
            r17 = 1
            r5 = r16[r17]
            float r5 = r5 * r14
            com.app.office.java.awt.Rectangle r2 = r2.getChildShapeAnchor(r3, r4, r5)
            if (r29 == 0) goto L_0x00b7
            int r3 = r2.x
            int r3 = r3 + r27
            r2.x = r3
            int r3 = r2.y
            int r3 = r3 + r28
            r2.y = r3
        L_0x00b7:
            com.app.office.common.shape.WPGroupShape r8 = new com.app.office.common.shape.WPGroupShape
            r8.<init>()
            int r3 = r10.x
            int r4 = r2.x
            int r3 = r3 - r4
            int r4 = r10.y
            int r2 = r2.y
            int r4 = r4 - r2
            r8.setOffPostion(r3, r4)
            r8.setBounds(r10)
            com.app.office.fc.ppt.reader.ReaderKit r2 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            r2.processRotation((com.app.office.fc.dom4j.Element) r1, (com.app.office.common.shape.IShape) r8)
            java.util.Iterator r18 = r23.elementIterator()
        L_0x00d7:
            boolean r1 = r18.hasNext()
            if (r1 == 0) goto L_0x0102
            java.lang.Object r1 = r18.next()
            r4 = r1
            com.app.office.fc.dom4j.Element r4 = (com.app.office.fc.dom4j.Element) r4
            r1 = r16[r15]
            float r6 = r1 * r13
            r1 = r16[r17]
            float r7 = r1 * r14
            r9 = 0
            r10 = 0
            r19 = 0
            r1 = r20
            r2 = r21
            r3 = r22
            r5 = r8
            r15 = r8
            r8 = r9
            r9 = r10
            r10 = r19
            r1.processAutoShape2010(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r8 = r15
            r15 = 0
            goto L_0x00d7
        L_0x0102:
            r15 = r8
            if (r12 != 0) goto L_0x0112
            com.app.office.common.shape.WPAutoShape r10 = new com.app.office.common.shape.WPAutoShape
            r10.<init>()
            r0 = r10
            com.app.office.common.shape.WPAutoShape r0 = (com.app.office.common.shape.WPAutoShape) r0
            r0.addGroupShape(r15)
            goto L_0x017b
        L_0x0112:
            r10 = r15
            goto L_0x017b
        L_0x0115:
            if (r10 == 0) goto L_0x017a
            boolean r2 = r1.equals(r2)
            if (r2 != 0) goto L_0x0130
            boolean r2 = r1.equals(r8)
            if (r2 == 0) goto L_0x0124
            goto L_0x0130
        L_0x0124:
            boolean r0 = r1.equals(r7)
            if (r0 == 0) goto L_0x017a
            com.app.office.common.shape.PictureShape r0 = r11.addPicture(r9, r10)
            r10 = r0
            goto L_0x017b
        L_0x0130:
            boolean r1 = r11.isProcessHF     // Catch:{ Exception -> 0x0174 }
            if (r1 == 0) goto L_0x0155
            com.app.office.fc.openxml4j.opc.PackagePart r1 = r11.hfPart     // Catch:{ Exception -> 0x0174 }
            if (r1 == 0) goto L_0x0155
            com.app.office.system.IControl r1 = r11.control     // Catch:{ Exception -> 0x0174 }
            com.app.office.fc.openxml4j.opc.ZipPackage r2 = r11.zipPackage     // Catch:{ Exception -> 0x0174 }
            com.app.office.fc.openxml4j.opc.PackagePart r3 = r11.hfPart     // Catch:{ Exception -> 0x0174 }
            java.util.Map<java.lang.String, java.lang.Integer> r6 = r11.themeColor     // Catch:{ Exception -> 0x0174 }
            r7 = 0
            boolean r8 = r11.hasTextbox2010(r9)     // Catch:{ Exception -> 0x0174 }
            r4 = r23
            r5 = r10
            com.app.office.common.shape.AbstractShape r10 = com.app.office.common.autoshape.AutoShapeDataKit.getAutoShape(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0174 }
            if (r10 == 0) goto L_0x017b
            r1 = r10
            com.app.office.common.shape.WPAutoShape r1 = (com.app.office.common.shape.WPAutoShape) r1     // Catch:{ Exception -> 0x0172 }
            r11.processTextbox2010(r0, r1, r9)     // Catch:{ Exception -> 0x0172 }
            goto L_0x017b
        L_0x0155:
            com.app.office.system.IControl r1 = r11.control     // Catch:{ Exception -> 0x0174 }
            com.app.office.fc.openxml4j.opc.ZipPackage r2 = r11.zipPackage     // Catch:{ Exception -> 0x0174 }
            java.util.Map<java.lang.String, java.lang.Integer> r6 = r11.themeColor     // Catch:{ Exception -> 0x0174 }
            r7 = 0
            boolean r8 = r11.hasTextbox2010(r9)     // Catch:{ Exception -> 0x0174 }
            r3 = r21
            r4 = r23
            r5 = r10
            com.app.office.common.shape.AbstractShape r10 = com.app.office.common.autoshape.AutoShapeDataKit.getAutoShape(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0174 }
            if (r10 == 0) goto L_0x017b
            r1 = r10
            com.app.office.common.shape.WPAutoShape r1 = (com.app.office.common.shape.WPAutoShape) r1     // Catch:{ Exception -> 0x0172 }
            r11.processTextbox2010(r0, r1, r9)     // Catch:{ Exception -> 0x0172 }
            goto L_0x017b
        L_0x0172:
            r0 = move-exception
            goto L_0x0176
        L_0x0174:
            r0 = move-exception
            r10 = 0
        L_0x0176:
            r0.printStackTrace()
            goto L_0x017b
        L_0x017a:
            r10 = 0
        L_0x017b:
            if (r10 == 0) goto L_0x019b
            if (r12 != 0) goto L_0x0185
            r1 = r22
            r11.addShape(r10, r1)
            goto L_0x019b
        L_0x0185:
            r10.setParent(r12)
            boolean r0 = r10 instanceof com.app.office.common.shape.WPAutoShape
            if (r0 == 0) goto L_0x0196
            r0 = r10
            com.app.office.common.shape.WPAutoShape r0 = (com.app.office.common.shape.WPAutoShape) r0
            short r1 = r24.getWrapType()
            r0.setWrap(r1)
        L_0x0196:
            r12.appendShapes(r10)
            goto L_0x019b
        L_0x019a:
            r10 = 0
        L_0x019b:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCXReader.processAutoShape2010(com.app.office.fc.openxml4j.opc.PackagePart, com.app.office.simpletext.model.ParagraphElement, com.app.office.fc.dom4j.Element, com.app.office.common.shape.WPGroupShape, float, float, int, int, boolean):com.app.office.common.shape.AbstractShape");
    }

    private void processAlternateContent(Element element, ParagraphElement paragraphElement) {
        Element element2;
        Element element3;
        short s;
        Element element4;
        Element element5;
        String attributeValue;
        if (element != null && (element2 = element.element("Choice")) != null && (element3 = element2.element("drawing")) != null) {
            Element element6 = element3.element("anchor");
            if (element6 == null) {
                element6 = element3.element("inline");
                s = 2;
            } else {
                s = -1;
            }
            if (element6 != null) {
                Element element7 = element6.element("docPr");
                if ((element7 == null || (attributeValue = element7.attributeValue("name")) == null || (!attributeValue.startsWith("Genko") && !attributeValue.startsWith("Header") && !attributeValue.startsWith("Footer"))) && (element4 = element6.element("graphic")) != null && (element5 = element4.element("graphicData")) != null) {
                    Iterator elementIterator = element5.elementIterator();
                    while (elementIterator.hasNext()) {
                        AbstractShape processAutoShape2010 = processAutoShape2010(paragraphElement, (Element) elementIterator.next(), (WPGroupShape) null, 1.0f, 1.0f, 0, 0, true);
                        if (processAutoShape2010 != null) {
                            if (processAutoShape2010 instanceof WPAutoShape) {
                                WPAutoShape wPAutoShape = (WPAutoShape) processAutoShape2010;
                                if (wPAutoShape.getGroupShape() != null) {
                                    WPGroupShape groupShape = wPAutoShape.getGroupShape();
                                    if (s == -1) {
                                        s = getDrawingWrapType(element6);
                                    }
                                    groupShape.setWrapType(s);
                                    setShapeWrapType(groupShape, s);
                                }
                            }
                            processWrapAndPosition_Drawing((WPAutoShape) processAutoShape2010, element6, processAutoShape2010.getBounds());
                        }
                    }
                }
            }
        }
    }

    private void setShapeWrapType(WPGroupShape wPGroupShape, short s) {
        for (IShape iShape : wPGroupShape.getShapes()) {
            if (iShape instanceof WPAbstractShape) {
                ((WPAbstractShape) iShape).setWrap(s);
            } else if (iShape instanceof WPGroupShape) {
                setShapeWrapType((WPGroupShape) iShape, s);
            }
        }
    }

    private void processRunAttribute(Element element, IAttributeSet iAttributeSet) {
        String attributeValue;
        int addFontName;
        Element element2 = element.element("szCs");
        Element element3 = element.element("sz");
        if (!(element2 == null && element3 == null)) {
            float f = 12.0f;
            if (element2 != null) {
                f = Math.max(12.0f, Float.parseFloat(element2.attributeValue("val")) / 2.0f);
            }
            if (element3 != null) {
                f = Math.max(f, Float.parseFloat(element3.attributeValue("val")) / 2.0f);
            }
            AttrManage.instance().setFontSize(iAttributeSet, (int) f);
        }
        Element element4 = element.element("rFonts");
        if (element4 != null) {
            String attributeValue2 = element4.attributeValue("hAnsi");
            if (attributeValue2 == null) {
                attributeValue2 = element4.attributeValue("eastAsia");
            }
            if (attributeValue2 != null && (addFontName = FontTypefaceManage.instance().addFontName(attributeValue2)) >= 0) {
                AttrManage.instance().setFontName(iAttributeSet, addFontName);
            }
        }
        Element element5 = element.element("color");
        if (element5 != null) {
            String attributeValue3 = element5.attributeValue("val");
            if (DebugKt.DEBUG_PROPERTY_VALUE_AUTO.equals(attributeValue3) || "FFFFFF".equals(attributeValue3)) {
                AttrManage.instance().setFontColor(iAttributeSet, -16777216);
            } else {
                AttrManage instance = AttrManage.instance();
                instance.setFontColor(iAttributeSet, Color.parseColor("#" + attributeValue3));
            }
        }
        int i = 1;
        if (element.element(HtmlTags.B) != null) {
            AttrManage.instance().setFontBold(iAttributeSet, true);
        }
        if (element.element("i") != null) {
            AttrManage.instance().setFontItalic(iAttributeSet, true);
        }
        Element element6 = element.element(HtmlTags.U);
        if (!(element6 == null || element6.attributeValue("val") == null)) {
            AttrManage.instance().setFontUnderline(iAttributeSet, 1);
            String attributeValue4 = element6.attributeValue("color");
            if (attributeValue4 != null && attributeValue4.length() > 0) {
                AttrManage instance2 = AttrManage.instance();
                instance2.setFontUnderlineColr(iAttributeSet, Color.parseColor("#" + attributeValue4));
            }
        }
        Element element7 = element.element(HtmlTags.STRIKE);
        if (element7 != null) {
            AttrManage.instance().setFontStrike(iAttributeSet, !"0".equals(element7.attributeValue("val")));
        }
        Element element8 = element.element("dstrike");
        if (element8 != null) {
            AttrManage.instance().setFontDoubleStrike(iAttributeSet, !"0".equals(element8.attributeValue("val")));
        }
        Element element9 = element.element("vertAlign");
        if (element9 != null) {
            String attributeValue5 = element9.attributeValue("val");
            AttrManage instance3 = AttrManage.instance();
            if (!attributeValue5.equals("superscript")) {
                i = 2;
            }
            instance3.setFontScript(iAttributeSet, i);
        }
        Element element10 = element.element("rStyle");
        if (!(element10 == null || (attributeValue = element10.attributeValue("val")) == null || attributeValue.length() <= 0)) {
            AttrManage.instance().setParaStyleID(iAttributeSet, this.styleStrID.get(attributeValue).intValue());
        }
        Element element11 = element.element("highlight");
        if (element11 != null) {
            AttrManage.instance().setFontHighLight(iAttributeSet, FCKit.convertColor(element11.attributeValue("val")));
        }
    }

    private int processValue(String str, boolean z) {
        int i;
        int i2 = z ? 144 : 72;
        if (str == null) {
            return i2;
        }
        if (ReaderKit.instance().isDecimal(str)) {
            i = Integer.parseInt(str);
        } else {
            i = Integer.parseInt(str, 16);
        }
        return (int) (((((float) i) * 96.0f) / 914400.0f) * 15.0f);
    }

    /* access modifiers changed from: private */
    public void processParagraphs(List<Element> list) {
        Iterator<Element> it = list.iterator();
        while (it.hasNext()) {
            Element next = it.next();
            if ("sdt".equals(next.getName()) && (next = next.element("sdtContent")) != null) {
                processParagraphs(next.elements());
            }
            if ("p".equals(next.getName())) {
                processParagraph(next, 0);
            } else if (PGPlaceholderUtil.TABLE.equals(next.getName())) {
                processTable(next);
            }
        }
    }

    private boolean hasTextbox2007(Element element) {
        String attributeValue;
        Element element2 = element.element("textbox");
        if (element2 != null) {
            if (element2.element("txbxContent") != null) {
                return true;
            }
        } else if (element.element("textpath") == null || (attributeValue = element.element("textpath").attributeValue((String) TypedValues.Custom.S_STRING)) == null || attributeValue.length() <= 0) {
            return false;
        } else {
            return true;
        }
        return false;
    }

    private boolean processTextbox2007(PackagePart packagePart2, WPAutoShape wPAutoShape, Element element) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        String str12;
        SectionElement sectionElement;
        int i;
        int i2;
        int i3;
        int i4;
        String str13;
        String str14;
        int i5;
        WPAutoShape wPAutoShape2 = wPAutoShape;
        Element element2 = element;
        Element element3 = element2.element("textbox");
        if (element3 != null) {
            Element element4 = element3.element("txbxContent");
            if (element4 == null) {
                return false;
            }
            long j = this.offset;
            long j2 = this.textboxIndex;
            String str15 = HtmlTags.ALIGN_BOTTOM;
            String str16 = "none";
            this.offset = (j2 << 32) + WPModelConstant.TEXTBOX;
            wPAutoShape2.setElementIndex((int) j2);
            SectionElement sectionElement2 = new SectionElement();
            sectionElement2.setStartOffset(this.offset);
            this.wpdoc.appendElement(sectionElement2, this.offset);
            processParagraphs(element4.elements());
            IAttributeSet attribute = sectionElement2.getAttribute();
            AttrManage.instance().setPageWidth(attribute, (int) (((float) wPAutoShape.getBounds().width) * 15.0f));
            AttrManage.instance().setPageHeight(attribute, (int) (((float) wPAutoShape.getBounds().height) * 15.0f));
            String attributeValue = element3.attributeValue("inset");
            if (attributeValue != null) {
                String[] split = attributeValue.split(",");
                if (split.length <= 0 || split[0].length() <= 0) {
                    sectionElement = sectionElement2;
                    i5 = 144;
                } else {
                    sectionElement = sectionElement2;
                    i5 = Math.round(getValueInPt(split[0], 1.0f) * 20.0f);
                }
                i2 = (split.length <= 1 || split[1].length() <= 0) ? 72 : Math.round(getValueInPt(split[1], 1.0f) * 20.0f);
                int round = (split.length <= 2 || split[2].length() <= 0) ? 144 : Math.round(getValueInPt(split[2], 1.0f) * 20.0f);
                int i6 = i5;
                if (split.length <= 3 || split[3].length() <= 0) {
                    i3 = round;
                    i = i6;
                    i4 = 72;
                } else {
                    i4 = Math.round(getValueInPt(split[3], 1.0f) * 20.0f);
                    i3 = round;
                    i = i6;
                }
            } else {
                sectionElement = sectionElement2;
                i4 = 72;
                i3 = 144;
                i2 = 72;
                i = 144;
            }
            AttrManage.instance().setPageMarginTop(attribute, i2);
            AttrManage.instance().setPageMarginBottom(attribute, i4);
            AttrManage.instance().setPageMarginLeft(attribute, i);
            AttrManage.instance().setPageMarginRight(attribute, i3);
            String attributeValue2 = element2.attributeValue(HtmlTags.STYLE);
            if (attributeValue2 != null) {
                String[] split2 = attributeValue2.split(";");
                int i7 = 0;
                while (i7 < split2.length) {
                    String[] split3 = split2[i7].split(":");
                    if (!(split3 == null || split3[0] == null || split3[1] == null || HtmlTags.TEXTALIGN.equalsIgnoreCase(split3[0]))) {
                        if (!"v-text-anchor".equalsIgnoreCase(split3[0])) {
                            str13 = str15;
                            if ("mso-wrap-style".equalsIgnoreCase(split3[0])) {
                                str14 = str16;
                                wPAutoShape2.setTextWrapLine(!str14.equalsIgnoreCase(split3[1]));
                                i7++;
                                str16 = str14;
                                str15 = str13;
                            }
                        } else if (HtmlTags.ALIGN_MIDDLE.equals(split3[1])) {
                            AttrManage.instance().setPageVerticalAlign(attribute, (byte) 1);
                        } else {
                            str13 = str15;
                            if (str13.equals(split3[1])) {
                                AttrManage.instance().setPageVerticalAlign(attribute, (byte) 2);
                            } else if (HtmlTags.ALIGN_TOP.equals(split3[1])) {
                                AttrManage.instance().setPageVerticalAlign(attribute, (byte) 0);
                            }
                        }
                        str14 = str16;
                        i7++;
                        str16 = str14;
                        str15 = str13;
                    }
                    str13 = str15;
                    str14 = str16;
                    i7++;
                    str16 = str14;
                    str15 = str13;
                }
            }
            wPAutoShape2.setElementIndex((int) this.textboxIndex);
            sectionElement.setEndOffset(this.offset);
            this.textboxIndex++;
            this.offset = j;
            return true;
        }
        String str17 = HtmlTags.ALIGN_BOTTOM;
        String str18 = "none";
        if (element2.element("textpath") == null) {
            return false;
        }
        String attributeValue3 = element2.element("textpath").attributeValue(TypedValues.Custom.S_STRING);
        wPAutoShape2.setBackgroundAndFill((BackgroundAndFill) null);
        String str19 = str17;
        long j3 = this.offset;
        long j4 = this.textboxIndex;
        String str20 = HtmlTags.ALIGN_MIDDLE;
        this.offset = (j4 << 32) + WPModelConstant.TEXTBOX;
        wPAutoShape2.setElementIndex((int) j4);
        SectionElement sectionElement3 = new SectionElement();
        sectionElement3.setStartOffset(this.offset);
        this.wpdoc.appendElement(sectionElement3, this.offset);
        ParagraphElement paragraphElement = new ParagraphElement();
        long j5 = this.offset;
        paragraphElement.setStartOffset(j5);
        String str21 = str20;
        int length = attributeValue3.length();
        if (length > 0) {
            LeafElement leafElement = new LeafElement(attributeValue3);
            str2 = str18;
            String attributeValue4 = element2.attributeValue("fillcolor");
            if (attributeValue4 == null || attributeValue4.length() <= 0) {
                str6 = "mso-wrap-style";
                str5 = "v-text-anchor";
                str4 = HtmlTags.TEXTALIGN;
            } else {
                str6 = "mso-wrap-style";
                AttrManage instance = AttrManage.instance();
                str5 = "v-text-anchor";
                IAttributeSet attribute2 = leafElement.getAttribute();
                str4 = HtmlTags.TEXTALIGN;
                instance.setFontColor(attribute2, getColor(attributeValue4, true));
            }
            float width = ((float) wPAutoShape.getBounds().getWidth()) - 19.2f;
            float height = ((float) wPAutoShape.getBounds().getHeight()) - 9.6f;
            int i8 = 12;
            Paint paint = PaintKit.instance().getPaint();
            str3 = ":";
            paint.setTextSize((float) 12);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            str = ";";
            while (((float) ((int) paint.measureText(attributeValue3))) < width && ((float) ((int) Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent)))) < height) {
                i8++;
                paint.setTextSize((float) i8);
                fontMetrics = paint.getFontMetrics();
            }
            AttrManage.instance().setFontSize(leafElement.getAttribute(), (int) (((float) (i8 - 1)) * 0.75f));
            leafElement.setStartOffset(this.offset);
            long j6 = this.offset + ((long) length);
            this.offset = j6;
            leafElement.setEndOffset(j6);
            paragraphElement.appendLeaf(leafElement);
        } else {
            str2 = str18;
            str6 = "mso-wrap-style";
            str5 = "v-text-anchor";
            str4 = HtmlTags.TEXTALIGN;
            str3 = ":";
            str = ";";
        }
        paragraphElement.setEndOffset(this.offset);
        long j7 = this.offset;
        if (j7 > j5) {
            this.wpdoc.appendParagraph(paragraphElement, j7);
        }
        IAttributeSet attribute3 = sectionElement3.getAttribute();
        AttrManage.instance().setPageWidth(attribute3, (int) (((float) wPAutoShape.getBounds().width) * 15.0f));
        AttrManage.instance().setPageHeight(attribute3, (int) (((float) wPAutoShape.getBounds().height) * 15.0f));
        AttrManage.instance().setPageMarginTop(attribute3, 72);
        AttrManage.instance().setPageMarginBottom(attribute3, 72);
        AttrManage.instance().setPageMarginLeft(attribute3, 144);
        AttrManage.instance().setPageMarginRight(attribute3, 144);
        String attributeValue5 = element2.attributeValue(HtmlTags.STYLE);
        if (attributeValue5 != null) {
            String[] split4 = attributeValue5.split(str);
            int i9 = 0;
            while (i9 < split4.length) {
                String str22 = str3;
                String[] split5 = split4[i9].split(str22);
                if (split5 == null || split5[0] == null || split5[1] == null) {
                    WPAutoShape wPAutoShape3 = wPAutoShape;
                    str10 = str21;
                    str8 = str6;
                    str12 = str5;
                    str11 = str4;
                } else {
                    str11 = str4;
                    if (str11.equalsIgnoreCase(split5[0])) {
                        WPAutoShape wPAutoShape4 = wPAutoShape;
                        str10 = str21;
                        str8 = str6;
                        str12 = str5;
                    } else {
                        str12 = str5;
                        if (str12.equalsIgnoreCase(split5[0])) {
                            str10 = str21;
                            if (str10.equals(split5[1])) {
                                AttrManage.instance().setPageVerticalAlign(attribute3, (byte) 1);
                                WPAutoShape wPAutoShape5 = wPAutoShape;
                                str8 = str6;
                            } else {
                                str9 = str19;
                                if (str9.equals(split5[1])) {
                                    AttrManage.instance().setPageVerticalAlign(attribute3, (byte) 2);
                                } else if (HtmlTags.ALIGN_TOP.equals(split5[1])) {
                                    AttrManage.instance().setPageVerticalAlign(attribute3, (byte) 0);
                                }
                                WPAutoShape wPAutoShape6 = wPAutoShape;
                                str8 = str6;
                            }
                        } else {
                            str10 = str21;
                            str9 = str19;
                            str8 = str6;
                            if (str8.equalsIgnoreCase(split5[0])) {
                                str7 = str2;
                                wPAutoShape.setTextWrapLine(!str7.equalsIgnoreCase(split5[1]));
                                i9++;
                                str3 = str22;
                                str5 = str12;
                                str4 = str11;
                                str21 = str10;
                                str19 = str9;
                                str6 = str8;
                                str2 = str7;
                            } else {
                                WPAutoShape wPAutoShape7 = wPAutoShape;
                            }
                        }
                        str7 = str2;
                        i9++;
                        str3 = str22;
                        str5 = str12;
                        str4 = str11;
                        str21 = str10;
                        str19 = str9;
                        str6 = str8;
                        str2 = str7;
                    }
                }
                str9 = str19;
                str7 = str2;
                i9++;
                str3 = str22;
                str5 = str12;
                str4 = str11;
                str21 = str10;
                str19 = str9;
                str6 = str8;
                str2 = str7;
            }
        }
        wPAutoShape.setElementIndex((int) this.textboxIndex);
        sectionElement3.setEndOffset(this.offset);
        this.textboxIndex++;
        this.offset = j3;
        return true;
    }

    private boolean hasTextbox2010(Element element) {
        Element element2 = element.element("txbx");
        return (element2 == null || element2.element("txbxContent") == null) ? false : true;
    }

    private boolean processTextbox2010(PackagePart packagePart2, WPAutoShape wPAutoShape, Element element) {
        Element element2;
        Element element3 = element.element("txbx");
        boolean z = false;
        if (element3 == null || (element2 = element3.element("txbxContent")) == null) {
            return false;
        }
        long j = this.offset;
        long j2 = this.textboxIndex;
        this.offset = (j2 << 32) + WPModelConstant.TEXTBOX;
        wPAutoShape.setElementIndex((int) j2);
        SectionElement sectionElement = new SectionElement();
        sectionElement.setStartOffset(this.offset);
        this.wpdoc.appendElement(sectionElement, this.offset);
        processParagraphs(element2.elements());
        IAttributeSet attribute = sectionElement.getAttribute();
        AttrManage.instance().setPageWidth(attribute, (int) (((float) wPAutoShape.getBounds().width) * 15.0f));
        AttrManage.instance().setPageHeight(attribute, (int) (((float) wPAutoShape.getBounds().height) * 15.0f));
        Element element4 = element.element("bodyPr");
        if (element4 != null) {
            AttrManage.instance().setPageMarginTop(attribute, processValue(element4.attributeValue("tIns"), false));
            AttrManage.instance().setPageMarginBottom(attribute, processValue(element4.attributeValue("bIns"), false));
            AttrManage.instance().setPageMarginLeft(attribute, processValue(element4.attributeValue("lIns"), true));
            AttrManage.instance().setPageMarginRight(attribute, processValue(element4.attributeValue("rIns"), true));
            String attributeValue = element4.attributeValue("anchor");
            if ("ctr".equals(attributeValue)) {
                AttrManage.instance().setPageVerticalAlign(attribute, (byte) 1);
            } else if (HtmlTags.B.equals(attributeValue)) {
                AttrManage.instance().setPageVerticalAlign(attribute, (byte) 2);
            } else if ("t".equals(attributeValue)) {
                AttrManage.instance().setPageVerticalAlign(attribute, (byte) 0);
            }
            String attributeValue2 = element4.attributeValue("wrap");
            if (attributeValue2 == null || "square".equalsIgnoreCase(attributeValue2)) {
                z = true;
            }
            wPAutoShape.setTextWrapLine(z);
            wPAutoShape.setElementIndex((int) this.textboxIndex);
        }
        sectionElement.setEndOffset(this.offset);
        this.textboxIndex++;
        this.offset = j;
        return true;
    }

    public boolean searchContent(File file, String str) throws Exception {
        ZipPackage zipPackage2 = new ZipPackage(this.filePath);
        this.zipPackage = zipPackage2;
        boolean z = false;
        this.packagePart = this.zipPackage.getPart(zipPackage2.getRelationshipsByType(PackageRelationshipTypes.CORE_DOCUMENT).getRelationship(0));
        SAXReader sAXReader = new SAXReader();
        InputStream inputStream = this.packagePart.getInputStream();
        Element element = sAXReader.read(inputStream).getRootElement().element("body");
        StringBuilder sb = new StringBuilder();
        if (element != null) {
            Iterator it = element.elements("p").iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                for (Element element2 : ((Element) it.next()).elements("r")) {
                    Element element3 = element2.element("t");
                    if (element3 != null) {
                        sb.append(element3.getText());
                    }
                }
                if (sb.indexOf(str) >= 0) {
                    z = true;
                    break;
                }
                sb.delete(0, sb.length());
            }
        }
        this.zipPackage = null;
        this.packagePart = null;
        inputStream.close();
        return z;
    }

    class DOCXSaxHandler implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        DOCXSaxHandler() {
        }

        public void onEnd(ElementPath elementPath) {
            if (!DOCXReader.this.abortReader) {
                Element current = elementPath.getCurrent();
                String name = current.getName();
                current.elements();
                if ("p".equals(name)) {
                    DOCXReader.this.processParagraph(current, 0);
                }
                if ("sdt".equals(current.getName())) {
                    current = current.element("sdtContent");
                    if (current != null) {
                        DOCXReader.this.processParagraphs(current.elements());
                    }
                } else if (PGPlaceholderUtil.TABLE.equals(name)) {
                    DOCXReader.this.processTable(current);
                } else if (Picture.PICT_TYPE.equals(name)) {
                    ParagraphElement paragraphElement = new ParagraphElement();
                    long access$400 = DOCXReader.this.offset;
                    paragraphElement.setStartOffset(DOCXReader.this.offset);
                    DOCXReader.this.processPicture(current, paragraphElement);
                    paragraphElement.setEndOffset(DOCXReader.this.offset);
                    if (DOCXReader.this.offset > access$400) {
                        DOCXReader.this.wpdoc.appendParagraph(paragraphElement, DOCXReader.this.offset);
                    }
                }
                current.detach();
                return;
            }
            throw new AbortReaderError("abort Reader");
        }
    }

    private void processThemeColor() throws Exception {
        PackageRelationship relationship;
        PackagePart part;
        PackagePart packagePart2 = this.packagePart;
        if (packagePart2 != null && (relationship = packagePart2.getRelationshipsByType(PackageRelationshipTypes.THEME_PART).getRelationship(0)) != null && (part = this.zipPackage.getPart(relationship.getTargetURI())) != null) {
            Map<String, Integer> themeColorMap = ThemeReader.instance().getThemeColorMap(part);
            this.themeColor = themeColorMap;
            if (themeColorMap != null) {
                themeColorMap.put(SchemeClrConstant.SCHEME_BG1, themeColorMap.get(SchemeClrConstant.SCHEME_LT1));
                Map<String, Integer> map = this.themeColor;
                map.put(SchemeClrConstant.SCHEME_TX1, map.get(SchemeClrConstant.SCHEME_DK1));
                Map<String, Integer> map2 = this.themeColor;
                map2.put(SchemeClrConstant.SCHEME_BG2, map2.get(SchemeClrConstant.SCHEME_LT2));
                Map<String, Integer> map3 = this.themeColor;
                map3.put(SchemeClrConstant.SCHEME_TX2, map3.get(SchemeClrConstant.SCHEME_DK2));
            }
        }
    }

    private void processRelativeShapeSize() {
        int pageWidth = AttrManage.instance().getPageWidth(this.secElem.getAttribute());
        int pageHeight = AttrManage.instance().getPageHeight(this.secElem.getAttribute());
        for (IShape next : this.relativeType) {
            int[] iArr = this.relativeValue.get(next);
            Rectangle bounds = next.getBounds();
            if (iArr[0] > 0) {
                bounds.width = (int) (((((float) pageWidth) * 0.06666667f) * ((float) iArr[0])) / 1000.0f);
            }
            if (iArr[2] > 0) {
                bounds.height = (int) (((((float) pageHeight) * 0.06666667f) * ((float) iArr[2])) / 1000.0f);
            }
        }
    }

    private byte getArrowType(String str) {
        if ("block".equalsIgnoreCase(str)) {
            return 1;
        }
        if ("classic".equalsIgnoreCase(str)) {
            return 2;
        }
        if ("oval".equalsIgnoreCase(str)) {
            return 4;
        }
        if ("diamond".equalsIgnoreCase(str)) {
            return 3;
        }
        return "open".equalsIgnoreCase(str) ? (byte) 5 : 0;
    }

    private int getArrowWidth(String str) {
        if ("narrow".equalsIgnoreCase(str)) {
            return 0;
        }
        return "wide".equalsIgnoreCase(str) ? 2 : 1;
    }

    private int getArrowLength(String str) {
        if ("short".equalsIgnoreCase(str)) {
            return 0;
        }
        return "long".equalsIgnoreCase(str) ? 2 : 1;
    }

    public void dispose() {
        if (isReaderFinish()) {
            this.filePath = null;
            this.zipPackage = null;
            this.wpdoc = null;
            this.packagePart = null;
            Map<String, Integer> map = this.styleStrID;
            if (map != null) {
                map.clear();
                this.styleStrID = null;
            }
            Map<Integer, Integer> map2 = this.tableGridCol;
            if (map2 != null) {
                map2.clear();
                this.tableGridCol = null;
            }
            this.tableGridCol = null;
            this.control = null;
            List<IShape> list = this.relativeType;
            if (list != null) {
                list.clear();
                this.relativeType = null;
            }
            Map<IShape, int[]> map3 = this.relativeValue;
            if (map3 != null) {
                map3.clear();
                this.relativeValue = null;
            }
            Hashtable<String, String> hashtable = this.bulletNumbersID;
            if (hashtable != null) {
                hashtable.clear();
                this.bulletNumbersID = null;
            }
        }
    }
}
