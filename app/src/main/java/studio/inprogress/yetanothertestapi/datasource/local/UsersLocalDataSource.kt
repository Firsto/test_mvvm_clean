package studio.inprogress.yetanothertestapi.datasource.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import studio.inprogress.yetanothertestapi.domain.model.UserEntity
import studio.inprogress.yetanothertestapi.providers.database.IUsersDao
import studio.inprogress.yetanothertestapi.utils.Resulted
import studio.inprogress.yetanothertestapi.utils.emptyResult
import studio.inprogress.yetanothertestapi.utils.successResult

class UsersLocalDataSource(private val usersDao: IUsersDao) :
    IUsersLocalDataSource {
    override suspend fun getAll(): Resulted.Success<List<UserEntity>> =
        Resulted.Success(usersDao.getAll())

    override suspend fun getByName(username: String): LiveData<Resulted<UserEntity>> =
        usersDao.getByName(username).map { userEntity ->
            if (userEntity != null) successResult(userEntity)
            else emptyResult()
        }

    override suspend fun insertAll(data: List<UserEntity>) {
        usersDao.insertAll(data)
    }

    override suspend fun insert(data: UserEntity) {
        usersDao.insert(data)
    }

}