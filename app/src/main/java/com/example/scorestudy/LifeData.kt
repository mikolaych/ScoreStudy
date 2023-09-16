package com.example.scorestudy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class LifeData: ViewModel() {

    //Для MainWindow
    val numLevel: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val numExercise: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val numMistakes: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val timer: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val timerPlus: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val multiplicationStatus: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    //Для Statistic
    val allTime: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val numAllExercises: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val numMistakesStat: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val numTrueResult: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val numLvlDown: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }


}