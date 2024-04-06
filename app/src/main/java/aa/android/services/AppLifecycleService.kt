import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import java.io.Closeable

interface LifecycleListener {
    fun onAppForeground()
    fun onAppBackground()
}

class AppLifecycleService() : LifecycleObserver, Closeable {
    private var listener: LifecycleListener? = null


    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    fun setLifecycleListener(listener: LifecycleListener) {
        this.listener = listener
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        listener?.onAppForeground()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        listener?.onAppBackground()
    }

    override fun close() {
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }
}