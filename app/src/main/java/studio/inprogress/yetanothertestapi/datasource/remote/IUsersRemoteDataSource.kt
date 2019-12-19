package studio.inprogress.yetanothertestapi.datasource.remote

import studio.inprogress.yetanothertestapi.domain.model.UserEntity
import studio.inprogress.yetanothertestapi.utils.Resulted

interface IUsersRemoteDataSource {
    suspend fun getUsersByName(username: String): Resulted<List<UserEntity>>

    suspend fun searchNewUsers(username: String): Resulted<List<UserEntity>>

    suspend fun getUser(username: String): Resulted<UserEntity>

    fun clear()
}