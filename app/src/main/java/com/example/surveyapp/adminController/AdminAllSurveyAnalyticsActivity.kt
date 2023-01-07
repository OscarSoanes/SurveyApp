package com.example.surveyapp.adminController

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.surveyapp.R
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.DataList
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.lang.Integer.parseInt

class AdminAllSurveyAnalyticsActivity : AppCompatActivity() {
    private var globalId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_all_survey_analytics)

        globalId = parseInt(intent.getStringExtra("id").toString())



        var barChart: BarChart = findViewById(R.id.barChart)
        barChart = setupBarChart(barChart)
        loadBarChartData(barChart)
    }



    private fun setupBarChart(barChart: BarChart): BarChart {
        barChart.setDrawValueAboveBar(true)
        barChart.description.isEnabled = false
        barChart.axisRight.isEnabled = false

        barChart.setExtraOffsets(1f, 1f, 1f, 1f)

        // x axis
        val database = DataBaseHelper(this)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.axisMinimum = 0.5f


        // font size
        barChart.axisLeft.textSize = 16f
        barChart.axisRight.textSize = 12f
        barChart.xAxis.textSize = 16f
//        barChart.xAxis.setCenterAxisLabels(true)
        barChart.xAxis.labelRotationAngle = 45f
        // labels
        val xAxisLabel = mutableListOf("")

        var count = 0f

        for (survey in database.getAllSurveysByAdminId(globalId)) {
            if (database.getResponseGreaterThanOne(survey.surveyId)) {
                xAxisLabel += "${survey.module}"
                count ++
            }
        }
        barChart.xAxis.labelCount = count.toInt()

            barChart.xAxis.axisMaximum = count + 0.5f

                // left axis stuff
        barChart.axisLeft.axisMinimum = 1f
        barChart.axisLeft.axisMaximum = 5f
        barChart.axisLeft.labelCount = 4
        barChart.axisLeft.setDrawTopYLabelEntry(true)

        // zoom
        barChart.zoom(1f, 1f, 1f, 1f)
        if (count > 5) {
            barChart.zoom(2f,1f,1f,1f)
        }

        barChart.setPinchZoom(false)
        barChart.isDoubleTapToZoomEnabled = false

        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)

        barChart.legend.isEnabled = false
        return barChart
    }

    private fun loadBarChartData(barChart: BarChart) {
        var entries = ArrayList<BarEntry>()
        var database = DataBaseHelper(this)
        var dataList: DataList
        var index = 1f

        var value: Float
        for (survey in database.getAllSurveysByAdminId(globalId)) {
            // ensuring survey actually has data to use
            if (!database.getResponseGreaterThanOne(survey.surveyId)) {
                continue
            }
            value = 0f

            // loop through all questions
            for (question in database.getAllQuestionsBySurveyID(survey.surveyId)) {
                dataList = database.getAllDataBySurveyAndQuestionID(survey.surveyId, question.questionId)
                value += dataList.getAverageFloat()
            }

            entries.add(BarEntry(index, value / 10))
            index++
        }

        var colours = ArrayList<Int>()
        for (colour in ColorTemplate.JOYFUL_COLORS) {
            colours.add(colour)
        }

        // pushing data to barchart
        var dataset = BarDataSet(entries, "")
        var data = BarData(dataset)
        dataset.colors = colours
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.BLACK)

        // starting barchart
        barChart.setFitBars(true)
        barChart.data = data
        barChart.invalidate()
    }

    fun btnReturn(view: View) {
        val intent = Intent(this, AdminDisplaySurveysActivity::class.java).apply {
            putExtra("id", globalId.toString())
        }
        finish()
        startActivity(intent)
    }
}