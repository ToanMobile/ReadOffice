package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.ListFormatOverride;
import com.app.office.fc.hwpf.model.ListFormatOverrideLevel;
import com.app.office.fc.hwpf.model.ListTables;
import com.app.office.fc.hwpf.model.PAPX;
import com.app.office.fc.hwpf.model.POIListLevel;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;

public final class ListEntry extends Paragraph {
    private static POILogger log = POILogFactory.getLogger(ListEntry.class);
    POIListLevel _level;
    ListFormatOverrideLevel _overrideLevel;

    public int type() {
        return 4;
    }

    ListEntry(PAPX papx, Range range, ListTables listTables) {
        super(papx, range);
        if (listTables == null || this._props.getIlfo() >= listTables.getOverrideCount()) {
            log.log(POILogger.WARN, (Object) "No ListTables found for ListEntry - document probably partly corrupt, and you may experience problems");
            return;
        }
        ListFormatOverride override = listTables.getOverride(this._props.getIlfo());
        this._overrideLevel = override.getOverrideLevel(this._props.getIlvl());
        this._level = listTables.getLevel(override.getLsid(), this._props.getIlvl());
    }
}
