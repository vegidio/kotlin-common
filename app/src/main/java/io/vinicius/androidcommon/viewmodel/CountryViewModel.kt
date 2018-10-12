package io.vinicius.androidcommon.viewmodel

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.vinicius.androidcommon.constant.NetworkState
import io.vinicius.androidcommon.model.Country
import io.vinicius.androidcommon.service.CountryService
import io.vinicius.androidcommon.service.ServiceFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CountryViewModel @Inject constructor(sf: ServiceFactory)
{
    private val service = sf.create(CountryService::class.java, 5, TimeUnit.MINUTES)

    // Subjects
    val state = BehaviorSubject.create<NetworkState>()!!
    val country = BehaviorSubject.create<Country>()!!

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