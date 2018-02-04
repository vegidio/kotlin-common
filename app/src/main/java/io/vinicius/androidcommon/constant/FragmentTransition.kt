package io.vinicius.androidcommon.constant

import android.transition.Slide
import android.view.Gravity

class FragmentTransition
{
    companion object
    {
        val SLIDE_RIGHT: Slide = {
            val trans = Slide(Gravity.RIGHT)
            trans
        }()

        val SLIDE_LEFT: Slide = {
            val trans = Slide(Gravity.LEFT)
            trans
        }()

        val SLIDE_TOP: Slide = {
            val trans = Slide(Gravity.TOP)
            trans
        }()

        val SLIDE_BOTTOM: Slide = {
            val trans = Slide(Gravity.BOTTOM)
            trans
        }()
    }
}