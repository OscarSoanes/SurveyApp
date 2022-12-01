package com.example.surveyapp.model

import java.io.Serializable

class QuestionList (): Serializable {
    private var questionList: ArrayList<Question> = ArrayList()
    private var count: Int = 0

    fun addQuestion(question: Question) {
        questionList.add(question)
        count++
    }

    fun updateQuestionAtIndex(id: Int, question: Question) {
        questionList[id] = question
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