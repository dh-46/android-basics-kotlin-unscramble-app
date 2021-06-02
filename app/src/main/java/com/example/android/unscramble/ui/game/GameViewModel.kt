package com.example.android.unscramble.ui.game

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 *  Created by DanielHuang on 2021/6/1
 */
class GameViewModel : ViewModel() {

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    // Backing property
    private val _currentScrambledWord = MutableLiveData<String>()
    // 這個讓外部存取 read-only
    // NOTE: Never expose mutable data fields from your ViewModel
    // NOTE: Mutable data inside the ViewModel should always be private.
//    val currentScrambledWord: LiveData<String> get() = _currentScrambledWord
    val currentScrambledWord: LiveData<Spannable> = Transformations.map(_currentScrambledWord) {
        if (it == null) {
            SpannableString("")
        } else {
            val scrambledWord = it.toString()
            val spannable: Spannable = SpannableString(scrambledWord)
            spannable.setSpan(
                TtsSpan.VerbatimBuilder(scrambledWord).build(),
                0,
                scrambledWord.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannable
        }
    }

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
        // 出第一題
        getNextWord()
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
            _currentScrambledWord.value = String(tempWord)
            // currentWordCount
            // use inc() Kotlin function to increment the value by one with null-safety.
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
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
        // Use the plus() Kotlin function to increase the _score value,
        // which performs the addition with null-safety.
        _score.value = _score.value?.plus(SCORE_INCREASE)
    }

    /**
     * Word count 已達到 10 時，需要 reset
     */
    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }
}