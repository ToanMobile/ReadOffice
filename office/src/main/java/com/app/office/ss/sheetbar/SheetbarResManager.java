package com.app.office.ss.sheetbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

public class SheetbarResManager {
    private Context context;
    private Drawable focusLeft;
    private Drawable focusMiddle;
    private Drawable focusRight;
    private Drawable hSeparator;
    private Drawable normalLeft;
    private Drawable normalMiddle;
    private Drawable normalRight;
    private Drawable pushLeft;
    private Drawable pushMiddle;
    private Drawable pushRight;
    private Drawable sheetbarBG;
    private Drawable sheetbarLeftShadow;
    private Drawable sheetbarRightShadow;

    public SheetbarResManager(Context context2) {
        this.context = context2;
        ClassLoader classLoader = context2.getClassLoader();
        this.sheetbarBG = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBAR_BG), SheetbarResConstant.RESNAME_SHEETBAR_BG);
        this.sheetbarLeftShadow = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBAR_SHADOW_LEFT), SheetbarResConstant.RESNAME_SHEETBAR_SHADOW_LEFT);
        this.sheetbarRightShadow = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBAR_SHADOW_RIGHT), SheetbarResConstant.RESNAME_SHEETBAR_SHADOW_RIGHT);
        this.hSeparator = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBAR_SEPARATOR_H), SheetbarResConstant.RESNAME_SHEETBAR_SEPARATOR_H);
        this.normalLeft = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_LEFT), SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_LEFT);
        this.normalRight = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_RIGHT), SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_RIGHT);
        this.normalMiddle = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_MIDDLE), SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_MIDDLE);
        this.pushLeft = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_LEFT), SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_LEFT);
        this.pushMiddle = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_MIDDLE), SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_MIDDLE);
        this.pushRight = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_RIGHT), SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_RIGHT);
        this.focusLeft = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_LEFT), SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_LEFT);
        this.focusMiddle = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_MIDDLE), SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_MIDDLE);
        this.focusRight = Drawable.createFromResourceStream(context2.getResources(), (TypedValue) null, classLoader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_RIGHT), SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_RIGHT);
    }

    public Drawable getDrawable(short s) {
        switch (s) {
            case 0:
                return this.sheetbarBG;
            case 1:
                return this.sheetbarLeftShadow;
            case 2:
                return this.sheetbarRightShadow;
            case 3:
                return this.hSeparator;
            case 4:
                return this.normalLeft;
            case 5:
                return Drawable.createFromResourceStream(this.context.getResources(), (TypedValue) null, this.context.getClassLoader().getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_MIDDLE), SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_MIDDLE);
            case 6:
                return this.normalRight;
            case 7:
                return this.pushLeft;
            case 8:
                return this.pushMiddle;
            case 9:
                return this.pushRight;
            case 10:
                return this.focusLeft;
            case 11:
                return this.focusMiddle;
            case 12:
                return this.focusRight;
            default:
                return null;
        }
    }

    public void dispose() {
        this.sheetbarBG = null;
        this.sheetbarLeftShadow = null;
        this.sheetbarRightShadow = null;
        this.hSeparator = null;
        this.normalLeft = null;
        this.normalMiddle = null;
        this.normalRight = null;
        this.pushLeft = null;
        this.pushMiddle = null;
        this.pushRight = null;
        this.focusLeft = null;
        this.focusMiddle = null;
        this.focusRight = null;
    }
}
