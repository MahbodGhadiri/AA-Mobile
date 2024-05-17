package aa.engine.level.levels

import aa.engine.level.Level

class Level16 : Level(
    16,
    3,
    arrayOf(0F, 40F, 60F, 100F, 120F, 160F, 200F, 220F, 240F, 260F, 300F),
    3.5F
) {
    init {
        optionalMovingJobs = emptyArray();
        optionalDetectionJobs = emptyArray();
        optionalTweakingJobs = emptyArray();
    }
}