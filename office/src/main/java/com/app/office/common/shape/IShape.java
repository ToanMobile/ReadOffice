package com.app.office.common.shape;

import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.IAnimation;

public interface IShape {
    void dispose();

    IAnimation getAnimation();

    Rectangle getBounds();

    Object getData();

    boolean getFlipHorizontal();

    boolean getFlipVertical();

    int getGroupShapeID();

    IShape getParent();

    int getPlaceHolderID();

    float getRotation();

    int getShapeID();

    short getType();

    boolean isHidden();

    void setAnimation(IAnimation iAnimation);

    void setBounds(Rectangle rectangle);

    void setData(Object obj);

    void setFlipHorizontal(boolean z);

    void setFlipVertical(boolean z);

    void setGroupShapeID(int i);

    void setHidden(boolean z);

    void setParent(IShape iShape);

    void setPlaceHolderID(int i);

    void setRotation(float f);

    void setShapeID(int i);
}
