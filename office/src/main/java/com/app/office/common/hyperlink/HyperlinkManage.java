package com.app.office.common.hyperlink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HyperlinkManage {
    private Map<String, Integer> hlinkIndexs = new HashMap();
    private List<Hyperlink> hlinks = new ArrayList();

    public Hyperlink getHyperlink(int i) {
        if (i < 0 || i >= this.hlinks.size()) {
            return null;
        }
        return this.hlinks.get(i);
    }

    public Integer getHyperlinkIndex(String str) {
        return this.hlinkIndexs.get(str);
    }

    public int addHyperlink(String str, int i) {
        Integer num = this.hlinkIndexs.get(str);
        if (num != null) {
            return num.intValue();
        }
        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setLinkType(i);
        hyperlink.setAddress(str);
        int size = this.hlinks.size();
        this.hlinks.add(hyperlink);
        this.hlinkIndexs.put(str, Integer.valueOf(size));
        return size;
    }

    public void dispose() {
        List<Hyperlink> list = this.hlinks;
        if (list != null) {
            for (Hyperlink dispose : list) {
                dispose.dispose();
            }
            this.hlinks.clear();
        }
        Map<String, Integer> map = this.hlinkIndexs;
        if (map != null) {
            map.clear();
        }
    }
}
