package com.example.surveyapp.model

data class StudentSurveyResponce(val studentResponseId: Int, val studentId: Int, val surveyId: Int,
val questionId: Int, var answer: Int) {
}