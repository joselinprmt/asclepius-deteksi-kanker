package com.dicoding.asclepius.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dicoding.asclepius.data.entity.History

@Dao
interface HistoryDao {
    @Query("SELECT * from history ORDER BY id ASC")
    fun getAllHistory(): LiveData<List<History>>

    @Query("SELECT * FROM history WHERE id = :id")
    fun getHistoryById(id: Int): LiveData<History>

    @Insert
    fun insert(history: History)
}