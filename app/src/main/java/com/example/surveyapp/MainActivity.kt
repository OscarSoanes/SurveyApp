package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun loginStudent(view: View) {
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra("role", "Student")
        }
        startActivity(intent)
    }

    fun signupStudent(view: View) {
        val intent = Intent(this, SignupActivity::class.java).apply {
            putExtra("role", "Student")
        }
        startActivity(intent)
    }

    fun loginAdmin(view: View) {
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra("role", "Teacher")
        }
        startActivity(intent)
    }

    fun signupAdmin(view: View) {
        val intent = Intent(this, SignupActivity::class.java).apply {
            putExtra("role", "Teacher")
        }
        startActivity(intent)
    }
}