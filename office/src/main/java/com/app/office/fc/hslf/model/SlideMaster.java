package com.app.office.fc.hslf.model;

import com.app.office.fc.hslf.model.textproperties.TextProp;
import com.app.office.fc.hslf.model.textproperties.TextPropCollection;
import com.app.office.fc.hslf.record.MainMaster;
import com.app.office.fc.hslf.record.TxMasterStyleAtom;
import com.app.office.fc.hslf.usermodel.SlideShow;

public final class SlideMaster extends MasterSheet {
    private TextRun[] _runs = findTextRuns(getPPDrawing());
    private TxMasterStyleAtom[] _txmaster;

    public MasterSheet getMasterSheet() {
        return null;
    }

    public SlideMaster(MainMaster mainMaster, int i) {
        super(mainMaster, i);
        int i2 = 0;
        while (true) {
            TextRun[] textRunArr = this._runs;
            if (i2 < textRunArr.length) {
                textRunArr[i2].setSheet(this);
                i2++;
            } else {
                return;
            }
        }
    }

    public TextRun[] getTextRuns() {
        return this._runs;
    }

    public TextProp getStyleAttribute(int i, int i2, String str, boolean z) {
        TextPropCollection[] textPropCollectionArr;
        TextProp textProp = null;
        for (int i3 = i2; i3 >= 0; i3--) {
            if (z) {
                textPropCollectionArr = this._txmaster[i].getCharacterStyles();
            } else {
                textPropCollectionArr = this._txmaster[i].getParagraphStyles();
            }
            if (i3 < textPropCollectionArr.length) {
                textProp = textPropCollectionArr[i3].findByName(str);
            }
            if (textProp != null) {
                break;
            }
        }
        if (textProp != null) {
            return textProp;
        }
        int i4 = 0;
        if (z) {
            if (i != 5) {
                if (i != 6) {
                    if (!(i == 7 || i == 8)) {
                        return null;
                    }
                }
                return getStyleAttribute(i4, i2, str, z);
            }
        } else if (i != 5) {
            if (i != 6) {
                if (!(i == 7 || i == 8)) {
                    return null;
                }
            }
            return getStyleAttribute(i4, i2, str, z);
        }
        i4 = 1;
        return getStyleAttribute(i4, i2, str, z);
    }

    public void setSlideShow(SlideShow slideShow) {
        super.setSlideShow(slideShow);
        if (this._txmaster == null) {
            this._txmaster = new TxMasterStyleAtom[9];
            TxMasterStyleAtom[] txMasterStyleAtoms = ((MainMaster) getSheetContainer()).getTxMasterStyleAtoms();
            for (int i = 0; i < txMasterStyleAtoms.length; i++) {
                this._txmaster[txMasterStyleAtoms[i].getTextType()] = txMasterStyleAtoms[i];
            }
            TxMasterStyleAtom txMasterStyleAtom = getSlideShow().getDocumentRecord().getEnvironment().getTxMasterStyleAtom();
            this._txmaster[txMasterStyleAtom.getTextType()] = txMasterStyleAtom;
        }
    }

    /* access modifiers changed from: protected */
    public void onAddTextShape(TextShape textShape) {
        TextRun textRun = textShape.getTextRun();
        TextRun[] textRunArr = this._runs;
        if (textRunArr == null) {
            this._runs = new TextRun[]{textRun};
            return;
        }
        int length = textRunArr.length + 1;
        TextRun[] textRunArr2 = new TextRun[length];
        System.arraycopy(textRunArr, 0, textRunArr2, 0, textRunArr.length);
        textRunArr2[length - 1] = textRun;
        this._runs = textRunArr2;
    }

    public TxMasterStyleAtom[] getTxMasterStyleAtoms() {
        return this._txmaster;
    }

    public void dispose() {
        super.dispose();
        TextRun[] textRunArr = this._runs;
        if (textRunArr != null) {
            for (TextRun dispose : textRunArr) {
                dispose.dispose();
            }
            this._runs = null;
        }
        TxMasterStyleAtom[] txMasterStyleAtomArr = this._txmaster;
        if (txMasterStyleAtomArr != null) {
            for (TxMasterStyleAtom txMasterStyleAtom : txMasterStyleAtomArr) {
                if (txMasterStyleAtom != null) {
                    txMasterStyleAtom.dispose();
                }
            }
            this._txmaster = null;
        }
    }
}
