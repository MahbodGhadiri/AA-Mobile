package aa.android.activities

import aa.android.R
import aa.android.views.GameView
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameActivity : AppCompatActivity() {
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


        val retryButton = findViewById<ImageView>(R.id.retryButton);
        retryButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java);
            startActivity(intent);
        }

        val menuButton = findViewById<ImageView>(R.id.menuButton);
        menuButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }
    }

    fun showGameOverButtons() {
        gameOverButtons.visibility = View.VISIBLE;
    }

    fun hideGameOverButtons() {
        gameOverButtons.visibility = View.GONE;
    }
}