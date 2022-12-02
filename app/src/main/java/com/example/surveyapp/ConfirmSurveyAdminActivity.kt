package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.surveyapp.model.DataBaseHelper
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

    fun btnSave(view: View) {
        val database = DataBaseHelper(this)

        val surveyId = database.addSurvey(survey).toInt()
        questions = questions.updateQuestionSurveyId(surveyId)

        for (question in questions.getQuestions()) {
            database.addQuestion(question)
        }

        Toast.makeText(applicationContext, "Record saved!", Toast.LENGTH_LONG).show()
        val intent = Intent(this, AdminDisplaySurveysActivity::class.java).apply {
            putExtra("id", adminId.toString())
        }
        startActivity(intent)
    }

    fun btnReturn(view: View) {
        val intent = Intent(this, CreateQuestionActivity::class.java).apply {
            putExtra("id", adminId.toString())
            putExtra("survey", survey)
            putExtra("index", "0")
            putExtra("questions", questions)
        }

        startActivity(intent)
    }
}