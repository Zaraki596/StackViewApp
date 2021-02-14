package com.example.stackviewapp.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

/*
To Apply fade for the view having the
* */
fun View.visibleFade() {
    this.apply {
        alpha = 0f
        visibility = View.VISIBLE

        animate().alpha(1f)
            .setDuration(resources.getInteger(android.R.integer.config_mediumAnimTime).toLong())
            .setListener(null)
    }
}


fun View.slideUp(duration: Int = 300) {
    val animate = TranslateAnimation(0f, 0f, this.height.toFloat(), 0f)
    animate.duration = duration.toLong()
    animate.fillBefore = true
    animate.isFillEnabled = true
    this.startAnimation(animate)
    visibility = View.VISIBLE
}

fun View.slideDown(duration: Int = 300) {
    val animate = TranslateAnimation(0f, 0f, 0f, this.height.toFloat())
    animate.duration = duration.toLong()
    animate.setInterpolator(this.context, android.R.anim.decelerate_interpolator)
    animate.fillBefore = true
    animate.isFillEnabled = true

/*    NEEDED animation listener to set offset coz the fillAfter = true cause weird issue where the
     view flickers at the time of going down*/
    animate.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {

        }

        override fun onAnimationRepeat(animation: Animation) {
        }

        override fun onAnimationEnd(animation: Animation) {
            offsetTopAndBottom(this@slideDown.height)
        }
    })
    this.startAnimation(animate)
    visibility = View.GONE
}






