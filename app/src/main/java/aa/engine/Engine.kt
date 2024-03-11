package aa.engine

import aa.engine.elements.MainCircle
import aa.engine.elements.SmallBall
import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.JobManager
import aa.engine.jobs.critical.CollisionJob
//import aa.engine.jobs.critical.MoveUpJob
import aa.engine.jobs.critical.OrbitDetectionJob
import aa.engine.jobs.critical.OrbitingJob
import aa.engine.jobs.critical.SpawnJob
import java.util.Timer
import kotlin.concurrent.timerTask

class Engine(mainCircle: MainCircle, smallBalls: ArrayList<SmallBall>) {
    private val jobManager: JobManager;
    private val context = ExecutionContext(mainCircle, smallBalls);
    private var timer: Timer? = null;


    init {
        context.setRotationSpeed(2);
        context.setApproachingSpeed(25);
        this.jobManager = JobManager(context);
        jobManager.addJobToMovingPeriod(OrbitingJob())
//        jobManager.addJobToMovingPeriod(MoveUpJob())
        jobManager.addJobToDetectionPeriod(OrbitDetectionJob())
        jobManager.addJobToTweakingPeriod(SpawnJob());
        jobManager.addJobToDetectionPeriod(CollisionJob())
    }

    public fun play(callback: () -> Unit): Timer {
        val timer = Timer();
        this.timer = timer;
        timer.scheduleAtFixedRate(
            timerTask { jobManager.tick(); callback(); },
            0,
            10
        );
        return timer;
    }

    fun stop() {
        this.timer?.cancel();
    }

    public fun getContext(): ExecutionContext {
        return this.context;
    }
}