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
    private val engine: Engine;


    init {
        AppConfig.initialize(context);
        val lbm = LocalBroadcastManager.getInstance(this.context);
        val intent = Intent().setAction("reRender");
        val receiver = ReRenderReceiver { invalidate(); requestLayout() }
        lbm.registerReceiver(receiver, IntentFilter("reRender"))

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
        this.smallBalls[1].setStatus(SmallBallStatus.SPAWNED);
        index++;

        //TODO: -----------------------------

        this.engine = Engine(mainCircle, smallBalls as ArrayList<SmallBall>);
        engine.play {
            lbm.sendBroadcast(intent);
        }
        
        this.setOnClickListener {
            val executionContext = engine.getContext();
            val ball = executionContext.getSpawnedBall();
            if (ball != null) {
                println("called, approaching:: " + executionContext.getApproachingBalls());
                executionContext.addApproachingBall(ball);
                executionContext.setSpawnedBall(null);

                val hiddenBall = executionContext.getAndPopHiddenBall();
                if (hiddenBall != null) executionContext.setSpawningBall(
                    hiddenBall
                ) else {
                    executionContext.setSpawningBall(null);
                }

            }
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
            smallBall.draw(canvas);
        }

        mainCircle.draw(canvas);

    }


}