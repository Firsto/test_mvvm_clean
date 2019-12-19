package studio.inprogress.yetanothertestapi.domain.repository

import studio.inprogress.yetanothertestapi.datasource.local.ILocalGoogleUserDataSource
import studio.inprogress.yetanothertestapi.domain.model.LocalGoogleUserEntity

class LocalGoogleUserRepository(
    private val dataSource: ILocalGoogleUserDataSource
) {
    fun getUser() = dataSource.getUser()
    suspend fun addUser(user: LocalGoogleUserEntity) = dataSource.addUser(user)
    suspend fun removeUser() = dataSource.removeUser()
}