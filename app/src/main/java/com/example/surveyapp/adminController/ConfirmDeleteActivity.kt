package com.example.surveyapp.adminController

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.surveyapp.R
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt

class ConfirmDeleteActivity : AppCompatActivity() {
    private var adminId = 0
    var survey = Survey(0,0,"","","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_delete)

        val id = parseInt(intent.getStringExtra("id").toString())
        val data = intent.getSerializableExtra("survey") as Survey

        adminId = id
        survey = data

        val moduleText = findViewById<TextView>(R.id.textModuleCode)
        moduleText.text = survey.module
    }

    fun btnDelete(view: View) {
        val database = DataBaseHelper(this)
        database.deleteSurvey(survey)

        database.deleteQuestionBySurveyId(survey.surveyId)

        Toast.makeText(applicationContext, "Successfully deleted", Toast.LENGTH_LONG).show()
        val intent = Intent(this, AdminDisplaySurveysActivity::class.java).apply {
            putExtra("id", adminId.toString())
        }
        finish()
        startActivity(intent)
    }

    fun btnCancel(view: View) {
        val intent = Intent(this, EditSurveyActivity::class.java).apply {
            putExtra("id", adminId.toString())
            putExtra("survey", survey.surveyId.toString())
        }
        finish()
        startActivity(intent)
    }
}