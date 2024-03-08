package aa.engine.helpers

import aa.engine.elements.MainCircle
import aa.engine.elements.SmallBall

class ExecutionContext(
    mainCircle: MainCircle,
    smallBalls: ArrayList<SmallBall>
) {
    private val mainCircle: MainCircle;
    private val smallBalls: ArrayList<SmallBall>;
    private var rotationSpeed: Int = 0;
    private var approachingSpeed: Int = 0;

    init {
        this.mainCircle = mainCircle;
        this.smallBalls = smallBalls;
    }

    public fun getMainCircle(): MainCircle {
        return this.mainCircle;
    }

    public fun getSmallBalls(): ArrayList<SmallBall> {
        return this.smallBalls;
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
}