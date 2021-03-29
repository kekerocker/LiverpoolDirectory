package com.dsoft.liverpooldirectory.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "close_games_table")
data class CloseGames(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val teamName1: String,
    val teamName2: String,
    val score: String,
    val date: String,
    val matchType: String,
    val tournamentLogo: String
): Parcelable