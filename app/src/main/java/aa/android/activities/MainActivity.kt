package aa.android.activities


import aa.android.R
import aa.android.sound.BackgroundMusicService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {
    private lateinit var backgroundMusicService: BackgroundMusicService
    private var isBound = false
    private var serviceConnected = false
    override fun onResume() {
        super.onResume()
        // Resume the music playback
        if (serviceConnected) {
            backgroundMusicService.resumeMusic()
        }
    }

    override fun onPause() {
        super.onPause()
        // Pause the music playback
        if (serviceConnected) {
            backgroundMusicService.pauseMusic()

            // Unbind from the service when the activity is paused
            if (isBound) {
                unbindService(serviceConnection)
                isBound = false
            }
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as BackgroundMusicService.LocalBinder
            backgroundMusicService = binder.getService()
            isBound = true
            serviceConnected = true
//            backgroundMusicService.startService()
            Log.d("BackgroundMusicService", "Background music nemidunam")
            backgroundMusicService.resumeMusic()
        }

        override fun onServiceDisconnected(className: ComponentName) {
            isBound = false
            serviceConnected = false
        }

    }
//    override fun onStart() {
//        super.onStart()
//        val intent = Intent(this, BackgroundMusicService::class.java)
//        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
//        backgroundMusicService.startService(intent)
//    }
    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val myintent = Intent(this, BackgroundMusicService::class.java)
        bindService(myintent, serviceConnection, Context.BIND_AUTO_CREATE)
        startService(myintent)
        if (serviceConnected) {
            backgroundMusicService.resumeMusic()
        }
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