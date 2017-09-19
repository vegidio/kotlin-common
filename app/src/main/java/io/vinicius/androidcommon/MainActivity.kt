package io.vinicius.androidcommon

import android.os.Bundle
import io.vinicius.androidcommon.util.FragmentUtil
import io.vinicius.androidcommon.screen.home.HomeFragment

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