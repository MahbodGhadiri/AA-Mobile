package aa.engine.helpers

import aa.engine.elements.MainCircle
import aa.engine.elements.SmallBall
import aa.engine.elements.SmallBallStatus

class ExecutionContext(
    private val mainCircle: MainCircle,
) {
    private var rotationSpeed: Float = 0F;
    private var approachingSpeed: Float = 0F;
    private var spawningSpeed: Float = 0F;

    private var _rotationSpeed: Float = 0F;
    private var _approachingSpeed: Float = 0F;
    private var _spawningSpeed: Float = 0F;

    private var rotationSpeedLocked = false;


    private val hiddenBalls = ArrayList<SmallBall>();
    private var spawnedBall: SmallBall? = null;
    private val spinningBalls = ArrayList<SmallBall>();
    private val approachingBalls = ArrayList<SmallBall>();
    private var spawningBall: SmallBall? = null;

    public fun getMainCircle(): MainCircle {
        return this.mainCircle;
    }

    /* this function should be called by the UI component responsible for generating
     small balls and called before calling engine.play();
     */
    public fun setSmallBalls(smallBalls: ArrayList<SmallBall>) {
        for (smallBall in smallBalls) {
            when (smallBall.getStatus()) {
                SmallBallStatus.HIDDEN -> hiddenBalls.add(smallBall);
                SmallBallStatus.SPAWNED -> spawnedBall = smallBall;
                SmallBallStatus.SPAWNING -> spawningBall = smallBall;
                SmallBallStatus.APPROACHING -> approachingBalls.add(smallBall)
                SmallBallStatus.SPINNING -> spinningBalls.add(smallBall);
            }
        }
    }

    // start:: spawning
    fun getSpawningSpeed(): Float {
        return spawningSpeed;
    }

    fun getSpawningSpeedCopy(): Float {
        return this._spawningSpeed;
    }

    fun setSpawningSpeed(speed: Float, setCopy: Boolean = true) {
        this.spawningSpeed = speed;
        if (setCopy) this._spawningSpeed = speed;
    }
    // end:: spawning

    fun getRotationSpeed(): Float {
        return rotationSpeed;
    }

    fun getRotationSpeedCopy(): Float {
        return _rotationSpeed;
    }

    fun setRotationSpeed(speed: Float, setCopy: Boolean = true) {
        if (rotationSpeedLocked) throw Error("Rotation Speed Was locked, but something tried to change it!")
        this.rotationSpeed = speed;
        if (setCopy) this._rotationSpeed = speed;
    }

    fun isRotationSpeedLocked(): Boolean {
        return rotationSpeedLocked;
    }

    fun setRotationSpeedLocked(bool: Boolean) {
        rotationSpeedLocked = bool
    }

    fun getApproachingSpeed(): Float {
        return approachingSpeed;
    }

    fun getApproachingSpeedCopy(): Float {
        return _approachingSpeed;
    }

    fun setApproachingSpeed(speed: Float, setCopy: Boolean = true) {
        this.approachingSpeed = speed;
        if (setCopy) this._approachingSpeed = speed;
    }

    fun setSpawningBall(ball: SmallBall?) {
        ball?.setStatus(SmallBallStatus.SPAWNING);
        this.spawningBall = ball;
    }

    fun getHiddenBalls(): ArrayList<SmallBall> {
        return this.hiddenBalls;
    }

    fun getAndPopHiddenBall(): SmallBall? {
        return if (this.hiddenBalls.size > 0) {
            this.hiddenBalls.removeLast();
        } else {
            null;
        }
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

    fun getSpawningBall(): SmallBall? {
        return this.spawningBall;
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

    fun flushBalls() {
        hiddenBalls.removeAll(hiddenBalls.toSet());
        spawnedBall = null;
        spawningBall = null;
        approachingBalls.removeAll(approachingBalls.toSet());
        spinningBalls.removeAll(spinningBalls.toSet());

    }
}