package studio.inprogress.yetanothertestapi.providers.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import studio.inprogress.yetanothertestapi.domain.model.UserEntity

@Dao
interface IUsersDao : IBaseDao<UserEntity> {

    @Query("SELECT * FROM UserEntity ORDER BY name ASC")
    suspend fun getAll(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE name = :username LIMIT 1")
    fun getByName(username: String): LiveData<UserEntity?>
}