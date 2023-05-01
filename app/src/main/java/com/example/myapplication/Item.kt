package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val name: String,
    val category: String,
    val picture: String,
    val currentCount: Int,
    val totalCount: Int,
) : Parcelable {

}
