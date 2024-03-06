package aa.android

import aa.android.elements.AndroidMainCircle
import aa.android.elements.AndroidSmallBall
import aa.engine.elements.SmallBallStatus
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View


public class GameView(context: Context, attrs: AttributeSet) :
    View(context, attrs) {
    private val mainCircle: AndroidMainCircle;
    private var smallBalls = arrayOf<AndroidSmallBall>();
    private var index = 0;

    init {
        this.setOnClickListener {
            if (this.smallBalls.size > index) {
                smallBalls[index].setStatus(SmallBallStatus.APPROACHING);
                index++;
            }
        }

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels

        this.mainCircle = AndroidMainCircle(width, height);
        this.smallBalls += AndroidSmallBall(width, height);

        invalidate();
        requestLayout();
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawOval(
            this.mainCircle.getRectF(),
            this.mainCircle.getPaint()
        )

        for (smallBall in this.smallBalls) {
            canvas.drawOval(smallBall.getRectF(), smallBall.getPaint())
        }

    }


}