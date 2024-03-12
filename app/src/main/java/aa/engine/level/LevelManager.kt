package aa.engine.level

import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.JobManager
import aa.engine.jobs.critical.CollisionJob
import aa.engine.jobs.critical.MoveUpJob
import aa.engine.jobs.critical.OrbitDetectionJob
import aa.engine.jobs.critical.OrbitingJob
import aa.engine.jobs.critical.SpawnJob

class LevelManager(
    private var jobManager: JobManager,
    private var context: ExecutionContext
) {
    private var currentLevel: Level? = null;
    private fun setupCriticalJobs() {
        context.setRotationSpeed(2);
        context.setApproachingSpeed(25);
        jobManager.addJobToMovingPeriod(OrbitingJob());
        jobManager.addJobToMovingPeriod(MoveUpJob());
        jobManager.addJobToDetectionPeriod(OrbitDetectionJob());
        jobManager.addJobToTweakingPeriod(SpawnJob());
        jobManager.addJobToDetectionPeriod(CollisionJob());
    }

    private fun setupOptionalJobs() {
        for (job in currentLevel!!.optionalTweakingJobs) {
            jobManager.addJobToTweakingPeriod(job)
        }
        for (job in currentLevel!!.optionalMovingJobs) {
            jobManager.addJobToMovingPeriod(job)
        }
        for (job in currentLevel!!.optionalDetectionJobs) {
            jobManager.addJobToDetectionPeriod(job)
        }
    }

    public fun setup(level: Level) {
        currentLevel = level;
        context.setRotationSpeed(currentLevel!!.getRotationSpeed())
        this.setupCriticalJobs();
        this.setupOptionalJobs();
    }

    public fun clearLevel() {
        currentLevel = null;
        this.jobManager.flushJobs();
        this.context.flushBalls();
    }

}