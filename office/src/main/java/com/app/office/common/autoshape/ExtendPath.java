package com.app.office.common.autoshape;

import android.graphics.Path;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;

public class ExtendPath {
    private BackgroundAndFill fill;
    private boolean hasLine;
    private boolean isArrow;
    private Line line;
    private Path path;

    public ExtendPath() {
        this.path = new Path();
        this.fill = null;
    }

    public ExtendPath(ExtendPath extendPath) {
        this.path = new Path(extendPath.getPath());
        this.fill = extendPath.getBackgroundAndFill();
        this.hasLine = extendPath.hasLine();
        this.line = extendPath.getLine();
        this.isArrow = extendPath.isArrowPath();
    }

    public void setPath(Path path2) {
        this.path = path2;
    }

    public Path getPath() {
        return this.path;
    }

    public void setBackgroundAndFill(BackgroundAndFill backgroundAndFill) {
        this.fill = backgroundAndFill;
    }

    public BackgroundAndFill getBackgroundAndFill() {
        return this.fill;
    }

    public boolean hasLine() {
        return this.hasLine;
    }

    public void setLine(boolean z) {
        this.hasLine = z;
        if (z && this.line == null) {
            this.line = new Line();
        }
    }

    public Line getLine() {
        return this.line;
    }

    public void setLine(Line line2) {
        this.line = line2;
        if (line2 != null) {
            this.hasLine = true;
        } else {
            this.hasLine = false;
        }
    }

    public void setArrowFlag(boolean z) {
        this.isArrow = z;
    }

    public boolean isArrowPath() {
        return this.isArrow;
    }

    public void dispose() {
        this.path = null;
        BackgroundAndFill backgroundAndFill = this.fill;
        if (backgroundAndFill != null) {
            backgroundAndFill.dispose();
        }
    }
}
