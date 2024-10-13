package com.app.office.thirdpart.achartengine;

import com.app.office.thirdpart.achartengine.chart.AbstractChart;
import com.app.office.thirdpart.achartengine.chart.BubbleChart;
import com.app.office.thirdpart.achartengine.chart.ColumnBarChart;
import com.app.office.thirdpart.achartengine.chart.CombinedXYChart;
import com.app.office.thirdpart.achartengine.chart.DialChart;
import com.app.office.thirdpart.achartengine.chart.DoughnutChart;
import com.app.office.thirdpart.achartengine.chart.LineChart;
import com.app.office.thirdpart.achartengine.chart.PieChart;
import com.app.office.thirdpart.achartengine.chart.RangeBarChart;
import com.app.office.thirdpart.achartengine.chart.ScatterChart;
import com.app.office.thirdpart.achartengine.chart.TimeChart;
import com.app.office.thirdpart.achartengine.model.CategorySeries;
import com.app.office.thirdpart.achartengine.model.MultipleCategorySeries;
import com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset;
import com.app.office.thirdpart.achartengine.renderers.DefaultRenderer;
import com.app.office.thirdpart.achartengine.renderers.DialRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;

public class ChartFactory {
    public static final String CHART = "chart";
    public static final String TITLE = "title";

    private ChartFactory() {
    }

    public static final AbstractChart getLineChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        return new LineChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
    }

    public static final AbstractChart getScatterChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        return new ScatterChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
    }

    public static final AbstractChart getBubbleChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        return new BubbleChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
    }

    public static final AbstractChart getTimeChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, String str) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        TimeChart timeChart = new TimeChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        timeChart.setDateFormat(str);
        return timeChart;
    }

    public static final AbstractChart getColumnBarChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, ColumnBarChart.Type type) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        return new ColumnBarChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer, type);
    }

    public static final AbstractChart getRangeBarChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, ColumnBarChart.Type type) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        return new RangeBarChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer, type);
    }

    public static final AbstractChart getCombinedXYChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, String[] strArr) {
        if (xYMultipleSeriesDataset == null || xYMultipleSeriesRenderer == null || strArr == null || xYMultipleSeriesDataset.getSeriesCount() != strArr.length) {
            throw new IllegalArgumentException("Dataset, renderer and types should be not null and the datasets series count should be equal to the types length");
        }
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        return new CombinedXYChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer, strArr);
    }

    public static final AbstractChart getPieChart(CategorySeries categorySeries, DefaultRenderer defaultRenderer) {
        checkParameters(categorySeries, defaultRenderer);
        return new PieChart(categorySeries, defaultRenderer);
    }

    public static final AbstractChart getDialChartView(CategorySeries categorySeries, DialRenderer dialRenderer) {
        checkParameters(categorySeries, (DefaultRenderer) dialRenderer);
        return new DialChart(categorySeries, dialRenderer);
    }

    public static final AbstractChart getDoughnutChartView(MultipleCategorySeries multipleCategorySeries, DefaultRenderer defaultRenderer) {
        checkParameters(multipleCategorySeries, defaultRenderer);
        return new DoughnutChart(multipleCategorySeries, defaultRenderer);
    }

    private static void checkParameters(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        if (xYMultipleSeriesDataset == null || xYMultipleSeriesRenderer == null || xYMultipleSeriesDataset.getSeriesCount() != xYMultipleSeriesRenderer.getSeriesRendererCount()) {
            throw new IllegalArgumentException("Dataset and renderer should be not null and should have the same number of series");
        }
    }

    private static void checkParameters(CategorySeries categorySeries, DefaultRenderer defaultRenderer) {
        if (categorySeries == null || defaultRenderer == null || categorySeries.getItemCount() != defaultRenderer.getSeriesRendererCount()) {
            throw new IllegalArgumentException("Dataset and renderer should be not null and the dataset number of items should be equal to the number of series renderers");
        }
    }

    private static void checkParameters(MultipleCategorySeries multipleCategorySeries, DefaultRenderer defaultRenderer) {
        if (multipleCategorySeries == null || defaultRenderer == null || !checkMultipleSeriesItems(multipleCategorySeries, defaultRenderer.getSeriesRendererCount())) {
            throw new IllegalArgumentException("Titles and values should be not null and the dataset number of items should be equal to the number of series renderers");
        }
    }

    private static boolean checkMultipleSeriesItems(MultipleCategorySeries multipleCategorySeries, int i) {
        int categoriesCount = multipleCategorySeries.getCategoriesCount();
        boolean z = true;
        for (int i2 = 0; i2 < categoriesCount && z; i2++) {
            z = multipleCategorySeries.getValues(i2).length == multipleCategorySeries.getTitles(i2).length;
        }
        return z;
    }
}
