package aa.engine.jobs.critical

import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.Job

class OrbitingJob : Job() {
    override fun run(context: ExecutionContext) {
        for (smallBall in context.getSpinningBalls()) {
            smallBall.setTheta(smallBall.getTheta() + context.getRotationSpeed());
        }
    }
}