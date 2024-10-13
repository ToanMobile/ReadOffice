package com.app.office.common;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import com.app.office.common.bg.AShader;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.bg.LinearGradientShader;
import com.app.office.common.bg.PatternShader;
import com.app.office.common.bg.TileShader;
import com.app.office.common.picture.Picture;
import com.app.office.common.picture.PictureKit;
import com.app.office.common.pictureefftect.PictureEffectInfo;
import com.app.office.common.pictureefftect.PictureStretchInfo;
import com.app.office.common.shape.AbstractShape;
import com.app.office.java.awt.Dimension;
import com.app.office.pg.animate.IAnimation;
import com.app.office.pg.control.Presentation;
import com.app.office.system.IControl;

public class BackgroundDrawer {
    public static void drawLineAndFill(Canvas canvas, IControl iControl, int i, AbstractShape abstractShape, Rect rect, float f) {
        if (abstractShape.hasLine()) {
            Paint paint = PaintKit.instance().getPaint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(((float) abstractShape.getLine().getLineWidth()) * f);
            drawBackground(canvas, iControl, i, abstractShape.getLine().getBackgroundAndFill(), rect, (IAnimation) null, f, paint);
        }
        if (abstractShape.getBackgroundAndFill() != null) {
            drawBackground(canvas, iControl, i, abstractShape.getBackgroundAndFill(), rect, (IAnimation) null, f);
        }
    }

    public static void drawPathBackground(Canvas canvas, IControl iControl, int i, BackgroundAndFill backgroundAndFill, Rect rect, IAnimation iAnimation, float f, Path path, Paint paint) {
        Canvas canvas2 = canvas;
        IControl iControl2 = iControl;
        BackgroundAndFill backgroundAndFill2 = backgroundAndFill;
        Rect rect2 = rect;
        Path path2 = path;
        Paint paint2 = paint;
        if (backgroundAndFill2 != null) {
            canvas.save();
            if (backgroundAndFill.isSlideBackgroundFill() && iControl2 != null && (iControl.getView() instanceof Presentation)) {
                canvas.clipRect(rect2);
                canvas.rotate(0.0f);
                Dimension pageSize = ((Presentation) iControl.getView()).getPGModel().getPageSize();
                rect2.set(0, 0, (int) (((float) pageSize.width) * f), (int) (((float) pageSize.height) * f));
            }
            switch (backgroundAndFill.getFillType()) {
                case 0:
                    paint2.setColor(backgroundAndFill.getForegroundColor());
                    if (iAnimation != null) {
                        paint2.setAlpha((int) ((((float) iAnimation.getCurrentAnimationInfor().getAlpha()) / 255.0f) * ((float) ((backgroundAndFill.getForegroundColor() >> 24) & 255))));
                    }
                    canvas.drawPath(path2, paint2);
                    break;
                case 1:
                case 2:
                case 4:
                case 5:
                case 6:
                case 7:
                    drawGradientAndTile(canvas, iControl, i, backgroundAndFill, rect, iAnimation, f, path, paint);
                    break;
                case 3:
                    canvas.clipPath(path2);
                    float f2 = (float) rect2.left;
                    float f3 = (float) rect2.top;
                    float width = (float) rect.width();
                    float height = (float) rect.height();
                    PictureStretchInfo stretch = backgroundAndFill.getStretch();
                    if (stretch != null) {
                        f2 += stretch.getLeftOffset() * width;
                        f3 += stretch.getTopOffset() * height;
                        width *= (1.0f - stretch.getLeftOffset()) - stretch.getRightOffset();
                        height *= (1.0f - stretch.getTopOffset()) - stretch.getBottomOffset();
                    }
                    float f4 = height;
                    float f5 = f3;
                    float f6 = width;
                    PictureKit instance = PictureKit.instance();
                    Picture picture = backgroundAndFill2.getPicture(iControl);
                    instance.drawPicture(canvas, iControl, i, picture, f2, f5, f, f6, f4, (PictureEffectInfo) null, iAnimation);
                    break;
            }
            canvas.restore();
        }
    }

    private static void drawGradientAndTile(Canvas canvas, IControl iControl, int i, BackgroundAndFill backgroundAndFill, Rect rect, IAnimation iAnimation, float f, Path path, Paint paint) {
        Canvas canvas2 = canvas;
        Rect rect2 = rect;
        float f2 = f;
        Path path2 = path;
        Paint paint2 = paint;
        AShader shader = backgroundAndFill.getShader();
        if (shader != null) {
            boolean z = shader instanceof LinearGradientShader;
            if (z) {
                float strokeWidth = paint.getStrokeWidth();
                if (((float) Math.abs(rect2.left - rect2.right)) <= strokeWidth) {
                    float f3 = strokeWidth / 2.0f;
                    rect2.set(Math.round(((float) rect2.left) - f3), Math.round((float) rect2.top), Math.round(((float) rect2.right) + f3), Math.round((float) rect2.bottom));
                } else if (((float) Math.abs(rect2.top - rect2.bottom)) <= strokeWidth) {
                    float f4 = strokeWidth / 2.0f;
                    rect2.set(Math.round((float) rect2.left), Math.round(((float) rect2.top) - f4), Math.round((float) rect2.right), Math.round(((float) rect2.bottom) + f4));
                }
            }
            Shader shader2 = shader.getShader();
            float f5 = 1.0f;
            if (shader2 == null) {
                float f6 = 1.0f / f2;
                shader2 = shader.createShader(iControl, i, new Rect(Math.round(((float) rect2.left) * f6), Math.round(((float) rect2.top) * f6), Math.round(((float) rect2.right) * f6), Math.round(((float) rect2.bottom) * f6)));
                if (shader2 == null) {
                    return;
                }
            }
            Matrix matrix = new Matrix();
            float f7 = (float) rect2.left;
            float f8 = (float) rect2.top;
            if (shader instanceof TileShader) {
                TileShader tileShader = (TileShader) shader;
                f7 += ((float) tileShader.getOffsetX()) * f2;
                f8 += ((float) tileShader.getOffsetY()) * f2;
                matrix.postScale(f2, f2);
            } else if (!(shader instanceof PatternShader)) {
                if (z) {
                    LinearGradientShader linearGradientShader = (LinearGradientShader) shader;
                    float f9 = 0.0f;
                    if (linearGradientShader.getAngle() == 90) {
                        int focus = linearGradientShader.getFocus();
                        if (focus != -50) {
                            if (focus != 0) {
                                if (focus == 50) {
                                    f5 = -0.5f;
                                    f9 = -0.5f;
                                } else if (focus == 100) {
                                    f5 = 0.0f;
                                }
                            }
                            f9 = 1.0f;
                        } else {
                            f5 = 0.5f;
                            f9 = 0.5f;
                        }
                        float f10 = f9;
                        f9 = f5;
                        f5 = f10;
                    } else {
                        int focus2 = linearGradientShader.getFocus();
                        if (focus2 != -50) {
                            if (focus2 != 0) {
                                if (focus2 == 50) {
                                    f5 = 0.5f;
                                    f9 = 0.5f;
                                } else if (focus2 == 100) {
                                    f5 = 0.0f;
                                }
                            }
                            f9 = 1.0f;
                        } else {
                            f5 = -0.5f;
                            f9 = -0.5f;
                        }
                    }
                    f7 += f5 * ((float) rect.width());
                    f8 += f9 * ((float) rect.height());
                }
                matrix.postScale(((float) rect.width()) / 100.0f, ((float) rect.height()) / 100.0f);
            }
            matrix.postTranslate(f7, f8);
            shader2.setLocalMatrix(matrix);
            paint2.setShader(shader2);
            int alpha = shader.getAlpha();
            if (iAnimation != null) {
                alpha = (int) ((((float) iAnimation.getCurrentAnimationInfor().getAlpha()) / 255.0f) * ((float) alpha));
            }
            paint2.setAlpha(alpha);
            if (path2 != null) {
                canvas2.drawPath(path2, paint2);
            } else {
                canvas2.drawRect(rect2, paint2);
            }
            paint2.setShader((Shader) null);
        }
    }

    public static boolean drawBackground(Canvas canvas, IControl iControl, int i, BackgroundAndFill backgroundAndFill, Rect rect, IAnimation iAnimation, float f) {
        return drawBackground(canvas, iControl, i, backgroundAndFill, rect, iAnimation, f, PaintKit.instance().getPaint());
    }

    public static boolean drawBackground(Canvas canvas, IControl iControl, int i, BackgroundAndFill backgroundAndFill, Rect rect, IAnimation iAnimation, float f, Paint paint) {
        Canvas canvas2 = canvas;
        IControl iControl2 = iControl;
        BackgroundAndFill backgroundAndFill2 = backgroundAndFill;
        Rect rect2 = rect;
        Paint paint2 = paint;
        if (backgroundAndFill2 != null) {
            canvas.save();
            if (backgroundAndFill.isSlideBackgroundFill() && iControl2 != null && (iControl.getView() instanceof Presentation)) {
                canvas.clipRect(rect2);
                canvas.rotate(0.0f);
                Dimension pageSize = ((Presentation) iControl.getView()).getPGModel().getPageSize();
                rect2.set(0, 0, (int) (((float) pageSize.width) * f), (int) (((float) pageSize.height) * f));
            }
            switch (backgroundAndFill.getFillType()) {
                case 0:
                    int color = paint.getColor();
                    paint2.setColor(backgroundAndFill.getForegroundColor());
                    if (iAnimation != null) {
                        paint2.setAlpha(iAnimation.getCurrentAnimationInfor().getAlpha());
                    }
                    canvas.drawRect(rect2, paint2);
                    paint2.setColor(color);
                    canvas.restore();
                    return true;
                case 1:
                case 2:
                case 4:
                case 5:
                case 6:
                case 7:
                    drawGradientAndTile(canvas, iControl, i, backgroundAndFill, rect, iAnimation, f, (Path) null, paint);
                    canvas.restore();
                    return true;
                case 3:
                    float f2 = (float) rect2.left;
                    float f3 = (float) rect2.top;
                    float width = (float) rect.width();
                    float height = (float) rect.height();
                    PictureStretchInfo stretch = backgroundAndFill.getStretch();
                    if (stretch != null) {
                        f2 += stretch.getLeftOffset() * width;
                        f3 += stretch.getTopOffset() * height;
                        width *= (1.0f - stretch.getLeftOffset()) - stretch.getRightOffset();
                        height *= (1.0f - stretch.getTopOffset()) - stretch.getBottomOffset();
                    }
                    float f4 = height;
                    float f5 = width;
                    float f6 = f2;
                    PictureKit instance = PictureKit.instance();
                    Picture picture = backgroundAndFill2.getPicture(iControl);
                    instance.drawPicture(canvas, iControl, i, picture, f6, f3, f, f5, f4, (PictureEffectInfo) null, iAnimation);
                    canvas.restore();
                    return true;
                default:
                    canvas.restore();
                    break;
            }
        }
        return false;
    }
}
