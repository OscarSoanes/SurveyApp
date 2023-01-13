package com.example.surveyapp.adminController

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.surveyapp.R
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.DataList
import com.example.surveyapp.model.QuestionList
import com.example.surveyapp.model.Survey
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.lang.Integer.parseInt

class AdminDisplayAnalyticsActivity : AppCompatActivity() {
    private var globalId = 0
    private var survey = Survey(0,0,"","","")
    private var questionList = QuestionList()
    private var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_display_analytics)

        globalId = parseInt(intent.getStringExtra("id").toString())
        survey = intent.getSerializableExtra("survey") as Survey
        questionList = intent.getSerializableExtra("questionList") as QuestionList
        index = parseInt(intent.getStringExtra("index").toString())

        // change text values
        val txtModule = findViewById<TextView>(R.id.textTitle)
        val txtQuestion = findViewById<TextView>(R.id.textQuestion)
        txtModule.text = "${survey.module}'s Pie-chart"
        txtQuestion.text = "${questionList.getQuestion(index).questionText}"

        val btnPrevious = findViewById<Button>(R.id.btnPrevious)
        val btnNext = findViewById<Button>(R.id.btnNext)

        if (index == 0) {
            btnPrevious.visibility = View.INVISIBLE
        } else if (index == 9) {
            btnNext.visibility = View.INVISIBLE
        }

        // Pie Chart
        // ref: https://www.youtube.com/watch?v=S3zqxVoIUig&t
        var pieChart: PieChart = findViewById(R.id.pieChart)
        pieChart = setupPieChart(pieChart)
        loadPieChartData(pieChart)
    }

    private fun setupPieChart(pieChart: PieChart): PieChart {
        // center text
        pieChart.isDrawHoleEnabled = true
        pieChart.centerText = "Question ${index+1}/10"
        pieChart.setCenterTextSize(18f)

        // Set the percent values inside piechart
        pieChart.setUsePercentValues(true)
//        pieChart.setEntryLabelTextSize(12f)
//        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.setDrawEntryLabels(false)

        // small UI elements
        pieChart.description.isEnabled = false

        // legend (key for chart)
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
        var database = DataBaseHelper(this)
        var dataList: DataList = database.getAllDataBySurveyAndQuestionID(survey.surveyId, questionList.getQuestion(index).questionId)

        var colours = java.util.ArrayList<Int>()

        // adding the entries
        if (dataList.getStrongAgree() != 0) {
            entries.add(PieEntry(dataList.getStrongAgreeAsFloat(), "Strongly Agree"))
            colours.add(ColorTemplate.rgb("#69B34C"))
        }
        if (dataList.getAgree() != 0) {
            entries.add(PieEntry(dataList.getAgreeAsFloat(), "Agree"))
            colours.add(ColorTemplate.rgb("#ACB334"))
        }
        if (dataList.getNeutral() != 0) {
            entries.add(PieEntry(dataList.getNeutralAsFloat(), "Neutral"))
            colours.add(ColorTemplate.rgb("#FAB733"))
        }
        if (dataList.getDisagree() != 0) {
            entries.add(PieEntry(dataList.getDisagreeAsFloat(), "Disagree"))
            colours.add(ColorTemplate.rgb("#FF4E11"))
        }
        if (dataList.getStrongDisagree() != 0) {
            entries.add(PieEntry(dataList.getStrongDisagreeAsFloat(), "Strongly Disagree"))
            colours.add(ColorTemplate.rgb("#FF0D0D"))
        }


        var dataset = PieDataSet(entries, "")
        dataset.colors = colours

        // pushing data to chart
        var data = PieData(dataset)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.BLACK)

        // starting piechart
        pieChart.data = data
        pieChart.invalidate()
        return pieChart
    }

    fun btnNext(view: View) {
        index++

        val intent = Intent(this, AdminDisplayAnalyticsActivity::class.java).apply {
            putExtra("id", globalId.toString())
            putExtra("survey", survey)
            putExtra("questionList", questionList)
            putExtra("index", index.toString())
        }
        finish()
        startActivity(intent)
    }

    fun btnPrev(view: View) {
        index--

        val intent = Intent(this, AdminDisplayAnalyticsActivity::class.java).apply {
            putExtra("id", globalId.toString())
            putExtra("survey", survey)
            putExtra("questionList", questionList)
            putExtra("index", index.toString())
        }
        finish()
        startActivity(intent)
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