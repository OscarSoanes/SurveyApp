package com.example.surveyapp.adminController

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.surveyapp.R
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.DataList
import com.example.surveyapp.model.QuestionList
import com.example.surveyapp.model.Survey
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.lang.Integer.parseInt

class DisplayAverageActivity : AppCompatActivity() {
    private var globalId = 0
    private var survey = Survey(0, 0, "", "", "")
    private var questionList = QuestionList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_average)

        globalId = parseInt(intent.getStringExtra("id").toString())
        survey = intent.getSerializableExtra("survey") as Survey
        questionList = intent.getSerializableExtra("questionList") as QuestionList

        var barChart: BarChart = findViewById(R.id.barChart)
        barChart = setupBarChart(barChart)
        loadBarChartData(barChart)

        var moduleText = findViewById<TextView>(R.id.textTitle)
        moduleText.text = "${survey.module}'s Average"
    }

    private fun setupBarChart(barChart: BarChart): BarChart {
        barChart.setDrawValueAboveBar(true)
        barChart.description.isEnabled = false

        barChart.setExtraOffsets(1f, 1f, 1f, 1f);

        // x axis
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.axisMinimum = 0.5f
        barChart.xAxis.axisMaximum = 10.5f
        barChart.xAxis.labelCount = 5

        // font size
        barChart.axisLeft.textSize = 16f
        barChart.axisRight.textSize = 12f
        barChart.xAxis.textSize = 16f

        // labels
        var xAxisLabel = listOf("","Q1","Q2","Q3","Q4","Q5","Q6","Q7","Q8","Q9","Q10")
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)

        var yAxisLabelRight = listOf("", "S. Disagree", "Disagree", "Neutral", "Agree", "S. Agree")
        barChart.axisRight.valueFormatter = IndexAxisValueFormatter(yAxisLabelRight)

        // right axis stuff
        barChart.axisRight.axisMinimum = 1f
        barChart.axisRight.axisMaximum = 5f
        barChart.axisRight.labelCount = 4
        barChart.axisRight.setDrawTopYLabelEntry(true)

        // left axis stuff
        barChart.axisLeft.axisMinimum = 1f
        barChart.axisLeft.axisMaximum = 5f
        barChart.axisLeft.labelCount = 4
        barChart.axisLeft.setDrawTopYLabelEntry(true)

        // zoom
        barChart.zoom(2f, 1f, 1f, 1f)
        barChart.setPinchZoom(false)
        barChart.isDoubleTapToZoomEnabled = false

        barChart.legend.isEnabled = false
        return barChart
    }

    private fun loadBarChartData(barChart: BarChart) : BarChart {
        var entries = ArrayList<BarEntry>()
        var database = DataBaseHelper(this)
        var dataList: DataList
        var index = 1f
        for (question in questionList.getQuestions()) {

            dataList = database.getAllDataBySurveyAndQuestionID(survey.surveyId, question.questionId)
            entries.add(BarEntry(index, dataList.getAverageFloat()))
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

        return barChart
    }

    fun btnReturn(view: View) {
        val intent = Intent(this, AdminTableData::class.java).apply {
            putExtra("id", globalId.toString())
            putExtra("survey", survey)
        }
        finish()
        startActivity(intent)
    }
}