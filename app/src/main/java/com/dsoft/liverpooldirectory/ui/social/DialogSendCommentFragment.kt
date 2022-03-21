package com.dsoft.liverpooldirectory.ui.social

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.databinding.FragmentCommentSectionBottomsheetDialogBinding
import com.dsoft.liverpooldirectory.ui.social.adapter.SocialCommentsRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogSendCommentFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<SocialViewModel>()
    private var _binding: FragmentCommentSectionBottomsheetDialogBinding? = null
    private val binding get() = _binding!!

    private var postId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentCommentSectionBottomsheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        arguments?.let {
            postId = it.getInt("postId")
            viewModel.getComments(postId.toString())
        }

        binding.closeMessageButton.setOnClickListener {
            dismiss()
        }

        binding.sendButton.setOnClickListener {
            val comment = binding.customEditText.text.toString()
            viewModel.sendMessage(comment, requireContext(), postId.toString())
            Log.d("Test222", "postId: $postId")
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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




