package studio.inprogress.yetanothertestapi.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import studio.inprogress.yetanothertestapi.providers.database.GitHubDatabase
import studio.inprogress.yetanothertestapi.providers.database.IGoogleUserDao
import studio.inprogress.yetanothertestapi.providers.database.IUsersDao

val databaseModule = Kodein.Module("database_module") {
    bind<IUsersDao>() with provider { instance<GitHubDatabase>().getUsersDao() }
    bind<IGoogleUserDao>() with provider { instance<GitHubDatabase>().getLocalGoogleUserDao() }
}