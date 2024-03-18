package aa.android.activities

import aa.android.GameView
import aa.android.R
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameActivity : AppCompatActivity() {
    private var gameView: GameView? = null
    private lateinit var gameOverButtons: LinearLayout;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        gameOverButtons = findViewById(R.id.gameOverButtons);

        val gameView = findViewById<GameView>(R.id.GameView)
        gameView.setActivity(this)  // Pass the context (activity) to GameView
        this.gameView = gameView

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
    }

    fun showGameOverButtons() {
        gameOverButtons.visibility = View.VISIBLE
    }

    fun hideGameOverButtons() {
        gameOverButtons.visibility = View.GONE;
    }
}