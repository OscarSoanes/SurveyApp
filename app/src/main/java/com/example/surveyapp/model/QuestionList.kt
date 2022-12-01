package com.example.surveyapp.model

import java.io.Serializable

class QuestionList (): Serializable {
    private var questionList: ArrayList<Question> = ArrayList()
    private var count: Int = 0

    fun addQuestion(question: Question) {
        questionList.add(question)
        count++
    }

    fun getCount(): Int = count

    fun getQuestion(index: Int) : Question {
        return questionList.get(index)
    }

    fun clear() {
        questionList.clear()
        count = 0
    }
}