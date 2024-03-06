package aa.engine.elements

import aa.engine.helpers.Position

enum class SmallBallStatus {
    HIDDEN,
    SPAWNED,
    APPROACHING,
    SPINNING
}

open class SmallBall constructor(
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