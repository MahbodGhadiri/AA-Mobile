package aa.engine.jobs.optional

import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.Job
import kotlin.math.abs

class ChangeSpeedJob(
    private val topSpeed: Float,
    private val lowSpeed: Float,
    quickestPossibleTimeFrame: Int,
    slowestPossibleTimeFrame: Int
) :
    Job() {
    private var normalSpeed = 0F;
    private var shouldChangeSpeed = false;
    private var hasNormalSpeed = true;
    private var sleepingPeriod = 0;
    private val sleepingRange =
        quickestPossibleTimeFrame..slowestPossibleTimeFrame

    override fun run(context: ExecutionContext) {
        if (normalSpeed == 0F) normalSpeed = context.getRotationSpeed()

        if (sleepingPeriod <= 0) {
            if (shouldChangeSpeed && hasNormalSpeed && !context.isRotationSpeedLocked()) {
                val newSpeed =
                    ((context.getRotationSpeed() / abs(context.getRotationSpeed())) * lowSpeed) + (Math.random()
                        .toFloat() * topSpeed)
                context.setRotationSpeed(newSpeed)
                shouldChangeSpeed = false
            } else if (shouldChangeSpeed && hasNormalSpeed && !context.isRotationSpeedLocked()) {
                context.setRotationSpeed(context.getRotationSpeedCopy(), false)
                shouldChangeSpeed = false
            }
            if (!shouldChangeSpeed) {
                sleepingPeriod = sleepingRange.random();
                shouldChangeSpeed = true;
            }
        }
        sleepingPeriod--;

    }
}