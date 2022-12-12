package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.surveyapp.adminController.AdminDisplaySurveysActivity
import com.example.surveyapp.model.Admin
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.Student
import com.example.surveyapp.studentController.StudentPanelActivity

class SignupActivity : AppCompatActivity() {
    var globalRole = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val role = intent.getStringExtra("role").toString()
        globalRole = role

        val info = findViewById<TextView>(R.id.textDetailSignUp)
        info.text = "Sign up as $role"
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
            Toast.makeText(applicationContext, "Password must be greater than 6 characters and include a number", Toast.LENGTH_LONG).show()
            return
        }

        if (globalRole == "Teacher") {
            val newAdmin = Admin(-1, userName, password)
            val database = DataBaseHelper(this)
            when (val id = database.addAdmin(newAdmin).toInt()) {
                in 1 .. Int.MAX_VALUE -> {
                    val intent = Intent(this, AdminDisplaySurveysActivity::class.java).apply {
                        putExtra("id", id.toString())
                    }
                    finish()
                    startActivity(intent)
                }
                -1 -> Toast.makeText(applicationContext, "Error creating new account", Toast.LENGTH_LONG).show()
                -2 -> Toast.makeText(applicationContext, "Cannot open database", Toast.LENGTH_LONG).show()
                -3 -> Toast.makeText(applicationContext, "Username already exists", Toast.LENGTH_LONG).show()
            }
            return
        }
        val newStudent = Student(-1, userName, password)
        val database = DataBaseHelper(this)


        when (val id = database.addStudent(newStudent).toInt()) {
            in 1 .. Int.MAX_VALUE -> {
                val intent = Intent(this, StudentPanelActivity::class.java).apply {
                    putExtra("id", id.toString())
                }
                finish()
                startActivity(intent)
            }
            -1 -> Toast.makeText(applicationContext, "Error creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(applicationContext, "Cannot open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(applicationContext, "Username already exists", Toast.LENGTH_LONG).show()
        }
    }

    fun btnBack(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}