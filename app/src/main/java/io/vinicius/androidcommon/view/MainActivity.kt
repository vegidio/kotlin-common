package io.vinicius.androidcommon.view

import android.os.Bundle
import io.vinicius.androidcommon.App
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.util.FragmentUtil
import io.vinicius.androidcommon.view.home.HomeFragment

class MainActivity : BaseActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Saving the current activity
        App.activity = this

        FragmentUtil.put(this, HomeFragment())
    }
}