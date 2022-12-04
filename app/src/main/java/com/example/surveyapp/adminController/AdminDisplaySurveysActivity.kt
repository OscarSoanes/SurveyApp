package com.example.surveyapp.adminController

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.example.surveyapp.MainActivity
import com.example.surveyapp.R
import com.example.surveyapp.model.Admin
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt

class AdminDisplaySurveysActivity : AppCompatActivity() {
    private var globalId = 0
    private var surveyList = ArrayList<Survey>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_display_surveys)

        val adminId = parseInt(intent.getStringExtra("id").toString())
        globalId = adminId

        val database = DataBaseHelper(this)
        surveyList.addAll(database.getAllSurveysByAdminId(globalId))

        val displayList = findViewById<ListView>(R.id.surveyListView)
        val customAdapterAdminSurvey = CustomAdapterAdminSurvey(applicationContext, surveyList, this)

        displayList.adapter = customAdapterAdminSurvey
    }

    fun newSurvey(view: View) {
        val intent = Intent(this, NewSurveyActivity::class.java).apply {
            putExtra("id", globalId.toString())
        }
        startActivity(intent)
    }

    fun logOff(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun btnEdit(view: View, surveyId: Int) {
        val intent = Intent(this, EditSurveyActivity::class.java).apply {
            putExtra("id", globalId.toString())
            putExtra("survey", surveyId.toString())
        }
        startActivity(intent)
    }

    fun btnData(view: View, survey: Survey) {
        val intent = Intent(this, AdminTableData::class.java).apply {
            putExtra("id", globalId.toString())
            putExtra("survey", survey)
        }
        startActivity(intent)
    }
}
