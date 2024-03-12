package com.dsoft.liverpooldirectory2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "epl_table")
data class TableData(
    @PrimaryKey(autoGenerate = true)
    val position: Int,
    val clubName: String,
    val matches: String,
    val win: String,
    val draw: String,
    val lose: String,
    val points: String,
): Parcelable