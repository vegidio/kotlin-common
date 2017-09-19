package io.vinicius.androidcommon.util

import android.app.Activity
import android.app.DialogFragment
import android.app.Fragment
import android.util.SparseArray
import io.vinicius.androidcommon.R
import java.util.*

class FragmentUtil
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
            if(fragments == null) fragments = Stack<Fragment>()
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
        fun put(activity: Activity, fragment: Fragment, anim1: Int? = null, anim2: Int? = null)
        {
            val ft = activity.fragmentManager.beginTransaction()

            // Clear the stack
            activities.put(activity.hashCode(), Stack())

            // Setting the fragment animation
            if(anim1 != null || anim2 != null) ft.setCustomAnimations(anim1!!, anim2!!)

            // Check if the activity is not ending
            if(!activity.isFinishing) ft.replace(R.id.frame_layout, fragment)

            ft.commitAllowingStateLoss()
            addToStack(activity, fragment)
        }

        fun push(activity: Activity, fragment: Fragment, anim1: Int? = null, anim2: Int? = null)
        {
            val ft = activity.fragmentManager.beginTransaction()

            // Clear the stack
            activities.put(activity.hashCode(), Stack())

            // Setting the fragment animation
            if(anim1 != null || anim2 != null) ft.setCustomAnimations(anim1!!, anim2!!)

            // Check if the activity is not ending
            if(!activity.isFinishing) ft.add(R.id.frame_layout, fragment)

            ft.commitAllowingStateLoss()
            addToStack(activity, fragment)
        }

        fun pop(activity: Activity, anim1: Int? = null, anim2: Int? = null)
        {
            val ft = activity.fragmentManager.beginTransaction()

            // Get the topmost fragment
            val topFragment = getFragments(activity).peek()

            // Setting the fragment animation
            if(anim1 != null || anim2 != null) ft.setCustomAnimations(anim1!!, anim2!!)

            // Check if the activity is not ending
            if(!activity.isFinishing) ft.remove(topFragment)

            ft.commitAllowingStateLoss()
            removeFromStack(activity)
        }

        fun replace(activity: Activity, fragment: Fragment, anim1: Int? = null, anim2: Int? = null)
        {
            val ft = activity.fragmentManager.beginTransaction()

            // Get the topmost fragment
            val topFragment = getFragments(activity).peek()

            // Setting the fragment animation
            if(anim1 != null || anim2 != null) ft.setCustomAnimations(anim1!!, anim2!!)

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