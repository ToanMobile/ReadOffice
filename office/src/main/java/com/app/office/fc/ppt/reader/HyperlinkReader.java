package com.app.office.fc.ppt.reader;

import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.PackageRelationshipTypes;
import com.app.office.system.IControl;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class HyperlinkReader {
    private static HyperlinkReader hyperlink = new HyperlinkReader();
    private Map<String, Integer> link;

    public static HyperlinkReader instance() {
        return hyperlink;
    }

    public void getHyperlinkList(IControl iControl, PackagePart packagePart) throws Exception {
        this.link = new Hashtable();
        Iterator<PackageRelationship> it = packagePart.getRelationshipsByType(PackageRelationshipTypes.HYPERLINK_PART).iterator();
        while (it.hasNext()) {
            PackageRelationship next = it.next();
            String id = next.getId();
            if (getLinkIndex(id) < 0) {
                this.link.put(id, Integer.valueOf(iControl.getSysKit().getHyperlinkManage().addHyperlink(next.getTargetURI().toString(), 1)));
            }
        }
    }

    public int getLinkIndex(String str) {
        Integer num;
        Map<String, Integer> map = this.link;
        if (map == null || map.size() <= 0 || (num = this.link.get(str)) == null) {
            return -1;
        }
        return num.intValue();
    }

    public void disposs() {
        Map<String, Integer> map = this.link;
        if (map != null) {
            map.clear();
            this.link = null;
        }
    }
}
