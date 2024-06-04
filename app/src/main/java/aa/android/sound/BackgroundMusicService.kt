package aa.android.sound

import aa.android.R
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable

/**
 * Service responsible for playing background music
 */
class BackgroundMusicService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.background_sound)
        mediaPlayer.setLooping(true)
        mediaPlayer.setVolume(100f, 100f)

    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        mediaPlayer.start()
        Log.d("BackgroundMusicService", "Background music started")
        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()
        Log.d("BackgroundMusicService", "Background music stopped")
    }
}