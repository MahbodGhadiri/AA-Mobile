package aa.android

import aa.android.elements.AndroidMainCircle
import aa.android.elements.AndroidSmallBall
import aa.android.receiver.ReRenderReceiver
import aa.engine.Engine
import aa.engine.elements.SmallBall
import aa.engine.elements.SmallBallStatus
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager


public class GameView(context: Context, attrs: AttributeSet) :
    View(context, attrs) {
    private val mainCircle: AndroidMainCircle;
    private var smallBalls = ArrayList<AndroidSmallBall>();
    private var index = 0;
    private val engine: Engine;

    init {
        val lbm = LocalBroadcastManager.getInstance(this.context);
        val intent = Intent().setAction("reRender");
        val receiver = ReRenderReceiver { invalidate(); requestLayout() }
        lbm.registerReceiver(receiver, IntentFilter("reRender"))

        this.setOnClickListener {
            if (this.smallBalls.size > index) {
                smallBalls[index].setStatus(SmallBallStatus.APPROACHING);
                index++;
                System.out.println("clicked")
            }
        }

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels

        //TODO: for test, should be moved in future
        this.mainCircle = AndroidMainCircle(width, height);
        this.smallBalls.add(AndroidSmallBall(width, height, mainCircle));
        this.smallBalls.add(AndroidSmallBall(width, height, mainCircle));
        this.smallBalls[0].setStatus(SmallBallStatus.SPINNING);
        index++;
        //TODO: -----------------------------

        this.engine = Engine(mainCircle, smallBalls as ArrayList<SmallBall>);
        engine.play {
            lbm.sendBroadcast(intent);
        }

    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawOval(
            this.mainCircle.getRectF(),
            this.mainCircle.getPaint()
        )


        for (smallBall in this.smallBalls) {

            smallBall.calculateNewRectF()

            canvas.drawOval(smallBall.getRectF(), smallBall.getPaint())
        }

    }


}