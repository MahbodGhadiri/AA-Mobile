package aa.engine.jobs.optional

import aa.engine.helpers.ExecutionContext
import aa.engine.jobs.Job

class ChangingOrbitingDirectionJob : Job() {

    var sleepPeriod = (30..500).random()


    override fun run(context: ExecutionContext) {
        if (this.sleepPeriod <= 0) {
            context.setRotationSpeed((-1) * context.getRotationSpeed())
            sleepPeriod = (30..500).random()

        }

        sleepPeriod -= 1


    }

}