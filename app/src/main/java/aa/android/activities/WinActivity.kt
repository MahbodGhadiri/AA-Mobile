package aa.android.activities

import aa.android.R
import aa.engine.config.AppConfig
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * activity for win page after passing a level.
 */
class WinActivity : BaseActivity() {

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


        val currentLevel =
            preferences.getString(getString(R.string.current_level), "1")
                .toString()

        if (currentLevel.toInt() < AppConfig.getLevelCount()) {
            with(preferences.edit()) {
                putString(
                    getString(R.string.current_level),
                    (currentLevel.toInt() + 1).toString()
                )
                apply()
            }
        }


        val retryButton = findViewById<FrameLayout>(R.id.retry);
        retryButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java);
            startActivity(intent);
            finish()
        }


        val nextLevelButton = findViewById<ImageView>(R.id.next_level)
        if (currentLevel.toInt() >= AppConfig.getLevelCount()) {
            nextLevelButton.visibility = View.INVISIBLE
        } else {
            nextLevelButton.setOnClickListener {
                val intent = Intent(this, GameActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val homeButton = findViewById<FrameLayout>(R.id.menu)
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val levelsMenuButton = findViewById<FrameLayout>(R.id.levels)
        levelsMenuButton.setOnClickListener {
            val intent = Intent(this, LevelMenuActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}