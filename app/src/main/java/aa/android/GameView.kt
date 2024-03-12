package aa.android

import aa.android.activities.GameOverActivity
import aa.android.activities.WinActivity
import aa.android.elements.AndroidLine
import aa.android.elements.AndroidMainCircle
import aa.android.elements.AndroidSmallBall
import aa.android.receiver.ChangeActivityReceiver
import aa.android.receiver.ReRenderReceiver
import aa.engine.Engine
import aa.engine.config.AppConfig
import aa.engine.config.EngineStatus
import aa.engine.elements.SmallBall
import aa.engine.elements.SmallBallStatus
import aa.engine.level.Level
import aa.engine.level.levels.Level1
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
        val reRenderIntent = Intent().setAction("reRender");
        val reRenderReceiver =
            ReRenderReceiver { invalidate(); requestLayout() }
        val changeActivityReceiver: ChangeActivityReceiver =
            ChangeActivityReceiver();

        lbm.registerReceiver(
            changeActivityReceiver,
            IntentFilter("changeActivity")
        );
        lbm.registerReceiver(reRenderReceiver, IntentFilter("reRender"))

        this.mainCircle = AndroidMainCircle();

        this.engine = Engine(mainCircle);
        //TODO: should be loaded by level activity -----
        val level = Level1();
        // ---------------------------------------------
        engine.play(level, generateSmallBalls(level)) {
            lbm.sendBroadcast(
                reRenderIntent
            )
        }

        this.setOnClickListener {
            val executionContext = engine.getContext();
            val ball = executionContext.getSpawnedBall();
            if (ball != null) {
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

    private fun generateSmallBalls(level: Level): ArrayList<SmallBall> {
        smallBalls.removeAll(smallBalls.toSet());
        System.out.println(level.getHiddenBallNum())
        for (i in 1..level.getHiddenBallNum()) {
            smallBalls.add(AndroidSmallBall())
        }
        smallBalls.add(AndroidSmallBall(SmallBallStatus.SPAWNING))
        for (theta in level.getSpinningBallsTheta()) {
            smallBalls.add(AndroidSmallBall(SmallBallStatus.SPINNING, theta));
        }
        return smallBalls as ArrayList<SmallBall>;
    }

    private fun handleGameOver() {
        // to stop the engine
        this.engine.stop();

        // going to GameOver activity
        val intent = Intent(context, GameOverActivity::class.java);
        context.startActivity(intent);
    }

    private fun handleWin() {
        // to stop the engine
        this.engine.stop();

        val intent = Intent(context, WinActivity::class.java);
        context.startActivity(intent);
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas);

        // checking if the app should stop or not
        if (AppConfig.getEngineStatus() == EngineStatus.GAMEOVER) this.handleGameOver();
        else if (AppConfig.getEngineStatus() == EngineStatus.WIN) this.handleWin();

        val mainCirclePosition = mainCircle.getPosition();
        for (smallBall in this.smallBalls) {
            val smallBallPosition = smallBall.getPosition()
            smallBall.calculateNewRectF();

//            if (smallBall.getStatus() == SmallBallStatus.HIDDEN) {
//                smallBall.setPosition(
//                    AppConfig.getScrWidth() / 2,
//                    AppConfig.getScrHeight()
//                );
//            }
            if (smallBall.getStatus() == SmallBallStatus.SPINNING) {
                this.line.draw(canvas, mainCirclePosition, smallBallPosition)
            }
            smallBall.draw(canvas);
        }

        mainCircle.draw(canvas);

    }

}