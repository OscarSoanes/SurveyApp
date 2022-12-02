package com.example.surveyapp.adminController

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.surveyapp.R
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt

class EditSurveyActivity : AppCompatActivity() {
    var adminId = 0
    var survey = Survey(0, 0, "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_survey)
        val id1 = parseInt(intent.getStringExtra("id").toString())
        val id2 = parseInt(intent.getStringExtra("survey").toString())
        adminId = id1


        // get Survey
        val database = DataBaseHelper(this)
        val data: Survey = database.getSurvey(id2)
        survey = data


        // Update current data to textfield
        val moduleText = findViewById<EditText>(R.id.editTextTitle)
        val startText = findViewById<EditText>(R.id.editTextStartDate)
        val endText = findViewById<EditText>(R.id.editTextEndDate)
        moduleText.setText(survey.module)
        startText.setText(survey.startDate)
        endText.setText(survey.endDate)
    }

    fun btnSave(view: View) {
        val moduleText = findViewById<EditText>(R.id.editTextTitle).text.toString()
        val startText = findViewById<EditText>(R.id.editTextStartDate).text.toString()
        val endText = findViewById<EditText>(R.id.editTextEndDate).text.toString()

        survey.module = moduleText
        survey.startDate = startText
        survey.endDate = endText

        val database = DataBaseHelper(this)
        database.updateSurvey(survey)

        val intent = Intent(this, AdminDisplaySurveysActivity::class.java).apply {
            putExtra("id", adminId.toString())
        }
        startActivity(intent)
    }

    fun btnReturn(view: View) {
        val intent = Intent(this, AdminDisplaySurveysActivity::class.java).apply {
            putExtra("id", adminId.toString())
        }
        startActivity(intent)
    }

    fun btnDelete(view: View) {
        val intent = Intent(this, ConfirmDeleteActivity::class.java).apply {
            putExtra("id", adminId.toString())
            putExtra("survey", survey)
        }
        startActivity(intent)
    }
}