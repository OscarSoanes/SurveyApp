package com.example.surveyapp.model

import java.io.Serializable

class AnswerList(): Serializable {
    private var answerList: ArrayList<StudentSurveyResponse> = ArrayList()
    private var count: Int = 0

    fun addAnswer(answer: StudentSurveyResponse) {
        answerList.add(answer)
        count++
    }

    fun addAnswers(answers: ArrayList<StudentSurveyResponse>) {
        answerList.addAll(answers)
        count += answers.size
    }

    fun updateAnswerAtIndex(id: Int, answer: StudentSurveyResponse) {
        answerList[id] = answer
    }

    fun getCount(): Int = count

    fun getAnswer(index: Int) : StudentSurveyResponse {
        return answerList.get(index)
    }

    fun getAnswers(): ArrayList<StudentSurveyResponse> = answerList

    fun clear() {
        answerList.clear()
        count = 0
    }
}