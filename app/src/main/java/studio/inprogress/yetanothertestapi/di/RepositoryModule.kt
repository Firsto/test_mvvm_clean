package studio.inprogress.yetanothertestapi.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import studio.inprogress.yetanothertestapi.domain.repository.GitHubUsersRepository
import studio.inprogress.yetanothertestapi.domain.repository.LocalGoogleUserRepository

val repositoryModule = Kodein.Module("repository_module") {
    bind<GitHubUsersRepository>() with singleton { GitHubUsersRepository(instance(), instance()) }
    bind<LocalGoogleUserRepository>() with singleton { LocalGoogleUserRepository(instance()) }
}