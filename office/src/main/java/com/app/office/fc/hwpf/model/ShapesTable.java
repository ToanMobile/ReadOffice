package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.usermodel.Shape;
import com.app.office.fc.util.Internal;
import java.util.ArrayList;
import java.util.List;

@Internal
@Deprecated
public final class ShapesTable {
    private List<Shape> _shapes = new ArrayList();
    private List<Shape> _shapesVisibili = new ArrayList();

    public ShapesTable(byte[] bArr, FileInformationBlock fileInformationBlock) {
        PlexOfCps plexOfCps = new PlexOfCps(bArr, fileInformationBlock.getFcPlcspaMom(), fileInformationBlock.getLcbPlcspaMom(), 26);
        for (int i = 0; i < plexOfCps.length(); i++) {
            Shape shape = new Shape(plexOfCps.getProperty(i));
            this._shapes.add(shape);
            if (shape.isWithinDocument()) {
                this._shapesVisibili.add(shape);
            }
        }
    }

    public List<Shape> getAllShapes() {
        return this._shapes;
    }

    public List<Shape> getVisibleShapes() {
        return this._shapesVisibili;
    }
}
