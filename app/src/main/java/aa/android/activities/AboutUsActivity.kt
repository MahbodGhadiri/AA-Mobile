package aa.android.activities

import aa.android.R
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about_us)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars =
//                insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(
//                systemBars.left,
//                systemBars.top,
//                systemBars.right,
//                systemBars.bottom
//            )
//            insets
//        }
    }

}