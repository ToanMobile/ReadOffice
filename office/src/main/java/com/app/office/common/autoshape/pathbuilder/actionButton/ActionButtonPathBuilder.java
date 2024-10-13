package com.app.office.common.autoshape.pathbuilder.actionButton;

import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.common.autoshape.ExtendPath;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.ShapeTypes;
import com.app.office.ss.util.ColorUtil;
import java.util.ArrayList;
import java.util.List;

public class ActionButtonPathBuilder {
    private static final int PICTURECOLOR = -1891351484;
    private static final int PICTURECOLOR_DARK = -1882337843;
    private static final int PICTURECOLOR_LIGHT = -1890233003;
    private static final float TINT_DARK = -0.5f;
    private static final float TINT_LIGHT1 = -0.3f;
    private static final float TINT_LIGHT2 = 0.6f;
    private static List<ExtendPath> pathExList = new ArrayList(2);
    private static RectF tempRect = new RectF();

    public static List<ExtendPath> getActionButtonExtendPath(AutoShape autoShape, Rect rect) {
        pathExList.clear();
        switch (autoShape.getShapeType()) {
            case ShapeTypes.ActionButtonBlank /*189*/:
                return getBlankPath(autoShape, rect);
            case ShapeTypes.ActionButtonHome /*190*/:
                return getHomePath(autoShape, rect);
            case ShapeTypes.ActionButtonHelp /*191*/:
                return getHelpPath(autoShape, rect);
            case ShapeTypes.ActionButtonInformation /*192*/:
                return getInformationPath(autoShape, rect);
            case ShapeTypes.ActionButtonForwardNext /*193*/:
                return getForwardNextPath(autoShape, rect);
            case ShapeTypes.ActionButtonBackPrevious /*194*/:
                return getBackPreviousPath(autoShape, rect);
            case ShapeTypes.ActionButtonEnd /*195*/:
                return getEndPath(autoShape, rect);
            case ShapeTypes.ActionButtonBeginning /*196*/:
                return getBeginningPath(autoShape, rect);
            case ShapeTypes.ActionButtonReturn /*197*/:
                return getReturnPath(autoShape, rect);
            case ShapeTypes.ActionButtonDocument /*198*/:
                return getDocumentPath(autoShape, rect);
            case ShapeTypes.ActionButtonSound /*199*/:
                return getSoundPath(autoShape, rect);
            case 200:
                return getMoviePath(autoShape, rect);
            default:
                return null;
        }
    }

    private static List<ExtendPath> getBackPreviousPath(AutoShape autoShape, Rect rect) {
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        tempRect.set(rect);
        path.addRect(tempRect, Path.Direction.CW);
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path2 = new Path();
        int round = Math.round(((float) Math.min(rect.width(), rect.height())) * 0.75f);
        float round2 = (float) Math.round((Math.sqrt(3.0d) / 4.0d) * ((double) round));
        path2.moveTo(((float) rect.centerX()) - round2, (float) rect.centerY());
        int i = round / 2;
        path2.lineTo(((float) rect.centerX()) + round2, (float) (rect.centerY() - i));
        path2.lineTo(((float) rect.centerX()) + round2, (float) (rect.centerY() + i));
        path2.close();
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.5d));
        }
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        extendPath2.setPath(path2);
        pathExList.add(extendPath2);
        return pathExList;
    }

    private static List<ExtendPath> getForwardNextPath(AutoShape autoShape, Rect rect) {
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        tempRect.set(rect);
        path.addRect(tempRect, Path.Direction.CW);
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path2 = new Path();
        int round = Math.round(((float) Math.min(rect.width(), rect.height())) * 0.75f);
        float round2 = (float) Math.round((Math.sqrt(3.0d) / 4.0d) * ((double) round));
        path2.moveTo(((float) rect.centerX()) + round2, (float) rect.centerY());
        int i = round / 2;
        path2.lineTo(((float) rect.centerX()) - round2, (float) (rect.centerY() + i));
        path2.lineTo(((float) rect.centerX()) - round2, (float) (rect.centerY() - i));
        path2.close();
        extendPath2.setPath(path2);
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.5d));
        }
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath2);
        return pathExList;
    }

    private static List<ExtendPath> getBeginningPath(AutoShape autoShape, Rect rect) {
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        tempRect.set(rect);
        path.addRect(tempRect, Path.Direction.CW);
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path2 = new Path();
        int min = Math.min(rect.width(), rect.height());
        float f = (float) min;
        float f2 = f * 0.375f;
        path2.addRect(((float) rect.centerX()) - f2, ((float) rect.centerY()) - f2, ((float) rect.centerX()) - (f * 0.275f), ((float) rect.centerY()) + f2, Path.Direction.CW);
        path2.moveTo(((float) rect.centerX()) - (((float) (min * 3)) / 16.0f), (float) rect.centerY());
        path2.lineTo(((float) rect.centerX()) + f2, ((float) rect.centerY()) - f2);
        path2.lineTo(((float) rect.centerX()) + f2, ((float) rect.centerY()) + f2);
        path2.close();
        extendPath2.setPath(path2);
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.5d));
        }
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath2);
        return pathExList;
    }

    private static List<ExtendPath> getEndPath(AutoShape autoShape, Rect rect) {
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        tempRect.set(rect);
        path.addRect(tempRect, Path.Direction.CW);
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path2 = new Path();
        int min = Math.min(rect.width(), rect.height());
        float f = (float) min;
        float f2 = f * 0.375f;
        path2.addRect((0.275f * f) + ((float) rect.centerX()), ((float) rect.centerY()) - f2, ((float) rect.centerX()) + f2, ((float) rect.centerY()) + f2, Path.Direction.CW);
        path2.moveTo(((float) rect.centerX()) + (((float) (min * 3)) / 16.0f), (float) rect.centerY());
        path2.lineTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) + f2);
        path2.lineTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) - f2);
        path2.close();
        extendPath2.setPath(path2);
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.5d));
        }
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath2);
        return pathExList;
    }

    private static List<ExtendPath> getHomePath(AutoShape autoShape, Rect rect) {
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        tempRect.set(rect);
        path.addRect(tempRect, Path.Direction.CW);
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path2 = new Path();
        float min = (float) Math.min(rect.width(), rect.height());
        float f = 0.28f * min;
        float f2 = min * 0.375f;
        path2.addRect(((float) rect.centerX()) - f, (float) rect.centerY(), ((float) rect.centerX()) + f, ((float) rect.centerY()) + f2, Path.Direction.CW);
        float f3 = 0.14f * min;
        float f4 = 0.33f * min;
        path2.moveTo(((float) rect.centerX()) + f3, ((float) rect.centerY()) - f4);
        float f5 = 0.24f * min;
        path2.lineTo(((float) rect.centerX()) + f5, ((float) rect.centerY()) - f4);
        path2.lineTo(((float) rect.centerX()) + f5, ((float) rect.centerY()) - (0.135f * min));
        path2.lineTo(((float) rect.centerX()) + f3, ((float) rect.centerY()) - (0.235f * min));
        path2.close();
        extendPath2.setPath(path2);
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR_LIGHT);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.30000001192092896d));
        }
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath2);
        ExtendPath extendPath3 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(((float) rect.centerX()) - f2, (float) rect.centerY());
        path3.lineTo((float) rect.centerX(), ((float) rect.centerY()) - f2);
        path3.lineTo(((float) rect.centerX()) + f2, (float) rect.centerY());
        path3.close();
        float f6 = 0.05f * min;
        path3.addRect(((float) rect.centerX()) - f6, ((float) rect.centerY()) + (min * 0.18f), ((float) rect.centerX()) + f6, ((float) rect.centerY()) + f2, Path.Direction.CW);
        extendPath3.setPath(path3);
        BackgroundAndFill backgroundAndFill3 = new BackgroundAndFill();
        backgroundAndFill3.setFillType((byte) 0);
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill3.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill3.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.5d));
        }
        extendPath3.setBackgroundAndFill(backgroundAndFill3);
        pathExList.add(extendPath3);
        return pathExList;
    }

    private static List<ExtendPath> getInformationPath(AutoShape autoShape, Rect rect) {
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        tempRect.set(rect);
        path.addRect(tempRect, Path.Direction.CW);
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path2 = new Path();
        float min = (float) Math.min(rect.width(), rect.height());
        path2.addCircle((float) rect.centerX(), (float) rect.centerY(), 0.375f * min, Path.Direction.CW);
        extendPath2.setPath(path2);
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.5d));
        }
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath2);
        ExtendPath extendPath3 = new ExtendPath();
        Path path3 = new Path();
        float f = 0.06f * min;
        path3.addCircle((float) rect.centerX(), ((float) rect.centerY()) - (0.22f * min), f, Path.Direction.CW);
        float f2 = 0.12f * min;
        float f3 = 0.11f * min;
        path3.moveTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) - f3);
        path3.lineTo(((float) rect.centerX()) + f, ((float) rect.centerY()) - f3);
        float f4 = 0.16f * min;
        path3.lineTo(((float) rect.centerX()) + f, ((float) rect.centerY()) + f4);
        path3.lineTo(((float) rect.centerX()) + f2, ((float) rect.centerY()) + f4);
        float f5 = 0.2f * min;
        path3.lineTo(((float) rect.centerX()) + f2, ((float) rect.centerY()) + f5);
        path3.lineTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) + f5);
        path3.lineTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) + f4);
        path3.lineTo(((float) rect.centerX()) - f, ((float) rect.centerY()) + f4);
        float f6 = min * 0.08f;
        path3.lineTo(((float) rect.centerX()) - f, ((float) rect.centerY()) - f6);
        path3.lineTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) - f6);
        path3.close();
        extendPath3.setPath(path3);
        BackgroundAndFill backgroundAndFill3 = new BackgroundAndFill();
        backgroundAndFill3.setFillType((byte) 0);
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill3.setForegroundColor(PICTURECOLOR_DARK);
        } else {
            backgroundAndFill3.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), 0.6000000238418579d));
        }
        extendPath3.setBackgroundAndFill(backgroundAndFill3);
        pathExList.add(extendPath3);
        return pathExList;
    }

    private static List<ExtendPath> getReturnPath(AutoShape autoShape, Rect rect) {
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        tempRect.set(rect);
        path.addRect(tempRect, Path.Direction.CW);
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        int min = Math.min(rect.width(), rect.height());
        ExtendPath extendPath2 = new ExtendPath();
        Path path2 = new Path();
        float f = (float) min;
        float f2 = 0.4f * f;
        float f3 = 0.2f * f;
        path2.moveTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) - f3);
        path2.lineTo(((float) rect.centerX()) - f3, ((float) rect.centerY()) - f3);
        float f4 = 0.1f * f;
        path2.lineTo(((float) rect.centerX()) - f3, ((float) rect.centerY()) + f4);
        tempRect.set(((float) rect.centerX()) - f3, (float) rect.centerY(), (float) rect.centerX(), ((float) rect.centerY()) + f3);
        path2.arcTo(tempRect, 180.0f, -90.0f);
        path2.lineTo((float) rect.centerX(), ((float) rect.centerY()) + f3);
        tempRect.set(((float) rect.centerX()) - f4, (float) rect.centerY(), ((float) rect.centerX()) + f4, ((float) rect.centerY()) + f3);
        path2.arcTo(tempRect, 90.0f, -90.0f);
        path2.lineTo(((float) rect.centerX()) + f4, ((float) rect.centerY()) - f3);
        path2.lineTo((float) rect.centerX(), ((float) rect.centerY()) - f3);
        path2.lineTo(((float) rect.centerX()) + f3, ((float) rect.centerY()) - f2);
        path2.lineTo(((float) rect.centerX()) + f2, ((float) rect.centerY()) - f3);
        float f5 = f * 0.3f;
        path2.lineTo(((float) rect.centerX()) + f5, ((float) rect.centerY()) - f3);
        tempRect.set(((float) rect.centerX()) - f5, ((float) rect.centerY()) - f3, ((float) rect.centerX()) + f5, ((float) rect.centerY()) + f2);
        path2.arcTo(tempRect, 0.0f, 90.0f);
        tempRect.set(((float) rect.centerX()) - f2, ((float) rect.centerY()) - f3, ((float) rect.centerX()) + f3, ((float) rect.centerY()) + f2);
        path2.arcTo(tempRect, 90.0f, 90.0f);
        path2.close();
        extendPath2.setPath(path2);
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.5d));
        }
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath2);
        return pathExList;
    }

    private static List<ExtendPath> getMoviePath(AutoShape autoShape, Rect rect) {
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        tempRect.set(rect);
        path.addRect(tempRect, Path.Direction.CW);
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        int min = Math.min(rect.width(), rect.height());
        ExtendPath extendPath2 = new ExtendPath();
        Path path2 = new Path();
        float f = (float) min;
        float f2 = 0.38f * f;
        float f3 = 0.2f * f;
        path2.moveTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) - f3);
        float f4 = 0.31f * f;
        path2.lineTo(((float) rect.centerX()) - f4, ((float) rect.centerY()) - f3);
        float f5 = 0.18f * f;
        path2.lineTo(((float) rect.centerX()) - (0.3f * f), ((float) rect.centerY()) - f5);
        path2.lineTo(((float) rect.centerX()) + f5, ((float) rect.centerY()) - f5);
        float f6 = 0.22f * f;
        float f7 = 0.15f * f;
        path2.lineTo(((float) rect.centerX()) + f6, ((float) rect.centerY()) - f7);
        float f8 = 0.12f * f;
        path2.lineTo(((float) rect.centerX()) + f6, ((float) rect.centerY()) - f8);
        path2.lineTo(((float) rect.centerX()) + f4, ((float) rect.centerY()) - f8);
        float f9 = 0.34f * f;
        path2.lineTo(((float) rect.centerX()) + f9, ((float) rect.centerY()) - f7);
        float f10 = 0.37f * f;
        path2.lineTo(((float) rect.centerX()) + f10, ((float) rect.centerY()) - f7);
        path2.lineTo(((float) rect.centerX()) + f10, ((float) rect.centerY()) + f7);
        path2.lineTo(((float) rect.centerX()) + f9, ((float) rect.centerY()) + f7);
        float f11 = 0.29f * f;
        path2.lineTo(((float) rect.centerX()) + f11, ((float) rect.centerY()) + f8);
        path2.lineTo(((float) rect.centerX()) + f6, ((float) rect.centerY()) + f8);
        float f12 = 0.16f * f;
        path2.lineTo(((float) rect.centerX()) + f6, ((float) rect.centerY()) + f12);
        path2.lineTo(((float) rect.centerX()) - f11, ((float) rect.centerY()) + f12);
        float centerX = ((float) rect.centerX()) - f11;
        float f13 = 0.06f * f;
        path2.lineTo(centerX, ((float) rect.centerY()) - f13);
        path2.lineTo(((float) rect.centerX()) - f4, ((float) rect.centerY()) - f13);
        float f14 = f * 0.04f;
        path2.lineTo(((float) rect.centerX()) - (0.32f * f), ((float) rect.centerY()) - f14);
        path2.lineTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) - f14);
        path2.close();
        extendPath2.setPath(path2);
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.5d));
        }
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath2);
        return pathExList;
    }

    private static List<ExtendPath> getDocumentPath(AutoShape autoShape, Rect rect) {
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        tempRect.set(rect);
        path.addRect(tempRect, Path.Direction.CW);
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        int min = Math.min(rect.width(), rect.height());
        ExtendPath extendPath2 = new ExtendPath();
        Path path2 = new Path();
        float f = (float) min;
        float f2 = 0.28f * f;
        float f3 = 0.38f * f;
        path2.moveTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) - f3);
        float f4 = 0.1f * f;
        path2.lineTo(((float) rect.centerX()) + f4, ((float) rect.centerY()) - f3);
        float f5 = f * 0.18f;
        path2.lineTo(((float) rect.centerX()) + f4, ((float) rect.centerY()) - f5);
        path2.lineTo(((float) rect.centerX()) + f2, ((float) rect.centerY()) - f5);
        path2.lineTo(((float) rect.centerX()) + f2, ((float) rect.centerY()) + f3);
        path2.lineTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) + f3);
        path2.close();
        extendPath2.setPath(path2);
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR_LIGHT);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.30000001192092896d));
        }
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath2);
        ExtendPath extendPath3 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(((float) rect.centerX()) + f4, ((float) rect.centerY()) - f3);
        path3.lineTo(((float) rect.centerX()) + f4, ((float) rect.centerY()) - f5);
        path3.lineTo(((float) rect.centerX()) + f2, ((float) rect.centerY()) - f5);
        path3.close();
        extendPath3.setPath(path3);
        BackgroundAndFill backgroundAndFill3 = new BackgroundAndFill();
        backgroundAndFill3.setFillType((byte) 0);
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill3.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill3.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.5d));
        }
        extendPath3.setBackgroundAndFill(backgroundAndFill3);
        pathExList.add(extendPath3);
        return pathExList;
    }

    private static List<ExtendPath> getSoundPath(AutoShape autoShape, Rect rect) {
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        tempRect.set(rect);
        path.addRect(tempRect, Path.Direction.CW);
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        int min = Math.min(rect.width(), rect.height());
        ExtendPath extendPath2 = new ExtendPath();
        Path path2 = new Path();
        float f = (float) min;
        float f2 = 0.38f * f;
        float f3 = 0.14f * f;
        path2.moveTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) - f3);
        path2.lineTo(((float) rect.centerX()) - f3, ((float) rect.centerY()) - f3);
        float f4 = 0.1f * f;
        path2.lineTo(((float) rect.centerX()) + f4, ((float) rect.centerY()) - f2);
        path2.lineTo(((float) rect.centerX()) + f4, ((float) rect.centerY()) + f2);
        path2.lineTo(((float) rect.centerX()) - f3, ((float) rect.centerY()) + f3);
        path2.lineTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) + f3);
        path2.close();
        float min2 = Math.min(5.0f, 0.01f * f);
        float f5 = 0.18f * f;
        path2.moveTo(((float) rect.centerX()) + f5, ((float) rect.centerY()) - f3);
        float f6 = f * 0.28f;
        path2.lineTo(((float) rect.centerX()) + f2, ((float) rect.centerY()) - f6);
        path2.lineTo(((float) rect.centerX()) + f2, (((float) rect.centerY()) - f6) + min2);
        path2.lineTo(((float) rect.centerX()) + f5, (((float) rect.centerY()) - f3) + min2);
        path2.close();
        path2.moveTo(((float) rect.centerX()) + f5, (float) rect.centerY());
        path2.lineTo(((float) rect.centerX()) + f2, (float) rect.centerY());
        path2.lineTo(((float) rect.centerX()) + f2, ((float) rect.centerY()) + min2);
        path2.lineTo(((float) rect.centerX()) + f5, ((float) rect.centerY()) + min2);
        path2.close();
        path2.moveTo(((float) rect.centerX()) + f5, ((float) rect.centerY()) + f3);
        path2.lineTo(((float) rect.centerX()) + f2, ((float) rect.centerY()) + f6);
        path2.lineTo(((float) rect.centerX()) + f2, ((float) rect.centerY()) + f6 + min2);
        path2.lineTo(((float) rect.centerX()) + f5, ((float) rect.centerY()) + f3 + min2);
        path2.close();
        extendPath2.setPath(path2);
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR_LIGHT);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.5d));
        }
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath2);
        return pathExList;
    }

    private static List<ExtendPath> getHelpPath(AutoShape autoShape, Rect rect) {
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        tempRect.set(rect);
        path.addRect(tempRect, Path.Direction.CW);
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        int min = Math.min(rect.width(), rect.height());
        ExtendPath extendPath2 = new ExtendPath();
        Path path2 = new Path();
        float f = (float) min;
        float f2 = 0.2f * f;
        path2.moveTo(((float) rect.centerX()) - f2, ((float) rect.centerY()) - (0.16f * f));
        tempRect.set(((float) rect.centerX()) - f2, ((float) rect.centerY()) - (0.36f * f), ((float) rect.centerX()) + f2, ((float) rect.centerY()) + (0.04f * f));
        path2.arcTo(tempRect, 180.0f, 240.0f);
        float f3 = 0.05f * f;
        float f4 = 0.15f * f;
        tempRect.set(((float) rect.centerX()) + f3, ((float) rect.centerY()) + (0.012f * f), ((float) rect.centerX()) + f4, ((float) rect.centerY()) + (0.112f * f));
        path2.arcTo(tempRect, 270.0f, -90.0f);
        float f5 = 0.18f * f;
        path2.lineTo(((float) rect.centerX()) + f3, ((float) rect.centerY()) + f5);
        path2.lineTo(((float) rect.centerX()) - f3, ((float) rect.centerY()) + f5);
        float f6 = 0.1f * f;
        path2.lineTo(((float) rect.centerX()) - f3, ((float) rect.centerY()) + f6);
        tempRect.set(((float) rect.centerX()) - f3, ((float) rect.centerY()) - (0.073f * f), ((float) rect.centerX()) + f4, ((float) rect.centerY()) + (0.273f * f));
        path2.arcTo(tempRect, 180.0f, 90.0f);
        tempRect.set(((float) rect.centerX()) - f6, ((float) rect.centerY()) - (0.26f * f), ((float) rect.centerX()) + f6, ((float) rect.centerY()) - (0.06f * f));
        path2.arcTo(tempRect, 60.0f, -240.0f);
        path2.close();
        path2.addCircle((float) rect.centerX(), ((float) rect.centerY()) + (0.28f * f), f * 0.08f, Path.Direction.CW);
        extendPath2.setPath(path2);
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR_LIGHT);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.5d));
        }
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath2);
        return pathExList;
    }

    private static List<ExtendPath> getBlankPath(AutoShape autoShape, Rect rect) {
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        tempRect.set(rect);
        path.addRect(tempRect, Path.Direction.CW);
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        return pathExList;
    }
}
