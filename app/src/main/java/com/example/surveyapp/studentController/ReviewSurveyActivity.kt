package com.example.surveyapp.studentController

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.surveyapp.R
import com.example.surveyapp.model.AnswerList
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.QuestionList
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt

class ReviewSurveyActivity : AppCompatActivity() {
    var studentId = 0
    var answerList = AnswerList()
    var questionList = QuestionList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_survey)

        val id = parseInt(intent.getStringExtra("id").toString())
        val data = intent.getSerializableExtra("answerList") as AnswerList
        val questions = intent.getSerializableExtra("questionList") as QuestionList
        val survey = intent.getSerializableExtra("survey") as Survey

        studentId = id
        answerList = data
        questionList = questions

        val moduleText = findViewById<TextView>(R.id.textModule)
        moduleText.text = survey.module

        val displayList = findViewById<ListView>(R.id.listQuestions)
        val customAdapterStudentQuestionSurvey = CustomAdapterStudentQuestionSurvey(applicationContext,
        answerList, questionList)
        displayList.adapter = customAdapterStudentQuestionSurvey
    }

    fun btnSave(view: View) {
        val database = DataBaseHelper(this)

        for (answer in answerList.getAnswers()) {
            database.addResponse(answer)
        }

        Toast.makeText(applicationContext, "Saved response", Toast.LENGTH_LONG).show()
        val intent = Intent(this, StudentPanelActivity::class.java).apply {
            putExtra("id", studentId.toString())
        }
        startActivity(intent)
    }

    fun btnReturn(view: View) {
        val survey = intent.getSerializableExtra("survey") as Survey

        val intent = Intent(this, StudentAnswerQuestionActivity::class.java).apply {
            putExtra("id", studentId.toString())
            putExtra("survey", survey)
            putExtra("index", "0")
            putExtra("questions", questionList)
            putExtra("answerList", answerList)
        }
        startActivity(intent)
    }
}