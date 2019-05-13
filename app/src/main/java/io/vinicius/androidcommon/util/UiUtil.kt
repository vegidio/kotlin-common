package io.vinicius.androidcommon.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class UiUtil @Inject constructor(private val context: Context)
{
    private val density = context.resources.displayMetrics.density
    private var snackbar: Snackbar? = null

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

        callback?.let { cb ->
            snackbar?.setAction(label) { cb.response(it) }
        }

        snackbar?.show()
    }

    fun hideSnackBar()
    {
        snackbar?.dismiss()
    }
}
