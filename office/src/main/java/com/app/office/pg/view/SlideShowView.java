package com.app.office.pg.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import com.app.office.common.shape.GroupShape;
import com.app.office.common.shape.IShape;
import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.AnimationManager;
import com.app.office.pg.animate.EmphanceAnimation;
import com.app.office.pg.animate.FadeAnimation;
import com.app.office.pg.animate.IAnimation;
import com.app.office.pg.animate.ShapeAnimation;
import com.app.office.pg.control.Presentation;
import com.app.office.pg.model.PGSlide;
import com.app.office.system.beans.CalloutView.CalloutView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlideShowView {
    private int animDuration = 1200;
    private Rect animShapeArea;
    private AnimationManager animationMgr;
    private Rect bgRect;
    private IAnimation pageAnimation;
    private Paint paint;
    private Presentation presentation;
    private Map<Integer, Map<Integer, IAnimation>> shapeVisible;
    private PGSlide slide;
    private int slideshowStep = 0;

    public SlideShowView(Presentation presentation2, PGSlide pGSlide) {
        this.presentation = presentation2;
        this.slide = pGSlide;
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setAntiAlias(true);
        this.paint.setTypeface(Typeface.SANS_SERIF);
        this.paint.setTextSize(24.0f);
        this.bgRect = new Rect();
    }

    private void removeAnimation() {
        Map<Integer, Map<Integer, IAnimation>> map = this.shapeVisible;
        if (map == null) {
            this.shapeVisible = new HashMap();
        } else {
            map.clear();
            this.slideshowStep = 0;
        }
        AnimationManager animationManager = this.animationMgr;
        if (animationManager != null) {
            animationManager.stopAnimation();
        }
        if (this.presentation.getEditor() != null) {
            this.presentation.getEditor().clearAnimation();
        }
        PGSlide pGSlide = this.slide;
        if (pGSlide != null) {
            int shapeCount = pGSlide.getShapeCount();
            for (int i = 0; i < shapeCount; i++) {
                removeShapeAnimation(this.slide.getShape(i));
            }
        }
    }

    private void removeShapeAnimation(IShape iShape) {
        if (iShape instanceof GroupShape) {
            for (IShape removeShapeAnimation : ((GroupShape) iShape).getShapes()) {
                removeShapeAnimation(removeShapeAnimation);
            }
            return;
        }
        IAnimation animation = iShape.getAnimation();
        if (animation != null) {
            iShape.setAnimation((IAnimation) null);
            animation.dispose();
        }
    }

    public void initSlideShow(PGSlide pGSlide, boolean z) {
        removeAnimation();
        this.slide = pGSlide;
        if (pGSlide != null) {
            List<ShapeAnimation> slideShowAnimation = pGSlide.getSlideShowAnimation();
            if (slideShowAnimation != null) {
                int size = slideShowAnimation.size();
                for (int i = 0; i < size; i++) {
                    ShapeAnimation shapeAnimation = slideShowAnimation.get(i);
                    Map map = this.shapeVisible.get(Integer.valueOf(shapeAnimation.getShapeID()));
                    if (map == null) {
                        map = new HashMap();
                        this.shapeVisible.put(Integer.valueOf(shapeAnimation.getShapeID()), map);
                    }
                    int paragraphBegin = shapeAnimation.getParagraphBegin();
                    while (true) {
                        if (paragraphBegin > shapeAnimation.getParagraphEnd()) {
                            break;
                        } else if (((IAnimation) map.get(Integer.valueOf(paragraphBegin))) == null) {
                            FadeAnimation fadeAnimation = new FadeAnimation(shapeAnimation, this.animDuration);
                            for (int paragraphBegin2 = shapeAnimation.getParagraphBegin(); paragraphBegin2 <= shapeAnimation.getParagraphEnd(); paragraphBegin2++) {
                                map.put(Integer.valueOf(paragraphBegin2), fadeAnimation);
                            }
                            setShapeAnimation(shapeAnimation.getShapeID(), (IAnimation) fadeAnimation);
                        } else {
                            paragraphBegin++;
                        }
                    }
                }
            }
            if (this.animationMgr == null) {
                this.animationMgr = this.presentation.getControl().getSysKit().getAnimationManager();
            }
            if (pGSlide.hasTransition()) {
                IAnimation iAnimation = this.pageAnimation;
                if (iAnimation == null) {
                    this.pageAnimation = new FadeAnimation(new ShapeAnimation(-3, (byte) 0), this.animDuration);
                } else {
                    iAnimation.setDuration(this.animDuration);
                }
                this.animationMgr.setAnimation(this.pageAnimation);
                if (z) {
                    this.animationMgr.beginAnimation(1000 / this.pageAnimation.getFPS());
                } else {
                    this.animationMgr.stopAnimation();
                }
            }
        }
    }

    private void setShapeAnimation(int i, IAnimation iAnimation) {
        int shapeCount = this.slide.getShapeCount();
        for (int i2 = 0; i2 < shapeCount; i2++) {
            IShape shape = this.slide.getShape(i2);
            if ((shape.getShapeID() == i || shape.getGroupShapeID() == i) && shape.getAnimation() == null) {
                setShapeAnimation(shape, iAnimation);
            }
        }
    }

    private void setShapeAnimation(IShape iShape, IAnimation iAnimation) {
        if (iShape instanceof GroupShape) {
            for (IShape shapeAnimation : ((GroupShape) iShape).getShapes()) {
                setShapeAnimation(shapeAnimation, iAnimation);
            }
            return;
        }
        iShape.setAnimation(iAnimation);
    }

    public void endSlideShow() {
        removeAnimation();
    }

    public boolean isExitSlideShow() {
        return this.slide == null;
    }

    public boolean gotopreviousSlide() {
        if (this.slide.getSlideShowAnimation() == null || this.slideshowStep <= 0) {
            return true;
        }
        return false;
    }

    public boolean gotoNextSlide() {
        List<ShapeAnimation> slideShowAnimation = this.slide.getSlideShowAnimation();
        if (slideShowAnimation == null || this.slideshowStep >= slideShowAnimation.size()) {
            return true;
        }
        return false;
    }

    public void previousActionSlideShow() {
        int i = this.slideshowStep - 1;
        initSlideShow(this.slide, false);
        while (true) {
            int i2 = this.slideshowStep;
            if (i2 < i) {
                int i3 = i2 + 1;
                this.slideshowStep = i3;
                updateShapeAnimation(i3, false);
            } else {
                return;
            }
        }
    }

    public void nextActionSlideShow() {
        int i = this.slideshowStep + 1;
        this.slideshowStep = i;
        updateShapeAnimation(i, true);
    }

    public void gotoLastAction() {
        while (!gotoNextSlide()) {
            int i = this.slideshowStep + 1;
            this.slideshowStep = i;
            updateShapeAnimation(i, false);
        }
    }

    private void updateShapeAnimation(int i, boolean z) {
        IAnimation iAnimation;
        List<ShapeAnimation> slideShowAnimation = this.slide.getSlideShowAnimation();
        if (slideShowAnimation != null) {
            ShapeAnimation shapeAnimation = slideShowAnimation.get(i - 1);
            updateShapeArea(shapeAnimation.getShapeID(), this.presentation.getZoom());
            if (shapeAnimation.getAnimationType() != 1) {
                iAnimation = new FadeAnimation(shapeAnimation, this.animDuration);
            } else {
                iAnimation = new EmphanceAnimation(shapeAnimation, this.animDuration);
            }
            this.shapeVisible.get(Integer.valueOf(shapeAnimation.getShapeID())).put(Integer.valueOf(shapeAnimation.getParagraphBegin()), iAnimation);
            updateShapeAnimation(shapeAnimation.getShapeID(), iAnimation, z);
        }
    }

    private void updateShapeAnimation(int i, IAnimation iAnimation, boolean z) {
        this.animationMgr.setAnimation(iAnimation);
        int shapeCount = this.slide.getShapeCount();
        for (int i2 = 0; i2 < shapeCount; i2++) {
            IShape shape = this.slide.getShape(i2);
            if (shape.getShapeID() == i || shape.getGroupShapeID() == i) {
                setShapeAnimation(shape, iAnimation);
            }
        }
        if (z) {
            this.animationMgr.beginAnimation(1000 / iAnimation.getFPS());
        } else {
            this.animationMgr.stopAnimation();
        }
    }

    private void updateShapeArea(int i, float f) {
        Rectangle bounds;
        int shapeCount = this.slide.getShapeCount();
        int i2 = 0;
        while (i2 < shapeCount) {
            IShape shape = this.slide.getShape(i2);
            if (shape.getShapeID() != i || (bounds = shape.getBounds()) == null) {
                i2++;
            } else {
                int round = Math.round(((float) bounds.x) * f);
                int round2 = Math.round(((float) bounds.y) * f);
                int round3 = Math.round(((float) bounds.width) * f);
                int round4 = Math.round(((float) bounds.height) * f);
                Rect rect = this.animShapeArea;
                if (rect == null) {
                    this.animShapeArea = new Rect(round, round2, round3 + round, round4 + round2);
                    return;
                } else {
                    rect.set(round, round2, round3 + round, round4 + round2);
                    return;
                }
            }
        }
        this.animShapeArea = null;
    }

    public void changeSlide(PGSlide pGSlide) {
        this.slide = pGSlide;
    }

    public Rect getDrawingRect() {
        return this.bgRect;
    }

    public void drawSlide(Canvas canvas, float f, CalloutView calloutView) {
        float f2;
        Canvas canvas2 = canvas;
        CalloutView calloutView2 = calloutView;
        IAnimation iAnimation = this.pageAnimation;
        if (iAnimation == null || iAnimation.getAnimationStatus() == 2) {
            f2 = f;
        } else {
            float progress = this.pageAnimation.getCurrentAnimationInfor().getProgress() * f;
            if (progress > 0.001f) {
                f2 = progress;
            } else {
                return;
            }
        }
        Dimension pageSize = this.presentation.getPageSize();
        int i = (int) (((float) pageSize.width) * f2);
        int i2 = (int) (((float) pageSize.height) * f2);
        int i3 = (this.presentation.getmWidth() - i) / 2;
        int i4 = (this.presentation.getmHeight() - i2) / 2;
        canvas.save();
        canvas2.translate((float) i3, (float) i4);
        canvas2.clipRect(0, 0, i, i2);
        this.bgRect.set(0, 0, i, i2);
        SlideDrawKit.instance().drawSlide(canvas, this.presentation.getPGModel(), this.presentation.getEditor(), this.slide, f2, this.shapeVisible);
        canvas.restore();
        if (calloutView2 != null) {
            IAnimation iAnimation2 = this.pageAnimation;
            if (iAnimation2 == null || iAnimation2.getAnimationStatus() == 2) {
                calloutView2.setZoom(f2);
                calloutView2.layout(i3, i4, i + i3, i2 + i4);
                calloutView2.setVisibility(0);
                return;
            }
            calloutView2.setVisibility(4);
        }
    }

    public void drawSlideForToPicture(Canvas canvas, float f, int i, int i2) {
        Rect clipBounds = canvas.getClipBounds();
        if (!(clipBounds.width() == i && clipBounds.height() == i2)) {
            f *= Math.min(((float) clipBounds.width()) / ((float) i), ((float) clipBounds.height()) / ((float) i2));
        }
        Canvas canvas2 = canvas;
        SlideDrawKit.instance().drawSlide(canvas2, this.presentation.getPGModel(), this.presentation.getEditor(), this.slide, f, this.shapeVisible);
    }

    public boolean animationStoped() {
        AnimationManager animationManager = this.animationMgr;
        if (animationManager != null) {
            return animationManager.hasStoped();
        }
        return true;
    }

    public void setAnimationDuration(int i) {
        this.animDuration = i;
    }

    public Bitmap getSlideshowToImage(PGSlide pGSlide, int i) {
        this.slide = pGSlide;
        initSlideShow(pGSlide, false);
        while (true) {
            int i2 = this.slideshowStep;
            if (i2 < i - 1) {
                int i3 = i2 + 1;
                this.slideshowStep = i3;
                updateShapeAnimation(i3, false);
            } else {
                Bitmap slideToImage = SlideDrawKit.instance().slideToImage(this.presentation.getPGModel(), this.presentation.getEditor(), pGSlide, this.shapeVisible);
                removeAnimation();
                return slideToImage;
            }
        }
    }

    public void dispose() {
        this.paint = null;
        this.presentation = null;
        this.slide = null;
        AnimationManager animationManager = this.animationMgr;
        if (animationManager != null) {
            animationManager.dispose();
            this.animationMgr = null;
        }
        Map<Integer, Map<Integer, IAnimation>> map = this.shapeVisible;
        if (map != null) {
            map.clear();
            this.shapeVisible = null;
        }
    }
}
