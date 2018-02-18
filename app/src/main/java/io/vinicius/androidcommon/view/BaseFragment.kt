package io.vinicius.androidcommon.view

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
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

    /*
     * Methods
     */

    fun dismissKeyboard()
    {
        val view = activity.currentFocus

        if(view != null) {
            val inputMethod = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethod.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}