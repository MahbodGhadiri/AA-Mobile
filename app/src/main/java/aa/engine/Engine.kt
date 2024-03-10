package aa.engine

import aa.engine.elements.MainCircle
import aa.engine.elements.SmallBall
import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.JobManager
import aa.engine.jobs.critical.OrbitDetectionJob
import aa.engine.jobs.critical.OrbitingJob
import aa.engine.jobs.critical.SpawnJob
import java.util.Timer
import kotlin.concurrent.timerTask

class Engine(mainCircle: MainCircle, smallBalls: ArrayList<SmallBall>) {
    val jobManager: JobManager;
    private val context = ExecutionContext(mainCircle, smallBalls);

    init {
        context.setRotationSpeed(2);
        context.setApproachingSpeed(25);
        this.jobManager = JobManager(context);
        jobManager.addJobToMovingPeriod(OrbitingJob())
//        jobManager.addJobToMovingPeriod(MoveUpJob())
        jobManager.addJobToDetectionPeriod(OrbitDetectionJob())
        jobManager.addJobToTweakingPeriod(SpawnJob());
    }

    public fun play(callback: () -> Unit): Timer {
        val timer = Timer();
        timer.scheduleAtFixedRate(
            timerTask { jobManager.tick(); callback(); },
            0,
            10
        );
        return timer;
    }

    public fun getContext(): ExecutionContext {
        return this.context;
    }
}