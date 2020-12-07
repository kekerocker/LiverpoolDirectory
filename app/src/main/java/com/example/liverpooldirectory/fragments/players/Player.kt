package com.example.liverpooldirectory.fragments.players

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
    val number: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val biography: String,
    @DrawableRes val photoRes: Int,
    @RawRes val sound: Int
) : Parcelable