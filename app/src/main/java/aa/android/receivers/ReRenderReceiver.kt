package aa.android.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReRenderReceiver(private val func: () -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "reRender") {
            func()
        }
    }

}