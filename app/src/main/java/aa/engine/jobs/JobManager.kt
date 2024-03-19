package aa.engine.jobs

import aa.engine.config.AppConfig
import aa.engine.config.EngineStatus
import aa.engine.helpers.ExecutionContext

class JobManager(context: ExecutionContext) {
    private val tweakingPeriod = ArrayList<Job>();
    private val movingPeriod = ArrayList<Job>();
    private val detectionPeriod = ArrayList<Job>();
    private val context: ExecutionContext;

    init {
        this.context = context;
    }

    fun addJobToTweakingPeriod(job: Job) {
        tweakingPeriod.add(job);
    }

    fun addJobToMovingPeriod(job: Job) {
        movingPeriod.add(job);
    }

    fun addJobToDetectionPeriod(job: Job) {
        detectionPeriod.add(job);
    }

    fun flushJobs() {
        tweakingPeriod.removeAll(tweakingPeriod.toSet());
        movingPeriod.removeAll(movingPeriod.toSet());
        detectionPeriod.removeAll(detectionPeriod.toSet());
    }

    public fun tick() {
        val tweakingIterator = tweakingPeriod.iterator();
        while (tweakingIterator.hasNext()) {
            val job = tweakingIterator.next();
            if (job.shouldExit()) {
                tweakingIterator.remove();
                continue;
            }
            job.execute(context);
        }

        val movingIterator = movingPeriod.iterator();
        while (movingIterator.hasNext()) {
            val job = movingIterator.next();
            if (job.shouldExit()) {
                movingIterator.remove();
                continue;
            }
            job.execute(context);
        }

        if (AppConfig.getEngineStatus() != EngineStatus.RUNNING) return;

        val detectionIterator = detectionPeriod.iterator();
        while (detectionIterator.hasNext()) {
            val job = detectionIterator.next();
            if (job.shouldExit()) {
                detectionIterator.remove();
                continue;
            }
            job.execute(context);
        }
    }
}