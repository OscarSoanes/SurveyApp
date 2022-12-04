package com.example.surveyapp.adminController

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.surveyapp.R
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.DataList
import com.example.surveyapp.model.Question
import java.text.DecimalFormat

class CustomAdapterAdminTableData (private val appContext: Context, private val questionList: ArrayList<Question>,
private var display: AdminTableData): BaseAdapter() {
    private val inflater: LayoutInflater =
        appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return questionList.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var view: View? = view
        view = inflater.inflate(R.layout.activity_admin_table_data_list_view, parent, false)

        // declaring stuff
        val database = DataBaseHelper(display)
        val questionText = view.findViewById<TextView>(R.id.textQuestion)

        val strongAgreeText = view.findViewById<TextView>(R.id.textStrongAgreePercent)
        val agreeText = view.findViewById<TextView>(R.id.textAgreePercent)
        val neutralText = view.findViewById<TextView>(R.id.textNeutralPercent)
        val disagreeText = view.findViewById<TextView>(R.id.textDisagreePercent)
        val strongDisagreeText = view.findViewById<TextView>(R.id.textStrongDisagreePercent)

        val averageText = view.findViewById<TextView>(R.id.textAveragePercent)

        var question = questionList[position]
        var questionId = question.questionId
        var surveyId = question.surveyId

        var dataList: DataList = database.getAllDataBySurveyAndQuestionID(surveyId, questionId)

        // if ends in x.0% hide the x.0%
        var df = DecimalFormat("0.#")

        // setting text
        questionText.text = question.questionText
        strongAgreeText.text = "${df.format(dataList.getStrongAgreePercent())}%"
        agreeText.text = "${df.format(dataList.getAgreePercent())}%"
        neutralText.text = "${df.format(dataList.getNeutralPercent())}%"
        disagreeText.text = "${df.format(dataList.getDisagreePercent())}%"
        strongDisagreeText.text = "${df.format(dataList.getStrongDisagreePercent())}%"
        averageText.text = dataList.getAverage()


        if (dataList.getAverage() == "Strongly Disagree") {
            averageText.textSize = 12f
            averageText.setTextColor(Color.parseColor("#FF0D0D"))
        }
        if (dataList.getAverage() == "Disagree") {
            averageText.textSize = 12f
            averageText.setTextColor(Color.parseColor("#FF4E11"))
        }
        if (dataList.getAverage() == "Neutral") {
            averageText.setTextColor(Color.parseColor("#FAB733"))
        }
        if (dataList.getAverage() == "Agree") {
            averageText.setTextColor(Color.parseColor("#ACB334"))
        }

        if (dataList.getAverage() == "Strongly Agree") {
            averageText.setTextColor(Color.parseColor("#69B34C"))
        }



        return view
    }
}