package com.example.lerningcount

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.scorestudy.LifeData
import com.example.scorestudy.R
import com.example.scorestudy.databinding.MainWindowBinding
import java.util.Random

//Полученные данные
var numLvls = 0
var numExercises = 5
var numMistakes = 0
var timerInput = 0
var timerPlus = 0
var multiplicationStatus = false

//Внутренние данные
var random1 = 0
var random2 = 0
var lvlActual = 1
var result = 0
var numOfTrue = 0
var numOfFalse = 0
var numExerciseActual = 0
var numMistakesActual = 0
var min = 0


//Для передачи в statistic
var numAllExercises = 0

class MainWindow : Fragment() {
    lateinit var binding: MainWindowBinding
    private val openModel: LifeData by activityViewModels()
    private var timer2 : CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainWindowBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return (binding.root)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Получение данных lifedata
        //LifeData
        openModel.numLevel.observe(activity as LifecycleOwner) {
            numLvls = it
        }
        openModel.numExercise.observe(activity as LifecycleOwner) {
            numExercises = it
        }
        openModel.numMistakes.observe(activity as LifecycleOwner) {
            numMistakes = it
        }
        openModel.timer.observe(activity as LifecycleOwner) {
            timerInput = it
        }
        openModel.timerPlus.observe(activity as LifecycleOwner) {
            timerPlus = it
        }
        openModel.multiplicationStatus.observe(activity as LifecycleOwner) {
            multiplicationStatus = it
        }
        generateExercise()
        answer()
        fullTimer()
        timer(timerInput)
    }
    //Таймер общего времени
    private fun fullTimer() {
        var timer1 = object : CountDownTimer(59.toLong() * 1000, 1000){
            override fun onTick(p0: Long) {
                if ((59 - (p0 / 1000)).toInt() == 0) {
                    binding.fullTimeTimerMin.text = "$min:"
                }
                binding.fullTimeTimer.text = (59 - (p0 / 1000)).toString()
            }
            override fun onFinish() {
                min++
                fullTimer()
            }
        }.start()
    }
    //Таймер
    private fun timer(time: Int) {
        if (timerInput != 0){
            binding.timer.visibility = View.VISIBLE
            timer2 = object : CountDownTimer(time.toLong() * 1000, 1000){
                override fun onTick(p0: Long) {
                    binding.timer.text = (p0/1000).toString()
                }
                override fun onFinish() {
                    numExerciseActual--
                    numOfFalse++
                    numMistakesActual++
                    binding.falseWindow.text = numOfFalse.toString()
                    numAllExercises++
                    numExerciseActual++
                    lvlControl()
                    timer(timerInput)
                    mistakesControl()
                    generateExercise()
                }
            }.start()
        }
    }
    //Нажатие на ответ
    private fun answer(){
        binding.apply {
            buttonAnswer.setOnClickListener {
                if (answerWindow.text.isNullOrEmpty()) {
                    info.text = "Введите число!"
                }else {
                    if (answerWindow.text.toString().toInt() == result) {
                        numOfTrue++
                        trueWindow.text = numOfTrue.toString()
                    } else if (answerWindow.text.toString().toInt() != result) {
                        numOfFalse++
                        numExerciseActual--
                        numMistakesActual++
                        falseWindow.text = numOfFalse.toString()
                    }
                    answerWindow.text = null
                    numAllExercises++
                    numExerciseActual++
                    lvlControl()
                    timer2?.cancel()
                    timer(timerInput)
                    mistakesControl()
                    generateExercise()

                }
            }
        }
    }
    //Контроль ошибок
    private fun mistakesControl() {
        if (numMistakesActual > numMistakes) {
            lvlActual--
            numMistakesActual = 0
            raitingControl(lvlActual)
        }
    }
    //Контроль уровня
    private fun lvlControl(){
        if (numExerciseActual == numExercises) {
            lvlActual++
            if (timerInput != 0) {
                timerInput+=timerPlus
            }
            numMistakesActual = 0
            raitingControl(lvlActual)
            numExerciseActual = 0
        }
    }
    //Контроль рейтинга
    private fun raitingControl(level: Int) {

        if (level == 0 ) {
            parentFragmentManager.beginTransaction().replace(R.id.fragment, Statistic()).commit()
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
        if (level <= numLvls) {
            binding.level.rating = level.toFloat()
        } else {
            parentFragmentManager.beginTransaction().replace(R.id.fragment, Statistic()).commit()
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }
    //Генератор примера
    private fun generateExercise() {
        binding.apply {
            if (multiplicationStatus) {
                var num1 = getNumber(lvlActual)
                var num2 = getNumber(lvlActual)
                result = num1 * num2
                exerciseWindow.text = "$num1 * $num2"
            } else {
                var num1 = getNumber(lvlActual)
                var num2 = getNumber(lvlActual)
                result = num1 + num2
                exerciseWindow.text = "$num1 + $num2"
            }
        }
    }
    //Получение числа от уровня
    private fun getNumber(lvl: Int): Int {
       var number: Int = 0
        if (multiplicationStatus) {

            when (lvl) {
                1 -> number = rand(1, 4)
                2 -> number = rand(3, 6)
                3 -> number = rand(5, 9)
                4 -> number = rand(8, 10)
            }

        } else {
            when (lvl) {
                1 -> number = rand(1, 9)
                2 -> number = rand(10, 19)
                3 -> number = rand(20, 50)
                4 -> number = rand(50, 99)
            }
        }
        return number
    }
    //Генератор случайных чисел
    private fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (start..end).shuffled().first()
    }
}