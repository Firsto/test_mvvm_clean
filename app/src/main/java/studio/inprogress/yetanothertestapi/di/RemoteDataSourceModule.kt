package studio.inprogress.yetanothertestapi.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import studio.inprogress.yetanothertestapi.datasource.remote.IUsersRemoteDataSource
import studio.inprogress.yetanothertestapi.datasource.remote.UsersRemoteDataSource

val remoteDataSourceModule = Kodein.Module("remote_data_source_module") {
    bind<IUsersRemoteDataSource>() with singleton { UsersRemoteDataSource(instance()) }
}