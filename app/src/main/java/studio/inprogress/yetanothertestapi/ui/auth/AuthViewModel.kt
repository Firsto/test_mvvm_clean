package studio.inprogress.yetanothertestapi.ui.auth

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import studio.inprogress.yetanothertestapi.App
import studio.inprogress.yetanothertestapi.domain.usecases.LocalGoogleUserUseCase

class AuthViewModel : ViewModel(), KodeinAware {
    override val kodein: Kodein by lazy { (App.context.applicationContext as KodeinAware).kodein }

    private val googleSignInClient: GoogleSignInClient by instance()
    private val localGoogleUserUseCase: LocalGoogleUserUseCase by instance()

    fun getGoogleSignInIntent() = googleSignInClient.signInIntent
    fun getGoogleSignOutTask(): Task<Void> = googleSignInClient.signOut()

    fun saveGoogleUser(account: GoogleSignInAccount) {
        CoroutineScope(Job() + Dispatchers.IO).launch {
            localGoogleUserUseCase.saveUser(
                account.id!!,
                account.email,
                account.photoUrl.toString(),
                account.displayName
            )
        }
    }

    fun logout() {
        CoroutineScope(Job() + Dispatchers.IO).launch {
            localGoogleUserUseCase.removeUser()
        }
    }
}
