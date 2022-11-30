package com.example.surveyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class AdminDisplaySurveyListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_display_survey_list_view)
    }

    fun btnEdit(view: View) {
        val surveyId = findViewById<Button>(R.id.btnEdit).tag

        // TODO (TAG HAS SURVEY ID)
    }

    fun btnData(view: View) {
        val surveyId = findViewById<Button>(R.id.btnData).tag
        // TODO
    }
}