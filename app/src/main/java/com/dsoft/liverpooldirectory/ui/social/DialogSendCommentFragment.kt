package com.dsoft.liverpooldirectory.ui.social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.databinding.FragmentCommentSectionBottomsheetDialogBinding
import com.dsoft.liverpooldirectory.ui.social.adapter.SocialCommentsRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogSendCommentFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<SocialViewModel>()
    private val binding by viewBinding(FragmentCommentSectionBottomsheetDialogBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View =
            inflater.inflate(R.layout.fragment_comment_section_bottomsheet_dialog, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postId = viewModel.appPreferences.getPostId()
        viewModel.getComments(postId)
        setupRecyclerView()

        binding.closeMessageButton.setOnClickListener {
            dismiss()
        }

        binding.sendButton.setOnClickListener {
            val comment = binding.customEditText.text.toString()
            viewModel.sendMessage(comment, requireContext())
            dismiss()
        }
    }

    private fun setupRecyclerView() {
        val adapter = SocialCommentsRecyclerAdapter()
        val recyclerView = binding.rvRecyclerViewComments
        val dividerDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable)
        val dividerItemDecoration = DividerItemDecoration()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER

        viewModel.listOfComments.observe(viewLifecycleOwner) { commentsList ->
            if (commentsList == null) {
                return@observe
            }
            adapter.commentsList = commentsList
        }

        if (dividerDrawable != null) {
            dividerItemDecoration.dividerItemDecoration(dividerDrawable)
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }
}




