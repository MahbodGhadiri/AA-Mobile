package aa.engine.level.levels

import aa.engine.level.Level

class Level3 : Level(3, 3, arrayOf(0F, 45F, 90F, 180F, 225F, 270F), 4) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = emptyArray();
    }
}