package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.Question
import com.example.surveyapp.model.QuestionList
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt

class CreateQuestionActivity : AppCompatActivity() {
    var adminId = 0
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
            // this should be first question
        }

        try {
            val question = intent.getSerializableExtra("question") as Question
            if (question.questionText.isNotEmpty()) {
                var txtQuestion = findViewById<TextView>(R.id.editTextQuestion)
                txtQuestion.text = question.questionText
            }
        } catch (_: NullPointerException) {
            // user has not gone previous
        }

        adminId = id
        survey = data
        index = i

        // changing values
        var txtModule = findViewById<TextView>(R.id.textModule)
        var txtQuestionCount = findViewById<TextView>(R.id.textQuestionCount)
        var btnPrevious = findViewById<Button>(R.id.btnPrev)
        var btnNext = findViewById<Button>(R.id.btnNext)

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
        val previousQuestion: Question = questions.getQuestion(index)


        val intent = Intent(this, CreateQuestionActivity::class.java).apply {
            putExtra("id", adminId.toString())
            putExtra("survey", survey)
            putExtra("questions", questions)
            putExtra("index", index.toString())
            putExtra("question", previousQuestion)
        }
        startActivity(intent)
    }

    fun next(view: View) {
        val questionText = findViewById<EditText>(R.id.editTextQuestion).text.toString()

        // validation
        if (questionText.isEmpty()) {
            Toast.makeText(applicationContext, "Please input a question", Toast.LENGTH_LONG).show()
            return
        }

        // Add question to ArrayList
        val question = Question(-1, survey.surveyId, questionText)
        questions.addQuestion(question)
        index++

        Toast.makeText(applicationContext, "$questions", Toast.LENGTH_LONG).show()

        val intent = Intent(this, CreateQuestionActivity::class.java).apply {
            putExtra("id", adminId.toString())
            putExtra("survey", survey)
            putExtra("questions", questions)
            putExtra("index", index.toString())
        }
        startActivity(intent)
    }

    fun cancel(view: View) {

    }
}