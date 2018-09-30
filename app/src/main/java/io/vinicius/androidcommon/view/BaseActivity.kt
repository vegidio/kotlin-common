package io.vinicius.androidcommon.view

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation
import io.vinicius.androidcommon.R
import timber.log.Timber

open class BaseActivity : AppCompatActivity()
{
    val navigation get() = Navigation.findNavController(this, R.id.fragment_host)

    override fun onBackPressed()
    {
        val alertDialog = AlertDialog.Builder(this)
                .setTitle("Android Common")
                .setMessage("Are you sure do you want to quit the app?")
                .setPositiveButton("Yes") { _, _ -> super.onBackPressed() }
                .setNegativeButton("No") { _, _ -> Timber.i("I changed my mind...") }
                .setCancelable(false)
                .create()

        if(navigation.currentDestination?.id == R.id.homeFragment)
            alertDialog.show()
        else
            super.onBackPressed()
    }
}