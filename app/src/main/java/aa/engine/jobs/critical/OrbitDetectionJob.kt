package aa.engine.jobs.critical

import aa.engine.elements.SmallBallStatus
import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.Job

class OrbitDetectionJob() : Job() {
    override fun run(context: ExecutionContext) {
        val y = context.getMainCircle().getPosition().getY();
        val orbit = context.getMainCircle().getOrbit();
        for (smallBall in context.getSmallBalls()) {
            if (smallBall.getStatus() == SmallBallStatus.APPROACHING) {
                if (smallBall.getPosition().getY() <= y + orbit) {
                    smallBall.setStatus(SmallBallStatus.SPINNING);
                    smallBall.setTheta(0);
                }
            }
        }
    }
}