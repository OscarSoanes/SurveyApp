package com.example.surveyapp.adminController

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.surveyapp.R
import com.example.surveyapp.model.DataBaseHelper
import com.example.surveyapp.model.Survey
import java.lang.Integer.parseInt
import java.util.*

class EditSurveyActivity : AppCompatActivity() {
    var adminId = 0
    var survey = Survey(0, 0, "", "", "")
    var surveyStarted = false

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


        val moduleText = findViewById<EditText>(R.id.editTextTitle)
        val startDay = findViewById<EditText>(R.id.editStartDay)
        val startMonth = findViewById<EditText>(R.id.editStartMonth)
        val startYear = findViewById<EditText>(R.id.editStartYear)
        val endDay = findViewById<EditText>(R.id.editEndDay)
        val endMonth = findViewById<EditText>(R.id.editEndMonth)
        val endYear = findViewById<EditText>(R.id.editEndYear)

        // Locks start date if survey started
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
        val surveyStartDate = sdf.parse(survey.startDate)
        val current = sdf.parse(sdf.format(Date()))

        if (current.after(surveyStartDate)) {
            surveyStarted = true
            moduleText.isFocusable = false
            moduleText.isClickable = false
            startDay.isFocusable = false
            startDay.isClickable = false
            startMonth.isFocusable = false
            startMonth.isClickable = false
            startYear.isFocusable = false
            startYear.isClickable = false
        }

        // Putting survey data to view
        var startDateToArray = survey.startDate.split("/").toTypedArray()
        var endDateToArray = survey.endDate.split("/").toTypedArray()
        moduleText.setText(survey.module)

        startDay.setText(startDateToArray[0])
        startMonth.setText(startDateToArray[1])
        startYear.setText(startDateToArray[2])

        endDay.setText(endDateToArray[0])
        endMonth.setText(endDateToArray[1])
        endYear.setText(endDateToArray[2])
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

        val moduleText = findViewById<EditText>(R.id.editTextTitle).text.toString()
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
        var message = ""
        if (!surveyStarted) {
            message = validation(parseInt(startDay), parseInt(startMonth), parseInt(startYear), "Start", currentYear)
            if (message.isNotEmpty()) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                return
            }
        }

        message = validation(parseInt(endDay), parseInt(endMonth), parseInt(endYear), "End", currentYear)
        if (message.isNotEmpty()) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            return
        }

        // Getting data to Date, to compare if before dates
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.UK)

        val sd = sdf.parse("$startDay/$startMonth/$startYear")
        val ed = sdf.parse("$endDay/$endMonth/$endYear")
        val current = sdf.parse(sdf.format(Date()))

        // validation on before dates
        if (!surveyStarted) {
            message = beforeCheck(sd, current, "The start date cannot be in past")
            if (message.isNotEmpty()) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                return
            }
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

        survey.module = moduleText
        survey.startDate = "$startDay/$startMonth/$startYear"
        survey.endDate = "$endDay/$endMonth/$endYear"

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

    fun checkStartDate(view: View) {
        if (surveyStarted) {
            Toast.makeText(applicationContext, "Start date cannot be changed once survey has started", Toast.LENGTH_LONG).show()
        }
    }

    fun checkModule(view: View) {
        if (surveyStarted) {
            Toast.makeText(applicationContext, "Module cannot be changed once survey has started", Toast.LENGTH_LONG).show()
        }
    }
}