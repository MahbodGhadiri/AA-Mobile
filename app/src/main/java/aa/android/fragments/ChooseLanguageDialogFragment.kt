package aa.android.fragments

import aa.android.R
import aa.android.activities.SettingsActivity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.DialogFragment


class ChooseLanguageDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(
                ContextThemeWrapper(
                    it,
                    R.style.LanguageDialogTheme
                )
            )
            it as SettingsActivity
            builder.setTitle(getString(R.string.choose_language))
            builder.setItems(it.languages) { dialog, which ->
                it.onLanguageSelect(which)
            }


            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}

