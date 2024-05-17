package aa.engine.level.levels

import aa.engine.jobs.optional.ChangeSpeedJob
import aa.engine.level.Level

class Level11 :
    Level(
        11,
        5,
        arrayOf(25F, 75F, 100F, 120F, 140F, 165F, 215F, 285F, 300F, 315F),
        3.4F
    ) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = arrayOf(ChangeSpeedJob(4F, 2.5F, 100, 200));
    }
}