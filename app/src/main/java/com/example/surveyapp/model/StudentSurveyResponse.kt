package com.example.surveyapp.model

data class StudentSurveyResponse(val studentResponseId: Int, val studentId: Int, val surveyId: Int,
                                 val questionId: Int, var answer: Int) {
}