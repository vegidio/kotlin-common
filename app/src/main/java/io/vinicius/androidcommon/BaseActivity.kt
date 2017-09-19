package io.vinicius.androidcommon

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import io.vinicius.androidcommon.util.FragmentUtil
import timber.log.Timber

open class BaseActivity : AppCompatActivity()
{
    override fun onBackPressed()
    {
        val alertDialog = AlertDialog.Builder(this)
                .setTitle("Android Common")
                .setMessage("Are you sure do you want to quit the app")
                .setPositiveButton("Yes") { dialog, i -> finish() }
                .setNegativeButton("No") { dialog, i -> Timber.i("I changed my mind...") }
                .setCancelable(false)
                .create()

        if(FragmentUtil.getFragments(this).size == 1)
            alertDialog.show()
        else
            FragmentUtil.pop(this, R.animator.slide_in_left, R.animator.slide_out_right)
    }
}