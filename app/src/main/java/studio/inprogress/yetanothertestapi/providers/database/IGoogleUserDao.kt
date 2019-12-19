package studio.inprogress.yetanothertestapi.providers.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import studio.inprogress.yetanothertestapi.domain.model.LocalGoogleUserEntity

@Dao
interface IGoogleUserDao : IBaseDao<LocalGoogleUserEntity> {

    @Query("SELECT * FROM local_user LIMIT 1")
    fun getUser(): LiveData<LocalGoogleUserEntity>

    @Insert
    fun addUser(user: LocalGoogleUserEntity)

    @Query("DELETE FROM local_user")
    fun removeUser()
}