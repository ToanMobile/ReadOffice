package com.app.office.fc.ss.usermodel.charts;

public interface ChartLegend extends ManuallyPositionable {
    LegendPosition getPosition();

    void setPosition(LegendPosition legendPosition);
}
