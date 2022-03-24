package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
    var currentIndex = 0
    var checker = 0
    var isCheater = false
    var cheat = false
    var test: Int = 0
    private val questionblakn = listOf(
        MainActivity.Question(R.string.questionOne, false),
        MainActivity.Question(R.string.questionTwo, false),
        MainActivity.Question(R.string.questionThre, true)
    )
    val currentQustionAnswer: Boolean get() = questionblakn[currentIndex].answer
    val currentQustionText: Int get() = questionblakn[currentIndex].textResId
    fun moveNext () {
        currentIndex = (currentIndex + 1) % questionblakn.size
    }
    fun moveBack() {
        currentIndex = (currentIndex + 1) % questionblakn.size
        currentIndex = (currentIndex + 1) % questionblakn.size
    }
}