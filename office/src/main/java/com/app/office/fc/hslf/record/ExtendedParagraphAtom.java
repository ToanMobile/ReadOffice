package com.app.office.fc.hslf.record;

import com.app.office.fc.hslf.model.textproperties.AutoNumberTextProp;
import com.app.office.fc.hslf.model.textproperties.TextProp;
import java.util.Iterator;
import java.util.LinkedList;

public final class ExtendedParagraphAtom extends RecordAtom {
    private static long _type = 4012;
    public static TextProp[] extendedParagraphPropTypes = {new TextProp(2, 16777216, "NumberingType"), new TextProp(2, 8388608, "Start")};
    private byte[] _header;
    private LinkedList<AutoNumberTextProp> autoNumberList = new LinkedList<>();

    /* JADX WARNING: Removed duplicated region for block: B:32:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x008f A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ExtendedParagraphAtom(byte[] r11, int r12, int r13) {
        /*
            r10 = this;
            r10.<init>()
            java.util.LinkedList r0 = new java.util.LinkedList
            r0.<init>()
            r10.autoNumberList = r0
            r0 = 8
            if (r13 >= r0) goto L_0x0010
            r13 = 8
        L_0x0010:
            byte[] r1 = new byte[r0]
            r10._header = r1
            r2 = 0
            java.lang.System.arraycopy(r11, r12, r1, r2, r0)
            int r1 = r12 + 8
        L_0x001a:
            int r3 = r12 + r13
            if (r1 >= r3) goto L_0x0096
            r3 = 28
            if (r13 < r3) goto L_0x0096
            int r3 = r13 - r1
            r4 = 4
            if (r3 >= r4) goto L_0x0029
            goto L_0x0096
        L_0x0029:
            com.app.office.fc.hslf.model.textproperties.AutoNumberTextProp r3 = new com.app.office.fc.hslf.model.textproperties.AutoNumberTextProp
            r3.<init>()
            int r4 = com.app.office.fc.util.LittleEndian.getInt(r11, r1)
            r5 = 50331648(0x3000000, float:3.761582E-37)
            if (r4 != r5) goto L_0x0038
            int r4 = r4 >> 1
        L_0x0038:
            int r1 = r1 + 4
            if (r4 == 0) goto L_0x008f
            r5 = 25165824(0x1800000, float:4.7019774E-38)
            if (r4 != r5) goto L_0x0043
            int r1 = r1 + 2
            goto L_0x0045
        L_0x0043:
            int r1 = r1 + 4
        L_0x0045:
            r6 = 0
        L_0x0046:
            com.app.office.fc.hslf.model.textproperties.TextProp[] r7 = extendedParagraphPropTypes
            int r8 = r7.length
            if (r6 >= r8) goto L_0x008b
            r7 = r7[r6]
            int r7 = r7.getMask()
            r7 = r7 & r4
            if (r7 == 0) goto L_0x008b
            short r7 = com.app.office.fc.util.LittleEndian.getShort(r11, r1)
            com.app.office.fc.hslf.model.textproperties.TextProp[] r8 = extendedParagraphPropTypes
            r8 = r8[r6]
            java.lang.String r8 = r8.getName()
            java.lang.String r9 = "NumberingType"
            boolean r8 = r9.equals(r8)
            if (r8 == 0) goto L_0x006c
            r3.setNumberingType(r7)
            goto L_0x007f
        L_0x006c:
            com.app.office.fc.hslf.model.textproperties.TextProp[] r8 = extendedParagraphPropTypes
            r8 = r8[r6]
            java.lang.String r8 = r8.getName()
            java.lang.String r9 = "Start"
            boolean r8 = r9.equals(r8)
            if (r8 == 0) goto L_0x007f
            r3.setStart(r7)
        L_0x007f:
            com.app.office.fc.hslf.model.textproperties.TextProp[] r7 = extendedParagraphPropTypes
            r7 = r7[r6]
            int r7 = r7.getSize()
            int r1 = r1 + r7
            int r6 = r6 + 1
            goto L_0x0046
        L_0x008b:
            if (r4 != r5) goto L_0x008f
            int r1 = r1 + 2
        L_0x008f:
            java.util.LinkedList<com.app.office.fc.hslf.model.textproperties.AutoNumberTextProp> r4 = r10.autoNumberList
            r4.add(r3)
            int r1 = r1 + r0
            goto L_0x001a
        L_0x0096:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hslf.record.ExtendedParagraphAtom.<init>(byte[], int, int):void");
    }

    protected ExtendedParagraphAtom() {
    }

    public LinkedList<AutoNumberTextProp> getExtendedParagraphPropList() {
        return this.autoNumberList;
    }

    public long getRecordType() {
        return _type;
    }

    public void dispose() {
        this._header = null;
        LinkedList<AutoNumberTextProp> linkedList = this.autoNumberList;
        if (linkedList != null) {
            Iterator it = linkedList.iterator();
            while (it.hasNext()) {
                ((AutoNumberTextProp) it.next()).dispose();
            }
            this.autoNumberList.clear();
            this.autoNumberList = null;
        }
    }
}
