package com.example.surveyapp.studentController

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.surveyapp.MainActivity
import com.example.surveyapp.R
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt

class StudentPanelActivity : AppCompatActivity() {
    private var studentId = 0
    private var surveyList = ArrayList<Survey>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_panel)

        val id = parseInt(intent.getStringExtra("id").toString())
        studentId = id

        val database = DataBaseHelper(this)
        surveyList.addAll(database.getAllSurveysToBeCompleted(studentId))

        val displayList = findViewById<ListView>(R.id.listSurveysToComplete)
        val customAdapterStudentSurvey = CustomAdapterStudentSurvey(applicationContext, surveyList, this)

        displayList.adapter = customAdapterStudentSurvey

        var popupMessage = findViewById<TextView>(R.id.textNoSurveys)
        if (surveyList.isEmpty()) {
            popupMessage.visibility = View.VISIBLE
        } else {
            popupMessage.visibility = View.INVISIBLE
        }
    }

    fun btnLogOut(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun btnStart(view: View, survey: Survey) {
        var intent = Intent(this, StudentAnswerQuestionActivity::class.java).apply {
            putExtra("id", studentId.toString())
            putExtra("survey", survey)
            putExtra("index", "0")
        }
        startActivity(intent)
    }
}