package aa.android.activities


import aa.android.R
import aa.android.fragments.ChooseLanguageDialogFragment
import aa.android.sound.BackgroundMusicService
import aa.engine.config.AppConfig
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * activity for settings page
 */
class SettingsActivity : BaseActivity() {

    private var hasSoundEffects = true
    private var hasMusic = true
    private var hasCloseCalls = true
    lateinit var languages: Array<String>
    private val languageCodes = ArrayList<String>()
    private var language = "en"
    private var dialog = ChooseLanguageDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.settings_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings)) { v, insets ->
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

        getPreferences();
        loadResources();
        setResources();
    }

    private fun getPreferences() {
        hasSoundEffects = preferences.getBoolean(
            getString(R.string.setting_sound_effects_preferences), true
        )

        hasMusic = preferences.getBoolean(
            getString(R.string.setting_music_preferences), true
        )

        hasCloseCalls = preferences.getBoolean(
            getString(R.string.setting_close_calls_preferences), true
        )

        language = preferences.getString(
            getString(R.string.setting_language_preferences), "en"
        ).toString()

    }

    /**
     * get current language and available languages
     */
    private fun loadResources() {
        languages = resources.getStringArray(R.array.languages)
        languageCodes.addAll(resources.getStringArray(R.array.LANGUAGE_CODES))
    }

    /**
     * callback to to change language
     */
    public fun onLanguageSelect(index: Int) {
        language = languageCodes[index]
        with(preferences.edit()) {
            putString(
                getString(R.string.setting_language_preferences),
                language
            )
            apply()
        }
        setLocale(language)
        restartApp();
    }

    /**
     * dynamically add images and icons based on current state of app
     */
    private fun setResources() {
        val soundEffectImage =
            findViewById<ImageView>(R.id.sound_effect_image)
        val soundEffectContainer =
            findViewById<LinearLayout>(R.id.sound_effects_container)
        val musicImage = findViewById<ImageView>(R.id.music_image)
        val musicContainer = findViewById<LinearLayout>(R.id.music_container)
        val closeCallsImage = findViewById<ImageView>(R.id.close_calls_image)
        val closeCallsContainer =
            findViewById<LinearLayout>(R.id.close_calls_container)
        val languageContainer =
            findViewById<LinearLayout>(R.id.language_container)

        if (hasSoundEffects) {
            soundEffectImage.setImageResource(R.drawable.settings_sound_effect_on)
        } else {
            soundEffectImage.setImageResource(R.drawable.settings_sound_effect_off)
        }
        soundEffectImage.adjustViewBounds = true
        soundEffectContainer.setOnClickListener {
            soundEffectOnClick(it);
        }


        if (hasMusic) {
            musicImage.setImageResource(R.drawable.settings_music_on)
        } else {
            musicImage.setImageResource(R.drawable.settings_music_off)
        }
        musicImage.adjustViewBounds = true
        musicContainer.setOnClickListener {
            musicOnClick(it);
        }

        if (hasCloseCalls) {
            closeCallsImage.setImageResource(R.drawable.settings_close_call_on)
        } else {
            closeCallsImage.setImageResource(R.drawable.settings_close_call_off)
        }
        closeCallsImage.adjustViewBounds = true
        closeCallsContainer.setOnClickListener {
            closeCallsOnClick(it);
        }

        languageContainer.setOnClickListener {
            languageOnClick(it)
        }
    }

    /**
     * change sound effect state
     */
    private fun soundEffectOnClick(v: View) {
        hasSoundEffects = !hasSoundEffects
        with(preferences.edit()) {
            putBoolean(
                getString(R.string.setting_sound_effects_preferences),
                hasSoundEffects
            )
            apply()
        }
        setResources()
        AppConfig.setSoundEffectsStatus(hasSoundEffects)
    }


    /**
     * change music state
     */
    private fun musicOnClick(v: View) {
        hasMusic = !hasMusic
        with(preferences.edit()) {
            putBoolean(
                getString(R.string.setting_music_preferences),
                hasMusic
            )
            apply()
        }
        setResources()
        AppConfig.setHasMusic(hasMusic);
        if (hasMusic) {
            startService(Intent(this, BackgroundMusicService::class.java))
        } else {
            stopService(Intent(this, BackgroundMusicService::class.java))
        }
    }

    /**
     * change close calls (slow mo) state
     */
    private fun closeCallsOnClick(v: View) {
        hasCloseCalls = !hasCloseCalls
        with(preferences.edit()) {
            putBoolean(
                getString(R.string.setting_close_calls_preferences),
                hasCloseCalls
            )
            apply()
        }
        setResources()
        AppConfig.setCloseCallsStatus(hasCloseCalls)
    }


    /**
     * change language state
     */
    private fun languageOnClick(v: View) {
        dialog

        dialog.show(
            supportFragmentManager,
            "LanguageDialog"
        )
    }

    /**
     * restart app after changing language
     */
    private fun restartApp() {
        val packageManager: PackageManager = this.packageManager
        val intent =
            packageManager.getLaunchIntentForPackage(this.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        mainIntent.setPackage(this.packageName)
        startActivity(mainIntent)
        finish()
    }


}