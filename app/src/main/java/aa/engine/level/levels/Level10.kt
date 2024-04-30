package aa.engine.level.levels

import aa.engine.jobs.optional.ChangeSpeedJob
import aa.engine.level.Level

class Level10 :
    Level(10, 4, arrayOf(22F, 66F, 110F, 154F, 198F, 270F, 290F), 3.9F) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = arrayOf(ChangeSpeedJob(5F, 2F, 100, 500));
    }
}