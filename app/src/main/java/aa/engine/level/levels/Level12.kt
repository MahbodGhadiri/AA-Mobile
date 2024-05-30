package aa.engine.level.levels

import aa.engine.jobs.optional.ChangeOrbitingDirectionJob
import aa.engine.level.Level

class Level12 : Level(
    12,
    3,
    arrayOf(
        15F,
        30F,
        45F,
        105F,
        120F,
        145F,
        185F,
        200F,
        215F,
        285F,
        300F,
        315F
    ),
    4.2F
) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = arrayOf(ChangeOrbitingDirectionJob(400, 1000));
    }
}