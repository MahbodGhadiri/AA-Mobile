package aa.android.activities

import AppLifecycleService
import aa.android.R
import aa.android.sound.BackgroundMusicService
import aa.engine.config.AppConfig
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import java.util.Locale

open class BaseActivity : AppCompatActivity() {
    protected lateinit var preferences: SharedPreferences
    private var counter: Number = 0;

    // storing a reference just to protect listener from garbage collection
    // as register api does not actually store a hard reference
    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == getString(R.string.setting_language_preferences)) {
                setLocale(sharedPreferences.getString(key, "en").toString())
                recreate()
            }
        }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        // initialize listener
        val scope =
            CoroutineScope(newSingleThreadContext("check-app-state-thread"));
        val appListener = AppLifecycleService();

        scope.launch {
            appListener.channel.consumeEach { isForeground ->
                if (isForeground) handleMoveToForeground()
                else handleMoveToBackground()
            }
        }



        preferences = this.getSharedPreferences(
            resources.getString(
                R.string.preferences
            ), Context.MODE_PRIVATE
        )

        preferences.registerOnSharedPreferenceChangeListener(listener)

        setLocale(
            preferences.getString(
                getString(R.string.setting_language_preferences),
                "en"
            ).toString()
        )

    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        resources.configuration.setLocale(locale)
        resources.updateConfiguration(
            resources.configuration,
            resources.displayMetrics
        )

    }

    private fun handleMoveToForeground() {
        if (AppConfig.hasMusic()) {
            startService(Intent(this, BackgroundMusicService::class.java))
        }
    }

    private fun handleMoveToBackground() {
        if (AppConfig.hasMusic()) {
            stopService(Intent(this, BackgroundMusicService::class.java))
        }
    }
}