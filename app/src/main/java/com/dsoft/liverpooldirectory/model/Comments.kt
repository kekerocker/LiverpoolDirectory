package com.dsoft.liverpooldirectory.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "comments_table")
data class Comments(
    @PrimaryKey val id: Int?,
    val text: String,
): Parcelable
