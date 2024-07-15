package com.dicoding.asclepius.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.HistoryRepository
import com.dicoding.asclepius.data.entity.History

class HistoryViewModel(application: Application) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    private val mHistoryRepository : HistoryRepository = HistoryRepository(application)

    fun getAllHistory() : LiveData<List<History>> {
        isLoading.value = false
        return mHistoryRepository.getAllHistory()
    }
}