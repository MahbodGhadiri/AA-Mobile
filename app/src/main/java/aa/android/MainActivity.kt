package aa.android

import aa.android.activities.GameActivity
import android.content.Intent
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
        val play_button = findViewById<ImageView>(R.id.imageView)
        play_button.setOnClickListener {
            // Create an Intent to start Activity
            val intent = Intent(this, GameActivity::class.java)

            // Start the new activity
            startActivity(intent)
        }
    }
}