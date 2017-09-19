package io.vinicius.androidcommon.view

import android.app.Fragment
import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment()
{
    internal val disposables = CompositeDisposable()

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        // To avoid the clicks on the current fragment to trigger other views underneath
        view.isClickable = true

        // Binding the views to the view model
        bindViewModel()
    }

    internal open fun bindViewModel()
    {}

    override fun onDestroy()
    {
        super.onDestroy()

        // Make sure we dispose any pending observable
        disposables.dispose()
    }
}