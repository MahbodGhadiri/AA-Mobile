package aa.engine.level

import aa.engine.config.AppConfig
import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.JobManager
import aa.engine.jobs.critical.CollisionJob
import aa.engine.jobs.critical.MoveUpJob
import aa.engine.jobs.critical.OrbitDetectionJob
import aa.engine.jobs.critical.OrbitingJob
import aa.engine.jobs.critical.SpawnJob
import aa.engine.jobs.critical.WinJob

class LevelManager(
    private var jobManager: JobManager,
    private var context: ExecutionContext
) {
    private var currentLevel: Level? = null;
    private fun setupCriticalJobs(
        onWinSound: () -> Unit,
        onCollisionSound: () -> Unit
    ) {
        context.setApproachingSpeed(AppConfig.getApproachingSpeed());
        jobManager.addJobToMovingPeriod(OrbitingJob());
        jobManager.addJobToMovingPeriod(MoveUpJob());
        jobManager.addJobToTweakingPeriod(SpawnJob());
        jobManager.addJobToDetectionPeriod(CollisionJob(onCollisionSound));
        jobManager.addJobToDetectionPeriod(WinJob(onWinSound));
        jobManager.addJobToDetectionPeriod(OrbitDetectionJob());
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

    public fun setup(
        level: Level,
        onWinSound: () -> Unit,
        onCollisionSound: () -> Unit
    ) {
        currentLevel = level;
        context.setRotationSpeed(currentLevel!!.getRotationSpeed());
        context.setSpawningSpeed(AppConfig.getSpawningSpeed());
        this.setupCriticalJobs(onWinSound, onCollisionSound);
        this.setupOptionalJobs();
    }

    public fun clearLevel() {
        currentLevel = null;
        this.jobManager.flushJobs();
        // this.context.flushBalls();
    }


}