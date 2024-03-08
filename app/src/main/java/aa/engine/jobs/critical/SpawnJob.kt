package aa.engine.jobs.critical

import AppConfig
import aa.engine.elements.SmallBallStatus
import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.Job

class SpawnJob() : Job() {
    override fun run(context: ExecutionContext) {
        for (smallBall in context.getSmallBalls()) {
            if (smallBall.getStatus() == SmallBallStatus.SPAWNING) {
                val y = smallBall.getPosition().getY();
                if (y > AppConfig.getSpawnBallPos().getY()) {
                    smallBall.setPosition(
                        smallBall.getPosition().getX(),
                        y - context.getRotationSpeed() * (y - AppConfig.getSpawnBallPos()
                            .getY()) / 25
                    );
                }

                if (y - AppConfig.getSpawnBallPos().getY() < 5) {
                    smallBall.setStatus(SmallBallStatus.SPAWNED);
                }
            }
        }
    }

}