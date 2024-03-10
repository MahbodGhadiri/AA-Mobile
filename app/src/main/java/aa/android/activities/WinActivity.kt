package aa.android.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class WinActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        enableEdgeToEdge();
        super.onCreate(savedInstanceState, persistentState);
    }
}