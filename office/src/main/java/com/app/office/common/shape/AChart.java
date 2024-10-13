package com.app.office.common.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.app.office.common.PaintKit;
import com.app.office.common.picture.Picture;
import com.app.office.system.IControl;
import com.app.office.thirdpart.achartengine.chart.AbstractChart;
import java.io.File;
import java.io.FileOutputStream;

public class AChart extends AbstractShape {
    private AbstractChart chart;
    private int picIndex = -1;

    public short getType() {
        return 5;
    }

    public void setAChart(AbstractChart abstractChart) {
        this.chart = abstractChart;
    }

    public AbstractChart getAChart() {
        return this.chart;
    }

    private void saveChartToPicture(IControl iControl) {
        Bitmap bitmap = null;
        try {
            int zoomRate = (int) (((float) this.rect.width) * getAChart().getZoomRate());
            int zoomRate2 = (int) (((float) this.rect.height) * getAChart().getZoomRate());
            bitmap = Bitmap.createBitmap(zoomRate, zoomRate2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            this.chart.draw(canvas, iControl, 0, 0, zoomRate, zoomRate2, PaintKit.instance().getPaint());
            canvas.save();
            canvas.restore();
            Picture picture = new Picture();
            File file = new File(iControl.getSysKit().getPictureManage().getPicTempPath() + File.separator + (String.valueOf(System.currentTimeMillis()) + ".tmp"));
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            bitmap.recycle();
            fileOutputStream.close();
            picture.setTempFilePath(file.getAbsolutePath());
            this.picIndex = iControl.getSysKit().getPictureManage().addPicture(picture);
        } catch (Exception e) {
            if (bitmap != null) {
                bitmap.recycle();
            }
            iControl.getSysKit().getErrorKit().writerLog(e);
        }
    }

    public int getDrawingPicture(IControl iControl) {
        if (this.picIndex == -1) {
            saveChartToPicture(iControl);
        }
        return this.picIndex;
    }

    public void dispose() {
        super.dispose();
        this.chart = null;
    }
}
