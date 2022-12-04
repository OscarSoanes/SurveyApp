package com.example.surveyapp.adminController

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.surveyapp.R
import com.example.surveyapp.model.Survey

class CustomAdapterAdminSurvey (private val appContext: Context, private val surveyList: ArrayList<Survey>,
private var display: AdminDisplaySurveysActivity
) : BaseAdapter() {

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
        view = inflater.inflate(R.layout.activity_admin_display_survey_list_view, parent, false)

        val module = view.findViewById<TextView>(R.id.textModule)
        module.text = surveyList[position].module

        // storing survey ID to buttons
        val edit = view.findViewById<Button>(R.id.btnEdit)
        val data = view.findViewById<Button>(R.id.btnData)

        /**
        Acts as on Click
         */
        val surveyId = surveyList[position].surveyId
        edit.setOnClickListener {
            display.btnEdit(view, surveyId)
            }

        data.setOnClickListener {
            display.btnData(view, surveyList[position])
        }

        return view
    }
}