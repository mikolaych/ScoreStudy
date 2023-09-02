package com.example.lerningcount

import android.content.Intent.getIntent
import android.graphics.Color
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
               binding.grade.text = "2"
               binding.grade.setBackgroundResource(R.drawable.grade_two)
           }
            in 51..70 -> {
                binding.grade.text = "3"
                binding.grade.setBackgroundResource(R.drawable.grade_three)
            }
            in 71..80 -> {
                binding.grade.text = "4"
                binding.grade.setBackgroundResource(R.drawable.grade_four)
            }
            in 81..100 -> {
                binding.grade.text = "5"
                binding.grade.setBackgroundResource(R.drawable.grade_five)
                binding.grade.setTextColor(R.drawable.grade_two)
            }
        }
    }



}