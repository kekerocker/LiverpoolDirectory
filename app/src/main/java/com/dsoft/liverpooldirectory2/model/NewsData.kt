package com.dsoft.liverpooldirectory2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "news_table")
data class NewsData(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String,
    val description: String,
    val image: String,
    val url: String
): Parcelable