package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt

class NewSurveyActivity : AppCompatActivity() {
    var globalId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_survey)

        val id = parseInt(intent.getStringExtra("id").toString())
        globalId = id
    }

    fun btnSave(view: View) {
        val title = findViewById<EditText>(R.id.editTextTitle).text.toString()
        val startDate = findViewById<EditText>(R.id.editTextStartDate).text.toString()
        val endDate = findViewById<EditText>(R.id.editTextEndDate).text.toString()

        // TODO VALIDATION ON DATES

        val newSurvey = Survey(-1, globalId, title, startDate, endDate)
        val database = DataBaseHelper(this)

        when (database.addSurvey(newSurvey)) {
            1 -> {
                Toast.makeText(applicationContext, "Working", Toast.LENGTH_LONG).show()
                val intent = Intent(this, AdminDisplaySurveysActivity::class.java).apply {
                    putExtra("id", globalId.toString())
                }
                startActivity(intent)
            }
            -1 -> {
                Toast.makeText(applicationContext, "Error creating new survey", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun btnGoBack(view: View) {
        val intent = Intent(this, AdminDisplaySurveysActivity::class.java).apply {
            putExtra("id", globalId.toString())
        }
        startActivity(intent)
    }
}