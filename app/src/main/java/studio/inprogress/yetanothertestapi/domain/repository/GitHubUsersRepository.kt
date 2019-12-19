package studio.inprogress.yetanothertestapi.domain.repository

import androidx.lifecycle.LiveData
import studio.inprogress.yetanothertestapi.datasource.local.IUsersLocalDataSource
import studio.inprogress.yetanothertestapi.datasource.remote.IUsersRemoteDataSource
import studio.inprogress.yetanothertestapi.domain.model.UserEntity
import studio.inprogress.yetanothertestapi.utils.Resulted

class GitHubUsersRepository(
    private val remoteDataSource: IUsersRemoteDataSource,
    private val localDataSource: IUsersLocalDataSource
) {
    var local = false
        private set

    suspend fun getLocalUsers(): Resulted<List<UserEntity>> =
        localDataSource.getAll().apply {
            local = this.data.isNotEmpty()
        }

    suspend fun getNewUsers(username: String): Resulted<List<UserEntity>> =
        remoteDataSource.searchNewUsers(username)

    suspend fun getRemoteUsers(username: String): Resulted<List<UserEntity>> =
        remoteDataSource.getUsersByName(username)

    suspend fun saveUsers(data: List<UserEntity>) {
        local = data.isNotEmpty()
        localDataSource.insertAll(data)
    }

    suspend fun saveUser(data: UserEntity) = localDataSource.insert(data)

    suspend fun getLocalUserByName(username: String): LiveData<Resulted<UserEntity>> =
        localDataSource.getByName(username)

    suspend fun getRemoteUserByName(username: String): Resulted<UserEntity> =
        remoteDataSource.getUser(username)

    fun clear() {
        local = false
        remoteDataSource.clear()
    }
}