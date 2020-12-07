package com.example.liverpooldirectory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "epl_table")
data class Table(
    @PrimaryKey val position: String,
    val clubName: String,
    val matches: String,
    val win: String,
    val draw: String,
    val lose: String,
    val points: String,

)