package aa.engine.level.levels

import aa.engine.level.Level

class Level2 : Level(2, 3, arrayOf(0F, 90F, 180F, 270F), 7) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = emptyArray();
    }
}