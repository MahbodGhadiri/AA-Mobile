package aa.android.activities

import aa.android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameOverActivity : AppCompatActivity() {
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        enableEdgeToEdge();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gameOver)) { v, insets ->
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

        val retryButton = findViewById<ImageView>(R.id.retry);
        retryButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java);
            startActivity(intent);
        }
    }
}