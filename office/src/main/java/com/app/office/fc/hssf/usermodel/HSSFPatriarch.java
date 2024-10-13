package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ddf.EscherBSERecord;
import com.app.office.fc.ddf.EscherComplexProperty;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperty;
import com.app.office.fc.hssf.record.EscherAggregate;
import com.app.office.fc.ss.usermodel.Chart;
import com.app.office.fc.ss.usermodel.Drawing;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.StringUtil;
import com.app.office.ss.model.XLSModel.ASheet;
import java.util.ArrayList;
import java.util.List;

public final class HSSFPatriarch implements HSSFShapeContainer, Drawing {
    private EscherAggregate _boundAggregate;
    private List<HSSFShape> _shapes = new ArrayList();
    protected ASheet _sheet;
    private int _x1 = 0;
    private int _x2 = IEEEDouble.EXPONENT_BIAS;
    private int _y1 = 0;
    private int _y2 = 255;

    public HSSFPatriarch(ASheet aSheet, EscherAggregate escherAggregate) {
        this._sheet = aSheet;
        this._boundAggregate = escherAggregate;
    }

    public HSSFShapeGroup createGroup(HSSFClientAnchor hSSFClientAnchor) {
        HSSFShapeGroup hSSFShapeGroup = new HSSFShapeGroup((EscherContainerRecord) null, (HSSFShape) null, hSSFClientAnchor);
        hSSFShapeGroup.setAnchor(hSSFClientAnchor);
        addShape(hSSFShapeGroup);
        return hSSFShapeGroup;
    }

    public HSSFSimpleShape createSimpleShape(HSSFClientAnchor hSSFClientAnchor) {
        HSSFSimpleShape hSSFSimpleShape = new HSSFSimpleShape((EscherContainerRecord) null, (HSSFShape) null, hSSFClientAnchor);
        hSSFSimpleShape.setAnchor(hSSFClientAnchor);
        addShape(hSSFSimpleShape);
        return hSSFSimpleShape;
    }

    public HSSFPicture createPicture(HSSFClientAnchor hSSFClientAnchor, int i) {
        HSSFPicture hSSFPicture = new HSSFPicture((EscherContainerRecord) null, (HSSFShape) null, hSSFClientAnchor);
        hSSFPicture.setPictureIndex(i);
        hSSFPicture.setAnchor(hSSFClientAnchor);
        addShape(hSSFPicture);
        EscherBSERecord bSERecord = this._sheet.getAWorkbook().getInternalWorkbook().getBSERecord(i);
        bSERecord.setRef(bSERecord.getRef() + 1);
        return hSSFPicture;
    }

    public HSSFPicture createPicture(IClientAnchor iClientAnchor, int i) {
        return createPicture((HSSFClientAnchor) iClientAnchor, i);
    }

    public HSSFPolygon createPolygon(HSSFClientAnchor hSSFClientAnchor) {
        HSSFPolygon hSSFPolygon = new HSSFPolygon((EscherContainerRecord) null, (HSSFShape) null, hSSFClientAnchor);
        hSSFPolygon.setAnchor(hSSFClientAnchor);
        addShape(hSSFPolygon);
        return hSSFPolygon;
    }

    public HSSFTextbox createTextbox(HSSFClientAnchor hSSFClientAnchor) {
        HSSFTextbox hSSFTextbox = new HSSFTextbox((EscherContainerRecord) null, (HSSFShape) null, hSSFClientAnchor);
        hSSFTextbox.setAnchor(hSSFClientAnchor);
        addShape(hSSFTextbox);
        return hSSFTextbox;
    }

    public HSSFComment createComment(HSSFAnchor hSSFAnchor) {
        HSSFComment hSSFComment = new HSSFComment((EscherContainerRecord) null, (HSSFShape) null, hSSFAnchor);
        hSSFComment.setAnchor(hSSFAnchor);
        addShape(hSSFComment);
        return hSSFComment;
    }

    /* access modifiers changed from: package-private */
    public HSSFSimpleShape createComboBox(HSSFAnchor hSSFAnchor) {
        HSSFSimpleShape hSSFSimpleShape = new HSSFSimpleShape((EscherContainerRecord) null, (HSSFShape) null, hSSFAnchor);
        hSSFSimpleShape.setShapeType(20);
        hSSFSimpleShape.setAnchor(hSSFAnchor);
        addShape(hSSFSimpleShape);
        return hSSFSimpleShape;
    }

    public HSSFComment createCellComment(IClientAnchor iClientAnchor) {
        return createComment((HSSFAnchor) iClientAnchor);
    }

    public List<HSSFShape> getChildren() {
        return this._shapes;
    }

    @Internal
    public void addShape(HSSFShape hSSFShape) {
        hSSFShape._patriarch = this;
        this._shapes.add(hSSFShape);
    }

    public int countOfAllChildren() {
        int size = this._shapes.size();
        for (HSSFShape countOfAllChildren : this._shapes) {
            size += countOfAllChildren.countOfAllChildren();
        }
        return size;
    }

    public void setCoordinates(int i, int i2, int i3, int i4) {
        this._x1 = i;
        this._y1 = i2;
        this._x2 = i3;
        this._y2 = i4;
    }

    public boolean containsChart() {
        EscherOptRecord escherOptRecord = (EscherOptRecord) this._boundAggregate.findFirstWithId(EscherOptRecord.RECORD_ID);
        if (escherOptRecord == null) {
            return false;
        }
        for (EscherProperty next : escherOptRecord.getEscherProperties()) {
            if (next.getPropertyNumber() == 896 && next.isComplex() && StringUtil.getFromUnicodeLE(((EscherComplexProperty) next).getComplexData()).equals("Chart 1\u0000")) {
                return true;
            }
        }
        return false;
    }

    public int getX1() {
        return this._x1;
    }

    public int getY1() {
        return this._y1;
    }

    public int getX2() {
        return this._x2;
    }

    public int getY2() {
        return this._y2;
    }

    /* access modifiers changed from: protected */
    public EscherAggregate _getBoundAggregate() {
        return this._boundAggregate;
    }

    public HSSFClientAnchor createAnchor(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        return new HSSFClientAnchor(i, i2, i3, i4, (short) i5, i6, (short) i7, i8);
    }

    public Chart createChart(IClientAnchor iClientAnchor) {
        throw new RuntimeException("NotImplemented");
    }

    public void dispose() {
        this._shapes.clear();
        this._shapes = null;
        this._boundAggregate = null;
        this._sheet = null;
    }
}
