package io.vinicius.androidcommon.extension

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.push(resId: Int, args: Bundle? = null, navOptions: NavOptions? = null)
{
    val options = NavOptions.Builder()
        .setEnterAnim(navOptions?.enterAnim ?: -1)
        .setExitAnim(navOptions?.exitAnim ?: androidx.navigation.R.attr.exitAnim)
        .setPopEnterAnim(navOptions?.popEnterAnim ?: androidx.navigation.R.attr.popEnterAnim)
        .setPopExitAnim(navOptions?.popExitAnim ?: -1)
        .build()

    this.navigate(resId, args, options)
}