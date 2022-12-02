package com.example.surveyapp.model

import java.io.Serializable

data class Question(val questionId: Int, var surveyId: Int, var questionText: String):
    Serializable {
}