package com.timtam.uikit.bottomnavigation

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.timtam.uikit.R
import kotlin.math.abs

class AnimatedBottomNavigation : BottomNavigationView, NavigationBarView.OnItemSelectedListener {

    private var externalSelectedListener: OnItemSelectedListener? = null
    private var animator: ValueAnimator? = null

    private val indicator = RectF()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var defaultBottomOffset = 0.0f
    private var defaultSize = 0.0f

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setupInitialValue(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setupInitialValue(context, attrs)
    }

    init {
        super.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (externalSelectedListener?.onNavigationItemSelected(item) != false) {
            onItemSelected(item.itemId)
            return true
        }
        return false
    }

    override fun setOnItemSelectedListener(listener: OnItemSelectedListener?) {
        externalSelectedListener = listener
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        doOnPreDraw {
            // Move the indicator in place when the view is laid out
            onItemSelected(selectedItemId, false)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // Clean up the animator if the view is going away
        cancelAnimator(setEndValues = true)
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (isLaidOut) {
            val cornerRadius = indicator.height() / 2f
            canvas.drawRoundRect(indicator, cornerRadius, cornerRadius, paint)
        }
    }

    private fun setupInitialValue(context: Context, attrs: AttributeSet?) {
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.AnimatedBottomNavigation)
        val dotOffset = attributes.getDimension(
            R.styleable.AnimatedBottomNavigation_dotBottomOffset,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_MARGIN_BOTTOM,
                resources.displayMetrics
            )
        )
        val dotSize = attributes.getDimension(
            R.styleable.AnimatedBottomNavigation_dotSize,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_INDICATOR_SIZE,
                resources.displayMetrics
            )
        )
        val dotColor = attributes.getColor(
            R.styleable.AnimatedBottomNavigation_dotColor,
            0
        )

        defaultBottomOffset = dotOffset
        defaultSize = dotSize
        paint.color = dotColor

        this.setPadding(0, 0, 0, defaultBottomOffset.toInt() + 1)

        attributes.recycle()
    }

    private fun onItemSelected(itemId: Int, animate: Boolean = true) {
        if (!isLaidOut) return

        // Interrupt any current animation, but don't set the end values,
        // if it's in the middle of a movement we want it to start from
        // the current position, to make the transition smoother.
        cancelAnimator(setEndValues = false)

        val itemView = findViewById<View>(itemId) ?: return
        val fromCenterX = indicator.centerX()
        val fromScale = indicator.width() / defaultSize

        animator = ValueAnimator.ofFloat(fromScale, MAX_SCALE, DEFAULT_SCALE).apply {
            addUpdateListener {
                val progress = it.animatedFraction
                val distanceTravelled = linearInterpolation(progress, fromCenterX, itemView.centerX)

                val scale = it.animatedValue as Float
                val indicatorWidth = defaultSize * scale

                val left = distanceTravelled - indicatorWidth / 2f
                val top = height - defaultBottomOffset - defaultSize
                val right = distanceTravelled + indicatorWidth / 2f
                val bottom = height - defaultBottomOffset

                indicator.set(left, top, right, bottom)
                invalidate()
            }

            interpolator = LinearOutSlowInInterpolator()

            val distanceToMove = abs(fromCenterX - itemView.centerX)
            duration = if (animate) calculateDuration(distanceToMove) else 0L

            start()
        }
    }

    /**
     * Linear interpolation between 'a' and 'b' based on the progress 't'
     */
    private fun linearInterpolation(t: Float, a: Float, b: Float) = (1 - t) * a + t * b

    /**
     * Calculates a duration for the translation based on a fixed duration + a duration
     * based on the distance the indicator is being moved.
     */
    private fun calculateDuration(distance: Float) =
        (BASE_DURATION + VARIABLE_DURATION * (distance / width).coerceIn(0f, 1f)).toLong()

    /**
     * Convenience property for getting the center X value of a View
     */
    private val View.centerX get() = left + width / 2f

    private fun cancelAnimator(setEndValues: Boolean) = animator?.let {
        if (setEndValues) {
            it.end()
        } else {
            it.cancel()
        }
        it.removeAllUpdateListeners()
        animator = null
    }

    companion object {
        private const val DEFAULT_MARGIN_BOTTOM = 8f
        private const val DEFAULT_INDICATOR_SIZE = 4f
        private const val DEFAULT_SCALE = 1f
        private const val MAX_SCALE = 15f
        private const val BASE_DURATION = 300L
        private const val VARIABLE_DURATION = 300L
    }
}
