package com.example.surveyapp.adminController

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.surveyapp.R
import com.example.surveyapp.model.QuestionList

class CustomAdapterAdminQuestionSurvey (private val appContext: Context, private val questions: QuestionList) :
BaseAdapter() {

    private val inflater: LayoutInflater
    = appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return questions.getCount()
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var view: View? = view
        view = inflater.inflate(R.layout.activity_admin_questions_list_view, parent, false)

        val questionNo = view.findViewById<TextView>(R.id.textQuestionNumber)
        val question = view.findViewById<TextView>(R.id.textQuestion)

        questionNo.text = "Question ${position + 1}"
        question.text = questions.getQuestion(position).questionText

        return view
    }
}