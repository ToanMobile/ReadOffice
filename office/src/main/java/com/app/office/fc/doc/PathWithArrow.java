package com.app.office.fc.doc;

import android.graphics.Path;

public class PathWithArrow {
    private Path endArrow;
    private Path[] polygon;
    private Path startArrow;

    public PathWithArrow(Path[] pathArr, Path path, Path path2) {
        this.polygon = pathArr;
        this.startArrow = path;
        this.endArrow = path2;
    }

    public Path getStartArrow() {
        return this.startArrow;
    }

    public Path getEndArrow() {
        return this.endArrow;
    }

    public Path[] getPolygonPath() {
        return this.polygon;
    }
}
