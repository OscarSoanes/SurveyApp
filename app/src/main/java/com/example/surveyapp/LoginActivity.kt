package com.example.surveyapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val role = intent.getStringExtra("role")

        val info = findViewById<TextView>(R.id.textDetail)
        info.text = "Log in as $role"
    }

    fun loginButton(view: View) {
        val role = intent.getStringExtra("role")

        if (role.equals("Student")) {
            // TODO (read from database)
        } else {
            // TODO (read from database)
        }
    }
}