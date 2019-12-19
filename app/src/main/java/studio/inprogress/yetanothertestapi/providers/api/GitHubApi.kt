package studio.inprogress.yetanothertestapi.providers.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import studio.inprogress.yetanothertestapi.domain.model.UserEntity
import studio.inprogress.yetanothertestapi.utils.Consts

interface GitHubApi {
    @GET("search/users")
    fun getUsersList(
        @Query("q") username: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Consts.PER_PAGE_RESULTS
    ) : Call<GetUsersResponse>

    @GET("/users/{username}")
    fun getUser(@Path("username") username: String): Call<UserEntity>
}