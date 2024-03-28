package aa.android.activities


import aa.android.R
import aa.android.sound.BackgroundMusicService
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        startService(Intent(this, BackgroundMusicService::class.java))
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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
        val animationView =
            findViewById<LottieAnimationView>(R.id.animation_view)
        animationView.playAnimation()
        val playButton = findViewById<ImageView>(R.id.PlayButton)
        playButton.setOnClickListener {
            // Create an Intent to start Activity
            val intent = Intent(this, GameActivity::class.java)

            // Start the new activity
            startActivity(intent)
        }

        val aboutUsButton = findViewById<ImageView>(R.id.AboutUsButton)
        aboutUsButton.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }

        val levelsButton = findViewById<ImageView>(R.id.LevelsButton)
        levelsButton.setOnClickListener {
            val intent = Intent(this, LevelMenuActivity::class.java)
            startActivity(intent)
        }



    }
}