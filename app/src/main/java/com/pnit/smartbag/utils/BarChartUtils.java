package com.pnit.smartbag.utils;

import android.graphics.Color;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;

public class BarChartUtils {

    public static int[] getColorsBarChart() {
        return (new int[]{Color.parseColor("#01DF01"),
                Color.parseColor("#FACC2E"),
                Color.parseColor("#FA5858")});
    }

    public static LimitLine getLimitLine(int limit, String label) {
        LimitLine l = new LimitLine(limit);
        l.setLabel(label);
        l.setLineColor(getColorsBarChart()[0]);
        l.setTextColor(getColorsBarChart()[0]);
        return l;
    }
}
