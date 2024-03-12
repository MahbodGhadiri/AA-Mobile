package aa.engine.jobs.critical

import aa.engine.elements.SmallBall
import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.Job

class OrbitDetectionJob() : Job() {
    override fun run(context: ExecutionContext) {
        val y = context.getMainCircle().getPosition().getY();
        val orbit = context.getMainCircle().getOrbit();
        var smallBall: SmallBall? = null
        val iterator = context.getApproachingBalls().iterator()
        while (iterator.hasNext()) {
            smallBall = iterator.next();
            if (smallBall.getPosition().getY() <= y + orbit) {
                context.addSpinningBall(smallBall);
                smallBall.setTheta(0F);
                iterator.remove();
            }

        }
    }
}