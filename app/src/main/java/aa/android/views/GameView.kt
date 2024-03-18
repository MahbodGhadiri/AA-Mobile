package aa.android.views

import aa.android.R
import aa.android.activities.GameActivity
import aa.android.activities.WinActivity
import aa.android.elements.AndroidLine
import aa.android.elements.AndroidMainCircle
import aa.android.elements.AndroidSmallBall
import aa.android.receivers.ChangeActivityReceiver
import aa.android.receivers.ReRenderReceiver
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
    private lateinit var mainCircle: AndroidMainCircle;
    private val smallBalls = ArrayList<AndroidSmallBall>();
    private val line = AndroidLine();
    private lateinit var engine: Engine;
    private var lbm: LocalBroadcastManager;
    private var reRenderReceiver: ReRenderReceiver;
    private var reRenderIntent: Intent;
    private var isFirstTime: Boolean = true;
    private var activityContext: Context? = null
    private var gameView: GameView? = null


    init {
        AppConfig.initialize(context);
        this.gameView = findViewById(R.id.GameView);
        this.lbm = LocalBroadcastManager.getInstance(this.context);
        this.reRenderIntent = Intent().setAction("reRender");
        this.reRenderReceiver =
            ReRenderReceiver { invalidate(); requestLayout() }
        val changeActivityReceiver: ChangeActivityReceiver =
            ChangeActivityReceiver();

        this.lbm.registerReceiver(
            changeActivityReceiver,
            IntentFilter("changeActivity")
        );
        this.lbm.registerReceiver(
            this.reRenderReceiver,
            IntentFilter("reRender")
        )


        this.initializeEngine();
        this.isFirstTime = false;
    }


    fun setActivity(context: Context) {
        this.activityContext = context
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.isFirstTime) {
            this.initializeEngine();
            AppConfig.setEngineStatus(EngineStatus.RUNNING);
        }
    }


    private fun initializeEngine() {
        this.mainCircle = AndroidMainCircle();

        this.engine = Engine(mainCircle);
        //TODO: should be loaded by level activity -----
        val level = Level1();
        // ---------------------------------------------
        engine.play(level, generateSmallBalls(level)) {
            this.lbm.sendBroadcast(
                this.reRenderIntent
            )
        }

        this.setOnClickListener {
            if (AppConfig.getEngineStatus() == EngineStatus.WIN) {
                val intent = Intent(context, WinActivity::class.java);
                context.startActivity(intent);
                this.engine.stop();
                AppConfig.setEngineStatus(EngineStatus.READY);
                this.gameView?.setBackgroundColor(resources.getColor(R.color.background));
            }
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
        this.engine.stop();
        this.gameView?.setBackgroundColor(resources.getColor(R.color.danger));

        (this.activityContext as GameActivity).showGameOverButtons();
    }


    @SuppressLint("DrawAllocation", "ResourceAsColor", "Range")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas);

        // checking if the app should stop or not
        if (AppConfig.getEngineStatus() == EngineStatus.GAMEOVER) {
            this.handleGameOver();
        };
        else if (AppConfig.getEngineStatus() == EngineStatus.WIN) {
            this.gameView?.setBackgroundColor(resources.getColor(R.color.success));
        } else if (AppConfig.getEngineStatus() == EngineStatus.READY) {
            return;
        }

        val mainCirclePosition = mainCircle.getPosition();
        for (smallBall in this.smallBalls) {
            val smallBallPosition = smallBall.getPosition()
            smallBall.calculateNewRectF();

            if (smallBall.getStatus() == SmallBallStatus.SPINNING) {
                this.line.draw(canvas, mainCirclePosition, smallBallPosition)
            }

            // if game over
            if (AppConfig.getEngineStatus() == EngineStatus.GAMEOVER) {
                if (smallBall.getStatus() == SmallBallStatus.SPAWNED || smallBall.getStatus() == SmallBallStatus.SPAWNING) {
                    continue;
                }
            }

            smallBall.draw(canvas);
        }

        mainCircle.draw(canvas);

    }

}