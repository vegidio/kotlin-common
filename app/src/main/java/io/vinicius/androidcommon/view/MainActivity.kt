package io.vinicius.androidcommon.view

import android.content.Intent
import android.os.Bundle
import com.facebook.CallbackManager
import io.vinicius.androidcommon.App
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.util.FragmentUtil
import io.vinicius.androidcommon.view.home.HomeFragment
import javax.inject.Inject

class MainActivity : BaseActivity()
{
    @Inject
    lateinit var callbackManager: CallbackManager

    init { App.component.inject(this) }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FragmentUtil.put(this, HomeFragment.newInstance())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
