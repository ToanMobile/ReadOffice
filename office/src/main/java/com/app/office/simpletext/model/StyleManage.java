package com.app.office.simpletext.model;

import java.util.Hashtable;
import java.util.Map;

public class StyleManage {
    private static StyleManage kit = new StyleManage();
    private Map<Integer, Style> styles = new Hashtable();

    public static StyleManage instance() {
        return kit;
    }

    public Style getStyle(int i) {
        return this.styles.get(Integer.valueOf(i));
    }

    public Style getStyleForName(String str) {
        for (Style next : this.styles.values()) {
            if (next.getName().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public void addStyle(Style style) {
        this.styles.put(Integer.valueOf(style.getId()), style);
    }

    public void dispose() {
        for (Style dispose : this.styles.values()) {
            dispose.dispose();
        }
        this.styles.clear();
        this.styles = null;
    }
}
