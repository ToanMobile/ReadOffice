package com.app.office.wp.control;

import com.app.office.common.shape.AbstractShape;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WPShapeManage {
    private Map<Integer, AbstractShape> shapes = new HashMap(20);

    public int addShape(AbstractShape abstractShape) {
        int size = this.shapes.size();
        this.shapes.put(Integer.valueOf(size), abstractShape);
        return size;
    }

    public AbstractShape getShape(int i) {
        if (i < 0 || i >= this.shapes.size()) {
            return null;
        }
        return this.shapes.get(Integer.valueOf(i));
    }

    public void dispose() {
        Collection<AbstractShape> values;
        Map<Integer, AbstractShape> map = this.shapes;
        if (map != null && (values = map.values()) != null) {
            for (AbstractShape dispose : values) {
                dispose.dispose();
            }
            this.shapes.clear();
        }
    }
}
