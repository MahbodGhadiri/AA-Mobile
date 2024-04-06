package aa.engine

//import aa.engine.jobs.critical.MoveUpJob

import aa.engine.elements.MainCircle
import aa.engine.elements.SmallBall
import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.JobManager
import aa.engine.level.Level
import aa.engine.level.LevelManager
import java.util.Timer
import kotlin.concurrent.timerTask

class Engine(
    mainCircle: MainCircle
) {
    private val jobManager: JobManager;
    private val levelManager: LevelManager;
    private val context = ExecutionContext(mainCircle);
    private lateinit var timer: Timer;


    init {
        this.jobManager = JobManager(context);
        this.levelManager = LevelManager(jobManager, context);
    }

    public fun play(
        level: Level,
        smallBalls: ArrayList<SmallBall>,
        callback: () -> Unit
    ): Timer {
        timer = Timer();
        context.setSmallBalls(smallBalls);
        levelManager.setup(level);
        timer.scheduleAtFixedRate(
            timerTask { jobManager.tick(); callback(); },
            0,
            15
        );
        return timer;
    }

    fun stop() {
        timer.cancel();
        this.levelManager.clearLevel();
    }

    public fun getContext(): ExecutionContext {
        return this.context;
    }
}