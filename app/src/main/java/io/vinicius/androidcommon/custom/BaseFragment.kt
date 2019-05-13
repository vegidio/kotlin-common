package io.vinicius.androidcommon.custom

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment()
{
    val disposables = CompositeDisposable()
    val navigation get() = NavHostFragment.findNavController(this)

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        // To avoid the clicks on the current fragment to trigger other views underneath
        view?.isFocusable = true

        // Binding the views to the view model
        bindViewModel()
    }

    open fun bindViewModel()
    {
        // Clear the disposables before we add anything new. Most of the time this object will be already cleared
        // because it's initialized together with the fragment, but if we are playing with the fragment stack and a
        // fragment is brought from the back to the front of the stack, then we must make sure that this is cleared.
        disposables.clear()
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