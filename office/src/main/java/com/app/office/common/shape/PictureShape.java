package com.app.office.common.shape;

import com.app.office.common.picture.Picture;
import com.app.office.common.pictureefftect.PictureEffectInfo;
import com.app.office.system.IControl;

public class PictureShape extends AbstractShape {
    private PictureEffectInfo effectInfor;
    private int pictureIndex;
    private short zoomX;
    private short zoomY;

    public short getType() {
        return 0;
    }

    public void setPictureIndex(int i) {
        this.pictureIndex = i;
    }

    public int getPictureIndex() {
        return this.pictureIndex;
    }

    public Picture getPicture(IControl iControl) {
        if (iControl == null) {
            return null;
        }
        return iControl.getSysKit().getPictureManage().getPicture(this.pictureIndex);
    }

    public static Picture getPicture(IControl iControl, int i) {
        if (iControl == null) {
            return null;
        }
        return iControl.getSysKit().getPictureManage().getPicture(i);
    }

    public void setZoomX(short s) {
        this.zoomX = s;
    }

    public void setZoomY(short s) {
        this.zoomY = s;
    }

    public void setPictureEffectInfor(PictureEffectInfo pictureEffectInfo) {
        this.effectInfor = pictureEffectInfo;
    }

    public PictureEffectInfo getPictureEffectInfor() {
        return this.effectInfor;
    }

    public void dispose() {
        super.dispose();
    }
}
