package com.example.surveyapp.studentController

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.surveyapp.R
import com.example.surveyapp.model.AnswerList
import com.example.surveyapp.model.QuestionList

class CustomAdapterStudentQuestionSurvey (
    private val appContext: Context, private val answers: AnswerList, private val questions: QuestionList
) : BaseAdapter() {
    private val inflater: LayoutInflater
            = appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return answers.getCount()
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var view: View? = view
        view = inflater.inflate(R.layout.activity_review_survey_questions_list_view, parent, false)

        val question = view.findViewById<TextView>(R.id.textQuestion)
        val answer = view.findViewById<TextView>(R.id.textAnswer)

        question.text = questions.getQuestion(position).questionText

        var answerMessage = ""
        when(answers.getAnswer(position).answer) {
            5 -> answerMessage = "You Strongly Agree"
            4 -> answerMessage = "You Agree"
            3 -> answerMessage = "You Neither Agree nor Disagree"
            2 -> answerMessage = "You Disagree"
            1 -> answerMessage = "You Strongly Disagree"
        }
        answer.text = answerMessage

        return view
    }
}