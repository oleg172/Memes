package com.olmi.android.memes.data.models

import java.io.Serializable

data class Mem(
    val id: Long,
    val title: String,
    val description: String,
    val isFavorite: Boolean,
    val createdDate: Long,
    val photoUrl: String
): Serializable