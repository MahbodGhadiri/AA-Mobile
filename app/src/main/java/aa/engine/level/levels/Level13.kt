package aa.engine.level.levels

import aa.engine.level.Level

class Level13 : Level(
    13,
    5,
    arrayOf(0F, 20F, 40F, 60F, 80F, 180F, 200F, 220F, 240F, 260F),
    3.8F
) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = emptyArray();
    }
}