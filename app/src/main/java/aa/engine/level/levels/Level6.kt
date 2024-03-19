package aa.engine.level.levels

import aa.engine.jobs.optional.ChangeOrbitingDirectionJob
import aa.engine.level.Level

class Level6 : Level(5, 10, emptyArray(), 4) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = arrayOf(ChangeOrbitingDirectionJob(200, 1200));
    }
}