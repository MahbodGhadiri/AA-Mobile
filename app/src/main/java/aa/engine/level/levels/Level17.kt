package aa.engine.level.levels

import aa.engine.level.Level

class Level17 :
    Level(17, 7, arrayOf(0F, 50F, 100F, 150F, 200F, 250F, 300F), 3.9F) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = emptyArray()
    }
}