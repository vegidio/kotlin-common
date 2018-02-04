package io.vinicius.androidcommon.util

import android.app.Activity
import android.app.DialogFragment
import android.app.Fragment
import android.transition.Transition
import android.util.SparseArray
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.constant.FragmentTransition
import java.util.*
import javax.inject.Inject

class FragmentUtil @Inject constructor(private val activity: Activity)
{
    companion object
    {
        private val activities = SparseArray<Stack<Fragment>>()

        /**
         * The active fragments of a specific activity.
         */
        fun getFragments(activity: Activity): Stack<Fragment>
        {
            var fragments = activities.get(activity.hashCode())
            if(fragments == null) fragments = Stack()
            return fragments
        }

        private fun addToStack(activity: Activity, fragment: Fragment)
        {
            val fragments = getFragments(activity)
            fragments.push(fragment)
            activities.put(activity.hashCode(), fragments)
        }

        private fun removeFromStack(activity: Activity)
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
         * @param anim1 the animation for the fragment that will appear.
         * @param anim2 the animation for the fragment that will disappear.
         */
        fun put(activity: Activity, fragment: Fragment, trans1: Transition? = null, trans2: Transition? = null)
        {
            fragment.enterTransition = trans1
            fragment.exitTransition = trans2

            val ft = activity.fragmentManager.beginTransaction()

            // Clear the stack
            activities.put(activity.hashCode(), Stack())

            // Check if the activity is not ending
            if(!activity.isFinishing) ft.replace(R.id.frame_layout, fragment)

            ft.commitAllowingStateLoss()
            addToStack(activity, fragment)
        }

        fun push(activity: Activity, fragment: Fragment, trans1: Transition? = FragmentTransition.SLIDE_RIGHT,
                 trans2: Transition? = FragmentTransition.SLIDE_RIGHT)
        {
            fragment.enterTransition = trans1
            fragment.exitTransition = trans2

            val ft = activity.fragmentManager.beginTransaction()

            // Check if the activity is not ending
            if(!activity.isFinishing) ft.add(R.id.frame_layout, fragment)

            ft.commitAllowingStateLoss()
            addToStack(activity, fragment)
        }

        fun pop(activity: Activity)
        {
            val ft = activity.fragmentManager.beginTransaction()

            // Get the topmost fragment
            val topFragment = getFragments(activity).peek()

            // Check if the activity is not ending
            if(!activity.isFinishing) ft.remove(topFragment)

            ft.commitAllowingStateLoss()
            removeFromStack(activity)
        }

        fun replace(activity: Activity, fragment: Fragment, trans1: Transition? = null, trans2: Transition? = null)
        {
            fragment.enterTransition = trans1
            fragment.exitTransition = trans2

            val ft = activity.fragmentManager.beginTransaction()

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
        fun showDialog(activity: Activity, fragment: DialogFragment)
        {
            val fm = activity.fragmentManager
            val ft = fm.beginTransaction()

            val oldFragment = fm.findFragmentByTag("dialog")
            if(oldFragment != null) ft.remove(oldFragment)
            ft.commitAllowingStateLoss()

            fragment.show(fm, "dialog")
        }
    }
}
