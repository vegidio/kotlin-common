package io.vinicius.androidcommon.view.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding2.view.RxView
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.custom.BaseFragment
import io.vinicius.androidcommon.viewmodel.AuthenticationViewModel
import kotlinx.android.synthetic.main.fragment_authentication.*
import java.util.concurrent.TimeUnit

class AuthenticationFragment : BaseFragment()
{
    private val viewModel by lazy { ViewModelProviders.of(this).get(AuthenticationViewModel::class.java) }

    /*
     * Lifecycle
     */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_authentication, container, false)

    override fun bindViewModel()
    {
        super.bindViewModel()

        disposables.addAll(
            RxView.clicks(btLoginLogout)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if(viewModel.user.value.isLoggedIn) {
                        viewModel.signOut()
                    } else {
                        navigation.navigate(R.id.authToLogin)
                    }
                },

            viewModel.user.subscribe { user ->
                if(user.isLoggedIn) {
                    txEmail.setTextColor(ContextCompat.getColor(this.context!!, R.color.black))
                    txEmail.text = user.email
                    btLoginLogout.text = "Logout"
                } else {
                    txEmail.setTextColor(ContextCompat.getColor(this.context!!, R.color.medium_gray))
                    txEmail.text = "No account"
                    btLoginLogout.text = "Login"
                }
            }
        )
    }
}