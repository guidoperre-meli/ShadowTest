package com.guido.shadowtest

import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape

class SectionDrawable(builder: Builder) : ShapeDrawable() {

    companion object {
        private const val ELEVATION = 10
        private const val SHADOW_BLUR = 10f
        private const val DEFAULT_CORNER_RADIUS = 10f
        private const val ZERO = 0
        private const val ZERO_FLOAT = 0f
        private const val TWO = 2

        /**
         * Builds the shape.
         *
         * @return IShapeBuilder
         */
        fun builder(): ShapeBuilder {
            return Builder()
        }
    }

    private var elevation = ELEVATION
    private var roundCorner = builder.roundCorner
    private var shadowBlur = SHADOW_BLUR
    private var shadowColor  = builder.shadowColor
    private var backgroundColor = builder.backgroundColor

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        setPadding(getVerticalPadding())
        paint.color = backgroundColor
        paint.setShadowLayer(shadowBlur, ZERO_FLOAT, (elevation / TWO).toFloat(), shadowColor)
        shape = RoundRectShape(getRoundCorner(), null, null)

    }

    fun getElevation(): Int {
        return elevation
    }

    fun getDrawable(): Drawable {
        val drawable = LayerDrawable(arrayOf<Drawable>(this))
        drawable.setLayerInset(ZERO, elevation + TWO, elevation, elevation + TWO, elevation * TWO)
        return drawable
    }

    private fun getRoundCorner(): FloatArray {
        val backgroundPaint = Paint()
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.setShadowLayer(roundCorner, ZERO_FLOAT, ZERO_FLOAT, ZERO)
        return floatArrayOf(
            roundCorner, roundCorner, roundCorner, roundCorner,
            roundCorner, roundCorner, roundCorner, roundCorner
        )
    }

    private fun getVerticalPadding(): Rect {
        val rect = Rect()
        rect.top = elevation
        rect.bottom = elevation * TWO
        rect.left = elevation
        rect.right = elevation
        return rect
    }

    class Builder : ConfigBuilder, ShapeBuilder {

        var roundCorner: Float = DEFAULT_CORNER_RADIUS
        var shadowColor: Int = ZERO
        var backgroundColor: Int = ZERO

        override fun beginConfig(): ConfigBuilder {
            return this
        }

        override fun setRoundCorner(corner: Float): ConfigBuilder {
            this.roundCorner = corner
            return this
        }

        override fun setShadowColor(color: Int): ConfigBuilder {
            this.shadowColor = color
            return this
        }

        override fun setBackgroundColor(color: Int): ConfigBuilder {
            this.backgroundColor = color
            return this
        }

        override fun endConfig(): ShapeBuilder {
            return this
        }

        override fun build(): SectionDrawable {
            return SectionDrawable(this)
        }
    }

    interface ConfigBuilder {
        /**
         * Sets corner radius
         *
         * @param corner the size of the corner radius.
         * @return IConfigBuilder
         */
        fun setRoundCorner(corner: Float): ConfigBuilder

        /**
         * Sets shadow color.
         *
         * @param color the shadow color
         * @return IConfigBuilder
         */
        fun setShadowColor(color: Int): ConfigBuilder

        /**
         * Sets background color.
         *
         * @param color the background color
         * @return IConfigBuilder
         */
        fun setBackgroundColor(color: Int): ConfigBuilder

        fun endConfig(): ShapeBuilder
    }

    interface ShapeBuilder {
        /**
         * Begin config.
         *
         * @return IConfigBuilder
         */
        fun beginConfig(): ConfigBuilder

        /**
         * Builds it.
         *
         * @return SectionDrawable
         */
        fun build(): SectionDrawable
    }
}