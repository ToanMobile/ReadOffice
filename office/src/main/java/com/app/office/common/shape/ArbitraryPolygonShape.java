package com.app.office.common.shape;

import com.app.office.common.autoshape.ExtendPath;
import java.util.ArrayList;
import java.util.List;

public class ArbitraryPolygonShape extends LineShape {
    private List<ExtendPath> paths = new ArrayList();

    public void appendPath(ExtendPath extendPath) {
        this.paths.add(extendPath);
    }

    public List<ExtendPath> getPaths() {
        return this.paths;
    }
}
