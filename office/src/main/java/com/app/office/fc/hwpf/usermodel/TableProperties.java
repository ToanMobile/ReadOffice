package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.types.TAPAbstractType;

public final class TableProperties extends TAPAbstractType implements Cloneable {
    public TableProperties() {
        setTlp(new TableAutoformatLookSpecifier());
        setShdTable(new ShadingDescriptor());
        setBrcBottom(new BorderCode());
        setBrcHorizontal(new BorderCode());
        setBrcLeft(new BorderCode());
        setBrcRight(new BorderCode());
        setBrcTop(new BorderCode());
        setBrcVertical(new BorderCode());
        setRgbrcInsideDefault_0(new BorderCode());
        setRgbrcInsideDefault_1(new BorderCode());
        setRgdxaCenter(new short[0]);
        setRgdxaCenterPrint(new short[0]);
        setRgshd(new ShadingDescriptor[0]);
        setRgtc(new TableCellDescriptor[0]);
    }

    public TableProperties(short s) {
        this();
        setItcMac(s);
        setRgshd(new ShadingDescriptor[s]);
        for (int i = 0; i < s; i++) {
            getRgshd()[i] = new ShadingDescriptor();
        }
        TableCellDescriptor[] tableCellDescriptorArr = new TableCellDescriptor[s];
        for (int i2 = 0; i2 < s; i2++) {
            tableCellDescriptorArr[i2] = new TableCellDescriptor();
        }
        setRgtc(tableCellDescriptorArr);
        setRgdxaCenter(new short[s]);
        setRgdxaCenterPrint(new short[s]);
    }

    public Object clone() throws CloneNotSupportedException {
        TableProperties tableProperties = (TableProperties) super.clone();
        tableProperties.setTlp(getTlp().clone());
        tableProperties.setRgshd(new ShadingDescriptor[getRgshd().length]);
        for (int i = 0; i < getRgshd().length; i++) {
            tableProperties.getRgshd()[i] = (ShadingDescriptor) getRgshd()[i].clone();
        }
        tableProperties.setBrcBottom((BorderCode) getBrcBottom().clone());
        tableProperties.setBrcHorizontal((BorderCode) getBrcHorizontal().clone());
        tableProperties.setBrcLeft((BorderCode) getBrcLeft().clone());
        tableProperties.setBrcRight((BorderCode) getBrcRight().clone());
        tableProperties.setBrcTop((BorderCode) getBrcTop().clone());
        tableProperties.setBrcVertical((BorderCode) getBrcVertical().clone());
        tableProperties.setShdTable((ShadingDescriptor) getShdTable().clone());
        tableProperties.setRgbrcInsideDefault_0((BorderCode) getRgbrcInsideDefault_0().clone());
        tableProperties.setRgbrcInsideDefault_1((BorderCode) getRgbrcInsideDefault_1().clone());
        tableProperties.setRgdxaCenter((short[]) getRgdxaCenter().clone());
        tableProperties.setRgdxaCenterPrint((short[]) getRgdxaCenterPrint().clone());
        tableProperties.setRgtc(new TableCellDescriptor[getRgtc().length]);
        for (int i2 = 0; i2 < getRgtc().length; i2++) {
            tableProperties.getRgtc()[i2] = (TableCellDescriptor) getRgtc()[i2].clone();
        }
        return tableProperties;
    }
}
