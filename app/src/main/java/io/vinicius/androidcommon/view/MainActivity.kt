package io.vinicius.androidcommon.view

import android.os.Bundle
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.util.FragmentUtil
import io.vinicius.androidcommon.view.home.HomeFragment
import javax.inject.Inject

class MainActivity : BaseActivity()
{
    @Inject
    lateinit var fu: FragmentUtil

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FragmentUtil.put(this, HomeFragment.newInstance())
    }
}
