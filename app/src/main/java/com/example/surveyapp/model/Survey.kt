package com.example.surveyapp.model

data class Survey(val surveyId: Int, val adminId: Int, var module: String,
var startDate: String, var endDate: String) {
}