package aa.android.activities


import aa.android.R
import aa.android.sound.BackgroundMusicService
import aa.engine.config.AppConfig
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class SettingsActivity : BaseActivity() {

    private var hasSoundEffects = true
    private var hasMusic = true
    private var hasCloseCalls = true
    private val languages = ArrayList<String>()
    private val languageCodes = ArrayList<String>()
    private var language = "en"
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<String>

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

        spinner = findViewById<Spinner>(R.id.language_spinner)
        getPreferences();
        loadResources();
        System.out.println("initializing spinner")
        initializeSpinner();
        System.out.println("setting resources")
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

    private fun loadResources() {
        languages.addAll(resources.getStringArray(R.array.languages))
        languageCodes.addAll(resources.getStringArray(R.array.LANGUAGE_CODES))

    }

    private fun initializeSpinner() {
        var spinnerPosition = 0;
        for (code in languageCodes) {
            if (code == language)
                break;
            spinnerPosition++;
        }


        adapter = ArrayAdapter(this, R.layout.spinner_language)
        adapter.addAll(languages)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(spinnerPosition, false)
        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {

                    language = languageCodes[position]
                    System.out.println(language)
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

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }

    }

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
            spinner.performClick()
        }
    }

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

    public fun restartApp() {
        val packageManager: PackageManager = this.packageManager
        val intent =
            packageManager.getLaunchIntentForPackage(this.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        mainIntent.setPackage(this.packageName)
        startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }


}