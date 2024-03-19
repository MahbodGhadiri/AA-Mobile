package aa.android.activities

import aa.android.R
import aa.engine.config.AppConfig
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LevelMenuActivity : AppCompatActivity() {
    lateinit var textIds: IntArray;
    lateinit var sharedPref: SharedPreferences;
    var currentLevel: String = "1";
    var highestCompletedLevel: String = "0";
    var page: Int = 1;

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

        sharedPref = this.getSharedPreferences(
            resources.getString(R.string.preferences),
            Context.MODE_PRIVATE
        )

        currentLevel =
            sharedPref.getString(getString(R.string.current_level), "1")
                .toString()

        highestCompletedLevel = sharedPref.getString(
            getString(R.string.highest_completed_level),
            "0"
        ).toString()

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
            val tag = ((page - 1) * 10) + i;
            textView = findViewById<TextView>(textIds[i - 1])
            if (tag <= AppConfig.getLevelCount()) {
                if (i.toString() == currentLevel) {
                    textView.setTextColor(getColor(R.color.primary_shade))
                }
                if (i > (highestCompletedLevel.toInt() + 1)) {
                    textView.background =
                        getDrawable(R.drawable.level_background_unavailable)
                }
                textView.setText(tag.toString());
                textView.setTag(tag.toString())
            } else textView.visibility = View.INVISIBLE

        }

    }

    public fun handleClickedButton(v: View) {
        val levelNumber = v.getTag().toString()

        if (levelNumber.toInt() <= (highestCompletedLevel.toInt() + 1)) {
            with(sharedPref.edit()) {
                putString(getString(R.string.current_level), levelNumber)
                apply()
            }
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }


    }
}
