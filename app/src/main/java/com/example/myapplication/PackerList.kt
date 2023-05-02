package com.example.myapplication

import android.os.Parcelable
import com.example.myapplication.data.Item
import kotlinx.parcelize.Parcelize

@Parcelize
data class PackerList(
    val name: String,
    val items: List<Item>,
) : Parcelable {

    }
