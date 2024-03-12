package aa.engine.level

import aa.engine.jobs.Job

abstract class Level(
    private val levelNo: Int,
    private val hiddenBallNum: Int,
    private val spinningBallsTheta: Array<Float>,
    private val rotationSpeed: Int
) {
    lateinit var optionalMovingJobs: Array<Job>;
    lateinit var optionalTweakingJobs: Array<Job>;
    lateinit var optionalDetectionJobs: Array<Job>;

    fun getLevelNo(): Int = levelNo;
    fun getHiddenBallNum(): Int = hiddenBallNum;
    fun getSpinningBallsTheta(): Array<Float> = spinningBallsTheta;
    fun getRotationSpeed(): Int = rotationSpeed;
}