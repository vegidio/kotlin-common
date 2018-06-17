package io.vinicius.androidcommon.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import io.vinicius.androidcommon.App
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.model.User
import io.vinicius.androidcommon.util.FragmentUtil
import io.vinicius.androidcommon.view.BaseFragment
import io.vinicius.androidcommon.viewmodel.AuthenticationViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginFragment : BaseFragment()
{
    companion object {
        fun newInstance() = LoginFragment()
    }

    @Inject
    lateinit var viewModel: AuthenticationViewModel

    init { App.component.inject(this) }

    /*
     * Lifecycle
     */

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View
        = inflater!!.inflate(R.layout.fragment_login, container, false)

    override fun bindViewModel()
    {
        disposables.addAll(

            RxView.clicks(btLogin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    val email = etEmail.text.toString().trim()
                    val password = etPassword.text.toString().trim()
                    doLogin(email, password)
                },

            RxView.clicks(btFacebook)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    doLoginWithFacebook()
                }
        )
    }

    /*
     * Private Methods
     */

    private val loginSuccessful = { user: User ->
        if (user.isLoggedIn) {
            dismissKeyboard()
            FragmentUtil.pop(activity)
        }
    }

    private fun doLogin(email: String, password: String)
    {
        disposables.add(
            viewModel.signIn(email, password).subscribe(loginSuccessful, {
                Timber.e(it, "Error doing e-mail login")
            })
        )
    }

    private fun doLoginWithFacebook()
    {
        disposables.add(
            viewModel.signInWithFacebook(activity).subscribe(loginSuccessful, {
                Timber.e(it, "Error doing Facebook login")
            })
        )
    }
}