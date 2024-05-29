package aa.android.fragments

import aa.android.R
import aa.android.activities.LevelMenuActivity
import aa.engine.config.AppConfig
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment

class LevelsContainerFragment : Fragment() {
    lateinit var textIds: IntArray;
    private var page = 0;
    private var firstTime = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textIds = intArrayOf(
            R.id.Button1,
            R.id.Button2,
            R.id.Button3,
            R.id.Button4,
            R.id.Button5,
            R.id.Button6,
            R.id.Button7,
            R.id.Button8,
            R.id.Button9,
            R.id.Button10
        )

        populateButtons()


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(
                R.layout.fragment_level_container,
                container,
                false
            )

        return view
    }

    override fun onResume() {
        super.onResume()
        populateButtons()
    }


    public fun populateButtons() {

        var textView: TextView;
        activity?.let {
            it as LevelMenuActivity

            if (page == 0)
                page = it.page
            System.out.println(page)

            for (i in 1..10) {
                val tag = ((page - 1) * 10) + i;
                textView = it.findViewById(textIds[i - 1])
                if (tag <= AppConfig.getLevelCount()) {
                    if (tag.toString() == it.currentLevel) {
                        textView.setTextColor(it.getColor(R.color.primary_shade))
                    }
                    if (tag > (it.highestCompletedLevel.toInt() + 1)) {
                        textView.background =
                            context?.let {
                                AppCompatResources.getDrawable(
                                    it,
                                    R.drawable.level_background_unavailable
                                )
                            }
                        textView.visibility = View.VISIBLE
                    } else {
                        textView.background =
                            context?.let {
                                AppCompatResources.getDrawable(
                                    it,
                                    R.drawable.level_background
                                )
                            }
                        textView.visibility = View.VISIBLE
                    }
                    textView.setText(tag.toString());
                    textView.setTag(tag.toString())
                } else textView.visibility = View.INVISIBLE

            }
        }

    }

    public fun setPage(page: Int) {
        this.page = page;
    }
}