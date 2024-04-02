package aa.engine.jobs.critical

import aa.engine.config.AppConfig
import aa.engine.config.EngineStatus
import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.Job

class WinJob(private val onWinSound: () -> Unit) : Job() {
    override fun run(context: ExecutionContext) {
        if ( // I know, this is not best practice.
            context.getHiddenBalls().size == 0 &&
            context.getSpawningBall() == null &&
            context.getSpawnedBall() == null &&
            context.getApproachingBalls().size == 0
        ) {
            context.setRotationSpeed(0F);
            AppConfig.setEngineStatus(EngineStatus.WIN);

            if (AppConfig.hasSoundEffects()) {
                onWinSound
            }
        }
    }
}