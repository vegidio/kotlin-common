package io.vinicius.androidcommon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.vinicius.androidcommon.util.FragmentUtil
import io.vinicius.androidcommon.view.CountryFragment

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Saving the current activity
        App.activity = this

        FragmentUtil.put(this, CountryFragment())
    }
}