package aa.engine.jobs.critical

import aa.engine.config.AppConfig
import aa.engine.config.EngineStatus
import aa.engine.elements.SmallBall
import aa.engine.helpers.ExecutionContext
import aa.engine.helpers.Position
import aa.engine.jobs.Job
import kotlin.math.pow
import kotlin.math.sqrt

class CollisionJob() : Job() {
    private var rotationSpeed: Float = 0F;
    private var approachingSpeed: Float = 0F;

    private fun calDistance(o1: Position, o2: Position): Double {
        val x2 = (o1.getX() - o2.getX()).toDouble().pow(2.0);
        val y2 = (o1.getY() - o2.getY()).toDouble().pow(2.0);
        val r = x2 + y2;

        return sqrt(r);
    }

    override fun run(context: ExecutionContext) { // trying to implement o(n)
        if (rotationSpeed == 0F || approachingSpeed == 0F) {
            rotationSpeed = context.getRotationSpeed();
            approachingSpeed = context.getApproachingSpeed();
        }


        val smallBallCriticalDistance: Float =
            AppConfig.getSmallBallRadius() * 2; // it's 2 * radius of the small ball.
        val approachingBalls: ArrayList<SmallBall> =
            context.getApproachingBalls();
        val spinningBalls: ArrayList<SmallBall> = context.getSpinningBalls();

        var closestBallToMainCircle: SmallBall? = null;


        for (i in approachingBalls.indices) {
            if (closestBallToMainCircle == null) closestBallToMainCircle =
                approachingBalls[i];
            else {
                if (closestBallToMainCircle.getPosition()
                        .getY() > approachingBalls[i].getPosition().getY()
                ) closestBallToMainCircle = approachingBalls[i]
            }
        }

        for (sBall in spinningBalls) {
            /*
              the normal speed of a approaching ball is 50 px per engine tick.
              so, if two ball distances were less than 50 px and not collided,
              there is a possibility that they collide later,
              here is the calculation.
             */

            if (
                closestBallToMainCircle != null
                && calDistance(
                    sBall.getPosition(),
                    closestBallToMainCircle.getPosition()
                ) <= 4 * AppConfig.getSmallBallRadius().toDouble()
            ) {
                context.setApproachingSpeed(1F);
                context.setRotationSpeed(.1F);
            }

            if (
                closestBallToMainCircle != null
                && calDistance(
                    sBall.getPosition(),
                    closestBallToMainCircle.getPosition()
                ) > 4 * AppConfig.getSmallBallRadius().toDouble()
            ) {
                context.setRotationSpeed(rotationSpeed);
                context.setApproachingSpeed(approachingSpeed);
            }
            if (
                closestBallToMainCircle != null
                && calDistance(
                    sBall.getPosition(),
                    closestBallToMainCircle.getPosition()
                ) <= smallBallCriticalDistance
            ) {
                // collision detected. stop the engine
                AppConfig.setEngineStatus(EngineStatus.GAMEOVER);
                break;
            }
        }
    }
}