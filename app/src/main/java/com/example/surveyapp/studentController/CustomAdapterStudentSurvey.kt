package com.example.surveyapp.studentController

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.surveyapp.R
import com.example.surveyapp.model.Survey

class CustomAdapterStudentSurvey (private val appContext: Context, private val surveyList: ArrayList<Survey>,
                                  private var display: StudentPanelActivity) : BaseAdapter() {

    private val inflater: LayoutInflater
            = appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return surveyList.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var view: View? = view
        view = inflater.inflate(R.layout.activity_student_panel_survey_list_view, parent, false)

        val module = view.findViewById<TextView>(R.id.textModule)
        val endText = view.findViewById<TextView>(R.id.textEndDate)

        module.text = surveyList[position].module
        endText.text = surveyList[position].endDate

        val start = view.findViewById<Button>(R.id.btnStart)
        val surveyId = surveyList[position].surveyId

        start.setOnClickListener {
            display.btnStart(view, surveyId)
        }

        return view
    }
}