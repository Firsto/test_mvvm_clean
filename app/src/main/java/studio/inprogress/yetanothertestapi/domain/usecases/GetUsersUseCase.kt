package studio.inprogress.yetanothertestapi.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import studio.inprogress.yetanothertestapi.domain.model.UserEntity
import studio.inprogress.yetanothertestapi.domain.repository.GitHubUsersRepository
import studio.inprogress.yetanothertestapi.utils.Resulted

class GetUsersUseCase(
    private val repository: GitHubUsersRepository,
    private val remoteUseCase: GetRemoteUsersUseCase,
    private val localUseCase: GetLocalUsersUseCase
) : ((String) -> LiveData<Resulted<List<UserEntity>>>) {
    override fun invoke(username: String): LiveData<Resulted<List<UserEntity>>> = liveData {
        repository.clear()
//        emit(localUseCase.exec(username))
        emit(remoteUseCase.exec(username))
    }
}
