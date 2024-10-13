package com.app.office.fc.hslf.usermodel;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.hslf.HSLFSlideShow;
import com.app.office.fc.hslf.exceptions.CorruptPowerPointFileException;
import com.app.office.fc.hslf.model.HeadersFooters;
import com.app.office.fc.hslf.model.Hyperlink;
import com.app.office.fc.hslf.model.Notes;
import com.app.office.fc.hslf.model.Sheet;
import com.app.office.fc.hslf.model.Slide;
import com.app.office.fc.hslf.model.SlideMaster;
import com.app.office.fc.hslf.model.TitleMaster;
import com.app.office.fc.hslf.record.Document;
import com.app.office.fc.hslf.record.DocumentAtom;
import com.app.office.fc.hslf.record.ExAviMovie;
import com.app.office.fc.hslf.record.ExControl;
import com.app.office.fc.hslf.record.ExHyperlink;
import com.app.office.fc.hslf.record.ExMCIMovie;
import com.app.office.fc.hslf.record.ExObjList;
import com.app.office.fc.hslf.record.ExObjListAtom;
import com.app.office.fc.hslf.record.ExOleObjAtom;
import com.app.office.fc.hslf.record.ExVideoContainer;
import com.app.office.fc.hslf.record.ExtendedParagraphHeaderAtom;
import com.app.office.fc.hslf.record.ExtendedPresRuleContainer;
import com.app.office.fc.hslf.record.FontCollection;
import com.app.office.fc.hslf.record.HeadersFootersContainer;
import com.app.office.fc.hslf.record.MainMaster;
import com.app.office.fc.hslf.record.PersistPtrHolder;
import com.app.office.fc.hslf.record.PositionDependentRecord;
import com.app.office.fc.hslf.record.PositionDependentRecordContainer;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.RecordContainer;
import com.app.office.fc.hslf.record.RecordTypes;
import com.app.office.fc.hslf.record.SlideListWithText;
import com.app.office.java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public final class SlideShow {
    private Document _documentRecord;
    private FontCollection _fonts;
    private HSLFSlideShow _hslfSlideShow;
    private SlideMaster[] _masters;
    private Record[] _mostRecentCoreRecords;
    private Notes[] _notes;
    private Record[] _records;
    private Hashtable<Integer, Integer> _sheetIdToCoreRecordsLookup;
    private Slide[] _slides;
    private TitleMaster[] _titleMasters;
    private boolean isGetThumbnail;

    public void write(OutputStream outputStream) throws IOException {
    }

    public SlideShow(HSLFSlideShow hSLFSlideShow) {
        this(hSLFSlideShow, false);
    }

    public SlideShow(HSLFSlideShow hSLFSlideShow, boolean z) {
        this._hslfSlideShow = hSLFSlideShow;
        Record[] records = hSLFSlideShow.getRecords();
        this._records = records;
        this.isGetThumbnail = z;
        for (Record record : records) {
            if (record instanceof RecordContainer) {
                RecordContainer.handleParentAwareRecords((RecordContainer) record);
            }
        }
        findMostRecentCoreRecords();
        buildSlidesAndNotes();
    }

    private void findMostRecentCoreRecords() {
        Hashtable hashtable = new Hashtable();
        int i = 0;
        int i2 = 0;
        while (true) {
            Record[] recordArr = this._records;
            if (i2 >= recordArr.length) {
                break;
            }
            if (recordArr[i2] instanceof PersistPtrHolder) {
                PersistPtrHolder persistPtrHolder = (PersistPtrHolder) recordArr[i2];
                int[] knownSlideIDs = persistPtrHolder.getKnownSlideIDs();
                for (int valueOf : knownSlideIDs) {
                    Integer valueOf2 = Integer.valueOf(valueOf);
                    if (hashtable.containsKey(valueOf2)) {
                        hashtable.remove(valueOf2);
                    }
                }
                Hashtable<Integer, Integer> slideLocationsLookup = persistPtrHolder.getSlideLocationsLookup();
                for (int valueOf3 : knownSlideIDs) {
                    Integer valueOf4 = Integer.valueOf(valueOf3);
                    hashtable.put(valueOf4, slideLocationsLookup.get(valueOf4));
                }
            }
            i2++;
        }
        this._mostRecentCoreRecords = new Record[hashtable.size()];
        this._sheetIdToCoreRecordsLookup = new Hashtable<>();
        int length = this._mostRecentCoreRecords.length;
        int[] iArr = new int[length];
        Enumeration keys = hashtable.keys();
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = ((Integer) keys.nextElement()).intValue();
        }
        Arrays.sort(iArr);
        for (int i4 = 0; i4 < length; i4++) {
            this._sheetIdToCoreRecordsLookup.put(Integer.valueOf(iArr[i4]), Integer.valueOf(i4));
        }
        int i5 = 0;
        while (true) {
            Record[] recordArr2 = this._records;
            if (i5 >= recordArr2.length) {
                break;
            }
            if (recordArr2[i5] instanceof PositionDependentRecord) {
                PositionDependentRecord positionDependentRecord = (PositionDependentRecord) recordArr2[i5];
                Integer valueOf5 = Integer.valueOf(positionDependentRecord.getLastOnDiskOffset());
                for (int i6 = 0; i6 < length; i6++) {
                    Integer valueOf6 = Integer.valueOf(iArr[i6]);
                    if (((Integer) hashtable.get(valueOf6)).equals(valueOf5)) {
                        int intValue = this._sheetIdToCoreRecordsLookup.get(valueOf6).intValue();
                        if (positionDependentRecord instanceof PositionDependentRecordContainer) {
                            ((PositionDependentRecordContainer) this._records[i5]).setSheetId(valueOf6.intValue());
                        }
                        this._mostRecentCoreRecords[intValue] = this._records[i5];
                    }
                }
            }
            i5++;
        }
        while (true) {
            Record[] recordArr3 = this._mostRecentCoreRecords;
            if (i < recordArr3.length) {
                if (recordArr3[i] != null && recordArr3[i].getRecordType() == ((long) RecordTypes.Document.typeID)) {
                    Document document = (Document) this._mostRecentCoreRecords[i];
                    this._documentRecord = document;
                    this._fonts = document.getEnvironment().getFontCollection();
                }
                i++;
            } else {
                return;
            }
        }
    }

    private Record getCoreRecordForSAS(SlideListWithText.SlideAtomsSet slideAtomsSet) {
        return getCoreRecordForRefID(slideAtomsSet.getSlidePersistAtom().getRefID());
    }

    private Record getCoreRecordForRefID(int i) {
        Integer num = this._sheetIdToCoreRecordsLookup.get(Integer.valueOf(i));
        if (num != null) {
            return this._mostRecentCoreRecords[num.intValue()];
        }
        return null;
    }

    private void buildSlidesAndNotes() {
        com.app.office.fc.hslf.record.Notes[] notesArr;
        com.app.office.fc.hslf.record.Slide[] slideArr;
        Notes notes;
        Integer num;
        ExtendedPresRuleContainer extendedPresRuleContainer;
        Document document = this._documentRecord;
        if (document != null) {
            SlideListWithText masterSlideListWithText = document.getMasterSlideListWithText();
            SlideListWithText slideSlideListWithText = this._documentRecord.getSlideSlideListWithText();
            SlideListWithText notesSlideListWithText = this._documentRecord.getNotesSlideListWithText();
            if (masterSlideListWithText != null) {
                SlideListWithText.SlideAtomsSet[] slideAtomsSets = masterSlideListWithText.getSlideAtomsSets();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (int i = 0; i < slideAtomsSets.length; i++) {
                    Record coreRecordForSAS = getCoreRecordForSAS(slideAtomsSets[i]);
                    int slideIdentifier = slideAtomsSets[i].getSlidePersistAtom().getSlideIdentifier();
                    if (coreRecordForSAS instanceof com.app.office.fc.hslf.record.Slide) {
                        TitleMaster titleMaster = new TitleMaster((com.app.office.fc.hslf.record.Slide) coreRecordForSAS, slideIdentifier);
                        titleMaster.setSlideShow(this);
                        arrayList2.add(titleMaster);
                    } else if (coreRecordForSAS instanceof MainMaster) {
                        SlideMaster slideMaster = new SlideMaster((MainMaster) coreRecordForSAS, slideIdentifier);
                        slideMaster.setSlideShow(this);
                        arrayList.add(slideMaster);
                    }
                }
                SlideMaster[] slideMasterArr = new SlideMaster[arrayList.size()];
                this._masters = slideMasterArr;
                arrayList.toArray(slideMasterArr);
                TitleMaster[] titleMasterArr = new TitleMaster[arrayList2.size()];
                this._titleMasters = titleMasterArr;
                arrayList2.toArray(titleMasterArr);
            }
            Hashtable hashtable = new Hashtable();
            if (notesSlideListWithText == null) {
                notesArr = new com.app.office.fc.hslf.record.Notes[0];
            } else {
                SlideListWithText.SlideAtomsSet[] slideAtomsSets2 = notesSlideListWithText.getSlideAtomsSets();
                ArrayList arrayList3 = new ArrayList();
                for (int i2 = 0; i2 < slideAtomsSets2.length; i2++) {
                    Record coreRecordForSAS2 = getCoreRecordForSAS(slideAtomsSets2[i2]);
                    if (coreRecordForSAS2 instanceof com.app.office.fc.hslf.record.Notes) {
                        arrayList3.add((com.app.office.fc.hslf.record.Notes) coreRecordForSAS2);
                        hashtable.put(Integer.valueOf(slideAtomsSets2[i2].getSlidePersistAtom().getSlideIdentifier()), Integer.valueOf(i2));
                    }
                }
                notesArr = (com.app.office.fc.hslf.record.Notes[]) arrayList3.toArray(new com.app.office.fc.hslf.record.Notes[arrayList3.size()]);
            }
            SlideListWithText.SlideAtomsSet[] slideAtomsSetArr = new SlideListWithText.SlideAtomsSet[0];
            if (slideSlideListWithText == null) {
                slideArr = new com.app.office.fc.hslf.record.Slide[0];
            } else {
                slideAtomsSetArr = slideSlideListWithText.getSlideAtomsSets();
                slideArr = new com.app.office.fc.hslf.record.Slide[slideAtomsSetArr.length];
                for (int i3 = 0; i3 < slideAtomsSetArr.length; i3++) {
                    Record coreRecordForSAS3 = getCoreRecordForSAS(slideAtomsSetArr[i3]);
                    if (coreRecordForSAS3 instanceof com.app.office.fc.hslf.record.Slide) {
                        slideArr[i3] = (com.app.office.fc.hslf.record.Slide) coreRecordForSAS3;
                    }
                }
            }
            int i4 = 1;
            this._notes = new Notes[(this.isGetThumbnail ? Math.min(notesArr.length, 1) : notesArr.length)];
            int i5 = 0;
            while (true) {
                Notes[] notesArr2 = this._notes;
                if (i5 >= notesArr2.length) {
                    break;
                }
                notesArr2[i5] = new Notes(notesArr[i5]);
                this._notes[i5].setSlideShow(this);
                i5++;
            }
            ExtendedPresRuleContainer.ExtendedParaAtomsSet[] extendedParaAtomsSets = (this._documentRecord.getList() == null || (extendedPresRuleContainer = this._documentRecord.getList().getExtendedPresRuleContainer()) == null) ? null : extendedPresRuleContainer.getExtendedParaAtomsSets();
            if (!this.isGetThumbnail) {
                i4 = slideArr.length;
            }
            this._slides = new Slide[i4];
            int i6 = 0;
            while (i6 < this._slides.length) {
                SlideListWithText.SlideAtomsSet slideAtomsSet = slideAtomsSetArr[i6];
                int slideIdentifier2 = slideAtomsSet.getSlidePersistAtom().getSlideIdentifier();
                Vector vector = new Vector();
                if (extendedParaAtomsSets != null) {
                    for (int i7 = 0; i7 < extendedParaAtomsSets.length; i7++) {
                        ExtendedParagraphHeaderAtom extendedParaHeaderAtom = extendedParaAtomsSets[i7].getExtendedParaHeaderAtom();
                        if (extendedParaHeaderAtom != null && extendedParaHeaderAtom.getRefSlideID() == slideIdentifier2) {
                            vector.add(extendedParaAtomsSets[i7]);
                        }
                    }
                }
                ExtendedPresRuleContainer.ExtendedParaAtomsSet[] extendedParaAtomsSetArr = vector.size() > 0 ? (ExtendedPresRuleContainer.ExtendedParaAtomsSet[]) vector.toArray(new ExtendedPresRuleContainer.ExtendedParaAtomsSet[vector.size()]) : null;
                int notesID = slideArr[i6].getSlideAtom().getNotesID();
                if (!(notesID == 0 || (num = (Integer) hashtable.get(Integer.valueOf(notesID))) == null)) {
                    int intValue = num.intValue();
                    Notes[] notesArr3 = this._notes;
                    if (intValue < notesArr3.length) {
                        notes = notesArr3[num.intValue()];
                        int i8 = i6 + 1;
                        this._slides[i6] = new Slide(slideArr[i6], notes, slideAtomsSet, extendedParaAtomsSetArr, slideIdentifier2, i8);
                        this._slides[i6].setSlideShow(this);
                        this._slides[i6].setSlideShowSlideInfoAtom(slideArr[i6].getSlideShowSlideInfoAtom());
                        this._slides[i6].setSlideProgTagsContainer(slideArr[i6].getSlideProgTagsContainer());
                        i6 = i8;
                    }
                }
                notes = null;
                int i82 = i6 + 1;
                this._slides[i6] = new Slide(slideArr[i6], notes, slideAtomsSet, extendedParaAtomsSetArr, slideIdentifier2, i82);
                this._slides[i6].setSlideShow(this);
                this._slides[i6].setSlideShowSlideInfoAtom(slideArr[i6].getSlideShowSlideInfoAtom());
                this._slides[i6].setSlideProgTagsContainer(slideArr[i6].getSlideProgTagsContainer());
                i6 = i82;
            }
            return;
        }
        throw new CorruptPowerPointFileException("The PowerPoint file didn't contain a Document Record in its PersistPtr blocks. It is probably corrupt.");
    }

    public Record[] getMostRecentCoreRecords() {
        return this._mostRecentCoreRecords;
    }

    public Slide[] getSlides() {
        return this._slides;
    }

    public Notes[] getNotes() {
        return this._notes;
    }

    public SlideMaster[] getSlidesMasters() {
        return this._masters;
    }

    public TitleMaster[] getTitleMasters() {
        return this._titleMasters;
    }

    public PictureData[] getPictureData() {
        return this._hslfSlideShow.getPictures();
    }

    public ObjectData[] getEmbeddedObjects() {
        return this._hslfSlideShow.getEmbeddedObjects();
    }

    public SoundData[] getSoundData() {
        return SoundData.find(this._documentRecord);
    }

    public Dimension getPageSize() {
        DocumentAtom documentAtom = this._documentRecord.getDocumentAtom();
        return new Dimension((int) ((((float) documentAtom.getSlideSizeX()) * 72.0f) / 576.0f), (int) ((((float) documentAtom.getSlideSizeY()) * 72.0f) / 576.0f));
    }

    public void setPageSize(Dimension dimension) {
        DocumentAtom documentAtom = this._documentRecord.getDocumentAtom();
        documentAtom.setSlideSizeX((long) (((float) (dimension.width * ShapeKit.MASTER_DPI)) / 72.0f));
        documentAtom.setSlideSizeY((long) (((float) (dimension.height * ShapeKit.MASTER_DPI)) / 72.0f));
    }

    /* access modifiers changed from: protected */
    public FontCollection getFontCollection() {
        return this._fonts;
    }

    public Document getDocumentRecord() {
        return this._documentRecord;
    }

    public void reorderSlide(int i, int i2) {
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException("Old and new slide numbers must be greater than 0");
        }
        Slide[] slideArr = this._slides;
        if (i > slideArr.length || i2 > slideArr.length) {
            throw new IllegalArgumentException("Old and new slide numbers must not exceed the number of slides (" + this._slides.length + ")");
        }
        SlideListWithText slideSlideListWithText = this._documentRecord.getSlideSlideListWithText();
        SlideListWithText.SlideAtomsSet[] slideAtomsSets = slideSlideListWithText.getSlideAtomsSets();
        int i3 = i - 1;
        SlideListWithText.SlideAtomsSet slideAtomsSet = slideAtomsSets[i3];
        int i4 = i2 - 1;
        slideAtomsSets[i3] = slideAtomsSets[i4];
        slideAtomsSets[i4] = slideAtomsSet;
        ArrayList arrayList = new ArrayList();
        int i5 = 0;
        while (i5 < slideAtomsSets.length) {
            arrayList.add(slideAtomsSets[i5].getSlidePersistAtom());
            Record[] slideRecords = slideAtomsSets[i5].getSlideRecords();
            for (Record add : slideRecords) {
                arrayList.add(add);
            }
            Slide slide = this._slides[i5];
            i5++;
            slide.setSlideNumber(i5);
        }
        slideSlideListWithText.setChildRecord((Record[]) arrayList.toArray(new Record[arrayList.size()]));
    }

    public Slide removeSlide(int i) {
        int notesID;
        int length = this._slides.length - 1;
        if (i < 0 || i > length) {
            throw new IllegalArgumentException("Slide index (" + i + ") is out of range (0.." + length + ")");
        }
        SlideListWithText slideSlideListWithText = this._documentRecord.getSlideSlideListWithText();
        SlideListWithText.SlideAtomsSet[] slideAtomsSets = slideSlideListWithText.getSlideAtomsSets();
        Slide slide = null;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (Notes add : getNotes()) {
            arrayList4.add(add);
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            Slide[] slideArr = this._slides;
            if (i2 >= slideArr.length) {
                break;
            }
            if (i2 != i) {
                arrayList3.add(slideArr[i2]);
                arrayList2.add(slideAtomsSets[i2]);
                this._slides[i2].setSlideNumber(i3);
                arrayList.add(slideAtomsSets[i2].getSlidePersistAtom());
                arrayList.addAll(Arrays.asList(slideAtomsSets[i2].getSlideRecords()));
                i3++;
            } else {
                slide = slideArr[i2];
                arrayList4.remove(slideArr[i2].getNotesSheet());
            }
            i2++;
        }
        if (arrayList2.size() == 0) {
            this._documentRecord.removeSlideListWithText(slideSlideListWithText);
        } else {
            slideSlideListWithText.setSlideAtomsSets((SlideListWithText.SlideAtomsSet[]) arrayList2.toArray(new SlideListWithText.SlideAtomsSet[arrayList2.size()]));
            slideSlideListWithText.setChildRecord((Record[]) arrayList.toArray(new Record[arrayList.size()]));
        }
        this._slides = (Slide[]) arrayList3.toArray(new Slide[arrayList3.size()]);
        if (!(slide == null || (notesID = slide.getSlideRecord().getSlideAtom().getNotesID()) == 0)) {
            SlideListWithText notesSlideListWithText = this._documentRecord.getNotesSlideListWithText();
            ArrayList arrayList5 = new ArrayList();
            ArrayList arrayList6 = new ArrayList();
            for (SlideListWithText.SlideAtomsSet slideAtomsSet : notesSlideListWithText.getSlideAtomsSets()) {
                if (slideAtomsSet.getSlidePersistAtom().getSlideIdentifier() != notesID) {
                    arrayList6.add(slideAtomsSet);
                    arrayList5.add(slideAtomsSet.getSlidePersistAtom());
                    if (slideAtomsSet.getSlideRecords() != null) {
                        arrayList5.addAll(Arrays.asList(slideAtomsSet.getSlideRecords()));
                    }
                }
            }
            if (arrayList6.size() == 0) {
                this._documentRecord.removeSlideListWithText(notesSlideListWithText);
            } else {
                notesSlideListWithText.setSlideAtomsSets((SlideListWithText.SlideAtomsSet[]) arrayList6.toArray(new SlideListWithText.SlideAtomsSet[arrayList6.size()]));
                notesSlideListWithText.setChildRecord((Record[]) arrayList5.toArray(new Record[arrayList5.size()]));
            }
        }
        this._notes = (Notes[]) arrayList4.toArray(new Notes[arrayList4.size()]);
        return slide;
    }

    public int getNumberOfFonts() {
        return getDocumentRecord().getEnvironment().getFontCollection().getNumberOfFonts();
    }

    public HeadersFooters getSlideHeadersFooters() {
        HeadersFootersContainer headersFootersContainer;
        boolean z = false;
        boolean equals = "___PPT12".equals(getSlidesMasters()[0].getProgrammableTag());
        Record[] childRecords = this._documentRecord.getChildRecords();
        int i = 0;
        while (true) {
            if (i < childRecords.length) {
                if ((childRecords[i] instanceof HeadersFootersContainer) && ((HeadersFootersContainer) childRecords[i]).getOptions() == 63) {
                    headersFootersContainer = (HeadersFootersContainer) childRecords[i];
                    break;
                }
                i++;
            } else {
                headersFootersContainer = null;
                break;
            }
        }
        if (headersFootersContainer == null) {
            headersFootersContainer = new HeadersFootersContainer(63);
            z = true;
        }
        return new HeadersFooters(headersFootersContainer, this, z, equals);
    }

    public HeadersFooters getNotesHeadersFooters() {
        HeadersFootersContainer headersFootersContainer;
        boolean z;
        boolean equals = "___PPT12".equals(getSlidesMasters()[0].getProgrammableTag());
        Record[] childRecords = this._documentRecord.getChildRecords();
        int i = 0;
        while (true) {
            if (i < childRecords.length) {
                if ((childRecords[i] instanceof HeadersFootersContainer) && ((HeadersFootersContainer) childRecords[i]).getOptions() == 79) {
                    headersFootersContainer = (HeadersFootersContainer) childRecords[i];
                    break;
                }
                i++;
            } else {
                headersFootersContainer = null;
                break;
            }
        }
        if (headersFootersContainer == null) {
            headersFootersContainer = new HeadersFootersContainer(79);
            z = true;
        } else {
            z = false;
        }
        if (equals) {
            Notes[] notesArr = this._notes;
            if (notesArr.length > 0) {
                return new HeadersFooters(headersFootersContainer, (Sheet) notesArr[0], z, equals);
            }
        }
        return new HeadersFooters(headersFootersContainer, this, z, equals);
    }

    public int addMovie(String str, int i) {
        ExMCIMovie exMCIMovie;
        ExObjList exObjList = (ExObjList) this._documentRecord.findFirstOfType((long) RecordTypes.ExObjList.typeID);
        if (exObjList == null) {
            exObjList = new ExObjList();
            Document document = this._documentRecord;
            document.addChildAfter(exObjList, document.getDocumentAtom());
        }
        ExObjListAtom exObjListAtom = exObjList.getExObjListAtom();
        int objectIDSeed = ((int) exObjListAtom.getObjectIDSeed()) + 1;
        exObjListAtom.setObjectIDSeed(objectIDSeed);
        if (i == 1) {
            exMCIMovie = new ExMCIMovie();
        } else if (i == 2) {
            exMCIMovie = new ExAviMovie();
        } else {
            throw new IllegalArgumentException("Unsupported Movie: " + i);
        }
        exObjList.appendChildRecord(exMCIMovie);
        ExVideoContainer exVideo = exMCIMovie.getExVideo();
        exVideo.getExMediaAtom().setObjectId(objectIDSeed);
        exVideo.getExMediaAtom().setMask(15204352);
        exVideo.getPathAtom().setText(str);
        return objectIDSeed;
    }

    public int addControl(String str, String str2) {
        ExObjList exObjList = (ExObjList) this._documentRecord.findFirstOfType((long) RecordTypes.ExObjList.typeID);
        if (exObjList == null) {
            exObjList = new ExObjList();
            Document document = this._documentRecord;
            document.addChildAfter(exObjList, document.getDocumentAtom());
        }
        ExObjListAtom exObjListAtom = exObjList.getExObjListAtom();
        int objectIDSeed = ((int) exObjListAtom.getObjectIDSeed()) + 1;
        exObjListAtom.setObjectIDSeed(objectIDSeed);
        ExControl exControl = new ExControl();
        ExOleObjAtom exOleObjAtom = exControl.getExOleObjAtom();
        exOleObjAtom.setObjID(objectIDSeed);
        exOleObjAtom.setDrawAspect(1);
        exOleObjAtom.setType(2);
        exOleObjAtom.setSubType(0);
        exControl.setProgId(str2);
        exControl.setMenuName(str);
        exControl.setClipboardName(str);
        exObjList.addChildAfter(exControl, exObjListAtom);
        return objectIDSeed;
    }

    public int addHyperlink(Hyperlink hyperlink) {
        ExObjList exObjList = (ExObjList) this._documentRecord.findFirstOfType((long) RecordTypes.ExObjList.typeID);
        if (exObjList == null) {
            exObjList = new ExObjList();
            Document document = this._documentRecord;
            document.addChildAfter(exObjList, document.getDocumentAtom());
        }
        ExObjListAtom exObjListAtom = exObjList.getExObjListAtom();
        int objectIDSeed = ((int) exObjListAtom.getObjectIDSeed()) + 1;
        exObjListAtom.setObjectIDSeed(objectIDSeed);
        ExHyperlink exHyperlink = new ExHyperlink();
        exHyperlink.getExHyperlinkAtom().setNumber(objectIDSeed);
        exHyperlink.setLinkURL(hyperlink.getAddress());
        exHyperlink.setLinkTitle(hyperlink.getTitle());
        exObjList.addChildAfter(exHyperlink, exObjListAtom);
        hyperlink.setId(objectIDSeed);
        return objectIDSeed;
    }

    public int getSlideCount() {
        return this._slides.length;
    }

    public Slide getSlide(int i) {
        if (i < 0 || i >= getSlideCount()) {
            return null;
        }
        return this._slides[i];
    }

    public void dispose() {
        HSLFSlideShow hSLFSlideShow = this._hslfSlideShow;
        if (hSLFSlideShow != null) {
            hSLFSlideShow.dispose();
            this._hslfSlideShow = null;
        }
        Record[] recordArr = this._records;
        if (recordArr != null) {
            for (Record dispose : recordArr) {
                dispose.dispose();
            }
            this._records = null;
        }
        Record[] recordArr2 = this._mostRecentCoreRecords;
        if (recordArr2 != null) {
            for (Record dispose2 : recordArr2) {
                dispose2.dispose();
            }
            this._mostRecentCoreRecords = null;
        }
        Hashtable<Integer, Integer> hashtable = this._sheetIdToCoreRecordsLookup;
        if (hashtable != null) {
            hashtable.clear();
            this._sheetIdToCoreRecordsLookup = null;
        }
        Document document = this._documentRecord;
        if (document != null) {
            document.dispose();
            this._documentRecord = null;
        }
        SlideMaster[] slideMasterArr = this._masters;
        if (slideMasterArr != null) {
            for (SlideMaster dispose3 : slideMasterArr) {
                dispose3.dispose();
            }
            this._masters = null;
        }
        TitleMaster[] titleMasterArr = this._titleMasters;
        if (titleMasterArr != null) {
            for (TitleMaster dispose4 : titleMasterArr) {
                dispose4.dispose();
            }
            this._titleMasters = null;
        }
        Slide[] slideArr = this._slides;
        if (slideArr != null) {
            for (Slide dispose5 : slideArr) {
                dispose5.dispose();
            }
            this._slides = null;
        }
        Notes[] notesArr = this._notes;
        if (notesArr != null) {
            for (Notes dispose6 : notesArr) {
                dispose6.dispose();
            }
            this._notes = null;
        }
        FontCollection fontCollection = this._fonts;
        if (fontCollection != null) {
            fontCollection.dispose();
            this._fonts = null;
        }
    }
}
