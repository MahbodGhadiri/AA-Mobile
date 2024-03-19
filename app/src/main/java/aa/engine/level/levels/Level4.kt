package aa.engine.level.levels

import aa.engine.level.Level

class Level4 : Level(4, 4, arrayOf(0F, 70F, 150F, 250F), 3) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = emptyArray();
    }
}