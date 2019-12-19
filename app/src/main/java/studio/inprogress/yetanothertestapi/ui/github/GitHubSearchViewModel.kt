package studio.inprogress.yetanothertestapi.ui.github

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import studio.inprogress.yetanothertestapi.App
import studio.inprogress.yetanothertestapi.domain.model.UserEntity
import studio.inprogress.yetanothertestapi.domain.usecases.GetRemoteUsersUseCase
import studio.inprogress.yetanothertestapi.domain.usecases.GetUsersUseCase
import studio.inprogress.yetanothertestapi.utils.Resulted
import studio.inprogress.yetanothertestapi.utils.emptyResult
import studio.inprogress.yetanothertestapi.utils.loading

class GitHubSearchViewModel : ViewModel(), KodeinAware {
    override val kodein: Kodein by lazy { (App.context.applicationContext as KodeinAware).kodein }

    private val getRemoteUsersUseCase: GetRemoteUsersUseCase by instance()
    private val getUsersUseCase: GetUsersUseCase by instance()

    private val name by lazy { MutableLiveData<String>() }
    private val usersList by lazy { MutableLiveData<Resulted<List<UserEntity>>>() }

    private val usersLiveData: LiveData<Resulted<List<UserEntity>>> by lazy {
        name.switchMap {
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(loading())
                emitSource(getUsersUseCase(it))
            }
        }
    }

    private val nextData: LiveData<Resulted<List<UserEntity>>> by lazy {
        usersList.switchMap {
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(loading())
                emit(getRemoteUsersUseCase.exec(name.value))
            }
        }
    }

    fun loadUsers(): LiveData<Resulted<List<UserEntity>>> = usersLiveData
    fun loadNextUsers(): LiveData<Resulted<List<UserEntity>>> = nextData

    fun next() {
        usersList.postValue(emptyResult())
    }

    fun searchNew(username: String) {
        name.postValue(username)
    }
}
