package com.example.surveyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.example.surveyapp.model.QuestionList
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt

class ConfirmSurveyAdminActivity : AppCompatActivity() {
    var adminId = 0
    var survey = Survey(0, 0, "", "", "")
    var questions = QuestionList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_survey_admin)

        val id = parseInt(intent.getStringExtra("id").toString())
        val data = intent.getSerializableExtra("survey") as Survey
        val questionList = intent.getSerializableExtra("questions") as QuestionList

        adminId = id
        survey = data
        questions = questionList

        val module = findViewById<TextView>(R.id.textModule)
        module.setText(survey.module)

        val displayList = findViewById<ListView>(R.id.listQuestions)
        val customAdapterAdminQuestionSurvey = CustomAdapterAdminQuestionSurvey(applicationContext,
        questionList)

        displayList.adapter = customAdapterAdminQuestionSurvey

    }
}