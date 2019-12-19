package studio.inprogress.yetanothertestapi.domain.usecases

import studio.inprogress.yetanothertestapi.domain.model.UserEntity
import studio.inprogress.yetanothertestapi.domain.repository.GitHubUsersRepository
import studio.inprogress.yetanothertestapi.utils.Resulted
import studio.inprogress.yetanothertestapi.utils.applyIf
import studio.inprogress.yetanothertestapi.utils.emptyResult

class GetRemoteUsersUseCase(
    private val repository: GitHubUsersRepository
) {
    suspend fun exec(username: String?): Resulted<List<UserEntity>> = username?.let {
        repository.getRemoteUsers(username).applyIf(!repository.local) {
            saveResult(this)
        }
    } ?: emptyResult()

    private suspend fun saveResult(resulted: Resulted<List<UserEntity>>) {
        if (resulted is Resulted.Success && resulted.data.isNotEmpty()) {
            repository.saveUsers(resulted.data)
        }
    }
}