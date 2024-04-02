package aa.android.activities

import aa.android.R
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

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
        super.onCreate(savedInstanceState)

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


}