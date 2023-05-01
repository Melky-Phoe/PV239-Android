package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PackerList(
    val name: String,
    val items: List<Item>,
) : Parcelable {

    }
