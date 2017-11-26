package io.vinicius.androidcommon.util

import android.support.v4.content.ContextCompat
import com.tbruyelle.rxpermissions2.RxPermissions
import io.vinicius.androidcommon.App
import timber.log.Timber

class PermissionUtil
{
    companion object
    {
        fun requestPermission(permission: String, callback: Callback<Boolean>)
        {
            RxPermissions(App.activity)
                    .request(permission)
                    .subscribe(
                            { granted -> callback.response(granted) },
                            { error ->
                                Timber.e("Permission not granted for ${permission.toUpperCase()}")
                                callback.response(false)
                            }
                    )
        }

        fun checkPermission(permission: String): Boolean
                = ContextCompat.checkSelfPermission(App.activity, permission) == 1
    }
}