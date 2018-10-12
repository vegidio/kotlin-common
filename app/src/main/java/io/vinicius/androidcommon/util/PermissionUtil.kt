package io.vinicius.androidcommon.util

import android.app.Activity
import androidx.core.content.ContextCompat
import com.tbruyelle.rxpermissions2.RxPermissions
import timber.log.Timber

class PermissionUtil
{
    lateinit var activity: Activity

    fun requestPermission(permission: String, callback: Callback<Boolean>)
    {
        RxPermissions(activity)
                .request(permission)
                .subscribe(
                        { callback.response(it) },
                        {
                            Timber.e("Permission not granted for ${permission.toUpperCase()}")
                            callback.response(false)
                        }
                )
    }

    fun checkPermission(permission: String): Boolean
            = ContextCompat.checkSelfPermission(activity, permission) == 1
}
