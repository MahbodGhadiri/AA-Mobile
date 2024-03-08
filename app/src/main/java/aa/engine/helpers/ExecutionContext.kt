package aa.engine.helpers

import aa.engine.elements.MainCircle
import aa.engine.elements.SmallBall
import aa.engine.elements.SmallBallStatus

class ExecutionContext(
    private val mainCircle: MainCircle,
    smallBalls: ArrayList<SmallBall>
) {
    private var rotationSpeed: Int = 0;
    private var approachingSpeed: Int = 0;

    private val hiddenBalls = ArrayList<SmallBall>();
    private var spawnedBall: SmallBall? = null;
    private val spinningBalls = ArrayList<SmallBall>();
    private val approachingBalls = ArrayList<SmallBall>();

    init {
        for (smallBall in smallBalls) {
            when (smallBall.getStatus()) {
                SmallBallStatus.HIDDEN -> hiddenBalls.add(smallBall);
                SmallBallStatus.SPAWNED -> spawnedBall = smallBall;
                SmallBallStatus.APPROACHING -> approachingBalls.add(smallBall)
                SmallBallStatus.SPINNING -> spinningBalls.add(smallBall);
            }
        }
    }

    public fun getMainCircle(): MainCircle {
        return this.mainCircle;
    }

    fun getRotationSpeed(): Int {
        return rotationSpeed;
    }

    fun setRotationSpeed(speed: Int) {
        this.rotationSpeed = speed;
    }

    fun getApproachingSpeed(): Int {
        return approachingSpeed;
    }

    fun setApproachingSpeed(speed: Int) {
        this.approachingSpeed = speed;
    }

    fun getHiddenBalls(): ArrayList<SmallBall> {
        return this.hiddenBalls;
    }

    fun getSpawnedBall(): SmallBall? {
        return this.spawnedBall;
    }

    fun getApproachingBalls(): ArrayList<SmallBall> {
        return this.approachingBalls;
    }

    fun getSpinningBalls(): ArrayList<SmallBall> {
        return this.spinningBalls;
    }

    fun addHiddenBall(ball: SmallBall) {
        ball.setStatus(SmallBallStatus.HIDDEN);
        hiddenBalls.add(ball);
    }

    fun setSpawnedBall(ball: SmallBall?) {
        ball?.setStatus(SmallBallStatus.SPAWNED)

        spawnedBall = ball;
    }

    fun addApproachingBall(ball: SmallBall) {
        ball.setStatus(SmallBallStatus.APPROACHING);
        approachingBalls.add(ball);
    }

    fun addSpinningBall(ball: SmallBall) {
        ball.setStatus(SmallBallStatus.SPINNING)
        spinningBalls.add(ball);
    }
}