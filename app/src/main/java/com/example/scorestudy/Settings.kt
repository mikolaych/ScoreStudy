package com.example.lerningcount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.lerningcount.databinding.SettingsBinding


class Settings : Fragment() {
    lateinit var binding: SettingsBinding
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

        // Включить/выключить таблицу умножения
        switchMultiplication()
        // Включение/выключение таймера
        switchTimer()
        //Обработка кнопки save
        buttonSaveClick()
    }

    private fun buttonSaveClick() {
        binding.apply {
            buttonSave.setOnClickListener {
                if (!switchMultiplication.isChecked) {
                    openModel.numLevel.value = sliderNumOfLevels.value.toInt()
                }
                openModel.numExercise.value = sliderNumOfExercises.value.toInt()
                openModel.numMistakes.value = sliderNumOfMistakes.value.toInt()
                if (switchTimer.isChecked){
                    openModel.timer.value = timerWindow.text.toString().toInt()
                    openModel.timerPlus.value = timerPlusWindow.text.toString().toInt()
                }
                parentFragmentManager.beginTransaction().replace(R.id.fragment, MainWindow()).commit()
                parentFragmentManager.beginTransaction().remove(this@Settings).commit()
            }
        }

    }

    private fun switchTimer() {
        binding.apply {
            switchTimer.setOnCheckedChangeListener { compoundButton, isChecked ->
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


    private fun switchMultiplication() {
        binding.apply {
            switchMultiplication.setOnCheckedChangeListener { compoundButton, isChecked ->
                if(isChecked) {
                    tittleNumOfLevels.visibility = View.INVISIBLE
                    sliderNumOfLevels.visibility = View.INVISIBLE
                } else {
                    tittleNumOfLevels.visibility = View.VISIBLE
                    sliderNumOfLevels.visibility = View.VISIBLE
                }
            }
        }
    }


}