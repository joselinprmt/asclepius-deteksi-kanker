package com.dicoding.asclepius.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class History(
    var result: String? = null,
    var score: String? = null,
    var imageByteArray: ByteArray,
    var imageUri: String? = null,
    var inferenceTime: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as History

        if (result != other.result) return false
        if (score != other.score) return false
        if (!imageByteArray.contentEquals(other.imageByteArray)) return false
        if (inferenceTime != other.inferenceTime) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = result?.hashCode() ?: 0
        result1 = 31 * result1 + (score?.hashCode() ?: 0)
        result1 = 31 * result1 + imageByteArray.contentHashCode()
        result1 = 31 * result1 + (inferenceTime?.hashCode() ?: 0)
        result1 = 31 * result1 + id
        return result1
    }
}
