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
private var numLvls = 0
private var numExercises = 5
private var numMistakes = 0
private var timerInput = 0
private var timerPlus = 0
private var multiplicationStatus = false

//Внутренние данные
private var random1 = 0
private var random2 = 0
private var lvlActual = 1
private var result = 0
private var numExerciseActual = 0
private var numMistakesActual = 0
private var min = 0
private var fullTimeMin = 0
private var fullTimeSec = 0
var lastScore = 0



//Для передачи в statistic
private var fullTime = 0
private var numAllExercises = 0
private var numOfTrue = 0
private var numOfFalse = 0
private var lvlDown = 0

class MainWindow : Fragment() {
    lateinit var binding: MainWindowBinding
    private val openModel: LifeData by activityViewModels()
    private var timer : CountDownTimer? = null
    private var timer2 : CountDownTimer? = null
    private var timer1 : CountDownTimer? = null

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
        timer1 = object : CountDownTimer(59.toLong() * 1000, 1000){
            override fun onTick(p0: Long) {
                if ((59 - (p0 / 1000)).toInt() == 0) {
                    binding.fullTimeTimerMin.text = "$min"
                }
                binding.fullTimeTimer.text = (59 - (p0 / 1000)).toString()
                fullTimeSec = (59 - (p0 / 1000)).toInt()

            }
            override fun onFinish() {
                fullTimeMin += 59
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
            binding.buttonAnswer.setOnClickListener {
                if (binding.answerWindow.text.isNullOrEmpty()) {

                    timer = object  : CountDownTimer(1000, 1000) {
                        override fun onTick(p0: Long) {
                            binding.answerWindow.setBackgroundResource(R.drawable.window_answer_error)
                            binding.info.text = "Введите число!"
                        }
                        override fun onFinish() {
                            binding.answerWindow.setBackgroundResource(R.drawable.window_answer_normal)
                            binding.info.text = null
                        }

                    }.start()
                }else {
                    if (binding.answerWindow.text.toString().toInt() == result) {
                        numOfTrue++
                        binding.trueWindow.text = numOfTrue.toString()
                        restart()
                    } else if (binding.answerWindow.text.toString().toInt() != result) {
                        timer2?.cancel()
                        numOfFalse++
                        numExerciseActual--
                        numMistakesActual++
                        binding.falseWindow.text = numOfFalse.toString()
                        binding.info.text = "Правильный ответ: $result"
                        binding.ok.visibility = View.VISIBLE
                        binding.ok.setOnClickListener {
                            binding.ok.visibility = View.INVISIBLE
                            binding.info.text = null
                            restart()
                        }
                    }
                }
            }

    }

    private fun restart() {
        binding.answerWindow.text = null
        numAllExercises++
        numExerciseActual++
        lvlControl()
        timer2?.cancel()
        timer(timerInput)
        mistakesControl()
        generateExercise()
    }

    //Контроль ошибок
    private fun mistakesControl() {
        if (numMistakesActual > numMistakes) {
            lvlActual--
            lvlDown++
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
            fullTime = fullTimeMin + fullTimeSec
            openModel.allTime.value = fullTime
            openModel.numAllExercises.value = numAllExercises
            openModel.numMistakesStat.value = numOfFalse
            openModel.numTrueResult.value = numOfTrue
            openModel.numLvlDown.value = lvlDown


            random1 = 0
            random2 = 0
            lvlActual = 1
            result = 0
            numExerciseActual = 0
            numMistakesActual = 0
            min = 0
            fullTimeMin = 0
            fullTimeSec = 0
            fullTime = 0
            numAllExercises = 0
            numOfTrue = 0
            numOfFalse = 0
            timer?.cancel()
            timer2?.cancel()
            timer1?.cancel()


            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragmentManager.beginTransaction().replace(R.id.fragment, Statistic()).commit()

        }
        if (level <= numLvls) {
            binding.level.rating = level.toFloat()
        } else {
            fullTime = fullTimeMin + fullTimeSec
            openModel.allTime.value = fullTime
            openModel.numAllExercises.value = numAllExercises
            openModel.numMistakesStat.value = numOfFalse
            openModel.numTrueResult.value = numOfTrue
            openModel.numLvlDown.value = lvlDown

              random1 = 0
              random2 = 0
              lvlActual = 1
              result = 0
              numExerciseActual = 0
              numMistakesActual = 0
              min = 0
              fullTimeMin = 0
              fullTimeSec = 0
              fullTime = 0
              numAllExercises = 0
              numOfTrue = 0
              numOfFalse = 0
            timer?.cancel()
            timer2?.cancel()
            timer1?.cancel()


            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragmentManager.beginTransaction().replace(R.id.fragment, Statistic()).commit()

        }
    }
    //Генератор примера
    private fun generateExercise() {
        binding.apply {
            var num1 = getNumber(lvlActual)
            var num2 = getNumber(lvlActual)
            if (multiplicationStatus) {
                if (lastScore == num1 * num2) {
                    generateExercise()
                } else {
                    if (lvlActual < 4) {
                        result = num1 * num2
                        lastScore = num1 * num2
                        exerciseWindow.text = "$num1 * $num2"

                    } else {
                        var predResultDivide = num1 * num2
                        result = num1
                        lastScore = num1 * num2
                        exerciseWindow.text = "$predResultDivide / $num2"
                    }
                }
            } else {
                if (num1 > num2) {
                    if (lastScore == num1 - num2) {
                        generateExercise()

                    } else {
                        result = num1 - num2
                        lastScore = num1 - num2
                        exerciseWindow.text = "$num1 - $num2"
                    }

                } else {
                    if (lastScore == num1 + num2) {
                        generateExercise()
                    } else {
                        result = num1 + num2
                        lastScore = num1 + num2
                        exerciseWindow.text = "$num1 + $num2"
                    }
                }
            }
        }
    }
    //Получение числа от уровня
    private fun getNumber(lvl: Int): Int {
       var number: Int = 0
        if (multiplicationStatus) {

            when (lvl) {
                1 -> number = rand(1, 5)
                2 -> number = rand(4, 6)
                3 -> number = rand(5, 9)
                4 -> number = rand(1, 9)
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

    override fun onStop() {
        super.onStop()
        timer?.cancel()
        timer2?.cancel()
        timer1?.cancel()
    }
}