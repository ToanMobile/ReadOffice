package com.app.office.thirdpart.achartengine.renderers;

import android.graphics.Typeface;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DefaultRenderer implements Serializable {
    public static final int BACKGROUND_COLOR = -16777216;
    public static final int NO_COLOR = 0;
    private static final Typeface REGULAR_TEXT_FONT = Typeface.create(Typeface.SERIF, 0);
    public static final int TEXT_COLOR = -16777216;
    private boolean antialiasing = true;
    private BackgroundAndFill chartFill = null;
    private float defaultFontSize = 12.0f;
    private Line frame = null;
    private boolean mApplyBackgroundColor = true;
    private int mAxesColor = -16777216;
    private String mChartTitle = "";
    private float mChartTitleTextSize = 15.0f;
    private boolean mFitLegend = false;
    private int mLabelsColor = -16777216;
    private float mLabelsTextSize = 10.0f;
    private int mLegendHeight = 0;
    private float mLegendTextSize = 12.0f;
    private double[] mMargins = {0.1d, 0.05d, 0.1d, 0.05d};
    private float mOriginalScale = 1.0f;
    private List<SimpleSeriesRenderer> mRenderers = new ArrayList();
    private float mScale = 1.0f;
    private boolean mShowAxes = true;
    private boolean mShowChartTitle = true;
    private boolean mShowCustomTextGrid = false;
    private boolean mShowLabels = true;
    private boolean mShowLegend = true;
    private boolean mXShowGrid = false;
    private boolean mYShowGrid = false;
    private boolean mZoomButtonsVisible = false;
    private boolean mZoomEnabled = true;
    private float mZoomRate = 1.0f;
    private String textTypefaceName = REGULAR_TEXT_FONT.toString();
    private int textTypefaceStyle = 0;

    public int getBackgroundColor() {
        return -16777216;
    }

    public boolean isPanEnabled() {
        return false;
    }

    public void setBackgroundColor(int i) {
    }

    public void addSeriesRenderer(SimpleSeriesRenderer simpleSeriesRenderer) {
        this.mRenderers.add(simpleSeriesRenderer);
    }

    public void addSeriesRenderer(int i, SimpleSeriesRenderer simpleSeriesRenderer) {
        this.mRenderers.add(i, simpleSeriesRenderer);
    }

    public void removeSeriesRenderer(SimpleSeriesRenderer simpleSeriesRenderer) {
        this.mRenderers.remove(simpleSeriesRenderer);
    }

    public SimpleSeriesRenderer getSeriesRendererAt(int i) {
        return this.mRenderers.get(i);
    }

    public int getSeriesRendererCount() {
        return this.mRenderers.size();
    }

    public SimpleSeriesRenderer[] getSeriesRenderers() {
        return (SimpleSeriesRenderer[]) this.mRenderers.toArray(new SimpleSeriesRenderer[0]);
    }

    public BackgroundAndFill getBackgroundAndFill() {
        return this.chartFill;
    }

    public void setBackgroundAndFill(BackgroundAndFill backgroundAndFill) {
        this.chartFill = backgroundAndFill;
    }

    public Line getChartFrame() {
        return this.frame;
    }

    public void setChartFrame(Line line) {
        this.frame = line;
    }

    public void setShowChartTitle(boolean z) {
        this.mShowChartTitle = z;
    }

    public void setDefaultFontSize(float f) {
        this.defaultFontSize = f;
    }

    public float getDefaultFontSize() {
        return this.defaultFontSize;
    }

    public boolean isShowChartTitle() {
        return this.mShowChartTitle;
    }

    public float getChartTitleTextSize() {
        return this.mChartTitleTextSize;
    }

    public void setChartTitleTextSize(float f) {
        this.mChartTitleTextSize = f;
    }

    public String getChartTitle() {
        return this.mChartTitle;
    }

    public void setChartTitle(String str) {
        this.mChartTitle = str;
    }

    public boolean isApplyBackgroundColor() {
        return this.mApplyBackgroundColor;
    }

    public void setApplyBackgroundColor(boolean z) {
        this.mApplyBackgroundColor = z;
    }

    public int getAxesColor() {
        return this.mAxesColor;
    }

    public void setAxesColor(int i) {
        this.mAxesColor = i;
    }

    public int getLabelsColor() {
        return this.mLabelsColor;
    }

    public void setLabelsColor(int i) {
        this.mLabelsColor = i;
    }

    public float getLabelsTextSize() {
        return this.mLabelsTextSize;
    }

    public void setLabelsTextSize(float f) {
        this.mLabelsTextSize = f;
    }

    public boolean isShowAxes() {
        return this.mShowAxes;
    }

    public void setShowAxes(boolean z) {
        this.mShowAxes = z;
    }

    public boolean isShowLabels() {
        return this.mShowLabels;
    }

    public void setShowLabels(boolean z) {
        this.mShowLabels = z;
    }

    public boolean isShowGridH() {
        return this.mXShowGrid;
    }

    public void setShowGridH(boolean z) {
        this.mXShowGrid = z;
    }

    public boolean isShowGridV() {
        return this.mYShowGrid;
    }

    public void setShowGridV(boolean z) {
        this.mYShowGrid = z;
    }

    public boolean isShowCustomTextGrid() {
        return this.mShowCustomTextGrid;
    }

    public void setShowCustomTextGrid(boolean z) {
        this.mShowCustomTextGrid = z;
    }

    public boolean isShowLegend() {
        return this.mShowLegend;
    }

    public void setShowLegend(boolean z) {
        this.mShowLegend = z;
    }

    public boolean isFitLegend() {
        return this.mFitLegend;
    }

    public void setFitLegend(boolean z) {
        this.mFitLegend = z;
    }

    public String getTextTypefaceName() {
        return this.textTypefaceName;
    }

    public int getTextTypefaceStyle() {
        return this.textTypefaceStyle;
    }

    public float getLegendTextSize() {
        return this.mLegendTextSize;
    }

    public void setLegendTextSize(float f) {
        this.mLegendTextSize = f;
    }

    public void setTextTypeface(String str, int i) {
        this.textTypefaceName = str;
        this.textTypefaceStyle = i;
    }

    public boolean isAntialiasing() {
        return this.antialiasing;
    }

    public void setAntialiasing(boolean z) {
        this.antialiasing = z;
    }

    public float getScale() {
        return this.mScale;
    }

    public float getOriginalScale() {
        return this.mOriginalScale;
    }

    public void setScale(float f) {
        if (this.mOriginalScale == 1.0f) {
            this.mOriginalScale = f;
        }
        this.mScale = f;
    }

    public boolean isZoomEnabled() {
        return this.mZoomEnabled;
    }

    public void setZoomEnabled(boolean z) {
        this.mZoomEnabled = z;
    }

    public boolean isZoomButtonsVisible() {
        return this.mZoomButtonsVisible;
    }

    public void setZoomButtonsVisible(boolean z) {
        this.mZoomButtonsVisible = z;
    }

    public float getZoomRate() {
        return this.mZoomRate;
    }

    public void setZoomRate(float f) {
        this.mZoomRate = f;
    }

    public int getLegendHeight() {
        return this.mLegendHeight;
    }

    public void setLegendHeight(int i) {
        this.mLegendHeight = i;
    }

    public double[] getMargins() {
        return this.mMargins;
    }

    public void setMargins(double[] dArr) {
        this.mMargins = dArr;
    }
}
