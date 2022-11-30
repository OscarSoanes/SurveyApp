package com.example.surveyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.surveyapp.model.Admin
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.Student

class SignupActivity : AppCompatActivity() {
    var globalRole = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val role = intent.getStringExtra("role").toString()
        globalRole = role
    }

    fun signUpButton(view: View) {
        val userName = findViewById<EditText>(R.id.editTextUsername).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword).text.toString()


        // validation
        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext,"Username and password are required!", Toast.LENGTH_LONG).show()
            return
        }

        if (userName.length < 3) {
            Toast.makeText(applicationContext, "Username must be greater than 3 characters", Toast.LENGTH_LONG).show()
            return
        }

        if (password.length < 6 && !password.contains("[0-9]".toRegex())) {
            Toast.makeText(applicationContext, "Password is too easy to guess", Toast.LENGTH_LONG).show()
            return
        }

        // TODO (CREATE VALIDATION RULES)
        if (globalRole == "Admin") {
            val newAdmin = Admin(-1, userName, password)
            val database = DataBaseHelper(this)

            when (database.addAdmin(newAdmin)) {
                1 -> {
                    Toast.makeText(applicationContext, "Working", Toast.LENGTH_LONG).show()
                    // TODO GOTO ADMIN PANEL (PASS ADMIN ID PLEASE)
                }
                -1 -> Toast.makeText(applicationContext, "Error creating new account", Toast.LENGTH_LONG).show()
                -2 -> Toast.makeText(applicationContext, "Cannot open database", Toast.LENGTH_LONG).show()
                -3 -> Toast.makeText(applicationContext, "Username already exists", Toast.LENGTH_LONG).show()
            }
            return
        }
        val newStudent = Student(-1, userName, password)
        val database = DataBaseHelper(this)

        when (database.addStudent(newStudent)) {
            1 -> {
                Toast.makeText(applicationContext, "Working", Toast.LENGTH_LONG).show()
                // TODO GOTO STUDENT PANEL (PASS STUDENT ID PLEASE)
            }
            -1 -> Toast.makeText(applicationContext, "Error creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(applicationContext, "Cannot open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(applicationContext, "Username already exists", Toast.LENGTH_LONG).show()
        }

    }
}