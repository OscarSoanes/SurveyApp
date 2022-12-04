package com.example.surveyapp.adminController

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.surveyapp.R
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.DataList
import com.example.surveyapp.model.Question

class CustomAdapterAdminTableData (private val appContext: Context, private val questionList: ArrayList<Question>,
private var display: AdminTableData): BaseAdapter() {
    private val inflater: LayoutInflater
            = appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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

        // setting text
        questionText.text = question.questionText
        strongAgreeText.text = "${dataList.getStrongAgreePercent()}%"
        agreeText.text = "${dataList.getAgreePercent()}%"
        neutralText.text = "${dataList.getNeutralPercent()}%"
        disagreeText.text = "${dataList.getDisagreePercent()}%"
        strongDisagreeText.text = "${dataList.getStrongDisagreePercent()}%"

        return view
    }
}