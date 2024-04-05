package aa.android.activities


import aa.android.R
import aa.android.sound.BackgroundMusicService
import aa.engine.config.AppConfig
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class SettingsActivity : BaseActivity() {

    private var hasSoundEffects = true
    private var hasMusic = true
    private var hasCloseCalls = true
    private var language = "en"

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

    private fun setResources() {
        val soundEffectButton =
            findViewById<ImageView>(R.id.sound_effect_button)
        val musicButton = findViewById<ImageView>(R.id.music_button)
        val closeCallsButton = findViewById<ImageView>(R.id.close_calls_button)
        val languageButton = findViewById<ImageView>(R.id.language_button)
        val languages = ArrayList<String>()
        languages.addAll(getResources().getStringArray(R.array.languages))
        languages.add(0,resources.getString(R.string.setting_language))
        val spinner = findViewById<Spinner>(R.id.language_spinner)

        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                if (position == 0) {
                    view.visibility = View.GONE
                } else {
                    view.visibility = View.VISIBLE
                }
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)
//        val spinnerListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                if (position == 2) {
//                    language = "en"
//                }else {
//                    language = "fa"
//                }
//                with(preferences.edit()) {
//                putString(
//                    getString(R.string.setting_language_preferences),
//                    language
//                )
//                apply()
//            }
//                spinner.setSelection(0)
//                }
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                // Do nothing
//                }
//        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }



        if (hasSoundEffects) {
            soundEffectButton.setImageResource(R.drawable.settings_sound_effect_on)
        } else {
            soundEffectButton.setImageResource(R.drawable.settings_sound_effect_off)
        }
        soundEffectButton.adjustViewBounds = true
        soundEffectButton.setOnClickListener {
            soundEffectOnClick(it)
        }

        if (hasMusic) {
            musicButton.setImageResource(R.drawable.settings_music_on)
        } else {
            musicButton.setImageResource(R.drawable.settings_music_off)
        }
        musicButton.adjustViewBounds = true
        musicButton.setOnClickListener {
            musicOnClick(it)
        }

        if (hasCloseCalls) {
            closeCallsButton.setImageResource(R.drawable.settings_close_call_on)
        } else {
            closeCallsButton.setImageResource(R.drawable.settings_close_call_off)
        }
        closeCallsButton.adjustViewBounds = true
        closeCallsButton.setOnClickListener {
            closeCallsOnClick(it)
        }

//        languageButton.listener
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

//    private fun languageOnClick(v: View) {
//        if (language == "en") {
//            language = "fa"
//        } else {
//            language = "en"
//        }
//
//        with(preferences.edit()) {
//            putString(
//                getString(R.string.setting_language_preferences),
//                language
//            )
//            apply()
//        }
//    }
class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item is selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos).
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback.
    }
}

    
}