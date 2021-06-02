package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

/**
 *  Created by DanielHuang on 2021/6/1
 */
class GameViewModel : ViewModel() {

    private var _score = 0
    val score: Int get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    // Backing property
    private lateinit var _currentScrambledWord: String
    // 這個讓外部存取 read-only
    // NOTE: Never expose mutable data fields from your ViewModel
    // NOTE: Mutable data inside the ViewModel should always be private.
    val currentScrambledWord: String get() = _currentScrambledWord

    /**
     * 已經出過題目的單字清單
     */
    private var wordsList: MutableList<String> = mutableListOf()

    /**
     * 使用者要解的字
     */
    private lateinit var currentWord: String

    init {
        // 物件初始化時執行
        Log.d("GameFragment", "GameViewModel created!")
        // 出第一題
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        // ViewModel被清除時執行
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    /**
     * Logic
     */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        // 拆成字母
        val tempWord = currentWord.toCharArray()
        // 字母洗亂
        tempWord.shuffle()

        // 洗亂後的結果不能跟原本一樣
        while (tempWord.toString().equals(currentWord, false)) {
            tempWord.shuffle()
        }

        if (wordsList.contains(currentWord)) {
            // 已經出過該單字
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            // currentWordCount
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    /**
     * 檢查使用者答案是否正確
     */
    fun isUserWordCorrect(playerWord: String):Boolean {
        if (playerWord.equals(currentWord, false)) {
            increaseScore()
            return true
        }
        return false
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }
}