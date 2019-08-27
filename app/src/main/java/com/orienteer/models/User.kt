package com.orienteer.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class User(
    val id: String,
    val name: String,
    val activeTreasureHunts: List<String>,
    val completedTreasureHunts: List<String>,
    val avatarUrl: String,
    val createdAt: Date) : Parcelable