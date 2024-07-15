package com.dicoding.asclepius.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.HistoryRepository
import com.dicoding.asclepius.data.entity.History

class ResultViewModel(application: Application) : ViewModel() {

    private val mHistoryRepository: HistoryRepository = HistoryRepository(application)
    private var isInserted = false

    fun insertHistory(history: History) {
        if (!isInserted) {
            mHistoryRepository.insert(history)
            isInserted = true
        }
    }
}