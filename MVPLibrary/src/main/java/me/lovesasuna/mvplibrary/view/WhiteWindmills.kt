package me.lovesasuna.mvplibrary.view

import me.lovesasuna.mvplibrary.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import java.lang.ref.WeakReference


/**
 * @author LovesAsuna
 * @date 2020/10/17 10:43
 **/
/**
 * 白色风车
 */
class WhiteWindmills(context: Context,  attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    /**
     * 叶片的长度
     */
    private var mBladeRadius = 0f

    /**
     * 风车叶片旋转中心x
     */
    private var mCenterY = 0

    /**
     * 风车叶片旋转中心y
     */
    private var mCenterX = 0

    /**
     * 风车旋转中心点圆的半径
     */
    private var mPivotRadius = 0f
    private val mPaint: Paint = Paint()

    /**
     * 风车旋转时叶片偏移的角度
     */
    private var mOffsetAngle = 0
    private val mPath: Path = Path()

    /**
     * 风车支柱顶部和底部为了画椭圆的矩形
     */
    private val mRect = RectF()

    /**
     * 控件的宽
     */
    private var mWid = 0

    /**
     * 控件高
     */
    private var mHei = 0

    /**
     * 控件颜色
     */
    private var mColor = 0
    private val mHandler = MsgHandler(this)

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    private fun initView(context: Context, attrs: AttributeSet?) {
        val array: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.WhiteWindmills)
        mColor = array.getColor(R.styleable.WhiteWindmills_windColor, Color.WHITE)
        array.recycle()
        //抗锯齿
        mPaint.isAntiAlias = true
        mPaint.color = mColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val heiMeasure = MeasureSpec.getSize(heightMeasureSpec)
        val heiMode = MeasureSpec.getMode(heightMeasureSpec)
        val widMode = MeasureSpec.getMode(widthMeasureSpec)
        val widMeasure = MeasureSpec.getSize(widthMeasureSpec)
        mWid = widMeasure
        mHei = heiMeasure
        mCenterY = mWid / 2
        mCenterX = mWid / 2
        mPivotRadius = mWid.toFloat() / 40.toFloat()
        mBladeRadius = mCenterY - 2 * mPivotRadius
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //画扇叶旋转的中心
        drawPivot(canvas)

        //画扇叶
        drawWindBlade(canvas)

        //画底部支柱
        drawPillar(canvas)
    }

    /**
     * 画风车支点
     *
     * @param canvas
     */
    private fun drawPivot(canvas: Canvas) {
        mPaint.style = Paint.Style.FILL
        canvas.drawCircle(mCenterX.toFloat(), mCenterY.toFloat(), mPivotRadius, mPaint)
    }

    /**
     * 画叶片
     *
     * @param canvas
     */
    private fun drawWindBlade(canvas: Canvas) {
        canvas.save()
        mPath.reset()
        //根据偏移量画初始时画布的位置
        canvas.rotate(mOffsetAngle.toFloat(), mCenterX.toFloat(), mCenterY.toFloat())
        //画三角形扇叶
        mPath.moveTo(mCenterX.toFloat(), mCenterY - mPivotRadius) // 此点为多边形的起点
        mPath.lineTo(mCenterX.toFloat(), mCenterY - mPivotRadius - mBladeRadius)
        mPath.lineTo(mCenterX + mPivotRadius, mPivotRadius + mBladeRadius * 2.toFloat() / 3.toFloat())
        mPath.close() // 使这些点构成封闭的多边形
        canvas.drawPath(mPath, mPaint)

        //旋转画布120度，画第二个扇叶
        canvas.rotate(120F, mCenterX.toFloat(), mCenterY.toFloat())
        canvas.drawPath(mPath, mPaint)

        //旋转画布120度，画第三个扇叶
        canvas.rotate(120F, mCenterX.toFloat(), mCenterY.toFloat())
        canvas.drawPath(mPath, mPaint)
        canvas.restore()
    }

    /**
     * 画支柱
     *
     * @param canvas
     */
    private fun drawPillar(canvas: Canvas) {
        mPath.reset()
        //画上下半圆之间的柱形
        mPath.moveTo(mCenterX - mPivotRadius / 2, mCenterY + mPivotRadius + mPivotRadius / 2)
        mPath.lineTo(mCenterX + mPivotRadius / 2, mCenterY + mPivotRadius + mPivotRadius / 2)
        mPath.lineTo(mCenterX + mPivotRadius, mHei - 2 * mPivotRadius)
        mPath.lineTo(mCenterX - mPivotRadius, mHei - 2 * mPivotRadius)
        mPath.close()

        //画顶部半圆
        mRect[mCenterX - mPivotRadius / 2, mCenterY + mPivotRadius, mCenterX + mPivotRadius / 2] =
            mCenterY + 2 * mPivotRadius
        mPath.addArc(mRect, 180F, 180F)
        //画底部半圆
        mRect[mCenterX - mPivotRadius, mHei - 3 * mPivotRadius, mCenterX + mPivotRadius] = mHei - mPivotRadius
        mPath.addArc(mRect, 0F, 180F)
        canvas.drawPath(mPath, mPaint)
    }

    /**
     * 开始旋转
     */
    private fun startRotate() {
        stop()
        mHandler.sendEmptyMessageDelayed(0, 10)
    }

    /**
     * 停止旋转
     */
    private fun stop() {
        mHandler.removeMessages(0)
    }

    internal class MsgHandler(view: WhiteWindmills?) : Handler() {
        private val mView: WeakReference<WhiteWindmills> = WeakReference<WhiteWindmills>(view)
        override fun handleMessage(msg: Message) {
            val view: WhiteWindmills? = mView.get()
            if (view != null) {
                view.handleMessage(msg)
            }
        }

    }

    private fun handleMessage(msg: Message) {
        mOffsetAngle = if (mOffsetAngle in 0..359) {
            mOffsetAngle + 1
        } else {
            1
        }
        invalidate()
        startRotate()
    }

    init {
        initView(context, attrs)
    }
}