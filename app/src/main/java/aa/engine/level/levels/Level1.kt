package aa.engine.level.levels

import aa.engine.jobs.optional.ChangeOrbitingDirectionJob
import aa.engine.level.Level

class Level1 : Level(1, 4, arrayOf(20F, 50F, 70F, 90F), 10) {
    init {
        optionalTweakingJobs = arrayOf(ChangeOrbitingDirectionJob(300, 900));
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
    }
}