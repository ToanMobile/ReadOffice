package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.record.EmbeddedObjectRefSubRecord;
import com.app.office.fc.hssf.record.ObjRecord;
import com.app.office.fc.hssf.record.SubRecord;
import com.app.office.fc.poifs.filesystem.DirectoryEntry;
import com.app.office.fc.poifs.filesystem.Entry;
import com.app.office.fc.util.HexDump;
import java.io.IOException;

public final class HSSFObjectData {
    private final ObjRecord _record;
    private final DirectoryEntry _root;

    public HSSFObjectData(ObjRecord objRecord, DirectoryEntry directoryEntry) {
        this._record = objRecord;
        this._root = directoryEntry;
    }

    public String getOLE2ClassName() {
        return findObjectRecord().getOLEClassName();
    }

    public DirectoryEntry getDirectory() throws IOException {
        int intValue = findObjectRecord().getStreamId().intValue();
        String str = "MBD" + HexDump.toHex(intValue);
        Entry entry = this._root.getEntry(str);
        if (entry instanceof DirectoryEntry) {
            return (DirectoryEntry) entry;
        }
        throw new IOException("Stream " + str + " was not an OLE2 directory");
    }

    public byte[] getObjectData() {
        return findObjectRecord().getObjectData();
    }

    public boolean hasDirectoryEntry() {
        Integer streamId = findObjectRecord().getStreamId();
        return (streamId == null || streamId.intValue() == 0) ? false : true;
    }

    /* access modifiers changed from: protected */
    public EmbeddedObjectRefSubRecord findObjectRecord() {
        for (SubRecord next : this._record.getSubRecords()) {
            if (next instanceof EmbeddedObjectRefSubRecord) {
                return (EmbeddedObjectRefSubRecord) next;
            }
        }
        throw new IllegalStateException("Object data does not contain a reference to an embedded object OLE2 directory");
    }
}
