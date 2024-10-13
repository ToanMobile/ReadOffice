package com.app.office.common.bg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Rect;
import android.graphics.Shader;
import androidx.core.view.ViewCompat;
import com.app.office.common.picture.Picture;
import com.app.office.system.IControl;

public class PatternShader extends AShader {
    private int backgroundColor;
    private int foregroundColor;
    private Picture picture;

    public PatternShader(Picture picture2, int i, int i2) {
        this.picture = picture2;
        this.backgroundColor = i;
        this.foregroundColor = i2;
    }

    public Shader createShader(IControl iControl, int i, Rect rect) {
        try {
            Bitmap bitmap = TileShader.getBitmap(iControl, i, this.picture, rect, (BitmapFactory.Options) null);
            if (bitmap != null) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int i2 = width * height;
                int[] iArr = new int[i2];
                bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((iArr[i3] & ViewCompat.MEASURED_SIZE_MASK) == 0) {
                        iArr[i3] = this.backgroundColor;
                    } else {
                        iArr[i3] = this.foregroundColor;
                    }
                }
                this.shader = new BitmapShader(Bitmap.createBitmap(iArr, width, height, Bitmap.Config.ARGB_8888), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            }
            return this.shader;
        } catch (Exception unused) {
            return null;
        }
    }
}
