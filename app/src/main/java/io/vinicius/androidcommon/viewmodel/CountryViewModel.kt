package io.vinicius.androidcommon.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.vinicius.androidcommon.App
import io.vinicius.androidcommon.constant.NetworkState
import io.vinicius.androidcommon.model.Country
import io.vinicius.androidcommon.service.CountryService
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class CountryViewModel : ViewModel()
{
    @Inject
    @field:Named("cached")
    lateinit var service: CountryService

    // Subjects
    val state = BehaviorSubject.create<NetworkState>()!!
    val country = BehaviorSubject.create<Country>()!!

    init { App.component.inject(this) }

    /*
     * API Calls
     */

    fun getCountryByCode(code: String): Observable<Country>
            = service.getByCountryCode(code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { state.onNext(NetworkState.LOADING) }
                    .doOnNext { country.onNext(it) }
                    .doOnError {
                        Timber.e(it, "Error getting the country by code")
                        state.onNext(NetworkState.ERROR)
                    }
                    .doOnComplete { state.onNext(NetworkState.IDLE) }
}