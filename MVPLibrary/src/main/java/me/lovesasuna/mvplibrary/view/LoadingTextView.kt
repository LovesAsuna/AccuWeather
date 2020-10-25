package me.lovesasuna.mvplibrary.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/**
 * 颜色波浪TextView
 *
 * @author LovesAsuna
 * @date 2020/10/25 17:36
 **/
class LoadingTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    private var mLinearGradient: LinearGradient? = null
    private var mGradientMatrix: Matrix? = null
    private var mPaint: Paint? = null
    private var mViewWidth = 0F
    private var mTranslate = 0
    private val mAnimating = true
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mViewWidth == 0F) {
            mViewWidth = measuredWidth.toFloat()
            if (mViewWidth > 0) {
                mPaint = paint
                mLinearGradient = LinearGradient(
                    -mViewWidth,
                    0F,
                    0F,
                    0F,
                    intArrayOf(0x33ffffff, -0xcd7913, 0x33ffffff),
                    floatArrayOf(0f, 0.5f, 1f),
                    Shader.TileMode.CLAMP
                )
                mPaint?.shader = mLinearGradient
                mGradientMatrix = Matrix()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mAnimating && mGradientMatrix != null) {
            mTranslate += mViewWidth.toInt() / 10
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = (-mViewWidth).toInt()
            }
            mGradientMatrix?.setTranslate(mTranslate.toFloat(), 0F)
            mLinearGradient?.setLocalMatrix(mGradientMatrix)
            postInvalidateDelayed(50)
        }
    }
}
