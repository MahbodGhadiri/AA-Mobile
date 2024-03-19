package aa.engine.level.levels

import aa.engine.jobs.optional.ChangeOrbitingDirectionJob
import aa.engine.level.Level

class Level5 : Level(5, 5, arrayOf(0F, 40F, 80F, 120F), 3) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = arrayOf(ChangeOrbitingDirectionJob(300, 900));
    }
}