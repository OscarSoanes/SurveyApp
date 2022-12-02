package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.surveyapp.adminController.AdminDisplaySurveysActivity
import com.example.surveyapp.model.DataBaseHelper

class LoginActivity : AppCompatActivity() {
    var globalRole = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val role = intent.getStringExtra("role").toString()
        globalRole = role

        val info = findViewById<TextView>(R.id.textDetail)
        info.text = "Log in as $role"
    }

    fun loginButton(view: View) {
        val userName = findViewById<EditText>(R.id.editTextUsername).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword).text.toString()

        if (globalRole == "Teacher") {
            val database = DataBaseHelper(this)
            val id = database.getAdminFromLogin(userName)

            if (id < 0) {
                Toast.makeText(applicationContext, "Username or password is incorrect", Toast.LENGTH_LONG).show()
                return
            }

            val admin = database.getAdmin(id)

            if (admin.password == password) {
                val intent = Intent(this, AdminDisplaySurveysActivity::class.java).apply {
                    putExtra("id", id.toString())
                }
                startActivity(intent)
                return
            }
            Toast.makeText(applicationContext, "Username or password is incorrect", Toast.LENGTH_LONG).show()
            return
        }
        val database = DataBaseHelper(this)
        val id = database.getStudentFromLogin(userName)

        if (id < 0) {
            Toast.makeText(applicationContext, "Username or password is incorrect", Toast.LENGTH_LONG).show()
            return
        }

        val student = database.getStudent(id)

        if (student.password == password) {
            // TODO LOGIN COMPLETE (PASS STUDENT ID PLS)
            return
        }
        Toast.makeText(applicationContext, "Username or password is incorrect", Toast.LENGTH_LONG).show()
    }
}