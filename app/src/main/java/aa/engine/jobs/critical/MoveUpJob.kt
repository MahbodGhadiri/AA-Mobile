package aa.engine.jobs.critical

import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.Job

class MoveUpJob : Job() {
    override fun run(context: ExecutionContext) {
        for (smallBall in context.getApproachingBalls()) {
            smallBall.setPosition(
                smallBall.getPosition().getX(),
                smallBall.getPosition().getY() - context.getApproachingSpeed()
            );
        }
    }
}