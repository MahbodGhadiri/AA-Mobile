package aa.android.activities

import aa.android.R
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LevelMenuActivity : AppCompatActivity() {
    lateinit var textIds: IntArray;

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        enableEdgeToEdge();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.levelmenu)) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        textIds = intArrayOf(
            R.id.Button1,
            R.id.Button2,
            R.id.Button3,
            R.id.Button4,
            R.id.Button5,
            R.id.Button6,
            R.id.Button7,
            R.id.Button8,
            R.id.Button9,
            R.id.Button10
        )

        var textView: TextView;
        for (i in 1..10) {
            textView = findViewById<TextView>(textIds[i - 1])
            textView.setText(i.toString());
        }

    }

    public fun handleClickedButton(v: View) {
        val buttonId = v.getId()
        println(buttonId)
    }
}
