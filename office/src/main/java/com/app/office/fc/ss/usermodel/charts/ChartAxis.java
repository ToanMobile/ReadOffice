package com.app.office.fc.ss.usermodel.charts;

public interface ChartAxis {
    void crossAxis(ChartAxis chartAxis);

    AxisCrosses getCrosses();

    long getId();

    double getLogBase();

    double getMaximum();

    double getMinimum();

    String getNumberFormat();

    AxisOrientation getOrientation();

    AxisPosition getPosition();

    boolean isSetLogBase();

    boolean isSetMaximum();

    boolean isSetMinimum();

    void setCrosses(AxisCrosses axisCrosses);

    void setLogBase(double d);

    void setMaximum(double d);

    void setMinimum(double d);

    void setNumberFormat(String str);

    void setOrientation(AxisOrientation axisOrientation);

    void setPosition(AxisPosition axisPosition);
}
