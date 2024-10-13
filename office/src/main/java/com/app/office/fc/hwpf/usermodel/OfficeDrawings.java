package com.app.office.fc.hwpf.usermodel;

import java.util.Collection;

public interface OfficeDrawings {
    OfficeDrawing getOfficeDrawingAt(int i);

    Collection<OfficeDrawing> getOfficeDrawings();
}
