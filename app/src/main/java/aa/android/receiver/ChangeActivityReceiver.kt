package aa.android.receiver

import aa.android.activities.WinActivity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ChangeActivityReceiver() :
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.getStringExtra("activity") == "win") this.toWinActivity(
            context,
            intent
        );
    }

    private fun toWinActivity(context: Context?, intent: Intent) {
        val sai = Intent(context, WinActivity::class.java);
        context?.startActivity(sai);
    }
}