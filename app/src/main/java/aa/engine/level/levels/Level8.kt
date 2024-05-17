package aa.engine.level.levels

import aa.engine.level.Level

class Level8 : Level(
    8,
    3,
    arrayOf(0F, 30F, 90F, 120F, 180F, 210F, 270F, 300F),
    3.4F
) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = emptyArray();
    }
}