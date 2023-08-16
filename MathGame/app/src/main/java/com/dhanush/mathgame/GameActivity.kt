package com.dhanush.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.dhanush.mathgame.databinding.ActivityGameBinding
import kotlin.random.Random


class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    var correctAns = 0
    var userScore=0
    var userLife = 3
    var pauseGame = false
    lateinit var timer: CountDownTimer
    private val startTimer: Long  = 20000 //60 seconds
    var timeLeft: Long = startTimer
    lateinit var choice: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)
        choice = intent.getStringExtra("choice").toString()
        supportActionBar!!.title = choice.uppercase()

        gameContinue(choice)
        binding.buttonOk.setOnClickListener {
            val input = binding.editTextAnswer.text.toString()
            if(input==""){
                Toast.makeText(applicationContext, "Please write a answer",Toast.LENGTH_LONG).show()
            }
            else{
                val answer = input.toInt()
                if(!pauseGame){
                    if(answer == correctAns){
                        userScore +=10
                        binding.textViewQuestion.text = "Congratulations!! Your answer is correct.."
                        binding.textViewScore.text = userScore.toString()
                    }else{
                        userLife--
                        binding.textViewQuestion.text = ":( Sorry.. Your answer is wrong.."
                        binding.textViewlife.text = userLife.toString()
                    }
                }else{
                    Toast.makeText(applicationContext,"Get a new question first",Toast.LENGTH_LONG).show()
                }
                pauseTimer()
            }

        }
        binding.buttonNext.setOnClickListener {
            pauseTimer()
            resetTimer()
            binding.editTextAnswer.setText("")
            if(userLife == 0){
                Toast.makeText(applicationContext,"Game Over", Toast.LENGTH_LONG).show()
                val intent = Intent(this@GameActivity, ResultActivity::class.java)
                intent.putExtra("score",userScore)
                startActivity(intent)
                finish()
            }
            else{
                gameContinue(choice)
            }
        }
    }
    fun gameContinue(choice: String) {

        var num2 = Random.nextInt(0,100)
        var num1 = Random.nextInt(num2,100)   

        if(choice=="addition"){
            binding.textViewQuestion.text = "$num1 + $num2"
            correctAns = num1+num2
            startTimer()
        }
        else if(choice == "subtraction"){
            binding.textViewQuestion.text = "$num1 - $num2"
            correctAns = num1-num2
            startTimer()
        }
        else if(choice=="multiplication"){
            binding.textViewQuestion.text = "$num1 x $num2"
            correctAns = num1*num2
            startTimer()
        }


    }
    fun startTimer(){
        pauseGame = false
        timer = object: CountDownTimer(timeLeft,1000){
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateTimeText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateTimeText()
                userLife--
                binding.textViewlife.text = userLife.toString()
                binding.textViewQuestion.text = "Sorry, Time Up!"
            }

        }.start()
    }

    private fun resetTimer() {
        timeLeft = startTimer
        updateTimeText()
    }

    private fun pauseTimer() {
        pauseGame = true
        timer.cancel()
    }

    private fun updateTimeText() {
        val remTime: Int = (timeLeft/1000).toInt()
        binding.textViewTime.text = remTime.toString()
    }
}