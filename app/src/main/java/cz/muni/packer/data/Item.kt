package cz.muni.packer.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.firebase.database.IgnoreExtraProperties
import android.os.Parcelable
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import java.io.File

@IgnoreExtraProperties
@Parcelize
data class Item(
    val id: String? = null,
    val name: String? = null,
    val category: Categories? = null,
    val picture: String? = null,
    var currentCount: Int? = null,
    val totalCount: Int? = null,
    val packerListId: String? = null
) : Parcelable {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false
        if (name != other.name) return false
        if (category != other.category) return false
        if (picture != null) {
            if (other.picture == null) return false
            if (!picture.contentEquals(other.picture)) return false
        } else if (other.picture != null) return false
        if (currentCount != other.currentCount) return false
        if (totalCount != other.totalCount) return false
        if (packerListId != other.packerListId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (category?.hashCode() ?: 0)
        result = 31 * result + (picture?.hashCode() ?: 0)
        result = 31 * result + (currentCount ?: 0)
        result = 31 * result + (totalCount ?: 0)
        result = 31 * result + (packerListId?.hashCode() ?: 0)
        return result
    }
}

suspend fun loadImageFromFirebase(pictureUrl: String): Bitmap? {
    val storageReference = Firebase.storage.getReferenceFromUrl(pictureUrl)
    val localFile = withContext(Dispatchers.IO) {
        File.createTempFile("images", "jpg")
    }

    try {
        storageReference.getFile(localFile).await()
        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
        localFile.delete()
        return bitmap
    } catch (e: Exception) {
        localFile.delete()
        throw e
    }
}
