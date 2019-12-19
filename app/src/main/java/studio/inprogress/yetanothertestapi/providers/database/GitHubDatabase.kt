package studio.inprogress.yetanothertestapi.providers.database

import androidx.room.Database
import androidx.room.RoomDatabase
import studio.inprogress.yetanothertestapi.domain.model.LocalGoogleUserEntity
import studio.inprogress.yetanothertestapi.domain.model.UserEntity

@Database(
    entities = [
        UserEntity::class,
        LocalGoogleUserEntity::class
    ], version = 1
)

abstract class GitHubDatabase : RoomDatabase() {
    abstract fun getUsersDao(): IUsersDao
    abstract fun getLocalGoogleUserDao(): IGoogleUserDao
}