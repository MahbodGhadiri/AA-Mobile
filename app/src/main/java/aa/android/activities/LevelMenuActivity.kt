package aa.android.activities

import aa.android.R
import aa.android.fragments.LevelsContainerFragment
import aa.engine.config.AppConfig
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class LevelMenuActivity : BaseActivity() {
    public var currentLevel: String = "1";
    public var highestCompletedLevel: String = "0";
    public var page: Int = 1;
    private var x1 = 0F;
    private var x2 = 0F;

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        enableEdgeToEdge();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_level_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.level)) { v, insets ->
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

        currentLevel =
            preferences.getString(getString(R.string.current_level), "1")
                .toString()

        highestCompletedLevel = preferences.getString(
            getString(R.string.highest_completed_level),
            "0"
        ).toString()

        page = preferences.getInt(
            getString(R.string.current_page),
            ((currentLevel.toInt() - 1) / 10) + 1
        )


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> x1 = event.x
            MotionEvent.ACTION_UP -> {
                x2 = event.x
                val deltaX: Float = x2 - x1
                if (deltaX > 150) {
                    onSwipeRight()
                } else if (deltaX < -150) {
                    onSwipeLeft()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    public fun handleClickedButton(v: View) {
        val levelNumber = v.getTag().toString()

        if (levelNumber.toInt() <= (highestCompletedLevel.toInt() + 1)) {
        with(preferences.edit()) {
            putString(getString(R.string.current_level), levelNumber)
            apply()
        }
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
        }
    }

    private fun onSwipeLeft() {
        if ((((AppConfig.getLevelCount() - 1) / 10) + 1) > page) {
            page += 1

            val fragment = LevelsContainerFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.levels_container, fragment).commit()
        }
    }

    private fun onSwipeRight() {
        if (1 < page) {
            page -= 1;
            val fragment = LevelsContainerFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.levels_container, fragment).commit()

        }
    }
}
