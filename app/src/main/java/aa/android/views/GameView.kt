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
    val preferences =
        context.applicationContext.getSharedPreferences(
            resources.getString(
                R.string.preferences
            ), Context.MODE_PRIVATE
        )

    init {
        AppConfig.initialize(
            context.resources.displayMetrics.widthPixels.toFloat(),
            context.resources.displayMetrics.heightPixels.toFloat()
        );

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


    override fun onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.isFirstTime) {
            this.initializeEngine();
            AppConfig.setEngineStatus(EngineStatus.RUNNING);
        }
    }


    private fun initializeEngine() {
        this.mainCircle = AndroidMainCircle();
        val currentLevel =
            preferences.getString(
                resources.getString(R.string.current_level),
                "1"
            )


        this.engine = Engine(mainCircle);
        //TODO: should be loaded by level activity -----
        val level = Class.forName("aa.engine.level.levels.Level" + currentLevel)
            .getDeclaredConstructor().newInstance() as Level;
        // ---------------------------------------------
        engine.play(level, generateSmallBalls(level)) {
            this.lbm.sendBroadcast(
                this.reRenderIntent
            )
        }

        this.setOnClickListener {
            if (AppConfig.getEngineStatus() == EngineStatus.WIN) {
                val highestFinishedLevel = preferences.getString(
                    resources.getString(R.string.highest_completed_level),
                    "0"
                )
                System.out.println(highestFinishedLevel)
                System.out.println(currentLevel)
                if (currentLevel != null && highestFinishedLevel != null) {
                    if (currentLevel.toInt() > highestFinishedLevel.toInt()) {
                        System.out.println("updated value")
                        with(preferences.edit()) {
                            putString(
                                resources.getString(R.string.highest_completed_level),
                                currentLevel
                            )
                            apply()
                        }
                    }


                }
                val intent = Intent(context, WinActivity::class.java);
                context.startActivity(intent);
                this.engine.stop();
                AppConfig.setEngineStatus(EngineStatus.READY);
                this.setBackgroundColor(resources.getColor(R.color.background));
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
            smallBalls.add(AndroidSmallBall()); }
        smallBalls.add(AndroidSmallBall(SmallBallStatus.SPAWNING))
        for (theta in level.getSpinningBallsTheta()) {
            smallBalls.add(AndroidSmallBall(SmallBallStatus.SPINNING, theta));
        }
        return smallBalls as ArrayList<SmallBall>;
    }

    private fun handleGameOver() {
        this.engine.stop();
        this.setBackgroundColor(resources.getColor(R.color.danger));

        (this.context as GameActivity).showGameOverButtons();
    }


    @SuppressLint("DrawAllocation", "ResourceAsColor", "Range")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas);

        // checking if the app should stop or not
        if (AppConfig.getEngineStatus() == EngineStatus.GAMEOVER) {
            this.handleGameOver();
        };
        else if (AppConfig.getEngineStatus() == EngineStatus.WIN) {
            this.setBackgroundColor(resources.getColor(R.color.success));
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

            smallBall.draw(canvas);
        }

        mainCircle.draw(canvas);

    }

}