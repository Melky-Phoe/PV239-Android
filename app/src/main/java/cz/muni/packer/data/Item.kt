package cz.muni.packer.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.ByteArrayOutputStream

@Parcelize
data class Item(
    val id: Long,
    val name: String,
    val category: Categories,
    val picture: ByteArray?,
    var currentCount: Int,
    val totalCount: Int,
    val packerListId: Long
) : Parcelable {

    // Generated by Android studio
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false
        if (name != other.name) return false
        if (category != other.category) return false
        if (currentCount != other.currentCount) return false
        if (totalCount != other.totalCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + currentCount
        result = 31 * result + totalCount
        return result
    }
}

fun byteArrayToBitmap(byteArray: ByteArray): Bitmap? {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    return outputStream.toByteArray()
}