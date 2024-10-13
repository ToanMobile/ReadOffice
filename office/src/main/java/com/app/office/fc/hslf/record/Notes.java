package com.app.office.fc.hslf.record;

public final class Notes extends SheetContainer {
    private static long _type = 1008;
    private ColorSchemeAtom _colorScheme;
    private byte[] _header;
    private NotesAtom notesAtom;
    private PPDrawing ppDrawing;

    public NotesAtom getNotesAtom() {
        return this.notesAtom;
    }

    public PPDrawing getPPDrawing() {
        return this.ppDrawing;
    }

    protected Notes(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        for (int i3 = 0; i3 < this._children.length; i3++) {
            if (this._children[i3] instanceof NotesAtom) {
                this.notesAtom = (NotesAtom) this._children[i3];
            }
            if (this._children[i3] instanceof PPDrawing) {
                this.ppDrawing = (PPDrawing) this._children[i3];
            }
            if (this.ppDrawing != null && (this._children[i3] instanceof ColorSchemeAtom)) {
                this._colorScheme = (ColorSchemeAtom) this._children[i3];
            }
        }
    }

    public long getRecordType() {
        return _type;
    }

    public ColorSchemeAtom getColorScheme() {
        return this._colorScheme;
    }

    public void dispose() {
        super.dispose();
        NotesAtom notesAtom2 = this.notesAtom;
        if (notesAtom2 != null) {
            notesAtom2.dispose();
            this.notesAtom = null;
        }
        PPDrawing pPDrawing = this.ppDrawing;
        if (pPDrawing != null) {
            pPDrawing.dispose();
            this.ppDrawing = null;
        }
        ColorSchemeAtom colorSchemeAtom = this._colorScheme;
        if (colorSchemeAtom != null) {
            colorSchemeAtom.dispose();
            this._colorScheme = null;
        }
    }
}
