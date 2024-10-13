package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Internal
public final class FSPATable {
    private final Map<Integer, GenericPropertyNode> _byStart = new LinkedHashMap();

    public FSPATable(byte[] bArr, FileInformationBlock fileInformationBlock, FSPADocumentPart fSPADocumentPart) {
        PlexOfCps plexOfCps = new PlexOfCps(bArr, fileInformationBlock.getFSPAPlcfOffset(fSPADocumentPart), fileInformationBlock.getFSPAPlcfLength(fSPADocumentPart), FSPA.getSize());
        for (int i = 0; i < plexOfCps.length(); i++) {
            GenericPropertyNode property = plexOfCps.getProperty(i);
            this._byStart.put(Integer.valueOf(property.getStart()), property);
        }
    }

    @Deprecated
    public FSPATable(byte[] bArr, int i, int i2, List<TextPiece> list) {
        if (i != 0) {
            PlexOfCps plexOfCps = new PlexOfCps(bArr, i, i2, FSPA.FSPA_SIZE);
            for (int i3 = 0; i3 < plexOfCps.length(); i3++) {
                GenericPropertyNode property = plexOfCps.getProperty(i3);
                this._byStart.put(Integer.valueOf(property.getStart()), property);
            }
        }
    }

    public FSPA getFspaFromCp(int i) {
        GenericPropertyNode genericPropertyNode = this._byStart.get(Integer.valueOf(i));
        if (genericPropertyNode == null) {
            return null;
        }
        return new FSPA(genericPropertyNode.getBytes(), 0);
    }

    public FSPA[] getShapes() {
        ArrayList arrayList = new ArrayList(this._byStart.size());
        for (GenericPropertyNode bytes : this._byStart.values()) {
            arrayList.add(new FSPA(bytes.getBytes(), 0));
        }
        return (FSPA[]) arrayList.toArray(new FSPA[arrayList.size()]);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[FPSA PLC size=");
        stringBuffer.append(this._byStart.size());
        stringBuffer.append("]\n");
        for (Map.Entry<Integer, GenericPropertyNode> key : this._byStart.entrySet()) {
            Integer num = (Integer) key.getKey();
            stringBuffer.append("  ");
            stringBuffer.append(num.toString());
            stringBuffer.append(" => \t");
            try {
                stringBuffer.append(getFspaFromCp(num.intValue()).toString());
            } catch (Exception e) {
                stringBuffer.append(e.getMessage());
            }
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/FSPA PLC]");
        return stringBuffer.toString();
    }
}
