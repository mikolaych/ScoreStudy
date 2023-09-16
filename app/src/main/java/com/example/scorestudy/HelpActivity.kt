package com.example.scorestudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scorestudy.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {
 lateinit var binding: ActivityHelpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHelpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLevelNum.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.helpFragment, BtnLevelNum()).commit()
        }
        binding.btnExerciseNum.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.helpFragment, BtnExerciseNum()).commit()
        }
        binding.btnMistakesNum.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.helpFragment, BtnMistakesNum()).commit()
        }
        binding.btnMultiplication.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.helpFragment, BtnMultiplication()).commit()
        }
        binding.btnSwitchTimer.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.helpFragment, BtnSwitchTimer()).commit()
        }
        binding.btnTimer.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.helpFragment, BtnTimer()).commit()
        }

        binding.btnClose.setOnClickListener {
           this.finish()
        }


    }
}