package com.example.surveyapp.studentController

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.example.surveyapp.R
import com.example.surveyapp.model.*
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
            val answersList = intent.getSerializableExtra("answerList") as AnswerList
            answers = answersList
        } catch (_: NullPointerException) {
        }

        // text changing
        val textQuestion = findViewById<TextView>(R.id.textQuestion)
        val textTitle = findViewById<TextView>(R.id.textTitle)
        val textQuestionCount = findViewById<TextView>(R.id.textQuestionCount)


        textTitle.text = "${survey.module} Survey"
        textQuestionCount.text = "Question ${index + 1}/10"
        textQuestion.text = questions.getQuestion(index).questionText

        if (index != answers.getCount()) {
            when (answers.getAnswer(index).answer) {
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
        if (answers.getCount() == 0 || index == 0) {
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
            putExtra("answerList", answers)
        }
        startActivity(intent)
    }

    fun next(view: View) {
        fun isChecked(checked: Boolean, new: Int, current: Int) : Int {
            if (checked) {
                when(new) {
                    5 -> return 5
                    4 -> return 4
                    3 -> return 3
                    2 -> return 2
                    1 -> return 1
                }
            }
            return current
        }

        val strongAgree = findViewById<RadioButton>(R.id.radioStrongAgree).isChecked
        val agree = findViewById<RadioButton>(R.id.radioAgree).isChecked
        val neutral = findViewById<RadioButton>(R.id.radioNeutral).isChecked
        val disagree = findViewById<RadioButton>(R.id.radioDisagree).isChecked
        val strongDisagree = findViewById<RadioButton>(R.id.radioStrongDisagree).isChecked


        // checking if answer is empty, otherwise will store answer
        var answer = 0
        answer = isChecked(strongAgree, 5, answer)
        answer = isChecked(agree, 4, answer)
        answer = isChecked(neutral, 3, answer)
        answer = isChecked(disagree, 2, answer)
        answer = isChecked(strongDisagree, 1, answer)

        if (answer == 0) {
            Toast.makeText(applicationContext, "Please answer the question", Toast.LENGTH_LONG).show()
            return
        }

        val response = StudentSurveyResponse(-1, studentId, survey.surveyId, questions.getQuestion(index).questionId, answer)

        if (index == answers.getCount()) {
            answers.addAnswer(response)
        } else {
            answers.updateAnswerAtIndex(index, response)
        }

        index++

        if (index != 10) {
            val intent = Intent(this, StudentAnswerQuestionActivity::class.java).apply {
                putExtra("id", studentId.toString())
                putExtra("survey", survey)
                putExtra("questions", questions)
                putExtra("index", index.toString())
                putExtra("answerList", answers)
            }
            startActivity(intent)
        } else {
            var intent = Intent(this, ReviewSurveyActivity::class.java).apply {
                putExtra("id", studentId.toString())
                putExtra("answerList", answers)
                putExtra("questionList", questions)
                putExtra("survey", survey)
            }
            startActivity(intent)
        }


    }

    fun radioStrongAgree(view: View) {
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.strongagree)
    }

    fun radioAgree(view: View) {
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.agree)
    }

    fun radioNeutral(view: View) {
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.neutral)
    }

    fun radioDisagree(view:View) {
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.disagree)
    }

    fun radioStrongDisagree(view: View) {
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.strongdisagree)
    }
}