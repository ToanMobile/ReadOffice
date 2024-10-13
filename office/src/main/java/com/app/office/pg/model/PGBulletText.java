package com.app.office.pg.model;

import java.util.ArrayList;
import java.util.List;

public class PGBulletText {
    private List<String> bulletTexts = new ArrayList();

    public int addBulletText(String str) {
        int size = this.bulletTexts.size();
        this.bulletTexts.add(str);
        return size;
    }

    public String getBulletText(int i) {
        if (i < 0 || i >= this.bulletTexts.size()) {
            return null;
        }
        return this.bulletTexts.get(i);
    }

    public void dispose() {
        List<String> list = this.bulletTexts;
        if (list != null) {
            list.clear();
            this.bulletTexts = null;
        }
    }
}
