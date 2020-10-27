package me.lovesasuna.mvplibrary.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Handler
import android.os.Message
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import kotlin.math.asin


/**
 * @author LovesAsuna
 * @date 2020/10/27 18:38
 **/
/**
 * 水波圆
 *
 * @author lgl
 */
class CircleView : View {
    private var mContext: Context
    private var mScreenWidth = 0
    private var mScreenHeight = 0
    private lateinit var mRingPaint: Paint
    private lateinit var mCirclePaint: Paint
    private lateinit var mWavePaint: Paint
    private lateinit var linePaint: Paint
    private lateinit var flowPaint: Paint
    private lateinit var leftPaint: Paint
    private val mRingSTROKEWidth = 15
    private val mCircleSTROKEWidth = 2
    private val mLineSTROKEWidth = 1
    private val mCircleColor: Int = Color.WHITE
    private val mRingColor: Int = Color.WHITE
    private val mWaveColor: Int = Color.WHITE
    private lateinit var mHandler: Handler
    private var c = 0L
    private var mStarted = false
    private val f = 0.033f
    private val mAlpha = 50 // 透明度
    private val mAmplitude = 10.0f // 振幅
    private var mWaterLevel = 0.5f // 水高(0~1)
    private lateinit var mPath: Path

    // 绘制文字显示在圆形中间，只是我没有设置，我觉得写在布局上也挺好的
    private val flowNum = ""
    private val flowLeft = "优"

    /**
     * @param context
     */
    constructor(context: Context) : super(context) {
        // TODO Auto-generated constructor stub
        mContext = context
        init(mContext)
    }

    /**
     * @param context
     * @param attrs
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        // TODO Auto-generated constructor stub
        mContext = context
        init(mContext)
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        // TODO Auto-generated constructor stub
        mContext = context
        init(mContext)
    }

    fun setmWaterLevel(mWaterLevel: Float) {
        this.mWaterLevel = mWaterLevel
    }

    private fun init(context: Context) {
        mRingPaint = Paint()
        mRingPaint.color = mRingColor
        mRingPaint.alpha = 50
        mRingPaint.style = Paint.Style.STROKE
        mRingPaint.isAntiAlias = true
        mRingPaint.strokeWidth = mRingSTROKEWidth.toFloat()
        mCirclePaint = Paint()
        mCirclePaint.color = mCircleColor
        mCirclePaint.style = Paint.Style.STROKE
        mCirclePaint.isAntiAlias = true
        mCirclePaint.strokeWidth = mCircleSTROKEWidth.toFloat()
        linePaint = Paint()
        linePaint.color = mCircleColor
        linePaint.style = Paint.Style.STROKE
        linePaint.isAntiAlias = true
        linePaint.strokeWidth = mLineSTROKEWidth.toFloat()
        flowPaint = Paint()
        flowPaint.color = mCircleColor
        flowPaint.style = Paint.Style.FILL
        flowPaint.isAntiAlias = true
        flowPaint.textSize = 36F
        leftPaint = Paint()
        leftPaint.color = mCircleColor
        leftPaint.style = Paint.Style.FILL
        leftPaint.isAntiAlias = true
        leftPaint.textSize = 36F
        mWavePaint = Paint()
        mWavePaint.strokeWidth = 1.0f
        mWavePaint.color = mWaveColor
        mWavePaint.alpha = mAlpha
        mPath = Path()
        mHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                if (msg.what == 0) {
                    invalidate()
                    if (mStarted) {
                        // 不断发消息给自己，使自己不断被重绘
                        mHandler.sendEmptyMessageDelayed(0, 60L)
                    }
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measure(widthMeasureSpec, true)
        val height = measure(heightMeasureSpec, false)
        if (width < height) {
            setMeasuredDimension(width, width)
        } else {
            setMeasuredDimension(height, height)
        }
    }

    /**
     * @category 测量
     * @param measureSpec
     * @param isWidth
     * @return
     */
    private fun measure(measureSpec: Int, isWidth: Boolean): Int {
        var result: Int
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        val padding: Int = if (isWidth) paddingLeft + paddingRight else paddingTop + paddingBottom
        if (mode == MeasureSpec.EXACTLY) {
            result = size
        } else {
            result = if (isWidth) suggestedMinimumWidth else suggestedMinimumHeight
            result += padding
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size)
                } else {
                    result = Math.min(result, size)
                }
            }
        }
        return result
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh)
        mScreenWidth = w
        mScreenHeight = h
    }

    override fun onDraw(canvas: Canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas)
        // 得到控件的宽高
        val width = width
        val height = height
        setBackgroundColor(android.R.color.transparent)
        // 计算当前油量线和水平中线的距离
        val centerOffset = Math.abs(
            mScreenWidth / 2 * mWaterLevel
                    - mScreenWidth / 4
        )
        // 计算油量线和与水平中线的角度
        val horiAngle = (asin(centerOffset / (mScreenWidth / 4).toDouble()) * 180 / Math.PI).toFloat()
        // 扇形的起始角度和扫过角度
        val startAngle: Float
        val sweepAngle: Float
        if (mWaterLevel > 0.5f) {
            startAngle = 360f - horiAngle
            sweepAngle = 180f + 2 * horiAngle
        } else {
            startAngle = horiAngle
            sweepAngle = 180f - 2 * horiAngle
        }
        canvas.drawLine(
            (mScreenWidth * 3 / 8).toFloat(), (mScreenHeight * 5 / 8).toFloat(),
            (mScreenWidth * 5 / 8).toFloat(), (mScreenHeight * 5 / 8).toFloat(), linePaint
        )
        val num: Float = flowPaint.measureText(flowNum)
        canvas.drawText(
            flowNum, mScreenWidth * 4 / 8 - num / 2,
            (mScreenHeight * 4 / 8).toFloat(), flowPaint
        )
        val left: Float = leftPaint.measureText(flowLeft)
        canvas.drawText(
            flowLeft, mScreenWidth * 4 / 8 - left / 2,
            (mScreenHeight * 3 / 8).toFloat(), leftPaint
        )

        // 如果未开始（未调用startWave方法）,绘制一个扇形
        if ((!mStarted) || (mScreenWidth == 0) || (mScreenHeight == 0)) {
            // 绘制,即水面静止时的高度
            val oval = RectF(
                (mScreenWidth / 4).toFloat(), (mScreenHeight / 4).toFloat(),
                (mScreenWidth * 3 / 4).toFloat(), (mScreenHeight * 3 / 4).toFloat()
            )
            canvas.drawArc(oval, startAngle, sweepAngle, false, mWavePaint)
            return
        }
        // 绘制,即水面静止时的高度
        // 绘制,即水面静止时的高度
        val oval = RectF(
            (mScreenWidth / 4).toFloat(), (mScreenHeight / 4).toFloat(),
            (mScreenWidth * 3 / 4).toFloat(), (mScreenHeight * 3 / 4).toFloat()
        )
        canvas.drawArc(oval, startAngle, sweepAngle, false, mWavePaint)
        if (c >= 8388607L) {
            c = 0L
        }
        // 每次onDraw时c都会自增
        c = (1L + c)
        val f1 = (mScreenHeight * (1.0f - (0.25f + mWaterLevel / 2))
                - mAmplitude)
        // 当前油量线的长度
        val waveWidth = Math.sqrt(
            (mScreenWidth * mScreenWidth / 16
                    - centerOffset * centerOffset).toDouble()
        ).toFloat()
        // 与圆半径的偏移量
        val offsetWidth = mScreenWidth / 4 - waveWidth
        val top = (f1 + mAmplitude).toInt()
        mPath.reset()
        // 起始振动X坐标，结束振动X坐标
        var startX: Int
        val endX: Int
        if (mWaterLevel > 0.50f) {
            startX = (mScreenWidth / 4 + offsetWidth).toInt()
            endX = (mScreenWidth / 2 + mScreenWidth / 4 - offsetWidth).toInt()
        } else {
            startX = (mScreenWidth / 4 + offsetWidth - mAmplitude).toInt()
            endX = (mScreenWidth / 2 + mScreenWidth / 4 - offsetWidth + mAmplitude).toInt()
        }
        // 波浪效果
        while (startX < endX) {
            val startY = (f1 - mAmplitude
                    * Math.sin(
                (Math.PI
                        * (2.0f * (startX + c * width * f))
                        / width)
            )).toInt()
            canvas.drawLine(startX.toFloat(), startY.toFloat(), startX.toFloat(), top.toFloat(), mWavePaint)
            startX++
        }
        canvas.drawCircle(
            (mScreenWidth / 2).toFloat(), (mScreenHeight / 2).toFloat(), ((mScreenWidth / 4
                    + mRingSTROKEWidth / 2).toFloat()), mRingPaint
        )
        canvas.drawCircle(
            (mScreenWidth / 2).toFloat(), (mScreenHeight / 2).toFloat(),
            (mScreenWidth / 4).toFloat(), mCirclePaint
        )
        canvas.restore()
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState: Parcelable? = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.progress = c.toInt()
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        super.onRestoreInstanceState(ss.getSuperState())
        c = ss.progress.toLong()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // 关闭硬件加速，防止异常unsupported operation exception
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    protected override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    /**
     * @category 开始波动
     */
    fun startWave() {
        if (!mStarted) {
            c = 0L
            mStarted = true
            mHandler.sendEmptyMessage(0)
        }
    }

    /**
     * @category 停止波动
     */
    fun stopWave() {
        if (mStarted) {
            c = 0L
            mStarted = false
            mHandler.removeMessages(0)
        }
    }

    /**
     * @category 保存状态
     */
    internal class SavedState : BaseSavedState {
        var progress = 0

        /**
         * Constructor called from [ProgressBar.onSaveInstanceState]
         */
        constructor(superState: Parcelable?) : super(superState) {}

        /**
         * Constructor called from [.CREATOR]
         */
        private constructor(`in`: Parcel) : super(`in`) {
            progress = `in`.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(progress)
        }

        companion object {
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState? {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}