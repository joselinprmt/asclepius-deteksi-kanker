package com.dicoding.asclepius.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.entity.History
import com.dicoding.asclepius.data.room.HistoryDao
import com.dicoding.asclepius.data.room.HistoryDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepository(application: Application) {
    private val mHistoryDao: HistoryDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HistoryDatabase.getDatabase(application)
        mHistoryDao = db.HistoryDao()
    }

    fun getAllHistory(): LiveData<List<History>> = mHistoryDao.getAllHistory()

    fun getHistoryById(id: Int): LiveData<History> =
        mHistoryDao.getHistoryById(id)

    fun insert(history: History) {
        executorService.execute { mHistoryDao.insert(history) }
    }
}