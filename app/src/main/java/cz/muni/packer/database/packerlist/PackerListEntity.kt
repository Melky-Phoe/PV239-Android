package cz.muni.packer.database.packerlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PackerListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
) {

    // Generated by IDE
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PackerListEntity

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
    // Generated by IDE END
}