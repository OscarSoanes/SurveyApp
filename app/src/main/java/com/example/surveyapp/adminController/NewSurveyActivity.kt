package com.example.surveyapp.adminController

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.surveyapp.R
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class NewSurveyActivity : AppCompatActivity() {
    private var globalId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_survey)

        val id = parseInt(intent.getStringExtra("id").toString())
        globalId = id
    }

    fun btnSave(view: View) {
        fun validation(day: Int, month: Int, year: Int, position: String, currentYear: Int): String {
            var message = ""
            // Checking year
            if (year >= currentYear+5) {
                message = "$position year cannot be set greater than 5 years from now"
            }
            // Checking months
            if (month !in 1 .. 12) {
                message = "$position month is invalid"
            }

            // Checking days
            if (month % 2 == 1) { // JAN, MAR, MAY, JUL, SEP, NOV
                if (day !in 1 .. 31) {
                    message = "$position day is invalid"
                }
            } else if (month % 2 == 0 && month != 2) { // APR, JUN, AUG, OCT, DEC
                if (day !in 1 .. 30) {
                    message = "$position day is invalid"
                }
            } else { // FEB
                if (year % 4 != 0) { // NOT LEAP YEAR
                    if (day !in 1 .. 28) {
                        message = "$position day is invalid"
                    }
                } else { // LEAP YEAR
                   if (day !in 1 .. 29) {
                       message = "$position day is invalid"
                   }
                }
            }
            return message
        }
        fun beforeCheck(dateBefore: Date, date: Date, output: String) : String {
            var message = ""
            if (dateBefore.before(date)) {
                message += output
            }
            return message
        }

        // Collecting data from activity
        val title = findViewById<EditText>(R.id.editTextTitle).text.toString()
        val startDay = findViewById<EditText>(R.id.editStartDay).text.toString()
        val startMonth = findViewById<EditText>(R.id.editStartMonth).text.toString()
        val startYear = findViewById<EditText>(R.id.editStartYear).text.toString()
        val endDay = findViewById<EditText>(R.id.editEndDay).text.toString()
        val endMonth = findViewById<EditText>(R.id.editEndMonth).text.toString()
        val endYear = findViewById<EditText>(R.id.editEndYear).text.toString()

        // Check for anything empty
        if (title.isEmpty()) {
            Toast.makeText(applicationContext, "The module cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        if (startDay.isEmpty() || startMonth.isEmpty() || startYear.isEmpty()) {
            Toast.makeText(applicationContext, "The start date cannot be empty", Toast.LENGTH_LONG).show()
            return
        }
        if (endDay.isEmpty() || endMonth.isEmpty() || endYear.isEmpty()) {
            Toast.makeText(applicationContext, "The end date cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        // Validation on each individual number
        var currentYear = Calendar.getInstance().get(Calendar.YEAR)
        var message = validation(parseInt(startDay), parseInt(startMonth), parseInt(startYear), "Start", currentYear)
        if (message.isNotEmpty()) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            return
        }

        message = validation(parseInt(endDay), parseInt(endMonth), parseInt(endYear), "End", currentYear)
        if (message.isNotEmpty()) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            return
        }

        // Getting data to Date, to compare if before dates
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.UK)

        val sd = sdf.parse("$startDay/$startMonth/$startYear")
        val ed = sdf.parse("$endDay/$endMonth/$endYear")
        val current = sdf.parse(sdf.format(Date()))

        // validation on before dates
        message = beforeCheck(sd, current, "The start date cannot be in past")
        if (message.isNotEmpty()) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            return
        }
        message = beforeCheck(ed, current, "The end date cannot be in past")
        if (message.isNotEmpty()) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            return
        }
        message = beforeCheck(ed, sd, "The start date cannot be after date")
        if (message.isNotEmpty()) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            return
        }

        // Output to Survey, start CreateQuestionActivity
        val startDate = "$startDay/$startMonth/$startYear"
        val endDate = "$endDay/$endMonth/$endYear"

        val newSurvey = Survey(-1, globalId, title, startDate, endDate)

        val intent = Intent(this, CreateQuestionActivity::class.java).apply {
            putExtra("id", globalId.toString())
            putExtra("survey", newSurvey)
            putExtra("index", "0")
        }
        startActivity(intent)
    }

    fun btnGoBack(view: View) {
        val intent = Intent(this, AdminDisplaySurveysActivity::class.java).apply {
            putExtra("id", globalId.toString())
        }
        startActivity(intent)
    }

    fun btnNow(view: View) {
        val startDay = findViewById<EditText>(R.id.editStartDay)
        val startMonth = findViewById<EditText>(R.id.editStartMonth)
        val startYear = findViewById<EditText>(R.id.editStartYear)

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
        val current = LocalDateTime.now()

        startDay.setText(current.dayOfMonth.toString())
        startMonth.setText(current.monthValue.toString())
        startYear.setText(current.year.toString())
    }
}