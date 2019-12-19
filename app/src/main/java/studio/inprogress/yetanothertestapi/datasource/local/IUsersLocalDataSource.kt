package studio.inprogress.yetanothertestapi.datasource.local

import androidx.lifecycle.LiveData
import studio.inprogress.yetanothertestapi.domain.model.UserEntity
import studio.inprogress.yetanothertestapi.utils.Resulted

interface IUsersLocalDataSource {
    suspend fun getAll(): Resulted.Success<List<UserEntity>>
    suspend fun getByName(username: String): LiveData<Resulted<UserEntity>>
    suspend fun insertAll(data: List<UserEntity>)
    suspend fun insert(data: UserEntity)
}