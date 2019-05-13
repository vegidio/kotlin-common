package io.vinicius.androidcommon.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.vinicius.androidcommon.App
import io.vinicius.androidcommon.model.User
import javax.inject.Inject

class AuthenticationViewModel : ViewModel()
{
    @Inject
    lateinit var callbackManager: CallbackManager

    val auth = BehaviorSubject.create<FirebaseAuth>()!!
    val user = BehaviorSubject.create<User>()!!

    init
    {
        App.component.inject(this)

        FirebaseAuth.getInstance().addAuthStateListener {
            auth.onNext(it)
            user.onNext(extractUser(it.currentUser))
        }

        extractUser(FirebaseAuth.getInstance().currentUser)
    }

    fun signIn(email: String, password: String): Observable<User>
    {
        return Observable.create { observer ->

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {

                if(it.isSuccessful) {
                    observer.onNext(extractUser(it.result?.user))
                    observer.onComplete()
                } else {
                    observer.onError(it.exception!!)
                }
            }
        }
    }

    fun signInWithFacebook(activity: Activity): Observable<User>
    {
        return Observable.create { observer ->

            with(LoginManager.getInstance()) {

                // Facebook callback
                this.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {

                        result?.accessToken?.let { accessToken ->
                            val credential = FacebookAuthProvider.getCredential(accessToken.token)

                            FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnCompleteListener {

                                    if(it.isSuccessful) {
                                        observer.onNext(extractUser(it.result?.user))
                                        observer.onComplete()
                                    } else {
                                        observer.onError(it.exception!!)
                                    }
                                }
                        }
                    }

                    override fun onError(error: FacebookException?) = observer.onError(error!!)

                    override fun onCancel() = observer.onComplete()

                })

                // Request Facebook access
                logInWithReadPermissions(activity, arrayListOf("public_profile", "email", "user_friends"))
            }
        }

    }

    fun signOut()
    {
        // Sign-off from all social networks
        FirebaseAuth.getInstance().currentUser?.providerData?.forEach {
            when(it.providerId) {
                "facebook.com" -> LoginManager.getInstance().logOut()
            }
        }

        // Sign-off on Firebase
        FirebaseAuth.getInstance().signOut()
    }

    /*
     * Private Methods
     */

    private fun extractUser(user: FirebaseUser?): User
    {
        val newUser = User()
        if(user == null) return newUser

        newUser.isLoggedIn = true
        newUser.loginType = if(user.isAnonymous) "anonymous" else "email"
        newUser.email = user.email

        // E-mail
        if(user.email.isNullOrBlank()) {
            user.providerData.forEach {
                when(it.providerId) {
                    "facebook.com" -> {
                        newUser.loginType = "facebook"
                        newUser.email = it.email
                    }
                }
            }
        }

        return newUser
    }
}