package aa.engine.jobs

import aa.engine.helpers.ExecutionContext

abstract class Job {
    private var counter: Int = 0;
    private var shouldExit = false;
    abstract fun run(context: ExecutionContext);

    // job will be removed on the next iteration of loop!
    private fun exit() {
        this.shouldExit = true;
    }

    public fun shouldExit(): Boolean {
        return this.shouldExit
    }

    public fun execute(context: ExecutionContext) {
        run(context);
        counter++;
    }
}