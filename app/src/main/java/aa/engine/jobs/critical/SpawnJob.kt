package aa.engine.jobs.critical

import AppConfig
import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.Job

class SpawnJob() : Job() {
    override fun run(context: ExecutionContext) {
        val smallBall = context.getSpawningBall();
        if (smallBall != null) {
            val y = smallBall.getPosition().getY();
            if (y > AppConfig.getSpawnBallPos().getY()) {
                smallBall.setPosition(
                    smallBall.getPosition().getX(),
                    y - context.getRotationSpeed() * (y - AppConfig.getSpawnBallPos()
                        .getY()) / 25
                );
            }

            if (y - AppConfig.getSpawnBallPos()
                    .getY() < 5 && context.getSpawningBall() != null
            ) {
                context.setSpawnedBall(smallBall);
            }
        }
    }

}