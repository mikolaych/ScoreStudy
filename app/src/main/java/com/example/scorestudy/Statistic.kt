package com.example.lerningcount

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.scorestudy.LifeData
import com.example.scorestudy.R
import com.example.scorestudy.databinding.StatisticBinding

//Полученные данные
private var allTime = 0
private var numAllExercises = 0
private var numMistakes = 0
private var numTrueEx = 0
private var numLvlDown = 0

private var preGrade = 0
class Statistic : Fragment() {
    lateinit var binding: StatisticBinding
    private val openModel: LifeData by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatisticBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return (binding.root)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Получение данных lifedata
        //LifeData
        openModel.allTime.observe(activity as LifecycleOwner) {
            allTime = it
        }
        openModel.numAllExercises.observe(activity as LifecycleOwner) {
            numAllExercises = it
        }
        openModel.numMistakesStat.observe(activity as LifecycleOwner) {
            numMistakes = it
        }
        openModel.numTrueResult.observe(activity as LifecycleOwner) {
            numTrueEx = it
        }
        openModel.numLvlDown.observe(activity as LifecycleOwner) {
            numLvlDown = it
        }

        //запись данных
        binding.apply {
            timeOfExercises.text = "${allTime/60} мин ${allTime%60} сек"
            numOfExercisesWindowStat.text = numAllExercises.toString()
            windowTrueExercises.text = numTrueEx.toString()
            windowFalseExercises.text = numMistakes.toString()
        }


        //Оценка
        when (numTrueEx * 100 / numAllExercises) {
           in 0..50 -> {
               preGrade = 2
           }
            in 51..70 -> {
                preGrade = 3
            }
            in 71..80 -> {
                preGrade = 4
            }
            in 81..100 -> {
                preGrade = 5
            }
        }


        when(numLvlDown) {
            0 -> {
                binding.grade.text = preGrade.toString()
            }
            1 -> {
                binding.grade.text = (preGrade - 1).toString()
            }
            2 -> {
                binding.grade.text = (preGrade - 2).toString()
            }
            3 -> {
                binding.grade.text = "2"
            }
            4 -> {
                binding.grade.text = "1"
            }
        }

        when (binding.grade.text) {
            "5" ->  {
                binding.grade.setBackgroundResource(R.drawable.grade_five)
                binding.grade.setTextColor(R.drawable.grade_two)
            }
            "4" -> binding.grade.setBackgroundResource(R.drawable.grade_four)
            "3" -> binding.grade.setBackgroundResource(R.drawable.grade_three)
            else -> binding.grade.setBackgroundResource(R.drawable.grade_two)
        }

        var randomNum = rand(1, 15)

        when (randomNum) {
            1 -> binding.textView.text = "Чему бы ты ни учился, ты учишься для себя."
            2 -> binding.textView.text = "Ученик, который учится без желания, — это птица без крыльев."
            3 -> binding.textView.text = "Век живи — век учись!"
            4 -> binding.textView.text = "Безграмотность доверчива и легкомысленна."
            5 -> binding.textView.text = "В конце концов имеет значение только то, чему ты научился и что по-настоящему усвоил. "
            6 -> binding.textView.text = "Учеба – это не время. Учеба – это усилия."
            7 -> binding.textView.text = "Важно не количество знаний, а качество их."
            8 -> binding.textView.text = "В учении нельзя останавливаться."
            9 -> binding.textView.text = "Ни искусство, ни мудрость не могут быть достигнуты, если им не учиться."
            10 -> binding.textView.text = "Кто ни о чем не спрашивает, тот ничему не научится."
            11 -> binding.textView.text = "Надо много учиться, чтобы знать хоть немного."
            12 -> binding.textView.text = "Чем больше у меня дела, тем больше я учусь."
            13 -> binding.textView.text = "Тот, кто не желает учиться, — никогда не станет настоящим человеком."
            14 -> binding.textView.text = "Ученье свет, а неученье — тьма."
            15 -> binding.textView.text = "Ученость — это сладкий плод горького корня."
        }

        //Кнопка заново
        again()
        //кнопка закрыть
        closeApp()



    }

    private fun closeApp() {
        binding.btnClose.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this@Statistic).commit()
            activity?.finish()
        }

    }

    private fun again() {
        binding.apply {
            btnAgain.setOnClickListener {
                openModel.allTime.value = 0
                openModel.numAllExercises.value = 0
                openModel.numMistakesStat.value = 0
                openModel.numTrueResult.value = 0
                openModel.numLvlDown.value = 0
                openModel.multiplicationStatus.value = false
                openModel.numLevel.value = 0
                openModel.timer.value = 0
                openModel.timerPlus.value = 0
                openModel.numExercise.value = 0
                openModel.numMistakes.value = 0

                parentFragmentManager.beginTransaction().remove(this@Statistic).commit()
                parentFragmentManager.beginTransaction().replace(R.id.fragment, Settings()).commit()
            }
        }
    }


    private fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (start..end).shuffled().first()
    }



}