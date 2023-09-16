package com.example.lerningcount

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.scorestudy.HelpActivity
import com.example.scorestudy.LifeData
import com.example.scorestudy.MainActivity
import com.example.scorestudy.R
import com.example.scorestudy.databinding.SettingsBinding


class Settings : Fragment() {
    private lateinit var binding: SettingsBinding
    private val openModel: LifeData by activityViewModels()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Включение/выключение таймера
        switchTimer()
        //Обработка кнопки save
        buttonSaveClick()
        //Включение/выключение умножения
        multiplicationSwitch()
        //Переход в справку
        btnHelp()
    }

    private fun buttonSaveClick() {
        binding.apply {
            buttonSave.setOnClickListener {
                if (switchTimer.isChecked) {
                    if (!switchMultiplication.isChecked && (timerPlusWindow.text.isNullOrEmpty() || timerWindow.text.isNullOrEmpty())) {
                        var timer = object : CountDownTimer(1000, 1000) {
                            override fun onTick(p0: Long) {
                                timerWindow.setBackgroundResource(R.drawable.round_style_red)
                                timerPlusWindow.setBackgroundResource(R.drawable.round_style_red)
                            }

                            override fun onFinish() {
                                timerWindow.setBackgroundResource(R.drawable.round_style)
                                timerPlusWindow.setBackgroundResource(R.drawable.round_style)
                            }

                        }.start()
                    } else if (switchMultiplication.isChecked && timerWindow.text.isNullOrEmpty()) {
                        var timer = object : CountDownTimer(1000, 1000) {
                            override fun onTick(p0: Long) {
                                timerWindow.setBackgroundResource(R.drawable.round_style_red)
                            }

                            override fun onFinish() {
                                timerWindow.setBackgroundResource(R.drawable.round_style)
                            }

                        }.start()
                    } else {
                        if (switchMultiplication.isChecked) {
                            openModel.multiplicationStatus.value = true
                            openModel.numLevel.value = sliderNumOfLevels.value.toInt()
                            openModel.timer.value = timerWindow.text.toString().toInt()
                            openModel.timerPlus.value = 0
                            openModel.numExercise.value = sliderNumOfExercises.value.toInt()
                            openModel.numMistakes.value = sliderNumOfMistakes.value.toInt()

                        } else {
                            openModel.multiplicationStatus.value = false
                            openModel.numLevel.value = sliderNumOfLevels.value.toInt()
                            openModel.timer.value = timerWindow.text.toString().toInt()
                            openModel.timerPlus.value = timerPlusWindow.text.toString().toInt()
                            openModel.numExercise.value = sliderNumOfExercises.value.toInt()
                            openModel.numMistakes.value = sliderNumOfMistakes.value.toInt()

                        }
                        parentFragmentManager.beginTransaction().remove(this@Settings).commit()
                        parentFragmentManager.beginTransaction().replace(R.id.fragment, MainWindow()).commit()

                    }


                } else {
                    openModel.multiplicationStatus.value = switchMultiplication.isChecked
                    openModel.numExercise.value = sliderNumOfExercises.value.toInt()
                    openModel.numMistakes.value = sliderNumOfMistakes.value.toInt()
                    openModel.numLevel.value = sliderNumOfLevels.value.toInt()

                    openModel.timer.value = 0
                    openModel.timerPlus.value = 0

                    parentFragmentManager.beginTransaction().remove(this@Settings).commit()
                    parentFragmentManager.beginTransaction().replace(R.id.fragment, MainWindow()).commit()

                }
            }
        }
    }

    private fun switchTimer() {
        binding.apply {
            switchTimer.setOnCheckedChangeListener { compoundButton, isChecked ->

                if (switchMultiplication.isChecked){
                    if (isChecked) {
                        tittleTimer.visibility = View.VISIBLE
                        timerWindow.visibility = View.VISIBLE
                        tittleTimerPlus.visibility = View.INVISIBLE
                        timerPlusWindow.visibility = View.INVISIBLE
                    } else {
                        tittleTimer.visibility = View.INVISIBLE
                        timerWindow.visibility = View.INVISIBLE
                        tittleTimerPlus.visibility = View.INVISIBLE
                        timerPlusWindow.visibility = View.INVISIBLE
                    }

                } else {
                    if (isChecked) {
                        tittleTimer.visibility = View.VISIBLE
                        timerWindow.visibility = View.VISIBLE
                        tittleTimerPlus.visibility = View.VISIBLE
                        timerPlusWindow.visibility = View.VISIBLE
                    } else {
                        tittleTimer.visibility = View.INVISIBLE
                        timerWindow.visibility = View.INVISIBLE
                        tittleTimerPlus.visibility = View.INVISIBLE
                        timerPlusWindow.visibility = View.INVISIBLE
                    }
                }

            }
        }
    }

    private fun multiplicationSwitch() {
        binding.apply {
            switchMultiplication.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (switchTimer.isChecked) {
                    if (isChecked) {
                        tittleTimerPlus.visibility = View.INVISIBLE
                        timerPlusWindow.visibility = View.INVISIBLE
                    } else {
                        tittleTimerPlus.visibility = View.VISIBLE
                        timerPlusWindow.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun btnHelp() {
        binding.btnHelp.setOnClickListener {
            val intent = Intent(context, HelpActivity::class.java)
            startActivity(intent)
        }
    }
}