package com.example.surveyapp.model

import java.io.Serializable

class AnswerList(): Serializable {
    private var answerList: ArrayList<Int> = ArrayList()
    private var count: Int = 0

    fun addAnswer(answer: Int) {
        answerList.add(answer)
    }

    fun updateAnswerAtIndex(id: Int, answer: Int) {
        answerList[id] = answer
    }

    fun getCount(): Int = count

    fun getAnswers(): ArrayList<Int> = answerList

    fun getAnswer(index: Int) : Int {
        return answerList.get(index)
    }

    fun clear() {
        answerList.clear()
        count = 0
    }
}