package studio.inprogress.yetanothertestapi.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_login.*
import studio.inprogress.yetanothertestapi.R
import studio.inprogress.yetanothertestapi.utils.logDebug
import studio.inprogress.yetanothertestapi.utils.showToast
import studio.inprogress.yetanothertestapi.utils.visible


class AuthFragment : Fragment() {

    val REQUEST_CODE_SIGN_IN = 42

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        sign_in_button.setSize(SignInButton.SIZE_WIDE)
        sign_in_button.setOnClickListener(signIn(viewModel.getGoogleSignInIntent()))
    }

    override fun onResume() {
        super.onResume()
        val lastAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
        sign_in_button.visible = (lastAccount == null)
        display_name.visible = (lastAccount != null)
        display_name.text = "Hello,\n ${lastAccount?.displayName}!"

        if (lastAccount == null) {
            sign_in_button.setOnClickListener(signIn(viewModel.getGoogleSignInIntent()))
        } else {
//            sign_in_button.setSize(SignInButton.SIZE_ICON_ONLY)
//            viewModel.saveGoogleUser(lastAccount)
//            sign_in_button.setOnClickListener { handleSignOutResult(viewModel.getGoogleSignOutTask())}
//            handleSignOutResult(viewModel.getGoogleSignOutTask())
        }
    }

    private fun signIn(googleSignInIntent: Intent): (View) -> Unit = {
        startActivityForResult(googleSignInIntent, REQUEST_CODE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            if (task != null) {
                val account: GoogleSignInAccount? = task?.getResult(ApiException::class.java)

                updateUI(account)
            } else {
                showToast("smth wrng")
            }
        } catch (e: ApiException) {
            showToast("Look like smth wrng w rrr " + e.localizedMessage)
            logDebug { "signInResult failed code= ${e.statusCode }"}
            updateUI(null)
        }
    }

    private fun handleSignOutResult(task: Task<Void>) {
        viewModel.logout()
        findNavController().navigateUp()
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        showToast("signed as " + account?.displayName)
        if (account != null) {
            viewModel.saveGoogleUser(account)
        }
        findNavController().navigate(R.id.action_login_to_search)
    }
}