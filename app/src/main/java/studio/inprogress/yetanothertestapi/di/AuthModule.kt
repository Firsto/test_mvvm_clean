package studio.inprogress.yetanothertestapi.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val authModule = Kodein.Module("auth_module") {
    bind<GoogleSignInOptions>() with singleton {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
            .build()
    }

    bind<GoogleSignInClient>() with singleton {
        GoogleSignIn.getClient(instance<Context>(), instance())
    }
}