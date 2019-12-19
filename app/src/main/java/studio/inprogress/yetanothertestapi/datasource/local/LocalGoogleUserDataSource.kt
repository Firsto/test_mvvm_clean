package studio.inprogress.yetanothertestapi.datasource.local

import androidx.lifecycle.LiveData
import studio.inprogress.yetanothertestapi.domain.model.LocalGoogleUserEntity
import studio.inprogress.yetanothertestapi.providers.database.IGoogleUserDao

class LocalGoogleUserDataSource(private val dao: IGoogleUserDao) :
    ILocalGoogleUserDataSource {

    override fun getUser(): LiveData<LocalGoogleUserEntity> = dao.getUser()

    override suspend fun addUser(user: LocalGoogleUserEntity) = dao.insert(user)

    override suspend fun removeUser() = dao.removeUser()

}