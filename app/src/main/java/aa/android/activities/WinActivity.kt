package aa.android.activities

import aa.android.R
import aa.engine.config.AppConfig
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WinActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        enableEdgeToEdge();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.win)) { v, insets ->
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


        val sharedPref = this.getSharedPreferences(
            resources.getString(R.string.preferences),
            Context.MODE_PRIVATE
        )

        val currentLevel =
            sharedPref.getString(getString(R.string.current_level), "1")
                .toString()

        val highestCompletedLevel = sharedPref.getString(
            getString(R.string.highest_completed_level),
            "0"
        )


        val retryButton = findViewById<ImageView>(R.id.retry);
        retryButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java);
            startActivity(intent);
        }


        val nextLevelButton = findViewById<ImageView>(R.id.next_level)
        if (currentLevel.toInt() >= AppConfig.getLevelCount()) {
            nextLevelButton.visibility = View.INVISIBLE
        } else {
            nextLevelButton.setOnClickListener {
                val intent = Intent(this, GameActivity::class.java)
                with(sharedPref.edit()) {
                    putString(
                        getString(R.string.current_level),
                        (currentLevel.toInt() + 1).toString()
                    )
                    apply()
                }
                startActivity(intent)
            }
        }

        val homeButton = findViewById<ImageView>(R.id.menu)
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val levelsMenuButton = findViewById<ImageView>(R.id.levels)
        levelsMenuButton.setOnClickListener {
            val intent = Intent(this, LevelMenuActivity::class.java)
            startActivity(intent)
        }

    }
}