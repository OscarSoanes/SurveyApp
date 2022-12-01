package com.example.surveyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class AdminDisplaySurveyListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_display_survey_list_view)

        val button = findViewById<Button>(R.id.btnEdit)
        button.setOnClickListener {
            Toast.makeText(this,"Working",Toast.LENGTH_LONG).show()
        }
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