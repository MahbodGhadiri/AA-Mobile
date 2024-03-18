package aa.android.fragments

import aa.android.R
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ContributorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var contributorName: CharSequence? = null
    private var contributorBio: CharSequence? = null
    private var contributorPicId: Int? = null
    private var contributorGithubUrl: String? = null
    private var contributorLinkedInUrl: String? = null
    private var contributorTelegramUrl: String? = null

    override fun onInflate(
        context: Context,
        attrs: AttributeSet,
        savedInstanceState: Bundle?
    ) {
        super.onInflate(context, attrs, savedInstanceState)
        val style = context.obtainStyledAttributes(
            attrs,
            R.styleable.ContributorFragment,
            0,
            0
        )
        contributorName =
            style.getText(R.styleable.ContributorFragment_contributor_name)

        contributorBio =
            style.getText(R.styleable.ContributorFragment_contributor_bio)

        contributorPicId =
            style.getResourceId(
                R.styleable.ContributorFragment_contributor_pic,
                0
            )

        contributorGithubUrl =
            style.getString(R.styleable.ContributorFragment_contributor_github_url)

        contributorLinkedInUrl =
            style.getString(R.styleable.ContributorFragment_contributor_linkedin_url)

        contributorTelegramUrl =
            style.getString(R.styleable.ContributorFragment_contributor_telegram_url)

        style.recycle()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =
            inflater.inflate(R.layout.fragment_contributor, container, false)



        contributorPicId?.let {
            view.findViewById<ImageView>(R.id.profilePicView)
                .setImageResource(it)
        }

        view.findViewById<TextView>(R.id.nameTextView)
            .setText(contributorName)

        view.findViewById<TextView>(R.id.bioTextView)
            .setText(contributorBio)

        val githubView = view.findViewById<ImageView>(R.id.githubIconView)
        val linkedinView = view.findViewById<ImageView>(R.id.linkedinIconView)
        val telegramView = view.findViewById<ImageView>(R.id.telegramIconView)
        githubView.setOnClickListener {
            openBrowser(
                githubView,
                contributorGithubUrl
            )
        }
        linkedinView.setOnClickListener {
            openBrowser(
                linkedinView,
                contributorLinkedInUrl
            )
        }
        telegramView.setOnClickListener {
            openBrowser(
                telegramView,
                contributorTelegramUrl
            )
        }
        return view;
    }

    fun openBrowser(view: View, url: String?) {
        //Get url from tag
        val intent = Intent()
        intent.setAction(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)

        //pass the url to intent data
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }
}