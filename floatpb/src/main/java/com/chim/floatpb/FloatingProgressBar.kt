package com.chim.floatpb

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.max
import kotlin.math.roundToInt

class FloatingProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val EXTRA_HEIGHT_MULTIPLY = 1
    }

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0

    private var paintIndicator = Paint(Paint.ANTI_ALIAS_FLAG)
    private var paintBarBackground = Paint(Paint.ANTI_ALIAS_FLAG)
    private var paintBarSelected = Paint(Paint.ANTI_ALIAS_FLAG)

    private var rectIndicator = Rect()
    private var rectView = RectF()
    private var rectBarBackground = RectF()
    private var rectBarSelected = RectF()

    private var textIndicatorSpacing = 0f

    private var barHeight = 0f
    private var barCorners = 0f

    var max = 0f
        private set
    var progress = 0f
        private set

    private val pathClip = Path()

    init {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        setLayerType(LAYER_TYPE_SOFTWARE,null)
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.FloatingProgressBar)
            barCorners = ta.getDimension(R.styleable.FloatingProgressBar_fpb_bar_corners_radius, 0f)
            barHeight = ta.getDimension(
                R.styleable.FloatingProgressBar_fpb_bar_height,
                resources.getDimension(R.dimen.def_bar_height)
            )
            paintBarBackground.color =
                ta.getColor(R.styleable.FloatingProgressBar_fpb_bar_background, Color.BLACK)
            paintBarSelected.color =
                ta.getColor(R.styleable.FloatingProgressBar_fpb_bar_selected, Color.WHITE)

            paintIndicator.color =
                ta.getColor(R.styleable.FloatingProgressBar_fpb_text_color, paintBarSelected.color)
            paintIndicator.textSize =
                ta.getDimension(R.styleable.FloatingProgressBar_fpb_text_size, 0f)
            textIndicatorSpacing =
                ta.getDimension(R.styleable.FloatingProgressBar_fpb_text_bottom_spacing, 0f)

            max = ta.getFloat(R.styleable.FloatingProgressBar_fpb_max, 100f)
            progress = ta.getFloat(R.styleable.FloatingProgressBar_fpb_progress, 0f)
            ta.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        var minHeight = barHeight + paddingTop + paddingBottom
        if (paintIndicator.textSize > 0f) {
            paintIndicator.getTextBounds("1", 0, 1, rectIndicator)
            minHeight += (textIndicatorSpacing + rectIndicator.height() * EXTRA_HEIGHT_MULTIPLY).roundToInt()
        }
        viewHeight = measureDimension(minHeight.roundToInt(), heightMeasureSpec)
        setMeasuredDimension(viewWidth, viewHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectView.set(
            0f + paddingLeft, 0f + paddingTop, (viewWidth - paddingRight).toFloat(),
            (viewHeight - paddingBottom).toFloat()
        )
        rectBarBackground.set(
            rectView.left,
            rectView.bottom - barHeight,
            rectView.right,
            rectView.bottom
        )
        rectBarSelected.top = rectBarBackground.top
        rectBarSelected.bottom = rectBarBackground.bottom
        rectBarSelected.left = rectBarBackground.left
        validateBarSelectedWithProgress()

        pathClip.reset()
        pathClip.addRoundRect(rectBarBackground, barCorners, barCorners, Path.Direction.CCW)
    }

    private fun validateBarSelectedWithProgress() {
        rectBarSelected.right = progress.ToDimensionPosition()
    }


    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = kotlin.math.min(result, specSize)
            }
        }
        return result
    }

    private fun Number.ToDimensionPosition(): Float {
        return (this.toFloat() / max * rectBarBackground.width() + rectView.left)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            drawView(it)
        }
    }

    private fun drawView(canvas: Canvas) {
        canvas.drawRoundRect(rectBarBackground, barCorners, barCorners, paintBarBackground)
        canvas.drawRoundRect(rectBarSelected, barCorners, barCorners, paintBarSelected)
        canvas.clipPath(pathClip)
    }
}