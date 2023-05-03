package cz.muni.packer

import android.os.Parcelable
import cz.muni.packer.data.Item
import kotlinx.parcelize.Parcelize

@Parcelize
data class PackerList(
    val name: String,
    val items: List<Item>,
) : Parcelable {

    }
