import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import java.io.Closeable

class AppLifecycleService() : LifecycleObserver, Closeable {

    val channel = Channel<Boolean>()

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        runBlocking { channel.send(true) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        runBlocking { channel.send(false) }
    }

    override fun close() {
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
        channel.close()
    }
}