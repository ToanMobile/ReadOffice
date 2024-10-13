package com.app.office.fc.ppt;

import android.graphics.Paint;
import com.app.office.common.PaintKit;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import com.app.office.common.shape.GroupShape;
import com.app.office.common.shape.IShape;
import com.app.office.common.shape.LineShape;
import com.app.office.common.shape.TableShape;
import com.app.office.common.shape.TextBox;
import com.app.office.constant.EventConstant;
import com.app.office.fc.FCKit;
import com.app.office.fc.hslf.HSLFSlideShow;
import com.app.office.fc.hslf.model.AutoShape;
import com.app.office.fc.hslf.model.HeadersFooters;
import com.app.office.fc.hslf.model.Hyperlink;
import com.app.office.fc.hslf.model.Notes;
import com.app.office.fc.hslf.model.Shape;
import com.app.office.fc.hslf.model.ShapeGroup;
import com.app.office.fc.hslf.model.Sheet;
import com.app.office.fc.hslf.model.SimpleShape;
import com.app.office.fc.hslf.model.Slide;
import com.app.office.fc.hslf.model.SlideMaster;
import com.app.office.fc.hslf.model.Table;
import com.app.office.fc.hslf.model.TableCell;
import com.app.office.fc.hslf.model.TextShape;
import com.app.office.fc.hslf.model.TitleMaster;
import com.app.office.fc.hslf.record.BinaryTagDataBlob;
import com.app.office.fc.hslf.record.ClientVisualElementContainer;
import com.app.office.fc.hslf.record.DocumentAtom;
import com.app.office.fc.hslf.record.OEPlaceholderAtom;
import com.app.office.fc.hslf.record.PositionDependentRecordContainer;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.SlideAtom;
import com.app.office.fc.hslf.record.SlideProgBinaryTagContainer;
import com.app.office.fc.hslf.record.SlideProgTagsContainer;
import com.app.office.fc.hslf.record.SlideShowSlideInfoAtom;
import com.app.office.fc.hslf.record.TextRulerAtom;
import com.app.office.fc.hslf.record.TimeAnimateBehaviorContainer;
import com.app.office.fc.hslf.record.TimeBehaviorContainer;
import com.app.office.fc.hslf.record.TimeColorBehaviorContainer;
import com.app.office.fc.hslf.record.TimeCommandBehaviorContainer;
import com.app.office.fc.hslf.record.TimeEffectBehaviorContainer;
import com.app.office.fc.hslf.record.TimeMotionBehaviorContainer;
import com.app.office.fc.hslf.record.TimeNodeAttributeContainer;
import com.app.office.fc.hslf.record.TimeNodeContainer;
import com.app.office.fc.hslf.record.TimeRotationBehaviorContainer;
import com.app.office.fc.hslf.record.TimeScaleBehaviorContainer;
import com.app.office.fc.hslf.record.TimeSetBehaviorContainer;
import com.app.office.fc.hslf.record.TimeVariant;
import com.app.office.fc.hslf.record.VisualShapeAtom;
import com.app.office.fc.hslf.usermodel.RichTextRun;
import com.app.office.fc.hslf.usermodel.SlideShow;
import com.app.office.fc.ppt.bulletnumber.BulletNumberManage;
import com.app.office.java.awt.Color;
import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.Rectanglef;
import com.app.office.java.awt.geom.Rectangle2D;
import com.app.office.pg.animate.ShapeAnimation;
import com.app.office.pg.model.PGModel;
import com.app.office.pg.model.PGNotes;
import com.app.office.pg.model.PGSlide;
import com.app.office.simpletext.font.FontTypefaceManage;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.LeafElement;
import com.app.office.simpletext.model.ParagraphElement;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.ss.util.format.NumericFormatter;
import com.app.office.system.AbstractReader;
import com.app.office.system.BackReaderThread;
import com.app.office.system.IControl;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.text.Typography;

public class PPTReader extends AbstractReader {
    public static final int DEFAULT_CELL_HEIGHT = 40;
    public static final int DEFAULT_CELL_WIDTH = 100;
    public static final int FIRST_READ_SLIDE_NUM = 2;
    public static final float POINT_PER_LINE_PER_FONTSIZE = 1.2f;
    private int currentReaderIndex;
    private String filePath;
    private boolean hasProcessedMasterDateTime;
    private boolean hasProcessedMasterFooter;
    private boolean hasProcessedMasterSlideNumber;
    private boolean isGetThumbnail;
    private int maxFontSize;
    private PGModel model;
    private int number;
    private int offset;
    private HeadersFooters poiHeadersFooters;
    private SlideShow poiSlideShow;
    private Map<Integer, Integer> slideMasterIndexs;
    private boolean tableShape;
    private Map<Integer, Integer> titleMasterIndexs;

    public PPTReader(IControl iControl, String str) {
        this(iControl, str, false);
    }

    public PPTReader(IControl iControl, String str, boolean z) {
        this.number = 1;
        this.filePath = str;
        this.control = iControl;
        this.isGetThumbnail = z;
    }

    public Object getModel() throws Exception {
        PGModel pGModel = this.model;
        if (pGModel != null) {
            return pGModel;
        }
        this.poiSlideShow = new SlideShow(new HSLFSlideShow(this.control, this.filePath), this.isGetThumbnail);
        this.model = new PGModel();
        Dimension pageSize = this.poiSlideShow.getPageSize();
        pageSize.width = (int) (((float) pageSize.width) * 1.3333334f);
        pageSize.height = (int) (((float) pageSize.height) * 1.3333334f);
        this.model.setPageSize(pageSize);
        DocumentAtom documentAtom = this.poiSlideShow.getDocumentRecord().getDocumentAtom();
        if (documentAtom != null) {
            this.model.setSlideNumberOffset(documentAtom.getFirstSlideNum() - 1);
            this.model.setOmitTitleSlide(documentAtom.getOmitTitlePlace());
        }
        int slideCount = this.poiSlideShow.getSlideCount();
        this.model.setSlideCount(slideCount);
        if (slideCount != 0) {
            this.poiHeadersFooters = this.poiSlideShow.getSlideHeadersFooters();
            int min = Math.min(slideCount, 2);
            for (int i = 0; i < min && !this.abortReader; i++) {
                SlideShow slideShow = this.poiSlideShow;
                int i2 = this.currentReaderIndex;
                this.currentReaderIndex = i2 + 1;
                processSlide(slideShow.getSlide(i2));
            }
            if (!isReaderFinish() && !this.isGetThumbnail) {
                new BackReaderThread(this, this.control).start();
            }
            return this.model;
        }
        throw new Exception("Format error");
    }

    public boolean isReaderFinish() {
        if (this.model == null || this.poiSlideShow == null || this.abortReader || this.model.getSlideCount() == 0 || this.currentReaderIndex >= this.poiSlideShow.getSlideCount()) {
            return true;
        }
        return false;
    }

    public void backReader() throws Exception {
        SlideShow slideShow = this.poiSlideShow;
        int i = this.currentReaderIndex;
        this.currentReaderIndex = i + 1;
        processSlide(slideShow.getSlide(i));
        if (!this.isGetThumbnail) {
            this.control.actionEvent(EventConstant.APP_COUNT_PAGES_CHANGE_ID, (Object) null);
        }
    }

    private boolean isTitleSlide(Slide slide) {
        int placeholderId;
        SlideAtom slideAtom = slide.getSlideRecord().getSlideAtom();
        int geometryType = (slideAtom == null || slideAtom.getSSlideLayoutAtom() == null) ? 0 : slideAtom.getSSlideLayoutAtom().getGeometryType();
        if (geometryType == 0) {
            return true;
        }
        if (geometryType != 16) {
            return false;
        }
        for (Shape shape : slide.getShapes()) {
            if (!(shape instanceof TextShape)) {
                return false;
            }
            OEPlaceholderAtom placeholderAtom = ((TextShape) shape).getPlaceholderAtom();
            if (placeholderAtom != null && (placeholderId = placeholderAtom.getPlaceholderId()) != 15 && placeholderId != 16 && placeholderId != -1) {
                return false;
            }
        }
        return true;
    }

    private void resetFlag() {
        this.hasProcessedMasterDateTime = false;
        this.hasProcessedMasterFooter = false;
        this.hasProcessedMasterSlideNumber = false;
    }

    private void processSlide(Slide slide) {
        PGSlide slideMaster;
        TextBox textBox;
        TextBox textBox2;
        TextBox textBox3;
        TextBox textBox4;
        TextBox textBox5;
        PGSlide pGSlide = new PGSlide();
        pGSlide.setSlideType(2);
        int i = this.number;
        this.number = i + 1;
        pGSlide.setSlideNo(i);
        if (slide.getBackground() != null) {
            pGSlide.setBackgroundAndFill(converFill(pGSlide, slide.getBackground().getFill()));
        }
        processMaster(pGSlide, slide);
        SlideAtom slideAtom = slide.getSlideRecord().getSlideAtom();
        if (!(slideAtom == null || slideAtom.getSSlideLayoutAtom() == null)) {
            pGSlide.setGeometryType(slideAtom.getSSlideLayoutAtom().getGeometryType());
        }
        resetFlag();
        boolean z = false;
        for (Shape processShape : slide.getShapes()) {
            processShape(pGSlide, (GroupShape) null, processShape, 2);
        }
        if ((!this.model.isOmitTitleSlide() || !isTitleSlide(slide)) && (slideMaster = this.model.getSlideMaster(pGSlide.getMasterIndexs()[0])) != null) {
            HeadersFooters slideHeadersFooters = slide.getSlideHeadersFooters();
            if (slideHeadersFooters != null) {
                pGSlide.setShowMasterHeadersFooters(false);
                if (slideHeadersFooters.isSlideNumberVisible() && !this.hasProcessedMasterSlideNumber && (textBox5 = (TextBox) slideMaster.getTextboxByPlaceHolderID(8)) != null) {
                    pGSlide.appendShapes(processCurrentSlideHeadersFooters(textBox5, String.valueOf(pGSlide.getSlideNo() + this.model.getSlideNumberOffset())));
                }
                if (!this.hasProcessedMasterFooter && slideHeadersFooters.isFooterVisible() && slideHeadersFooters.getFooterText() != null && (textBox4 = (TextBox) slideMaster.getTextboxByPlaceHolderID(9)) != null) {
                    pGSlide.appendShapes(processCurrentSlideHeadersFooters(textBox4, slideHeadersFooters.getFooterText()));
                }
                if (!this.hasProcessedMasterDateTime && slideHeadersFooters.isUserDateVisible() && slideHeadersFooters.getDateTimeText() != null) {
                    TextBox textBox6 = (TextBox) slideMaster.getTextboxByPlaceHolderID(7);
                    if (textBox6 != null) {
                        pGSlide.appendShapes(processCurrentSlideHeadersFooters(textBox6, slideHeadersFooters.getDateTimeText()));
                    }
                } else if (!this.hasProcessedMasterDateTime && slideHeadersFooters.isDateTimeVisible()) {
                    String formatContents = NumericFormatter.instance().getFormatContents("yyyy/m/d", new Date(System.currentTimeMillis()));
                    TextBox textBox7 = (TextBox) slideMaster.getTextboxByPlaceHolderID(7);
                    if (!(textBox7 == null || textBox7.getElement() == null)) {
                        pGSlide.appendShapes(processCurrentSlideHeadersFooters(textBox7, formatContents));
                    }
                }
            } else {
                if (!this.hasProcessedMasterSlideNumber && this.poiHeadersFooters.isSlideNumberVisible() && (textBox3 = (TextBox) slideMaster.getTextboxByPlaceHolderID(8)) != null) {
                    pGSlide.appendShapes(processCurrentSlideHeadersFooters(textBox3, String.valueOf(pGSlide.getSlideNo() + this.model.getSlideNumberOffset())));
                }
                if (!this.hasProcessedMasterFooter && this.poiHeadersFooters.isFooterVisible() && this.poiHeadersFooters.getFooterText() != null && (textBox2 = (TextBox) slideMaster.getTextboxByPlaceHolderID(9)) != null) {
                    pGSlide.appendShapes(textBox2);
                }
                if (!this.hasProcessedMasterDateTime && (((this.poiHeadersFooters.getDateTimeText() != null && this.poiHeadersFooters.isUserDateVisible()) || this.poiHeadersFooters.isDateTimeVisible()) && (textBox = (TextBox) slideMaster.getTextboxByPlaceHolderID(7)) != null)) {
                    pGSlide.appendShapes(textBox);
                }
            }
        }
        processNotes(pGSlide, slide.getNotesSheet());
        processGroupShape(pGSlide);
        SlideShowSlideInfoAtom slideShowSlideInfoAtom = slide.getSlideShowSlideInfoAtom();
        if (slideShowSlideInfoAtom != null && slideShowSlideInfoAtom.isValidateTransition()) {
            z = true;
        }
        pGSlide.setTransition(z);
        processSlideshow(pGSlide, slide.getSlideProgTagsContainer());
        this.model.appendSlide(pGSlide);
        if (this.abortReader || this.model.getSlideCount() == 0 || this.currentReaderIndex >= this.poiSlideShow.getSlideCount()) {
            this.slideMasterIndexs.clear();
            this.slideMasterIndexs = null;
            this.titleMasterIndexs.clear();
            this.titleMasterIndexs = null;
        }
    }

    private TextBox processCurrentSlideHeadersFooters(TextBox textBox, String str) {
        if (textBox == null || str == null || str.length() <= 0 || textBox.getElement() == null || textBox.getElement().getEndOffset() - textBox.getElement().getStartOffset() <= 0) {
            return null;
        }
        TextBox textBox2 = new TextBox();
        textBox2.setBounds(textBox.getBounds());
        textBox2.setWrapLine(textBox.isWrapLine());
        SectionElement sectionElement = new SectionElement();
        sectionElement.setStartOffset(0);
        sectionElement.setEndOffset((long) str.length());
        sectionElement.setAttribute(textBox.getElement().getAttribute().clone());
        textBox2.setElement(sectionElement);
        ParagraphElement paragraphElement = (ParagraphElement) textBox.getElement().getParaCollection().getElementForIndex(0);
        ParagraphElement paragraphElement2 = new ParagraphElement();
        paragraphElement2.setStartOffset(0);
        paragraphElement2.setEndOffset((long) str.length());
        paragraphElement2.setAttribute(paragraphElement.getAttribute().clone());
        sectionElement.appendParagraph(paragraphElement2, 0);
        LeafElement leafElement = (LeafElement) paragraphElement.getElementForIndex(0);
        String text = leafElement.getText((IDocument) null);
        if (text != null && text.contains("*")) {
            str = text.replace("*", str);
        }
        LeafElement leafElement2 = new LeafElement(str);
        leafElement2.setStartOffset(0);
        leafElement2.setEndOffset((long) str.length());
        leafElement2.setAttribute(leafElement.getAttribute().clone());
        paragraphElement2.appendLeaf(leafElement2);
        return textBox2;
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

    private void processSlideshow(PGSlide pGSlide, SlideProgTagsContainer slideProgTagsContainer) {
        Record findFirstOfType;
        Record findFirstOfType2;
        Record[] childRecords;
        List<ShapeAnimation> processAnimation;
        if (slideProgTagsContainer != null) {
            try {
                Record[] childRecords2 = slideProgTagsContainer.getChildRecords();
                if (childRecords2 != null && childRecords2.length >= 1) {
                    if (childRecords2[0] instanceof SlideProgBinaryTagContainer) {
                        Record findFirstOfType3 = ((SlideProgBinaryTagContainer) childRecords2[0]).findFirstOfType(BinaryTagDataBlob.RECORD_ID);
                        if (findFirstOfType3 != null && (findFirstOfType = ((BinaryTagDataBlob) findFirstOfType3).findFirstOfType(TimeNodeContainer.RECORD_ID)) != null && (findFirstOfType2 = ((TimeNodeContainer) findFirstOfType).findFirstOfType(TimeNodeContainer.RECORD_ID)) != null && (childRecords = ((TimeNodeContainer) findFirstOfType2).getChildRecords()) != null) {
                            for (Record record : childRecords) {
                                if ((record instanceof TimeNodeContainer) && (processAnimation = processAnimation(pGSlide, (TimeNodeContainer) record)) != null) {
                                    for (ShapeAnimation addShapeAnimation : processAnimation) {
                                        pGSlide.addShapeAnimation(addShapeAnimation);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                this.control.getSysKit().getErrorKit().writerLog(e);
            }
        }
    }

    private List<ShapeAnimation> processAnimation(PGSlide pGSlide, TimeNodeContainer timeNodeContainer) {
        ShapeAnimation processSingleAnimation;
        try {
            ArrayList arrayList = new ArrayList();
            Record[] childRecords = timeNodeContainer.getChildRecords();
            if (childRecords == null) {
                return null;
            }
            ArrayList<TimeNodeContainer> arrayList2 = new ArrayList<>();
            for (int i = 0; i < childRecords.length; i++) {
                if (childRecords[i] instanceof TimeNodeContainer) {
                    arrayList2.add((TimeNodeContainer) childRecords[i]);
                }
            }
            if (arrayList2.size() > 1) {
                for (TimeNodeContainer findFirstOfType : arrayList2) {
                    Record findFirstOfType2 = findFirstOfType.findFirstOfType(TimeNodeContainer.RECORD_ID);
                    if (!(findFirstOfType2 == null || (processSingleAnimation = processSingleAnimation(pGSlide, (TimeNodeContainer) findFirstOfType2)) == null)) {
                        arrayList.add(processSingleAnimation);
                    }
                }
            } else if (arrayList2.size() == 1) {
                arrayList2.clear();
                Record[] childRecords2 = ((TimeNodeContainer) arrayList2.get(0)).getChildRecords();
                for (int i2 = 0; i2 < childRecords2.length; i2++) {
                    if (childRecords2[i2] instanceof TimeNodeContainer) {
                        arrayList2.add((TimeNodeContainer) childRecords2[i2]);
                    }
                }
                if (arrayList2.size() == 1) {
                    ShapeAnimation processSingleAnimation2 = processSingleAnimation(pGSlide, (TimeNodeContainer) arrayList2.get(0));
                    if (processSingleAnimation2 != null) {
                        arrayList.add(processSingleAnimation2);
                    }
                } else if (arrayList2.size() > 1) {
                    for (TimeNodeContainer processSingleAnimation3 : arrayList2) {
                        ShapeAnimation processSingleAnimation4 = processSingleAnimation(pGSlide, processSingleAnimation3);
                        if (processSingleAnimation4 != null) {
                            arrayList.add(processSingleAnimation4);
                        }
                    }
                }
            }
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    private ShapeAnimation processSingleAnimation(PGSlide pGSlide, TimeNodeContainer timeNodeContainer) {
        byte b;
        Record record;
        try {
            Record[] childRecords = ((TimeNodeAttributeContainer) timeNodeContainer.findFirstOfType(TimeNodeAttributeContainer.RECORD_ID)).getChildRecords();
            int length = childRecords.length;
            int i = 0;
            int i2 = 0;
            while (true) {
                b = 1;
                if (i2 >= length) {
                    b = -1;
                    break;
                }
                Record record2 = childRecords[i2];
                if (!(record2 instanceof TimeVariant) || ((TimeVariant) record2).getAttributeType() != 11) {
                    i2++;
                } else {
                    int intValue = ((Integer) ((TimeVariant) record2).getValue()).intValue();
                    if (intValue == 1) {
                        b = 0;
                    } else if (intValue == 2) {
                        b = 2;
                    } else if (intValue != 3) {
                        return null;
                    }
                }
            }
            Record[] childRecords2 = ((TimeNodeContainer) timeNodeContainer.findFirstOfType(TimeNodeContainer.RECORD_ID)).getChildRecords();
            int length2 = childRecords2.length;
            while (true) {
                if (i >= length2) {
                    break;
                }
                record = childRecords2[i];
                if (record.getRecordType() == TimeAnimateBehaviorContainer.RECORD_ID || record.getRecordType() == TimeColorBehaviorContainer.RECORD_ID || record.getRecordType() == TimeEffectBehaviorContainer.RECORD_ID || record.getRecordType() == TimeMotionBehaviorContainer.RECORD_ID || record.getRecordType() == TimeRotationBehaviorContainer.RECORD_ID || record.getRecordType() == TimeScaleBehaviorContainer.RECORD_ID || record.getRecordType() == TimeSetBehaviorContainer.RECORD_ID) {
                    break;
                } else if (record.getRecordType() == TimeCommandBehaviorContainer.RECORD_ID) {
                    break;
                } else {
                    i++;
                }
            }
            VisualShapeAtom visualShapeAtom = (VisualShapeAtom) ((ClientVisualElementContainer) ((TimeBehaviorContainer) ((PositionDependentRecordContainer) record).findFirstOfType(TimeBehaviorContainer.RECORD_ID)).findFirstOfType(ClientVisualElementContainer.RECORD_ID)).findFirstOfType(VisualShapeAtom.RECORD_ID);
            int targetElementType = visualShapeAtom.getTargetElementType();
            if (targetElementType == 0) {
                return new ShapeAnimation(visualShapeAtom.getTargetElementID(), b, -2, -2);
            }
            if (targetElementType == 2) {
                int paraIndex = getParaIndex(pGSlide, visualShapeAtom);
                return new ShapeAnimation(visualShapeAtom.getTargetElementID(), b, paraIndex, paraIndex);
            } else if (targetElementType != 6) {
                return null;
            } else {
                return new ShapeAnimation(visualShapeAtom.getTargetElementID(), b, -1, -1);
            }
        } catch (Exception unused) {
        }
    }

    private int getParaIndex(PGSlide pGSlide, VisualShapeAtom visualShapeAtom) {
        IShape[] shapes = pGSlide.getShapes();
        int length = shapes.length;
        int i = 0;
        int i2 = 0;
        while (i2 < length) {
            if (!(shapes[i2] instanceof TextBox) || shapes[i2].getShapeID() != visualShapeAtom.getTargetElementID()) {
                i2++;
            } else {
                SectionElement element = ((TextBox) shapes[i2]).getElement();
                ParagraphElement paragraphElement = (ParagraphElement) element.getElement(0);
                while (paragraphElement != null) {
                    long endOffset = paragraphElement.getEndOffset();
                    if (paragraphElement.getStartOffset() == ((long) visualShapeAtom.getData1()) && (endOffset == ((long) visualShapeAtom.getData2()) || endOffset == ((long) (visualShapeAtom.getData2() - 1)))) {
                        return i;
                    }
                    i++;
                    paragraphElement = (ParagraphElement) element.getElement(endOffset);
                }
                return -2;
            }
        }
        return -2;
    }

    public void processMaster(PGSlide pGSlide, Slide slide) {
        if (this.slideMasterIndexs == null) {
            this.slideMasterIndexs = new HashMap();
        }
        if (this.titleMasterIndexs == null) {
            this.titleMasterIndexs = new HashMap();
        }
        SlideAtom slideAtom = slide.getSlideRecord().getSlideAtom();
        if (slideAtom.getFollowMasterObjects()) {
            int masterID = slideAtom.getMasterID();
            SlideMaster[] slidesMasters = this.poiSlideShow.getSlidesMasters();
            int i = 0;
            while (true) {
                if (i >= slidesMasters.length) {
                    break;
                } else if (masterID == slidesMasters[i]._getSheetNumber()) {
                    Integer num = this.slideMasterIndexs.get(Integer.valueOf(masterID));
                    if (num != null) {
                        pGSlide.setMasterSlideIndex(num.intValue());
                        return;
                    }
                    PGSlide pGSlide2 = new PGSlide();
                    pGSlide2.setSlideType(0);
                    pGSlide2.setBackgroundAndFill(pGSlide.getBackgroundAndFill());
                    Shape[] shapes = slidesMasters[i].getShapes();
                    for (Shape processShape : shapes) {
                        processShape(pGSlide2, (GroupShape) null, processShape, 0);
                    }
                    if (pGSlide2.getShapeCount() > 0) {
                        Integer valueOf = Integer.valueOf(this.model.appendSlideMaster(pGSlide2));
                        pGSlide.setMasterSlideIndex(valueOf.intValue());
                        this.slideMasterIndexs.put(Integer.valueOf(masterID), valueOf);
                    }
                } else {
                    i++;
                }
            }
            TitleMaster[] titleMasters = this.poiSlideShow.getTitleMasters();
            if (titleMasters != null) {
                for (int i2 = 0; i2 < titleMasters.length; i2++) {
                    if (masterID == titleMasters[i2]._getSheetNumber()) {
                        Integer num2 = this.titleMasterIndexs.get(Integer.valueOf(masterID));
                        if (num2 != null) {
                            pGSlide.setLayoutSlideIndex(num2.intValue());
                            return;
                        }
                        PGSlide pGSlide3 = new PGSlide();
                        pGSlide3.setSlideType(0);
                        pGSlide3.setBackgroundAndFill(pGSlide.getBackgroundAndFill());
                        Shape[] shapes2 = titleMasters[i2].getShapes();
                        for (Shape processShape2 : shapes2) {
                            processShape(pGSlide3, (GroupShape) null, processShape2, 0);
                        }
                        if (pGSlide3.getShapeCount() > 0) {
                            Integer valueOf2 = Integer.valueOf(this.model.appendSlideMaster(pGSlide3));
                            pGSlide.setLayoutSlideIndex(valueOf2.intValue());
                            this.titleMasterIndexs.put(Integer.valueOf(masterID), valueOf2);
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }

    private Line getShapeLine(SimpleShape simpleShape) {
        return getShapeLine(simpleShape, false);
    }

    private Line getShapeLine(SimpleShape simpleShape, boolean z) {
        if (simpleShape != null && simpleShape.hasLine()) {
            int round = (int) Math.round(simpleShape.getLineWidth() * 1.3333333730697632d);
            boolean z2 = simpleShape.getLineDashing() > 0;
            Color lineColor = simpleShape.getLineColor();
            if (lineColor == null) {
                return null;
            }
            Line line = new Line();
            BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
            backgroundAndFill.setForegroundColor(converterColor(lineColor));
            line.setBackgroundAndFill(backgroundAndFill);
            line.setDash(z2);
            line.setLineWidth(round);
            return line;
        } else if (!z) {
            return null;
        } else {
            Line line2 = new Line();
            BackgroundAndFill backgroundAndFill2 = new BackgroundAndFill();
            backgroundAndFill2.setForegroundColor(-16777216);
            line2.setBackgroundAndFill(backgroundAndFill2);
            return line2;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00ca, code lost:
        if (r10 != 0) goto L_0x00d5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.common.bg.BackgroundAndFill converFill(com.app.office.pg.model.PGSlide r18, com.app.office.fc.hslf.model.Fill r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = 0
            if (r19 == 0) goto L_0x013c
            int r2 = r19.getFillType()
            r3 = 9
            if (r2 != r3) goto L_0x0013
            com.app.office.common.bg.BackgroundAndFill r1 = r18.getBackgroundAndFill()
            goto L_0x013c
        L_0x0013:
            r3 = 0
            if (r2 != 0) goto L_0x0031
            com.app.office.java.awt.Color r2 = r19.getForegroundColor()
            if (r2 == 0) goto L_0x013c
            com.app.office.common.bg.BackgroundAndFill r1 = new com.app.office.common.bg.BackgroundAndFill
            r1.<init>()
            r1.setFillType(r3)
            com.app.office.java.awt.Color r2 = r19.getForegroundColor()
            int r2 = r0.converterColor(r2)
            r1.setForegroundColor(r2)
            goto L_0x013c
        L_0x0031:
            r4 = 1
            r5 = 6
            r6 = 5
            r7 = 4
            r8 = 7
            r9 = 2
            if (r2 == r8) goto L_0x00ba
            if (r2 == r7) goto L_0x00ba
            if (r2 == r6) goto L_0x00ba
            if (r2 != r5) goto L_0x0041
            goto L_0x00ba
        L_0x0041:
            if (r2 != r9) goto L_0x0079
            com.app.office.common.bg.BackgroundAndFill r1 = new com.app.office.common.bg.BackgroundAndFill
            r1.<init>()
            r1.setFillType(r9)
            com.app.office.fc.hslf.usermodel.PictureData r2 = r19.getPictureData()
            if (r2 == 0) goto L_0x013c
            com.app.office.system.IControl r4 = r0.control
            com.app.office.system.SysKit r4 = r4.getSysKit()
            com.app.office.common.picture.PictureManage r4 = r4.getPictureManage()
            int r2 = r4.addPicture((com.app.office.fc.hslf.usermodel.PictureData) r2)
            com.app.office.common.bg.TileShader r4 = new com.app.office.common.bg.TileShader
            com.app.office.system.IControl r5 = r0.control
            com.app.office.system.SysKit r5 = r5.getSysKit()
            com.app.office.common.picture.PictureManage r5 = r5.getPictureManage()
            com.app.office.common.picture.Picture r2 = r5.getPicture(r2)
            r5 = 1065353216(0x3f800000, float:1.0)
            r4.<init>(r2, r3, r5, r5)
            r1.setShader(r4)
            goto L_0x013c
        L_0x0079:
            r5 = 3
            if (r2 != r5) goto L_0x009d
            com.app.office.fc.hslf.usermodel.PictureData r2 = r19.getPictureData()
            if (r2 == 0) goto L_0x013c
            com.app.office.common.bg.BackgroundAndFill r1 = new com.app.office.common.bg.BackgroundAndFill
            r1.<init>()
            r1.setFillType(r5)
            com.app.office.system.IControl r3 = r0.control
            com.app.office.system.SysKit r3 = r3.getSysKit()
            com.app.office.common.picture.PictureManage r3 = r3.getPictureManage()
            int r2 = r3.addPicture((com.app.office.fc.hslf.usermodel.PictureData) r2)
            r1.setPictureIndex(r2)
            goto L_0x013c
        L_0x009d:
            if (r2 != r4) goto L_0x013c
            com.app.office.java.awt.Color r2 = r19.getFillbackColor()
            if (r2 == 0) goto L_0x013c
            com.app.office.common.bg.BackgroundAndFill r1 = new com.app.office.common.bg.BackgroundAndFill
            r1.<init>()
            r1.setFillType(r3)
            com.app.office.java.awt.Color r2 = r19.getFillbackColor()
            int r2 = r0.converterColor(r2)
            r1.setForegroundColor(r2)
            goto L_0x013c
        L_0x00ba:
            int r10 = r19.getFillAngle()
            r11 = -135(0xffffffffffffff79, float:NaN)
            if (r10 == r11) goto L_0x00d3
            r11 = -90
            if (r10 == r11) goto L_0x00d0
            r11 = -45
            if (r10 == r11) goto L_0x00cd
            if (r10 == 0) goto L_0x00d0
            goto L_0x00d5
        L_0x00cd:
            r10 = 135(0x87, float:1.89E-43)
            goto L_0x00d5
        L_0x00d0:
            int r10 = r10 + 90
            goto L_0x00d5
        L_0x00d3:
            r10 = 45
        L_0x00d5:
            int r11 = r19.getFillFocus()
            com.app.office.java.awt.Color r12 = r19.getForegroundColor()
            com.app.office.java.awt.Color r13 = r19.getFillbackColor()
            boolean r14 = r19.isShaderPreset()
            if (r14 == 0) goto L_0x00f0
            int[] r14 = r19.getShaderColors()
            float[] r15 = r19.getShaderPositions()
            goto L_0x00f2
        L_0x00f0:
            r14 = r1
            r15 = r14
        L_0x00f2:
            if (r14 != 0) goto L_0x010b
            int[] r14 = new int[r9]
            r16 = -1
            if (r12 != 0) goto L_0x00fc
            r12 = -1
            goto L_0x0100
        L_0x00fc:
            int r12 = r12.getRGB()
        L_0x0100:
            r14[r3] = r12
            if (r13 != 0) goto L_0x0105
            goto L_0x0109
        L_0x0105:
            int r16 = r13.getRGB()
        L_0x0109:
            r14[r4] = r16
        L_0x010b:
            if (r15 != 0) goto L_0x0112
            float[] r15 = new float[r9]
            r15 = {0, 1065353216} // fill-array
        L_0x0112:
            if (r2 != r8) goto L_0x011b
            com.app.office.common.bg.LinearGradientShader r1 = new com.app.office.common.bg.LinearGradientShader
            float r3 = (float) r10
            r1.<init>(r3, r14, r15)
            goto L_0x012a
        L_0x011b:
            if (r2 == r7) goto L_0x0121
            if (r2 == r6) goto L_0x0121
            if (r2 != r5) goto L_0x012a
        L_0x0121:
            com.app.office.common.bg.RadialGradientShader r1 = new com.app.office.common.bg.RadialGradientShader
            int r3 = r19.getRadialGradientPositionType()
            r1.<init>(r3, r14, r15)
        L_0x012a:
            if (r1 == 0) goto L_0x012f
            r1.setFocus(r11)
        L_0x012f:
            com.app.office.common.bg.BackgroundAndFill r3 = new com.app.office.common.bg.BackgroundAndFill
            r3.<init>()
            byte r2 = (byte) r2
            r3.setFillType(r2)
            r3.setShader(r1)
            r1 = r3
        L_0x013c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.PPTReader.converFill(com.app.office.pg.model.PGSlide, com.app.office.fc.hslf.model.Fill):com.app.office.common.bg.BackgroundAndFill");
    }

    private void processNotes(PGSlide pGSlide, Notes notes) {
        TextShape textShape;
        OEPlaceholderAtom placeholderAtom;
        String text;
        if (notes != null) {
            String str = "";
            for (Shape shape : notes.getShapes()) {
                if (this.abortReader) {
                    break;
                }
                if (((shape instanceof AutoShape) || (shape instanceof com.app.office.fc.hslf.model.TextBox)) && (placeholderAtom = (textShape = (TextShape) shape).getPlaceholderAtom()) != null && placeholderAtom.getPlaceholderId() == 12 && (text = textShape.getText()) != null && text.length() > 0) {
                    str = (str + text) + 10;
                }
            }
            if (str.trim().length() > 0) {
                pGSlide.setNotes(new PGNotes(str.trim()));
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: com.app.office.common.shape.LineShape} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v43, resolved type: com.app.office.common.shape.LineShape} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v44, resolved type: com.app.office.common.shape.LineShape} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processShape(com.app.office.pg.model.PGSlide r18, com.app.office.common.shape.GroupShape r19, com.app.office.fc.hslf.model.Shape r20, int r21) {
        /*
            r17 = this;
            r6 = r17
            r7 = r18
            r8 = r19
            r9 = r20
            r10 = r21
            r0 = 0
            r6.tableShape = r0
            boolean r1 = r6.abortReader
            if (r1 != 0) goto L_0x0482
            boolean r1 = r20.isHidden()
            if (r1 == 0) goto L_0x0019
            goto L_0x0482
        L_0x0019:
            boolean r1 = r9 instanceof com.app.office.fc.hslf.model.ShapeGroup
            if (r1 == 0) goto L_0x0025
            r2 = r9
            com.app.office.fc.hslf.model.ShapeGroup r2 = (com.app.office.fc.hslf.model.ShapeGroup) r2
            com.app.office.java.awt.geom.Rectangle2D r2 = r2.getClientAnchor2D(r9)
            goto L_0x0029
        L_0x0025:
            com.app.office.java.awt.geom.Rectangle2D r2 = r20.getLogicalAnchor2D()
        L_0x0029:
            if (r2 != 0) goto L_0x002c
            return
        L_0x002c:
            com.app.office.java.awt.Rectangle r11 = new com.app.office.java.awt.Rectangle
            r11.<init>()
            double r3 = r2.getX()
            r12 = 4608683618854764544(0x3ff5555560000000, double:1.3333333730697632)
            double r3 = r3 * r12
            int r3 = (int) r3
            r11.x = r3
            double r3 = r2.getY()
            double r3 = r3 * r12
            int r3 = (int) r3
            r11.y = r3
            double r3 = r2.getWidth()
            double r3 = r3 * r12
            int r3 = (int) r3
            r11.width = r3
            double r2 = r2.getHeight()
            double r2 = r2 * r12
            int r2 = (int) r2
            r11.height = r2
            boolean r2 = r9 instanceof com.app.office.fc.hslf.model.SimpleShape
            r12 = 2
            if (r2 == 0) goto L_0x00b6
            if (r10 != r12) goto L_0x0088
            int r2 = r20.getMasterShapeID()
            int[] r3 = r18.getMasterIndexs()
            com.app.office.pg.model.PGModel r4 = r6.model
            r3 = r3[r0]
            com.app.office.pg.model.PGSlide r3 = r4.getSlideMaster(r3)
            if (r3 == 0) goto L_0x0088
            int r4 = r3.getShapeCount()
            r5 = 0
        L_0x0078:
            if (r5 >= r4) goto L_0x0088
            com.app.office.common.shape.IShape r14 = r3.getShape(r5)
            int r15 = r14.getShapeID()
            if (r15 != r2) goto L_0x0085
            goto L_0x0089
        L_0x0085:
            int r5 = r5 + 1
            goto L_0x0078
        L_0x0088:
            r14 = 0
        L_0x0089:
            com.app.office.fc.hslf.model.Fill r2 = r20.getFill()
            com.app.office.common.bg.BackgroundAndFill r2 = r6.converFill(r7, r2)
            if (r2 != 0) goto L_0x00a0
            if (r14 == 0) goto L_0x00a0
            boolean r3 = r14 instanceof com.app.office.common.shape.AbstractShape
            if (r3 == 0) goto L_0x00a0
            r2 = r14
            com.app.office.common.shape.AbstractShape r2 = (com.app.office.common.shape.AbstractShape) r2
            com.app.office.common.bg.BackgroundAndFill r2 = r2.getBackgroundAndFill()
        L_0x00a0:
            r3 = r9
            com.app.office.fc.hslf.model.SimpleShape r3 = (com.app.office.fc.hslf.model.SimpleShape) r3
            com.app.office.common.borders.Line r3 = r6.getShapeLine(r3)
            if (r3 != 0) goto L_0x00b8
            if (r14 == 0) goto L_0x00b8
            boolean r4 = r14 instanceof com.app.office.common.shape.AbstractShape
            if (r4 == 0) goto L_0x00b8
            com.app.office.common.shape.AbstractShape r14 = (com.app.office.common.shape.AbstractShape) r14
            com.app.office.common.borders.Line r3 = r14.getLine()
            goto L_0x00b8
        L_0x00b6:
            r2 = 0
            r3 = 0
        L_0x00b8:
            boolean r4 = r9 instanceof com.app.office.fc.hslf.model.Line
            if (r4 != 0) goto L_0x0137
            boolean r5 = r9 instanceof com.app.office.fc.hslf.model.Freeform
            if (r5 != 0) goto L_0x0137
            boolean r5 = r9 instanceof com.app.office.fc.hslf.model.AutoShape
            if (r5 != 0) goto L_0x0137
            boolean r5 = r9 instanceof com.app.office.fc.hslf.model.TextBox
            if (r5 != 0) goto L_0x0137
            boolean r5 = r9 instanceof com.app.office.fc.hslf.model.Picture
            if (r5 == 0) goto L_0x00cd
            goto L_0x0137
        L_0x00cd:
            boolean r2 = r9 instanceof com.app.office.fc.hslf.model.Table
            if (r2 == 0) goto L_0x00db
            r0 = r9
            com.app.office.fc.hslf.model.Table r0 = (com.app.office.fc.hslf.model.Table) r0
            if (r0 == 0) goto L_0x0482
            r6.processTable(r7, r0, r8, r10)
            goto L_0x0482
        L_0x00db:
            if (r1 == 0) goto L_0x0482
            r1 = r9
            com.app.office.fc.hslf.model.ShapeGroup r1 = (com.app.office.fc.hslf.model.ShapeGroup) r1
            com.app.office.common.shape.GroupShape r2 = new com.app.office.common.shape.GroupShape
            r2.<init>()
            r2.setBounds(r11)
            int r3 = r20.getShapeId()
            r2.setShapeID(r3)
            boolean r3 = r1.getFlipHorizontal()
            r2.setFlipHorizontal(r3)
            boolean r3 = r1.getFlipVertical()
            r2.setFlipVertical(r3)
            r2.setParent(r8)
            r6.processGrpRotation(r9, r2)
            com.app.office.fc.hslf.model.Shape[] r1 = r1.getShapes()
            java.util.ArrayList r3 = new java.util.ArrayList
            int r4 = r1.length
            r3.<init>(r4)
        L_0x010d:
            int r4 = r1.length
            if (r0 >= r4) goto L_0x0125
            r4 = r1[r0]
            r6.processShape(r7, r2, r4, r10)
            r4 = r1[r0]
            int r4 = r4.getShapeId()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3.add(r4)
            int r0 = r0 + 1
            goto L_0x010d
        L_0x0125:
            if (r8 != 0) goto L_0x012b
            r7.appendShapes(r2)
            goto L_0x012e
        L_0x012b:
            r8.appendShapes(r2)
        L_0x012e:
            int r0 = r20.getShapeId()
            r7.addGroupShape(r0, r3)
            goto L_0x0482
        L_0x0137:
            r1 = 1065353216(0x3f800000, float:1.0)
            r5 = 33
            r14 = 1
            if (r4 == 0) goto L_0x01b0
            if (r3 == 0) goto L_0x0482
            com.app.office.common.shape.LineShape r4 = new com.app.office.common.shape.LineShape
            r4.<init>()
            int r10 = r20.getShapeType()
            r4.setShapeType(r10)
            r4.setBounds(r11)
            r4.setBackgroundAndFill(r2)
            r4.setLine((com.app.office.common.borders.Line) r3)
            r2 = r9
            com.app.office.fc.hslf.model.Line r2 = (com.app.office.fc.hslf.model.Line) r2
            java.lang.Float[] r3 = r2.getAdjustmentValue()
            int r10 = r4.getShapeType()
            if (r10 != r5) goto L_0x0170
            if (r3 != 0) goto L_0x0170
            java.lang.Float[] r3 = new java.lang.Float[r14]
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r3[r0] = r1
            r4.setAdjustData(r3)
            goto L_0x0173
        L_0x0170:
            r4.setAdjustData(r3)
        L_0x0173:
            int r0 = r20.getStartArrowType()
            if (r0 <= 0) goto L_0x0185
            byte r0 = (byte) r0
            int r1 = r20.getStartArrowWidth()
            int r3 = r20.getStartArrowLength()
            r4.createStartArrow(r0, r1, r3)
        L_0x0185:
            int r0 = r2.getEndArrowType()
            if (r0 <= 0) goto L_0x0197
            byte r0 = (byte) r0
            int r1 = r20.getEndArrowWidth()
            int r2 = r20.getEndArrowLength()
            r4.createEndArrow(r0, r1, r2)
        L_0x0197:
            r0 = r9
            com.app.office.fc.hslf.model.SimpleShape r0 = (com.app.office.fc.hslf.model.SimpleShape) r0
            r6.processGrpRotation(r0, r4)
            int r0 = r20.getShapeId()
            r4.setShapeID(r0)
            if (r8 != 0) goto L_0x01ab
            r7.appendShapes(r4)
            goto L_0x0482
        L_0x01ab:
            r8.appendShapes(r4)
            goto L_0x0482
        L_0x01b0:
            boolean r4 = r9 instanceof com.app.office.fc.hslf.model.Freeform
            r15 = 5
            if (r4 == 0) goto L_0x02c9
            if (r2 != 0) goto L_0x01b9
            if (r3 == 0) goto L_0x0482
        L_0x01b9:
            com.app.office.common.shape.ArbitraryPolygonShape r1 = new com.app.office.common.shape.ArbitraryPolygonShape
            r1.<init>()
            r4 = 233(0xe9, float:3.27E-43)
            r1.setShapeType(r4)
            r1.setBounds(r11)
            int r4 = r20.getStartArrowType()
            if (r4 <= 0) goto L_0x0225
            r5 = r9
            com.app.office.fc.hslf.model.Freeform r5 = (com.app.office.fc.hslf.model.Freeform) r5
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r5 = r5.getStartArrowPathAndTail(r11)
            if (r5 == 0) goto L_0x0225
            android.graphics.Path r10 = r5.getArrowPath()
            if (r10 == 0) goto L_0x0225
            android.graphics.PointF r10 = r5.getArrowTailCenter()
            com.app.office.common.autoshape.ExtendPath r12 = new com.app.office.common.autoshape.ExtendPath
            r12.<init>()
            android.graphics.Path r5 = r5.getArrowPath()
            r12.setPath(r5)
            r12.setArrowFlag(r14)
            if (r4 == r15) goto L_0x021d
            if (r3 == 0) goto L_0x0201
            com.app.office.common.bg.BackgroundAndFill r5 = r3.getBackgroundAndFill()
            if (r5 != 0) goto L_0x01f9
            goto L_0x0201
        L_0x01f9:
            com.app.office.common.bg.BackgroundAndFill r5 = r3.getBackgroundAndFill()
            r12.setBackgroundAndFill(r5)
            goto L_0x0220
        L_0x0201:
            r5 = r9
            com.app.office.fc.hslf.model.SimpleShape r5 = (com.app.office.fc.hslf.model.SimpleShape) r5
            com.app.office.java.awt.Color r5 = r5.getLineColor()
            if (r5 == 0) goto L_0x0220
            com.app.office.common.bg.BackgroundAndFill r13 = new com.app.office.common.bg.BackgroundAndFill
            r13.<init>()
            r13.setFillType(r0)
            int r5 = r6.converterColor(r5)
            r13.setForegroundColor(r5)
            r12.setBackgroundAndFill(r13)
            goto L_0x0220
        L_0x021d:
            r12.setLine((com.app.office.common.borders.Line) r3)
        L_0x0220:
            r1.appendPath(r12)
            r12 = r10
            goto L_0x0226
        L_0x0225:
            r12 = 0
        L_0x0226:
            int r5 = r20.getEndArrowType()
            if (r5 <= 0) goto L_0x0286
            r10 = r9
            com.app.office.fc.hslf.model.Freeform r10 = (com.app.office.fc.hslf.model.Freeform) r10
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r10 = r10.getEndArrowPathAndTail(r11)
            if (r10 == 0) goto L_0x0286
            android.graphics.Path r13 = r10.getArrowPath()
            if (r13 == 0) goto L_0x0286
            android.graphics.PointF r13 = r10.getArrowTailCenter()
            com.app.office.common.autoshape.ExtendPath r0 = new com.app.office.common.autoshape.ExtendPath
            r0.<init>()
            android.graphics.Path r10 = r10.getArrowPath()
            r0.setPath(r10)
            r0.setArrowFlag(r14)
            if (r5 == r15) goto L_0x027e
            if (r3 == 0) goto L_0x0261
            com.app.office.common.bg.BackgroundAndFill r10 = r3.getBackgroundAndFill()
            if (r10 != 0) goto L_0x0259
            goto L_0x0261
        L_0x0259:
            com.app.office.common.bg.BackgroundAndFill r10 = r3.getBackgroundAndFill()
            r0.setBackgroundAndFill(r10)
            goto L_0x0281
        L_0x0261:
            r10 = r9
            com.app.office.fc.hslf.model.SimpleShape r10 = (com.app.office.fc.hslf.model.SimpleShape) r10
            com.app.office.java.awt.Color r10 = r10.getLineColor()
            if (r10 == 0) goto L_0x0281
            com.app.office.common.bg.BackgroundAndFill r14 = new com.app.office.common.bg.BackgroundAndFill
            r14.<init>()
            r15 = 0
            r14.setFillType(r15)
            int r10 = r6.converterColor(r10)
            r14.setForegroundColor(r10)
            r0.setBackgroundAndFill(r14)
            goto L_0x0281
        L_0x027e:
            r0.setLine((com.app.office.common.borders.Line) r3)
        L_0x0281:
            r1.appendPath(r0)
            r14 = r13
            goto L_0x0287
        L_0x0286:
            r14 = 0
        L_0x0287:
            r10 = r9
            com.app.office.fc.hslf.model.Freeform r10 = (com.app.office.fc.hslf.model.Freeform) r10
            byte r13 = (byte) r4
            byte r15 = (byte) r5
            android.graphics.Path[] r0 = r10.getFreeformPath(r11, r12, r13, r14, r15)
            r4 = 0
        L_0x0291:
            if (r0 == 0) goto L_0x02b0
            int r5 = r0.length
            if (r4 >= r5) goto L_0x02b0
            com.app.office.common.autoshape.ExtendPath r5 = new com.app.office.common.autoshape.ExtendPath
            r5.<init>()
            r10 = r0[r4]
            r5.setPath(r10)
            if (r3 == 0) goto L_0x02a5
            r5.setLine((com.app.office.common.borders.Line) r3)
        L_0x02a5:
            if (r2 == 0) goto L_0x02aa
            r5.setBackgroundAndFill(r2)
        L_0x02aa:
            r1.appendPath(r5)
            int r4 = r4 + 1
            goto L_0x0291
        L_0x02b0:
            r0 = r9
            com.app.office.fc.hslf.model.SimpleShape r0 = (com.app.office.fc.hslf.model.SimpleShape) r0
            r6.processGrpRotation(r0, r1)
            int r0 = r20.getShapeId()
            r1.setShapeID(r0)
            if (r8 != 0) goto L_0x02c4
            r7.appendShapes(r1)
            goto L_0x0482
        L_0x02c4:
            r8.appendShapes(r1)
            goto L_0x0482
        L_0x02c9:
            boolean r0 = r9 instanceof com.app.office.fc.hslf.model.AutoShape
            if (r0 != 0) goto L_0x0344
            boolean r0 = r9 instanceof com.app.office.fc.hslf.model.TextBox
            if (r0 == 0) goto L_0x02d2
            goto L_0x0344
        L_0x02d2:
            boolean r0 = r9 instanceof com.app.office.fc.hslf.model.Picture
            if (r0 == 0) goto L_0x0482
            r0 = r9
            com.app.office.fc.hslf.model.Picture r0 = (com.app.office.fc.hslf.model.Picture) r0
            com.app.office.fc.hslf.usermodel.PictureData r1 = r0.getPictureData()
            if (r1 == 0) goto L_0x0322
            com.app.office.common.shape.PictureShape r4 = new com.app.office.common.shape.PictureShape
            r4.<init>()
            com.app.office.system.IControl r5 = r6.control
            com.app.office.system.SysKit r5 = r5.getSysKit()
            com.app.office.common.picture.PictureManage r5 = r5.getPictureManage()
            int r1 = r5.addPicture((com.app.office.fc.hslf.usermodel.PictureData) r1)
            r4.setPictureIndex(r1)
            r4.setBounds(r11)
            r1 = r9
            com.app.office.fc.hslf.model.SimpleShape r1 = (com.app.office.fc.hslf.model.SimpleShape) r1
            r6.processGrpRotation(r1, r4)
            int r1 = r20.getShapeId()
            r4.setShapeID(r1)
            com.app.office.fc.ddf.EscherOptRecord r0 = r0.getEscherOptRecord()
            com.app.office.common.pictureefftect.PictureEffectInfo r0 = com.app.office.common.pictureefftect.PictureEffectInfoFactory.getPictureEffectInfor((com.app.office.fc.ddf.EscherOptRecord) r0)
            r4.setPictureEffectInfor(r0)
            r4.setBackgroundAndFill(r2)
            r4.setLine((com.app.office.common.borders.Line) r3)
            if (r8 != 0) goto L_0x031d
            r7.appendShapes(r4)
            goto L_0x0482
        L_0x031d:
            r8.appendShapes(r4)
            goto L_0x0482
        L_0x0322:
            if (r2 != 0) goto L_0x0326
            if (r3 == 0) goto L_0x0482
        L_0x0326:
            com.app.office.common.shape.AutoShape r0 = new com.app.office.common.shape.AutoShape
            r0.<init>(r14)
            r1 = 0
            r0.setAuotShape07(r1)
            r0.setBounds(r11)
            r0.setBackgroundAndFill(r2)
            r0.setLine((com.app.office.common.borders.Line) r3)
            if (r8 != 0) goto L_0x033f
            r7.appendShapes(r0)
            goto L_0x0482
        L_0x033f:
            r8.appendShapes(r0)
            goto L_0x0482
        L_0x0344:
            r4 = r9
            com.app.office.fc.hslf.model.TextShape r4 = (com.app.office.fc.hslf.model.TextShape) r4
            int r13 = r4.getPlaceholderId()
            if (r2 != 0) goto L_0x0353
            if (r3 == 0) goto L_0x0350
            goto L_0x0353
        L_0x0350:
            r15 = 0
            goto L_0x0412
        L_0x0353:
            int r0 = r20.getShapeType()
            r15 = 20
            if (r0 == r15) goto L_0x03a6
            r15 = 32
            if (r0 == r15) goto L_0x03a6
            if (r0 == r5) goto L_0x03a6
            r15 = 34
            if (r0 == r15) goto L_0x03a6
            r15 = 35
            if (r0 == r15) goto L_0x03a6
            r15 = 36
            if (r0 == r15) goto L_0x03a6
            r15 = 37
            if (r0 == r15) goto L_0x03a6
            r15 = 38
            if (r0 == r15) goto L_0x03a6
            r15 = 39
            if (r0 == r15) goto L_0x03a6
            r15 = 40
            if (r0 != r15) goto L_0x037e
            goto L_0x03a6
        L_0x037e:
            com.app.office.common.shape.AutoShape r0 = new com.app.office.common.shape.AutoShape
            int r1 = r20.getShapeType()
            r0.<init>(r1)
            r1 = 0
            r0.setAuotShape07(r1)
            r0.setBounds(r11)
            r0.setBackgroundAndFill(r2)
            if (r3 == 0) goto L_0x0396
            r0.setLine((com.app.office.common.borders.Line) r3)
        L_0x0396:
            int r1 = r20.getShapeType()
            r2 = 202(0xca, float:2.83E-43)
            if (r1 == r2) goto L_0x03f8
            java.lang.Float[] r1 = r20.getAdjustmentValue()
            r0.setAdjustData(r1)
            goto L_0x03f8
        L_0x03a6:
            com.app.office.common.shape.LineShape r0 = new com.app.office.common.shape.LineShape
            r0.<init>()
            int r2 = r20.getShapeType()
            r0.setShapeType(r2)
            r0.setBounds(r11)
            r0.setLine((com.app.office.common.borders.Line) r3)
            java.lang.Float[] r2 = r20.getAdjustmentValue()
            int r3 = r0.getShapeType()
            if (r3 != r5) goto L_0x03d1
            if (r2 != 0) goto L_0x03d1
            java.lang.Float[] r2 = new java.lang.Float[r14]
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r3 = 0
            r2[r3] = r1
            r0.setAdjustData(r2)
            goto L_0x03d4
        L_0x03d1:
            r0.setAdjustData(r2)
        L_0x03d4:
            int r1 = r20.getStartArrowType()
            if (r1 <= 0) goto L_0x03e6
            byte r1 = (byte) r1
            int r2 = r20.getStartArrowWidth()
            int r3 = r20.getStartArrowLength()
            r0.createStartArrow(r1, r2, r3)
        L_0x03e6:
            int r1 = r20.getEndArrowType()
            if (r1 <= 0) goto L_0x03f8
            byte r1 = (byte) r1
            int r2 = r20.getEndArrowWidth()
            int r3 = r20.getEndArrowLength()
            r0.createEndArrow(r1, r2, r3)
        L_0x03f8:
            r1 = r9
            com.app.office.fc.hslf.model.SimpleShape r1 = (com.app.office.fc.hslf.model.SimpleShape) r1
            r6.processGrpRotation(r1, r0)
            int r1 = r20.getShapeId()
            r0.setShapeID(r1)
            r0.setPlaceHolderID(r13)
            if (r8 != 0) goto L_0x040e
            r7.appendShapes(r0)
            goto L_0x0411
        L_0x040e:
            r8.appendShapes(r0)
        L_0x0411:
            r15 = r0
        L_0x0412:
            com.app.office.common.shape.TextBox r5 = new com.app.office.common.shape.TextBox
            r5.<init>()
            byte r3 = r4.getMetaCharactersType()
            r5.setMCType(r3)
            r0 = r17
            r1 = r5
            r2 = r4
            r4 = r3
            r3 = r11
            r11 = r4
            r4 = r21
            r16 = r5
            r5 = r13
            r0.processTextShape(r1, r2, r3, r4, r5)
            com.app.office.simpletext.model.SectionElement r0 = r16.getElement()
            if (r0 == 0) goto L_0x0482
            boolean r0 = r16.isWordArt()
            if (r0 == 0) goto L_0x043f
            if (r15 == 0) goto L_0x043f
            r0 = 0
            r15.setBackgroundAndFill(r0)
        L_0x043f:
            r0 = r9
            com.app.office.fc.hslf.model.SimpleShape r0 = (com.app.office.fc.hslf.model.SimpleShape) r0
            r1 = r16
            r6.processGrpRotation(r0, r1)
            int r0 = r20.getShapeId()
            r1.setShapeID(r0)
            r1.setPlaceHolderID(r13)
            if (r10 != r12) goto L_0x0470
            r0 = 9
            if (r13 != r0) goto L_0x045a
            r6.hasProcessedMasterFooter = r14
            goto L_0x0470
        L_0x045a:
            r0 = 7
            if (r13 != r0) goto L_0x0468
            if (r11 == r12) goto L_0x0465
            r0 = 3
            if (r11 == r0) goto L_0x0465
            r0 = 5
            if (r11 != r0) goto L_0x0468
        L_0x0465:
            r6.hasProcessedMasterDateTime = r14
            goto L_0x0470
        L_0x0468:
            r0 = 8
            if (r13 != r0) goto L_0x0470
            if (r11 != r14) goto L_0x0470
            r6.hasProcessedMasterSlideNumber = r14
        L_0x0470:
            if (r8 == 0) goto L_0x047f
            if (r10 != 0) goto L_0x047b
            boolean r0 = com.app.office.fc.hslf.model.MasterSheet.isPlaceholder(r20)
            if (r0 == 0) goto L_0x047b
            goto L_0x047f
        L_0x047b:
            r8.appendShapes(r1)
            goto L_0x0482
        L_0x047f:
            r7.appendShapes(r1)
        L_0x0482:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.PPTReader.processShape(com.app.office.pg.model.PGSlide, com.app.office.common.shape.GroupShape, com.app.office.fc.hslf.model.Shape, int):void");
    }

    private void processTable(PGSlide pGSlide, Table table, GroupShape groupShape, int i) {
        int i2;
        int i3;
        int i4;
        Rectangle2D logicalAnchor2D;
        PGSlide pGSlide2 = pGSlide;
        Table table2 = table;
        GroupShape groupShape2 = groupShape;
        Rectangle2D clientAnchor2D = table2.getClientAnchor2D(table2);
        Rectangle2D coordinates = table.getCoordinates();
        this.tableShape = true;
        int numberOfRows = table.getNumberOfRows();
        int numberOfColumns = table.getNumberOfColumns();
        TableShape tableShape2 = new TableShape(numberOfRows, numberOfColumns);
        int i5 = 0;
        while (i5 < numberOfRows) {
            int i6 = 0;
            while (i6 < numberOfColumns) {
                if (!this.abortReader) {
                    TableCell cell = table2.getCell(i5, i6);
                    if (cell == null || (logicalAnchor2D = cell.getLogicalAnchor2D()) == null) {
                        i3 = i6;
                        i2 = i5;
                        i4 = numberOfRows;
                    } else {
                        double width = coordinates.getWidth() / clientAnchor2D.getWidth();
                        double height = coordinates.getHeight() / clientAnchor2D.getHeight();
                        double x = clientAnchor2D.getX() + ((logicalAnchor2D.getX() - coordinates.getX()) / width);
                        double y = clientAnchor2D.getY() + ((logicalAnchor2D.getY() - coordinates.getY()) / height);
                        double height2 = logicalAnchor2D.getHeight() / height;
                        Rectanglef rectanglef = new Rectanglef();
                        i4 = numberOfRows;
                        rectanglef.setX((float) (x * 1.3333333730697632d));
                        rectanglef.setY((float) (y * 1.3333333730697632d));
                        rectanglef.setWidth((float) ((logicalAnchor2D.getWidth() / width) * 1.3333333730697632d));
                        rectanglef.setHeight((float) (height2 * 1.3333333730697632d));
                        com.app.office.common.shape.TableCell tableCell = new com.app.office.common.shape.TableCell();
                        tableCell.setBounds(rectanglef);
                        tableCell.setLeftLine(getShapeLine(cell.getBorderLeft(), true));
                        tableCell.setRightLine(getShapeLine(cell.getBorderRight(), true));
                        tableCell.setTopLine(getShapeLine(cell.getBorderTop(), true));
                        tableCell.setBottomLine(getShapeLine(cell.getBorderBottom(), true));
                        tableCell.setBackgroundAndFill(converFill(pGSlide2, cell.getFill()));
                        String text = cell.getText();
                        if (text == null || text.trim().length() <= 0) {
                            i3 = i6;
                            i2 = i5;
                        } else {
                            TextBox textBox = new TextBox();
                            TableCell tableCell2 = cell;
                            i3 = i6;
                            i2 = i5;
                            processTextShape(textBox, tableCell2, new Rectangle((int) rectanglef.getX(), (int) rectanglef.getY(), (int) rectanglef.getWidth(), (int) rectanglef.getHeight()), i, -1);
                            if (textBox.getElement() != null) {
                                processGrpRotation(tableCell2, textBox);
                                tableCell.setText(textBox);
                            }
                        }
                        tableShape2.addCell((i2 * numberOfColumns) + i3, tableCell);
                    }
                    i6 = i3 + 1;
                    table2 = table;
                    numberOfRows = i4;
                    i5 = i2;
                } else {
                    return;
                }
            }
            int i7 = numberOfRows;
            i5++;
            table2 = table;
        }
        for (com.app.office.fc.hslf.model.Line line : table.getTableBorders()) {
            Line shapeLine = getShapeLine(line, true);
            if (shapeLine != null) {
                Rectangle2D logicalAnchor2D2 = line.getLogicalAnchor2D();
                if (logicalAnchor2D2 != null) {
                    Rectangle rectangle = new Rectangle();
                    rectangle.x = (int) (logicalAnchor2D2.getX() * 1.3333333730697632d);
                    rectangle.y = (int) (logicalAnchor2D2.getY() * 1.3333333730697632d);
                    rectangle.width = (int) (logicalAnchor2D2.getWidth() * 1.3333333730697632d);
                    rectangle.height = (int) (logicalAnchor2D2.getHeight() * 1.3333333730697632d);
                    LineShape lineShape = new LineShape();
                    lineShape.setShapeType(line.getShapeType());
                    lineShape.setBounds(rectangle);
                    lineShape.setLine(shapeLine);
                    Float[] adjustmentValue = line.getAdjustmentValue();
                    if (lineShape.getShapeType() == 33 && adjustmentValue == null) {
                        lineShape.setAdjustData(new Float[]{Float.valueOf(1.0f)});
                    } else {
                        lineShape.setAdjustData((Float[]) null);
                    }
                    processGrpRotation(line, lineShape);
                    lineShape.setShapeID(line.getShapeId());
                    pGSlide2.appendShapes(lineShape);
                } else {
                    return;
                }
            }
        }
        Rectangle rectangle2 = new Rectangle();
        rectangle2.x = (int) (clientAnchor2D.getX() * 1.3333333730697632d);
        rectangle2.y = (int) (clientAnchor2D.getY() * 1.3333333730697632d);
        rectangle2.width = (int) (clientAnchor2D.getWidth() * 1.3333333730697632d);
        rectangle2.height = (int) (clientAnchor2D.getHeight() * 1.3333333730697632d);
        tableShape2.setBounds(rectangle2);
        tableShape2.setShapeID(table.getShapeId());
        tableShape2.setTable07(false);
        if (groupShape2 == null) {
            pGSlide2.appendShapes(tableShape2);
        } else {
            groupShape2.appendShapes(tableShape2);
        }
        this.tableShape = false;
    }

    private int getBorderColor(com.app.office.fc.hslf.model.Line line) {
        Color lineColor;
        if (line == null || (lineColor = line.getLineColor()) == null) {
            return -16777216;
        }
        return converterColor(lineColor);
    }

    private void processTextShape(TextBox textBox, TextShape textShape, Rectangle rectangle, int i, int i2) {
        Rectangle rectangle2;
        if (rectangle == null) {
            Rectangle2D logicalAnchor2D = textShape.getLogicalAnchor2D();
            if (logicalAnchor2D != null) {
                Rectangle rectangle3 = new Rectangle();
                rectangle3.x = (int) (logicalAnchor2D.getX() * 1.3333333730697632d);
                rectangle3.y = (int) (logicalAnchor2D.getY() * 1.3333333730697632d);
                rectangle3.width = (int) (logicalAnchor2D.getWidth() * 1.3333333730697632d);
                rectangle3.height = (int) (logicalAnchor2D.getHeight() * 1.3333333730697632d);
                rectangle2 = rectangle3;
            } else {
                return;
            }
        } else {
            rectangle2 = rectangle;
        }
        textBox.setBounds(rectangle2);
        textBox.setWrapLine(textShape.getWordWrap() == 0);
        if (textShape.getText() != null) {
            processNormalTextShape(textBox, textShape, rectangle2, i, i2);
            return;
        }
        String unicodeGeoText = textShape.getUnicodeGeoText();
        if (unicodeGeoText != null && unicodeGeoText.length() > 0) {
            textBox.setWordArt(true);
            processWordArtTextShape(textBox, textShape, unicodeGeoText, rectangle2, i, i2);
        }
    }

    private void processNormalTextShape(TextBox textBox, TextShape textShape, Rectangle rectangle, int i, int i2) {
        byte b;
        Rectangle rectangle2 = rectangle;
        String text = textShape.getText();
        if (text != null && text.trim().length() > 0) {
            SectionElement sectionElement = new SectionElement();
            textBox.setElement(sectionElement);
            IAttributeSet attribute = sectionElement.getAttribute();
            AttrManage.instance().setPageWidth(attribute, (int) (((float) rectangle2.width) * 15.0f));
            AttrManage.instance().setPageHeight(attribute, (int) (((float) rectangle2.height) * 15.0f));
            AttrManage.instance().setPageMarginLeft(attribute, (int) (textShape.getMarginLeft() * 20.0f));
            AttrManage.instance().setPageMarginRight(attribute, (int) (textShape.getMarginRight() * 20.0f));
            AttrManage.instance().setPageMarginTop(attribute, (int) (textShape.getMarginTop() * 20.0f));
            AttrManage.instance().setPageMarginBottom(attribute, (int) (textShape.getMarginBottom() * 20.0f));
            int verticalAlignment = textShape.getVerticalAlignment();
            switch (verticalAlignment) {
                case 1:
                case 4:
                    b = 1;
                    break;
                case 2:
                case 5:
                case 7:
                case 9:
                    b = 2;
                    break;
                default:
                    b = 0;
                    break;
            }
            if (verticalAlignment == 3 || verticalAlignment == 8 || verticalAlignment == 4 || verticalAlignment == 5 || verticalAlignment == 9) {
                AttrManage.instance().setPageHorizontalAlign(attribute, (byte) 1);
            }
            AttrManage.instance().setPageVerticalAlign(attribute, b);
            this.offset = 0;
            sectionElement.setStartOffset((long) 0);
            int length = text.length();
            Hyperlink[] hyperlinks = textShape.getTextRun().getHyperlinks();
            int i3 = 0;
            if (textShape.getTextRun().getRunType() != 0) {
                for (int i4 = 0; i4 < length && !this.abortReader; i4++) {
                    if (text.charAt(i4) == 10) {
                        int i5 = i4 + 1;
                        if (i5 < length) {
                            processParagraph(sectionElement, textShape, text, hyperlinks, i3, i5, i2);
                            i3 = i5;
                        }
                    }
                }
            }
            processParagraph(sectionElement, textShape, text, hyperlinks, i3, length, i2);
            sectionElement.setEndOffset((long) this.offset);
            BulletNumberManage.instance().clearData();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0119 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processWordArtTextShape(com.app.office.common.shape.TextBox r11, com.app.office.fc.hslf.model.TextShape r12, java.lang.String r13, com.app.office.java.awt.Rectangle r14, int r15, int r16) {
        /*
            r10 = this;
            r0 = r10
            r1 = r13
            r2 = r14
            r3 = r15
            r4 = r16
            r5 = 0
            r6 = 2
            java.lang.String r7 = "*"
            r8 = 7
            r9 = 9
            if (r4 != r9) goto L_0x0039
            boolean r9 = r13.contains(r7)
            if (r9 == 0) goto L_0x0039
            if (r3 != 0) goto L_0x0026
            com.app.office.fc.hslf.model.HeadersFooters r3 = r0.poiHeadersFooters
            java.lang.String r3 = r3.getFooterText()
            if (r3 == 0) goto L_0x0062
            com.app.office.fc.hslf.model.HeadersFooters r1 = r0.poiHeadersFooters
            java.lang.String r1 = r1.getFooterText()
            goto L_0x0062
        L_0x0026:
            if (r3 != r6) goto L_0x0062
            com.app.office.fc.hslf.model.HeadersFooters r1 = r0.poiHeadersFooters
            java.lang.String r1 = r1.getFooterText()
            if (r1 == 0) goto L_0x0037
            com.app.office.fc.hslf.model.HeadersFooters r1 = r0.poiHeadersFooters
            java.lang.String r1 = r1.getFooterText()
            goto L_0x0062
        L_0x0037:
            r1 = r5
            goto L_0x0062
        L_0x0039:
            if (r4 != r8) goto L_0x0062
            boolean r4 = r13.contains(r7)
            if (r4 == 0) goto L_0x0062
            if (r3 != 0) goto L_0x0052
            com.app.office.fc.hslf.model.HeadersFooters r3 = r0.poiHeadersFooters
            java.lang.String r3 = r3.getDateTimeText()
            if (r3 == 0) goto L_0x0062
            com.app.office.fc.hslf.model.HeadersFooters r1 = r0.poiHeadersFooters
            java.lang.String r1 = r1.getDateTimeText()
            goto L_0x0062
        L_0x0052:
            if (r3 != r6) goto L_0x0062
            com.app.office.fc.hslf.model.HeadersFooters r1 = r0.poiHeadersFooters
            java.lang.String r1 = r1.getDateTimeText()
            if (r1 == 0) goto L_0x0037
            com.app.office.fc.hslf.model.HeadersFooters r1 = r0.poiHeadersFooters
            java.lang.String r1 = r1.getDateTimeText()
        L_0x0062:
            com.app.office.simpletext.model.SectionElement r3 = new com.app.office.simpletext.model.SectionElement
            r3.<init>()
            r4 = r11
            r11.setElement(r3)
            com.app.office.simpletext.model.IAttributeSet r4 = r3.getAttribute()
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            int r6 = r2.width
            float r6 = (float) r6
            r7 = 1097859072(0x41700000, float:15.0)
            float r6 = r6 * r7
            int r6 = (int) r6
            r5.setPageWidth(r4, r6)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            int r6 = r2.height
            float r6 = (float) r6
            float r6 = r6 * r7
            int r6 = (int) r6
            r5.setPageHeight(r4, r6)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            float r6 = r12.getMarginLeft()
            r7 = 1101004800(0x41a00000, float:20.0)
            float r6 = r6 * r7
            int r6 = (int) r6
            r5.setPageMarginLeft(r4, r6)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            float r6 = r12.getMarginRight()
            float r6 = r6 * r7
            int r6 = (int) r6
            r5.setPageMarginRight(r4, r6)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            float r6 = r12.getMarginTop()
            float r6 = r6 * r7
            int r6 = (int) r6
            r5.setPageMarginTop(r4, r6)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            float r6 = r12.getMarginBottom()
            float r6 = r6 * r7
            int r6 = (int) r6
            r5.setPageMarginBottom(r4, r6)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            r6 = 1
            r5.setPageHorizontalAlign(r4, r6)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            r5.setPageVerticalAlign(r4, r6)
            int r4 = r2.width
            float r4 = (float) r4
            float r5 = r12.getMarginLeft()
            float r6 = r12.getMarginRight()
            float r5 = r5 + r6
            r6 = 1068149419(0x3faaaaab, float:1.3333334)
            float r5 = r5 * r6
            float r4 = r4 - r5
            int r4 = (int) r4
            int r2 = r2.height
            float r2 = (float) r2
            float r5 = r12.getMarginTop()
            float r7 = r12.getMarginBottom()
            float r5 = r5 + r7
            float r5 = r5 * r6
            float r2 = r2 - r5
            int r2 = (int) r2
            r5 = 0
            r0.offset = r5
            long r6 = (long) r5
            r3.setStartOffset(r6)
            com.app.office.fc.hslf.model.Fill r6 = r12.getFill()
            int r7 = r6.getFillType()
            r9 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            if (r7 != 0) goto L_0x0119
            com.app.office.java.awt.Color r5 = r6.getForegroundColor()
            if (r5 == 0) goto L_0x013e
            com.app.office.java.awt.Color r5 = r6.getForegroundColor()
            int r5 = r10.converterColor(r5)
            goto L_0x0140
        L_0x0119:
            if (r7 == r8) goto L_0x0124
            r8 = 4
            if (r7 == r8) goto L_0x0124
            r8 = 5
            if (r7 == r8) goto L_0x0124
            r8 = 6
            if (r7 != r8) goto L_0x013e
        L_0x0124:
            com.app.office.java.awt.Color r7 = r6.getForegroundColor()
            boolean r8 = r6.isShaderPreset()
            if (r8 == 0) goto L_0x013e
            int[] r6 = r6.getShaderColors()
            if (r6 == 0) goto L_0x0137
            r5 = r6[r5]
            goto L_0x0140
        L_0x0137:
            if (r7 == 0) goto L_0x013e
            int r5 = r7.getRGB()
            goto L_0x0140
        L_0x013e:
            r5 = -16777216(0xffffffffff000000, float:-1.7014118E38)
        L_0x0140:
            r11 = r10
            r12 = r3
            r13 = r1
            r14 = r4
            r15 = r2
            r16 = r5
            r11.processWordArtParagraph(r12, r13, r14, r15, r16)
            int r1 = r0.offset
            long r1 = (long) r1
            r3.setEndOffset(r1)
            com.app.office.fc.ppt.bulletnumber.BulletNumberManage r1 = com.app.office.fc.ppt.bulletnumber.BulletNumberManage.instance()
            r1.clearData()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.PPTReader.processWordArtTextShape(com.app.office.common.shape.TextBox, com.app.office.fc.hslf.model.TextShape, java.lang.String, com.app.office.java.awt.Rectangle, int, int):void");
    }

    private void processParagraph(SectionElement sectionElement, TextShape textShape, String str, Hyperlink[] hyperlinkArr, int i, int i2, int i3) {
        IAttributeSet iAttributeSet;
        IAttributeSet iAttributeSet2;
        int i4;
        boolean z;
        int i5;
        int i6;
        int addBulletNumber;
        String str2 = str;
        Hyperlink[] hyperlinkArr2 = hyperlinkArr;
        int i7 = i;
        int i8 = i2;
        int i9 = i3;
        ParagraphElement paragraphElement = new ParagraphElement();
        paragraphElement.setStartOffset((long) this.offset);
        IAttributeSet attribute = paragraphElement.getAttribute();
        RichTextRun richTextRunAt = textShape.getTextRun().getRichTextRunAt(i7);
        AttrManage.instance().setParaHorizontalAlign(attribute, richTextRunAt.getAlignment());
        int lineSpacing = richTextRunAt.getLineSpacing();
        if (lineSpacing >= 0) {
            if (lineSpacing == 0) {
                lineSpacing = 100;
            }
            AttrManage.instance().setParaLineSpaceType(attribute, 5);
            AttrManage.instance().setParaLineSpace(attribute, ((float) lineSpacing) / 100.0f);
        } else {
            AttrManage.instance().setParaLineSpaceType(attribute, 4);
            AttrManage.instance().setParaLineSpace(attribute, (float) ((int) (((float) ((-lineSpacing) / 8)) * 20.0f)));
        }
        if (this.tableShape) {
            if (i7 == 0) {
                AttrManage.instance().setParaBefore(paragraphElement.getAttribute(), 0);
            }
            if (i8 == str.length()) {
                AttrManage.instance().setParaAfter(paragraphElement.getAttribute(), 0);
            }
        }
        int textOffset = (int) (((float) richTextRunAt.getTextOffset()) * 20.0f);
        int bulletOffset = (int) (((float) richTextRunAt.getBulletOffset()) * 20.0f);
        int indentLevel = richTextRunAt.getIndentLevel();
        TextRulerAtom textRuler = textShape.getTextRun().getTextRuler();
        if (textRuler != null) {
            int i10 = textRuler.getBulletOffsets()[indentLevel];
            if (i10 >= 0) {
                textOffset = (int) (((((float) i10) * 72.0f) / 576.0f) * 20.0f);
            }
            int i11 = textRuler.getTextOffsets()[indentLevel];
            if (i11 >= 0) {
                bulletOffset = (int) (((((float) i11) * 72.0f) / 576.0f) * 20.0f);
            }
        }
        int i12 = bulletOffset - textOffset;
        AttrManage.instance().setParaSpecialIndent(attribute, i12);
        if (i12 < 0) {
            AttrManage.instance().setParaIndentLeft(attribute, bulletOffset);
        } else {
            AttrManage.instance().setParaIndentLeft(attribute, textOffset);
        }
        if (richTextRunAt.isBullet() && !"\n".equals(str2.substring(i7, i8)) && (addBulletNumber = BulletNumberManage.instance().addBulletNumber(this.control, indentLevel, textShape.getTextRun().getNumberingType(i7), textShape.getTextRun().getNumberingStart(i7), richTextRunAt.getBulletChar())) >= 0) {
            AttrManage.instance().setPGParaBulletID(attribute, addBulletNumber);
        }
        int i13 = 1;
        int i14 = i7;
        boolean z2 = textShape.getTextRun().getRunType() == 0;
        while (i14 < i8 && !this.abortReader) {
            RichTextRun richTextRunAt2 = textShape.getTextRun().getRichTextRunAt(i14);
            if (richTextRunAt2 == null) {
                break;
            }
            int endIndex = richTextRunAt2.getEndIndex();
            int i15 = endIndex > i8 ? i8 : endIndex;
            if (hyperlinkArr2 != null) {
                int i16 = 0;
                while (true) {
                    if (i16 >= hyperlinkArr2.length) {
                        i4 = i15;
                        iAttributeSet2 = attribute;
                        z = false;
                        break;
                    }
                    int startIndex = hyperlinkArr2[i16].getStartIndex();
                    int endIndex2 = hyperlinkArr2[i16].getEndIndex();
                    if (startIndex < i14 || startIndex > i15) {
                        i4 = i15;
                        iAttributeSet2 = attribute;
                        int i17 = endIndex2;
                        if (i14 <= startIndex || i17 <= i14) {
                            i16++;
                            i15 = i4;
                            attribute = iAttributeSet2;
                            i13 = 1;
                            int i18 = i2;
                        } else {
                            int addHyperlink = this.control.getSysKit().getHyperlinkManage().addHyperlink(hyperlinkArr2[i16].getAddress(), 1);
                            if (i4 <= i17) {
                                processRun(textShape, richTextRunAt2, paragraphElement, str2.substring(i14, i4), addHyperlink, i14, i4, z2);
                                i5 = i4;
                            } else {
                                processRun(textShape, richTextRunAt2, paragraphElement, str2.substring(i14, i17), addHyperlink, i14, i17, z2);
                                i5 = i17;
                            }
                            i14 = i5;
                            z = true;
                        }
                    } else {
                        int addHyperlink2 = this.control.getSysKit().getHyperlinkManage().addHyperlink(hyperlinkArr2[i16].getAddress(), i13);
                        int i19 = startIndex;
                        i4 = i15;
                        processRun(textShape, richTextRunAt2, paragraphElement, str2.substring(i14, startIndex), -1, i14, i19, z2);
                        int i20 = endIndex2;
                        if (i20 <= i4) {
                            int i21 = i19;
                            iAttributeSet2 = attribute;
                            processRun(textShape, richTextRunAt2, paragraphElement, str2.substring(i21, i20), addHyperlink2, i21, i20, z2);
                            i6 = i20;
                        } else {
                            int i22 = i19;
                            iAttributeSet2 = attribute;
                            processRun(textShape, richTextRunAt2, paragraphElement, str2.substring(i22, i4), addHyperlink2, i22, i4, z2);
                            i6 = i4;
                        }
                        i14 = i6;
                        z = true;
                    }
                }
                if (z) {
                    i8 = i2;
                    attribute = iAttributeSet2;
                    i13 = 1;
                }
            } else {
                i4 = i15;
                iAttributeSet2 = attribute;
            }
            if (i9 == 7 || i9 == 9) {
                processRun(textShape, richTextRunAt2, paragraphElement, str, -1, i14, i4, z2);
                i14 = i2;
                i8 = i2;
                attribute = iAttributeSet2;
                i13 = 1;
            } else {
                processRun(textShape, richTextRunAt2, paragraphElement, str2.substring(i14, i4), -1, i14, i4, z2);
                i14 = i4;
                i8 = i2;
                attribute = iAttributeSet2;
                i13 = 1;
            }
        }
        IAttributeSet iAttributeSet3 = attribute;
        int spaceBefore = richTextRunAt.getSpaceBefore();
        if (spaceBefore > 0) {
            iAttributeSet = iAttributeSet3;
            AttrManage.instance().setParaBefore(iAttributeSet, (int) ((((float) spaceBefore) / 100.0f) * ((float) this.maxFontSize) * 1.2f * 20.0f));
        } else {
            iAttributeSet = iAttributeSet3;
            if (spaceBefore < 0) {
                AttrManage.instance().setParaBefore(iAttributeSet, (int) (((float) ((-spaceBefore) / 8)) * 20.0f));
            }
        }
        int spaceAfter = richTextRunAt.getSpaceAfter();
        if (spaceAfter >= 0) {
            AttrManage.instance().setParaAfter(iAttributeSet, (int) ((((float) spaceAfter) / 100.0f) * ((float) this.maxFontSize) * 1.2f * 20.0f));
        } else if (spaceAfter < 0) {
            AttrManage.instance().setParaAfter(iAttributeSet, (int) (((float) ((-spaceAfter) / 8)) * 20.0f));
        }
        paragraphElement.setEndOffset((long) this.offset);
        sectionElement.appendParagraph(paragraphElement, 0);
    }

    private void processWordArtParagraph(SectionElement sectionElement, String str, int i, int i2, int i3) {
        ParagraphElement paragraphElement = new ParagraphElement();
        paragraphElement.setStartOffset((long) this.offset);
        AttrManage.instance().setParaHorizontalAlign(paragraphElement.getAttribute(), 1);
        LeafElement leafElement = new LeafElement(str);
        IAttributeSet attribute = leafElement.getAttribute();
        Paint paint = PaintKit.instance().getPaint();
        int i4 = 12;
        paint.setTextSize((float) 12);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        while (((int) paint.measureText(str)) < i && ((int) Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent))) < i2) {
            i4++;
            paint.setTextSize((float) i4);
            fontMetrics = paint.getFontMetrics();
        }
        AttrManage.instance().setFontSize(leafElement.getAttribute(), (int) (((float) (i4 - 1)) * 0.75f));
        AttrManage.instance().setFontColor(attribute, i3);
        setMaxFontSize(18);
        leafElement.setStartOffset((long) this.offset);
        int length = this.offset + str.length();
        this.offset = length;
        leafElement.setEndOffset((long) length);
        paragraphElement.appendLeaf(leafElement);
        paragraphElement.setEndOffset((long) this.offset);
        sectionElement.appendParagraph(paragraphElement, 0);
    }

    private void processRun(TextShape textShape, RichTextRun richTextRun, ParagraphElement paragraphElement, String str, int i, int i2, int i3, boolean z) {
        int addFontName;
        int i4;
        int i5 = i;
        int i6 = i3;
        Sheet sheet = textShape.getSheet();
        byte metaCharactersType = textShape.getMetaCharactersType();
        String replace = str.replace(Typography.nbsp, ' ');
        int i7 = 0;
        int i8 = 0;
        if (z) {
            int i9 = 0;
            while (i9 < replace.length()) {
                if (replace.charAt(i9) == 10) {
                    int i10 = i2 + i9;
                    TextShape textShape2 = textShape;
                    RichTextRun richTextRun2 = richTextRun;
                    ParagraphElement paragraphElement2 = paragraphElement;
                    int i11 = i;
                    i4 = i9;
                    processRun(textShape2, richTextRun2, paragraphElement2, replace.substring(i8, i9), i11, i2 + i8, i10, false);
                    processRun(textShape2, richTextRun2, paragraphElement2, String.valueOf(11), i11, i10, i10 + 1, false);
                    i8 = i4 + 1;
                } else {
                    i4 = i9;
                }
                i9 = i4 + 1;
            }
            if (i8 < replace.length()) {
                processRun(textShape, richTextRun, paragraphElement, replace.substring(i8, replace.length()), i, i2 + i8, i2 + replace.length(), false);
                i8 = replace.length();
            }
        }
        int i12 = i2 + i8;
        this.maxFontSize = 0;
        if (i6 > i12) {
            if (replace.length() > i6) {
                replace = replace.substring(i12, i6);
            }
            int i13 = 2;
            if (replace.contains("*")) {
                if (metaCharactersType == 2 || metaCharactersType == 3 || metaCharactersType == 5) {
                    replace = replace.replace("*", NumericFormatter.instance().getFormatContents("yyyy/m/d", new Date(System.currentTimeMillis())));
                } else if (metaCharactersType == 4 && this.poiHeadersFooters.getFooterText() != null) {
                    replace = this.poiHeadersFooters.getFooterText();
                }
            }
            LeafElement leafElement = new LeafElement(replace);
            IAttributeSet attribute = leafElement.getAttribute();
            int fontSize = richTextRun.getFontSize();
            AttrManage instance = AttrManage.instance();
            if (fontSize <= 0) {
                fontSize = 18;
            }
            instance.setFontSize(attribute, fontSize);
            setMaxFontSize(richTextRun.getFontSize());
            if (!"\n".equals(replace)) {
                if (richTextRun.getFontName() != null && (addFontName = FontTypefaceManage.instance().addFontName(richTextRun.getFontName())) >= 0) {
                    AttrManage.instance().setFontName(attribute, addFontName);
                }
                AttrManage.instance().setFontColor(attribute, converterColor(richTextRun.getFontColor()));
                AttrManage.instance().setFontBold(attribute, richTextRun.isBold());
                AttrManage.instance().setFontItalic(attribute, richTextRun.isItalic());
                AttrManage instance2 = AttrManage.instance();
                if (richTextRun.isUnderlined()) {
                    i7 = 1;
                }
                instance2.setFontUnderline(attribute, i7);
                AttrManage.instance().setFontStrike(attribute, richTextRun.isStrikethrough());
                int superscript = richTextRun.getSuperscript();
                if (superscript != 0) {
                    AttrManage instance3 = AttrManage.instance();
                    if (superscript > 0) {
                        i13 = 1;
                    }
                    instance3.setFontScript(attribute, i13);
                }
                if (i5 >= 0) {
                    int i14 = -16776961;
                    if (sheet != null) {
                        i14 = FCKit.BGRtoRGB(sheet.getColorScheme().getAccentAndHyperlinkColourRGB());
                    }
                    AttrManage.instance().setFontColor(attribute, i14);
                    AttrManage.instance().setFontUnderline(attribute, 1);
                    AttrManage.instance().setFontUnderlineColr(attribute, i14);
                    AttrManage.instance().setHyperlinkID(attribute, i5);
                }
            }
            leafElement.setStartOffset((long) this.offset);
            int length = this.offset + replace.length();
            this.offset = length;
            leafElement.setEndOffset((long) length);
            paragraphElement.appendLeaf(leafElement);
        }
    }

    public boolean searchContent(File file, String str) throws Exception {
        OEPlaceholderAtom placeholderAtom;
        for (Slide slide : new SlideShow(new HSLFSlideShow(this.control, this.filePath)).getSlides()) {
            for (Shape searchShape : slide.getShapes()) {
                if (searchShape(searchShape, str)) {
                    return true;
                }
            }
            Notes notesSheet = slide.getNotesSheet();
            if (notesSheet != null) {
                for (Shape shape : notesSheet.getShapes()) {
                    if (((shape instanceof AutoShape) || (shape instanceof com.app.office.fc.hslf.model.TextBox)) && (placeholderAtom = ((TextShape) shape).getPlaceholderAtom()) != null && placeholderAtom.getPlaceholderId() == 12 && searchShape(shape, str)) {
                        return true;
                    }
                }
                continue;
            }
        }
        return false;
    }

    public boolean searchShape(Shape shape, String str) {
        StringBuilder sb = new StringBuilder();
        if ((shape instanceof AutoShape) || (shape instanceof com.app.office.fc.hslf.model.TextBox)) {
            sb.append(((TextShape) shape).getText());
            if (sb.indexOf(str) >= 0) {
                return true;
            }
            sb.delete(0, sb.length());
        } else if (shape instanceof ShapeGroup) {
            Shape[] shapes = ((ShapeGroup) shape).getShapes();
            for (Shape searchShape : shapes) {
                if (searchShape(searchShape, str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int converterColor(Color color) {
        return color.getRGB();
    }

    public void setMaxFontSize(int i) {
        if (i > this.maxFontSize) {
            this.maxFontSize = i;
        }
    }

    public boolean isRectangle(TextShape textShape) {
        int shapeType = textShape.getShapeType();
        return shapeType == 1 || shapeType == 2 || shapeType == 202;
    }

    public void processGrpRotation(Shape shape, IShape iShape) {
        float rotation = (float) shape.getRotation();
        if (shape.getFlipHorizontal()) {
            iShape.setFlipHorizontal(true);
            rotation = -rotation;
        }
        if (shape.getFlipVertical()) {
            iShape.setFlipVertical(true);
            rotation = -rotation;
        }
        if ((iShape instanceof LineShape) && ((rotation == 45.0f || rotation == 135.0f || rotation == 225.0f) && !iShape.getFlipHorizontal() && !iShape.getFlipVertical())) {
            rotation -= 90.0f;
        }
        iShape.setRotation(rotation);
    }

    public void dispose() {
        PGModel pGModel;
        if (isReaderFinish()) {
            super.dispose();
            if (this.abortReader && (pGModel = this.model) != null && pGModel.getSlideCount() < 2 && this.poiSlideShow.getSlideCount() > 0) {
                this.model.dispose();
            }
            this.model = null;
            this.filePath = null;
            SlideShow slideShow = this.poiSlideShow;
            if (slideShow != null) {
                try {
                    slideShow.dispose();
                } catch (Exception unused) {
                }
                this.poiSlideShow = null;
            }
            Map<Integer, Integer> map = this.slideMasterIndexs;
            if (map != null) {
                map.clear();
                this.slideMasterIndexs = null;
            }
            Map<Integer, Integer> map2 = this.titleMasterIndexs;
            if (map2 != null) {
                map2.clear();
                this.titleMasterIndexs = null;
            }
            BulletNumberManage.instance().dispose();
            System.gc();
        }
    }
}
