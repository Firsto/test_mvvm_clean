package studio.inprogress.yetanothertestapi.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import studio.inprogress.yetanothertestapi.datasource.local.ILocalGoogleUserDataSource
import studio.inprogress.yetanothertestapi.datasource.local.IUsersLocalDataSource
import studio.inprogress.yetanothertestapi.datasource.local.LocalGoogleUserDataSource
import studio.inprogress.yetanothertestapi.datasource.local.UsersLocalDataSource

val localDataSourceModule = Kodein.Module("local_data_source_module") {
    bind<IUsersLocalDataSource>() with singleton { UsersLocalDataSource(instance()) }
    bind<ILocalGoogleUserDataSource>() with singleton { LocalGoogleUserDataSource(instance()) }
}