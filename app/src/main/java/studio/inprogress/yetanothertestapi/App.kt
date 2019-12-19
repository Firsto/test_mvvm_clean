package studio.inprogress.yetanothertestapi

import android.app.Application
import androidx.room.Room
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import studio.inprogress.yetanothertestapi.di.*
import studio.inprogress.yetanothertestapi.providers.database.GitHubDatabase

class App : Application(), KodeinAware {
    init {
        context = this
    }

    override val kodein: Kodein by Kodein.lazy {
        bind<GitHubDatabase>() with singleton {
            Room.databaseBuilder(
                applicationContext,
                GitHubDatabase::class.java, "usersDB"
            ).build()
        }

        import(androidXModule(this@App))
        import(authModule)
        import(netModule)
        import(databaseModule)
        import(localDataSourceModule)
        import(remoteDataSourceModule)
        import(repositoryModule)
        import(useCasesModule)
    }

    companion object {

        lateinit var context: App
            private set
    }
}