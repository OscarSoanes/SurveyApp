package com.example.surveyapp.model

import java.io.Serializable
import kotlin.math.floor

class DataList(): Serializable {
    private var dataList = IntArray(5) { 0 }
    private var count = 0

    fun getStrongAgree() : Int {
        return dataList[0]
    }
    fun getAgree() : Int {
        return dataList[1]
    }
    fun getNeutral() : Int {
        return dataList[2]
    }
    fun getDisagree() : Int {
        return dataList[3]
    }
    fun getStrongDisagree() : Int {
        return dataList[4]
    }

    fun getStrongAgreeAsFloat() : Float {
        return dataList[0].toFloat()
    }
    fun getAgreeAsFloat() : Float {
        return dataList[1].toFloat()
    }
    fun getNeutralAsFloat() : Float {
        return dataList[2].toFloat()
    }
    fun getDisagreeAsFloat() : Float {
        return dataList[3].toFloat()
    }
    fun getStrongDisagreeAsFloat() : Float {
        return dataList[4].toFloat()
    }

    fun getStrongAgreePercent(): Double {
        var number: Double
        try {
            number = getStrongAgree() / getCount().toDouble()
            number = floor(number * 10000) / 100
        } catch (e: ArithmeticException) {
            return 0.0
        }

        return number
    }

    fun getAgreePercent(): Double {
        var number: Double
        try {
            number = getAgree() / getCount().toDouble()
            number = floor(number * 10000) / 100
        } catch (e: ArithmeticException) {
            return 0.0
        }
        return number
    }

    fun getNeutralPercent(): Double {
        var number: Double
        try {
            number = getNeutral() / getCount().toDouble()
            number = floor(number * 10000) / 100
        } catch (e: ArithmeticException) {
            return 0.0
        }
        return number
    }

    fun getDisagreePercent(): Double {
        var number: Double
        try {
            number = getDisagree() / getCount().toDouble()
            number = floor(number * 10000) / 100
        } catch (e: ArithmeticException) {
            return 0.0
        }
        return number
    }

    fun getStrongDisagreePercent(): Double {
        var number: Double
        try {
            number = getStrongDisagree() / getCount().toDouble()
            number = floor(number * 10000) / 100
        } catch (e: ArithmeticException) {
            return 0.0
        }
        return number
    }

    fun addOneStrongAgree() {
        dataList[0] = dataList[0] + 1
        count++
    }
    fun addOneAgree() {
        dataList[1] = dataList[1] + 1
        count++
    }
    fun addOneNeutral() {
        dataList[2] = dataList[2] + 1
        count++
    }
    fun addOneDisagree() {
        dataList[3] = dataList[3] + 1
        count++
    }
    fun addOneStrongDisagree() {
        dataList[4] = dataList[4] + 1
        count++
    }

    fun getCount() : Int {
        return count
    }

    fun clear() {
        count = 0
        dataList = IntArray(5) {0}
    }

    fun getAverage() : String {
        val sA = getStrongAgree()
        val a = getAgree()
        val n = getNeutral()
        val d = getDisagree()
        val sD = getStrongDisagree()
        val count = getCount()

        val total = (sA * 5) + (a * 4) + (n * 3) + (d * 2) + (sD * 1)
        val calculation: Double = total / count.toDouble()
        val convert: Int = calculation.toInt() * 100
        when (convert) {
            in 1 .. 180 -> {
                return "Strongly Disagree"
            }
            in 181 .. 260 -> {
                return "Disagree"
            }
            in 261 .. 340 -> {
                return "Neutral"
            }
            in 341 .. 420 -> {
                return "Agree"
            }
            in 421 .. 500 -> {
                return "Strongly Agree"
            }
            else -> {
                return "Error"
            }

        }
    }

    fun getAverageFloat(): Float {
        val sA = getStrongAgree()
        val a = getAgree()
        val n = getNeutral()
        val d = getDisagree()
        val sD = getStrongDisagree()
        val count = getCount()

        val total = (sA * 5) + (a * 4) + (n * 3) + (d * 2) + (sD * 1)
        val calculation: Double = total / count.toDouble()

        return calculation.toFloat()
    }
}