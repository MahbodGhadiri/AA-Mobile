package aa.engine.level.levels

import aa.engine.jobs.optional.ChangeSpeedJob
import aa.engine.level.Level

class Level15 :
    Level(15, 9, arrayOf(20F, 40F, 120F, 140F, 220F, 240F, 320F, 340F), 2.5F) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = arrayOf(ChangeSpeedJob(5F, 2F, 500, 1500));
    }
}