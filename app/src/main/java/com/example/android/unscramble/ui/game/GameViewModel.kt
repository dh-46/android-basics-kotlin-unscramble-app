package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

/**
 *  Created by DanielHuang on 2021/6/1
 */
class GameViewModel : ViewModel() {

    private var score = 0
    private var currentWordCount = 0

    // Backing property
    private var _currentScrambledWord = "test"
    // 這個讓外部存取 read-only
    // NOTE: Never expose mutable data fields from your ViewModel
    // NOTE: Mutable data inside the ViewModel should always be private.
    val currentScrambledWord: String get() = _currentScrambledWord

    init {
        // 物件初始化時執行
        Log.d("GameFragment", "GameViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        // ViewModel被清除時執行
        Log.d("GameFragment", "GameViewModel destroyed!")
    }
}