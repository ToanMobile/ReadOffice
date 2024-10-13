package com.app.office.fc.hssf.model;

import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherProperty;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.hssf.record.CommonObjectDataSubRecord;
import com.app.office.fc.hssf.record.NoteRecord;
import com.app.office.fc.hssf.record.NoteStructureSubRecord;
import com.app.office.fc.hssf.record.ObjRecord;
import com.app.office.fc.hssf.record.SubRecord;
import com.app.office.fc.hssf.usermodel.HSSFComment;
import com.app.office.fc.hssf.usermodel.HSSFShape;
import java.util.Iterator;
import java.util.List;

public final class CommentShape extends TextboxShape {
    private NoteRecord _note;

    /* access modifiers changed from: package-private */
    public int getCmoObjectId(int i) {
        return i;
    }

    public CommentShape(HSSFComment hSSFComment, int i) {
        super(hSSFComment, i);
        this._note = createNoteRecord(hSSFComment, i);
        ObjRecord objRecord = getObjRecord();
        List<SubRecord> subRecords = objRecord.getSubRecords();
        int i2 = 0;
        for (int i3 = 0; i3 < subRecords.size(); i3++) {
            SubRecord subRecord = subRecords.get(i3);
            if (subRecord instanceof CommonObjectDataSubRecord) {
                ((CommonObjectDataSubRecord) subRecord).setAutofill(false);
                i2 = i3;
            }
        }
        objRecord.addSubRecord(i2 + 1, new NoteStructureSubRecord());
    }

    private NoteRecord createNoteRecord(HSSFComment hSSFComment, int i) {
        NoteRecord noteRecord = new NoteRecord();
        noteRecord.setColumn(hSSFComment.getColumn());
        noteRecord.setRow(hSSFComment.getRow());
        noteRecord.setFlags(hSSFComment.isVisible() ? (short) 2 : 0);
        noteRecord.setShapeId(i);
        noteRecord.setAuthor(hSSFComment.getAuthor() == null ? "" : hSSFComment.getAuthor());
        return noteRecord;
    }

    /* access modifiers changed from: protected */
    public int addStandardOptions(HSSFShape hSSFShape, EscherOptRecord escherOptRecord) {
        super.addStandardOptions(hSSFShape, escherOptRecord);
        Iterator<EscherProperty> it = escherOptRecord.getEscherProperties().iterator();
        while (it.hasNext()) {
            short id = it.next().getId();
            if (!(id == 387 || id == 448 || id == 959)) {
                switch (id) {
                    case 129:
                    case 130:
                    case 131:
                    case 132:
                        break;
                }
            }
            it.remove();
        }
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(959, ((HSSFComment) hSSFShape).isVisible() ? 655360 : 655362));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.SHADOWSTYLE__SHADOWOBSURED, 196611));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(513, 0));
        escherOptRecord.sortProperties();
        return escherOptRecord.getEscherProperties().size();
    }

    public NoteRecord getNoteRecord() {
        return this._note;
    }
}
