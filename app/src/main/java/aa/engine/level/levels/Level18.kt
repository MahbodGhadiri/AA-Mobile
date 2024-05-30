package aa.engine.level.levels

import aa.engine.jobs.optional.ChangeOrbitingDirectionJob
import aa.engine.level.Level

class Level18 : Level(18, 19, emptyArray(), 2.7F) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = arrayOf(ChangeOrbitingDirectionJob(200, 700));
    }
}