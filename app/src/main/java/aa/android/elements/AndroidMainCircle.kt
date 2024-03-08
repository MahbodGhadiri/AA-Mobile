package aa.android.elements

import aa.engine.elements.MainCircle
import aa.engine.helpers.Position
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

public class AndroidMainCircle(width: Float, height: Float) : MainCircle(
    Position(
        width / 2,
        height / 3
    ), width / 6, width / 3
) {
    private var rectF: RectF;
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GREEN
        style = Paint.Style.FILL
    }

    init {
        val left =
            this.getPosition().getX() - this.getRadius();
        val top =
            this.getPosition().getY() - this.getRadius();
        val right =
            this.getPosition().getX() + this.getRadius();
        val bottom =
            this.getPosition().getY() + this.getRadius();

        this.rectF = RectF(
            left,
            top,
            right,
            bottom
        )
    }

    public fun getRectF(): RectF {
        return this.rectF;
    }

    public fun getPaint(): Paint {
        return this.paint;
    }
}