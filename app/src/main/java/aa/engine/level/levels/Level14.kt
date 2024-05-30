package aa.engine.level.levels

import aa.engine.jobs.optional.ChangeOrbitingDirectionJob
import aa.engine.jobs.optional.ChangeSpeedJob
import aa.engine.level.Level

class Level14 :
    Level(
        14,
        4,
        arrayOf(0F, 40F, 80F, 120F, 160F, 200F, 240F, 280F, 320F),
        3.7F
    ) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = arrayOf(
            ChangeOrbitingDirectionJob(200, 1200),
            ChangeSpeedJob(4F, 3.7F, 200, 1200)
        );
    }
}