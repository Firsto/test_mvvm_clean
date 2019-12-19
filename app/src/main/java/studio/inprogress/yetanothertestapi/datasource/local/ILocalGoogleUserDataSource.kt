package studio.inprogress.yetanothertestapi.datasource.local

import androidx.lifecycle.LiveData
import studio.inprogress.yetanothertestapi.domain.model.LocalGoogleUserEntity

interface ILocalGoogleUserDataSource {
    fun getUser(): LiveData<LocalGoogleUserEntity>
    suspend fun addUser(user: LocalGoogleUserEntity)
    suspend fun removeUser()
}