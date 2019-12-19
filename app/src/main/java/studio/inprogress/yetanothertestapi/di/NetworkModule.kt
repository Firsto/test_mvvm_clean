package studio.inprogress.yetanothertestapi.di

import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.kodein.di.generic.with
import studio.inprogress.yetanothertestapi.BuildConfig
import studio.inprogress.yetanothertestapi.providers.api.GitHubApi
import studio.inprogress.yetanothertestapi.providers.api.createOkHttpClient
import studio.inprogress.yetanothertestapi.providers.api.createWebServiceApi

private const val TAG_SERVER_URL = "server_url_const"

val netModule = Kodein.Module("net_module") {
    constant(TAG_SERVER_URL) with BuildConfig.ApiEndpoint
    bind<OkHttpClient>() with singleton { createOkHttpClient() }
    bind<GitHubApi>() with singleton {
        createWebServiceApi<GitHubApi>(
            instance(),
            instance(TAG_SERVER_URL)
        )
    }
}