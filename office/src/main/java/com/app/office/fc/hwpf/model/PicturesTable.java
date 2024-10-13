package com.app.office.fc.hwpf.model;

import com.app.office.fc.ddf.DefaultEscherRecordFactory;
import com.app.office.fc.ddf.EscherBSERecord;
import com.app.office.fc.ddf.EscherBlipRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.hwpf.HWPFDocument;
import com.app.office.fc.hwpf.usermodel.CharacterRun;
import com.app.office.fc.hwpf.usermodel.InlineWordArt;
import com.app.office.fc.hwpf.usermodel.Picture;
import com.app.office.fc.hwpf.usermodel.Range;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;
import java.util.List;

@Internal
public final class PicturesTable {
    static final int BLOCK_TYPE_OFFSET = 14;
    static final int MM_MODE_TYPE_OFFSET = 6;
    static final int TYPE_HORIZONTAL_LINE = 14;
    static final int TYPE_IMAGE = 8;
    static final int TYPE_IMAGE_PASTED_FROM_CLIPBOARD = 10;
    static final int TYPE_IMAGE_PASTED_FROM_CLIPBOARD_WORD2000 = 2;
    static final int TYPE_IMAGE_WORD2000 = 0;
    private byte[] _dataStream;
    @Deprecated
    private EscherRecordHolder _dgg;
    private HWPFDocument _document;
    @Deprecated
    private FSPATable _fspa;
    private byte[] _mainStream;

    private boolean isInlineWordArtRecognized(short s, short s2) {
        return s == 6 && s2 == 100;
    }

    private boolean isPictureRecognized(short s, short s2) {
        return s == 8 || s == 10 || (s == 0 && s2 == 100) || (s == 2 && s2 == 100);
    }

    @Deprecated
    public PicturesTable(HWPFDocument hWPFDocument, byte[] bArr, byte[] bArr2, FSPATable fSPATable, EscherRecordHolder escherRecordHolder) {
        this._document = hWPFDocument;
        this._dataStream = bArr;
        this._mainStream = bArr2;
        this._fspa = fSPATable;
        this._dgg = escherRecordHolder;
    }

    public PicturesTable(HWPFDocument hWPFDocument, byte[] bArr, byte[] bArr2) {
        this._document = hWPFDocument;
        this._dataStream = bArr;
        this._mainStream = bArr2;
    }

    public boolean hasInlineWordArt(CharacterRun characterRun) {
        if (characterRun != null && characterRun.isSpecialCharacter() && !characterRun.isObj() && !characterRun.isOle2() && !characterRun.isData() && ("\u0001".equals(characterRun.text()) || "\u0001\u0015".equals(characterRun.text()))) {
            return isBlockContainsInlineWordArt(characterRun.getPicOffset());
        }
        return false;
    }

    public boolean hasPicture(CharacterRun characterRun) {
        if (characterRun != null && characterRun.isSpecialCharacter() && !characterRun.isObj() && !characterRun.isOle2() && !characterRun.isData() && ("\u0001".equals(characterRun.text()) || "\u0001\u0015".equals(characterRun.text()))) {
            return isBlockContainsImage(characterRun.getPicOffset());
        }
        return false;
    }

    public boolean hasEscherPicture(CharacterRun characterRun) {
        return characterRun.isSpecialCharacter() && !characterRun.isObj() && !characterRun.isOle2() && !characterRun.isData() && characterRun.text().startsWith("\b");
    }

    public boolean hasHorizontalLine(CharacterRun characterRun) {
        if (!characterRun.isSpecialCharacter() || !"\u0001".equals(characterRun.text())) {
            return false;
        }
        return isBlockContainsHorizontalLine(characterRun.getPicOffset());
    }

    private static short getBlockType(byte[] bArr, int i) {
        return LittleEndian.getShort(bArr, i + 14);
    }

    private static short getMmMode(byte[] bArr, int i) {
        return LittleEndian.getShort(bArr, i + 6);
    }

    public Picture extractPicture(String str, CharacterRun characterRun, boolean z) {
        if (hasPicture(characterRun)) {
            try {
                return new Picture(str, characterRun.getPicOffset(), this._dataStream, z);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public InlineWordArt extracInlineWordArt(CharacterRun characterRun) {
        if (hasInlineWordArt(characterRun)) {
            return new InlineWordArt(this._dataStream, characterRun.getPicOffset());
        }
        return null;
    }

    private void searchForPictures(List<EscherRecord> list, List<Picture> list2) {
        for (EscherRecord next : list) {
            if (next instanceof EscherBSERecord) {
                EscherBSERecord escherBSERecord = (EscherBSERecord) next;
                EscherBlipRecord blipRecord = escherBSERecord.getBlipRecord();
                if (blipRecord != null) {
                    list2.add(new Picture(blipRecord.getPicturedata()));
                } else if (escherBSERecord.getOffset() > 0) {
                    DefaultEscherRecordFactory defaultEscherRecordFactory = new DefaultEscherRecordFactory();
                    EscherRecord createRecord = defaultEscherRecordFactory.createRecord(this._mainStream, escherBSERecord.getOffset());
                    if (createRecord instanceof EscherBlipRecord) {
                        createRecord.fillFields(this._mainStream, escherBSERecord.getOffset(), defaultEscherRecordFactory);
                        list2.add(new Picture(((EscherBlipRecord) createRecord).getPicturedata()));
                    }
                }
            }
            searchForPictures(next.getChildRecords(), list2);
        }
    }

    public List<Picture> getAllPictures(String str) {
        Picture extractPicture;
        ArrayList arrayList = new ArrayList();
        Range overallRange = this._document.getOverallRange();
        for (int i = 0; i < overallRange.numCharacterRuns(); i++) {
            CharacterRun characterRun = overallRange.getCharacterRun(i);
            if (!(characterRun == null || (extractPicture = extractPicture(str, characterRun, false)) == null)) {
                arrayList.add(extractPicture);
            }
        }
        searchForPictures(this._dgg.getEscherRecords(), arrayList);
        return arrayList;
    }

    private boolean isBlockContainsImage(int i) {
        return isPictureRecognized(getBlockType(this._dataStream, i), getMmMode(this._dataStream, i));
    }

    private boolean isBlockContainsHorizontalLine(int i) {
        return getBlockType(this._dataStream, i) == 14 && getMmMode(this._dataStream, i) == 100;
    }

    private boolean isBlockContainsInlineWordArt(int i) {
        return isInlineWordArtRecognized(getBlockType(this._dataStream, i), getMmMode(this._dataStream, i));
    }
}
