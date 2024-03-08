package aa.engine.jobs.critical

import aa.engine.elements.SmallBallStatus
import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.Job

class OrbitingJob : Job() {
    override fun run(context: ExecutionContext) {
        for (smallBall in context.getSmallBalls()) {
            if (smallBall.getStatus() == SmallBallStatus.SPINNING) {
                smallBall.setTheta(smallBall.getTheta() + context.getRotationSpeed())
            }
        }
    }
}