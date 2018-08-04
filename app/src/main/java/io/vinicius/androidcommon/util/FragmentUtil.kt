package io.vinicius.androidcommon.util

import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.transition.Transition
import android.util.SparseArray
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.constant.FragmentTransition
import java.util.Stack
import javax.inject.Inject

class FragmentUtil @Inject constructor(private val activity: FragmentActivity)
{
    companion object
    {
        private val activities = SparseArray<Stack<Fragment>>()

        /**
         * The active fragments of a specific activity.
         */
        fun getFragments(activity: FragmentActivity): Stack<Fragment>
        {
            var fragments = activities.get(activity.hashCode())
            if(fragments == null) fragments = Stack()
            return fragments
        }

        private fun addToStack(activity: FragmentActivity, fragment: Fragment)
        {
            val fragments = getFragments(activity)
            fragments.push(fragment)
            activities.put(activity.hashCode(), fragments)
        }

        private fun removeFromStack(activity: FragmentActivity)
        {
            val fragments = getFragments(activity)
            fragments.pop()
            activities.put(activity.hashCode(), fragments)
        }

        /**
         * Put a fragment in a container, using an animation.
         *
         * @param activity
         * @param fragment
         * @param trans1 the animation for the fragment that will appear.
         * @param trans2 the animation for the fragment that will disappear.
         */
        fun put(activity: FragmentActivity, fragment: Fragment, trans1: Transition? = null, trans2: Transition? = null)
        {
            fragment.enterTransition = trans1
            fragment.exitTransition = trans2

            val ft = activity.supportFragmentManager.beginTransaction()

            // Clear the stack
            activities.put(activity.hashCode(), Stack())

            // Check if the activity is not ending
            if(!activity.isFinishing) ft.replace(R.id.frame_layout, fragment)

            ft.commitAllowingStateLoss()
            addToStack(activity, fragment)
        }

        fun push(activity: FragmentActivity, fragment: Fragment, trans1: Transition? = FragmentTransition.SLIDE_RIGHT,
                 trans2: Transition? = FragmentTransition.SLIDE_RIGHT)
        {
            fragment.enterTransition = trans1
            fragment.exitTransition = trans2

            val ft = activity.supportFragmentManager.beginTransaction()

            // Check if the activity is not ending
            if(!activity.isFinishing) ft.add(R.id.frame_layout, fragment)

            ft.commitAllowingStateLoss()
            addToStack(activity, fragment)
        }

        fun pop(activity: FragmentActivity)
        {
            val ft = activity.supportFragmentManager.beginTransaction()

            // Get the topmost fragment
            val topFragment = getFragments(activity).peek()

            // Check if the activity is not ending
            if(!activity.isFinishing) ft.remove(topFragment)

            ft.commitAllowingStateLoss()
            removeFromStack(activity)
        }

        fun replace(activity: FragmentActivity, fragment: Fragment, trans1: Transition? = null,
                    trans2: Transition? = null)
        {
            fragment.enterTransition = trans1
            fragment.exitTransition = trans2

            val ft = activity.supportFragmentManager.beginTransaction()

            // Get the topmost fragment
            val topFragment = getFragments(activity).peek()

            if(!activity.isFinishing && topFragment != null) {
                ft.add(R.id.frame_layout, fragment)
                ft.remove(topFragment)
            }

            ft.commitAllowingStateLoss()
            removeFromStack(activity)
            addToStack(activity, fragment)
        }

        /**
         * Show a dialog fragment.
         */
        fun showDialog(activity: FragmentActivity, fragment: DialogFragment)
        {
            val fm = activity.supportFragmentManager
            val ft = fm.beginTransaction()

            val oldFragment = fm.findFragmentByTag("dialog")
            oldFragment?.let { ft.remove(it) }
            ft.commitAllowingStateLoss()

            fragment.show(fm, "dialog")
        }
    }
}
