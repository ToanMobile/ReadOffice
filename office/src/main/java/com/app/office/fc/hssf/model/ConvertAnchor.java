package com.app.office.fc.hssf.model;

import com.app.office.fc.ddf.EscherChildAnchorRecord;
import com.app.office.fc.ddf.EscherClientAnchorRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.hssf.usermodel.HSSFAnchor;
import com.app.office.fc.hssf.usermodel.HSSFChildAnchor;
import com.app.office.fc.hssf.usermodel.HSSFClientAnchor;

public class ConvertAnchor {
    public static EscherRecord createAnchor(HSSFAnchor hSSFAnchor) {
        if (hSSFAnchor instanceof HSSFClientAnchor) {
            HSSFClientAnchor hSSFClientAnchor = (HSSFClientAnchor) hSSFAnchor;
            EscherClientAnchorRecord escherClientAnchorRecord = new EscherClientAnchorRecord();
            escherClientAnchorRecord.setRecordId(EscherClientAnchorRecord.RECORD_ID);
            escherClientAnchorRecord.setOptions(0);
            escherClientAnchorRecord.setFlag((short) hSSFClientAnchor.getAnchorType());
            escherClientAnchorRecord.setCol1((short) Math.min(hSSFClientAnchor.getCol1(), hSSFClientAnchor.getCol2()));
            escherClientAnchorRecord.setDx1((short) hSSFClientAnchor.getDx1());
            escherClientAnchorRecord.setRow1((short) Math.min(hSSFClientAnchor.getRow1(), hSSFClientAnchor.getRow2()));
            escherClientAnchorRecord.setDy1((short) hSSFClientAnchor.getDy1());
            escherClientAnchorRecord.setCol2((short) Math.max(hSSFClientAnchor.getCol1(), hSSFClientAnchor.getCol2()));
            escherClientAnchorRecord.setDx2((short) hSSFClientAnchor.getDx2());
            escherClientAnchorRecord.setRow2((short) Math.max(hSSFClientAnchor.getRow1(), hSSFClientAnchor.getRow2()));
            escherClientAnchorRecord.setDy2((short) hSSFClientAnchor.getDy2());
            return escherClientAnchorRecord;
        }
        HSSFChildAnchor hSSFChildAnchor = (HSSFChildAnchor) hSSFAnchor;
        EscherChildAnchorRecord escherChildAnchorRecord = new EscherChildAnchorRecord();
        escherChildAnchorRecord.setRecordId(EscherChildAnchorRecord.RECORD_ID);
        escherChildAnchorRecord.setOptions(0);
        escherChildAnchorRecord.setDx1((short) Math.min(hSSFChildAnchor.getDx1(), hSSFChildAnchor.getDx2()));
        escherChildAnchorRecord.setDy1((short) Math.min(hSSFChildAnchor.getDy1(), hSSFChildAnchor.getDy2()));
        escherChildAnchorRecord.setDx2((short) Math.max(hSSFChildAnchor.getDx2(), hSSFChildAnchor.getDx1()));
        escherChildAnchorRecord.setDy2((short) Math.max(hSSFChildAnchor.getDy2(), hSSFChildAnchor.getDy1()));
        return escherChildAnchorRecord;
    }
}
