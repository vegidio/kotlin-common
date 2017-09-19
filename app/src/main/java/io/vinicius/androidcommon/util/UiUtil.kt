package io.vinicius.androidcommon.util

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast

class UiUtil(appContext: Context)
{
    private val context: Context
    private val density: Float
    private var snackbar: Snackbar? = null

    init
    {
        this.context = appContext
        this.density = context.resources.displayMetrics.density
    }

    /*
     * Calculate pixel vs. screen density
     */

    fun pixelToDp(pixel: Int): Int = Math.round(pixel / density)

    fun dpToPixel(dp: Int): Int = Math.round(dp * density)

    /*
     * Toasts and Snackbars
     */

    fun showToast(message: String, length: Int = Toast.LENGTH_SHORT)
    {
        Toast.makeText(context, message, length).show()
    }

    fun showSnackBar(view: View, message: String, label: String, callback: Callback<View>? = null)
    {
        // Hide any previous snackbar before showing a new one
        hideSnackBar()

        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        if(callback != null) snackbar?.setAction(label, { v -> callback.response(v) })
        snackbar?.show()
    }

    fun hideSnackBar()
    {
        snackbar?.dismiss()
    }
}