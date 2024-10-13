package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Stroke;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class WidenPath extends EMFTag {
    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return this;
    }

    public WidenPath() {
        super(66, 1);
    }

    public void render(EMFRenderer eMFRenderer) {
        GeneralPath path = eMFRenderer.getPath();
        Stroke penStroke = eMFRenderer.getPenStroke();
        if (path != null && penStroke != null) {
            GeneralPath generalPath = new GeneralPath(eMFRenderer.getWindingRule());
            generalPath.append(penStroke.createStrokedShape(path), false);
            eMFRenderer.setPath(generalPath);
        }
    }
}
