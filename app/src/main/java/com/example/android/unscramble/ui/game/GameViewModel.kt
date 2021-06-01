package com.example.android.unscramble.ui.game

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
}