package studio.inprogress.yetanothertestapi.datasource.remote

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult
import studio.inprogress.yetanothertestapi.domain.model.UserEntity
import studio.inprogress.yetanothertestapi.providers.api.GetUsersResponse
import studio.inprogress.yetanothertestapi.providers.api.GitHubApi
import studio.inprogress.yetanothertestapi.utils.*

class UsersRemoteDataSource(
    private val gitHubApi: GitHubApi
) : IUsersRemoteDataSource {

    private val mutex = Mutex()

    private var currentPage = 1
    private var totalCount = Consts.MAX_RESULTS
    private val changePage: Boolean
        get() = currentPage < (totalCount / Consts.PER_PAGE_RESULTS)

    override suspend fun getUsersByName(username: String): Resulted<List<UserEntity>> =
        if (changePage) {
            request(username = username, page = currentPage, nextPage = true)
        } else emptyResult()

    private suspend fun request(username: String, page: Int, nextPage: Boolean = true) =
        mutex.withLock {
            when (val result = gitHubApi.getUsersList(username, page).awaitResult()) {
                is Result.Ok -> {
                    val response = result.value
                    logDebug { "ОК $response" }
                    if (response.items != null) {
                        if (nextPage) nextPage(response)
                        successResult(response.items)
                    } else {
                        errorResult(result.response.code(), result.response.message())
                    }
                }
                is Result.Error -> {
                    logDebug { "ERROR ${result.exception.message()}" }
                    errorResult(result.response.code(), result.exception.message())
                }
                is Result.Exception -> {
                    logDebug { "OMG EXCEPTION ${result.exception.message}" }
                    emptyResult()
                }
            }
        }

    override suspend fun searchNewUsers(username: String): Resulted<List<UserEntity>> {
        return request(username = username, page = 1, nextPage = false)
    }

    override suspend fun getUser(username: String): Resulted<UserEntity> {
        return when (val result = gitHubApi.getUser(username).awaitResult()) {
            is Result.Ok -> {
                logDebug { "OK ${result.value}" }
                successResult(result.value)
            }
            is Result.Error -> {
                logDebug { "ERROR" }
                errorResult(result.response.code(), result.exception.message())
            }
            is Result.Exception -> {
                logDebug { "OMG EXCEPTION ${result.exception.message}" }
                emptyResult()
            }
        }
    }

    override fun clear() {
        currentPage = 1
        totalCount = Consts.MAX_RESULTS
    }

    private fun nextPage(response: GetUsersResponse) {
        totalCount = response.total_count
        if (currentPage < (totalCount / Consts.PER_PAGE_RESULTS)) currentPage++
    }
}