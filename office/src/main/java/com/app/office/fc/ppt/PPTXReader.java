package com.app.office.fc.ppt;

import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.shape.GroupShape;
import com.app.office.common.shape.IShape;
import com.app.office.constant.EventConstant;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.ElementHandler;
import com.app.office.fc.dom4j.ElementPath;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.ContentTypes;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.PackageRelationshipCollection;
import com.app.office.fc.openxml4j.opc.PackageRelationshipTypes;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.fc.ppt.attribute.RunAttr;
import com.app.office.fc.ppt.bulletnumber.BulletNumberManage;
import com.app.office.fc.ppt.reader.BackgroundReader;
import com.app.office.fc.ppt.reader.HyperlinkReader;
import com.app.office.fc.ppt.reader.LayoutReader;
import com.app.office.fc.ppt.reader.MasterReader;
import com.app.office.fc.ppt.reader.PictureReader;
import com.app.office.fc.ppt.reader.ReaderKit;
import com.app.office.fc.ppt.reader.SmartArtReader;
import com.app.office.fc.ppt.reader.StyleReader;
import com.app.office.fc.ppt.reader.TableStyleReader;
import com.app.office.java.awt.Dimension;
import com.app.office.pg.animate.ShapeAnimation;
import com.app.office.pg.model.PGLayout;
import com.app.office.pg.model.PGMaster;
import com.app.office.pg.model.PGModel;
import com.app.office.pg.model.PGNotes;
import com.app.office.pg.model.PGSlide;
import com.app.office.pg.model.PGStyle;
import com.app.office.simpletext.model.Style;
import com.app.office.simpletext.model.StyleManage;
import com.app.office.system.AbortReaderError;
import com.app.office.system.AbstractReader;
import com.app.office.system.BackReaderThread;
import com.app.office.system.IControl;
import com.app.office.system.StopReaderError;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PPTXReader extends AbstractReader {
    public static final int FIRST_READ_SLIDE_NUM = 2;
    private int currentReaderIndex;
    /* access modifiers changed from: private */
    public PGStyle defaultStyle;
    private String filePath;
    /* access modifiers changed from: private */
    public String key;
    private Map<String, PGLayout> nameLayout = new Hashtable();
    private Map<String, PGMaster> nameMaster = new Hashtable();
    /* access modifiers changed from: private */
    public boolean note;
    /* access modifiers changed from: private */
    public PackagePart packagePart;
    /* access modifiers changed from: private */
    public PGLayout pgLayout;
    /* access modifiers changed from: private */
    public PGMaster pgMaster;
    /* access modifiers changed from: private */
    public PGModel pgModel;
    /* access modifiers changed from: private */
    public PGSlide pgSlide;
    /* access modifiers changed from: private */
    public boolean searched;
    /* access modifiers changed from: private */
    public boolean showMasterSp;
    private List<String> sldIds;
    private int slideNum = 1;
    /* access modifiers changed from: private */
    public PackagePart slidePart;
    /* access modifiers changed from: private */
    public ZipPackage zipPackage;

    class PresentationSaxHandler implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        PresentationSaxHandler() {
        }

        public void onEnd(ElementPath elementPath) {
            if (!PPTXReader.this.abortReader) {
                Element current = elementPath.getCurrent();
                String name = current.getName();
                try {
                    if (name.equals("sldMasterIdLst")) {
                        PPTXReader.this.processMasterPart(current);
                    } else if (name.equals("defaultTextStyle")) {
                        PPTXReader.this.processDefaultTextStyle(current);
                    } else if (name.equals("sldSz")) {
                        PPTXReader.this.setPageSize(current);
                    } else if (name.equals("sldId")) {
                        PPTXReader.this.addSlideID(current);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                current.detach();
                return;
            }
            throw new AbortReaderError("abort Reader");
        }
    }

    class SlideSaxHandler implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        SlideSaxHandler() {
        }

        public void onEnd(ElementPath elementPath) {
            String attributeValue;
            if (!PPTXReader.this.abortReader) {
                Element current = elementPath.getCurrent();
                try {
                    if ("bg".equals(current.getName())) {
                        PPTXReader pPTXReader = PPTXReader.this;
                        pPTXReader.processBackground(pPTXReader.slidePart, PPTXReader.this.pgMaster, PPTXReader.this.pgLayout, PPTXReader.this.pgSlide, current);
                    } else {
                        boolean z = false;
                        if ("sld".equals(current.getName())) {
                            if (current.attribute("showMasterSp") != null && (attributeValue = current.attributeValue("showMasterSp")) != null && attributeValue.length() > 0 && Integer.valueOf(attributeValue).intValue() == 0) {
                                boolean unused = PPTXReader.this.showMasterSp = false;
                            }
                        } else if ("par".equals(current.getName())) {
                            PPTXReader pPTXReader2 = PPTXReader.this;
                            pPTXReader2.processSlideShow(pPTXReader2.pgSlide, current);
                        } else if ("transition".equals(current.getName())) {
                            PGSlide access$700 = PPTXReader.this.pgSlide;
                            if (current.elements().size() > 0) {
                                z = true;
                            }
                            access$700.setTransition(z);
                        } else {
                            ShapeManage.instance().processShape(PPTXReader.this.control, PPTXReader.this.zipPackage, PPTXReader.this.slidePart, PPTXReader.this.pgModel, PPTXReader.this.pgMaster, PPTXReader.this.pgLayout, PPTXReader.this.defaultStyle, PPTXReader.this.pgSlide, (byte) 2, current, (GroupShape) null, 1.0f, 1.0f);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                current.detach();
                return;
            }
            throw new AbortReaderError("abort Reader");
        }
    }

    class PresentationSaxHandler_Search implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        PresentationSaxHandler_Search() {
        }

        /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x009d */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onEnd(com.app.office.fc.dom4j.ElementPath r8) {
            /*
                r7 = this;
                com.app.office.fc.ppt.PPTXReader r0 = com.app.office.fc.ppt.PPTXReader.this
                boolean r0 = r0.abortReader
                if (r0 != 0) goto L_0x00b0
                com.app.office.fc.dom4j.Element r8 = r8.getCurrent()
                java.lang.String r0 = r8.getName()
                java.lang.String r1 = "sldId"
                boolean r0 = r1.equals(r0)
                if (r0 == 0) goto L_0x00ac
                com.app.office.fc.ppt.PPTXReader r0 = com.app.office.fc.ppt.PPTXReader.this
                r1 = 0
                boolean unused = r0.note = r1
                com.app.office.fc.ppt.PPTXReader r0 = com.app.office.fc.ppt.PPTXReader.this
                com.app.office.fc.openxml4j.opc.ZipPackage r0 = r0.zipPackage
                com.app.office.fc.ppt.PPTXReader r2 = com.app.office.fc.ppt.PPTXReader.this
                com.app.office.fc.openxml4j.opc.PackagePart r2 = r2.packagePart
                r3 = 1
                com.app.office.fc.dom4j.Attribute r4 = r8.attribute((int) r3)
                java.lang.String r4 = r4.getValue()
                com.app.office.fc.openxml4j.opc.PackageRelationship r2 = r2.getRelationship(r4)
                java.net.URI r2 = r2.getTargetURI()
                com.app.office.fc.openxml4j.opc.PackagePart r0 = r0.getPart((java.net.URI) r2)
                if (r0 == 0) goto L_0x00ac
                com.app.office.fc.dom4j.io.SAXReader r2 = new com.app.office.fc.dom4j.io.SAXReader
                r2.<init>()
                java.io.InputStream r4 = r0.getInputStream()     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                com.app.office.fc.ppt.PPTXReader$SlideNoteSaxHandler_Search r5 = new com.app.office.fc.ppt.PPTXReader$SlideNoteSaxHandler_Search     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                com.app.office.fc.ppt.PPTXReader r6 = com.app.office.fc.ppt.PPTXReader.this     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                r5.<init>()     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                java.lang.String r6 = "/sld/cSld/spTree/sp"
                r2.addHandler(r6, r5)     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                java.lang.String r6 = "/sld/cSld/spTree/grpSp"
                r2.addHandler(r6, r5)     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                r2.read((java.io.InputStream) r4)     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                r4.close()     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                java.lang.String r4 = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/notesSlide"
                com.app.office.fc.openxml4j.opc.PackageRelationshipCollection r0 = r0.getRelationshipsByType(r4)     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                com.app.office.fc.openxml4j.opc.PackageRelationship r0 = r0.getRelationship(r1)     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                if (r0 == 0) goto L_0x0099
                com.app.office.fc.ppt.PPTXReader r1 = com.app.office.fc.ppt.PPTXReader.this     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                com.app.office.fc.openxml4j.opc.ZipPackage r1 = r1.zipPackage     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                java.net.URI r0 = r0.getTargetURI()     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                com.app.office.fc.openxml4j.opc.PackagePart r0 = r1.getPart((java.net.URI) r0)     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                com.app.office.fc.ppt.PPTXReader r1 = com.app.office.fc.ppt.PPTXReader.this     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                boolean unused = r1.note = r3     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                java.io.InputStream r0 = r0.getInputStream()     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                r2.resetHandlers()     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                java.lang.String r1 = "/notes/cSld/spTree/sp"
                r2.addHandler(r1, r5)     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                r2.read((java.io.InputStream) r0)     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                r0.close()     // Catch:{ StopReaderError -> 0x009d, Exception -> 0x0095 }
                goto L_0x0099
            L_0x0093:
                r8 = move-exception
                goto L_0x00a8
            L_0x0095:
                r0 = move-exception
                r0.printStackTrace()     // Catch:{ all -> 0x0093 }
            L_0x0099:
                r2.resetHandlers()
                goto L_0x00ac
            L_0x009d:
                r8.detach()     // Catch:{ all -> 0x0093 }
                com.app.office.system.StopReaderError r8 = new com.app.office.system.StopReaderError     // Catch:{ all -> 0x0093 }
                java.lang.String r0 = "stop"
                r8.<init>(r0)     // Catch:{ all -> 0x0093 }
                throw r8     // Catch:{ all -> 0x0093 }
            L_0x00a8:
                r2.resetHandlers()
                throw r8
            L_0x00ac:
                r8.detach()
                return
            L_0x00b0:
                com.app.office.system.AbortReaderError r8 = new com.app.office.system.AbortReaderError
                java.lang.String r0 = "abort Reader"
                r8.<init>(r0)
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.PPTXReader.PresentationSaxHandler_Search.onEnd(com.app.office.fc.dom4j.ElementPath):void");
        }
    }

    class SlideNoteSaxHandler_Search implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        SlideNoteSaxHandler_Search() {
        }

        public void onEnd(ElementPath elementPath) {
            if (!PPTXReader.this.abortReader) {
                Element current = elementPath.getCurrent();
                PPTXReader pPTXReader = PPTXReader.this;
                pPTXReader.searchContentForText(current, pPTXReader.key);
                current.detach();
                if (PPTXReader.this.searched) {
                    throw new StopReaderError("stop");
                }
                return;
            }
            throw new AbortReaderError("abort Reader");
        }
    }

    public PPTXReader(IControl iControl, String str) {
        this.control = iControl;
        this.filePath = str;
    }

    public Object getModel() throws Exception {
        PGModel pGModel = this.pgModel;
        if (pGModel != null) {
            return pGModel;
        }
        initPackagePart();
        this.pgModel = new PGModel();
        processPresentation();
        return this.pgModel;
    }

    public void initPackagePart() throws Exception {
        ZipPackage zipPackage2 = new ZipPackage(this.filePath);
        this.zipPackage = zipPackage2;
        PackageRelationship relationship = zipPackage2.getRelationshipsByType(PackageRelationshipTypes.CORE_DOCUMENT).getRelationship(0);
        if (relationship == null || !relationship.getTargetURI().toString().equals("/ppt/presentation.xml")) {
            throw new Exception("Format error");
        }
        this.packagePart = this.zipPackage.getPart(relationship);
    }

    private void processPresentation() throws Exception {
        PackagePart packagePart2;
        String attributeValue;
        SAXReader sAXReader = new SAXReader();
        try {
            InputStream inputStream = this.packagePart.getInputStream();
            PresentationSaxHandler presentationSaxHandler = new PresentationSaxHandler();
            sAXReader.addHandler("/presentation/sldMasterIdLst", presentationSaxHandler);
            sAXReader.addHandler("/presentation/defaultTextStyle", presentationSaxHandler);
            sAXReader.addHandler("/presentation/sldSz", presentationSaxHandler);
            sAXReader.addHandler("/presentation/sldIdLst/sldId", presentationSaxHandler);
            Element rootElement = sAXReader.read(inputStream).getRootElement();
            if (!(rootElement == null || rootElement.attribute("firstSlideNum") == null || (attributeValue = rootElement.attributeValue("firstSlideNum")) == null || attributeValue.length() <= 0)) {
                this.pgModel.setSlideNumberOffset(Integer.valueOf(attributeValue).intValue() - 1);
            }
            inputStream.close();
            List<String> list = this.sldIds;
            if (list != null) {
                this.pgModel.setSlideCount(list.size());
                ArrayList<PackagePart> partsByContentType = this.zipPackage.getPartsByContentType(ContentTypes.TABLE_STYLE_PART);
                if (partsByContentType.size() > 0 && (packagePart2 = partsByContentType.get(0)) != null) {
                    Style style = StyleManage.instance().getStyle(this.defaultStyle.getStyle(1));
                    int i = 12;
                    if (style != null) {
                        int attribute = style.getAttrbuteSet().getAttribute(1);
                        if (attribute >= 0) {
                            i = attribute;
                        }
                    }
                    TableStyleReader.instance().read(this.pgModel, packagePart2, i);
                }
                processSlidePart();
                sAXReader.resetHandlers();
                return;
            }
            throw new Exception("Format error");
        } catch (Exception e) {
            throw e;
        } catch (Throwable th) {
            sAXReader.resetHandlers();
            throw th;
        }
    }

    private void processDefaultFontColor(Element element, int i) {
        Element element2;
        Element element3;
        Element element4;
        String attributeValue;
        if (element != null && (element2 = element.element("defRPr")) != null && (element3 = element2.element("solidFill")) != null && (element4 = element3.element("schemeClr")) != null && element4.attribute("val") != null && (attributeValue = element4.attributeValue("val")) != null && attributeValue.length() > 0) {
            this.defaultStyle.addDefaultFontColor(i, attributeValue);
        }
    }

    /* access modifiers changed from: private */
    public void processDefaultTextStyle(Element element) {
        Map<String, PGMaster> map = this.nameMaster;
        if (map != null) {
            Iterator<String> it = map.keySet().iterator();
            if (it.hasNext()) {
                StyleReader.instance().setStyleIndex(1);
                this.defaultStyle = StyleReader.instance().getStyles(this.control, this.nameMaster.get(it.next()), (Element) null, element);
            }
        }
        if (element != null && this.defaultStyle != null) {
            Element element2 = element.element("lvl1pPr");
            if (element2 != null) {
                processDefaultFontColor(element2, 1);
            }
            Element element3 = element.element("lvl2pPr");
            if (element3 != null) {
                processDefaultFontColor(element3, 2);
            }
            Element element4 = element.element("lvl3pPr");
            if (element4 != null) {
                processDefaultFontColor(element4, 3);
            }
            Element element5 = element.element("lvl4pPr");
            if (element5 != null) {
                processDefaultFontColor(element5, 4);
            }
            Element element6 = element.element("lvl5pPr");
            if (element6 != null) {
                processDefaultFontColor(element6, 5);
            }
            Element element7 = element.element("lvl6pPr");
            if (element7 != null) {
                processDefaultFontColor(element7, 6);
            }
            Element element8 = element.element("lvl7pPr");
            if (element8 != null) {
                processDefaultFontColor(element8, 7);
            }
            Element element9 = element.element("lvl8pPr");
            if (element9 != null) {
                processDefaultFontColor(element9, 8);
            }
            Element element10 = element.element("lvl9pPr");
            if (element10 != null) {
                processDefaultFontColor(element10, 9);
            }
        }
    }

    public void processMasterPart(Element element) throws Exception {
        List elements = element.elements("sldMasterId");
        if (elements.size() > 0) {
            int i = 0;
            Element element2 = (Element) elements.get(0);
            if (!this.abortReader) {
                if (element2.attributeCount() > 1) {
                    i = 1;
                }
                PackagePart part = this.zipPackage.getPart(this.packagePart.getRelationship(element2.attribute(i).getValue()).getTargetURI());
                this.nameMaster.put(part.getPartName().getName(), MasterReader.instance().getMasterData(this.control, this.zipPackage, part, this.pgModel));
            }
        }
    }

    /* access modifiers changed from: private */
    public void addSlideID(Element element) {
        if (this.sldIds == null) {
            this.sldIds = new ArrayList();
        }
        this.sldIds.add(element.attribute(1).getValue());
    }

    public void processSlidePart() throws Exception {
        if (this.sldIds.size() > 0) {
            int min = Math.min(this.sldIds.size(), 2);
            for (int i = 0; i < min && !this.abortReader; i++) {
                List<String> list = this.sldIds;
                int i2 = this.currentReaderIndex;
                this.currentReaderIndex = i2 + 1;
                processSlide(list.get(i2));
            }
            if (!isReaderFinish()) {
                new BackReaderThread(this, this.control).start();
                return;
            }
            return;
        }
        throw new Exception("Format error");
    }

    public boolean isReaderFinish() {
        if (this.pgModel == null || this.sldIds == null || this.abortReader || this.pgModel.getSlideCount() == 0 || this.currentReaderIndex >= this.sldIds.size()) {
            return true;
        }
        return false;
    }

    public void backReader() throws Exception {
        try {
            List<String> list = this.sldIds;
            int i = this.currentReaderIndex;
            this.currentReaderIndex = i + 1;
            processSlide(list.get(i));
            this.control.actionEvent(EventConstant.APP_COUNT_PAGES_CHANGE_ID, (Object) null);
        } catch (Error e) {
            this.control.getSysKit().getErrorKit().writerLog(e, true);
        }
    }

    private void processSlide(String str) throws Exception {
        this.showMasterSp = true;
        PackagePart part = this.zipPackage.getPart(this.packagePart.getRelationship(str).getTargetURI());
        this.slidePart = part;
        PackagePart part2 = this.zipPackage.getPart(part.getRelationshipsByType(PackageRelationshipTypes.LAYOUT_PART).getRelationship(0).getTargetURI());
        PackageRelationship relationship = part2.getRelationshipsByType(PackageRelationshipTypes.SLIDE_MASTER).getRelationship(0);
        PGMaster pGMaster = this.nameMaster.get(relationship.getTargetURI().toString());
        this.pgMaster = pGMaster;
        if (pGMaster == null) {
            PackagePart part3 = this.zipPackage.getPart(relationship.getTargetURI());
            this.pgMaster = MasterReader.instance().getMasterData(this.control, this.zipPackage, part3, this.pgModel);
            this.nameMaster.put(part3.getPartName().getName(), this.pgMaster);
        }
        PGLayout pGLayout = this.nameLayout.get(part2.getPartName().getName());
        this.pgLayout = pGLayout;
        if (pGLayout == null) {
            this.pgLayout = LayoutReader.instance().getLayouts(this.control, this.zipPackage, part2, this.pgModel, this.pgMaster, (PGStyle) null);
            this.nameLayout.put(part2.getPartName().getName(), this.pgLayout);
        }
        PGSlide pGSlide = new PGSlide();
        this.pgSlide = pGSlide;
        pGSlide.setSlideType(2);
        PackageRelationshipCollection relationshipsByType = this.slidePart.getRelationshipsByType(PackageRelationshipTypes.DIAGRAM_DATA);
        if (relationshipsByType != null && relationshipsByType.size() > 0) {
            int size = relationshipsByType.size();
            for (int i = 0; i < size; i++) {
                PackageRelationship relationship2 = relationshipsByType.getRelationship(i);
                PGSlide pGSlide2 = this.pgSlide;
                String id = relationship2.getId();
                SmartArtReader instance = SmartArtReader.instance();
                IControl iControl = this.control;
                ZipPackage zipPackage2 = this.zipPackage;
                pGSlide2.addSmartArt(id, instance.read(iControl, zipPackage2, this.pgModel, this.pgMaster, this.pgLayout, this.pgSlide, this.slidePart, zipPackage2.getPart(relationship2.getTargetURI())));
            }
        }
        HyperlinkReader.instance().getHyperlinkList(this.control, this.slidePart);
        SAXReader sAXReader = new SAXReader();
        try {
            InputStream inputStream = this.slidePart.getInputStream();
            SlideSaxHandler slideSaxHandler = new SlideSaxHandler();
            sAXReader.addHandler("/sld/cSld/bg", slideSaxHandler);
            sAXReader.addHandler("/sld/cSld/spTree/sp", slideSaxHandler);
            sAXReader.addHandler("/sld/cSld/spTree/cxnSp", slideSaxHandler);
            sAXReader.addHandler("/sld/cSld/spTree/pic", slideSaxHandler);
            sAXReader.addHandler("/sld/cSld/spTree/graphicFrame", slideSaxHandler);
            sAXReader.addHandler("/sld/cSld/spTree/grpSp", slideSaxHandler);
            sAXReader.addHandler("/sld/cSld/spTree/AlternateContent", slideSaxHandler);
            sAXReader.addHandler("/sld/timing/tnLst/par/cTn/childTnLst/seq/cTn/childTnLst/par", slideSaxHandler);
            sAXReader.addHandler("/sld/timing/bldLst/bldP", slideSaxHandler);
            sAXReader.addHandler("/sld/transition", slideSaxHandler);
            sAXReader.addHandler("/sld/AlternateContent/Choice/transition", slideSaxHandler);
            sAXReader.addHandler("/sld", slideSaxHandler);
            sAXReader.read(inputStream);
            inputStream.close();
            processBackground(this.slidePart, this.pgMaster, this.pgLayout, this.pgSlide, (Element) null);
            processGroupShape(this.pgSlide);
            PGSlide pGSlide3 = this.pgSlide;
            int i2 = this.slideNum;
            this.slideNum = i2 + 1;
            pGSlide3.setSlideNo(i2);
            processNotes(this.slidePart, this.pgSlide);
            if (this.showMasterSp && this.pgLayout.isAddShapes()) {
                this.pgSlide.setMasterSlideIndex(this.pgMaster.getSlideMasterIndex());
            }
            this.pgSlide.setLayoutSlideIndex(this.pgLayout.getSlideMasterIndex());
            this.pgModel.appendSlide(this.pgSlide);
            this.pgSlide = null;
            this.pgLayout = null;
            this.pgMaster = null;
            this.slidePart = null;
            PictureReader.instance().dispose();
            HyperlinkReader.instance().disposs();
        } finally {
            sAXReader.resetHandlers();
        }
    }

    private void processGroupShape(PGSlide pGSlide) {
        Map<Integer, List<Integer>> groupShape = pGSlide.getGroupShape();
        if (groupShape != null) {
            int shapeCount = pGSlide.getShapeCount();
            for (int i = 0; i < shapeCount; i++) {
                IShape shape = pGSlide.getShape(i);
                shape.setGroupShapeID(getGroupShapeID(shape.getShapeID(), groupShape));
            }
        }
    }

    private int getGroupShapeID(int i, Map<Integer, List<Integer>> map) {
        for (Integer intValue : map.keySet()) {
            int intValue2 = intValue.intValue();
            List list = map.get(Integer.valueOf(intValue2));
            if (list != null && list.contains(Integer.valueOf(i))) {
                return intValue2;
            }
        }
        return -1;
    }

    private void processGroupShapeID(Map<Integer, List<Integer>> map) {
        ArrayList arrayList = null;
        boolean z = false;
        for (Integer intValue : map.keySet()) {
            int intValue2 = intValue.intValue();
            List list = map.get(Integer.valueOf(intValue2));
            int size = list.size();
            ArrayList arrayList2 = null;
            for (int i = 0; i < size; i++) {
                List list2 = map.get(list.get(i));
                if (list2 != null && list2.size() > 0) {
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                    }
                    arrayList2.addAll(list2);
                }
            }
            if (arrayList2 == null || arrayList2.size() <= 0) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(Integer.valueOf(intValue2));
            } else {
                list.addAll(arrayList2);
                z = true;
            }
        }
        if (z) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                map.remove(arrayList.get(i2));
            }
            processGroupShapeID(map);
        }
    }

    /* access modifiers changed from: private */
    public void processBackground(PackagePart packagePart2, PGMaster pGMaster, PGLayout pGLayout, PGSlide pGSlide, Element element) throws Exception {
        if (element == null && pGSlide.getBackgroundAndFill() == null) {
            BackgroundAndFill backgroundAndFill = pGLayout.getBackgroundAndFill();
            if (backgroundAndFill == null) {
                backgroundAndFill = pGMaster.getBackgroundAndFill();
            }
            pGSlide.setBackgroundAndFill(backgroundAndFill);
        } else if (element != null) {
            pGSlide.setBackgroundAndFill(BackgroundReader.instance().getBackground(this.control, this.zipPackage, packagePart2, pGMaster, element));
        }
    }

    public void setPageSize(Element element) {
        float f;
        float f2 = 0.0f;
        if (element != null) {
            f2 = (Float.parseFloat(element.attributeValue("cx")) * 96.0f) / 914400.0f;
            f = (Float.parseFloat(element.attributeValue("cy")) * 96.0f) / 914400.0f;
        } else {
            f = 0.0f;
        }
        this.pgModel.setPageSize(new Dimension((int) f2, (int) f));
    }

    private void processNotes(PackagePart packagePart2, PGSlide pGSlide) throws Exception {
        String notes;
        PackageRelationship relationship = packagePart2.getRelationshipsByType(PackageRelationshipTypes.NOTES_SLIDE).getRelationship(0);
        if (relationship != null) {
            PackagePart part = this.zipPackage.getPart(relationship.getTargetURI());
            SAXReader sAXReader = new SAXReader();
            InputStream inputStream = part.getInputStream();
            Element rootElement = sAXReader.read(inputStream).getRootElement();
            if (!(rootElement == null || (notes = ReaderKit.instance().getNotes(rootElement)) == null)) {
                pGSlide.setNotes(new PGNotes(notes));
            }
            inputStream.close();
        }
    }

    /* access modifiers changed from: private */
    public void processSlideShow(PGSlide pGSlide, Element element) {
        try {
            List<Element> elements = element.element("cTn").element("childTnLst").elements("par");
            if (elements.size() >= 1) {
                for (Element element2 : elements) {
                    for (Element element3 : element2.element("cTn").element("childTnLst").elements("par")) {
                        processAnimation(pGSlide, element3.element("cTn"));
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    private void processAnimation(PGSlide pGSlide, Element element) {
        Element element2;
        String attributeValue = element.attributeValue("presetClass");
        Element element3 = element.element("childTnLst");
        byte b = 0;
        if (element3.element("set") != null) {
            element2 = element3.element("set").element("cBhvr").element("tgtEl").element("spTgt");
        } else {
            element2 = ((Element) element3.elements().get(0)).element("cBhvr").element("tgtEl").element("spTgt");
        }
        String attributeValue2 = element2.attributeValue("spid");
        if (!attributeValue.equals("entr")) {
            if (attributeValue.equals("emph")) {
                b = 1;
            } else if (attributeValue.equals("exit")) {
                b = 2;
            } else {
                return;
            }
        }
        if (element2.element("txEl") != null && element2.element("txEl").element("pRg") != null) {
            Element element4 = element2.element("txEl").element("pRg");
            pGSlide.addShapeAnimation(new ShapeAnimation(Integer.parseInt(attributeValue2), b, Integer.parseInt(element4.attributeValue("st")), Integer.parseInt(element4.attributeValue("end"))));
        } else if (element2.element("bg") != null) {
            pGSlide.addShapeAnimation(new ShapeAnimation(Integer.parseInt(attributeValue2), b, -1, -1));
        } else {
            pGSlide.addShapeAnimation(new ShapeAnimation(Integer.parseInt(attributeValue2), b));
        }
    }

    public boolean searchContent(File file, String str) throws Exception {
        this.searched = false;
        this.key = str;
        ZipPackage zipPackage2 = new ZipPackage(file.getAbsolutePath());
        this.zipPackage = zipPackage2;
        this.packagePart = this.zipPackage.getPart(zipPackage2.getRelationshipsByType(PackageRelationshipTypes.CORE_DOCUMENT).getRelationship(0));
        SAXReader sAXReader = new SAXReader();
        try {
            InputStream inputStream = this.packagePart.getInputStream();
            sAXReader.addHandler("/presentation/sldIdLst/sldId", new PresentationSaxHandler_Search());
            sAXReader.read(inputStream);
            inputStream.close();
        } catch (StopReaderError unused) {
        } catch (Throwable th) {
            sAXReader.resetHandlers();
            throw th;
        }
        sAXReader.resetHandlers();
        this.key = null;
        this.zipPackage = null;
        this.packagePart = null;
        return this.searched;
    }

    public boolean searchContentForText(Element element, String str) {
        Element element2;
        String name = element.getName();
        if (name.equals("sp")) {
            StringBuilder sb = new StringBuilder();
            if ((!this.note || "body".equals(ReaderKit.instance().getPlaceholderType(element))) && (element2 = element.element("txBody")) != null) {
                for (Element elements : element2.elements("p")) {
                    for (Element element3 : elements.elements("r")) {
                        Element element4 = element3.element("t");
                        if (element4 != null) {
                            sb.append(element4.getText());
                        }
                    }
                    if (sb.indexOf(str) >= 0) {
                        this.key = null;
                        this.zipPackage = null;
                        this.packagePart = null;
                        this.searched = true;
                        return true;
                    }
                    sb.delete(0, sb.length());
                }
            }
        } else if (name.equals("grpSp")) {
            Iterator elementIterator = element.elementIterator();
            while (elementIterator.hasNext()) {
                if (searchContentForText((Element) elementIterator.next(), str)) {
                    this.key = null;
                    this.zipPackage = null;
                    this.packagePart = null;
                    this.searched = true;
                    return true;
                }
            }
        }
        return false;
    }

    public void dispose() {
        PGModel pGModel;
        List<String> list;
        if (isReaderFinish()) {
            super.dispose();
            if (this.abortReader && (pGModel = this.pgModel) != null && pGModel.getSlideCount() < 2 && (list = this.sldIds) != null && list.size() > 0) {
                this.pgModel.dispose();
            }
            this.pgModel = null;
            this.filePath = null;
            this.zipPackage = null;
            this.packagePart = null;
            Map<String, PGLayout> map = this.nameLayout;
            if (map != null) {
                for (String str : map.keySet()) {
                    this.nameLayout.get(str).disposs();
                }
                this.nameLayout.clear();
                this.nameLayout = null;
            }
            Map<String, PGMaster> map2 = this.nameMaster;
            if (map2 != null) {
                map2.clear();
                this.nameMaster = null;
            }
            List<String> list2 = this.sldIds;
            if (list2 != null) {
                list2.clear();
                this.sldIds = null;
            }
            PGStyle pGStyle = this.defaultStyle;
            if (pGStyle != null) {
                pGStyle.dispose();
                this.defaultStyle = null;
            }
            this.key = null;
            this.pgSlide = null;
            this.pgLayout = null;
            this.pgMaster = null;
            this.slidePart = null;
            HyperlinkReader.instance().disposs();
            PictureReader.instance().dispose();
            LayoutReader.instance().dispose();
            MasterReader.instance().dispose();
            RunAttr.instance().dispose();
            BulletNumberManage.instance().dispose();
        }
    }
}
