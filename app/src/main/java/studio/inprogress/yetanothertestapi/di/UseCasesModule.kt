package studio.inprogress.yetanothertestapi.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import studio.inprogress.yetanothertestapi.domain.usecases.GetLocalUsersUseCase
import studio.inprogress.yetanothertestapi.domain.usecases.GetRemoteUsersUseCase
import studio.inprogress.yetanothertestapi.domain.usecases.GetUsersUseCase
import studio.inprogress.yetanothertestapi.domain.usecases.LocalGoogleUserUseCase

val useCasesModule = Kodein.Module("usecases_module") {

    bind<GetUsersUseCase>() with provider {
        GetUsersUseCase(instance(), instance(), instance())
    }
    bind<GetRemoteUsersUseCase>() with provider {
        GetRemoteUsersUseCase(instance())
    }
    bind<GetLocalUsersUseCase>() with provider {
        GetLocalUsersUseCase(instance())
    }
    bind<LocalGoogleUserUseCase>() with provider {
        LocalGoogleUserUseCase(instance())
    }
}