package io.vinicius.androidcommon.screen.country

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.vinicius.androidcommon.model.Country
import io.vinicius.androidcommon.service.CountryService
import io.vinicius.androidcommon.service.ServiceFactory
import timber.log.Timber

class CountryViewModel
{
    private val service = ServiceFactory.create(CountryService::class.java)

    // Subjects
    val isLoading = BehaviorSubject.create<Boolean>()
    val name = BehaviorSubject.create<String>()
    val capital = BehaviorSubject.create<String>()

    /*
     * API Calls
     */

    fun getCountryByCode(code: String): Observable<Country>
            = service.getByCountryCode(code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

                    .doOnSubscribe { isLoading.onNext(true) }

                    .doOnNext { country ->
                        name.onNext(country.name!!)
                        capital.onNext(country.capital!!)
                    }

                    .doOnError { t -> Timber.e(t, "Error getting the country by code") }
}