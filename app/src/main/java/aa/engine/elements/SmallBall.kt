package aa.engine.elements

import aa.engine.helpers.Position
import kotlin.math.cos
import kotlin.math.sin

enum class SmallBallStatus {
    HIDDEN,
    SPAWNED,
    APPROACHING,
    SPINNING
}

open class SmallBall constructor(
    //TODO: better way of keeping main circle orbit is needed!
    private val mainCircle: MainCircle,
    private val position: Position,
    private var radius: Int,
    private var status: SmallBallStatus = SmallBallStatus.HIDDEN,
    private var theta: Int = 90
) {
    fun setPosition(x: Int, y: Int) {
        this.position.setPosition(x, y);
    }

    fun setRadius(radius: Int) {
        this.radius = radius;
    }

    fun setStatus(status: SmallBallStatus) {
        this.status = status;
    }

    fun setTheta(theta: Int) {
        this.theta = theta;

        // calculate new position
        val x =
            mainCircle.getPosition()
                .getX() + (mainCircle.getOrbit() * sin(Math.toRadians(theta.toDouble())))

        val y = mainCircle.getPosition().getY() + (mainCircle.getOrbit() * cos(
            Math.toRadians(theta.toDouble())
        ))

        this.setPosition(x.toInt(), y.toInt())
    }


    fun getPosition(): Position {
        return this.position;
    }

    fun getRadius(): Int {
        return this.radius;
    }

    fun getStatus(): SmallBallStatus {
        return this.status;
    }

    fun getTheta(): Int {
        return this.theta;
    }
}