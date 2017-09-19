package io.vinicius.androidcommon.util

import android.content.Context
import android.location.Location
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.rx.ObservableFactory
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class LocationUtil(private val context: Context)
{
    val lastLocation = BehaviorSubject.create<Location>()

    fun updateLocation(oneFix: Boolean = false)
    {
        val locationControl = if (oneFix) SmartLocation.with(context).location().oneFix() else
            SmartLocation.with(context).location()

        ObservableFactory.from(locationControl).subscribe(
                { location -> lastLocation.onNext(location) },
                { error -> Timber.e(error, "Error getting the location") }
        )
    }

    fun stopUpdates()
    {
        SmartLocation.with(context).location().stop()
    }
}