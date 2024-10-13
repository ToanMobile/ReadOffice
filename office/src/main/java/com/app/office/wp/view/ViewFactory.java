package com.app.office.wp.view;

import com.app.office.common.shape.AbstractShape;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.WPAutoShape;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.IView;
import com.app.office.system.IControl;

public class ViewFactory {
    public static void dispose() {
    }

    public static IView createView(IControl iControl, IElement iElement, IElement iElement2, int i) {
        IView iView;
        switch (i) {
            case 4:
                return new PageView(iElement);
            case 5:
                return new ParagraphView(iElement);
            case 6:
                return new LineView(iElement);
            case 7:
                if (AttrManage.instance().hasAttribute(iElement.getAttribute(), 13)) {
                    AbstractShape shape = iControl.getSysKit().getWPShapeManage().getShape(AttrManage.instance().getShapeID(iElement.getAttribute()));
                    if (shape == null) {
                        return new ObjView(iElement2, iElement, (WPAutoShape) null);
                    }
                    if (shape.getType() == 2 || shape.getType() == 5) {
                        iView = new ShapeView(iElement2, iElement, (AutoShape) shape);
                    } else if (shape.getType() != 0) {
                        return null;
                    } else {
                        iView = new ObjView(iElement2, iElement, (WPAutoShape) shape);
                    }
                    return iView;
                } else if (AttrManage.instance().hasAttribute(iElement.getAttribute(), 16)) {
                    return new EncloseCharacterView(iElement2, iElement);
                } else {
                    return new LeafView(iElement2, iElement);
                }
            case 9:
                if (iElement.getType() == 2) {
                    return new TableView(iElement);
                }
                return new ParagraphView(iElement);
            case 10:
                return new RowView(iElement);
            case 11:
                return new CellView(iElement);
            case 12:
                return new TitleView(iElement);
            case 13:
                return new BNView();
            default:
                return null;
        }
    }
}
