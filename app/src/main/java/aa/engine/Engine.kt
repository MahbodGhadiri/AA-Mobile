package aa.engine

<<<<<<< HEAD
//import aa.engine.jobs.critical.MoveUpJob
=======

>>>>>>> master
import aa.engine.elements.MainCircle
import aa.engine.elements.SmallBall
import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.JobManager
<<<<<<< HEAD
import aa.engine.jobs.critical.CollisionJob
import aa.engine.jobs.critical.MoveUpJob
import aa.engine.jobs.critical.OrbitDetectionJob
import aa.engine.jobs.critical.OrbitingJob
import aa.engine.jobs.critical.SpawnJob
import aa.engine.jobs.optional.ChangingOrbitingDirectionJob
=======
import aa.engine.level.Level
import aa.engine.level.LevelManager
>>>>>>> master
import java.util.Timer
import kotlin.concurrent.timerTask

class Engine(
    mainCircle: MainCircle
) {
    private val jobManager: JobManager;
    private val levelManager: LevelManager;
    private val context = ExecutionContext(mainCircle);
    private var timer: Timer? = null;


    init {
        this.jobManager = JobManager(context);
<<<<<<< HEAD
        jobManager.addJobToMovingPeriod(OrbitingJob())
        jobManager.addJobToMovingPeriod(ChangingOrbitingDirectionJob())
        jobManager.addJobToMovingPeriod(MoveUpJob())
        jobManager.addJobToDetectionPeriod(OrbitDetectionJob())
        jobManager.addJobToTweakingPeriod(SpawnJob());
        jobManager.addJobToDetectionPeriod(CollisionJob())
=======
        this.levelManager = LevelManager(jobManager, context);
>>>>>>> master
    }

    public fun play(
        level: Level,
        smallBalls: ArrayList<SmallBall>,
        callback: () -> Unit
    ): Timer {
        val timer = Timer();
        this.timer = timer;
        context.setSmallBalls(smallBalls);
        levelManager.setup(level);
        timer.scheduleAtFixedRate(
            timerTask { jobManager.tick(); callback(); },
            0,
            10
        );
        return timer;
    }

    fun stop() {
        this.levelManager.clearLevel();
        this.timer?.cancel();
    }

    public fun getContext(): ExecutionContext {
        return this.context;
    }
}