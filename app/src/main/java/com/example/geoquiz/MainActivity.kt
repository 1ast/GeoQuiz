package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private var trueAnswers = 0

    private val questions = listOf<Question>(Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val quizViewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            if (!questions[currentIndex].answered) {
                checkAnswer(true)
                questions[currentIndex].answered = true
            }
        }
        falseButton.setOnClickListener {
            if (!questions[currentIndex].answered) {
                checkAnswer(false)
                questions[currentIndex].answered = true
            }
        }
        nextButton.setOnClickListener {
            if (currentIndex == questions.size-1){
                Toast.makeText(this, "You correctly answered $trueAnswers/${questions.size}", Toast.LENGTH_SHORT).show()
            }
            currentIndex = (currentIndex + 1) % questions.size
            updateQuestion()
        }
        prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1 + questions.size) % questions.size
            updateQuestion()
        }
        val questionTextResId = questions[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun updateQuestion(){
        val question = questions[currentIndex].textResId
        questionTextView.setText(question)
        if (currentIndex == 0){
            trueAnswers = 0
            for(i in questions){
                i.answered = false
            }
        }

    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questions[currentIndex].answer
        val message = if (correctAnswer == userAnswer){
            trueAnswers++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}