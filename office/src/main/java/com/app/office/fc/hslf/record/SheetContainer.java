package com.app.office.fc.hslf.record;

public abstract class SheetContainer extends PositionDependentRecordContainer {
    public abstract ColorSchemeAtom getColorScheme();

    public abstract PPDrawing getPPDrawing();
}
