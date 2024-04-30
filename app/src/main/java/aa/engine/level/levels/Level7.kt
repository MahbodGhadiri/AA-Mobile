package aa.engine.level.levels

import aa.engine.level.Level

class Level7 : Level(7, 6, arrayOf(0F, 63F, 123F, 184F, 288F), 3.5F) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = emptyArray();
    }
}