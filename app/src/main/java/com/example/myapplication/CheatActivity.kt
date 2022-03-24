package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val EXTRA_ANSWER_IS_TRUE = "qqq"
private const val LOG_TEST = "testik"
class CheatActivity : AppCompatActivity() {
    private var answerIsTrue = false
    private lateinit var showAnswerButton: Button
    private lateinit var answerTextView: TextView
    var test = 1
    private val quizViewModel:CheatViewModel by lazy {
        ViewModelProviders.of(this).get(CheatViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TEST, "${quizViewModel.test} ayayayyayaya")
        setAnswerShownResult(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        showAnswerButton = findViewById(R.id.show_answer_text)
        answerTextView = findViewById(R.id.anwer_text_view)
        answerIsTrue = intent.getBooleanExtra("keyOne", false)

        showAnswerButton.setOnClickListener {
            val anwerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(anwerText)
            setAnswerShownResult(true)
            quizViewModel.test = 1
            test += 1

        }
    }
    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra("keyOne", answerIsTrue)


            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TEST, "${test}")
        Log.d(LOG_TEST, "${quizViewModel.test}")
    }
    private fun setAnswerShownResult(isAnswerShown:Boolean) {
        if (quizViewModel.test == 1)  {setResult(RESULT_OK, Intent().apply { putExtra("keyOne", false) })
        }

        val data: Intent = if (quizViewModel.test == 1) {
            Intent().apply {
                putExtra("keyOne", true)
            }
        } else {
            Intent().apply {
                putExtra("keyOne", isAnswerShown)
            }
        }
        setResult(
            RESULT_OK, data)
    }
    override fun onPause() {
        super.onPause()

    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("KEY", test)
    }
    override fun onStop() {
        super.onStop()
    }

}