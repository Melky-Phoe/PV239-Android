package cz.muni.packer.data

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class PackerList(
    val id: String? = null,
    val name: String? = null,
    val items: List<Item>? = null,
) : Parcelable {

}
