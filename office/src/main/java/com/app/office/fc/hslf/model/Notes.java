package com.app.office.fc.hslf.model;

public final class Notes extends Sheet {
    private TextRun[] _runs = findTextRuns(getPPDrawing());

    public MasterSheet getMasterSheet() {
        return null;
    }

    public Notes(com.app.office.fc.hslf.record.Notes notes) {
        super(notes, notes.getNotesAtom().getSlideID());
        int i = 0;
        while (true) {
            TextRun[] textRunArr = this._runs;
            if (i < textRunArr.length) {
                textRunArr[i].setSheet(this);
                i++;
            } else {
                return;
            }
        }
    }

    public TextRun[] getTextRuns() {
        return this._runs;
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
