package com.example.surveyapp.adminController

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.surveyapp.R
import com.example.surveyapp.model.Question
import com.example.surveyapp.model.QuestionList
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt

class CreateQuestionActivity : AppCompatActivity() {
    private var adminId = 0
    var survey = Survey(0, 0, "", "", "")
    var questions = QuestionList()
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_question)

        // collecting records
        val id = parseInt(intent.getStringExtra("id").toString())
        val data = intent.getSerializableExtra("survey") as Survey
        val i = parseInt(intent.getStringExtra("index").toString())

        // Values that could be null based on initialisation
        try {
            val questionList = intent.getSerializableExtra("questions") as QuestionList
            questions = questionList
        } catch (_: NullPointerException) {
            // this is the initial first question, nothing serious happens
        }

        adminId = id
        survey = data
        index = i

        val txtQuestion = findViewById<EditText>(R.id.editTextQuestion)
        if (index != questions.getCount()) {
            txtQuestion.setText(questions.getQuestion(index).questionText)
        }

        // changing values
        val txtModule = findViewById<TextView>(R.id.textModule)
        val txtQuestionCount = findViewById<TextView>(R.id.textQuestionCount)
        val btnPrevious = findViewById<Button>(R.id.btnPrev)
        val btnNext = findViewById<Button>(R.id.btnNext)

        txtModule.text = survey.module
        txtQuestionCount.text = "Question ${index + 1}/10"

        // button checking
        if (questions.getCount() == 0 || index == 0) {
            btnPrevious.visibility = View.INVISIBLE
        }
        if (index == 9) {
            btnNext.text = "Save"
        }
    }

    fun previous(view: View) {
        index--

        val intent = Intent(this, CreateQuestionActivity::class.java).apply {
            putExtra("id", adminId.toString())
            putExtra("survey", survey)
            putExtra("questions", questions)
            putExtra("index", index.toString())
        }
        finish()
        startActivity(intent)

    }

    fun next(view: View) {
        val questionText = findViewById<EditText>(R.id.editTextQuestion).text.toString()

        // validation
        if (questionText.isEmpty()) {
            Toast.makeText(applicationContext, "Please input a question", Toast.LENGTH_LONG).show()
            return
        }

        // Add question to ArrayList or update at current index if we have data later on
        val question = Question(-1, survey.surveyId, questionText)
        if (index == questions.getCount()) {
            questions.addQuestion(question)
        } else {
            questions.updateQuestionAtIndex(index, question)
        }

        index++

        // if we are at point need to save
        if (index != 10) {
            val intent = Intent(this, CreateQuestionActivity::class.java).apply {
                putExtra("id", adminId.toString())
                putExtra("survey", survey)
                putExtra("questions", questions)
                putExtra("index", index.toString())
            }
            finish()
            startActivity(intent)
        } else {
            val intent = Intent(this, ConfirmSurveyAdminActivity::class.java).apply {
                putExtra("id", adminId.toString())
                putExtra("survey", survey)
                putExtra("questions", questions)
            }
            startActivity(intent)
        }
    }

    fun cancel(view: View) {
        val intent = Intent(this, AdminDisplaySurveysActivity::class.java).apply {
            putExtra("id", adminId.toString())
        }
        startActivity(intent)
    }
}