package com.app.office.fc.hslf.model;

import com.app.office.fc.hslf.model.textproperties.TextProp;
import com.app.office.fc.hslf.record.Slide;

public final class TitleMaster extends MasterSheet {
    private TextRun[] _runs = findTextRuns(getPPDrawing());

    public TitleMaster(Slide slide, int i) {
        super(slide, i);
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
        MasterSheet masterSheet = getMasterSheet();
        if (masterSheet == null) {
            return null;
        }
        return masterSheet.getStyleAttribute(i, i2, str, z);
    }

    public MasterSheet getMasterSheet() {
        SlideMaster[] slidesMasters = getSlideShow().getSlidesMasters();
        int masterID = ((Slide) getSheetContainer()).getSlideAtom().getMasterID();
        for (int i = 0; i < slidesMasters.length; i++) {
            if (masterID == slidesMasters[i]._getSheetNumber()) {
                return slidesMasters[i];
            }
        }
        return null;
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
    }
}
