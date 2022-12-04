package com.example.surveyapp.adminController

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.surveyapp.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class AdminDisplayAnalyticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_display_analytics)

        var pieChart: PieChart = findViewById(R.id.pieChart)
        pieChart = setupPieChart(pieChart)
        loadPieChartData(pieChart)
    }

    private fun setupPieChart(pieChart: PieChart): PieChart {
        pieChart.isDrawHoleEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)

        pieChart.description.isEnabled = false

        var legend = pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL

        legend.setDrawInside(false)
        legend.textSize = 14f
        legend.isEnabled = true
        legend.isWordWrapEnabled = true


        return pieChart
    }

    private fun loadPieChartData(pieChart: PieChart) : PieChart {
        var entries = ArrayList<PieEntry>()
        // TODO GET DATA FROM DATABASE

        entries.add(PieEntry(.2f, "Strongly Agree"))
        entries.add(PieEntry(0f, "Agree"))
        entries.add(PieEntry(.2f, "Neutral"))
        entries.add(PieEntry(.2f, "Disagree"))
        entries.add(PieEntry(.2f, "Strongly Disagree"))

        var colours = java.util.ArrayList<Int>()
        for (colour in ColorTemplate.MATERIAL_COLORS) {
            colours.add(colour)
        }

        for (colour in ColorTemplate.VORDIPLOM_COLORS) {
            colours.add(colour)
        }

        var dataset = PieDataSet(entries, "")
        dataset.colors = colours

        var data = PieData(dataset)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.BLACK)

        pieChart.data = data
        pieChart.invalidate()
        return pieChart
    }
}