package io.vinicius.androidcommon.viewmodel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class AuthenticationViewModel
{
    val auth = BehaviorSubject.create<FirebaseAuth>()
    val user = BehaviorSubject.create<FirebaseUser>()

    init
    {
        FirebaseAuth.getInstance().addAuthStateListener {
            auth.onNext(it)
            if(it.currentUser != null) user.onNext(it.currentUser!!)
        }
    }

    fun signIn(email: String, password: String): Observable<FirebaseUser>
    {
        return Observable.create { observer ->

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {

                if (it.isSuccessful) {
                    observer.onNext(it.result.user)
                    observer.onComplete()
                } else {
                    observer.onError(it.exception!!)
                }
            }
        }
    }

    fun signOut()
    {
        FirebaseAuth.getInstance().currentUser?.providerData?.forEach {
            when(it.providerId) {

            }
        }

        FirebaseAuth.getInstance().signOut()
    }
}