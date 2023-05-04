package cz.muni.packer.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PackerList(
    val id: Long,
    val name: String,
    val items: List<Item>,
) : Parcelable {

}
