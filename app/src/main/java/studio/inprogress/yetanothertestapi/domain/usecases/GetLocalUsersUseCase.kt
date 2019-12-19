package studio.inprogress.yetanothertestapi.domain.usecases

import studio.inprogress.yetanothertestapi.domain.model.UserEntity
import studio.inprogress.yetanothertestapi.domain.repository.GitHubUsersRepository
import studio.inprogress.yetanothertestapi.utils.Resulted
import studio.inprogress.yetanothertestapi.utils.emptyResult

class GetLocalUsersUseCase(
    private val repository: GitHubUsersRepository
) {
    suspend fun exec(username: String): Resulted<List<UserEntity>> =
        repository.getLocalUsers().let { resulted ->
            if (repository.local) resulted
            else emptyResult()
        }
}