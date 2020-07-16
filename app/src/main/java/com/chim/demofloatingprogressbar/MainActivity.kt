package com.chim.demofloatingprogressbar

import android.annotation.SuppressLint
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ViewConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.abs

class MainActivity : AppCompatActivity() {
    private var isMoving = false
    private val scaleTouch by lazy {
        ViewConfiguration.get(this).scaledTouchSlop
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private val pointDown = PointF()
    @SuppressLint("ClickableViewAccessibility")
    fun init() {
        pbLoading.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    pointDown.set(event.x, event.y)
                }
                MotionEvent.ACTION_MOVE -> {
                    val disMove = event.x - pointDown.x
                    if (isMoving) {
                        pointDown.set(event.x, event.y)
                        if (disMove > 0) {
                            pbLoading.setProgress(pbLoading.progress + 1)
                        } else {
                            pbLoading.setProgress(pbLoading.progress - 1)
                        }
                    } else {
                        if (abs(disMove) >= scaleTouch) {
                            isMoving = true
                        }
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isMoving = false
                }
            }
            true
        }
    }
}