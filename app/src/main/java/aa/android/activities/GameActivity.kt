package aa.android.activities

import aa.android.R
import aa.android.views.GameView
import aa.engine.config.AppConfig
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameActivity : BaseActivity() {
    private lateinit var gameOverButtons: LinearLayout;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        gameOverButtons = findViewById(R.id.gameOverButtons);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game)) { v, insets ->
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

        val gameView = findViewById<GameView>(R.id.GameView)

        val retryButton = findViewById<ImageView>(R.id.retryButton);
        retryButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java);
            startActivity(intent);
        }

        val menuButton = findViewById<FrameLayout>(R.id.menuButton);
        menuButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
            finish()
        }

        val levelsButton = findViewById<FrameLayout>(R.id.levelsButton);
        levelsButton.setOnClickListener {
            val intent = Intent(this, LevelMenuActivity::class.java);
            startActivity(intent);
            finish()
        }

        val hasCloseCalls = preferences.getBoolean(
            getString(R.string.setting_close_calls_preferences),
            true
        )
        AppConfig.setCloseCallsStatus(hasCloseCalls)
    }

    fun showGameOverButtons() {
        gameOverButtons.visibility = View.VISIBLE;
    }

    fun hideGameOverButtons() {
        gameOverButtons.visibility = View.GONE;
    }
}