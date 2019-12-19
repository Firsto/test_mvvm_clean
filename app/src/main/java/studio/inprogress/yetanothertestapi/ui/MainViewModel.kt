package studio.inprogress.yetanothertestapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import studio.inprogress.yetanothertestapi.App
import studio.inprogress.yetanothertestapi.domain.model.LocalGoogleUserEntity
import studio.inprogress.yetanothertestapi.domain.usecases.LocalGoogleUserUseCase

class MainViewModel : ViewModel(), KodeinAware {
    override val kodein: Kodein by lazy { (App.context.applicationContext as KodeinAware).kodein }

    private val localGoogleUserUseCase: LocalGoogleUserUseCase by instance()
    private val googleSignInClient: GoogleSignInClient by instance()

    fun getUser(): LiveData<LocalGoogleUserEntity> = localGoogleUserUseCase.getUser()

    fun logout() {
        googleSignInClient.signOut().addOnSuccessListener {
            CoroutineScope(Job() + Dispatchers.IO).launch {
                localGoogleUserUseCase.removeUser()
            }
        }
    }
}
