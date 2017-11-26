package io.vinicius.androidcommon.util

import android.content.Context
import android.location.Location
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.rx.ObservableFactory
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class LocationUtil @Inject constructor(private val context: Context)
{
    val lastLocation = BehaviorSubject.create<Location>()

    fun updateLocation(oneFix: Boolean = false)
    {
        val locationControl = if (oneFix) SmartLocation.with(context).location().oneFix() else
            SmartLocation.with(context).location()

        ObservableFactory.from(locationControl).subscribe(
                { lastLocation.onNext(it) },
                { Timber.e(it, "Error getting the location") }
        )
    }

    fun stopUpdates()
    {
        SmartLocation.with(context).location().stop()
    }
}
