package com.app.office.common.borders;

import java.util.ArrayList;
import java.util.List;

public class BordersManage {
    private List<Borders> borders = new ArrayList();

    public int addBorders(Borders borders2) {
        int size = this.borders.size();
        this.borders.add(borders2);
        return size;
    }

    public Borders getBorders(int i) {
        return this.borders.get(i);
    }

    public void dispose() {
        List<Borders> list = this.borders;
        if (list != null) {
            list.clear();
            this.borders = null;
        }
    }
}
