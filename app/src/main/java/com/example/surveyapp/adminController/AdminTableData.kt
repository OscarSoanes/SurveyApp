package com.example.surveyapp.adminController

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.surveyapp.R
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.Question
import com.example.surveyapp.model.QuestionList
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt

class AdminTableData : AppCompatActivity() {
    private var globalId = 0
    private var survey = Survey(0,0,"","","")
    private var questionList = QuestionList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_table_data)

        val adminId = parseInt(intent.getStringExtra("id").toString())
        globalId = adminId

        val data = intent.getSerializableExtra("survey") as Survey
        survey = data

        val database = DataBaseHelper(this)
        val allQuestions: ArrayList<Question> = database.getAllQuestionsBySurveyID(survey.surveyId)

        questionList.addQuestions(allQuestions)

        val displayList = findViewById<ListView>(R.id.listData)
        val customAdapterAdminTableData = CustomAdapterAdminTableData(applicationContext, allQuestions, this)

        displayList.adapter = customAdapterAdminTableData

        val textTitle = findViewById<TextView>(R.id.textTitle)
        textTitle.text = "${survey.module}'s Data"
    }

    fun participants(count: Int) {
        val participantsText = findViewById<TextView>(R.id.textParticipants)
        participantsText.text = "Participants: $count"
    }

    fun btnReturn(view: View) {
        val intent = Intent(this, AdminDisplaySurveysActivity::class.java).apply {
            putExtra("id", globalId.toString())
        }
        finish()
        startActivity(intent)
    }

    fun btnAnalytics(view: View) {
        val intent = Intent(this, AdminDisplayAnalyticsActivity::class.java).apply {
            putExtra("id", globalId.toString())
            putExtra("survey", survey)
            putExtra("questionList", questionList)
            putExtra("index", "0")
        }
        startActivity(intent)
    }

    fun disableAnalytics() {
        val analytics = findViewById<Button>(R.id.btnChart)
        val average = findViewById<Button>(R.id.btnAverage)
        analytics.visibility = View.INVISIBLE
        average.visibility = View.INVISIBLE
    }

    fun btnAverage(view: View) {
        val intent = Intent(this, DisplayAverageActivity::class.java).apply {
            putExtra("id", globalId.toString())
            putExtra("survey", survey)
            putExtra("questionList", questionList)
        }
        startActivity(intent)
    }
}