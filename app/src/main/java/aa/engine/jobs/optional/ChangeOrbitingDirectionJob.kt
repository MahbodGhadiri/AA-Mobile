package aa.engine.jobs.optional

import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.Job

class ChangeOrbitingDirectionJob(
    private val lowerBound: Int,
    private val upperBound: Int
) : Job() {

    private var sleepingPeriod: Int = 0;
    private val sleepingRange = lowerBound..upperBound;

    init {
        generateSleepingPeroid()
    }

    override fun run(context: ExecutionContext) {
        if (this.sleepingPeriod <= 0) {
            context.setRotationSpeed((-1) * context.getRotationSpeed())
            generateSleepingPeroid()

        }

        sleepingPeriod -= 1


    }

    fun generateSleepingPeroid() {
        this.sleepingPeriod = sleepingRange.random()
    }

}