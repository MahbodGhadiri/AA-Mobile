package aa.engine

import aa.engine.elements.MainCircle
import aa.engine.elements.SmallBall
import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.JobManager
import aa.engine.jobs.critical.OrbitingJob
import java.util.Timer
import kotlin.concurrent.timerTask

class Engine(mainCircle: MainCircle, smallBalls: ArrayList<SmallBall>) {
    val jobManager: JobManager;

    init {
        val context = ExecutionContext(mainCircle, smallBalls);
        context.setRotationSpeed(2);
        this.jobManager = JobManager(context);
        jobManager.addJobToMovingPeriod(OrbitingJob())
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
}