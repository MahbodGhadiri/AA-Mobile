package aa.engine.elements

import aa.engine.config.AppConfig
import aa.engine.helpers.Position
import kotlin.math.cos
import kotlin.math.sin

enum class SmallBallStatus {
    HIDDEN,
    SPAWNING,
    SPAWNED,
    APPROACHING,
    SPINNING
}

open class SmallBall constructor(

    private var status: SmallBallStatus = SmallBallStatus.HIDDEN,
    private var theta: Float = 90F,
    private val position: Position = Position(
        AppConfig.getScrWidth() / 2,
        AppConfig.getScrHeight() + AppConfig.getScrHeight() / 10
    ),
    private var radius: Float = AppConfig.getSmallBallRadius(),
) {
    fun setPosition(x: Float, y: Float) {
        this.position.setPosition(x, y);
    }

    fun setRadius(radius: Float) {
        this.radius = radius;
    }

    fun setStatus(status: SmallBallStatus) {
        this.status = status;
    }

    fun setTheta(theta: Float) {
        this.theta = theta;

        // calculate new position
        val x =
            AppConfig.getMainCirclePos()
                .getX() + (AppConfig.getMainCircleOrbit() * sin(
                Math.toRadians(
                    theta.toDouble()
                )
            ))

        val y = AppConfig.getMainCirclePos()
            .getY() + (AppConfig.getMainCircleOrbit() * cos(
            Math.toRadians(theta.toDouble())
        ))

        this.setPosition(x.toFloat(), y.toFloat())
    }


    fun getPosition(): Position {
        return this.position;
    }

    fun getRadius(): Float {
        return this.radius;
    }

    fun getStatus(): SmallBallStatus {
        return this.status;
    }

    fun getTheta(): Float {
        return this.theta;
    }
}