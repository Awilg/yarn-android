package dev.iwagl.ravel.data.models

import android.os.*
import kotlinx.android.parcel.*
import java.util.*

@Parcelize
data class User(
    val id: String,
    val name: String,
    val activeTreasureHunts: List<String>,
    val completedTreasureHunts: List<String>,
    val avatarUrl: String,
    val createdAt: Date) : Parcelable