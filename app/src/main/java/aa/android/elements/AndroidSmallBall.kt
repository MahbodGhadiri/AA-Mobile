package aa.android.elements

import aa.engine.config.AppConfig
import aa.engine.elements.SmallBall
import aa.engine.elements.SmallBallStatus
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF

class AndroidSmallBall(
    status: SmallBallStatus = SmallBallStatus.HIDDEN,
    theta: Float = 90F,
    countNumber: Int = 0


) : SmallBall(status, theta) {

    private var countNumber: Int = 0
    private var left: Float = 0F;
    private var top: Float = 0F;
    private var right: Float = 0F;
    private var bottom: Float = 0F;
    private val rectF: RectF = RectF(left, top, right, bottom);
    private val textRect: Rect = Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt());
    private val ovalPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor(AppConfig.getGamePageElementsColor())
        style = Paint.Style.FILL

    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = AppConfig.getTextSize()

    }


    init {
        this.calculateNewRectF();
        this.countNumber = countNumber

    }

    public fun calculateNewRectF() {
        this.left =
            (this.getPosition().getX() - this.getRadius());
        this.top =
            (this.getPosition().getY() - this.getRadius());
        this.right =
            (this.getPosition().getX() + this.getRadius());
        this.bottom =
            (this.getPosition().getY() + this.getRadius());

        rectF.top = top;
        rectF.left = left;
        rectF.right = right;
        rectF.bottom = bottom;

        textPaint.getTextBounds(
            countNumber.toString(),
            0,
            countNumber.toString().length,
            textRect
        );
    }

    public fun getRectF(): RectF {
        return this.rectF;
    }

    public fun getPaint(): Paint {
        return this.ovalPaint;
    }

    public fun draw(canvas: Canvas) {
        this.calculateNewRectF();


        canvas.drawOval(rectF, ovalPaint);
        if (countNumber != 0) {
            canvas.drawText(
                countNumber.toString(),
                this.getPosition().getX() - (textRect.width() / 2),
                this.getPosition().getY() + (textRect.height() / 2),
                textPaint
            )
        }

    }
}