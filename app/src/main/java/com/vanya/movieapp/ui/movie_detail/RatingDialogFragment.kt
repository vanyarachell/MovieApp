package com.vanya.movieapp.ui.movie_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.FragmentDialogRatingBinding
import com.vanya.movieapp.util.Constants

class RatingDialogFragment : DialogFragment(R.layout.fragment_dialog_rating) {
    companion object {

        const val TAG = "SimpleDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String, subTitle: String): RatingDialogFragment {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            val fragment = RatingDialogFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private var binding: FragmentDialogRatingBinding? = null

    private var rate = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_rating, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDialogRatingBinding.bind(view)
        binding?.apply {
            btnSubmit.setOnClickListener {
                Log.e("BTN SUBMIT", "HELLO ${rb.rating}")

                // Use the Kotlin extension in the fragment-ktx artifact.
                setFragmentResult(Constants.REQUEST_GET_RATE, bundleOf(Constants.ITEM_RATE to rate))
                dismiss()
            }

            rb.onRatingBarChangeListener =
                OnRatingBarChangeListener { _, rating, _ ->
                    val finalRate = (rating * 20).toInt()
                    tvDetailRate.text = finalRate.toString()
                    rate = finalRate.toString()
                }
        }
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}