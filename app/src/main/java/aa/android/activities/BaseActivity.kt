package aa.android.activities

import AppLifecycleService
import LifecycleListener
import aa.android.R
import aa.android.sound.BackgroundMusicService
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

/**
 * This class should be inherited by all other activities!
 * loads preferences and attach a listener on language change.
 * also handles music playing and pause.
 * */
open class BaseActivity : AppCompatActivity() {
    protected lateinit var preferences: SharedPreferences

    // storing a reference just to protect listener from garbage collection
    // as register api does not actually store a hard reference
    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == getString(R.string.setting_language_preferences)) {
                setLocale(sharedPreferences.getString(key, "en").toString())
                recreate()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        /*
        *   START :: APP FOCUSED / UNFOCUSED
        *   This part uses lifecycle Observers to check if
        *   the app is focused or not.
        * */

        // initializing app service
        val lifecycleService = AppLifecycleService();
        val appServiceListener = object : LifecycleListener {
            override fun onAppForeground() {
                handleMoveToForeground()
            }

            override fun onAppBackground() {
                handleMoveToBackground()
            }
        }

        // setting callbacks on class.
        lifecycleService.setLifecycleListener(appServiceListener)

        /*
        *   END :: APP FOCUSED / UNFOCUSED
        * */

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

    /**
     * Set language by language code
     * */
    protected fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        resources.configuration.setLocale(locale)
        resources.updateConfiguration(
            resources.configuration,
            resources.displayMetrics
        )

    }

    private fun handleMoveToForeground() {
        if (preferences.getBoolean(
                getString(R.string.setting_music_preferences), true
            )
        ) {
            startService(Intent(this, BackgroundMusicService::class.java))
        }
    }

    private fun handleMoveToBackground() {
        if (preferences.getBoolean(
                getString(R.string.setting_music_preferences), true
            )
        ) {
            stopService(Intent(this, BackgroundMusicService::class.java))
        }
    }
}