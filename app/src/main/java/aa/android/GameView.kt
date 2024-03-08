package aa.android

import AppConfig
import aa.android.elements.AndroidLine
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
    private val smallBalls = ArrayList<AndroidSmallBall>();
    private val line = AndroidLine();
    private var index = 0;
    private val engine: Engine;


    init {
        AppConfig.initialize(context);
        val lbm = LocalBroadcastManager.getInstance(this.context);
        val intent = Intent().setAction("reRender");
        val receiver = ReRenderReceiver { invalidate(); requestLayout() }
        lbm.registerReceiver(receiver, IntentFilter("reRender"))

        var hiddenChecked: Boolean = false;
        var spawningChecked: Boolean = false;
        this.setOnClickListener {
            for (smallBall in smallBalls) {
                if (smallBall.getStatus() == SmallBallStatus.SPAWNED && !spawningChecked) {
                    smallBall.setStatus(SmallBallStatus.APPROACHING);
                    spawningChecked = true;
                }
                if (smallBall.getStatus() == SmallBallStatus.HIDDEN && !hiddenChecked) {
                    smallBall.setStatus(SmallBallStatus.SPAWNING);
                    hiddenChecked = true;
                }
                println(smallBall.getStatus());

                if (hiddenChecked && spawningChecked) {
                    hiddenChecked = false;
                    spawningChecked = false;
                    break;
                }
            }
            hiddenChecked = false;
            spawningChecked = false;
        }

        val width = resources.displayMetrics.widthPixels.toFloat()
        val height = resources.displayMetrics.heightPixels.toFloat()

        //TODO: for test, should be moved in future
        this.mainCircle = AndroidMainCircle(width, height);
        this.smallBalls.add(AndroidSmallBall(width, height, mainCircle));
        this.smallBalls.add(AndroidSmallBall(width, height, mainCircle));
        this.smallBalls.add(AndroidSmallBall(width, height, mainCircle));
        this.smallBalls.add(AndroidSmallBall(width, height, mainCircle));
        this.smallBalls.add(AndroidSmallBall(width, height, mainCircle));
        this.smallBalls.add(AndroidSmallBall(width, height, mainCircle));
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
        val mainCirclePosition = mainCircle.getPosition();
        for (smallBall in this.smallBalls) {
            val smallBallPosition = smallBall.getPosition()
            smallBall.calculateNewRectF();

            if (smallBall.getStatus() == SmallBallStatus.HIDDEN) {
                smallBall.setPosition(
                    AppConfig.getScrWidth() / 2,
                    AppConfig.getScrHeight()
                );
            }

            if (smallBall.getStatus() == SmallBallStatus.SPINNING) {
                this.line.draw(canvas, mainCirclePosition, smallBallPosition)
            }

            canvas.drawOval(smallBall.getRectF(), smallBall.getPaint())
        }

        canvas.drawOval(
            this.mainCircle.getRectF(),
            this.mainCircle.getPaint()
        )

    }


}