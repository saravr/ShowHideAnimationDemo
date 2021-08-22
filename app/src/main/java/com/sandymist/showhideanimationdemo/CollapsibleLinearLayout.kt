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
    private var showAtStart = false
    private var viewHeight = 0

    companion object {
        private const val ANIMATION_DURATION = 300L
    }

    init {
        viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    viewTreeObserver.removeOnPreDrawListener(this)
                    val widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                    val heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                    measure(widthSpec, heightSpec)
                    viewHeight = measuredHeight
                    visibility = if (showAtStart) View.VISIBLE else View.GONE
                    return true
                }
            }
        )
    }

    fun toggleView() {
        if (visibility == View.GONE) {
            visibility = View.VISIBLE
            val slideAnimator = collapseAnimator(0, viewHeight)
            slideAnimator.start()
        } else {
            val slideAnimator = collapseAnimator(height, 0)
            slideAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animator: Animator) {
                    visibility = View.GONE
                }
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })
            slideAnimator.start()
        }
    }

    private fun collapseAnimator(start: Int, end: Int): ValueAnimator {
        val animator = ValueAnimator.ofInt(start, end)
        animator.duration = ANIMATION_DURATION
        animator.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val lp = layoutParams
            lp?.height = value
            layoutParams = lp
        }
        return animator
    }
}