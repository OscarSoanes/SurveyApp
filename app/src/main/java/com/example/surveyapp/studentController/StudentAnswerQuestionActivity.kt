package com.example.surveyapp.studentController

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.example.surveyapp.R
import com.example.surveyapp.model.AnswerList
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.QuestionList
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt

class StudentAnswerQuestionActivity : AppCompatActivity() {
    var studentId = 0
    var survey = Survey(0,0,"","","")
    var index = 0
    var questions = QuestionList()
    var answers = AnswerList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_answer_question)

        val id = parseInt(intent.getStringExtra("id").toString())
        val data = intent.getSerializableExtra("survey") as Survey
        val i = parseInt(intent.getStringExtra("index").toString())

        studentId = id
        survey = data
        index = i

        // Check if questions have been made, if not, make questions by survey id
        try {
            val questionList = intent.getSerializableExtra("questions") as QuestionList
            questions = questionList
        } catch (_: NullPointerException) {
            val db = DataBaseHelper(this)
            questions.addQuestions(db.getAllQuestionsBySurveyID(survey.surveyId))
        }

        // Checking answer list
        try {
            val answersList = intent.getSerializableExtra("answers") as AnswerList
            answers = answersList
        } catch (_: NullPointerException) {
            // there has been no answers stored yet, nothing serious happens
        }

        // text changing
        val textTitle = findViewById<TextView>(R.id.textTitle)
        val textQuestionCount = findViewById<TextView>(R.id.textQuestionCount)
        val textQuestion = findViewById<TextView>(R.id.textQuestion)

        textTitle.text = "${survey.module} Survey"
        textQuestionCount.text = "Question ${index + 1}/10"
        textQuestion.text = questions.getQuestion(index).questionText

        // radio buttons

        if (index != answers.getCount()) {
            when (answers.getAnswer(index)) {
                5 -> {
                    val selected = findViewById<RadioButton>(R.id.radioStrongAgree)
                    selected.isChecked = true
                }
                4 -> {
                    val selected = findViewById<RadioButton>(R.id.radioAgree)
                    selected.isChecked = true
                }
                3 -> {
                    val selected = findViewById<RadioButton>(R.id.radioNeutral)
                    selected.isChecked = true
                }
                2 -> {
                    val selected = findViewById<RadioButton>(R.id.radioDisagree)
                    selected.isChecked = true
                }
                1 -> {
                    val selected = findViewById<RadioButton>(R.id.radioStrongAgree)
                    selected.isChecked = true
                }
            }
        }

        // button checking
        val btnPrevious = findViewById<Button>(R.id.btnPrev)
        val btnNext = findViewById<Button>(R.id.btnNext)
        if (questions.getCount() == 0 || index == 0) {
            btnPrevious.visibility = View.INVISIBLE
        }
        if (index == 9) {
            btnNext.text = "Save"
        }
    }

    fun previous(view: View) {
        index--

        val intent = Intent(this, StudentAnswerQuestionActivity::class.java).apply {
            putExtra("id", studentId.toString())
            putExtra("survey", survey)
            putExtra("questions", questions)
            putExtra("index", index.toString())
            putExtra("answers", answers)
        }
    }
}