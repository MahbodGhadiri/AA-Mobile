package aa.engine.level.levels

import aa.engine.level.Level

class Level1 : Level(1, 5, arrayOf(20F, 50F, 70F, 90F), 10) {
    init {
        optionalTweakingJobs = emptyArray();
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
    }
}