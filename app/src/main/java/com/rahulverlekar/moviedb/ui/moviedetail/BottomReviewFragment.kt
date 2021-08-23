package com.rahulverlekar.moviedb.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rahulverlekar.moviedb.R
import com.rahulverlekar.moviedb.databinding.BottomSheetReviewBinding
import com.rahulverlekar.moviedb.ext.ListItem
import com.rahulverlekar.moviedb.ext.RecyclerViewCallback
import com.rahulverlekar.moviedb.ext.addDataSource

class BottomReviewFragment : BottomSheetDialogFragment(), RecyclerViewCallback {

    private val binding by lazy {
        BottomSheetReviewBinding.inflate(layoutInflater)
    }

    var listener: BottomSheetInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {
        binding.rvOptions.addDataSource(
            listener?.getOptions() ?: emptyList(),
            R.layout.item_movie_review,
            this
        )
        binding.txtTitle.text = listener?.getTitle()
    }

    override fun onClick(item: ListItem) {
        listener?.onSelected(item as ReviewItem)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): BottomSheetDialogFragment {
            val fragment = BottomReviewFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}

data class ReviewItem(
    val id: String,
    val text: String,
    val author: String
) : ListItem {
}

interface BottomSheetInterface {
    fun getOptions(): List<ReviewItem>
    fun onSelected(item: ReviewItem)
    fun getTitle(): String
}
