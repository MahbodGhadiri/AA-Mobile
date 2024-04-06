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
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager

public class GameView(context: Context, attrs: AttributeSet) :
    View(context, attrs) {
    private var originalOrbit: Float = 0F;
    private lateinit var mainCircle: AndroidMainCircle;
    private val smallBalls = ArrayList<AndroidSmallBall>();
    private val line = AndroidLine();
    private lateinit var engine: Engine;
    private var lbm: LocalBroadcastManager;
    private var reRenderReceiver: ReRenderReceiver;
    private var reRenderIntent: Intent;
    private var isFirstTime: Boolean = true;
    private val winSoundPlayer: MediaPlayer;
    private val loseSoundPlayer: MediaPlayer;
    private val popSoundFile: Int;
    private var winProcessStarted: Boolean = false;

    private val preferences =
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

        if (originalOrbit == 0F) {
            originalOrbit = AppConfig.getMainCircleOrbit();
        }

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

        this.winSoundPlayer = MediaPlayer.create(this.context, R.raw.gamewin)
        this.loseSoundPlayer = MediaPlayer.create(this.context, R.raw.gameover)
        this.popSoundFile = R.raw.popsound8

        this.initializeEngine();
        this.isFirstTime = false;
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!this.isFirstTime) {
            this.initializeEngine();
            AppConfig.setEngineStatus(EngineStatus.RUNNING);
        }
    }


    private fun initializeEngine() {
        val hasSoundEffects = preferences.getBoolean(
            resources.getString(R.string.setting_sound_effects_preferences),
            true
        )
        AppConfig.setSoundEffectsStatus(hasSoundEffects)

        this.mainCircle = AndroidMainCircle();
        val currentLevel =
            preferences.getString(
                resources.getString(R.string.current_level),
                "1"
            )


        this.engine = Engine(mainCircle);

        val level = Class.forName("aa.engine.level.levels.Level" + currentLevel)
            .getDeclaredConstructor().newInstance() as Level;

        engine.play(level, generateSmallBalls(level)) {
            this.lbm.sendBroadcast(
                this.reRenderIntent
            )
        }

        this.setOnClickListener {
            if (AppConfig.getEngineStatus() == EngineStatus.WIN) {
                this.handleLevelWin();
            }
            if (AppConfig.getScreenClickable()) {
                val executionContext = engine.getContext();
                val ball = executionContext.getSpawnedBall();
                if (ball != null) {
                    if (AppConfig.hasSoundEffects()) {
                        (ball as AndroidSmallBall).playPopSound()
                    }
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
    }

    private fun handleLevelWin() {
        val intent = Intent(context, WinActivity::class.java);
        context.startActivity(intent);
        AppConfig.setEngineStatus(EngineStatus.READY);

        val currentLevel =
            preferences.getString(
                resources.getString(R.string.current_level),
                "1"
            )

        val highestFinishedLevel = preferences.getString(
            resources.getString(R.string.highest_completed_level),
            "0"
        )
        if (currentLevel != null && highestFinishedLevel != null) {
            if (currentLevel.toInt() > highestFinishedLevel.toInt()) {
                with(preferences.edit()) {
                    putString(
                        resources.getString(R.string.highest_completed_level),
                        currentLevel
                    )
                    apply()
                }
            }
        }

        engine.stop();
        setBackgroundColor(resources.getColor(R.color.background));
    }

    private fun generateSmallBalls(level: Level): ArrayList<SmallBall> {
        smallBalls.removeAll(smallBalls.toSet());
        for (i in 1..level.getInitialHiddenBallNum()) {
            smallBalls.add(AndroidSmallBall(number = i));
        }
        smallBalls.add(
            AndroidSmallBall(
                SmallBallStatus.SPAWNING,
                number = level.getInitialHiddenBallNum() + 1
            )
        )

        for (ball in smallBalls) {
            ball.setPopSound(
                MediaPlayer.create(
                    this.context,
                    this.popSoundFile
                )
            )
        }

        for (theta in level.getSpinningBallsTheta()) {
            smallBalls.add(AndroidSmallBall(SmallBallStatus.SPINNING, theta));
        }
        return smallBalls as ArrayList<SmallBall>;
    }

    private fun handleGameOver() {
        if (!this.loseSoundPlayer.isPlaying && AppConfig.hasSoundEffects()) {
            this.loseSoundPlayer.start();
        }
        this.engine.stop();
        this.setBackgroundColor(resources.getColor(R.color.danger));

        (this.context as GameActivity).showGameOverButtons();
    }


    @SuppressLint("DrawAllocation", "ResourceAsColor", "Range")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas);
        if (AppConfig.getEngineStatus() == EngineStatus.CLEANED_UP) {

        }
        // checking if the app should stop or not
        else if (AppConfig.getEngineStatus() == EngineStatus.GAMEOVER) {
            this.handleGameOver();
            AppConfig.setEngineStatus(EngineStatus.CLEANED_UP)
        };
        else if (AppConfig.getEngineStatus() == EngineStatus.WIN) {
            if (!this.winSoundPlayer.isPlaying && AppConfig.hasSoundEffects() && !this.winProcessStarted) {
                this.winSoundPlayer.start()
                this.winProcessStarted = true;
            };

            if (AppConfig.getMainCircleOrbit() < AppConfig.getScrWidth()) {
                AppConfig.setMainCircleOrbit(AppConfig.getMainCircleOrbit() + 5);
                this.setBackgroundColor(resources.getColor(R.color.success));
            } else {
                handleLevelWin();
                this.winSoundPlayer.stop();
                this.winSoundPlayer.release();
            }
        }
//        else if (AppConfig.getEngineStatus() == EngineStatus.READY) {
//            // do nothing
//        }

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