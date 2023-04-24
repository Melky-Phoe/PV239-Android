package com.example.myapplication

import android.graphics.Picture

data class Item(
    val name: String,
    val picture: Picture,
    val currentCount: Int,
    val totalCount: Int,
)
