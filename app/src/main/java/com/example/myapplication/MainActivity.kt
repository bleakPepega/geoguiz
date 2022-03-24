package com.example.myapplication

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.util.EnumSet.of
private const val KEY_INDEX = "index"
private const val REQUEST_CODE = 0

class MainActivity : AppCompatActivity() {
    private lateinit var false_button: Button //заглушки
    private lateinit var text_one: TextView
    private var checker_procent_correct_answer = 0
    private lateinit var next_button: Button
    private lateinit var true_button: Button
    private lateinit var textOnQuest: TextView
    private lateinit var button_back: Button
    private lateinit var text_procent: TextView
    private lateinit var cheatButton: Button

    private val quizViewModel:QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        text_one = findViewById(R.id.textOne)
        textOnQuest = findViewById(R.id.answerOnQuestion)
        next_button = findViewById(R.id.next_button)
        false_button = findViewById(R.id.false_button)
        true_button = findViewById(R.id.true_button)
        button_back = findViewById(R.id.button_back)
        cheatButton = findViewById(R.id.cheat_button)
                                                            // находим ip
        text_one.setText(quizViewModel.currentQustionText)
                                                            // слшушатели
        true_button.setOnClickListener {
            if (checker()) {
                quizViewModel
                if (quizViewModel.currentQustionAnswer) textOnQuest.setText(R.string.true_button)
                else textOnQuest.setText(R.string.false_button)
                quizViewModel.checker +=1
            }
            checkAnswer(true)
        }

        false_button.setOnClickListener {
            if (checker()) {
                quizViewModel.checker += 1
            if (!quizViewModel.currentQustionAnswer) {
                    textOnQuest.setText(R.string.true_button)
                    checker_procent_correct_answer += 1
                }
                else textOnQuest.setText(R.string.false_button)
            }
            checkAnswer(false)
        }

        next_button.setOnClickListener {
            quizViewModel.moveNext()
            update()
        }

        button_back.setOnClickListener {
            quizViewModel.moveBack()
            update()
        }
        cheatButton.setOnClickListener { view ->
            val answerIsTrue = quizViewModel.currentQustionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            val options = ActivityOptions.makeClipRevealAnimation(view, 0, 0, view.width, view.height)
            startActivityForResult(intent, REQUEST_CODE, options.toBundle())

        }

    }
    private fun update() {
        quizViewModel.currentQustionText
        text_one.setText(quizViewModel.currentQustionText)
        quizViewModel.checker = 0
        textOnQuest.text = " "
        quizViewModel.test += 1
        quizViewModel.isCheater = false
    }

    private fun checker(): Boolean {
        return quizViewModel.checker != 1
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (quizViewModel.isCheater)   Log.d("test", "isCheatr == true")


        else {
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == REQUEST_CODE) {
            quizViewModel.isCheater = data?.getBooleanExtra("keyOne", false) ?: false
        }
            quizViewModel.isCheater = true
        }
    }
    private fun checkAnswer(userAnser: Boolean) {
        val correctAnswer: Boolean = quizViewModel.currentQustionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judment_toast
            userAnser == correctAnswer -> R.string.correct_tjast
            else -> R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }



    data class Question(@StringRes val textResId: Int, val answer: Boolean)


}