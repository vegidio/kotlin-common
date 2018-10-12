package io.vinicius.androidcommon.view

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import io.reactivex.disposables.CompositeDisposable
import io.vinicius.androidcommon.extension.*

open class BaseFragment : Fragment()
{
    val disposables = CompositeDisposable()
    val navigation get() = NavHostFragment.findNavController(this)
    private var isBoundViewModel = false

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        // To avoid the clicks on the current fragment to trigger other views underneath
        view?.isFocusable = true

        // Binding the views to the view model
        if(!isBoundViewModel) bindViewModel()
    }

    open fun bindViewModel()
    {
        isBoundViewModel = true
    }

    override fun onDestroy()
    {
        // Make sure we dispose any pending observable
        disposables.dispose()

        super.onDestroy()
    }

    /*
     * Methods
     */

    fun dismissKeyboard()
    {
        activity?.currentFocus?.let {
            val inputMethod = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethod.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}