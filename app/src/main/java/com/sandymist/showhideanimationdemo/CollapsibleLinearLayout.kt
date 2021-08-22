package com.sandymist.showhideanimationdemo

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout

class CollapsibleLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
): LinearLayout(context, attrs, defStyle, defStyleRes) {
    private lateinit var animator: ValueAnimator

    init {
        val self = this
        viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    viewTreeObserver.removeOnPreDrawListener(this)
                    visibility = View.GONE
                    val widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                    val heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                    measure(widthSpec, heightSpec)
                    animator = slideAnimator(self, 0, measuredHeight)
                    return true
                }
            }
        )
    }

    fun toggleView() {
        if (visibility == View.GONE) {
            expand(this, animator)
        } else {
            collapse(this)
        }
    }
    private fun expand(view: View, animator: ValueAnimator) {
        view.visibility = View.VISIBLE
        animator.start()
    }

    private fun collapse(view: View) {
        val finalHeight = view.height
        val slideAnimator = slideAnimator(view, finalHeight, 0)
        slideAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animator: Animator) {
                view.visibility = View.GONE
            }

            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        slideAnimator.start()
    }

    private fun slideAnimator(view: View, start: Int, end: Int): ValueAnimator {
        val animator = ValueAnimator.ofInt(start, end)
        animator.addUpdateListener { valueAnimator -> // Update Height
            val value = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams?.height = value
            view.layoutParams = layoutParams
        }
        return animator
    }
}