package io.vinicius.androidcommon.view.authentication

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import io.vinicius.androidcommon.App
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.constant.FragmentTransition
import io.vinicius.androidcommon.util.FragmentUtil
import io.vinicius.androidcommon.view.BaseFragment
import io.vinicius.androidcommon.view.login.LoginFragment
import io.vinicius.androidcommon.viewmodel.AuthenticationViewModel
import kotlinx.android.synthetic.main.fragment_authentication.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthenticationFragment : BaseFragment()
{
    @Inject
    lateinit var viewModel: AuthenticationViewModel

    init { App.component.inject(this) }

    /*
     * Lifecycle
     */

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View
        = inflater!!.inflate(R.layout.fragment_authentication, container, false)

    override fun bindViewModel()
    {
        disposables.addAll(

            RxView.clicks(btLoginLogout)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if(viewModel.user.value.isLoggedIn) {
                        viewModel.signOut()
                    } else {
                        FragmentUtil.push(this.activity, LoginFragment.newInstance(),
                            FragmentTransition.SLIDE_BOTTOM, FragmentTransition.SLIDE_BOTTOM)
                    }
                },

            viewModel.user.subscribe { user ->
                if(user.isLoggedIn) {
                    txEmail.setTextColor(ContextCompat.getColor(this.activity, R.color.black))
                    txEmail.text = user.email
                    btLoginLogout.text = "Logout"
                } else {
                    txEmail.setTextColor(ContextCompat.getColor(this.activity, R.color.medium_gray))
                    txEmail.text = "No account"
                    btLoginLogout.text = "Login"
                }
            }
        )
    }
}