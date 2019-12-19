package studio.inprogress.yetanothertestapi.domain.usecases

import androidx.lifecycle.LiveData
import studio.inprogress.yetanothertestapi.domain.model.LocalGoogleUserEntity
import studio.inprogress.yetanothertestapi.domain.repository.LocalGoogleUserRepository

class LocalGoogleUserUseCase(
    private val repository: LocalGoogleUserRepository
) {
    fun getUser(): LiveData<LocalGoogleUserEntity> = repository.getUser()

    suspend fun saveUser(
        id: String,
        email: String?,
        photoUrl: String?,
        displayName: String?
    ) {
        repository.addUser(LocalGoogleUserEntity(id, email, photoUrl, displayName))
    }

    suspend fun removeUser() {
        repository.removeUser()
    }
}
