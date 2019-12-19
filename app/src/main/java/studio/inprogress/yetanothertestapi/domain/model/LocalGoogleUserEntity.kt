package studio.inprogress.yetanothertestapi.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_user")
data class LocalGoogleUserEntity(
    @PrimaryKey
    val id: String,
    val email: String?,
    val photoUrl: String?,
    val displayName: String?
)